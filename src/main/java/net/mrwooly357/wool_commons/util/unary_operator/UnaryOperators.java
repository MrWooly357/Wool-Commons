package net.mrwooly357.wool_commons.util.unary_operator;

import java.util.function.UnaryOperator;

public final class UnaryOperators {

    private UnaryOperators() throws UnsupportedOperationException {
        throw new UnsupportedOperationException("Can't instantiate UnaryOperators!");
    }


    public static <A> IdentifiedUnaryOperator<A> identified(UnaryOperator<A> operator, String in) {
        return new IdentifiedUnaryOperator<>(operator, in);
    }

    public static <A> IdentifiedUnaryOperator<A> identified(UnaryOperator<A> operator, Class<A> in) {
        return new IdentifiedUnaryOperator<>(operator, in.getSimpleName());
    }
}
