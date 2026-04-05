package net.mrwooly357.wool_commons.util.codec.encoder;

import net.mrwooly357.wool_commons.util.codec.DataResult;
import net.mrwooly357.wool_commons.util.codec.operations.CodecOperations;
import net.mrwooly357.wool_commons.util.supplier.Suppliers;

import java.util.function.Function;
import java.util.function.Supplier;

public class RecursiveEncoder<A> implements Encoder<A> {

    private final Supplier<Encoder<A>> wrapped;

    public RecursiveEncoder(Function<RecursiveEncoder<A>, Encoder<A>> wrapped) {
        this.wrapped = Suppliers.memoize(() -> wrapped.apply(this));
    }


    @Override
    public <R> DataResult<R> encode(CodecOperations<R> operations, A input) {
        return wrapped.get().encode(operations, input);
    }

    @Override
    public int hashCode() {
        return wrapped.hashCode();
    }

    @Override
    public boolean equals(Object other) {
        return super.equals(other) || (other instanceof RecursiveEncoder<?> encoder
                && wrapped.equals(encoder.wrapped));
    }

    @Override
    public String toString() {
        return "RecursiveEncoder[wrapped: " + wrapped + "]";
    }
}
