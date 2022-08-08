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
     */
    void setBackColor(Color color);

    /**
     * Deletes all the drawings in this canvas
     */
    void clear();

    /**
     * Returns the <i>home</i> position of this canvas
     *
     * @return the <i>home</i> position of this canvas
     */
    Point getHome();

    List<Shape<?>> getDrawings();

    void moveCursor(int distance);

    Cursor getCursor();
}
