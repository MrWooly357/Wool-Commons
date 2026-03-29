package net.mrwooly357.wool_commons.util.consumer;

import net.mrwooly357.wool_commons.util.consumer.custom.IdentifiedBiConsumer;
import net.mrwooly357.wool_commons.util.consumer.custom.IdentifiedConsumer;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

public final class Consumers {

    private Consumers() throws UnsupportedOperationException {
        throw new UnsupportedOperationException("Can't instantiate Consumers!");
    }


    public static <A> IdentifiedConsumer<A> identified(Consumer<A> consumer, String in) {
        return new IdentifiedConsumer<>(consumer, in);
    }

    public static <A> IdentifiedConsumer<A> identified(Consumer<A> consumer, Class<A> in) {
        return new IdentifiedConsumer<>(consumer, in.getSimpleName());
    }

    public static <A, B> IdentifiedBiConsumer<A, B> identified(BiConsumer<A, B> consumer, String firstIn, String secondIn) {
        return new IdentifiedBiConsumer<>(consumer, firstIn, secondIn);
    }

    public static <A, B> IdentifiedBiConsumer<A, B> identified(BiConsumer<A, B> consumer, Class<A> firstIn, Class<B> secondIn) {
        return new IdentifiedBiConsumer<>(consumer, firstIn.getSimpleName(), secondIn.getSimpleName());
    }
}
