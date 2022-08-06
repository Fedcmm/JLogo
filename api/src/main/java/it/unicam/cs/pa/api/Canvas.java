package it.unicam.cs.pa.api;

import java.awt.Color;

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
     * Returns the <i>home</i> position of the canvas
     *
     * @return the <i>home</i> position of the canvas
     */
    Point getHome();
}
