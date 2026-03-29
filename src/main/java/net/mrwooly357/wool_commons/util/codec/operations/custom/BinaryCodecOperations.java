package net.mrwooly357.wool_commons.util.codec.operations.custom;

import net.mrwooly357.wool_commons.util.Pair;
import net.mrwooly357.wool_commons.util.codec.DataResult;
import net.mrwooly357.wool_commons.util.codec.operations.CodecOperations;

import java.util.Collection;
import java.util.Map;
import java.util.function.Function;

public class BinaryCodecOperations implements CodecOperations<byte[]> {

    public BinaryCodecOperations INSTANCE = new BinaryCodecOperations();
    private static final DataResult<byte[]> EMPTY = new DataResult.Success<>(new byte[0]);

    protected BinaryCodecOperations() {}


    @Override
    public boolean usesStringFieldIds() {
        return false;
    }

    @Override
    public DataResult<byte[]> empty() {
        return EMPTY;
    }

    @Override
    public boolean isEmpty(byte[] input) {
        return input.length == 0;
    }

    @Override
    public DataResult<byte[]> encodeByte(byte b) {
        return new DataResult.Success<>(new byte[] {b});
    }

    @Override
    public DataResult<byte[]> encodeShort(short s) {
        return null;
    }

    @Override
    public DataResult<byte[]> encodeInt(int i) {
        return null;
    }

    @Override
    public DataResult<byte[]> encodeLong(long l) {
        return null;
    }

    @Override
    public DataResult<byte[]> encodeFloat(float f) {
        return null;
    }

    @Override
    public DataResult<byte[]> encodeDouble(double d) {
        return null;
    }

    @Override
    public DataResult<byte[]> encodeBoolean(boolean bl) {
        return null;
    }

    @Override
    public DataResult<byte[]> encodeChar(char c) {
        return null;
    }

    @Override
    public DataResult<byte[]> encodeString(String s) {
        return null;
    }

    @Override
    public <E> DataResult<byte[]> encodeCollection(Collection<E> c, Function<? super E, ? extends DataResult<byte[]>> valueEncoder) {
        return null;
    }

    @Override
    public <K, V> DataResult<byte[]> encodeMap(Map<K, V> m, Function<? super K, ? extends DataResult<byte[]>> keyEncoder, Function<? super V, ? extends DataResult<byte[]>> valueEncoder) {
        return null;
    }

    @Override
    public <F, S> DataResult<byte[]> encodePair(Pair<F, S> pair, Function<? super F, ? extends DataResult<byte[]>> firstEncoder, Function<? super S, ? extends DataResult<byte[]>> secondEncoder) {
        return null;
    }

    @Override
    public DataResult<Byte> decodeByte(byte[] input) {
        return new DataResult.Success<>(input[0]);
    }

    @Override
    public DataResult<Short> decodeShort(byte[] input) {
        return null;
    }

    @Override
    public DataResult<Integer> decodeInt(byte[] input) {
        return null;
    }

    @Override
    public DataResult<Long> decodeLong(byte[] input) {
        return null;
    }

    @Override
    public DataResult<Float> decodeFloat(byte[] input) {
        return null;
    }

    @Override
    public DataResult<Double> decodeDouble(byte[] input) {
        return null;
    }

    @Override
    public DataResult<Boolean> decodeBoolean(byte[] input) {
        return null;
    }

    @Override
    public DataResult<Character> decodeChar(byte[] input) {
        return null;
    }

    @Override
    public DataResult<String> decodeString(byte[] input) {
        return null;
    }

    @Override
    public <E> DataResult<Collection<E>> decodeCollection(byte[] input, Function<? super byte[], ? extends DataResult<E>> valueDecoder) {
        return null;
    }

    @Override
    public <K, V> DataResult<Map<K, V>> decodeMap(byte[] input, Function<? super byte[], ? extends DataResult<K>> keyDecoder, Function<? super byte[], ? extends DataResult<V>> valueDecoder) {
        return null;
    }

    @Override
    public <F, S> DataResult<Pair<F, S>> decodePair(byte[] input, Function<? super byte[], ? extends DataResult<F>> firstDecoder, Function<? super byte[], ? extends DataResult<S>> secondDecoder) {
        return null;
    }
}
