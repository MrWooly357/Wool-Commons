package net.mrwooly357.wool_commons.util;

import net.mrwooly357.wool_commons.util.codec.Codec;
import net.mrwooly357.wool_commons.util.codec.Codecs;
import net.mrwooly357.wool_commons.util.function.Functions;

import java.util.Objects;

public final class Id {

    public static final String DELIMITER = ":";
    public static final String ALLOWED_SYMBOLS = "[a-zA-Z0-9_]+";
    public static final Codec<Id> CODEC = Codecs.STRING
            .validated(s -> {
                String[] split = s.split(DELIMITER, 2);

                return split[0].matches(ALLOWED_SYMBOLS) && split[1].matches(ALLOWED_SYMBOLS);
            }, () -> "Id's namespace and path can only consist of " + ALLOWED_SYMBOLS + " and cannot be blank!")
            .map(Functions.identified(Id::toString, Id.class, String.class), Functions.identified(Id::of, String.class, Id.class));

    private final String namespace;
    private final String path;

    private Id(String namespace, String path) {
        this.namespace = namespace;
        this.path = path;
    }


    public static Id of(String id) {
        String[] split = id.split(DELIMITER, 2);

        return of(split[0], split[1]);
    }

    public static Id of(String namespace, String path) {
        if (namespace.matches(ALLOWED_SYMBOLS) && path.matches(ALLOWED_SYMBOLS))
            return new Id(namespace, path);
        else
            throw new IllegalArgumentException("Id's namespace and path can only consist of " + ALLOWED_SYMBOLS + " and cannot be blank!");
    }

    public String namespace() {
        return namespace;
    }

    public String path() {
        return path;
    }

    public Id prefixed(String prefix) {
        return of(namespace, prefix + path);
    }

    public Id suffixed(String suffix) {
        return of(namespace, path + suffix);
    }

    @Override
    public int hashCode() {
        return Objects.hash(namespace, path);
    }

    @Override
    public boolean equals(Object other) {
        return super.equals(other) || (other instanceof Id id
                && namespace.equals(id.namespace)
                && path.equals(id.path));
    }

    @Override
    public String toString() {
        return namespace + DELIMITER + path;
    }
}
