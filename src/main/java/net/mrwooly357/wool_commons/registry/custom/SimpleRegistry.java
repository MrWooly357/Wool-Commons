package net.mrwooly357.wool_commons.registry.custom;

import net.mrwooly357.wool_commons.registry.RegisteredFeature;
import net.mrwooly357.wool_commons.util.iterator.custom.ImmutableIterator;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

public class SimpleRegistry<F extends RegisteredFeature<F>> implements MutableRegistry<F> {

    protected final Map<Key<F>, F> values = new ConcurrentHashMap<>();


    @SuppressWarnings("unchecked")
    @Override
    public F register(F feature) {
        return values.computeIfAbsent((Key<F>) feature.getKey(), _ -> feature);
    }

    @Override
    public Optional<F> get(Key<? extends F> key) {
        return Optional.ofNullable(values.get(key));
    }

    @Override
    public Stream<F> stream() {
        return values.values().stream();
    }

    @Override
    public Lookup<F> lookup() {
        return () -> Map.copyOf(values);
    }

    @SuppressWarnings("unchecked")
    @Override
    public Iterator<F> iterator() {
        return (Iterator<F>) (Iterator<?>) new ImmutableIterator<>(values.values().toArray());
    }

    @Override
    public Spliterator<F> spliterator() {
        return Spliterators.spliterator(iterator(), values.size(), Spliterator.SIZED | Spliterator.NONNULL | Spliterator.IMMUTABLE | Spliterator.CONCURRENT | Spliterator.SUBSIZED);
    }

    @Override
    public int hashCode() {
        return values.hashCode();
    }

    @Override
    public boolean equals(Object other) {
        return super.equals(other) || (other instanceof SimpleRegistry<?> registry
                && values.equals(registry.values));
    }

    @Override
    public String toString() {
        return "SimpleRegistry[values: " + values + "]";
    }
}
