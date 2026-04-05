package net.mrwooly357.wool_commons.util.codec;

import net.mrwooly357.wool_commons.util.codec.operations.CodecOperations;
import net.mrwooly357.wool_commons.util.function.QuadFunction;

import java.util.*;

public record Group4Codec<A1, A2, A3, A4, O>(
        FieldCodec<A1, O> field1,
        FieldCodec<A2, O> field2,
        FieldCodec<A3, O> field3,
        FieldCodec<A4, O> field4,
        QuadFunction<? super A1, ? super A2, ? super A3, ? super A4, ? extends O> constructor
) implements Codec<O> {


    @Override
    public <R> DataResult<R> encode(CodecOperations<R> operations, O input) {
        Fields<R> fields = operations.usesStringFieldIds() ? new Fields.MapLike<>(new LinkedHashMap<>()) : new Fields.ListLike<>(new ArrayList<>());
        field1.encode(operations, input, fields);
        field2.encode(operations, input, fields);
        field3.encode(operations, input, fields);
        field4.encode(operations, input, fields);

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

        DataResult<A1> f1 = field1.decode(operations, fields);
        DataResult<A2> f2 = field2.decode(operations, fields);
        DataResult<A3> f3 = field3.decode(operations, fields);
        DataResult<A4> f4 = field4.decode(operations, fields);

        return f1.isSuccess() && f2.isSuccess() && f3.isSuccess()
                ? new DataResult.Success<>(constructor.apply(f1.getOrThrow(), f2.getOrThrow(), f3.getOrThrow(), f4.getOrThrow()))
                : new DataResult.Error<>("Failed to decode " + input + "!", new IllegalArgumentException());
    }

    @Override
    public String toString() {
        return "Group4Codec[field_1: " + field1
                + ", field_2: " + field2
                + ", field_3: " + field3
                + ", field_4: " + field4
                + ", constructor: " + constructor + "]";
    }
}
