package net.mrwooly357.wool_commons.util.codec.decoder;

import net.mrwooly357.wool_commons.util.codec.DataResult;
import net.mrwooly357.wool_commons.util.codec.operations.CodecOperations;

import java.util.function.Function;

public record DispatchedDecoder<T, A>(Decoder<T> typeDecoder, Function<T, Decoder<A>> valueDecoder) implements Decoder<A> {


    @Override
    public <R> DataResult<A> decode(CodecOperations<R> operations, R input) {
        return operations.decodeMap(input, t -> typeDecoder.decode(operations, t), (t, v) -> valueDecoder.apply(t).decode(operations, v)).map(m -> m.values().iterator().next());
    }

    @Override
    public String toString() {
        return "DispatchedDecoder[type_decoder: " + typeDecoder
                + ", value_decoder: " + valueDecoder + "]";
    }
}
