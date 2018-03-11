package moon.sound.MoonBot;

import moon.sound.MoonBot.storage.Data;
import moon.sound.MoonBot.utils.TimeUtil;
import moon.sound.MoonBot.manager.*;
import moon.sound.MoonBot.utils.ChatColorUtil;
import org.jline.builtins.Completers;
import org.jline.reader.*;
import org.jline.reader.impl.DefaultParser;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;

import java.awt.*;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import static org.jline.builtins.Completers.TreeCompleter.node;

public class MoonBot {

    private static final MoonBot instance = new MoonBot();
    private LineReader reader;
    private boolean waitingForCommand = false;
    private SimpleFormatter simpleFormatter = new SimpleFormatter();
    private boolean running = true;
    private FileManager fileManager;
    private Logger logger;
    private JdaManager jdaManager;
    private ConsoleManager consoleManager;
    private Data data;
    private CommandManager commandManager;

    public static void main(String[] args) {
        getInstance().makeInstances();
        getInstance().setRunning(true);
        getInstance().getConsoleManager().initConsole();
        info("Loading libraries...");
        getInstance().getJdaManager().initJdaManager();
        info("Successful connected to Discord!");
            MessageManager.sendAll(
                    MoonBot.getInstance().getData().getMessage("startUp-Message"),
                    MoonBot.getInstance().getData().getMessage("startUp-Channel"),
                    Color.GREEN);
            try {
                waitForCommands();
            } catch (IOException e) {
                severe("Error while initializing terminal: ");
                e.printStackTrace();
                info("Bot is now complete online!");
            }
    }

    private void makeInstances() {
        getInstance().fileManager = new FileManager();
        getInstance().consoleManager = new ConsoleManager();
        getInstance().jdaManager = new JdaManager();
        getInstance().data = new Data();
        getInstance().commandManager = new CommandManager();
    }

    public static void info(String message) {
        if (getInstance().getReader() == null) {
            System.out.println(TimeUtil.formatTimeConsole() + Data.getPrefix() + message);
            return;
        }
        if (getInstance().isWaitingForCommand()) getInstance().getReader().callWidget(LineReader.CLEAR);
        getInstance().getReader().getTerminal().writer().print(TimeUtil.formatTimeConsole() + Data.getPrefix() + message + "\n");
        if (getInstance().isWaitingForCommand()) getInstance().getReader().callWidget(LineReader.REDRAW_LINE);
        if (getInstance().isWaitingForCommand()) getInstance().getReader().callWidget(LineReader.REDISPLAY);
        getInstance().getReader().getTerminal().writer().flush();
        if (getInstance().getLogger() != null) getInstance().getLogger().info(message);
        Data.addToLog(message);
    }

    public static void severe(String message) {
        if (getInstance().getReader() == null) {
            System.err.println(Data.getPrefix() + message);
            return;
        }
        if (getInstance().isWaitingForCommand()) getInstance().getReader().callWidget(LineReader.CLEAR);
        getInstance().getReader().getTerminal().writer().print(getInstance().getSimpleFormatter().format(new LogRecord(Level.SEVERE, ChatColorUtil.ANSI_RED + message + ChatColorUtil.ANSI_RESET)));
        if (getInstance().isWaitingForCommand()) getInstance().getReader().callWidget(LineReader.REDRAW_LINE);
        if (getInstance().isWaitingForCommand()) getInstance().getReader().callWidget(LineReader.REDISPLAY);
        getInstance().getReader().getTerminal().writer().flush();

      if (getInstance().getLogger() != null) getInstance().getLogger().severe(message);
    }

    private static void waitForCommands() throws IOException {
        TerminalBuilder builder = TerminalBuilder.builder();
        builder.encoding(Charset.defaultCharset());
        builder.system(true);
        Terminal terminal = builder.build();
        Completer completer = new Completers.TreeCompleter(
                node("help"),
                node("info"),
                node("reload"),
                node("exit"),
                node("bot"),
                node("say"),
                node("openSupport"),
                node("closeSupport")
        );
        Parser parser = new DefaultParser();
        String prompt = "> ";
        String rightPrompt = null;
        LineReader reader = LineReaderBuilder.builder()
                .terminal(terminal)
                .completer(completer)
                .parser(parser)
                .build();
        getInstance().reader = reader;
        while (getInstance().running) {
            getInstance().waitingForCommand = true;
            String line = null;
            try {
                line = reader.readLine(prompt, rightPrompt, (MaskingCallback) null, null);
            } catch (UserInterruptException e) {
                System.exit(0);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (line == null) continue;
            getInstance().waitingForCommand = false;
            line = line.trim();
            if (line.isEmpty()) continue;
            getInstance().consoleManager.onCommand(line);
        }
    }

    public static MoonBot getInstance() {
        return instance;
    }

    public Data getData() {
        return data;
    }

    public ConsoleManager getConsoleManager() {
        return consoleManager;
    }

    public JdaManager getJdaManager() {
        return jdaManager;
    }

    public FileManager getFileManager() {
        return fileManager;
    }

    public SimpleFormatter getSimpleFormatter() {
        return simpleFormatter;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public boolean isWaitingForCommand() {
        return waitingForCommand;
    }

    public LineReader getReader() {
        return reader;
    }

    public Logger getLogger() {
        return logger;
    }

    public CommandManager getCommandManager() {
        return commandManager;
    }
}
