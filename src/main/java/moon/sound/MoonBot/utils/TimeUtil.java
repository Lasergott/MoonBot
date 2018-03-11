package moon.sound.MoonBot.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class TimeUtil {

    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd - HH:mm:ss", Locale.GERMAN);
    private static SimpleDateFormat simpleDateFormatConsole = new SimpleDateFormat("[HH:mm:ss] ", Locale.GERMAN);
    private static SimpleDateFormat simpleDateFormatLog = new SimpleDateFormat("yyyy-MM-dd", Locale.GERMAN);

    public static String formatTime() {
        return simpleDateFormat.format(new Date());
    }
    public static String formatTimeLog() {
        return simpleDateFormatLog.format(new Date());
    }
    public static String formatTimeConsole() {
        return simpleDateFormatConsole.format(new Date());
    }
}
