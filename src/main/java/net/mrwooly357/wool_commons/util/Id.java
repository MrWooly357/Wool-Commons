package net.mrwooly357.wool_commons.util;

import java.util.Objects;

public final class Id {

    public static final String DELIMITER = ":";
    public static final String ALLOWED_SYMBOLS = "[a-zA-Z0-9_]+";

    private final String namespace;
    private final String path;

    private Id(String namespace, String path) {
        this.namespace = namespace;
        this.path = path;
    }


    public static Id of(String namespace, String path) {
        if (namespace.matches(ALLOWED_SYMBOLS) && path.matches(ALLOWED_SYMBOLS))
            return new Id(namespace, path);
        else
            throw new IllegalArgumentException("Id can only consist of " + ALLOWED_SYMBOLS + " and cannot be blank!");
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
