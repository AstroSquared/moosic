package commands;

import commands.lavaplayer.GuildMusicManager;
import commands.lavaplayer.LavaPlayer;
import commands.utils.QuickEmbedBuilder;
import net.dv8tion.jda.api.interactions.commands.SlashCommandInteraction;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;

public class StopCommand implements Command {
    @Override
    public SlashCommandData data() {
        return Commands.slash("stop", "Force stop the player.");
    }

    @Override
    public void onRun(SlashCommandInteraction event, LavaPlayer lavaPlayer) {
        if (event.getGuild().getAudioManager().getConnectedChannel() == null) {
            event.replyEmbeds(QuickEmbedBuilder.generateErrorEmbed("I am not in a voice channel! Use /join before using music commands!")).queue();
            return;
        }
        GuildMusicManager guildMusicManager = lavaPlayer.getGuildAudioPlayer(event.getGuild());
        LavaPlayer.resetPlayer(guildMusicManager);
        event.replyEmbeds(QuickEmbedBuilder.generateQueueActionEmbed("Stopped the player!")).setEphemeral(false).queue();
    }
}
