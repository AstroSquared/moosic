package commands;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackInfo;
import commands.lavaplayer.LavaPlayer;
import commands.utils.ButtonBuilder;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.interactions.commands.SlashCommandInteraction;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;

import java.awt.Color;

public class NowPlayingCommand implements Command {
    @Override
    public SlashCommandData data() {
        return Commands.slash("nowplaying", "Shows the time left on the currently playing song.");
    }

    @Override
    public void onRun(SlashCommandInteraction event, LavaPlayer lavaPlayer) {
        EmbedBuilder embed = new EmbedBuilder();
        embed.setTitle("Now Playing");
        embed.setColor(Color.ORANGE);
        try {
            AudioTrack nowPlaying = lavaPlayer.getGuildAudioPlayer(event.getGuild()).player.getPlayingTrack();
            AudioTrackInfo npInfo = nowPlaying.getInfo();

            embed.addField("Now Playing", npInfo.title + "\nby " + npInfo.author, false);
        } catch (Exception e) {
            embed.addField("Nothing is playing", "Nothing is playing, use /play to add some songs!", false);
        }

        event.replyEmbeds(embed.build()).addActionRow(ButtonBuilder.QUEUE, ButtonBuilder.PLAY, ButtonBuilder.PAUSE, ButtonBuilder.SKIP).queue();
    }
}
