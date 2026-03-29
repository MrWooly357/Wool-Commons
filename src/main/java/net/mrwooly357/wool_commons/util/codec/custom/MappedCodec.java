package net.mrwooly357.wool_commons.util.codec.custom;

import net.mrwooly357.wool_commons.util.codec.Codec;
import net.mrwooly357.wool_commons.util.codec.operations.CodecOperations;
import net.mrwooly357.wool_commons.util.codec.DataResult;
import net.mrwooly357.wool_commons.util.codec.decoder.custom.MappedDecoder;
import net.mrwooly357.wool_commons.util.codec.encoder.custom.MappedEncoder;

public record MappedCodec<A, O>(MappedEncoder<A, O> encoder, MappedDecoder<A, O> decoder) implements Codec<O> {


    @Override
    public <R> DataResult<R> encode(CodecOperations<R> operations, O input) {
        return encoder.encode(operations, input);
    }

    @Override
    public <R> DataResult<O> decode(CodecOperations<R> operations, R input) {
        return decoder.decode(operations, input);
    }

    @Override
    public String toString() {
        return "MappedCodec[encoder: " + encoder
                + ", decoder: " + decoder + "]";
    }
}
