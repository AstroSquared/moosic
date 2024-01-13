package commands.lavaplayer;

import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackInfo;
import net.dv8tion.jda.api.entities.Guild;
import commands.utils.*;
import net.dv8tion.jda.api.interactions.commands.SlashCommandInteraction;

import java.net.URL;
import java.util.HashMap;

public class LavaPlayer {
    private final DefaultAudioPlayerManager playerManager;
    private final HashMap<Object, Object> musicManagers;

    public LavaPlayer() {
        this.musicManagers = new HashMap<>();

        this.playerManager = new DefaultAudioPlayerManager();
        AudioSourceManagers.registerRemoteSources(playerManager);
        AudioSourceManagers.registerLocalSource(playerManager);
    }


    public synchronized GuildMusicManager getGuildAudioPlayer(Guild guild) {
        long guildId = Long.parseLong(guild.getId());
        GuildMusicManager musicManager = (GuildMusicManager) musicManagers.get(guildId);

        if (musicManager == null) {
            musicManager = new GuildMusicManager(playerManager);
            musicManagers.put(guildId, musicManager);
        }

        guild.getAudioManager().setSendingHandler(musicManager.getSendHandler());

        return musicManager;
    }

    public void loadAndPlay(final Guild guild, final String urlOrSearch, final SlashCommandInteraction hook) {
        GuildMusicManager musicManager = getGuildAudioPlayer(guild);

        final String trackUrl;
        String trackUrl1;
        try {
            URL tempUrl = new URL(urlOrSearch);
            trackUrl1 = urlOrSearch;
        } catch (Exception e) {
            trackUrl1 = "ytsearch:" + urlOrSearch;
        }

        trackUrl = trackUrl1;
        playerManager.loadItemOrdered(musicManager, trackUrl, new AudioLoadResultHandler() {
            @Override
            public void trackLoaded(AudioTrack track) {
                AudioTrackInfo trackInfo = track.getInfo();
                hook.replyEmbeds(QuickEmbedBuilder.generateQueueActionEmbed("**Adding to queue**\n" + trackInfo.title + "\nby " + trackInfo.author)).queue();

                play(musicManager, track);
            }

            @Override
            public void playlistLoaded(AudioPlaylist playlist) {
                if (!playlist.getName().startsWith("Search results for:")) {
                    hook.replyEmbeds(QuickEmbedBuilder.generateQueueActionEmbed("**Adding to queue**\n" + playlist.getName() + "\n(playlist)")).queue();

                    for (AudioTrack track : playlist.getTracks()) {
                        play(musicManager, track);
                    }
                } else {
                    AudioTrack search = playlist.getTracks().get(0);
                    AudioTrackInfo searchInfo = search.getInfo();
                    hook.replyEmbeds(QuickEmbedBuilder.generateQueueActionEmbed("**Adding to queue**\n" + searchInfo.title + "\nby " + searchInfo.author + "\n(youtube search)")).queue();play(musicManager, search);
                }
            }

            @Override
            public void noMatches() {
                if (!trackUrl.startsWith("ytsearch:")) {
                    hook.reply("Nothing found. Is the URL incorrect?").queue();
                } else {
                    hook.reply("Could not find that song on YouTube.").queue();
                }
            }

            @Override
            public void loadFailed(FriendlyException exception) {
                hook.reply("An error occured. Please report this to staff: " + exception.getMessage() + "\nNote: spotify is not supported yet.").queue();
            }
        });
    }

    private void play(GuildMusicManager musicManager, AudioTrack track) {
        musicManager.scheduler.queue(track);
    }

    public void skipTrack(Guild guild, SlashCommandInteraction hook) {
        GuildMusicManager musicManager = getGuildAudioPlayer(guild);
        musicManager.scheduler.nextTrack(musicManager.player.getPlayingTrack());

        hook.replyEmbeds(QuickEmbedBuilder.generateQueueActionEmbed("Skipped to next track.")).queue();
    }

    public static void resetPlayer(GuildMusicManager guildMusicManager) {
        guildMusicManager.player.stopTrack();
        guildMusicManager.scheduler.clear();
        guildMusicManager.scheduler.setMode(PlayerMode.REGULAR);
        guildMusicManager.player.setVolume(100);
    }
}
