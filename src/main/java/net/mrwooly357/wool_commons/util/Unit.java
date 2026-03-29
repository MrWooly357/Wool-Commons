package net.mrwooly357.wool_commons.util;

import net.mrwooly357.wool_commons.util.codec.Codec;

public enum Unit {

    INSTANCE;

    public static final Codec<Unit> CODEC = Codec.unit(INSTANCE);
}
