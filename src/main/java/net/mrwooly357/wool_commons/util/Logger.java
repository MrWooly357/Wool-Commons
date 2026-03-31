package net.mrwooly357.wool_commons.util;

import net.mrwooly357.wool_commons.util.runnable.Runnables;

import java.io.*;
import java.nio.file.StandardOpenOption;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

public class Logger {

    private static final Map<FilePath, BufferedWriter> LOGS = new ConcurrentHashMap<>();
    public static final String INFO_TYPE = "info";
    public static final String WARNING_TYPE = "warning";
    public static final String ERROR_TYPE = "error";
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss");

    private final String id;
    private final boolean sub;
    private final boolean printToConsole;
    private final BufferedWriter log;
    private final Map<String, Logger> subs = new ConcurrentHashMap<>();

    public Logger(String id, FilePath path, boolean printToConsole) {
        this(id, false, printToConsole, LOGS.computeIfAbsent(path, p -> p.createWriter(StandardOpenOption.CREATE, StandardOpenOption.APPEND, StandardOpenOption.SYNC)));
    }

    private Logger(String id, boolean sub, boolean printToConsole, BufferedWriter writer) {
        this.id = id;
        this.sub = sub;
        this.printToConsole = printToConsole;
        this.log = writer;
    }


    static {
        Runtime.getRuntime().addShutdownHook(new Thread(Runnables.identified(() -> LOGS.values().forEach(log -> {
            try {
                log.close();
            } catch (IOException e) {
                throw new RuntimeException("Failed to close log " + log + "!", e);
            }
        }))));
    }

    public void info(String message) {
        log(INFO_TYPE, message);
    }

    public void warning(String message) {
        log(WARNING_TYPE, message);
    }

    public void error(String message) {
        log(ERROR_TYPE, message);
    }

    public void log(String type, String message) {
        try {
            String msg = "[" + LocalTime.now().format(TIME_FORMATTER) + "] [" + id + "/" + type + "] " + message;

            if (printToConsole)
                IO.println(msg);

            log.write(msg);
            log.newLine();
        } catch (IOException e) {
            throw new RuntimeException("Failed to log " + type + " " + message + " " + this + "!", e);
        }
    }

    public void info(String message, Formatting formatting) {
        log(INFO_TYPE, message, formatting);
    }

    public void warning(String message, Formatting formatting) {
        log(WARNING_TYPE, message, formatting);
    }

    public void error(String message, Formatting formatting) {
        log(ERROR_TYPE, message, formatting);
    }

    public void log(String type, String message, Formatting formatting) {
        try {
            String time = "[" + LocalTime.now().format(TIME_FORMATTER) + "]";
            String t = "[" + id + "/" + type + "]";

            if (printToConsole)
                IO.println(formatting.format(time, t, message));

            log.write(time + " " + t + " " + message);
            log.newLine();
        } catch (IOException e) {
            throw new RuntimeException("Failed to log " + type + " " + message + " " + this + "!", e);
        }
    }

    public Logger getSub(String id) {
        return subs.computeIfAbsent(id, s -> new Logger(this.id + ":" + s, true, printToConsole, log));
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, sub, printToConsole, log, subs);
    }

    @Override
    public boolean equals(Object other) {
        return super.equals(other) || (other instanceof Logger logger
                && id.equals(logger.id)
                && sub == logger.sub
                && printToConsole == logger.printToConsole
                && log.equals(logger.log)
                && subs.equals(logger.subs));
    }

    @Override
    public String toString() {
        return "Logger[id: " + id
                + ", sub: " + sub
                + ", print_to_console: " + printToConsole
                + ", log: " + log
                + ", subs: " + subs + "]";
    }


    public record Formatting(String timeFormatting, String typeFormatting, String messageFormatting) {

        public static final String NONE = "";
        public static final String RESET = "\u001B[0m";
        public static final String BOLD = "\u001B[1m";
        public static final String DIM = "\u001B[2m";
        public static final String ITALIC = "\u001B[3m";
        public static final String UNDERLINED = "\u001B[4m";
        public static final String DOUBLE_UNDERLINED = "\u001B[21m";
        public static final String BLINKING = "\u001B[5m";
        public static final String INVERSED = "\u001B[7m";
        public static final String HIDDEN = "\u001B[8m";
        public static final String STRIKETHROUGH = "\u001B[9m";
        public static final String FRAMED = "\u001B[51m";
        public static final String BLACK = "\u001B[30m";
        public static final String RED = "\u001B[31m";
        public static final String GREEN = "\u001B[32m";
        public static final String YELLOW = "\u001B[33m";
        public static final String BLUE = "\u001B[34m";
        public static final String MAGENTA = "\u001B[35m";
        public static final String CYAN = "\u001B[36m";
        public static final String WHITE = "\u001B[37m";
        public static final String BACKGROUND_BLACK = "\u001B[40m";
        public static final String BACKGROUND_RED = "\u001B[41m";
        public static final String BACKGROUND_GREEN = "\u001B[42m";
        public static final String BACKGROUND_YELLOW = "\u001B[43m";
        public static final String BACKGROUND_BLUE = "\u001B[44m";
        public static final String BACKGROUND_MAGENTA = "\u001B[45m";
        public static final String BACKGROUND_CYAN = "\u001B[46m";
        public static final String BACKGROUND_WHITE = "\u001B[47m";
        public static final String BRIGHT_BLACK = "\u001B[90m";
        public static final String BRIGHT_RED = "\u001B[91m";
        public static final String BRIGHT_GREEN = "\u001B[92m";
        public static final String BRIGHT_YELLOW = "\u001B[93m";
        public static final String BRIGHT_BLUE = "\u001B[94m";
        public static final String BRIGHT_MAGENTA = "\u001B[95m";
        public static final String BRIGHT_CYAN = "\u001B[96m";
        public static final String BRIGHT_WHITE = "\u001B[97m";
        public static final String BRIGHT_BACKGROUND_BLACK = "\u001B[100m";
        public static final String BRIGHT_BACKGROUND_RED = "\u001B[101m";
        public static final String BRIGHT_BACKGROUND_GREEN = "\u001B[102m";
        public static final String BRIGHT_BACKGROUND_YELLOW = "\u001B[103m";
        public static final String BRIGHT_BACKGROUND_BLUE = "\u001B[104m";
        public static final String BRIGHT_BACKGROUND_MAGENTA = "\u001B[105m";
        public static final String BRIGHT_BACKGROUND_CYAN = "\u001B[106m";
        public static final String BRIGHT_BACKGROUND_WHITE = "\u001B[107m";


        public static Formatting time(String formatting) {
            return new Formatting(formatting, NONE, NONE);
        }

        public static Formatting type(String formatting) {
            return new Formatting(NONE, formatting, NONE);
        }

        public static Formatting message(String formatting) {
            return new Formatting(NONE, NONE, formatting);
        }

        public static Formatting timeAndType(String timeFormatting, String typeFormatting) {
            return new Formatting(timeFormatting, typeFormatting, NONE);
        }

        public static Formatting timeAndMessage(String timeFormatting, String messageFormatting) {
            return new Formatting(timeFormatting, NONE, messageFormatting);
        }

        public static Formatting typeAndMessage(String typeFormatting, String messageFormatting) {
            return new Formatting(NONE, typeFormatting, messageFormatting);
        }

        private String format(String time, String type, String message) {
            return timeFormatting + time + RESET + " " + typeFormatting + type + RESET + " " + messageFormatting + message + RESET;
        }

        @Override
        public String toString() {
            return "Logger.Formatting[time_formatting: " + timeFormatting
                    + ", type_formatting: " + typeFormatting
                    + "message_formatting: " + messageFormatting + "]";
        }
    }
}
