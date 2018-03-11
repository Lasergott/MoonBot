package moon.sound.MoonBot.audio;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.track.playback.AudioFrame;
import net.dv8tion.jda.core.audio.AudioSendHandler;

public class AudioPlayerSendHandler implements AudioSendHandler {

    private final AudioPlayer audioPlayer;
    private AudioFrame audioFrame;

    public AudioPlayerSendHandler(AudioPlayer audioPlayer) {
        this.audioPlayer = audioPlayer;
    }

    @Override
    public boolean canProvide() {
        if (audioFrame == null) {
            audioFrame = audioPlayer.provide();
        }
        return audioFrame != null;
    }

    @Override
    public byte[] provide20MsAudio() {
        if (audioFrame == null) {
            audioFrame = audioPlayer.provide();
        }
        byte[] data = audioFrame != null ? audioFrame.data : null;
        audioFrame = null;
        return data;
    }

    @Override
    public boolean isOpus() {
        return true;
    }

}