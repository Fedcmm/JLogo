package it.unicam.cs.pa.api;

import java.awt.Color;

/**
 * Represents a line drawn in a Logo program
 */
public abstract class Line {

    private final Point a;
    private final Point b;
    private final int size;
    private final Color color;


    public Line(Point a, Point b, int size, Color color) {
        this.a = a;
        this.b = b;
        this.size = size;
        this.color = color;
    }
}
