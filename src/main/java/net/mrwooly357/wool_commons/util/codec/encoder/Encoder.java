package net.mrwooly357.wool_commons.util.codec.encoder;

import net.mrwooly357.wool_commons.util.codec.StringIdentifiable;
import net.mrwooly357.wool_commons.util.codec.operations.CodecOperations;
import net.mrwooly357.wool_commons.util.codec.DataResult;
import net.mrwooly357.wool_commons.util.function.Functions;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public interface Encoder<A> {


    static <A> UnitEncoder<A> unit() {
        return new UnitEncoder<>();
    }

    static <A> OptionalEncoder<A> optional(Encoder<A> encoder) {
        return new OptionalEncoder<>(encoder);
    }

    static <F, S> PairEncoder<F, S> pair(Encoder<F> firstEncoder, Encoder<S> secondEncoder) {
        return new PairEncoder<>(firstEncoder, secondEncoder);
    }

    static <L, R> EitherEncoder<L, R> either(Encoder<L> leftEncoder, Encoder<R> rightEncoder) {
        return new EitherEncoder<>(leftEncoder, rightEncoder);
    }

    @SuppressWarnings("unchecked")
    static <T, A> DispatchedEncoder<T, A> dispatched(Encoder<T> typeEncoder, Function<? super A, ? extends T> typeGetter, Function<? super T, ? extends Encoder<? extends A>> valueEncoder) {
        return new DispatchedEncoder<>(typeEncoder, Functions.identified(typeGetter::apply, "A", "T"), Functions.identified(t -> (Encoder<A>) valueEncoder.apply(t), "T", "Encoder<A>"));
    }

    static <A> RecursiveEncoder<A> recursive(Function<? super RecursiveEncoder<A>, ? extends Encoder<A>> wrapped) {
        return new RecursiveEncoder<>(Functions.identified(wrapped::apply, "RecursiveEncoder<A>", "Encoder<A>"));
    }

    static <E> CollectionEncoder<E> collection(Encoder<E> valueEncoder) {
        return new CollectionEncoder<>(valueEncoder);
    }

    static <E> MappedEncoder<Collection<E>, List<E>> list(Encoder<E> valueEncoder) {
        return mapTo(collection(valueEncoder), Functions.identified(Function.identity(), "Collection<E>", "List<E>"));
    }

    static <E> MappedEncoder<Collection<E>, Set<E>> set(Encoder<E> valueEncoder) {
        return mapTo(collection(valueEncoder), Functions.identified(Set::copyOf, "Collection<E>", "List<E>"));
    }

    static <K, V> MapEncoder<K, V> map(Encoder<K> keyEncoder, Encoder<V> valueEncoder) {
        return new MapEncoder<>(keyEncoder, valueEncoder);
    }

    static <K, V> BoundedMapEncoder<K, V> map(Encoder<K> keyEncoder, Function<? super K, ? extends Encoder<V>> valueEncoder) {
        return new BoundedMapEncoder<>(keyEncoder, Functions.identified(valueEncoder::apply, "K", "Encoder<V>"));
    }

    static <E extends Enum<E> & StringIdentifiable> EnumEncoder<E> forEnum() {
        return new EnumEncoder<>();
    }

    static <A, O> MappedEncoder<A, O> mapTo(Encoder<A> encoder, Function<? super O, ? extends A> mapper) {
        return new MappedEncoder<>(encoder, mapper);
    }

    static <A> ValidatedEncoder<A> validated(Encoder<A> encoder, Predicate<? super A> predicate, Supplier<String> message) {
        return new ValidatedEncoder<>(encoder, predicate, message);
    }

    <R> DataResult<R> encode(CodecOperations<R> operations, A input);
}
