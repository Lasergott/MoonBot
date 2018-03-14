package moon.sound.MoonBot.commands.administration;

import moon.sound.MoonBot.MoonBot;
import moon.sound.MoonBot.utils.Command;
import moon.sound.MoonBot.utils.PermsHandler;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import static moon.sound.MoonBot.MoonBot.info;

public class StopCommand implements Command {

    @Override
    public void executed(MessageReceivedEvent event) {
        info("User " + event.getAuthor().getName() + " executed the command " + event.getMessage().getContentRaw());
    }


    @Override
    public void action(String[] args, MessageReceivedEvent e) {
        if(PermsHandler.check(e))
            return;
        if(args.length == 0) {
            MoonBot.getInstance().getCommandManager().stopBot();
        }
    }
}

