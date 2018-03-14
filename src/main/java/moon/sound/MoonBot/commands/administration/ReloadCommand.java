package moon.sound.MoonBot.commands.administration;

import moon.sound.MoonBot.MoonBot;
import moon.sound.MoonBot.utils.Command;
import moon.sound.MoonBot.manager.MessageManager;
import moon.sound.MoonBot.utils.PermsHandler;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.awt.*;

import static moon.sound.MoonBot.MoonBot.info;

public class ReloadCommand implements Command {

    @Override
    public void executed(MessageReceivedEvent event) {
        info("User " + event.getAuthor().getName() + " executed the command " + event.getMessage().getContentRaw());
    }


    @Override
    public void action(String[] args, MessageReceivedEvent e) {
        if (PermsHandler.check(e))
            return;
        if (args.length == 0) {
            MoonBot.getInstance().getFileManager().load();
            info(e.getAuthor().getName() + " has reloaded the bot configuration.");
            MessageManager.sendAll(MoonBot.getInstance().getData().getMessage("reload-Message").replace("{member}", e.getMember().getEffectiveName()), MoonBot.getInstance().getData().getMessage("reload-Channel"), Color.GREEN);
        }
    }
}
