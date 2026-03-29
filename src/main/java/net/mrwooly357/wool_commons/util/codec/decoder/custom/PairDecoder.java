package net.mrwooly357.wool_commons.util.codec.decoder.custom;

import net.mrwooly357.wool_commons.util.Pair;
import net.mrwooly357.wool_commons.util.codec.DataResult;
import net.mrwooly357.wool_commons.util.codec.decoder.Decoder;
import net.mrwooly357.wool_commons.util.codec.operations.CodecOperations;

public record PairDecoder<F, S>(Decoder<F> fisrtDecoder, Decoder<S> secondDecoder) implements Decoder<Pair<F, S>> {


    @Override
    public <R> DataResult<Pair<F, S>> decode(CodecOperations<R> operations, R input) {
        return operations.decodePair(input, r -> fisrtDecoder.decode(operations, r), r -> secondDecoder.decode(operations, r));
    }

    @Override
    public String toString() {
        return "PairDecoder[first_decoder: " + fisrtDecoder
                + ", second_decoder: " + secondDecoder + "]";
    }
}
