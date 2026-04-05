package net.mrwooly357.wool_commons.util.function;

@FunctionalInterface
public interface QuadFunction<A, B, C, D, E> {


    E apply(A a, B b, C c, D d);

    default IdentifiedQuadFunction<A, B, C, D, E> identified(String firstIn, String secondIn, String thirdIn, String fourthIn, String out) {
        return new IdentifiedQuadFunction<>(this, firstIn, secondIn, thirdIn, fourthIn, out);
    }

    default IdentifiedQuadFunction<A, B, C, D, E> identified(Class<A> firstIn, Class<B> secondIn, Class<C> thirdIn, Class<D> fourthIn, Class<E> out) {
        return new IdentifiedQuadFunction<>(this, firstIn.getSimpleName(), secondIn.getSimpleName(), thirdIn.getSimpleName(), fourthIn.getSimpleName(), out.getSimpleName());
    }
}
