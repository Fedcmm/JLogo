package it.unicam.cs.pa.api;

import java.awt.Color;

/**
 * Represents a line drawn in a Logo program
 */
public class LogoLine implements Shape<String> {

    private final Point a;
    private final Point b;
    private final int size;
    private final Color color;


    public LogoLine(Point a, Point b, int size, Color color) {
        this.a = a;
        this.b = b;
        this.size = size;
        this.color = color;
    }


    @Override
    public String draw() {
        return null;
    }
}
