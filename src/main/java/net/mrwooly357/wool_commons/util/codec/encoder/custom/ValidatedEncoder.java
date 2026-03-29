package net.mrwooly357.wool_commons.util.codec.encoder.custom;

import net.mrwooly357.wool_commons.util.codec.operations.CodecOperations;
import net.mrwooly357.wool_commons.util.codec.DataResult;
import net.mrwooly357.wool_commons.util.codec.encoder.Encoder;

import java.util.function.Predicate;
import java.util.function.Supplier;

public record ValidatedEncoder<A>(Encoder<A> encoder, Predicate<? super A> predicate, Supplier<String> message) implements Encoder<A> {


    @Override
    public <R> DataResult<R> encode(CodecOperations<R> operations, A input) {
        return predicate.test(input) ? encoder.encode(operations, input) : new DataResult.Error<>(message.get(), new IllegalArgumentException());
    }

    @Override
    public String toString() {
        return "ValidatedEncoder[encoder: " + encoder
                + ", predicate: " + predicate
                + ", message: " + message + "]";
    }
}
