package net.mrwooly357.wool_commons.util.predicate.custom;

import java.util.function.BiPredicate;

public record IdentifiedBiPredicate<A, B>(BiPredicate<A, B> delegate, String firstIn, String secondIn) implements BiPredicate<A, B> {


    @Override
    public boolean test(A a, B b) {
        return delegate().test(a, b);
    }

    @Override
    public String toString() {
        return "(" + firstIn + ", " + secondIn + ") -> boolean";
    }
}
