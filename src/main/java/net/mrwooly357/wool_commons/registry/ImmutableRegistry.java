package net.mrwooly357.wool_commons.registry;

import net.mrwooly357.wool_commons.util.iterator.ImmutableIterator;

import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Stream;

public class ImmutableRegistry<F extends RegisteredFeature<F>> implements Registry<F> {

    private final Map<Key<F>, F> values;

    @SuppressWarnings("unchecked")
    public ImmutableRegistry(Consumer<Builder<F>> builder) {
        Map<Key<F>, F> m = new HashMap<>();
        builder.accept(feature -> m.computeIfAbsent((Key<F>) feature.getKey(), _ -> feature));

        this.values = Map.copyOf(m);
    }


    @Override
    public Optional<F> get(Key<? extends F> key) {
        return Optional.ofNullable(values.get(key));
    }

    @Override
    public Stream<F> stream() {
        return values.values().stream();
    }

    @SuppressWarnings("unchecked")
    @Override
    public Lookup<F> lookup() {
        return () -> (Map<Key<? extends F>, F>) (Map<?, ?>) values;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Iterator<F> iterator() {
        return (Iterator<F>) (Iterator<?>) new ImmutableIterator<>(values.values().toArray());
    }

    @Override
    public Spliterator<F> spliterator() {
        return Spliterators.spliterator(iterator(), values.size(), Spliterator.SIZED | Spliterator.NONNULL | Spliterator.IMMUTABLE | Spliterator.SUBSIZED);
    }


    @FunctionalInterface
    public interface Builder<F> {


        F register(F feature);
    }
}
