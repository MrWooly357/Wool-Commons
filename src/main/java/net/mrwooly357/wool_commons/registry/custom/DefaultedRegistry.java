package net.mrwooly357.wool_commons.registry.custom;

import net.mrwooly357.wool_commons.registry.RegisteredFeature;

import java.util.Objects;
import java.util.Optional;

public class DefaultedRegistry<F extends RegisteredFeature<F>> extends SimpleRegistry<F> {

    private final F defaultValue;

    public DefaultedRegistry(F defaultValue) {
        this.defaultValue = defaultValue;
    }


    @Override
    public Optional<F> get(Key<? extends F> key) {
        return Optional.of(values.getOrDefault(key, defaultValue));
    }

    public F getOrDefault(Key<? extends F> key) {
        return values.getOrDefault(key, defaultValue);
    }

    public F defaultValue() {
        return defaultValue;
    }

    @Override
    public int hashCode() {
        return Objects.hash(values, defaultValue);
    }

    @Override
    public boolean equals(Object other) {
        return (this == other) || (other instanceof DefaultedRegistry<?> registry
                && values.equals(registry.values)
                && defaultValue.equals(registry.defaultValue));
    }

    @Override
    public String toString() {
        return "DefaultedRegistry[values: " + values
                + ", default_value: " + defaultValue + "]";
    }
}
