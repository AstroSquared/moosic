package commands;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackInfo;
import commands.lavaplayer.LavaPlayer;
import commands.utils.QuickEmbedBuilder;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.interactions.commands.SlashCommandInteraction;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;

import java.awt.Color;

public class QueueCommand implements Command {
    @Override
    public SlashCommandData data() {
        return Commands.slash("queue", "View the songs that are going to play next");
    }

    @Override
    public void onRun(SlashCommandInteraction event, LavaPlayer lavaPlayer) {
        if (event.getGuild().getAudioManager().getConnectedChannel() == null) {
            event.replyEmbeds(QuickEmbedBuilder.generateErrorEmbed("I am not in a voice channel! Use /join before using music commands!")).queue();
            return;
        }
        event.replyEmbeds(getQueue(lavaPlayer, event.getGuild())).queue();
    }

    public MessageEmbed getQueue(LavaPlayer lavaPlayer, Guild guild) {
        Object[] tracks = lavaPlayer.getGuildAudioPlayer(guild).scheduler.getQueue();
        StringBuilder desc = new StringBuilder();
        for (Object trackObject :
                tracks) {
            AudioTrack track = (AudioTrack) trackObject;
            desc.append(track.getInfo().title + " by " + track.getInfo().author + " [[Link]](" + track.getInfo().uri + ") \n");
        }
        if (tracks.length == 0) {
            desc.append("Nothing is up next! Add songs with /play!");
        }

        EmbedBuilder embed = new EmbedBuilder();
        embed.setTitle("Queue");
        embed.setColor(Color.ORANGE);
        try {
            AudioTrack nowPlaying = lavaPlayer.getGuildAudioPlayer(guild).player.getPlayingTrack();
            AudioTrackInfo npInfo = nowPlaying.getInfo();

            embed.addField("Now Playing", npInfo.title + " by " + npInfo.author + " [[Link]](" + npInfo.uri + ")", false);
        } catch (Exception e) {
            embed.addField("Now Playing", "Nothing is playing, use /play to add some songs!", false);
        }
        embed.addField("Playing next", desc.toString(), false);
        return embed.build();
    }
}
