package net.mrwooly357.wool_commons.util.codec;

import net.mrwooly357.wool_commons.util.codec.decoder.DispatchedDecoder;
import net.mrwooly357.wool_commons.util.codec.encoder.DispatchedEncoder;
import net.mrwooly357.wool_commons.util.codec.operations.CodecOperations;

public record DispatchedCodec<T, A>(DispatchedEncoder<T, A> encoder, DispatchedDecoder<T, A> decoder) implements Codec<A> {


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
        return "DispatchedCodec[encoder: " + encoder
                + ", decoder: " + decoder + "]";
    }
}
