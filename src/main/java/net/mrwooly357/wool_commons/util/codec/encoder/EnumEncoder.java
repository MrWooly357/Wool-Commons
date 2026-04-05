package net.mrwooly357.wool_commons.util.codec.encoder;

import net.mrwooly357.wool_commons.util.codec.DataResult;
import net.mrwooly357.wool_commons.util.codec.StringIdentifiable;
import net.mrwooly357.wool_commons.util.codec.operations.CodecOperations;

public record EnumEncoder<E extends Enum<E> & StringIdentifiable>() implements Encoder<E> {


    @Override
    public <R> DataResult<R> encode(CodecOperations<R> operations, E input) {
        return operations.encodeString(input.asString());
    }

    @Override
    public String toString() {
        return "EnumEncoder[]";
    }
}
