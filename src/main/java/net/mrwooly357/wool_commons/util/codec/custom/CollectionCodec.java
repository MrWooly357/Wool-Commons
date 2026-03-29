package net.mrwooly357.wool_commons.util.codec.custom;

import net.mrwooly357.wool_commons.util.codec.Codec;
import net.mrwooly357.wool_commons.util.codec.DataResult;
import net.mrwooly357.wool_commons.util.codec.decoder.custom.CollectionDecoder;
import net.mrwooly357.wool_commons.util.codec.encoder.custom.CollectionEncoder;
import net.mrwooly357.wool_commons.util.codec.operations.CodecOperations;

import java.util.Collection;

public record CollectionCodec<E>(CollectionEncoder<E> encoder, CollectionDecoder<E> decoder) implements Codec<Collection<E>> {


    @Override
    public <R> DataResult<R> encode(CodecOperations<R> operations, Collection<E> input) {
        return encoder.encode(operations, input);
    }

    @Override
    public <R> DataResult<Collection<E>> decode(CodecOperations<R> operations, R input) {
        return decoder.decode(operations, input);
    }

    @Override
    public String toString() {
        return "CollectionCodec[encoder: " + encoder
                + ", decoder: " + decoder + "]";
    }
}
