package net.mrwooly357.wool_commons.util.codec.encoder.custom;

import net.mrwooly357.wool_commons.util.codec.operations.CodecOperations;
import net.mrwooly357.wool_commons.util.codec.DataResult;
import net.mrwooly357.wool_commons.util.codec.encoder.Encoder;

import java.util.function.Function;

public record MappedEncoder<A, O>(Encoder<A> original, Function<? super O, ? extends A> mapper) implements Encoder<O> {


    @Override
    public <R> DataResult<R> encode(CodecOperations<R> operations, O input) {
        return original.encode(operations, mapper.apply(input));
    }

    @Override
    public String toString() {
        return "MappedEncoder[original: " + original
                + ", mapper: " + mapper + "]";
    }
}
