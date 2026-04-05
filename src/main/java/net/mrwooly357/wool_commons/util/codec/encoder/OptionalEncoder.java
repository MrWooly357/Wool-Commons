package net.mrwooly357.wool_commons.util.codec.encoder;

import net.mrwooly357.wool_commons.util.codec.operations.CodecOperations;
import net.mrwooly357.wool_commons.util.codec.DataResult;

import java.util.Optional;

public record OptionalEncoder<A>(Encoder<A> encoder) implements Encoder<Optional<A>> {


    @Override
    public <R> DataResult<R> encode(CodecOperations<R> operations, Optional<A> input) {
        return input.map(a -> encoder.encode(operations, a)).orElse(operations.empty());
    }

    @Override
    public String toString() {
        return "OptionalEncoder[encoder: " + encoder + "]";
    }
}
