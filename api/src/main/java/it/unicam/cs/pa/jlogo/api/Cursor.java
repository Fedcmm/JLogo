package it.unicam.cs.pa.jlogo.api;

import java.awt.Color;

/**
 * Represents a cursor in a Logo program
 */
public interface Cursor {

    /**
     * Moves the cursor in the direction it is facing
     *
     * @param distance the distance to travel. If negative, the cursor
     *                 will move backwards
     * @return the position of the cursor after the movement
     */
    Point move(int distance);

    /**
     * Changes the direction of the cursor
     *
     * @param direction the new direction, may be a value between 0 and 360
     *
     * @throws IllegalArgumentException if direction is not a value between
     * 0 and 360
     */
    void setDirection(int direction);

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
