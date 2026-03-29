package net.mrwooly357.wool_commons.util.codec.custom;

import net.mrwooly357.wool_commons.util.codec.Codec;
import net.mrwooly357.wool_commons.util.codec.DataResult;
import net.mrwooly357.wool_commons.util.codec.StringIdentifiable;
import net.mrwooly357.wool_commons.util.codec.decoder.custom.EnumDecoder;
import net.mrwooly357.wool_commons.util.codec.encoder.custom.EnumEncoder;
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
