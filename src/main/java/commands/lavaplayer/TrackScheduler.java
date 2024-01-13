package commands.lavaplayer;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.event.AudioEventAdapter;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackEndReason;

import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * This class schedules tracks for the audio player. It contains the queue of tracks.
 */
public class TrackScheduler extends AudioEventAdapter {
    private final AudioPlayer player;
    private final BlockingQueue<AudioTrack> queue;
    private PlayerMode mode;

    /**
     * @param player The audio player this scheduler uses
     */
    public TrackScheduler(AudioPlayer player) {
        this.player = player;
        this.queue = new LinkedBlockingQueue<>();
        this.mode = PlayerMode.REGULAR;
    }

    /**
     * Add the next track to queue or play right away if nothing is in the queue.
     *
     * @param track The track to play or add to queue.
     */
    public void queue(AudioTrack track) {
        // Calling startTrack with the noInterrupt set to true will start the track only if nothing is currently playing. If
        // something is playing, it returns false and does nothing. In that case the player was already playing so this
        // track goes to the queue instead.
        if (!player.startTrack(track, true)) {
            queue.offer(track);
        }
    }

    /**
     * Start the next track, stopping the current one if it is playing.
     */
    public void nextTrack(AudioTrack currentTrack) {
        // Start the next track, regardless of if something is already playing or not. In case queue was empty, we are
        // giving null to startTrack, which is a valid argument and will simply stop the player.
        if (currentTrack == null) {
            player.stopTrack();
            return;
        }

        switch (mode) {
            case REGULAR:
                player.startTrack(queue.poll(), false);
                break;
            case LOOP:
                queue.add(currentTrack.makeClone());
                player.startTrack(queue.poll(), false);
                break;
            case LOOP_TRACK:
                player.startTrack(currentTrack.makeClone(), false);
                break;
            case SHUFFLE:
                AudioTrack[] arrayTracks = queue.toArray(new AudioTrack[0]);
                AudioTrack randomTrack = arrayTracks[new Random().nextInt(arrayTracks.length)];
                queue.remove(randomTrack);
                player.startTrack(randomTrack.makeClone(), false);
                break;
            default:
                player.startTrack(null, false);
                break;
        }
    }

    @Override
    public void onTrackEnd(AudioPlayer player, AudioTrack track, AudioTrackEndReason endReason) {
        // Only start the next track if the end reason is suitable for it (FINISHED or LOAD_FAILED)
        if (endReason.mayStartNext) {
            nextTrack(track);
        }
    }

    public Object[] getQueue() {
        return queue.stream().toArray();
    }

    public void setPaused(boolean paused) {
        player.setPaused(paused);
    }

    public void clear() {
        queue.clear();
    }

    public PlayerMode getMode() {
        return mode;
    }

    public void setMode(PlayerMode mode) {
        this.mode = mode;
    }
}