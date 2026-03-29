package net.mrwooly357.wool_commons.util.function.custom;

import java.util.function.Function;

public record IdentifiedFunction<A, B>(Function<A, B> delegate, String in, String out) implements Function<A, B> {


    @Override
    public B apply(A a) {
        return delegate.apply(a);
    }

    @Override
    public String toString() {
        return in + " -> " + out;
    }
}
