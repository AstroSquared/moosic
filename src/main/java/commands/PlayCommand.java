package commands;

import commands.lavaplayer.LavaPlayer;
import commands.utils.QuickEmbedBuilder;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.SlashCommandInteraction;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;

public class PlayCommand implements Command {
    @Override
    public SlashCommandData data() {
        return Commands.slash("play", "Adds a song to the queue")
                .addOption(OptionType.STRING, "song", "The name/link of the song to play", true);
    }

    @Override
    public void onRun(SlashCommandInteraction event, LavaPlayer lavaPlayer) {
        String song = event.getOption("song").getAsString();
        if (event.getGuild().getAudioManager().getConnectedChannel() == null) {
            event.replyEmbeds(QuickEmbedBuilder.generateErrorEmbed("I am not in a voice channel! Use /join before using music commands!")).queue();
            return;
        }
        lavaPlayer.loadAndPlay(event.getGuild(), song, event);
    }
}
