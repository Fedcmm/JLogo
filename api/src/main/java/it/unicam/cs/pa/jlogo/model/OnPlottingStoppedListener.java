package it.unicam.cs.pa.jlogo.model;

/**
 * Interface to define a callback to be invoked when a canvas stops plotting
 */
public interface OnPlottingStoppedListener {

    /**
     * Invoked when the canvas stops plotting
     *
     * @param area the area created by the cursor while it was plotting
     */
    void onPlottingStopped(ClosedArea area);
}
