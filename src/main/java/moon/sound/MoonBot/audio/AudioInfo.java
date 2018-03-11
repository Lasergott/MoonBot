package moon.sound.MoonBot.audio;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import net.dv8tion.jda.core.entities.Member;

public class AudioInfo {

    private final AudioTrack audioTrack;
    private final Member author;

    AudioInfo(AudioTrack audioTrack, Member author) {
        this.audioTrack = audioTrack;
        this.author = author;
    }

    public AudioTrack getTrack() {
        return audioTrack;
    }

    public Member getAuthor() {
        return author;
    }

}