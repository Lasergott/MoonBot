package moon.sound.MoonBot.commands.admintration;

import moon.sound.MoonBot.MoonBot;
import moon.sound.MoonBot.utils.Command;
import moon.sound.MoonBot.manager.JdaManager;
import moon.sound.MoonBot.manager.MessageManager;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import moon.sound.MoonBot.utils.PermsHandler;

import java.awt.*;

import static moon.sound.MoonBot.MoonBot.info;


public class CloseSupportCMD implements Command {

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
            MoonBot.getInstance().getCommandManager().closeSupport(guild, e.getMember().getEffectiveName());
        } catch (Exception e1) {
            MessageManager.sendDiscordPrivateMessage("Please use .closeSupport <Guild>", e.getMember().getUser(), Color.red);
        }
        }
    }


    public String help(MessageReceivedEvent event) {
        return MoonBot.getInstance().getData().getMessage("commandPrefix") + "closeSupport";
    }
}
