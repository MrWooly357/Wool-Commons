package net.mrwooly357.wool_commons.util.codec.decoder;

import net.mrwooly357.wool_commons.util.codec.operations.CodecOperations;
import net.mrwooly357.wool_commons.util.codec.DataResult;

import java.util.function.Predicate;
import java.util.function.Supplier;

public record ValidatedDecoder<A>(Decoder<A> decoder, Predicate<? super A> predicate, Supplier<String> message) implements Decoder<A> {


    @Override
    public <R> DataResult<A> decode(CodecOperations<R> operations, R input) {
        return decoder.decode(operations, input).validate(predicate, message.get());
    }

    @Override
    public String toString() {
        return "ValidatedDecoder[decoder: " + decoder
                + ", predicate: " + predicate
                + ", message: " + message + "]";
    }
}
