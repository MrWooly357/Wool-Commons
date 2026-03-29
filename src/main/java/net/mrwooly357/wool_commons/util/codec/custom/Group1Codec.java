package net.mrwooly357.wool_commons.util.codec.custom;

import net.mrwooly357.wool_commons.util.codec.*;
import net.mrwooly357.wool_commons.util.codec.operations.CodecOperations;

import java.util.*;
import java.util.function.Function;

public record Group1Codec<A, O>(FieldCodec<A, O> field, Function<? super A, ? extends O> constructor) implements Codec<O> {


    @Override
    public <R> DataResult<R> encode(CodecOperations<R> operations, O input) {
        Fields<R> fields = operations.usesStringFieldIds() ? new Fields.MapLike<>(new LinkedHashMap<>()) : new Fields.ListLike<>(new ArrayList<>());
        field.encode(operations, input, fields);

        return fields.encode(operations);
    }

    @Override
    public <R> DataResult<O> decode(CodecOperations<R> operations, R input) {
        Fields<R> fields;

        if (operations.usesStringFieldIds()) {
            DataResult<Map<String, R>> map = operations.decodeMap(input, operations::decodeString, DataResult.Success::new);

            if (map.isSuccess())
                fields = new Fields.MapLike<>(map.getOrThrow());
            else
                return new DataResult.Error<>("Failed to decode " + input + "!", new IllegalArgumentException());
        } else {
            DataResult<Collection<R>> list = operations.decodeCollection(input, DataResult.Success::new);

            if (list.isSuccess())
                fields = new Fields.ListLike<>((List<R>) list.getOrThrow());
            else
                return new DataResult.Error<>("Failed to decode " + input + "!", new IllegalArgumentException());
        }

        return field.decode(operations, fields)
                .map(constructor);
    }

    @Override
    public String toString() {
        return "Group1Codec[field: " + field
                + ", constructor: " + constructor + "]";
    }
}
