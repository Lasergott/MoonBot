package moon.sound.MoonBot.storage;

import moon.sound.MoonBot.MoonBot;
import moon.sound.MoonBot.utils.TimeUtil;
import moon.sound.MoonBot.utils.ChatColorUtil;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

public class Data {

    public static final String prefix = ChatColorUtil.ANSI_YELLOW + "[" + ChatColorUtil.ANSI_BLUE + "Moon" + ChatColorUtil.ANSI_RESET + "Bot" + ChatColorUtil.ANSI_YELLOW + "]" + ChatColorUtil.ANSI_RESET;

    public static void addToLog(String message) {
        try {
            File logsDirectory;
            logsDirectory = new File("logs/");
            logsDirectory.mkdirs();
            File logFile = new File("logs/" + TimeUtil.formatTimeLog() + ".log");
            BufferedWriter bw = new BufferedWriter(new FileWriter(logFile, true));
            if (!logFile.exists())
                logFile.createNewFile();
            bw.write(String.format("[%s]" + " [MoonBot] " + "%s\n",
                    TimeUtil.formatTime(),
                    message));
            bw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getMessage(String message) {
        try {
            if (MoonBot.getInstance().getFileManager().getConfig().get(message) == null) {
                MoonBot.info("Please add an valid message '" + message + "' in the config.yml");
                MoonBot.getInstance().getCommandManager().stopBot();
                return null;
            } else {
                return MoonBot.getInstance().getFileManager().getConfig().get(message).toString();
            }
        } catch (Exception e) {
            return null;
        }
    }

    public static final String getPrefix() {
        return prefix + " ";
    }
}

