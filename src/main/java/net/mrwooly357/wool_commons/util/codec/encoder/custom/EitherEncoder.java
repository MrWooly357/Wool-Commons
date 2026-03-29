package net.mrwooly357.wool_commons.util.codec.encoder.custom;

import net.mrwooly357.wool_commons.util.Either;
import net.mrwooly357.wool_commons.util.codec.operations.CodecOperations;
import net.mrwooly357.wool_commons.util.codec.DataResult;
import net.mrwooly357.wool_commons.util.codec.encoder.Encoder;

public record EitherEncoder<L, R>(Encoder<L> left, Encoder<R> right) implements Encoder<Either<L, R>> {


    @Override
    public <R1> DataResult<R1> encode(CodecOperations<R1> operations, Either<L, R> input) {
        return input.to(l -> left.encode(operations, l), r -> right.encode(operations, r));
    }

    @Override
    public String toString() {
        return "EitherEncoder[left: " + left
                + ", right: " + right + "]";
    }
}
