package moon.sound.MoonBot.manager;

import moon.sound.MoonBot.MoonBot;
import moon.sound.MoonBot.utils.ChatColorUtil;
import net.dv8tion.jda.core.entities.Guild;

import java.awt.*;
import java.util.Arrays;
import java.util.function.Consumer;
import static moon.sound.MoonBot.MoonBot.info;

public class ConsoleManager {


    public void initConsole() {
        sendStartScreen();

    }


    public static void sendStartScreen() {
        System.out.println("\n" +
                "  __  __                   ____        _   \n" +
                " |  \\/  |                 |  _ \\      | |  \n" +
                " | \\  / | ___   ___  _ __ | |_) | ___ | |_ \n" +
                " | |\\/| |/ _ \\ / _ \\| '_ \\|  _ < / _ \\| __|\n" +
                " | |  | | (_) | (_) | | | | |_) | (_) | |_ \n" +
                " |_|  |_|\\___/ \\___/|_| |_|____/ \\___/ \\__|\n");
        System.out.println("MoonBot (Version 1.0) by Sebastian - Lasergott");
        System.out.println("");
        info("Type 'help' for all available commands!");
    }

    private void sendHelp(Consumer<String> sendMessage) {
        info("All available commands:");
        info("  help             -    shows the help page");
        info("  info             -    information's about the bot");
        info("  reload           -    reload all configurations");
        info("  openSupport      -    open the 'Support' channel");
        info("  closeSupport     -    close the 'Support' channel");
        info("  exit             -    terminate this process");
        info("  say              -    Use say <Guild> <Channel> <Message>");
    }

    private void send(String message) {
        info(message);
    }

    private void sendError(String message) {
        MoonBot.severe(message);
    }

    private void sendError(Consumer<String> sendMessage, String message) {
        sendMessage.accept("&c" + message);
    }

    private void sendError(Consumer<String> sendMessage, boolean local, String message) {
        if (local) sendError(sendMessage, message);
        else sendError(sendMessage, message);
    }

    public void onCommand(String command) {
        onCommand((str) -> send(ChatColorUtil.toLegacyText(str + "&r")), true, command);
    }

    public void onCommand(Consumer<String> sendMessage, boolean local, String command) {
        String[] split = command.split(" ");
        if (split.length < 1) return;
        String cmd = split[0];
        String[] args = split.length == 1 ? new String[0] : Arrays.copyOfRange(split, 1, split.length);
        onCommand(sendMessage, local, cmd, args);
    }

    public void onCommand(Consumer<String> sendMessage, boolean local, String command, String... args) {
        try {
            if (command.equalsIgnoreCase("reload")) {
                MoonBot.getInstance().getFileManager().load();
                sendMessage.accept("&2Successfully reloaded from configuration!");
                MessageManager.sendAll(MoonBot.getInstance().getData().getMessage("reload-Message").replace("{member}", "CONSOLE"), MoonBot.getInstance().getData().getMessage("reload-Channel"), Color.GREEN);
                return;
            }
            if (command.equalsIgnoreCase("info")) {
                sendMessage.accept("&9&lAuthor: &2Lasergott | Sebastian");
                sendMessage.accept("&9Version: &2" + MoonBot.class.getPackage().getImplementationVersion());
                sendMessage.accept("&9Guild using this bot: &2" + MoonBot.getInstance().getCommandManager().getGuilds());
                sendMessage.accept("&9Guild names using this bot: &2" + MoonBot.getInstance().getCommandManager().getGuildsName());
                sendMessage.accept("&9Our Homepage: &2https://timo.cloud");
                return;
            }
            if (command.equalsIgnoreCase("say")) {
                StringBuilder sb = new StringBuilder();
                for (int i = 2; i < args.length; i++) {
                    sb.append(args[i]).append(" ");
                }
                String allArgs = sb.toString().trim();
                try {
                    Guild guild = JdaManager.getJda().getGuildsByName(args[0], false).get(0);
                    String channel = args[1];
                    MessageManager.sendDiscordNormalMessage(guild, channel, Color.GREEN, "Alert", allArgs);
                    info("Successful send a message to " + guild.getName() + " (" + channel + ", Message: " + allArgs + ")");
                } catch (Exception e) {
                    info("Please use: say <Guild> <Channel> <Message>");
                }
                return;
            }
            if (command.equalsIgnoreCase("help")) {
                sendHelp(sendMessage);
                return;
            }
            if (command.equalsIgnoreCase("exit")) {
                MoonBot.getInstance().getCommandManager().stopBot();
                return;
            }
            if (command.equalsIgnoreCase("closesupport")) {
                try {
                    Guild guild = JdaManager.getJda().getGuildsByName(args[0], false).get(0);
                    MoonBot.getInstance().getCommandManager().closeSupport(guild, "CONSOLE");
                } catch (Exception e) {
                    info("Please use: closeSupport <Guild>");
                }
                return;
            }
            if (command.equalsIgnoreCase("opensupport")) {
                try {
                    Guild guild = JdaManager.getJda().getGuildsByName(args[0], false).get(0);
                    MoonBot.getInstance().getCommandManager().openSupport(guild,"CONSOLE");
                } catch (Exception e) {
                    info("Please use: openSupport <Guild>");
                }
                return;
            }
            } catch(Exception e){
                sendError(sendMessage, local, "Wrong usage.");
                sendHelp(sendMessage);
                e.printStackTrace();
                return;
            }
            sendError(sendMessage, local, "Unknown command: " + command);
            sendHelp(sendMessage);
        }
    }
