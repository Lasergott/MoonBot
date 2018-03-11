package moon.sound.MoonBot.listeners;
import moon.sound.MoonBot.MoonBot;
import net.dv8tion.jda.core.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class AutoRoleListener extends ListenerAdapter {

    @Override
    public void onGuildMemberJoin(GuildMemberJoinEvent event) {
        if (event.getMember().getRoles().contains(event.getGuild().getJDA().getRolesByName(MoonBot.getInstance().getData().getMessage("autoRole"), false).get(0)))
            return;
        event.getGuild().getController().addSingleRoleToMember(event.getMember(), event.getGuild().getRolesByName(MoonBot.getInstance().getData().getMessage("autoRole"), false).get(0)).queue();
    }
}
