package net.mrwooly357.wool_commons.util.codec.encoder;

import net.mrwooly357.wool_commons.util.codec.DataResult;
import net.mrwooly357.wool_commons.util.codec.operations.CodecOperations;

import java.util.Map;
import java.util.function.Function;

public record BoundedMapEncoder<K, V>(Encoder<K> keyEncoder, Function<K, Encoder<V>> valueEncoder) implements Encoder<Map<K, V>> {


    @Override
    public <R> DataResult<R> encode(CodecOperations<R> operations, Map<K, V> input) {
        return operations.encodeMap(input, k -> keyEncoder.encode(operations, k), (k, v) -> valueEncoder.apply(k).encode(operations, v));
    }

    @Override
    public String toString() {
        return "BoundedMapEncoder[key_encoder: " + keyEncoder
                + ", value_encoder: " + valueEncoder + "]";
    }
}
