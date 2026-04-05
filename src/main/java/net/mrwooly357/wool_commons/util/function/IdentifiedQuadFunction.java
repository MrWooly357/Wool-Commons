package net.mrwooly357.wool_commons.util.function;

public record IdentifiedQuadFunction<A, B, C, D, E>(QuadFunction<A, B, C, D, E> delegate, String firstIn, String secondIn, String thirdIn, String fourthIn, String out) implements QuadFunction<A, B, C, D, E> {


    @Override
    public E apply(A a, B b, C c, D d) {
        return delegate.apply(a, b, c, d);
    }

    @Override
    public String toString() {
        return "(" + firstIn + ", " + secondIn + ", " + thirdIn + ", " + fourthIn + ") -> " + out;
    }
}
