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

    void moveCursor(int distance);

    /**
     * Changes the background color of the canvas
     *
     * @param color the new color
     */
    void setBackColor(Color color);

    int getWidth();

    int getHeight();

    List<Line> getDrawings();

    Cursor getCursor();

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
}
