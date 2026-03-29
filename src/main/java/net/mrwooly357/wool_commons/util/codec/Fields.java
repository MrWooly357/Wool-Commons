package net.mrwooly357.wool_commons.util.codec;

import net.mrwooly357.wool_commons.util.codec.operations.CodecOperations;

import java.util.List;
import java.util.Map;

public sealed interface Fields<R> {


    void put(String id, R field);

    void put(R field);

    R get(String id);

    R get(int index);

    DataResult<R> encode(CodecOperations<R> operations);


    record MapLike<R>(Map<String, R> fields) implements Fields<R> {


        @Override
        public void put(String id, R field) {
            fields.put(id, field);
        }

        @Override
        public void put(R field) {
            throw new UnsupportedOperationException("Fields.MapLike is not index-based!");
        }

        @Override
        public R get(String id) {
            return fields.get(id);
        }

        @Override
        public R get(int index) {
            throw new UnsupportedOperationException("Fields.MapLike is not index-based!");
        }

        @Override
        public DataResult<R> encode(CodecOperations<R> operations) {
            return operations.encodeMap(fields, operations::encodeString, DataResult.Success::new);
        }

        @Override
        public String toString() {
            return "Fields.MapLike[fields: " + fields + "]";
        }
    }


    record ListLike<R>(List<R> fields) implements Fields<R> {


        @Override
        public void put(String id, R field) {
            throw new UnsupportedOperationException("Fields.List is not id-based!");
        }

        @Override
        public void put(R field) {
            fields.add(field);
        }

        @Override
        public R get(String id) {
            throw new UnsupportedOperationException("Fields.ListLike is not id-based!");
        }

        @Override
        public R get(int index) {
            return fields.get(index);
        }

        @Override
        public DataResult<R> encode(CodecOperations<R> operations) {
            return operations.encodeCollection(fields, DataResult.Success::new);
        }

        @Override
        public String toString() {
            return "Fields.ListLike[fields: " + fields + "]";
        }
    }
}
