package it.unicam.cs.pa.jlogo.model;

import it.unicam.cs.pa.jlogo.Point;

import java.awt.Color;
import java.util.List;

/**
 * Represents a drawing area in a Logo program
 */
public interface Canvas {

    /**
     * Deletes all the drawings in this canvas
     */
    void clear();

    /**
     * Changes the background color of the canvas
     *
     * @param color the new color
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

    Cursor getCursor();

    void moveCursor(int distance);

    /**
     * Registers a callback to be invoked when a new line is drawn
     *
     * @param listener the callback
     */
    void setOnLineDrawnListener(OnLineDrawnListener listener);

    /**
     * Registers a callback to be invoked when a new <b>complete</b> closed area is created
     *
     * @param listener the callback
     */
    void setOnClosedAreaDrawnListener(OnClosedAreaDrawnListener listener);

    /**
     * @return the <i>home</i> position of this canvas
     */
    default Point getHome() {
        return new Point(getWidth() / 2.0, getHeight() / 2.0);
    }

    default void setBackColor(int r, int g, int b) {
        setBackColor(new Color(r, g, b));
    }
}
