package moon.sound.MoonBot.utils;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public interface Command {

    void action(String[] args, MessageReceivedEvent event);
    void executed(MessageReceivedEvent event);

}
