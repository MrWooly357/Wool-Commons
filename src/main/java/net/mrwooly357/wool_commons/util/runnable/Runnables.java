package net.mrwooly357.wool_commons.util.runnable;

import net.mrwooly357.wool_commons.util.runnable.custom.IdentifiedRunnable;

public final class Runnables {

    private Runnables() throws UnsupportedOperationException {
        throw new UnsupportedOperationException("Can't instantiate Runnables!");
    }


    public static IdentifiedRunnable identified(Runnable runnable) {
        return new IdentifiedRunnable(runnable);
    }
}
