package it.unicam.cs.pa.jlogo.model;

/**
 * Interface to define a callback to be invoked when a closed area is created
 */
public interface OnClosedAreaDrawnListener {

    /**
     * Invoked when a closed area is created
     *
     * @param area the new area
     */
    void closedAreaDrawn(ClosedArea area);
}
