package it.unicam.cs.pa.jlogo.model;

import it.unicam.cs.pa.jlogo.Point;

/**
 * Represents a line in a Logo program
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
     * Checks whether this line and another intersect
     *
     * @param other the other line
     * @return <code>true</code> if the two lines intersect, <code>false</code> otherwise
     */
    boolean intersectsWith(Line other);
}
