package net.mrwooly357.wool_commons.util.function.custom;

import java.util.function.BiFunction;

public record IdentifiedBiFunction<A, B, C>(BiFunction<A, B, C> delegate, String firstIn, String secondIn, String out) implements BiFunction<A, B, C> {


    @Override
    public C apply(A a, B b) {
        return delegate.apply(a, b);
    }

    @Override
    public String toString() {
        return "(" + firstIn + ", " + secondIn + ") -> " + out;
    }
}
