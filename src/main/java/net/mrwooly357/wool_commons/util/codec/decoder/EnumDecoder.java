package net.mrwooly357.wool_commons.util.codec.decoder;

import net.mrwooly357.wool_commons.util.codec.DataResult;
import net.mrwooly357.wool_commons.util.codec.StringIdentifiable;
import net.mrwooly357.wool_commons.util.codec.operations.CodecOperations;

import java.util.function.Function;

public record EnumDecoder<E extends Enum<E> & StringIdentifiable>(Function<String, E> mapper) implements Decoder<E> {


    @Override
    public <R> DataResult<E> decode(CodecOperations<R> operations, R input) {
        return operations.decodeString(input).map(mapper);
    }

    @Override
    public String toString() {
        return "EnumDecoder[mapper: " + mapper + "]";
    }
}
