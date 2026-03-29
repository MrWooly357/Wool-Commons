package net.mrwooly357.wool_commons.util.supplier.custom;

import java.util.Objects;
import java.util.function.Supplier;

public class MemoizingSupplier<A> implements Supplier<A> {

    private final Supplier<A> supplier;
    private volatile A cache;

    public MemoizingSupplier(Supplier<A> supplier) {
        this.supplier = supplier;
    }


    @Override
    public A get() {
        if (cache == null)
            cache = supplier.get();

        return cache;
    }

    @Override
    public int hashCode() {
        return Objects.hash(supplier, cache);
    }

    @Override
    public boolean equals(Object other) {
        return super.equals(other) || (other instanceof MemoizingSupplier<?> supplier
                && this.supplier.equals(supplier.supplier)
                && Objects.equals(cache, supplier.cache));
    }

    @Override
    public String toString() {
        return "MemoizingSupplier[supplier: " + supplier
                + ", cache: " + cache + "]";
    }
}
