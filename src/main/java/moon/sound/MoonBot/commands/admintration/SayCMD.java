package moon.sound.MoonBot.commands.admintration;

import moon.sound.MoonBot.utils.Command;
import moon.sound.MoonBot.manager.JdaManager;
import moon.sound.MoonBot.manager.MessageManager;
import moon.sound.MoonBot.utils.PermsHandler;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.awt.*;

import static moon.sound.MoonBot.MoonBot.info;

public class SayCMD implements Command {


    @Override
    public void executed(MessageReceivedEvent event) {
        info("User " + event.getAuthor().getName() + " executed the command " + event.getMessage().getContentRaw());
    }

    @Override
    public void action(String[] args, MessageReceivedEvent e) {
        if (PermsHandler.check(e))
            return;
        if (args.length > 0) {
            StringBuilder sb = new StringBuilder();
            for (int i = 2; i < args.length; i++) {
                sb.append(args[i]).append(" ");
            }
            String allArgs = sb.toString().trim();
            try {
                Guild guild = JdaManager.getJda().getGuildsByName(args[0], false).get(0);
                String channel = args[1];
                MessageManager.sendDiscordMessage(e, "Alert", allArgs, Color.GREEN);
                info(e.getMember().getEffectiveName() + " send successful a message to " + guild.getName() + " (" + channel + ", Message: " + allArgs + ")");
            } catch (Exception exception) {
                MessageManager.sendDiscordPrivateMessage("Please use: .say <Guild> <Channel> <Message>", e.getMember().getUser(), Color.RED);
            }
        }
    }
}

