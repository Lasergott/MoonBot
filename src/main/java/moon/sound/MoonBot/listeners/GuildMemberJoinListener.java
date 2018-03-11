package moon.sound.MoonBot.listeners;

import moon.sound.MoonBot.MoonBot;
import moon.sound.MoonBot.manager.MessageManager;
import net.dv8tion.jda.core.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import java.awt.*;

public class GuildMemberJoinListener extends ListenerAdapter {

    @Override
    public void onGuildMemberJoin(GuildMemberJoinEvent event) {
        try {
            MessageManager.sendDiscordPrivateMessage(
                    MoonBot.getInstance().getData().getMessage("newMemberMessage").replace("{member}", event.getMember().getEffectiveName()),
                    event.getUser(),
                    Color.GREEN);
            MessageManager.sendDiscordNormalMessage(event.getGuild(), MoonBot.getInstance().getData().getMessage("newMemberAnnounce-Channel"), Color.GREEN, "New User", event.getMember().getEffectiveName() + " is new on the Discord Server!");
            MoonBot.info(event.getMember().getEffectiveName() + " is new on the Discord Server! (" + event.getGuild().getName() + ")");
        } catch (Exception e) {
        }
    }
}

