package moon.sound.MoonBot.utils;

import moon.sound.MoonBot.MoonBot;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.util.ArrayList;
import java.util.Arrays;

public class CommandParser {

    public static commandContainer parser(String raw, MessageReceivedEvent e) {
        String beheaded = raw.replaceFirst(MoonBot.getInstance().getData().getMessage("commandPrefix"), "");
        String[] splitBeheaded = beheaded.split(" ");
        String invoke = splitBeheaded[0];
        ArrayList<String> split = new ArrayList<>(Arrays.asList(splitBeheaded));
        String[] args = new String[split.size() - 1];
        split.subList(1, split.size()).toArray(args);
        return new commandContainer(raw, beheaded, splitBeheaded, invoke, args, e);
    }

    public static class commandContainer {
        public final String raw;
        public final String beheaded;
        public final String[] splitBeheaded;
        public final String invoke;
        public final String[] args;
        public final MessageReceivedEvent e;

        public commandContainer(String rw, String beheaded, String[] splitBeheaded, String invoke, String[] args, MessageReceivedEvent e) {
            this.raw = rw;
            this.beheaded = beheaded;
            this.splitBeheaded = splitBeheaded;
            this.invoke = invoke;
            this.args = args;
            this.e = e;
        }
    }
}
