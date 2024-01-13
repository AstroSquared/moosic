import commands.*;
import commands.lavaplayer.GuildMusicManager;
import commands.lavaplayer.LavaPlayer;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Commands extends ListenerAdapter {
    public Commands(JDA jda) {
        System.out.println("Starting bot...");
        for (Command command :
                commands) {
            System.out.println("Loading command " + command.data().getName());
            jda.upsertCommand(command.data()).queue();
            System.out.println("Loaded command " + command.data().getName());
        }

        System.out.println("Reloading commands...");
        jda.updateCommands().queue();
    }

    public Command[] commands = {
            new PlayCommand(),
            new JoinCommand(),
            new LeaveCommand(),
            new QueueCommand(),
            new StopCommand(),
            new HelpCommand(),
            new SkipCommand(),
            new PauseCommand(),
            new ClearCommand(),
            new InviteCommand(),
            new NowPlayingCommand(),
            new ModeCommand(),
            new VolumeCommand()
    };

    private final LavaPlayer lavaplayer = new LavaPlayer();

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        String name = event.getName();

        if (event.getGuild() == null) {
            event.reply("Commands are disabled in DMs").queue();
            return;
        }

        for (Command command :
                commands) {
            String commandName = command.data().getName();
            if (name.equals(commandName)) {
                System.out.println("[" + event.getGuild().getId() + "] COMMAND EXECUTED " + name);
                command.onRun(event, lavaplayer);
                break;
            }
        }
    }

    @Override
    public void onButtonInteraction(ButtonInteractionEvent event) {
        if (event.getGuild() == null) return; // this should be impossible, don't bother lol
        GuildMusicManager guildMusicManager = lavaplayer.getGuildAudioPlayer(event.getGuild());
        switch (event.getComponentId()) {
            case "skip":
                guildMusicManager.scheduler.nextTrack(guildMusicManager.player.getPlayingTrack());
                event.reply("Skipped to next track.").setEphemeral(false).queue();
            case "play":
                guildMusicManager.scheduler.setPaused(false);
                event.reply("Unpaused the player.").setEphemeral(false).queue();
            case "pause":
                guildMusicManager.scheduler.setPaused(true);
                event.reply("Paused the player.").setEphemeral(false).queue();
            case "queue":
                MessageEmbed embed = new QueueCommand().getQueue(lavaplayer, event.getGuild());
                event.replyEmbeds(embed).setEphemeral(true).queue();
            case "volume_lower":
                lavaplayer.getGuildAudioPlayer(event.getGuild()).player.setVolume(20);
                event.reply("Volume is now set to: Lower (20%)").queue();
                break;
            case "volume_low":
                lavaplayer.getGuildAudioPlayer(event.getGuild()).player.setVolume(60);
                event.reply("Volume is now set to: Low (60%)").queue();
                break;
            case "volume_normal":
                lavaplayer.getGuildAudioPlayer(event.getGuild()).player.setVolume(100);
                event.reply("Volume is now set to: Normal (100%)").queue();
                break;
            case "volume_high":
                lavaplayer.getGuildAudioPlayer(event.getGuild()).player.setVolume(140);
                event.reply("Volume is now set to: High (140%)").queue();
                break;
            case "volume_higher":
                lavaplayer.getGuildAudioPlayer(event.getGuild()).player.setVolume(180);
                event.reply("Volume is now set to: Higher (180%)").queue();
                break;
        }
    }
}
