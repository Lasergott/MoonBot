package moon.sound.MoonBot.listeners;

import moon.sound.MoonBot.MoonBot;
import moon.sound.MoonBot.manager.MessageManager;
import net.dv8tion.jda.core.events.guild.member.GuildMemberNickChangeEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.awt.*;

import static moon.sound.MoonBot.MoonBot.info;

public class UserNameUpdateListener extends ListenerAdapter {

    @Override
    public void onGuildMemberNickChange(GuildMemberNickChangeEvent event) {
       info("User " +  event.getMember().getUser()  + " changed his/her displayname to " +  event.getNewNick());
        MessageManager.sendDiscordNormalMessage(event.getGuild(),  MoonBot.getInstance().getData().getMessage("userChangeName-Channel"), Color.YELLOW, "User", "The user " + event.getMember().getUser().getName() + " has changed his nickname to " + event.getNewNick());
    }
}
