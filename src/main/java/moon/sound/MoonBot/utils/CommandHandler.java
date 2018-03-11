package moon.sound.MoonBot.utils;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.PrivateChannel;

import java.awt.*;
import java.util.HashMap;

public class CommandHandler {

    public static final CommandParser parse = new CommandParser();
    public static HashMap<String, Command> commands = new HashMap<>();

    public static void handleCommand(CommandParser.commandContainer cmd){
        if(commands.containsKey(cmd.invoke.toLowerCase())){
            commands.get(cmd.invoke.toLowerCase()).action(cmd.args, cmd.e);
            commands.get(cmd.invoke.toLowerCase()).executed(cmd.e);
        }else {
            PrivateChannel privateChannel = cmd.e.getAuthor().openPrivateChannel().complete();
            try {
                cmd.e.getMessage().delete().queue();
            }catch (IllegalStateException ignored){
                privateChannel.sendMessage(new EmbedBuilder()
                        .setDescription(":warning: I cannot do this!")
                        .setColor(Color.RED)
                        .build())
                        .queue();
                return;
            }
            privateChannel.sendMessage(new EmbedBuilder()
                    .setDescription(":warning: This command is not registered!")
                    .setColor(Color.RED)
                    .build())
                    .queue();
        }
    }
}