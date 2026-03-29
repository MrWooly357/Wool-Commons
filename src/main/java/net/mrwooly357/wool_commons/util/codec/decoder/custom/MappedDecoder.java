package net.mrwooly357.wool_commons.util.codec.decoder.custom;

import net.mrwooly357.wool_commons.util.codec.operations.CodecOperations;
import net.mrwooly357.wool_commons.util.codec.DataResult;
import net.mrwooly357.wool_commons.util.codec.decoder.Decoder;

import java.util.function.Function;

public record MappedDecoder<A, O>(Decoder<A> original, Function<? super A, ? extends O> mapper) implements Decoder<O> {


    @Override
    public <R> DataResult<O> decode(CodecOperations<R> operations, R input) {
        return original.decode(operations, input).map(mapper);
    }

    @Override
    public String toString() {
        return "MappedDecoder[original: " + original
                + ", mapper: " + mapper + "]";
    }
}
