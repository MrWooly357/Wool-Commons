package net.mrwooly357.wool_commons.util.codec.encoder;

import net.mrwooly357.wool_commons.util.codec.DataResult;
import net.mrwooly357.wool_commons.util.codec.operations.CodecOperations;

import java.util.Map;
import java.util.function.Function;

public record DispatchedEncoder<T, A>(Encoder<T> typeEncoder, Function<A, T> typeGetter, Function<T, Encoder<A>> valueEncoder) implements Encoder<A> {


    @Override
    public <R> DataResult<R> encode(CodecOperations<R> operations, A input) {
        return operations.encodeMap(Map.of(typeGetter.apply(input), input), t -> typeEncoder.encode(operations, t), (t, v) -> valueEncoder.apply(t).encode(operations, v));
    }

    @Override
    public String toString() {
        return "DispatchedEncoder[type_encoder: " + typeEncoder
                + ", type_getter: " + typeGetter
                + ", value_encoder: " + valueEncoder + "]";
    }
}
