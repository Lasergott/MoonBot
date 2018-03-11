package moon.sound.MoonBot.manager;

import moon.sound.MoonBot.MoonBot;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.VoiceChannel;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;

import static moon.sound.MoonBot.MoonBot.info;

public class CommandManager {

    public void stopBot() {
        MessageManager.sendAll(MoonBot.getInstance().getData().getMessage("exit-Message"), MoonBot.getInstance().getData().getMessage("exit-Channel"), Color.RED);
        System.exit(0);
    }

    public void sendHelp(MessageReceivedEvent event) {
        event.getGuild().getTextChannelsByName(event.getChannel().getName()
                , false).get(0).
                sendMessage(new EmbedBuilder().setDescription("Hey, welcome on the **help-page** from the 'Discord Bot'!\n").
                        addField("Available Commands:", ".help  - Show this page\n+openSupport  - Open the Support channel\n.closeSupport - Close the support\n.exit        - Stop the bot\n.info  - Some information's about the MoonBot\n.reload  - Reloads all configuration's\n.say -   Use say <Guild> <Channel> <Message>", false).setThumbnail("https://www.spigotmc.org/data/resource_icons/53/53757.jpg?1519568981").setColor(Color.GREEN).build()).queue();
    }

    public void sendInfo(MessageReceivedEvent event) {
        event.getGuild().getTextChannelsByName(event.getChannel().getName()
                , false).get(0).
                sendMessage(new EmbedBuilder().setTitle("Informations about MoonBot:").
                        addField("**Author:**", "Lasergott | Sebastian",true).
                        addField("**Version:**",  MoonBot.class.getPackage().getImplementationVersion(), true).
                        addField("**Guild using this bot:**", MoonBot.getInstance().getCommandManager().getGuilds() + "", true).
                        addField("**Our Homepage**", "https://timo.cloud", true)
                        .setColor(Color.BLUE).setThumbnail("https://www.spigotmc.org/data/resource_icons/53/53757.jpg?1519568981").build()).queue();
    }

    public void closeSupport(Guild guild, String author) {
            VoiceChannel voiceChannel = guild.getVoiceChannelsByName("Support-Waitroom", true).get(0);
            Permission permission = Permission.VOICE_CONNECT;
            Collection<Permission> perms = new ArrayList<>();
            perms.add(permission);
            voiceChannel.putPermissionOverride(guild.getRolesByName("User", false).get(0)).setDeny(perms).queue();
            info("CONSOLE has close the support channel!");
            MessageManager.sendDiscordNormalMessage(guild, MoonBot.getInstance().getData().getMessage("closeSupport-Channel"), Color.GREEN, "Support", "The Support channel has been closed by " + author);
    }

    public void openSupport(Guild guild, String author) {
            VoiceChannel voiceChannel = guild.getVoiceChannelsByName("Support-Waitroom", true).get(0);
            Permission permission = Permission.VOICE_CONNECT;
            Collection<Permission> perms = new ArrayList<>();
            perms.add(permission);
            voiceChannel.putPermissionOverride(guild.getRolesByName("User", false).get(0)).setAllow(perms).queue();
            info("CONSOLE has open the support channel!");
            MessageManager.sendDiscordNormalMessage(guild, MoonBot.getInstance().getData().getMessage("openSupport-Channel"), Color.GREEN, "Support", "The Support channel has been opened by " + author);
        }
    public String getGuildsName() {
        String out = "";
        for (Guild g : JdaManager.getJda().getGuilds()) {
            out += g.getName() + ", ";
        }
        return out;
    }

    public int getGuilds() {
        return JdaManager.getJda().getGuilds().size();
    }
}
