package net.mrwooly357.wool_commons.registry.tag;

import net.mrwooly357.wool_commons.registry.RegisteredFeature;
import net.mrwooly357.wool_commons.registry.Registry;
import net.mrwooly357.wool_commons.util.codec.Codec;
import net.mrwooly357.wool_commons.util.function.Functions;

import java.util.Set;
import java.util.function.Function;

public final class Tag<F extends RegisteredFeature<F>> {

    @SuppressWarnings("unchecked")
    public static final Codec<Tag<?>> CODEC = Codec.set(Registry.Key.CODEC, Set::copyOf)
            .map(
                    (Function<? super Tag<?>, ? extends Set<Registry.Key<?>>>) Functions.identified(tag -> ((Tag<?>) tag).keys, "Tag", "Set"),
                    Functions.identified(Tag::new, "Set", "Tag")
            );

    private final Set<Registry.Key<F>> keys;

    @SuppressWarnings("unchecked")
    private Tag(Set<Registry.Key<?>> keys) {
        this.keys = (Set<Registry.Key<F>>) (Object) keys;
    }


    public boolean contains(Registry.Key<? extends F> key) {
        return keys.contains(key);
    }

    public boolean contains(F feature) {
        return contains(feature.getKey());
    }

    @Override
    public int hashCode() {
        return keys.hashCode();
    }

    @Override
    public boolean equals(Object other) {
        return super.equals(other) || (other instanceof Tag<?> tag
                && keys.equals(tag.keys));
    }

    @Override
    public String toString() {
        return "Tag[keys: " + keys + "]";
    }
}
