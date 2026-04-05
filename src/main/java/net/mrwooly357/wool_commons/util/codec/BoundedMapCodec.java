package net.mrwooly357.wool_commons.util.codec;

import net.mrwooly357.wool_commons.util.codec.decoder.BoundedMapDecoder;
import net.mrwooly357.wool_commons.util.codec.encoder.BoundedMapEncoder;
import net.mrwooly357.wool_commons.util.codec.operations.CodecOperations;

import java.util.Map;

public record BoundedMapCodec<K, V>(BoundedMapEncoder<K, V> encoder, BoundedMapDecoder<K, V> decoder) implements Codec<Map<K, V>> {


    @Override
    public <R> DataResult<R> encode(CodecOperations<R> operations, Map<K, V> input) {
        return encoder.encode(operations, input);
    }

    @Override
    public <R> DataResult<Map<K, V>> decode(CodecOperations<R> operations, R input) {
        return decoder.decode(operations, input);
    }

    @Override
    public String toString() {
        return "BoundedMapCodec[encoder: " + encoder
                + ", decoder: " + decoder + "]";
    }
}
