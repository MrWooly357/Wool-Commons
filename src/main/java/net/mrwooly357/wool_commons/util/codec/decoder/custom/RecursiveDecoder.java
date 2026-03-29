package net.mrwooly357.wool_commons.util.codec.decoder.custom;

import net.mrwooly357.wool_commons.util.codec.DataResult;
import net.mrwooly357.wool_commons.util.codec.decoder.Decoder;
import net.mrwooly357.wool_commons.util.codec.operations.CodecOperations;
import net.mrwooly357.wool_commons.util.supplier.Suppliers;

import java.util.function.Function;
import java.util.function.Supplier;

public class RecursiveDecoder<A> implements Decoder<A> {

    private final Supplier<Decoder<A>> wrapped;

    public RecursiveDecoder(Function<RecursiveDecoder<A>, Decoder<A>> wrapped) {
        this.wrapped = Suppliers.memoize(() -> wrapped.apply(this));
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
        return super.equals(other) || (other instanceof RecursiveDecoder<?> decoder
                && wrapped.equals(decoder.wrapped));
    }

    @Override
    public String toString() {
        return "RecursiveDecoder[wrapped: " + wrapped + "]";
    }
}
