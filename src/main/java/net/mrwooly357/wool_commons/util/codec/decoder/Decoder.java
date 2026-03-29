package net.mrwooly357.wool_commons.util.codec.decoder;

import net.mrwooly357.wool_commons.util.codec.StringIdentifiable;
import net.mrwooly357.wool_commons.util.codec.operations.CodecOperations;
import net.mrwooly357.wool_commons.util.codec.DataResult;
import net.mrwooly357.wool_commons.util.codec.decoder.custom.*;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public interface Decoder<A> {


    static <A> UnitDecoder<A> unit(A instance) {
        return new UnitDecoder<>(instance);
    }

    static <A> OptionalDecoder<A> optional(Decoder<A> decoder) {
        return new OptionalDecoder<>(decoder, Optional.empty());
    }

    static <A> OptionalDecoder<A> optional(Decoder<A> decoder, A fallback) {
        return new OptionalDecoder<>(decoder, Optional.ofNullable(fallback));
    }

    static <F, S> PairDecoder<F, S> pair(Decoder<F> firstDecoder, Decoder<S> secondDecoder) {
        return new PairDecoder<>(firstDecoder, secondDecoder);
    }

    static <L, R> EitherDecoder<L, R> either(Decoder<L> leftDecoder, Decoder<R> rightDecoder) {
        return new EitherDecoder<>(leftDecoder, rightDecoder);
    }

    static <E> CollectionDecoder<E> collection(Decoder<E> valueDecoder) {
        return new CollectionDecoder<>(valueDecoder);
    }

    static <E> MappedDecoder<Collection<E>, List<E>> list(Decoder<E> valueDecoder, Function<? super Collection<E>, ? extends List<E>> constructor) {
        return collection(valueDecoder).mapDecoder(constructor);
    }

    static <E> MappedDecoder<Collection<E>, Set<E>> set(Decoder<E> valueDecoder, Function<? super Collection<E>, ? extends Set<E>> constructor) {
        return collection(valueDecoder).mapDecoder(constructor);
    }

    static <K, V> MapDecoder<K, V> map(Decoder<K> keyDecoder, Decoder<V> valueDecoder) {
        return new MapDecoder<>(keyDecoder, valueDecoder);
    }

    static <A> RecursiveDecoder<A> recursive(Function<? super RecursiveDecoder<A>, ? extends Decoder<A>> wrapped) {
        return new RecursiveDecoder<>(wrapped::apply);
    }

    static <E extends Enum<E> & StringIdentifiable> EnumDecoder<E> forEnum(Function<? super String, ? extends E> mapper) {
        return new EnumDecoder<>(mapper::apply);
    }

    <R> DataResult<A> decode(CodecOperations<R> operations, R input);

    default <O> MappedDecoder<A, O> mapDecoder(Function<? super A, ? extends O> mapper) {
        return new MappedDecoder<>(this, mapper);
    }

    default ValidatedDecoder<A> validatedDecoder(Predicate<? super A> predicate, Supplier<String> message) {
        return new ValidatedDecoder<>(this, predicate, message);
    }
}
