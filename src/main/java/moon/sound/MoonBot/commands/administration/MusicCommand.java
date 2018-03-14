package moon.sound.MoonBot.commands.administration;

import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackInfo;
import moon.sound.MoonBot.MoonBot;
import moon.sound.MoonBot.audio.AudioInfo;
import moon.sound.MoonBot.audio.AudioPlayerSendHandler;
import moon.sound.MoonBot.audio.TrackManager;
import moon.sound.MoonBot.utils.Command;
import moon.sound.MoonBot.utils.PermsHandler;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

import static moon.sound.MoonBot.MoonBot.info;

public class MusicCommand implements Command {

    private static final int PLAYLIST_LIMIT = 1000;
    private Guild guild;
    private static final AudioPlayerManager MANAGER = new DefaultAudioPlayerManager();
    private static final Map<Guild, Map.Entry<AudioPlayer, TrackManager>> PLAYERS = new HashMap<>();

    public MusicCommand(){
        AudioSourceManagers.registerRemoteSources(MANAGER);
    }

    private AudioPlayer createPlayer(Guild guild){
        AudioPlayer audioPlayer = MANAGER.createPlayer();
        TrackManager trackManager = new TrackManager(audioPlayer);
        audioPlayer.addListener(trackManager);
        guild.getAudioManager().setSendingHandler(new AudioPlayerSendHandler(audioPlayer));
        PLAYERS.put(guild, new AbstractMap.SimpleEntry<>(audioPlayer, trackManager));
        return audioPlayer;
    }

    private boolean hasPlayer(Guild guild){
        return PLAYERS.containsKey(guild);
    }

    private AudioPlayer getPlayer(Guild guild){
        if(hasPlayer(guild))
            return PLAYERS.get(guild).getKey();
        else
            return createPlayer(guild);
    }

    private TrackManager getManager(Guild guild){
        return PLAYERS.get(guild).getValue();
    }

    private boolean isIdle(Guild guild){
        return !hasPlayer(guild) || getPlayer(guild).getPlayingTrack() == null;
    }

    private void loadTrack(String id, Member author, Message message){
        Guild guild = author.getGuild();
        getPlayer(guild);
        MANAGER.setFrameBufferDuration(10000);
        MANAGER.loadItemOrdered(guild, id, new AudioLoadResultHandler() {
            @Override
            public void trackLoaded(AudioTrack audioTrack) {
                getManager(guild).queue(audioTrack, author);
            }

            @Override
            public void playlistLoaded(AudioPlaylist audioPlaylist) {
                if (audioPlaylist.getSelectedTrack() != null) {
                    trackLoaded(audioPlaylist.getSelectedTrack());
                } else if (audioPlaylist.isSearchResult()) {
                    trackLoaded(audioPlaylist.getTracks().get(0));
                } else {
                    try {
                        for (int i = 0; i < Math.min(audioPlaylist.getTracks().size(), PLAYLIST_LIMIT); i++) {
                            getTrackManager(guild).queue(audioPlaylist.getTracks().get(i), author);
                        }
                    }catch (NullPointerException ignored){
                        ignored.printStackTrace();
                    }
                }
            }

            @Override
            public void noMatches() {
            }

            @Override
            public void loadFailed(FriendlyException e) {
            }
        });
    }

    private void skip(Guild guild){
        getPlayer(guild).stopTrack();
    }

    private String getTimeStamp(long millis){
        long seconds = millis / 1000;
        long hours = Math.floorDiv(seconds, 3600);
        seconds = seconds - (hours * 3600);
        long mins = Math.floorDiv(seconds, 60);
        seconds = seconds - (mins * 60);
        return (hours == 0 ? "" : hours + ":") + String.format("%02d", mins) + ":" + String.format("%02d", seconds);
    }

    private String buildQueueMessage(AudioInfo audioInfo){
        AudioTrackInfo audioTrackInfo = audioInfo.getTrack().getInfo();
        String title = audioTrackInfo.title;
        long length = audioTrackInfo.length;
        return "`[ " + getTimeStamp(length) + " ]` " + title + "\n";
    }

    private void sendErrorMessage(MessageReceivedEvent event, String title, String content){
        event.getTextChannel().sendMessage(new EmbedBuilder()
                .setColor(Color.RED)
                .addField(title, content, false)
                .build())
                .queue();
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) {
        if(PermsHandler.check(event))
            return;
        guild = event.getGuild();
        if(args.length < 1){
            sendErrorMessage(event, "Error", "Please use " +
                    MoonBot.getInstance().getData().getMessage("commandPrefix") + "music <play/loop/shuffle/queue/skip/stop> <url>");
            return;
        }
        switch (args[0].toLowerCase()){
            case "play":
            case "p":
                if(args.length < 2){
                    sendErrorMessage(event, "Error", "Please enter a valid source!");
                    return;
                }
                String input = Arrays.stream(args).skip(1).map(s -> " " + s).collect(Collectors.joining()).substring(1);
                if(!(input.startsWith("http://") || input.startsWith("https://")))
                    input = "ytsearch: " + input;
                loadTrack(input, event.getMember(), event.getMessage());
                break;
            case "skip":
            case "s":
                if(isIdle(guild))
                    return;
                for(int i = (args.length > 1 ? Integer.parseInt(args[1]) : 1); i == 1; i--)
                    skip(guild);
                break;
            case "stop":
                if(isIdle(guild))
                    return;
                getManager(guild).purgeQueue();
                skip(guild);
                guild.getAudioManager().closeAudioConnection();
                break;
            case "shuffle":
                if(isIdle(guild))
                    return;
                getManager(guild).shuffleQueue();
                break;
            case "now":
            case "info":
            case "playing":
                if(isIdle(guild))
                    return;
                AudioTrack audioTrack = getPlayer(guild).getPlayingTrack();
                AudioTrackInfo audioTrackInfo = audioTrack.getInfo();
                event.getTextChannel().sendMessage(new EmbedBuilder()
                        .setColor(Color.GRAY)
                        .setDescription("**CURRENT TRACK INFO:**")
                        .addField("Title", audioTrackInfo.title, false)
                        .addField("Duration", "`[" + getTimeStamp(audioTrack.getPosition()) + "/" + getTimeStamp(audioTrack.getDuration()) + "]`", false)
                        .addField("Author", audioTrackInfo.author, false)
                        .build())
                        .queue();
                break;
            case "queue":
            case "qu":
                if(isIdle(guild))
                    return;
                int sideNumb = args.length > 1 ? Integer.parseInt(args[1]) : 1;
                List<String> tracks = new ArrayList<>();
                List<String> trackSubList;
                getManager(guild).getQueuedTracks().forEach(audioTrackInf -> tracks.add(buildQueueMessage(audioTrackInf)));
                if(tracks.size() > 10)
                    trackSubList = tracks.subList((sideNumb - 1) * 10, (sideNumb - 1) * 10 + 10);
                else
                    trackSubList = tracks;
                String out = trackSubList.stream().collect(Collectors.joining("\n"));
                int sideNumbAll = tracks.size() >= 10 ? tracks.size() / 10 : 1;
                event.getTextChannel().sendMessage(new EmbedBuilder()
                        .setColor(Color.GRAY)
                        .setDescription(("**CURRENT QUEUE:**\n\n" +
                                "*[" + getManager(guild).getQueuedTracks().toArray().length + " Tracks | Side " + sideNumb + " / " + sideNumbAll + "]*\n\n" +
                                out))
                        .build())
                        .queue();
                break;
        }
    }

    @Override
    public void executed(MessageReceivedEvent event) {
        info("User " + event.getAuthor().getName() + " executed the command " + event.getMessage().getContentRaw());
    }

    @SuppressWarnings("SuspiciousMethodCalls")
    private TrackManager getTrackManager(Guild guild) {
        return PLAYERS.get(guild.getId()).getValue();
    }
}