package net.mrwooly357.wool_commons;

import net.mrwooly357.wool_commons.util.FilePath;
import net.mrwooly357.wool_commons.util.Logger;

public final class WoolCommons {

    public static final Logger LOGGER;

    WoolCommons() {}


    static {
        FilePath.RUN.createDirectories();
        FilePath log = FilePath.RUN.resolve("wool_commons.log");
        log.deleteIfExists();
        LOGGER = new Logger("wool_commons", log);
    }


    public static WoolCommons getInstance() {
        return Main.INSTANCE;
    }
}
