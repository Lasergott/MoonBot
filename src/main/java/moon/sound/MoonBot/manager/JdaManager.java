package moon.sound.MoonBot.manager;

import moon.sound.MoonBot.MoonBot;
import moon.sound.MoonBot.commands.administration.*;
import moon.sound.MoonBot.commands.user.HelpCommand;
import moon.sound.MoonBot.commands.user.InfoCommand;
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
        CommandHandler.commands.put("opensupport", new OpenSupportCommand());
        CommandHandler.commands.put("closesupport", new CloseSupportCommand());
        CommandHandler.commands.put("stop", new StopCommand());
        CommandHandler.commands.put("help", new HelpCommand());
        CommandHandler.commands.put("say", new SayCommand());
        CommandHandler.commands.put("info", new InfoCommand());
        CommandHandler.commands.put("reload", new ReloadCommand());
        CommandHandler.commands.put("music", new MusicCommand());
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

