package net.mrwooly357.wool_commons.registry;

import net.mrwooly357.wool_commons.registry.tag.Tag;
import net.mrwooly357.wool_commons.registry.tag.TagKey;
import net.mrwooly357.wool_commons.util.Id;

public interface RegistryManager {


    <F extends RegisteredFeature<F>> Registry<F> getRegistry(Id id);

    <F extends RegisteredFeature<F>> Tag<F> getTag(TagKey<F> key);
}
