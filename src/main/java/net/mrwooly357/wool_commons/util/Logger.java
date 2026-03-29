package net.mrwooly357.wool_commons.util;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.StandardOpenOption;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

public class Logger {

    public static final String INFO_TYPE = "info";
    public static final String WARNING_TYPE = "warning";
    public static final String ERROR_TYPE = "error";
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss");

    private final String id;
    private final boolean sub;
    private final BufferedWriter log;
    private final Map<String, Logger> subs = new ConcurrentHashMap<>();

    public Logger(String id, FilePath path) {
        this(id, false, path.writer(StandardOpenOption.CREATE, StandardOpenOption.APPEND, StandardOpenOption.SYNC));

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            if (!sub)
                try {
                    log.close();
                } catch (IOException e) {
                    throw new RuntimeException("Failed to close logger " + this + "!", e);
                }
        }));
    }

    private Logger(String id, boolean sub, BufferedWriter writer) {
        this.id = id;
        this.sub = sub;
        this.log = writer;
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
            log.write("[" + LocalTime.now().format(TIME_FORMATTER) + "] [" + id + "/" + type + "] " + message);
            log.newLine();
        } catch (IOException e) {
            throw new RuntimeException("Failed to log " + type + " " + message + " " + this + "!", e);
        }
    }

    public Logger sub(String id) {
        return subs.computeIfAbsent(id, s -> new Logger(this.id + ":" + s, true, log));
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, sub, log, subs);
    }

    @Override
    public boolean equals(Object other) {
        return super.equals(other) || (other instanceof Logger logger
                && id.equals(logger.id)
                && sub == logger.sub
                && log.equals(logger.log)
                && subs.equals(logger.subs));
    }

    @Override
    public String toString() {
        return "Logger[id: " + id
                + ", sub: " + sub
                + ", log: " + log
                + ", subs: " + subs + "]";
    }
}
