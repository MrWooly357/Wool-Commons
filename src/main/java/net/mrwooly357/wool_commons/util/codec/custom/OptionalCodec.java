package net.mrwooly357.wool_commons.util.codec.custom;

import net.mrwooly357.wool_commons.util.codec.Codec;
import net.mrwooly357.wool_commons.util.codec.operations.CodecOperations;
import net.mrwooly357.wool_commons.util.codec.DataResult;
import net.mrwooly357.wool_commons.util.codec.decoder.custom.OptionalDecoder;
import net.mrwooly357.wool_commons.util.codec.encoder.custom.OptionalEncoder;

import java.util.Optional;

public record OptionalCodec<A>(OptionalEncoder<A> encoder, OptionalDecoder<A> decoder) implements Codec<Optional<A>> {


    @Override
    public <R> DataResult<R> encode(CodecOperations<R> operations, Optional<A> input) {
        return encoder.encode(operations, input);
    }

    @Override
    public <R> DataResult<Optional<A>> decode(CodecOperations<R> operations, R input) {
        return decoder.decode(operations, input);
    }

    @Override
    public String toString() {
        return "OptionalCodec[encoder: " + encoder
                + ", decoder: " + decoder + "]";
    }
}
