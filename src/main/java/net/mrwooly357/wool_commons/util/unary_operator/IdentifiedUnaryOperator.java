package net.mrwooly357.wool_commons.util.unary_operator;

import java.util.function.UnaryOperator;

public record IdentifiedUnaryOperator<A>(UnaryOperator<A> delegate, String in) implements UnaryOperator<A> {


    @Override
    public A apply(A a) {
        return delegate.apply(a);
    }

    @Override
    public String toString() {
        return in + " -> " + in;
    }
}
