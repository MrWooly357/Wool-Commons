package net.mrwooly357.wool_commons.util.codec;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public sealed interface DataResult<A> {


    boolean isSuccess();

    boolean isError();

    Optional<A> get();

    A getOrElse(Supplier<A> fallback);

    A getOrThrow();

    DataResult<A> fallbacked(A fallback);

    <O> DataResult<O> map(Function<? super A, ? extends O> mapper);

    DataResult<A> ifSuccess(Consumer<? super A> action);

    DataResult<A> ifError(Consumer<? super Error<?>> action);

    DataResult<A> validate(Predicate<? super A> predicate, String message);


    record Success<A>(A result) implements DataResult<A> {


        @Override
        public boolean isSuccess() {
            return true;
        }

        @Override
        public boolean isError() {
            return false;
        }

        @Override
        public Optional<A> get() {
            return Optional.of(result);
        }

        @Override
        public A getOrElse(Supplier<A> fallback) {
            return result;
        }

        @Override
        public A getOrThrow() {
            return result;
        }

        @Override
        public DataResult<A> fallbacked(A fallback) {
            return this;
        }

        @Override
        public <O> DataResult<O> map(Function<? super A, ? extends O> mapper) {
            return new Success<>(mapper.apply(result));
        }

        @Override
        public DataResult<A> ifSuccess(Consumer<? super A> action) {
            action.accept(result);

            return this;
        }

        @Override
        public DataResult<A> ifError(Consumer<? super Error<?>> action) {
            return this;
        }

        @Override
        public DataResult<A> validate(Predicate<? super A> predicate, String message) {
            if (predicate.test(result))
                return this;
            else
                return new Error<>(message, new IllegalArgumentException());
        }

        @Override
        public String toString() {
            return "DataResult.Success[result: " + result + "]";
        }
    }


    record Error<A>(String message, Throwable cause) implements DataResult<A> {


        @Override
        public boolean isSuccess() {
            return false;
        }

        @Override
        public boolean isError() {
            return true;
        }

        @Override
        public Optional<A> get() {
            return Optional.empty();
        }

        @Override
        public A getOrElse(Supplier<A> fallback) {
            return fallback.get();
        }

        @Override
        public A getOrThrow() {
            throw new NullPointerException("No result!");
        }

        @Override
        public DataResult<A> fallbacked(A fallback) {
            return new Success<>(fallback);
        }

        @Override
        public <O> DataResult<O> map(Function<? super A, ? extends O> mapper) {
            return new Error<>(message, cause);
        }

        @Override
        public DataResult<A> ifSuccess(Consumer<? super A> action) {
            return this;
        }

        @Override
        public DataResult<A> ifError(Consumer<? super Error<?>> action) {
            action.accept(this);

            return this;
        }

        @Override
        public DataResult<A> validate(Predicate<? super A> predicate, String message) {
            return this;
        }

        @Override
        public String toString() {
            return "DataResult.Error[message: " + message
                    + ", cause: " + cause + "]";
        }
    }
}
