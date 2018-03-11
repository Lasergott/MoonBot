package moon.sound.MoonBot.utils;

import moon.sound.MoonBot.MoonBot;
import moon.sound.MoonBot.manager.MessageManager;
import net.dv8tion.jda.core.entities.ChannelType;
import net.dv8tion.jda.core.entities.Role;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.awt.*;
import java.util.Arrays;

public class PermsHandler {

    public static boolean check(MessageReceivedEvent e) {
        if (e.getMessage().getChannelType() != ChannelType.PRIVATE) {
            for (Role r : e.getGuild().getMember(e.getAuthor()).getRoles()) {
                if (Arrays.stream(Perms.STAFF).parallel().anyMatch(r.getName()::contains))
                    return false;
                else {
                    try {
                        e.getMessage().delete().queue();
                        MessageManager.sendDiscordPrivateMessage(MoonBot.getInstance().getData().getMessage("NoPermissions").replace("{name}", e.getMember().getEffectiveName()),                               e.getMember().getUser(),
                                Color.RED);
                    }catch (Exception ex){
                    }
                }
                return true;
            }
        }
        return false;
    }

}
