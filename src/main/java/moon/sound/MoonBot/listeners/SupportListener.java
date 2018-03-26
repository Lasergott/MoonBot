package moon.sound.MoonBot.listeners;

import moon.sound.MoonBot.MoonBot;
import moon.sound.MoonBot.manager.MessageManager;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.PrivateChannel;
import net.dv8tion.jda.core.events.guild.voice.GuildVoiceJoinEvent;
import net.dv8tion.jda.core.events.guild.voice.GuildVoiceMoveEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.awt.*;

import static moon.sound.MoonBot.MoonBot.info;

public class SupportListener extends ListenerAdapter {

    @Override
    public void onGuildVoiceJoin(GuildVoiceJoinEvent event) {
        Member member = event.getMember();
        if (event.getChannelJoined().getName().equals("Support-Waitroom \uD83D\uDD5B")) {
            int staffs = 0;
            info("Member " + member.getEffectiveName() + " is waiting in the support-channel (" + event.getChannelJoined().getName() + ")!");
            for (Member staff : event.getGuild().getMembers()) {
                try {
                    if (staff.getRoles().contains(event.getGuild().getRolesByName("Supporter", false).get(0))) {
                        staffs++;
                        sendWaiting(staff, event);
                    } else if (staff.getRoles().contains(event.getGuild().getRolesByName("Leiter", false).get(0))) {
                        staffs++;
                        sendWaiting(staff, event);
                    } else if (staff.getRoles().contains(event.getGuild().getRolesByName("Administrator", false).get(0))) {
                        staffs++;
                        sendWaiting(staff, event);
                    }
                } catch (Exception ignored) {
                }
            }
            try {
                MessageManager.sendDiscordPrivateMessage(
                        MoonBot.getInstance().getData().getMessage("teamInformed").replace("{staff}", staffs + ""),
                        member.getUser(),
                        Color.GREEN);
            } catch (Exception ignored) {
            }
        }
    }

    @Override
    public void onGuildVoiceMove(GuildVoiceMoveEvent event) {
        Member member = event.getMember();
        if (event.getChannelJoined().getName().equals("Support-Waitroom \uD83D\uDD5B")) {
            int staffs = 0;
            info("Member " + member.getEffectiveName() + " is waiting in the support-channel (" + event.getChannelJoined().getName() + ")!");
            for (Member staff : event.getGuild().getMembers()) {
                try {
                    if (staff.getRoles().contains(event.getGuild().getRolesByName("Supporter", false).get(0))) {
                        staffs++;
                        sendWaiting(staff, event);
                    } else if (staff.getRoles().contains(event.getGuild().getRolesByName("Leiter", false).get(0))) {
                        staffs++;
                        sendWaiting(staff, event);
                    } else if (staff.getRoles().contains(event.getGuild().getRolesByName("Administrator", false).get(0))) {
                        staffs++;
                        sendWaiting(staff, event);
                    }
                } catch (Exception ignored) {
                }
            }
            try {
                MessageManager.sendDiscordPrivateMessage(
                        MoonBot.getInstance().getData().getMessage("teamInformed").replace("{staff}", staffs + ""),
                        member.getUser(),
                        Color.GREEN);
            } catch (Exception ignored) {
            }
        }
    }

    private void sendWaiting(Member member, GuildVoiceMoveEvent event) {
        MessageManager.sendDiscordPrivateMessage(
                MoonBot.getInstance().getData().getMessage("memberIsWaiting").replace("{member}", event.getMember().getEffectiveName()).replace("{channel}", event.getChannelJoined().getName()), member.getUser(), Color.GREEN);
    }

    private void sendWaiting(Member member, GuildVoiceJoinEvent event) {
        MessageManager.sendDiscordPrivateMessage(
                MoonBot.getInstance().getData().getMessage("memberIsWaiting").replace("{member}", event.getMember().getEffectiveName()).replace("{channel}", event.getChannelJoined().getName()), member.getUser(), Color.GREEN);
    }
}

