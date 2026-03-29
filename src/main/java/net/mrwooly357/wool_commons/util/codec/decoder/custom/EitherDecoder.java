package net.mrwooly357.wool_commons.util.codec.decoder.custom;

import net.mrwooly357.wool_commons.util.Either;
import net.mrwooly357.wool_commons.util.codec.operations.CodecOperations;
import net.mrwooly357.wool_commons.util.codec.DataResult;
import net.mrwooly357.wool_commons.util.codec.decoder.Decoder;

public record EitherDecoder<L, R>(Decoder<L> left, Decoder<R> right) implements Decoder<Either<L, R>> {


    @Override
    public <R1> DataResult<Either<L, R>> decode(CodecOperations<R1> operations, R1 input) {
        DataResult<Either<L, R>> leftResult = left.decode(operations, input).map(Either.Left::new);
        DataResult<Either<L, R>> rightResult = right.decode(operations, input).map(Either.Right::new);

        if (leftResult.isSuccess()) {

            if (rightResult.isSuccess())
                throw new IllegalStateException("Failed to decode " + input + " as EitherDecoder does not allow both types present!");

            return leftResult;
        } else
            return rightResult;
    }

    @Override
    public String toString() {
        return "EitherDecoder[left: " + left
                + ", right: " + right + "]";
    }
}
