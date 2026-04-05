package net.mrwooly357.wool_commons.util.consumer;

import java.util.function.Consumer;

public record IdentifiedConsumer<A>(Consumer<A> delegate, String in) implements Consumer<A> {


    @Override
    public void accept(A a) {
        delegate.accept(a);
    }

    @Override
    public String toString() {
        return in + " -> void";
    }
}
