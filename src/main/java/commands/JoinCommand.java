package commands;

import commands.lavaplayer.LavaPlayer;
import commands.utils.QuickEmbedBuilder;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.channel.unions.AudioChannelUnion;
import net.dv8tion.jda.api.interactions.commands.SlashCommandInteraction;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;
import net.dv8tion.jda.api.managers.AudioManager;

public class JoinCommand implements Command {
    @Override
    public SlashCommandData data() {
        return Commands.slash("join", "Joins the voice channel you are currently in");
    }

    @Override
    public void onRun(SlashCommandInteraction event, LavaPlayer lavaPlayer) {
        AudioManager audioManager = event.getGuild().getAudioManager();
        GuildVoiceState voiceState = event.getMember().getVoiceState();
        if (voiceState == null) {
            event.replyEmbeds(QuickEmbedBuilder.generateErrorEmbed("Please join a voice channel!")).queue();
            return;
        }
        AudioChannelUnion voiceChannel = voiceState.getChannel();
        if (voiceChannel == null) {
            event.replyEmbeds(QuickEmbedBuilder.generateErrorEmbed("Please join a voice channel!")).queue();
            return;
        }
        lavaPlayer.getGuildAudioPlayer(event.getGuild()).scheduler.clear();
        audioManager.setSelfDeafened(true);
        audioManager.openAudioConnection(voiceChannel);
        event.replyEmbeds(QuickEmbedBuilder.generateSuccessEmbed("Connected to " + voiceChannel.getName() + "!")).queue();
    }
}
