package commands;

import commands.lavaplayer.GuildMusicManager;
import commands.lavaplayer.LavaPlayer;
import commands.lavaplayer.PlayerMode;
import commands.utils.QuickEmbedBuilder;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.SlashCommandInteraction;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;

public class ModeCommand implements Command {
    @Override
    public SlashCommandData data() {
        OptionData option = new OptionData(OptionType.STRING, "mode", "The mode you want to set the player to.", true);
        for (PlayerMode mode : PlayerMode.values()) {
            option.addChoice(mode.name, mode.toString());
        }
        return Commands.slash("mode", "Set the player mode. (loop, shuffle, etc)").addOptions(option);
    }

    @Override
    public void onRun(SlashCommandInteraction event, LavaPlayer lavaPlayer) {
        if (event.getGuild().getAudioManager().getConnectedChannel() == null) {
            event.replyEmbeds(QuickEmbedBuilder.generateErrorEmbed("I am not in a voice channel! Use /join before using music commands!")).queue();
            return;
        }
        GuildMusicManager guildMusicManager = lavaPlayer.getGuildAudioPlayer(event.getGuild());
        PlayerMode mode = PlayerMode.valueOf(event.getOption("mode").getAsString());
        String name = mode.name;
        event.replyEmbeds(QuickEmbedBuilder.generateQueueActionEmbed("Set the mode to **" + name + "** successfully!")).setEphemeral(false).queue();
        guildMusicManager.scheduler.setMode(mode);
    }
}
