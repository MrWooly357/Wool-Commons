package net.mrwooly357.wool_commons.util.codec.custom;

import net.mrwooly357.wool_commons.util.codec.Codec;
import net.mrwooly357.wool_commons.util.codec.DataResult;
import net.mrwooly357.wool_commons.util.codec.decoder.custom.MapDecoder;
import net.mrwooly357.wool_commons.util.codec.encoder.custom.MapEncoder;
import net.mrwooly357.wool_commons.util.codec.operations.CodecOperations;

import java.util.Map;

public record MapCodec<K, V>(MapEncoder<K, V> encoder, MapDecoder<K, V> decoder) implements Codec<Map<K, V>> {


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
        return "MapCodec[encoder: " + encoder
                + ", decoder: " + decoder + "]";
    }
}
