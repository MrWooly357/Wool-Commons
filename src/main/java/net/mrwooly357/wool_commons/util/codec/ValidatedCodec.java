package net.mrwooly357.wool_commons.util.codec;

import net.mrwooly357.wool_commons.util.codec.operations.CodecOperations;
import net.mrwooly357.wool_commons.util.codec.decoder.ValidatedDecoder;
import net.mrwooly357.wool_commons.util.codec.encoder.ValidatedEncoder;

public record ValidatedCodec<A>(ValidatedEncoder<A> encoder, ValidatedDecoder<A> decoder) implements Codec<A> {


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
        return "ValidatedCodec[encoder: " + encoder
                + ", decoder: " + decoder + "]";
    }
}
