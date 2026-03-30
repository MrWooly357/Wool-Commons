package net.mrwooly357.wool_commons;

import net.mrwooly357.wool_commons.util.FilePath;
import net.mrwooly357.wool_commons.util.Logger;

public final class WoolCommons {

    private static WoolCommons INSTANCE;
    public static final String NAMESPACE = "wool_commons";
    private static Logger LOGGER;
    private static final FilePath LATEST_LOG = FilePath.RUN.resolve("latest.log");

    WoolCommons() {}


    public static void initialise() {
        INSTANCE = new WoolCommons();
        FilePath.RUN.createDirectories();
        LATEST_LOG.deleteIfExists();
        LOGGER = createLatestLogLogger(NAMESPACE);
        LOGGER.info("Wool Commons initialised!");
    }

    public static WoolCommons getInstance() {
        return INSTANCE;
    }

    public static Logger getLogger() {
        return LOGGER;
    }

    public static Logger createLatestLogLogger(String id) {
        return new Logger(id, LATEST_LOG);
    }
}
