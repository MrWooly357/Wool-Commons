package net.mrwooly357.wool_commons.registry.custom;

import net.mrwooly357.wool_commons.registry.RegisteredFeature;
import net.mrwooly357.wool_commons.registry.Registry;

public interface MutableRegistry<F extends RegisteredFeature<F>> extends Registry<F> {


    F register(F feature);
}
