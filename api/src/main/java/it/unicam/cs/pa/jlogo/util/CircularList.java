package it.unicam.cs.pa.jlogo.util;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.function.BiPredicate;

/**
 * A two-way linked list where all elements are logically connected by the given {@link BiPredicate}.
 * The list allows its elements to circle back to the first one, once this happens no more elements
 * can to be added
 *
 * @param <E> the type of the elements in this list
 */
public class CircularList<E> implements Collection<E> {

    private final BiPredicate<E, E> connectPredicate;

    private final Node<E> first;
    private Node<E> last;


    public CircularList(E first, BiPredicate<E, E> connectPredicate) {
        this.connectPredicate = connectPredicate;
        this.first = new Node<E>(first, null);
        this.last = this.first;
    }


    public boolean isComplete() {
        return last.next == first;
    }

    @Override
    public int size() {
        int size = 0;
        for (E ignored : this) {
            size++;
        }
        return size;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public boolean contains(Object o) {
        for (E e : this) {
            if (Objects.equals(o, e))
                return true;
        }
        return false;
    }

    @Override
    public Iterator<E> iterator() {
        return new CircularIterator();
    }

    @Override
    public Object[] toArray() {
        Object[] array = new Object[size()];
        Iterator<E> iterator = iterator();
        for (int i = 0; i < array.length; i++) {
            array[i] = iterator.next();
        }
        return array;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T[] toArray(T[] a) {
        if (a.length < size()) {
            a = (T[]) Array.newInstance(a.getClass().getComponentType(), size());
        }

        int i = 0;
        Object[] result = a;
        for (E e : this) result[i++] = e;

        if (a.length > size())
            a[size()] = null;

        return a;
    }

    public boolean add(E e) {
        if (!addCheck(e))
            return false;

        Node<E> newNode = new Node<>(e, last);
        last.next = newNode;
        last = newNode;
        if (connectPredicate.test(last.elem, first.elem))
            last.next = first;
        return true;
    }

    @Override
    public boolean remove(Object o) {
        return throwUnsupported();
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        for (Object o : c) {
            if (!this.contains(o))
                return false;
        }
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        return throwUnsupported();
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return throwUnsupported();
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return throwUnsupported();
    }

    @Override
    public void clear() {
        throwUnsupported();
    }

    private boolean addCheck(E elem) {
        if (isComplete())
            throw new IllegalStateException("Attempt to add an element in a complete list");

        return connectPredicate.test(elem, last.elem);
    }

    private boolean throwUnsupported() {
        throw new UnsupportedOperationException("This operation is not supported");
    }


    private static class Node<E> {

        final E elem;
        Node<E> next;
        Node<E> prev;

        public Node(E elem, Node<E> prev) {
            this.elem = elem;
            this.prev = prev;
        }
    }

    private class CircularIterator implements Iterator<E> {

        Node<E> current = first;
        int returned = 0;


        @Override
        public boolean hasNext() {
            return returned != 0 && current != first;
        }

        @Override
        public E next() {
            if (current.next == null || (returned != 0 && current == first))
                throw new NoSuchElementException("The iterator has no more elements");

            E result = current.elem;
            current = current.next;
            returned++;
            return result;
        }
    }
}
