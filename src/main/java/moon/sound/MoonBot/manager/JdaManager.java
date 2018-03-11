package moon.sound.MoonBot.manager;

import moon.sound.MoonBot.MoonBot;
import moon.sound.MoonBot.commands.admintration.*;
import moon.sound.MoonBot.commands.user.HelpCMD;
import moon.sound.MoonBot.commands.user.InfoCMD;
import moon.sound.MoonBot.listeners.*;
import moon.sound.MoonBot.utils.CommandHandler;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.OnlineStatus;
import net.dv8tion.jda.core.entities.Game;

import javax.security.auth.login.LoginException;

import static moon.sound.MoonBot.MoonBot.info;

public class JdaManager {
    static JDABuilder jdaBuilder;
    public static JDA jda;

    public void initJdaManager() {
        setSettings();
        addListener();
        addCommands();
        buildBlocking();
    }

    private static void addCommands() {
        CommandHandler.commands.put("opensupport", new OpenSupportCMD());
        CommandHandler.commands.put("closesupport", new CloseSupportCMD());
        CommandHandler.commands.put("stop", new StopCMD());
        CommandHandler.commands.put("help", new HelpCMD());
        CommandHandler.commands.put("say", new SayCMD());
        CommandHandler.commands.put("info", new InfoCMD());
        CommandHandler.commands.put("reload", new ReloadCMD());
        CommandHandler.commands.put("music", new MusicCMD());
    }

    private static void addListener() {
        jdaBuilder.addEventListener(new EventCommandListener());
        jdaBuilder.addEventListener(new AutoRoleListener());
        jdaBuilder.addEventListener(new GuildMemberJoinListener());
        jdaBuilder.addEventListener(new SupportListener());
        jdaBuilder.addEventListener(new UserNameUpdateListener());
        jdaBuilder.addEventListener(new GuildLeavelistener());
        jdaBuilder.addEventListener(new PrivateMessageReceived());
    }

    private static void setSettings() {
        jdaBuilder = new JDABuilder(AccountType.BOT);
        jdaBuilder.setToken(MoonBot.getInstance().getData().getMessage("token"));
        jdaBuilder.setAutoReconnect(true);
        jdaBuilder.setStatus(OnlineStatus.ONLINE);
        jdaBuilder.setGame(Game.of(Game.GameType.DEFAULT, MoonBot.getInstance().getData().getMessage("game")));
    }

    public static JDA getJda() {
        return jda;
    }

    private static void buildBlocking() {
        try {
            jda = jdaBuilder.buildBlocking();
        } catch (LoginException | InterruptedException e) {
            info("The provided token is invalid! Please edit it in the config.yml");
            System.exit(0);
        }
    }
}

