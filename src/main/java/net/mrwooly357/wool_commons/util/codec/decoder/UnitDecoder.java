package net.mrwooly357.wool_commons.util.codec.decoder;

import net.mrwooly357.wool_commons.util.codec.operations.CodecOperations;
import net.mrwooly357.wool_commons.util.codec.DataResult;

public record UnitDecoder<A>(A instance) implements Decoder<A> {


    public DataResult<A> decode() {
        return decode(null, null);
    }

    @Override
    public <R> DataResult<A> decode(CodecOperations<R> operations, R input) {
        return new DataResult.Success<>(instance);
    }

    @Override
    public String toString() {
        return "UnitDecoder[instance: " + instance + "]";
    }
}
