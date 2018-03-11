package moon.sound.MoonBot.manager;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.PrivateChannel;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.awt.*;

public class MessageManager {

    public static void sendDiscordMessage(MessageReceivedEvent event, String title, String content, Color color) {
        event.getTextChannel().sendMessage(new EmbedBuilder()
                .setColor(color)
                .addField(title, content, false)
                .setThumbnail("https://www.spigotmc.org/data/resource_icons/53/53757.jpg?1519568981")
                .build())
                .queue();
    }

    public static void sendAll(String message, String channel, Color color) {
        for(Guild guild : JdaManager.getJda().getGuilds()) {
            guild.getTextChannelsByName(channel, false).get(0).sendMessage(new EmbedBuilder().setDescription(message).setColor(color).setThumbnail("https://www.spigotmc.org/data/resource_icons/53/53757.jpg?1519568981").build()).queue();
        }
    }
    public static void sendDiscordNormalMessage(Guild guild, String channel, Color color, String title, String content) {
        guild.getTextChannelsByName(channel, false).get(0).sendMessage(new EmbedBuilder()
                .setColor(color)
                .addField(title, content, false)
                .build())
                .queue();
    }

    public static void sendDiscordPrivateMessage(String message, User user, Color color) {
        PrivateChannel privateChannel;
        privateChannel = JdaManager.getJda().getUserById(user.getId()).openPrivateChannel().complete();
        privateChannel.sendMessage(new EmbedBuilder()
                .setDescription(message)
                .setColor(color)
                .setThumbnail("https://www.spigotmc.org/data/resource_icons/53/53757.jpg?1519568981")
                .build())
                .queue();
    }
}
