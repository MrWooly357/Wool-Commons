package net.mrwooly357.wool_commons;

import net.mrwooly357.wool_commons.util.FilePath;
import net.mrwooly357.wool_commons.util.Logger;

public final class WoolCommons {

    public static final String NAMESPACE = "wool_commons";
    public static final Logger LOGGER;
    private static final FilePath LATEST_LOG = FilePath.RUN.resolve("latest.log");

    WoolCommons() {}

    static {
        FilePath.RUN.createDirectories();
        LATEST_LOG.deleteIfExists();
        LOGGER = createLatestLogLogger(NAMESPACE);
    }

    public static WoolCommons getInstance() {
        return Main.INSTANCE;
    }

    public static Logger createLatestLogLogger(String id) {
        return new Logger(id, LATEST_LOG);
    }
}
