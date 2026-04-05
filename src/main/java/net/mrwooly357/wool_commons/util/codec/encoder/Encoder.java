package net.mrwooly357.wool_commons.util.codec.encoder;

import net.mrwooly357.wool_commons.util.codec.StringIdentifiable;
import net.mrwooly357.wool_commons.util.codec.operations.CodecOperations;
import net.mrwooly357.wool_commons.util.codec.DataResult;

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

    static <E> CollectionEncoder<E> collection(Encoder<E> valueEncoder) {
        return new CollectionEncoder<>(valueEncoder);
    }

    static <E> MappedEncoder<Collection<E>, List<E>> list(Encoder<E> valueEncoder) {
        return collection(valueEncoder).mapEncoder(l -> l);
    }

    static <E> MappedEncoder<Collection<E>, Set<E>> set(Encoder<E> valueEncoder) {
        return collection(valueEncoder).mapEncoder(s -> s);
    }

    static <K, V> MapEncoder<K, V> map(Encoder<K> keyEncoder, Encoder<V> valueEncoder) {
        return new MapEncoder<>(keyEncoder, valueEncoder);
    }

    static <K, V> BoundedMapEncoder<K, V> map(Encoder<K> keyEncoder, Function<? super K, ? extends Encoder<V>> valueEncoder) {
        return new BoundedMapEncoder<>(keyEncoder, valueEncoder::apply);
    }

    static <A> RecursiveEncoder<A> recursive(Function<? super RecursiveEncoder<A>, ? extends Encoder<A>> wrapped) {
        return new RecursiveEncoder<>(wrapped::apply);
    }

    static <E extends Enum<E> & StringIdentifiable> EnumEncoder<E> forEnum() {
        return new EnumEncoder<>();
    }

    <R> DataResult<R> encode(CodecOperations<R> operations, A input);

    default <O> MappedEncoder<A, O> mapEncoder(Function<? super O, ? extends A> mapper) {
        return new MappedEncoder<>(this, mapper);
    }

    default ValidatedEncoder<A> validatedEncoder(Predicate<? super A> predicate, Supplier<String> message) {
        return new ValidatedEncoder<>(this, predicate, message);
    }
}
