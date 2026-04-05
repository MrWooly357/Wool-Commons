package net.mrwooly357.wool_commons.util.codec;

import net.mrwooly357.wool_commons.util.codec.decoder.EnumDecoder;
import net.mrwooly357.wool_commons.util.codec.encoder.EnumEncoder;
import net.mrwooly357.wool_commons.util.codec.operations.CodecOperations;

public record EnumCodec<E extends Enum<E> & StringIdentifiable>(EnumEncoder<E> encoder, EnumDecoder<E> decoder) implements Codec<E> {


    @Override
    public <R> DataResult<R> encode(CodecOperations<R> operations, E input) {
        return encoder.encode(operations, input);
    }

    @Override
    public <R> DataResult<E> decode(CodecOperations<R> operations, R input) {
        return decoder.decode(operations, input);
    }

    @Override
    public String toString() {
        return "EnumCodec[encoder: " + encoder
                + ", decoder: " + decoder + "]";
    }
}
