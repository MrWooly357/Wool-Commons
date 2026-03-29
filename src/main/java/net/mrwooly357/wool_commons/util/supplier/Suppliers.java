package net.mrwooly357.wool_commons.util.supplier;

import net.mrwooly357.wool_commons.util.supplier.custom.IdentifiedSupplier;
import net.mrwooly357.wool_commons.util.supplier.custom.MemoizingSupplier;

import java.util.function.Supplier;

public final class Suppliers {

    private Suppliers() throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }


    public static <A> IdentifiedSupplier<A> identified(Supplier<A> supplier, String out) {
        return new IdentifiedSupplier<>(supplier, out);
    }

    public static <A> IdentifiedSupplier<A> identified(Supplier<A> supplier, Class<A> out) {
        return new IdentifiedSupplier<>(supplier, out.getSimpleName());
    }

    public static <A> MemoizingSupplier<A> memoize(Supplier<A> supplier) {
        return new MemoizingSupplier<>(supplier);
    }
}
