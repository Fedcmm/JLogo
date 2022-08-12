package it.unicam.cs.pa.jlogo.model;

/**
 * Interface to define a callback to be invoked when a line is drawn in a canvas
 */
public interface OnLineDrawnListener {

    /**
     * Invoked when a line is drawn
     *
     * @param line the new line
     */
    void lineDrawn(Line line);
}
