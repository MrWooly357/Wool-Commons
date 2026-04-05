package net.mrwooly357.wool_commons.registry;

public interface MutableRegistry<F extends RegisteredFeature<F>> extends Registry<F> {


    F register(F feature);
}
