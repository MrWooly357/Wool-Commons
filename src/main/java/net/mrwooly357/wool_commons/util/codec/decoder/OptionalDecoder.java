package net.mrwooly357.wool_commons.util.codec.decoder;

import net.mrwooly357.wool_commons.util.codec.operations.CodecOperations;
import net.mrwooly357.wool_commons.util.codec.DataResult;

import java.util.Optional;

public record OptionalDecoder<A>(Decoder<A> decoder, Optional<A> fallback) implements Decoder<Optional<A>> {


    @Override
    public <R> DataResult<Optional<A>> decode(CodecOperations<R> operations, R input) {
        return operations.isEmpty(input) ? new DataResult.Success<>(fallback) : decoder.decode(operations, input).map(Optional::of);
    }

    @Override
    public String toString() {
        return "OptionalDecoder[decoder: " + decoder
                + ", fallback: " + fallback + "]";
    }
}
