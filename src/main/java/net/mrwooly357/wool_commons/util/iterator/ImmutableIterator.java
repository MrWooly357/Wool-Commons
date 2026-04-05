package net.mrwooly357.wool_commons.util.iterator;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Objects;

public class ImmutableIterator<E> implements Iterator<E> {

    private final E[] elements;
    private int pointer = -1;

    public ImmutableIterator(E[] elements) {
        this.elements = elements;
    }


    @Override
    public boolean hasNext() {
        return pointer < elements.length - 1;
    }

    @Override
    public E next() {
        pointer++;

        return elements[pointer];
    }

    @Override
    public int hashCode() {
        return Objects.hash(Arrays.hashCode(elements), pointer);
    }

    @Override
    public boolean equals(Object other) {
        return super.equals(other) || (other instanceof ImmutableIterator<?> iterator
                && Arrays.equals(elements, iterator.elements)
                && pointer == iterator.pointer);
    }

    @Override
    public String toString() {
        return "ImmutableIterator[elements: " + Arrays.toString(elements)
                + ", pointer: " + pointer + "]";
    }
}
