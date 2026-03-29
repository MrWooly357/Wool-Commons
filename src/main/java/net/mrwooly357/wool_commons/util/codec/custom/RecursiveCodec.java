package net.mrwooly357.wool_commons.util.codec.custom;

import net.mrwooly357.wool_commons.util.codec.Codec;
import net.mrwooly357.wool_commons.util.codec.DataResult;
import net.mrwooly357.wool_commons.util.codec.operations.CodecOperations;
import net.mrwooly357.wool_commons.util.supplier.Suppliers;

import java.util.function.Function;
import java.util.function.Supplier;

public class RecursiveCodec<A> implements Codec<A> {

    private final Supplier<Codec<A>> wrapped;

    public RecursiveCodec(Function<RecursiveCodec<A>, Codec<A>> wrapped) {
        this.wrapped = Suppliers.memoize(() -> wrapped.apply(this));
    }


    @Override
    public <R> DataResult<R> encode(CodecOperations<R> operations, A input) {
        return wrapped.get().encode(operations, input);
    }

    @Override
    public <R> DataResult<A> decode(CodecOperations<R> operations, R input) {
        return wrapped.get().decode(operations, input);
    }

    @Override
    public int hashCode() {
        return wrapped.hashCode();
    }

    @Override
    public boolean equals(Object other) {
        return super.equals(other) || (other instanceof RecursiveCodec<?> codec
                && wrapped.equals(codec.wrapped));
    }

    @Override
    public String toString() {
        return "RecursiveCodec[encoder: " + wrapped + "]";
    }
}
