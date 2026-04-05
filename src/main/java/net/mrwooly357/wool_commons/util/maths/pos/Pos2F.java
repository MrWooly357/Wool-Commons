package net.mrwooly357.wool_commons.util.maths.pos;

import net.mrwooly357.wool_commons.util.Pair;
import net.mrwooly357.wool_commons.util.codec.Codec;
import net.mrwooly357.wool_commons.util.codec.Codecs;
import net.mrwooly357.wool_commons.util.function.Functions;

public record Pos2F(float x, float y) {

    public static final Codec<Pos2F> CODEC = Codec.pair(Codecs.FLOAT, Codecs.FLOAT)
            .map(
                    Functions.identified(pos -> new Pair<>(pos.x, pos.y), "Pos2F", "Pair<Float, Float>"),
                    Functions.identified(pair -> new Pos2F(pair.first(), pair.second()), "Pair<Float, Float>", "Pos2F")
            );


    public Pos2F add(float x, float y) {
        return new Pos2F(x, y);
    }

    public float squaredDistanceTo(Pos2F other) {
        float dx = other.x - x;
        float dy = other.y - y;

        return dx * dx + dy * dy;
    }

    @Override
    public String toString() {
        return "(" + x + "; " + y + ")";
    }
}
