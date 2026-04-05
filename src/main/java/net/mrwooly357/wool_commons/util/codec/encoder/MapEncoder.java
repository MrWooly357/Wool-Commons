package net.mrwooly357.wool_commons.util.codec.encoder;

import net.mrwooly357.wool_commons.util.codec.DataResult;
import net.mrwooly357.wool_commons.util.codec.operations.CodecOperations;

import java.util.Map;

public record MapEncoder<K, V>(Encoder<K> keyEncoder, Encoder<V> valueEncoder) implements Encoder<Map<K, V>> {


    @Override
    public <R> DataResult<R> encode(CodecOperations<R> operations, Map<K, V> input) {
        return operations.encodeMap(input, k -> keyEncoder.encode(operations, k), v -> valueEncoder.encode(operations, v));
    }

    @Override
    public String toString() {
        return "MapEncoder[key_encoder: " + keyEncoder
                + ", value_encoder: " + valueEncoder + "]";
    }
}
