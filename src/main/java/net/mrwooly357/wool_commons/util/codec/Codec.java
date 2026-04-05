package net.mrwooly357.wool_commons.util.codec;

import net.mrwooly357.wool_commons.util.codec.decoder.Decoder;
import net.mrwooly357.wool_commons.util.codec.encoder.Encoder;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public interface Codec<A> extends Encoder<A>, Decoder<A> {


    static <A> UnitCodec<A> unit(A instance) {
        return new UnitCodec<>(Encoder.unit(), Decoder.unit(instance));
    }

    static <A> SimpleCodec<A> of(Encoder<A> encoder, Decoder<A> decoder) {
        return new SimpleCodec<>(encoder, decoder);
    }

    static <A> OptionalCodec<A> optional(Codec<A> codec) {
        return new OptionalCodec<>(Encoder.optional(codec), Decoder.optional(codec));
    }

    static <A> OptionalCodec<A> optional(Codec<A> codec, A fallback) {
        return new OptionalCodec<>(Encoder.optional(codec), Decoder.optional(codec, fallback));
    }

    static <F, S> PairCodec<F, S> pair(Codec<F> firstCodec, Codec<S> secondCodec) {
        return new PairCodec<>(Encoder.pair(firstCodec, secondCodec), Decoder.pair(firstCodec, secondCodec));
    }

    static <L, R> EitherCodec<L, R> either(Codec<L> leftCodec, Codec<R> rightCodec) {
        return new EitherCodec<>(Encoder.either(leftCodec, rightCodec), Decoder.either(leftCodec, rightCodec));
    }

    static <K, V> MapCodec<K, V> map(Codec<K> keyCodec, Codec<V> valueCodec) {
        return new MapCodec<>(Encoder.map(keyCodec, valueCodec), Decoder.map(keyCodec, valueCodec));
    }

    static <K, V> BoundedMapCodec<K, V> map(Codec<K> keyCodec, Function<? super K, ? extends Codec<V>> valueCodec) {
        return new BoundedMapCodec<>(Encoder.map(keyCodec, valueCodec), Decoder.map(keyCodec, valueCodec));
    }

    static <A> RecursiveCodec<A> recursive(Function<? super RecursiveCodec<A>, ? extends Codec<A>> wrapped) {
        return new RecursiveCodec<>(wrapped::apply);
    }

    static <E extends Enum<E> & StringIdentifiable> Codec<E> forEnum(Function<? super String, ? extends E> mapper) {
        return new EnumCodec<>(Encoder.forEnum(), Decoder.forEnum(mapper));
    }

    static <A, O> CodecGroups.G1<A, O> group(FieldCodec.AbstractBuilder<A, O, ?> field) {
        return new CodecGroups.G1<>(field.build(0));
    }

    static <A1, A2, O> CodecGroups.G2<A1, A2, O> group(FieldCodec.AbstractBuilder<A1, O, ?> field1, FieldCodec.AbstractBuilder<A2, O, ?> field2) {
        return new CodecGroups.G2<>(field1.build(0), field2.build(1));
    }

    static <A1, A2, A3, O> CodecGroups.G3<A1, A2, A3, O> group(FieldCodec.AbstractBuilder<A1, O, ?> field1, FieldCodec.AbstractBuilder<A2, O, ?> field2, FieldCodec.AbstractBuilder<A3, O, ?> field3) {
        return new CodecGroups.G3<>(field1.build(0), field2.build(1), field3.build(2));
    }

    static <A1, A2, A3, A4, O> CodecGroups.G4<A1, A2, A3, A4, O> group(
            FieldCodec.AbstractBuilder<A1, O, ?> field1,
            FieldCodec.AbstractBuilder<A2, O, ?> field2,
            FieldCodec.AbstractBuilder<A3, O, ?> field3,
            FieldCodec.AbstractBuilder<A4, O, ?> field4
    ) {
        return new CodecGroups.G4<>(field1.build(0), field2.build(1), field3.build(2), field4.build(3));
    }

    default <O> DispatchedCodec<A, O> dispatched(Function<? super O, ? extends A> typeGetter, Function<? super A, ? extends Codec<? extends O>> valueCodec) {
        return new DispatchedCodec<>(Encoder.dispatched(this, typeGetter, valueCodec), Decoder.dispatched(this, valueCodec));
    }

    default CollectionCodec<A> collection() {
        return new CollectionCodec<>(Encoder.collection(this), Decoder.collection(this));
    }

    default MappedCodec<Collection<A>, List<A>> list(Function<? super Collection<A>, ? extends List<A>> constructor) {
        return new MappedCodec<>(Encoder.list(this), Decoder.list(this, constructor));
    }

    default MappedCodec<Collection<A>, Set<A>> set(Function<? super Collection<A>, ? extends Set<A>> constructor) {
        return new MappedCodec<>(Encoder.set(this), Decoder.set(this, constructor));
    }

    default <O> MappedCodec<A, O> map(Function<? super O, ? extends A> from, Function<? super A, ? extends O> to) {
        return new MappedCodec<>(Encoder.mapTo(this, from), Decoder.mapTo(this, to));
    }

    default ValidatedCodec<A> validated(Predicate<? super A> predicate, Supplier<String> message) {
        return new ValidatedCodec<>(Encoder.validated(this, predicate, message), Decoder.validated(this, predicate, message));
    }

    default <O> FieldCodec.Builder<A, O> fieldOf(String id, Function<O, A> getter) {
        return new FieldCodec.Builder<>(id, this, getter);
    }

    default <O> FieldCodec.Optional.Builder<A, O> optionalFieldOf(String id, Function<O, A> getter, A fallback) {
        return new FieldCodec.Optional.Builder<>(id, this, getter, fallback);
    }
}
