package it.unicam.cs.pa.jlogo.util;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.function.BiPredicate;

/**
 * A simple linked list where all elements are logically connected by the given {@link BiPredicate}.
 * The list allows its elements to connect back to the first one, once this happens no more elements
 * can be added
 *
 * @param <E> the type of the elements in this list
 */
public class CircularList<E> implements Collection<E> {

    private final BiPredicate<E, E> connectPredicate;

    private Node<E> first;
    private Node<E> last;

    private int size = 0;
    private int modCount = 0;


    /**
     * Constructs an empty list, all elements inserted will have to satisfy the given
     * {@link BiPredicate}
     *
     * @param connectPredicate the predicate that will link all the elements of this list
     *
     * @throws NullPointerException if the predicate is null
     */
    public CircularList(BiPredicate<E, E> connectPredicate) {
        this.connectPredicate = Objects.requireNonNull(connectPredicate);
    }


    /**
     * @return <code>true</code> if the sequence of items in this list circles back to
     * the first one, <code>false</code> otherwise
     */
    public boolean isComplete() {
        return last != null && last.next == first;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return first == null;
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

    @Override
    public boolean add(E e) {
        if (addCheck(e))
            return false;

        addElement(e);
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
        first = last = null;
        size = 0;
        modCount++;
    }

    public E getFirst() {
        return first.elem;
    }

    public E getLast() {
        return last.elem;
    }

    /**
     * Checks if the given element can be added to this list
     *
     * @return <code>true</code> if the element can <b>not</b> be added to this list
     */
    private boolean addCheck(E e) {
        return isComplete() || (last != null && !connectPredicate.test(e, last.elem));
    }

    private void addElement(E e) {
        Node<E> newNode = new Node<>(e);
        if (first == null)
            first = newNode;
        else
            last.next = newNode;
        last = newNode;
        size++; modCount++;
        if (size > 2 && connectPredicate.test(last.elem, first.elem))
            last.next = first;
    }

    private boolean throwUnsupported() {
        throw new UnsupportedOperationException("This operation is not supported");
    }


    private static class Node<E> {

        private final E elem;
        private Node<E> next;

        public Node(E elem) {
            this.elem = elem;
        }
    }

    private class CircularIterator implements Iterator<E> {

        private final int expectedModCount = modCount;
        private Node<E> current = first;


        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public E next() {
            if (modCount != expectedModCount)
                throw new ConcurrentModificationException("The list can't be modified while iteration is in progress");
            if (!hasNext())
                throw new NoSuchElementException("The iterator has no more elements");

            E result = current.elem;
            current = current.next;
            return result;
        }
    }
}
