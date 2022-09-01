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
    Optional<Line> move(int distance);

    /**
     * Rotates the cursor
     *
     * @param degrees the amount of degrees of rotation, positive to rotate counterclockwise,
     *                negative to rotate clockwise
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

    /**
     * Registers a callback to be invoked when the cursor stops plotting
     *
     * @param listener the callback
     */
    void setOnClosedAreaDrawnListener(OnClosedAreaDrawnListener listener);

    /**
     * @return the distance from the <i>home</i> position of the canvas this cursor is in
     */
    int getDistanceFromHome();

    /**
     * Changes the color of the lines drawn by the cursor using the specified red, green
     * and blue values in the range (0 - 255)
     *
     * @param r the red component
     * @param g the green component
     * @param b the blue component
     *
     * @throws IllegalArgumentException if r, g or b are outside of the range 0 to 255, inclusive
     */
    default void setLineColor(int r, int g, int b) {
        setLineColor(new Color(r, g, b));
    }

    /**
     * Changes the color of the closed areas created by the cursor using the specified red,
     * green and blue values in the range (0 - 255)
     *
     * @param r the red component
     * @param g the green component
     * @param b the blue component
     *
     * @throws IllegalArgumentException if r, g or b are outside of the range 0 to 255, inclusive
     */
    default void setAreaColor(int r, int g, int b) {
        setAreaColor(new Color(r, g, b));
    }
}
