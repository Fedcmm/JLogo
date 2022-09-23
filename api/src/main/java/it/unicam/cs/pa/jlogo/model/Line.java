package it.unicam.cs.pa.jlogo.model;

import it.unicam.cs.pa.jlogo.Point;

import java.awt.Color;

/**
 * Represents a line drawn between two points in a Logo program
 */
public interface Line {

    /**
     * @return the first point of this line
     */
    Point getA();

    /**
     * @return the second point of this line
     */
    Point getB();

    /**
     * @return the thickness of this line
     */
    int getSize();

    /**
     * @return the color of this line
     */
    Color getColor();

    /**
     * Checks whether this line connects to the given one, meaning that the two
     * lines have one of their points in common
     *
     * @param other another line
     * @return <code>true</code> if the lines share exactly one point
     *
     * @throws NullPointerException if other is <code>null</code>
     */
    boolean isConnectedTo(Line other);
}
