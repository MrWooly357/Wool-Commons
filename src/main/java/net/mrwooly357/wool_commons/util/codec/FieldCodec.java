package net.mrwooly357.wool_commons.util.codec;

import net.mrwooly357.wool_commons.util.codec.operations.CodecOperations;

import java.util.Objects;
import java.util.function.Function;

public sealed class FieldCodec<A, O> {

    protected final String id;
    protected final int index;
    protected final Codec<A> codec;
    protected final Function<O, A> getter;

    public FieldCodec(String id, int index, Codec<A> codec, Function<O, A> getter) {
        this.id = id;
        this.index = index;
        this.codec = codec;
        this.getter = getter;
    }


    public <R> void encode(CodecOperations<R> operations, O owner, Fields<R> fields) {
        codec.encode(operations, getter.apply(owner))
                .ifSuccess(r -> {
                    if (operations.usesStringFieldIds())
                        fields.put(id, r);
                    else
                        fields.put(r);
                });
    }

    public <R> DataResult<A> decode(CodecOperations<R> operations, Fields<R> fields) {
        return codec.decode(operations, operations.usesStringFieldIds() ? fields.get(id) : fields.get(index));
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, index, codec, getter);
    }

    @Override
    public boolean equals(Object other) {
        return super.equals(other) || (other instanceof FieldCodec<?,?> field
                && id.equals(field.id)
                && index == field.index
                && this.codec.equals(field.codec)
                && getter.equals(field.getter));
    }

    @Override
    public String toString() {
        return "FieldCodec[id: " + id
                + ", index: " + index
                + ", codec: " + codec
                + ", getter: " + getter + "]";
    }


    public sealed interface AbstractBuilder<A, O, F extends FieldCodec<A, O>> {


        F build(int index);
    }


    public record Builder<A, O>(String id, Codec<A> codec, Function<O, A> getter) implements AbstractBuilder<A, O, FieldCodec<A, O>> {


        @Override
        public FieldCodec<A, O> build(int index) {
            return new FieldCodec<>(id, index, codec, getter);
        }

        @Override
        public String toString() {
            return "FieldCodec.Builder[id: " + id
                    + ", codec: " + codec
                    + ", getter: " + getter + "]";
        }
    }


    public static final class Optional<A, O> extends FieldCodec<A, O> {

        private final A fallback;
        private final DataResult<A> cached;

        public Optional(String id, int index, Codec<A> codec, Function<O, A> getter, A fallback) {
            super(id, index, codec, getter);

            this.fallback = fallback;
            cached = new DataResult.Success<>(fallback);
        }


        @Override
        public <R> void encode(CodecOperations<R> operations, O owner, Fields<R> fields) {
            A input = getter.apply(owner);

            if (input != null)
                super.encode(operations, owner, fields);
        }

        @Override
        public <R> DataResult<A> decode(CodecOperations<R> operations, Fields<R> fields) {
            if (operations.usesStringFieldIds()) {
                R r = fields.get(id);

                return r != null ? codec.decode(operations, r) : cached;
            } else {
                R r = fields.get(index);

                return r != null ? codec.decode(operations, r) : cached;
            }
        }

        @Override
        public int hashCode() {
            return Objects.hash(id, index, codec, getter, fallback);
        }

        @Override
        public boolean equals(Object other) {
            return (this == other) || (other instanceof FieldCodec.Optional<?,?> field
                    && id.equals(field.id)
                    && index == field.index
                    && this.codec.equals(field.codec)
                    && getter.equals(field.getter)
                    && Objects.equals(fallback, field.fallback));
        }

        @Override
        public String toString() {
            return "FieldCodec.Optional[id: " + id
                    + ", index: " + index
                    + ", codec: " + codec
                    + ", getter: " + getter
                    + ", fallback: " + fallback + "]";
        }


        public record Builder<A, O>(String id, Codec<A> codec, Function<O, A> getter, A fallback) implements AbstractBuilder<A, O, Optional<A, O>> {


            @Override
            public FieldCodec.Optional<A, O> build(int index) {
                return new Optional<>(id, index, codec, getter, fallback);
            }

            @Override
            public String toString() {
                return "FieldCodec.Optional.Builder[id: " + id
                        + ", codec: " + codec
                        + ", getter: " + getter
                        + ", fallback: " + fallback + "]";
            }
        }
    }
}
