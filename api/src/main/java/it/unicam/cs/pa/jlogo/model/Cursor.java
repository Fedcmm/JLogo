package it.unicam.cs.pa.jlogo.model;

import java.awt.Color;
import java.util.Optional;

/**
 * Represents a cursor in a Logo program
 */
public interface Cursor {

    /**
     * Moves the cursor
     *
     * @param distance the distance to travel. If negative, the cursor
     *                 will move backwards
     * @return the shape drawn by the movement, or {@link Optional#empty()} if
     * drawing is disabled
     */
    Optional<Shape<?>> move(int distance);

    /**
     * Rotates the cursor
     *
     * @param degrees the amount of degrees of rotation, positive to rotate to the left,
     *                negative to rotate to the right
     */
    void rotate(int degrees);

    /**
     * Changes the thickness of the lines drawn by the cursor
     *
     * @param size the new size
     *
     * @throws IllegalArgumentException if size is less than 1
     */
    void setPenSize(int size);

    /**
     * Sets whether the cursor should draw lines when moving
     *
     * @param plotting <code>true</code> to activate drawing,
     *                 <code>false</code> to disable it
     */
    void setPlotting(boolean plotting);

    /**
     * Changes the color of the lines drawn by the cursor
     *
     * @param color the new color
     */
    void setLineColor(Color color);

    /**
     * Changes the color of the closed areas created by the cursor
     *
     * @param color the new color
     */
    void setAreaColor(Color color);
}
