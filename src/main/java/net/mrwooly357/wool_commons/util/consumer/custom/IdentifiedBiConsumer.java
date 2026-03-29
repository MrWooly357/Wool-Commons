package net.mrwooly357.wool_commons.util.consumer.custom;

import java.util.function.BiConsumer;

public record IdentifiedBiConsumer<A, B>(BiConsumer<A, B> delegate, String firstIn, String secondIn) implements BiConsumer<A, B> {


    @Override
    public void accept(A a, B b) {
        delegate.accept(a, b);
    }

    @Override
    public String toString() {
        return "(" + firstIn + ", " + secondIn + ") -> void";
    }
}
