package net.mrwooly357.wool_commons.util.codec.encoder;

import net.mrwooly357.wool_commons.util.Pair;
import net.mrwooly357.wool_commons.util.codec.DataResult;
import net.mrwooly357.wool_commons.util.codec.operations.CodecOperations;

public record PairEncoder<F, S>(Encoder<F> firstEncoder, Encoder<S> secondEncoder) implements Encoder<Pair<F, S>> {


    @Override
    public <R> DataResult<R> encode(CodecOperations<R> operations, Pair<F, S> input) {
        return operations.encodePair(input, f -> firstEncoder.encode(operations, f), s -> secondEncoder.encode(operations, s));
    }

    @Override
    public String toString() {
        return "PairEncoder[first_encoder: " + firstEncoder
                + ", second_encoder: " + secondEncoder + "]";
    }
}
