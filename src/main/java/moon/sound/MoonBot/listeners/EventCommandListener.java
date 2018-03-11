package moon.sound.MoonBot.listeners;

import moon.sound.MoonBot.MoonBot;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import moon.sound.MoonBot.utils.CommandHandler;
import moon.sound.MoonBot.utils.CommandParser;

public class EventCommandListener extends ListenerAdapter {

    @Override
    public void onMessageReceived(MessageReceivedEvent e) {
        if(e.getMessage().getContentRaw().startsWith(MoonBot.getInstance().getData().getMessage("commandPrefix")) && e.getMessage().getAuthor().getId() != e.getJDA().getSelfUser().getId())
            CommandHandler.handleCommand(CommandParser.parser(e.getMessage().getContentRaw(), e));
    }
}
