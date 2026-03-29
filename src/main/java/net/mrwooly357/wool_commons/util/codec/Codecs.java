package net.mrwooly357.wool_commons.util.codec;

import net.mrwooly357.wool_commons.util.codec.operations.CodecOperations;

public final class Codecs {

    public static final Codec<Byte> BYTE = new Codec<>() {


        @Override
        public <R> DataResult<R> encode(CodecOperations<R> operations, Byte input) {
            return operations.encodeByte(input);
        }

        @Override
        public <R> DataResult<Byte> decode(CodecOperations<R> operations, R input) {
            return operations.decodeByte(input);
        }

        @Override
        public String toString() {
            return "ByteCodec[]";
        }
    };
    public static final Codec<Short> SHORT = new Codec<>() {


        @Override
        public <R> DataResult<R> encode(CodecOperations<R> operations, Short input) {
            return operations.encodeShort(input);
        }

        @Override
        public <R> DataResult<Short> decode(CodecOperations<R> operations, R input) {
            return operations.decodeShort(input);
        }

        @Override
        public String toString() {
            return "ShortCodec[]";
        }
    };
    public static final Codec<Integer> INT = new Codec<>() {


        @Override
        public <R> DataResult<R> encode(CodecOperations<R> operations, Integer input) {
            return operations.encodeInt(input);
        }

        @Override
        public <R> DataResult<Integer> decode(CodecOperations<R> operations, R input) {
            return operations.decodeInt(input);
        }

        @Override
        public String toString() {
            return "IntCodec[]";
        }
    };
    public static final Codec<Long> LONG = new Codec<>() {


        @Override
        public <R> DataResult<R> encode(CodecOperations<R> operations, Long input) {
            return operations.encodeLong(input);
        }

        @Override
        public <R> DataResult<Long> decode(CodecOperations<R> operations, R input) {
            return operations.decodeLong(input);
        }

        @Override
        public String toString() {
            return "LongCodec[]";
        }
    };
    public static final Codec<Float> FLOAT = new Codec<>() {


        @Override
        public <R> DataResult<R> encode(CodecOperations<R> operations, Float input) {
            return operations.encodeFloat(input);
        }

        @Override
        public <R> DataResult<Float> decode(CodecOperations<R> operations, R input) {
            return operations.decodeFloat(input);
        }

        @Override
        public String toString() {
            return "FloatCodec[]";
        }
    };
    public static final Codec<Double> DOUBLE = new Codec<>() {


        @Override
        public <R> DataResult<R> encode(CodecOperations<R> operations, Double input) {
            return operations.encodeDouble(input);
        }

        @Override
        public <R> DataResult<Double> decode(CodecOperations<R> operations, R input) {
            return operations.decodeDouble(input);
        }

        @Override
        public String toString() {
            return "DoubleCodec[]";
        }
    };
    public static final Codec<Boolean> BOOLEAN = new Codec<>() {


        @Override
        public <R> DataResult<R> encode(CodecOperations<R> operations, Boolean input) {
            return operations.encodeBoolean(input);
        }

        @Override
        public <R> DataResult<Boolean> decode(CodecOperations<R> operations, R input) {
            return operations.decodeBoolean(input);
        }

        @Override
        public String toString() {
            return "BooleanCodec[]";
        }
    };
    public static final Codec<Character> CHAR = new Codec<>() {


        @Override
        public <R> DataResult<R> encode(CodecOperations<R> operations, Character input) {
            return operations.encodeChar(input);
        }

        @Override
        public <R> DataResult<Character> decode(CodecOperations<R> operations, R input) {
            return operations.decodeChar(input);
        }

        @Override
        public String toString() {
            return "CharCodec[]";
        }
    };
    public static final Codec<String> STRING = new Codec<>() {


        @Override
        public <R> DataResult<R> encode(CodecOperations<R> operations, String input) {
            return operations.encodeString(input);
        }

        @Override
        public <R> DataResult<String> decode(CodecOperations<R> operations, R input) {
            return operations.decodeString(input);
        }

        @Override
        public String toString() {
            return "StringCodec[]";
        }
    };

    private Codecs() throws UnsupportedOperationException {
        throw new UnsupportedOperationException("Can't instantiate Codecs!");
    }
}
