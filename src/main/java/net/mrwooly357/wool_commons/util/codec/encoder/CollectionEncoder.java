package net.mrwooly357.wool_commons.util.codec.encoder;

import net.mrwooly357.wool_commons.util.codec.DataResult;
import net.mrwooly357.wool_commons.util.codec.operations.CodecOperations;

import java.util.Collection;

public record CollectionEncoder<E>(Encoder<E> valueEncoder) implements Encoder<Collection<E>> {


    @Override
    public <R> DataResult<R> encode(CodecOperations<R> operations, Collection<E> input) {
        return operations.encodeCollection(input, e -> valueEncoder.encode(operations, e));
    }

    @Override
    public String toString() {
        return "CollectionEncoder[value_encoder: " + valueEncoder + "]";
    }
}
