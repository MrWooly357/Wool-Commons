package net.mrwooly357.wool_commons.util.function;

import net.mrwooly357.wool_commons.util.function.custom.IdentifiedBiFunction;
import net.mrwooly357.wool_commons.util.function.custom.IdentifiedFunction;

import java.util.function.BiFunction;
import java.util.function.Function;

public final class Functions {

    private Functions() throws UnsupportedOperationException {
        throw new UnsupportedOperationException("Can't instantiate Functions!");
    }


    public static <A, B> IdentifiedFunction<A, B> identified(Function<A, B> function, String in, String out) {
        return new IdentifiedFunction<>(function, in, out);
    }

    public static <A, B> IdentifiedFunction<A, B> identified(Function<A, B> function, Class<A> in, Class<B> out) {
        return new IdentifiedFunction<>(function, in.getSimpleName(), out.getSimpleName());
    }

    public static <A, B, C> IdentifiedBiFunction<A, B, C> identified(BiFunction<A, B, C> function, String firstIn, String secondIn, String out) {
        return new IdentifiedBiFunction<>(function, firstIn, secondIn, out);
    }

    public static <A, B, C> IdentifiedBiFunction<A, B, C> identified(BiFunction<A, B, C> function, Class<A> firstIn, Class<B> secondIn, Class<C> out) {
        return new IdentifiedBiFunction<>(function, firstIn.getSimpleName(), secondIn.getSimpleName(), out.getSimpleName());
    }
}
