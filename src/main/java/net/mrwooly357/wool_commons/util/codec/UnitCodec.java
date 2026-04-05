package net.mrwooly357.wool_commons.util.codec;

import net.mrwooly357.wool_commons.util.codec.operations.CodecOperations;
import net.mrwooly357.wool_commons.util.codec.decoder.UnitDecoder;
import net.mrwooly357.wool_commons.util.codec.encoder.UnitEncoder;

public record UnitCodec<A>(UnitEncoder<A> encoder, UnitDecoder<A> decoder) implements Codec<A> {


    @Override
    public <R> DataResult<R> encode(CodecOperations<R> operations, A input) {
        return encoder.encode(operations);
    }

    @Override
    public <R> DataResult<A> decode(CodecOperations<R> operations, R input) {
        return decoder.decode();
    }

    @Override
    public String toString() {
        return "UnitCodec[encoder: " + encoder
                + ", decoder: " + decoder + "]";
    }
}
