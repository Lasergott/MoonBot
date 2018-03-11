package moon.sound.MoonBot.listeners;

import net.dv8tion.jda.core.events.message.priv.PrivateMessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class PrivateMessageReceived extends ListenerAdapter {

    @Override
    public void onPrivateMessageReceived(PrivateMessageReceivedEvent event) {
        if (event.getMessage().getAuthor() != event.getJDA().getSelfUser())
            event.getChannel().sendMessage("Sorry, but i cannot write with you :frowning2:").queue();
    }
}
