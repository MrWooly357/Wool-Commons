package net.mrwooly357.wool_commons.util.runnable;

public record IdentifiedRunnable(Runnable delegate) implements Runnable {


    @Override
    public void run() {
        delegate.run();
    }

    @Override
    public String toString() {
        return "() -> void";
    }
}
