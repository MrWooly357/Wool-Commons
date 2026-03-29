package net.mrwooly357.wool_commons.util;

import java.util.function.Consumer;
import java.util.function.Function;

public sealed interface Either<L, R> {


    boolean isLeft();

    boolean isRight();

    <A> A to(Function<? super L, ? extends A> leftMapper, Function<? super R, ? extends A> rightMapper);

    <OL> Either<OL, R> mapLeft(Function<? super L, ? extends OL> mapper);

    <OR> Either<L, OR> mapRight(Function<? super R, ? extends OR> mapper);

    <OL, OR> Either<OL, OR> map(Function<? super L, ? extends OL> leftMapper, Function<? super R, ? extends OR> rightMapper);

    Either<L, R> ifLeft(Consumer<? super L> action);

    Either<L, R> ifRight(Consumer<? super R> action);


    record Left<L, R>(L value) implements Either<L, R> {


        @Override
        public boolean isLeft() {
            return true;
        }

        @Override
        public boolean isRight() {
            return false;
        }

        @Override
        public <A> A to(Function<? super L, ? extends A> leftMapper, Function<? super R, ? extends A> rightMapper) {
            return leftMapper.apply(value);
        }

        @Override
        public <OL, OR> Either<OL, OR> map(Function<? super L, ? extends OL> leftMapper, Function<? super R, ? extends OR> rightMapper) {
            return new Left<>(leftMapper.apply(value));
        }

        @Override
        public <OL> Either<OL, R> mapLeft(Function<? super L, ? extends OL> mapper) {
            return new Left<>(mapper.apply(value));
        }

        @Override
        public <OR> Either<L, OR> mapRight(Function<? super R, ? extends OR> mapper) {
            return new Left<>(value);
        }

        @Override
        public Either<L, R> ifLeft(Consumer<? super L> action) {
            action.accept(value);

            return this;
        }

        @Override
        public Either<L, R> ifRight(Consumer<? super R> action) {
            return this;
        }

        @Override
        public String toString() {
            return "Either.Left[value: " + value + "]";
        }
    }


    record Right<L, R>(R value) implements Either<L, R> {


        @Override
        public boolean isLeft() {
            return false;
        }

        @Override
        public boolean isRight() {
            return true;
        }

        @Override
        public <A> A to(Function<? super L, ? extends A> leftMapper, Function<? super R, ? extends A> rightMapper) {
            return rightMapper.apply(value);
        }

        @Override
        public <OL, OR> Either<OL, OR> map(Function<? super L, ? extends OL> leftMapper, Function<? super R, ? extends OR> rightMapper) {
            return new Right<>(rightMapper.apply(value));
        }

        @Override
        public <OL> Either<OL, R> mapLeft(Function<? super L, ? extends OL> mapper) {
            return new Right<>(value);
        }

        @Override
        public <OR> Either<L, OR> mapRight(Function<? super R, ? extends OR> mapper) {
            return new Right<>(mapper.apply(value));
        }

        @Override
        public Either<L, R> ifLeft(Consumer<? super L> action) {
            return this;
        }

        @Override
        public Either<L, R> ifRight(Consumer<? super R> action) {
            action.accept(value);

            return this;
        }

        @Override
        public String toString() {
            return "Either.Right[value: " + value + "]";
        }
    }
}
