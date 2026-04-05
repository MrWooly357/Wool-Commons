package net.mrwooly357.wool_commons.util.codec.operations;

import net.mrwooly357.wool_commons.util.Pair;
import net.mrwooly357.wool_commons.util.codec.DataResult;

import java.util.Collection;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;

public interface CodecOperations<A> {


    boolean usesStringFieldIds();

    DataResult<A> empty();

    boolean isEmpty(A input);

    DataResult<A> encodeByte(byte b);

    DataResult<A> encodeShort(short s);

    DataResult<A> encodeInt(int i);

    DataResult<A> encodeLong(long l);

    DataResult<A> encodeFloat(float f);

    DataResult<A> encodeDouble(double d);

    DataResult<A> encodeBoolean(boolean bl);

    DataResult<A> encodeChar(char c);

    DataResult<A> encodeString(String s);

    <E> DataResult<A> encodeCollection(Collection<E> c, Function<? super E, ? extends DataResult<A>> valueEncoder);

    default  <K, V> DataResult<A> encodeMap(Map<K, V> m, Function<? super K, ? extends DataResult<A>> keyEncoder, Function<? super V, ? extends DataResult<A>> valueEncoder) {
        return encodeMap(m, keyEncoder, (_, v) -> valueEncoder.apply(v));
    }

    <K, V> DataResult<A> encodeMap(Map<K, V> m, Function<? super K, ? extends DataResult<A>> keyEncoder, BiFunction<? super K, ? super V, ? extends DataResult<A>> valueEncoder);

    <F, S> DataResult<A> encodePair(Pair<F, S> pair, Function<? super F, ? extends DataResult<A>> firstEncoder, Function<? super S, ? extends DataResult<A>> secondEncoder);

    DataResult<Byte> decodeByte(A input);

    DataResult<Short> decodeShort(A input);

    DataResult<Integer> decodeInt(A input);

    DataResult<Long> decodeLong(A input);

    DataResult<Float> decodeFloat(A input);

    DataResult<Double> decodeDouble(A input);

    DataResult<Boolean> decodeBoolean(A input);

    DataResult<Character> decodeChar(A input);

    DataResult<String> decodeString(A input);

    <E> DataResult<Collection<E>> decodeCollection(A input, Function<? super A, ? extends DataResult<E>> valueDecoder);

    default  <K, V> DataResult<Map<K, V>> decodeMap(A input, Function<? super A, ? extends DataResult<K>> keyDecoder, Function<? super A, ? extends DataResult<V>> valueDecoder) {
        return decodeMap(input, keyDecoder, (_, a) -> valueDecoder.apply(a));
    }

    <K, V> DataResult<Map<K, V>> decodeMap(A input, Function<? super A, ? extends DataResult<K>> keyDecoder, BiFunction<? super K, ? super A, ? extends DataResult<V>> valueDecoder);

    <F, S> DataResult<Pair<F, S>> decodePair(A input, Function<? super A, ? extends DataResult<F>> firstDecoder, Function<? super A, ? extends DataResult<S>> secondDecoder);
}
