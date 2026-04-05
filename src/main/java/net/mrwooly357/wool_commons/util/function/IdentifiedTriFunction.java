package net.mrwooly357.wool_commons.util.function;

public record IdentifiedTriFunction<A, B, C, D>(TriFunction<A, B, C, D> delegate, String firstIn, String secondIn, String thirdIn, String out) implements TriFunction<A, B, C, D> {


    @Override
    public D apply(A a, B b, C c) {
        return delegate.apply(a, b, c);
    }

    @Override
    public String toString() {
        return "(" + firstIn + ", " + secondIn + ", " + thirdIn + ") -> " + out;
    }
}
