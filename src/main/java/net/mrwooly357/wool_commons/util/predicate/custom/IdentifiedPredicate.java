package net.mrwooly357.wool_commons.util.predicate.custom;

import java.util.function.Predicate;

public record IdentifiedPredicate<A>(Predicate<A> delegate, String in) implements Predicate<A> {


    @Override
    public boolean test(A a) {
        return delegate.test(a);
    }

    @Override
    public String toString() {
        return in + " -> boolean";
    }
}
