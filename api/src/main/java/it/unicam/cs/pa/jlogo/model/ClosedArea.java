package it.unicam.cs.pa.jlogo.model;

import java.awt.Color;
import java.util.List;

/**
 * Represents a closed area in a Logo program, that is, a sequence of lines
 * that start and end in the same point. The area can be incomplete, meaning that the
 * first and last line are not connected
 */
public interface ClosedArea {

    /**
     * Adds a line to this area, this can only be done if the area is not complete
     *
     * @param line the line to add
     */
    void addLine(Line line);

    Color getFillColor();

    /**
     * @return <code>true</code> if this area is complete, <code>false</code> otherwise
     */
    boolean isComplete();

    Line getFirstLine();

    Line getLastLine();

    /**
     * @return the lines this area contains
     */
    List<Line> getLines();

    /**
     * Joins this area with the given one, the fill color of the resulting area
     * will be the same as the caller of this method
     *
     * @param other the area to join to this one
     * @return the new area
     */
    ClosedArea join(ClosedArea other);

    /**
     * Checks if this area is connected to the given one
     *
     * @param other another area
     * @return <code>true</code> if one end of this area is connected to one end
     * of the other, <code>false</code> otherwise or if one of the areas is complete
     */
    default boolean isConnectedTo(ClosedArea other) {
        return !isComplete() && !other.isComplete() &&
                (getFirstLine().isConnectedTo(other.getFirstLine()) ||
                getFirstLine().isConnectedTo(other.getLastLine()) ||
                getLastLine().isConnectedTo(other.getFirstLine()) ||
                getLastLine().isConnectedTo(other.getLastLine()));
    }
}
