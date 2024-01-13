package commands;

import commands.lavaplayer.LavaPlayer;
import commands.utils.QuickEmbedBuilder;
import net.dv8tion.jda.api.interactions.commands.SlashCommandInteraction;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;
import net.dv8tion.jda.api.managers.AudioManager;

public class LeaveCommand implements Command {
    @Override
    public SlashCommandData data() {
        return Commands.slash("leave", "Leave's the bot's current voice channel");
    }

    @Override
    public void onRun(SlashCommandInteraction event, LavaPlayer lavaPlayer) {
        AudioManager audioManager = event.getGuild().getAudioManager();

        if (audioManager.getConnectedChannel() == null) {
            event.replyEmbeds(QuickEmbedBuilder.generateErrorEmbed("I am not in a voice channel!")).queue();
            return;
        }

        audioManager.closeAudioConnection();
        event.replyEmbeds(QuickEmbedBuilder.generateSuccessEmbed("Successfully disconnected!")).queue();
    }
}
