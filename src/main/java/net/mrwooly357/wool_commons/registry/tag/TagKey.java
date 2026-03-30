package net.mrwooly357.wool_commons.registry.tag;

import net.mrwooly357.wool_commons.registry.RegisteredFeature;
import net.mrwooly357.wool_commons.util.Id;

public record TagKey<F extends RegisteredFeature<F>>(Id id) {

    public static final String PREFIX = "#";


    @Override
    public String toString() {
        return PREFIX + id;
    }
}
