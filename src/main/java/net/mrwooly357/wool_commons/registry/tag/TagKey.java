package net.mrwooly357.wool_commons.registry.tag;

import net.mrwooly357.wool_commons.registry.RegisteredFeature;
import net.mrwooly357.wool_commons.util.Id;
import net.mrwooly357.wool_commons.util.codec.Codec;
import net.mrwooly357.wool_commons.util.codec.Codecs;
import net.mrwooly357.wool_commons.util.function.Functions;

public record TagKey<F extends RegisteredFeature<F>>(Id id) {

    public static final Codec<TagKey<?>> CODEC = Codecs.STRING.map(
            Functions.identified(TagKey::toString, "TagKey<F>", "String"),
            Functions.identified(s -> new TagKey<>(Id.of(s)), "String", "TagKey<F>")
    );
    public static final String PREFIX = "#";


    @Override
    public String toString() {
        return PREFIX + id;
    }
}
