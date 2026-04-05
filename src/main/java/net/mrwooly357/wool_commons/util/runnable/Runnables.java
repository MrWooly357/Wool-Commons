package net.mrwooly357.wool_commons.util.runnable;

public final class Runnables {

    private Runnables() throws UnsupportedOperationException {
        throw new UnsupportedOperationException("Can't instantiate Runnables!");
    }


    public static IdentifiedRunnable identified(Runnable runnable) {
        return new IdentifiedRunnable(runnable);
    }
}
