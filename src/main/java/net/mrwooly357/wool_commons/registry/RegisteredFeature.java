package net.mrwooly357.wool_commons.registry;

import net.mrwooly357.wool_commons.registry.tag.TagKey;

public interface RegisteredFeature<F extends RegisteredFeature<F>> {


    Registry.Key<? extends F> getKey();

    @SuppressWarnings("unchecked")
    default boolean isIn(RegistryManager registryManager, TagKey<? extends F> tag) {
        return registryManager.getTag((TagKey<F>) tag).contains(getKey());
    }
}
