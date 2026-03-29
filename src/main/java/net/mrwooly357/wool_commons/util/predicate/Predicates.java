package net.mrwooly357.wool_commons.util.predicate;

import net.mrwooly357.wool_commons.util.predicate.custom.IdentifiedBiPredicate;
import net.mrwooly357.wool_commons.util.predicate.custom.IdentifiedPredicate;

import java.util.function.BiPredicate;
import java.util.function.Predicate;

public final class Predicates {

    private Predicates() throws UnsupportedOperationException {
        throw new UnsupportedOperationException("Can't instantiate Predicates!");
    }


    public static <A> IdentifiedPredicate<A> identified(Predicate<A> predicate, String in) {
        return new IdentifiedPredicate<>(predicate, in);
    }

    public static <A> IdentifiedPredicate<A> identified(Predicate<A> predicate, Class<A> in) {
        return new IdentifiedPredicate<>(predicate, in.getSimpleName());
    }

    public static <A, B> IdentifiedBiPredicate<A, B> identified(BiPredicate<A, B> predicate, String firstIn, String secondIn) {
        return new IdentifiedBiPredicate<>(predicate, firstIn, secondIn);
    }

    public static <A, B> IdentifiedBiPredicate<A, B> identified(BiPredicate<A, B> predicate, Class<A> firstIn, Class<B> secondIn) {
        return new IdentifiedBiPredicate<>(predicate, firstIn.getSimpleName(), secondIn.getSimpleName());
    }
}
