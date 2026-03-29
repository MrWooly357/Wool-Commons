package net.mrwooly357.wool_commons.util.codec.custom;

import net.mrwooly357.wool_commons.util.Either;
import net.mrwooly357.wool_commons.util.codec.Codec;
import net.mrwooly357.wool_commons.util.codec.operations.CodecOperations;
import net.mrwooly357.wool_commons.util.codec.DataResult;
import net.mrwooly357.wool_commons.util.codec.decoder.custom.EitherDecoder;
import net.mrwooly357.wool_commons.util.codec.encoder.custom.EitherEncoder;

public record EitherCodec<L, R>(EitherEncoder<L, R> encoder, EitherDecoder<L, R> decoder) implements Codec<Either<L, R>> {


    @Override
    public <R1> DataResult<R1> encode(CodecOperations<R1> operations, Either<L, R> input) {
        return encoder.encode(operations, input);
    }

    @Override
    public <R1> DataResult<Either<L, R>> decode(CodecOperations<R1> operations, R1 input) {
        return decoder.decode(operations, input);
    }

    @Override
    public String toString() {
        return "EitherCodec[encoder: " + encoder
                + ", decoder: " + decoder + "]";
    }
}
