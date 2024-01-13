package commands;

import commands.lavaplayer.LavaPlayer;
import commands.utils.ButtonBuilder;
import commands.utils.QuickEmbedBuilder;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.SlashCommandInteraction;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;

public class PauseCommand implements Command {
    @Override
    public SlashCommandData data() {
        return Commands.slash("pause", "Set if the player is paused or not.")
                .addOption(OptionType.BOOLEAN, "paused", "True = paused, False = unpaused", true);
    }

    @Override
    public void onRun(SlashCommandInteraction event, LavaPlayer lavaPlayer) {
        if (event.getGuild().getAudioManager().getConnectedChannel() == null) {
            event.replyEmbeds(QuickEmbedBuilder.generateErrorEmbed("I am not in a voice channel! Use /join before using music commands!")).queue();
            return;
        }
        boolean paused = event.getOption("paused").getAsBoolean();
        lavaPlayer.getGuildAudioPlayer(event.getGuild()).scheduler.setPaused(paused);
        String pausedText;
        if (paused) {
            pausedText = "paused";
        } else {
            pausedText = "unpaused";
        }
        event.replyEmbeds(QuickEmbedBuilder.generateQueueActionEmbed("Successfully " + pausedText + " the player!")).addActionRow(ButtonBuilder.PLAY, ButtonBuilder.PAUSE).queue();
    }
}
