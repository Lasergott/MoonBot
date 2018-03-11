package moon.sound.MoonBot.commands.admintration;

import moon.sound.MoonBot.MoonBot;
import moon.sound.MoonBot.utils.Command;
import moon.sound.MoonBot.manager.JdaManager;
import moon.sound.MoonBot.manager.MessageManager;
import moon.sound.MoonBot.utils.PermsHandler;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.awt.*;

import static moon.sound.MoonBot.MoonBot.info;


public class OpenSupportCMD implements Command {

    @Override
    public void executed(MessageReceivedEvent event) {
        info("User " + event.getAuthor().getName() + " executed the command " + event.getMessage().getContentRaw());
    }


    @Override
    public void action(String[] args, MessageReceivedEvent e) {
        if(PermsHandler.check(e))
            return;
        if (args.length > 0) {
            try {
                Guild guild = JdaManager.getJda().getGuildsByName(args[0], false).get(0);
                MoonBot.getInstance().getCommandManager().openSupport(guild, e.getMember().getEffectiveName());
            } catch (Exception e1) {
                MessageManager.sendDiscordPrivateMessage("Please use .openSupport <Guild>", e.getMember().getUser(), Color.red);
            }
        }
    }

}
