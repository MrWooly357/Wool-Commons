package net.mrwooly357.wool_commons.util.function;

@FunctionalInterface
public interface TriFunction<A, B, C, D> {


    D apply(A a, B b, C c);

    default IdentifiedTriFunction<A, B, C, D> identified(String firstIn, String secondIn, String thirdIn, String out) {
        return new IdentifiedTriFunction<>(this, firstIn, secondIn, thirdIn, out);
    }

    default IdentifiedTriFunction<A, B, C, D> identified(Class<A> firstIn, Class<B> secondIn, Class<C> thirdIn, Class<D> out) {
        return new IdentifiedTriFunction<>(this, firstIn.getSimpleName(), secondIn.getSimpleName(), thirdIn.getSimpleName(), out.getSimpleName());
    }
}
