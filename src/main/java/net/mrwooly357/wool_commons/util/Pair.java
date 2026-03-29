package net.mrwooly357.wool_commons.util;

import java.util.function.Function;

public record Pair<F, S>(F first, S second) {


    public <OF> Pair<OF, S> mapFirst(Function<? super F, ? extends OF> mapper) {
        return new Pair<>(mapper.apply(first), second);
    }

    public <OS> Pair<F, OS> mapSecond(Function<? super S, ? extends OS> mapper) {
        return new Pair<>(first, mapper.apply(second));
    }

    public <OF, OS> Pair<OF, OS> map(Function<? super F, ? extends OF> firstMapper, Function<? super S, ? extends OS> secondMapper) {
        return new Pair<>(firstMapper.apply(first), secondMapper.apply(second));
    }

    @Override
    public String toString() {
        return "(" + first + "; " + second + ")";
    }
}
