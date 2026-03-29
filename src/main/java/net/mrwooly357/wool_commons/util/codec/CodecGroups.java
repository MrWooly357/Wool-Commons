package net.mrwooly357.wool_commons.util.codec;

import net.mrwooly357.wool_commons.util.codec.custom.Group1Codec;
import net.mrwooly357.wool_commons.util.codec.custom.Group2Codec;
import net.mrwooly357.wool_commons.util.codec.custom.Group3Codec;
import net.mrwooly357.wool_commons.util.codec.custom.Group4Codec;
import net.mrwooly357.wool_commons.util.function.custom.QuadFunction;
import net.mrwooly357.wool_commons.util.function.custom.TriFunction;

import java.util.function.BiFunction;
import java.util.function.Function;

public final class CodecGroups {

    private CodecGroups() throws UnsupportedOperationException {
        throw new UnsupportedOperationException("Can't instantiate CodecGroups!");
    }


    public record G1<A, O>(FieldCodec<A, O> field) {


        public Group1Codec<A, O> apply(Function<? super A, ? extends O> constructor) {
            return new Group1Codec<>(field, constructor);
        }

        public <A2> G2<A, A2, O> and(FieldCodec<A2, O> field) {
            return new G2<>(this.field, field);
        }

        public <A2, A3> G3<A, A2, A3, O> and(FieldCodec<A2, O> field1, FieldCodec<A3, O> field2) {
            return new G3<>(field, field1, field2);
        }

        public <A2, A3, A4> G4<A, A2, A3, A4, O> and(FieldCodec<A2, O> field1, FieldCodec<A3, O> field2, FieldCodec<A4, O> field3) {
            return new G4<>(field, field1, field2, field3);
        }

        @Override
        public String toString() {
            return "CodecGroups.G1[field: " + field + "]";
        }
    }


    public record G2<A1, A2, O>(FieldCodec<A1, O> field1, FieldCodec<A2, O> field2) {


        public Group2Codec<A1, A2, O> apply(BiFunction<? super A1, ? super A2, ? extends O> constructor) {
            return new Group2Codec<>(field1, field2, constructor);
        }

        public <A3> G3<A1, A2, A3, O> and(FieldCodec<A3, O> field) {
            return new G3<>(field1, field2, field);
        }

        public <A3, A4> G4<A1, A2, A3, A4, O> and(FieldCodec<A3, O> field1, FieldCodec<A4, O> field2) {
            return new G4<>(this.field1, this.field2, field1, field2);
        }

        public G1<A1, O> withoutSecond() {
            return new G1<>(field1);
        }

        public G1<A2, O> withoutFirst() {
            return new G1<>(field2);
        }

        @Override
        public String toString() {
            return "CodecGroups.G2[field_1: " + field1
                    + ", field_2: " + field2 + "]";
        }
    }


    public record G3<A1, A2, A3, O>(FieldCodec<A1, O> field1, FieldCodec<A2, O> field2, FieldCodec<A3, O> field3) {


        public Group3Codec<A1, A2, A3, O> apply(TriFunction<? super A1, ? super A2, ? super A3, ? extends O> constructor) {
            return new Group3Codec<>(field1, field2, field3, constructor);
        }

        public <A4> G4<A1, A2, A3, A4, O> and(FieldCodec<A4, O> field) {
            return new G4<>(field1, field2, field3, field);
        }

        public G2<A1, A2, O> withoutThird() {
            return new G2<>(field1, field2);
        }

        public G2<A1, A3, O> withoutSecond() {
            return new G2<>(field1, field3);
        }

        public G2<A2, A3, O> withoutFirst() {
            return new G2<>(field2, field3);
        }

        @Override
        public String toString() {
            return "CodecGroups.G3[field_1: " + field1
                    + ", field_2: " + field2
                    + ", field_3: " + field3 + "]";
        }
    }


    public record G4<A1, A2, A3, A4, O>(FieldCodec<A1, O> field1, FieldCodec<A2, O> field2, FieldCodec<A3, O> field3, FieldCodec<A4, O> field4) {


        public Group4Codec<A1, A2, A3, A4, O> apply(QuadFunction<? super A1, ? super A2, ? super A3, ? super A4, ? extends O> constructor) {
            return new Group4Codec<>(field1, field2, field3, field4, constructor);
        }

        public G3<A1, A2, A3, O> withoutFourth() {
            return new G3<>(field1, field2, field3);
        }

        public G3<A1, A2, A4, O> withoutThird() {
            return new G3<>(field1, field2, field4);
        }

        public G3<A1, A3, A4, O> withoutSecond() {
            return new G3<>(field1, field3, field4);
        }

        public G3<A2, A3, A4, O> withoutFirst() {
            return new G3<>(field2, field3, field4);
        }

        @Override
        public String toString() {
            return "CodecGroups.G4[field_1: " + field1
                    + ", field_2: " + field2
                    + ", field_3: " + field3
                    + ", field_4: " + field4 + "]";
        }
    }
}
