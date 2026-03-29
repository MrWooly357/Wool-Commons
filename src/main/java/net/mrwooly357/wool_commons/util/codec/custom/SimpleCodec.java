package net.mrwooly357.wool_commons.util.codec.custom;

import net.mrwooly357.wool_commons.util.codec.Codec;
import net.mrwooly357.wool_commons.util.codec.DataResult;
import net.mrwooly357.wool_commons.util.codec.decoder.Decoder;
import net.mrwooly357.wool_commons.util.codec.encoder.Encoder;
import net.mrwooly357.wool_commons.util.codec.operations.CodecOperations;

public record SimpleCodec<A>(Encoder<A> encoder, Decoder<A> decoder) implements Codec<A> {


    @Override
    public <R> DataResult<R> encode(CodecOperations<R> operations, A input) {
        return encoder.encode(operations, input);
    }

    @Override
    public <R> DataResult<A> decode(CodecOperations<R> operations, R input) {
        return decoder.decode(operations, input);
    }

    @Override
    public String toString() {
        return "SimpleCodec[encoder: " + encoder
                + ", decoder: " + decoder + "]";
    }
}
