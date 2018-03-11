package moon.sound.MoonBot.listeners;

import moon.sound.MoonBot.MoonBot;
import moon.sound.MoonBot.manager.MessageManager;
import net.dv8tion.jda.core.events.guild.member.GuildMemberLeaveEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import java.awt.*;

public class GuildLeavelistener extends ListenerAdapter{

    @Override
    public void onGuildMemberLeave(GuildMemberLeaveEvent event) {
        MessageManager.sendDiscordNormalMessage(event.getGuild(), MoonBot.getInstance().getData().getMessage("memberLeave-Channel"),  Color.red, "User", event.getMember().getEffectiveName() + " has left the discord!");
        MoonBot.info(event.getMember().getEffectiveName() + " has left the Discord Server! (" + event.getGuild().getName() + ")");
        MessageManager.sendDiscordPrivateMessage(MoonBot.getInstance().getData().getMessage("memberLeave-Private"), event.getUser(), Color.RED);
    }
}
