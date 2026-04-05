package net.mrwooly357.wool_commons.util.codec.decoder;

import net.mrwooly357.wool_commons.util.codec.DataResult;
import net.mrwooly357.wool_commons.util.codec.operations.CodecOperations;

import java.util.Map;
import java.util.function.Function;

public record BoundedMapDecoder<K, V>(Decoder<K> keyDecoder, Function<K, Decoder<V>> valueDecoder) implements Decoder<Map<K, V>> {


    @Override
    public <R> DataResult<Map<K, V>> decode(CodecOperations<R> operations, R input) {
        return operations.decodeMap(input, r -> keyDecoder.decode(operations, r), (k, r) -> valueDecoder.apply(k).decode(operations, r));
    }

    @Override
    public String toString() {
        return "BoundedMapDecoder[key_decoder: " + keyDecoder
                + ", value_decoder: " + valueDecoder + "]";
    }
}
