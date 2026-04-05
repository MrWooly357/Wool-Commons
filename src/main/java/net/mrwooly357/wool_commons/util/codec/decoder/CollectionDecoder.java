package net.mrwooly357.wool_commons.util.codec.decoder;

import net.mrwooly357.wool_commons.util.codec.DataResult;
import net.mrwooly357.wool_commons.util.codec.operations.CodecOperations;

import java.util.Collection;

public record CollectionDecoder<E>(Decoder<E> valueDecoder) implements Decoder<Collection<E>> {


    @Override
    public <R> DataResult<Collection<E>> decode(CodecOperations<R> operations, R input) {
        return operations.decodeCollection(input, r -> valueDecoder.decode(operations, r));
    }

    @Override
    public String toString() {
        return "CollectionDecoder[value_decoder: " + valueDecoder + "]";
    }
}
