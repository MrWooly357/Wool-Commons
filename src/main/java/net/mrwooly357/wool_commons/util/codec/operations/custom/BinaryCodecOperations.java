package net.mrwooly357.wool_commons.util.codec.operations.custom;

import net.mrwooly357.wool_commons.util.Pair;
import net.mrwooly357.wool_commons.util.codec.DataResult;
import net.mrwooly357.wool_commons.util.codec.operations.CodecOperations;

import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Function;

public class BinaryCodecOperations implements CodecOperations<byte[]> {

    public static final BinaryCodecOperations INSTANCE = new BinaryCodecOperations();
    private static final DataResult<byte[]> EMPTY = new DataResult.Success<>(new byte[0]);
    private static final DataResult<byte[]> TRUE = new DataResult.Success<>(new byte[] {1});
    private static final DataResult<byte[]> FALSE = new DataResult.Success<>(new byte[] {0});

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
        return new DataResult.Success<>(new byte[] {
                (byte) (s >>> 8),
                (byte) s
        });
    }

    @Override
    public DataResult<byte[]> encodeInt(int i) {
        return new DataResult.Success<>(intToBytes(i));
    }

    @Override
    public DataResult<byte[]> encodeLong(long l) {
        return new DataResult.Success<>(new byte[] {
                (byte) (l >>> 56),
                (byte) (l >>> 48),
                (byte) (l >>> 40),
                (byte) (l >>> 32),
                (byte) (l >>> 24),
                (byte) (l >>> 16),
                (byte) (l >>> 8),
                (byte) l
        });
    }

    @Override
    public DataResult<byte[]> encodeFloat(float f) {
        return encodeInt(Float.floatToIntBits(f));
    }

    @Override
    public DataResult<byte[]> encodeDouble(double d) {
        return encodeLong(Double.doubleToLongBits(d));
    }

    @Override
    public DataResult<byte[]> encodeBoolean(boolean bl) {
        return bl ? TRUE : FALSE;
    }

    @Override
    public DataResult<byte[]> encodeChar(char c) {
        return new DataResult.Success<>(new byte[] {
                (byte) (c >>> 8),
                (byte) c
        });
    }

    @Override
    public DataResult<byte[]> encodeString(String s) {
        byte[] bytes = s.getBytes(StandardCharsets.UTF_8);
        int length = bytes.length;
        byte[] result = new byte[length + 4];
        System.arraycopy(intToBytes(length), 0, result, 0, 4);
        System.arraycopy(bytes, 0, result, 4, length);

        return new DataResult.Success<>(result);
    }

    private static byte[] intToBytes(int i) {
        return new byte[] {
                (byte) (i >>> 24),
                (byte) (i >>> 16),
                (byte) (i >>> 8),
                (byte) i
        };
    }

    @Override
    public <E> DataResult<byte[]> encodeCollection(Collection<E> c, Function<? super E, ? extends DataResult<byte[]>> valueEncoder) {
        try {
            List<byte[]> encoded = new ArrayList<>();
            int totalSize = 4;

            for (E element : c) {
                byte[] data = valueEncoder.apply(element).getOrThrow();
                encoded.add(data);
                totalSize += 4 + data.length;
            }

            byte[] result = new byte[totalSize];
            System.arraycopy(intToBytes(c.size()), 0, result, 0, 4);
            int offset = 4;

            for (byte[] bytes : encoded) {
                int length = bytes.length;
                System.arraycopy(intToBytes(length), 0, result, offset, 4);
                System.arraycopy(bytes, 0, result, offset + 4, length);
                offset += length + 4;
            }

            return new DataResult.Success<>(result);
        } catch (Exception e) {
            return new DataResult.Error<>("Failed to encode collection " + c + "!", e);
        }
    }

    @Override
    public <K, V> DataResult<byte[]> encodeMap(Map<K, V> m, Function<? super K, ? extends DataResult<byte[]>> keyEncoder, BiFunction<? super K, ? super V, ? extends DataResult<byte[]>> valueEncoder) {
        try {
            int totalSize = 4;
            int size = m.size();
            List<byte[]> encoded = new ArrayList<>();

            for (Map.Entry<K, V> entry : m.entrySet()) {
                K k = entry.getKey();
                byte[] key = keyEncoder.apply(k).getOrThrow();
                byte[] value = valueEncoder.apply(k, entry.getValue()).getOrThrow();
                encoded.add(key);
                encoded.add(value);
                totalSize += key.length + value.length + 8;
            }

            byte[] result = new byte[totalSize];
            System.arraycopy(intToBytes(size), 0, result, 0, 4);
            int offset = 4;

            for (byte[] bytes : encoded) {
                int length = bytes.length;
                System.arraycopy(intToBytes(length), 0, result, offset, 4);
                System.arraycopy(bytes, 0, result, offset + 4, length);
                offset += length + 4;
            }

            return new DataResult.Success<>(result);
        } catch (Exception e) {
            return new DataResult.Error<>("Failed to encode mapTo " + m + "!", e);
        }
    }

    @Override
    public <F, S> DataResult<byte[]> encodePair(Pair<F, S> pair, Function<? super F, ? extends DataResult<byte[]>> firstEncoder, Function<? super S, ? extends DataResult<byte[]>> secondEncoder) {
        try {
            byte[] f = firstEncoder.apply(pair.first()).getOrThrow();
            byte[] s = secondEncoder.apply(pair.second()).getOrThrow();
            int firstLength = f.length;
            int secondLength = s.length;
            byte[] result = new byte[firstLength + secondLength + 8];
            System.arraycopy(intToBytes(firstLength), 0, result, 0, 4);
            System.arraycopy(f, 0, result, 4, firstLength);
            System.arraycopy(intToBytes(secondLength), 0, result, firstLength + 4, 4);
            System.arraycopy(s, 0, result, firstLength + 8, secondLength);

            return new DataResult.Success<>(result);
        } catch (Exception e) {
            return new DataResult.Error<>("Failed to encode pair " + pair + "!", e);
        }
    }

    @Override
    public DataResult<Byte> decodeByte(byte[] input) {
        if (input.length != 1)
            return new DataResult.Error<>("Malformed binary byte " + Arrays.toString(input) + "!", new IllegalArgumentException());
        else
            return new DataResult.Success<>(input[0]);
    }

    @Override
    public DataResult<Short> decodeShort(byte[] input) {
        if (input.length != 2)
            return new DataResult.Error<>("Malformed binary short " + Arrays.toString(input) + "!", new IllegalArgumentException());
        else
            return new DataResult.Success<>(
                    (short) (((input[0] & 0xFF) << 8)
                    | (input[1] & 0xFF))
            );
    }

    @Override
    public DataResult<Integer> decodeInt(byte[] input) {
        if (input.length != 4)
            return new DataResult.Error<>("Malformed binary int " + Arrays.toString(input) + "!", new IllegalArgumentException());
        else
            return new DataResult.Success<>(bytesToInt(input));
    }

    @Override
    public DataResult<Long> decodeLong(byte[] input) {
        if (input.length != 8)
            return new DataResult.Error<>("Malformed binary long " + Arrays.toString(input) + "!", new IllegalArgumentException());
        else
            return new DataResult.Success<>(
                    ((long) (input[0] & 0xFF) << 56)
                    | ((long) (input[1] & 0xFF) << 48)
                    | ((long) (input[2] & 0xFF) << 40)
                    | ((long) (input[3] & 0xFF) << 32)
                    | ((long) (input[4] & 0xFF) << 24)
                    | ((long) (input[5] & 0xFF) << 16)
                    | ((long) (input[6] & 0xFF) << 8)
                    | ((long) (input[7] & 0xFF))
            );
    }

    @Override
    public DataResult<Float> decodeFloat(byte[] input) {
        return decodeInt(input).map(Float::intBitsToFloat);
    }

    @Override
    public DataResult<Double> decodeDouble(byte[] input) {
        return decodeLong(input).map(Double::longBitsToDouble);
    }

    @Override
    public DataResult<Boolean> decodeBoolean(byte[] input) {
        if (input.length != 1)
            return new DataResult.Error<>("Malformed binary byte: " + Arrays.toString(input) + "!", new IllegalArgumentException());
        else
            return new DataResult.Success<>(input[0] != 0);
    }

    @Override
    public DataResult<Character> decodeChar(byte[] input) {
        if (input.length != 2)
            return new DataResult.Error<>("Malformed binary char: " + Arrays.toString(input) + "!", new IllegalArgumentException());
        else
            return new DataResult.Success<>(
                    (char) (((input[0] & 0xFF) << 8)
                    | (input[1] & 0xFF))
            );
    }

    @Override
    public DataResult<String> decodeString(byte[] input) {
        return new DataResult.Success<>(new String(input, 4, bytesToInt(input), StandardCharsets.UTF_8));
    }

    private static int bytesToInt(byte[] bytes) {
        return bytesToInt(bytes, 0);
    }

    private static int bytesToInt(byte[] bytes, int offset) {
        return ((bytes[offset] & 0xFF) << 24)
                | ((bytes[offset + 1] & 0xFF) << 16)
                | ((bytes[offset + 2] & 0xFF) << 8)
                | (bytes[offset + 3] & 0xFF);
    }

    @Override
    public <E> DataResult<Collection<E>> decodeCollection(byte[] input, Function<? super byte[], ? extends DataResult<E>> valueDecoder) {
        int size = bytesToInt(input, 0);
        List<E> result = new ArrayList<>();
        int offset = 4;

        for (int i = 0; i < size; i++) {
            if (offset + 4 > input.length)
                break;

            int length = bytesToInt(input, offset);
            offset += 4;
            byte[] data = new byte[length];
            System.arraycopy(input, offset, data, 0, length);
            offset += length;
            valueDecoder.apply(data).ifSuccess(result::add);
        }

        return new DataResult.Success<>(List.copyOf(result));
    }

    @Override
    public <K, V> DataResult<Map<K, V>> decodeMap(byte[] input, Function<? super byte[], ? extends DataResult<K>> keyDecoder, BiFunction<? super K, ? super byte[], ? extends DataResult<V>> valueDecoder) {
        int size = bytesToInt(input);
        Map<K, V> map = new LinkedHashMap<>();
        int offset = 4;

        for (int i = 0; i < size; i++) {
            if (offset + 4 > input.length)
                break;

            int keyLength = bytesToInt(input, offset);
            offset += 4;
            byte[] keyData = new byte[keyLength];
            System.arraycopy(input, offset, keyData, 0, keyLength);
            offset += keyLength;
            int valueLength = bytesToInt(input, offset);
            offset += 4;
            byte[] valueData = new byte[valueLength];
            System.arraycopy(input, offset, valueData, 0, valueLength);
            offset += valueLength;
            K key = keyDecoder.apply(keyData).getOrThrow();
            map.put(key, valueDecoder.apply(key, valueData).getOrThrow());
        }

        return new DataResult.Success<>(Map.copyOf(map));
    }

    @Override
    public <F, S> DataResult<Pair<F, S>> decodePair(byte[] input, Function<? super byte[], ? extends DataResult<F>> firstDecoder, Function<? super byte[], ? extends DataResult<S>> secondDecoder) {
        if (input.length < 8)
            return new DataResult.Error<>("Malformed byte pair " + Arrays.toString(input) + "!", new IllegalArgumentException());
        else {
            int firstLength = bytesToInt(input, 0);
            byte[] fD = new byte[firstLength];
            System.arraycopy(input, 4, fD, 0, firstLength);
            DataResult<F> f = firstDecoder.apply(fD);
            int secondLength = bytesToInt(input, firstLength + 4);
            byte[] sD = new byte[secondLength];
            System.arraycopy(input, firstLength + 8, sD, 0, secondLength);
            DataResult<S> s = secondDecoder.apply(sD);

            if (f.isSuccess() && s.isSuccess())
                return new DataResult.Success<>(new Pair<>(f.getOrThrow(), s.getOrThrow()));
            else
                return new DataResult.Error<>("Failed to decode pair " + Arrays.toString(input) + "!", new IllegalArgumentException());
        }
    }
}
