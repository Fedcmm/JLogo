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

    /**
     * @return the fill color of this area
     */
    Color getFillColor();

    /**
     * @return <code>true</code> if this area is complete, <code>false</code> otherwise
     */
    boolean isComplete();

    /**
     * @return the first line added to this area
     */
    Line getFirstLine();

    /**
     * @return the last line added to this area
     */
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
     * @return a new area that contains the lines of both areas
     *
     * @throws IllegalArgumentException if the two areas are not connected
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
                (this.getFirstLine().isConnectedTo(other.getFirstLine()) ||
                this.getFirstLine().isConnectedTo(other.getLastLine()) ||
                this.getLastLine().isConnectedTo(other.getFirstLine()) ||
                this.getLastLine().isConnectedTo(other.getLastLine()));
    }
}
