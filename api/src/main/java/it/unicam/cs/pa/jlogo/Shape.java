package it.unicam.cs.pa.jlogo;

/**
 * Represents a shape that can be drawn in a Logo program
 *
 * @param <T> how the shape is represented after drawing
 */
public interface Shape<T> {

    /**
     * Draws the shape
     *
     * @return the representation of the drawn shape
     */
    T draw();
}
