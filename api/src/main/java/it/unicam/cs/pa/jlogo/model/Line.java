package it.unicam.cs.pa.jlogo.model;

/**
 * Represents a line in a Logo program
 */
public interface Line {

    /**
     * Checks whether this line and the other intersect
     *
     * @param other the other line
     * @return <code>true</code> if the two lines intersect, <code>false</code> otherwise
     */
    boolean intersectsWith(Line other);
}
