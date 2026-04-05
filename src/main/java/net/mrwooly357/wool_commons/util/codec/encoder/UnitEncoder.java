package net.mrwooly357.wool_commons.util.codec.encoder;

import net.mrwooly357.wool_commons.util.codec.operations.CodecOperations;
import net.mrwooly357.wool_commons.util.codec.DataResult;

public record UnitEncoder<A>() implements Encoder<A> {


    public <R> DataResult<R> encode(CodecOperations<R> operations) {
        return encode(operations, null);
    }

    @Override
    public <R> DataResult<R> encode(CodecOperations<R> operations, A input) {
        return operations.empty();
    }

    @Override
    public String toString() {
        return "UnitEncoder[]";
    }
}
