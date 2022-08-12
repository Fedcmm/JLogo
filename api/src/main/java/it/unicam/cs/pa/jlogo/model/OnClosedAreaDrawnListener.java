package it.unicam.cs.pa.jlogo.model;

/**
 * Interface to define a callback to be invoked when a closed area is created in a canvas
 */
public interface OnClosedAreaDrawnListener {

    /**
     * Invoked when a closed area is formed
     *
     * @param area the new area
     */
    void closedAreaDrawn(ClosedArea area);
}
