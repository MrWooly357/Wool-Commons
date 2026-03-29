package net.mrwooly357.wool_commons.util.codec.decoder.custom;

import net.mrwooly357.wool_commons.util.codec.DataResult;
import net.mrwooly357.wool_commons.util.codec.decoder.Decoder;
import net.mrwooly357.wool_commons.util.codec.operations.CodecOperations;

import java.util.Map;

public record MapDecoder<K, V>(Decoder<K> keyDecoder, Decoder<V> valueDecoder) implements Decoder<Map<K, V>> {


    @Override
    public <R> DataResult<Map<K, V>> decode(CodecOperations<R> operations, R input) {
        return operations.decodeMap(input, r -> keyDecoder.decode(operations, r), r -> valueDecoder.decode(operations, r));
    }

    @Override
    public String toString() {
        return "MapDecoder[key_decoder: " + keyDecoder
                + ", value_decoder: " + valueDecoder + "]";
    }
}
