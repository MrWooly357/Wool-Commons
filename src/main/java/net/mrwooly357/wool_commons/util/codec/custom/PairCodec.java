package net.mrwooly357.wool_commons.util.codec.custom;

import net.mrwooly357.wool_commons.util.Pair;
import net.mrwooly357.wool_commons.util.codec.Codec;
import net.mrwooly357.wool_commons.util.codec.DataResult;
import net.mrwooly357.wool_commons.util.codec.decoder.custom.PairDecoder;
import net.mrwooly357.wool_commons.util.codec.encoder.custom.PairEncoder;
import net.mrwooly357.wool_commons.util.codec.operations.CodecOperations;

public record PairCodec<F, S>(PairEncoder<F, S> encoder, PairDecoder<F, S> decoder) implements Codec<Pair<F, S>> {


    @Override
    public <R> DataResult<R> encode(CodecOperations<R> operations, Pair<F, S> input) {
        return encoder.encode(operations, input);
    }

    @Override
    public <R> DataResult<Pair<F, S>> decode(CodecOperations<R> operations, R input) {
        return decoder.decode(operations, input);
    }

    @Override
    public String toString() {
        return "PairCodec[encoder: " + encoder
                + ", decoder: " + decoder + "]";
    }
}
