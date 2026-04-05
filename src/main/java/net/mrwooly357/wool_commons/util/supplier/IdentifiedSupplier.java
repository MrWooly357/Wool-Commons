package net.mrwooly357.wool_commons.util.supplier;

import java.util.function.Supplier;

public record IdentifiedSupplier<A>(Supplier<A> delegate, String out) implements Supplier<A> {


    @Override
    public A get() {
        return delegate.get();
    }

    @Override
    public String toString() {
        return "() -> " + out;
    }
}
