package it.unicam.cs.pa.jlogo.model;

import it.unicam.cs.pa.jlogo.Point;

import java.awt.Color;
import java.util.List;

/**
 * Represents a drawing area in a Logo program
 */
public interface Canvas {

    /**
     * Changes the background color of the canvas
     *
     * @param color the new color
     *
     * @throws NullPointerException if color is null
     */
    void setBackColor(Color color);

    /**
     * @return the background color of this canvas
     */
    Color getBackColor();

    /**
     * @return the width of this canvas
     */
    int getWidth();

    /**
     * @return the height of this canvas
     */
    int getHeight();

    /**
     * @return the lines contained in this canvas
     */
    List<Line> getLines();

    /**
     * @return the complete closed areas contained in this canvas
     */
    List<ClosedArea> getClosedAreas();

    /**
     * @return the cursor contained in this canvas
     */
    Cursor getCursor();

    /**
     * Moves the cursor contained in this canvas
     *
     * @param distance the distance the cursor should travel
     */
    void moveCursor(double distance);

    /**
     * Deletes all the drawings in this canvas
     */
    void clear();

    /**
     * Resets the state of this canvas
     */
    void reset();


    /**
     * @return the <i>home</i> position of this canvas
     */
    default Point getHome() {
        return new Point(getWidth() / 2.0, getHeight() / 2.0);
    }

    /**
     * Moves the cursor to the <i>home</i> position of this canvas
     */
    default void moveCursorToHome() {
        moveCursor(getCursor().getPosition().distanceFrom(getHome()));
    }

    /**
     * Changes the background color of this canvas using the specified red, green
     * and blue values in the range (0 - 255)
     *
     * @param r the red component
     * @param g the green component
     * @param b the blue component
     *
     * @throws IllegalArgumentException if r, g or b are outside the range 0 to 255, inclusive
     */
    default void setBackColor(int r, int g, int b) {
        setBackColor(new Color(r, g, b));
    }
}
