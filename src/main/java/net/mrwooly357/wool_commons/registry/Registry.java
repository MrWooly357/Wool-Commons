package net.mrwooly357.wool_commons.registry;

import net.mrwooly357.wool_commons.util.Id;
import net.mrwooly357.wool_commons.util.codec.Codec;
import net.mrwooly357.wool_commons.util.codec.Codecs;
import net.mrwooly357.wool_commons.util.function.Functions;
import net.mrwooly357.wool_commons.util.iterator.ImmutableIterator;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

public interface Registry<F extends RegisteredFeature<F>> extends Iterable<F> {


    Optional<F> get(Key<? extends F> key);

    Stream<F> stream();

    Lookup<F> lookup();


    final class Key<F extends RegisteredFeature<F>> {

        public static final String DELIMITER = "/";
        public static final Codec<Key<?>> CODEC = Codecs.STRING
                .map(Functions.identified(Key::toString, Key.class, String.class), Functions.identified(s -> {
                    Id[] split = Arrays.stream(s.split(DELIMITER)).map(Id::of).toArray(Id[]::new);

                    return of(split[0], split[1]);
                }, "String", "Registry.Key"));
        private static final Map<String, Key<?>> INSTANCES = new ConcurrentHashMap<>();

        private final Id root;
        private final Id id;

        private Key(Id root, Id id) {
            this.root = root;
            this.id = id;
        }


        @SuppressWarnings("unchecked")
        public static <F extends RegisteredFeature<F>> Key<F> of(Id root, Id id) {
            return (Key<F>) INSTANCES.computeIfAbsent(root + DELIMITER + id, _ -> new Key<>(root, id));
        }

        public Id root() {
            return root;
        }

        public Id id() {
            return id;
        }

        @Override
        public int hashCode() {
            return Objects.hash(root, id);
        }

        @Override
        public boolean equals(Object other) {
            return super.equals(other) || (other instanceof Key<?> key
                    && root.equals(key.root)
                    && id.equals(key.id));
        }

        @Override
        public String toString() {
            return root + DELIMITER + id;
        }
    }


    @FunctionalInterface
    interface Lookup<F extends RegisteredFeature<F>> extends Iterable<F> {


        Map<Key<? extends F>, F> values();

        default Stream<F> stream() {
            return values().values().stream();
        }

        @SuppressWarnings("unchecked")
        @Override
        default Iterator<F> iterator() {
            return (Iterator<F>) (Iterator<?>) new ImmutableIterator<>(values().values().toArray());
        }

        @Override
        default Spliterator<F> spliterator() {
            return Spliterators.spliterator(iterator(), values().size(), Spliterator.SIZED | Spliterator.NONNULL | Spliterator.IMMUTABLE | Spliterator.SUBSIZED);
        }
    }
}
