package it.unicam.cs.pa.jlogo;

import it.unicam.cs.pa.jlogo.model.Line;

import java.awt.Color;

/**
 * Represents a line drawn in a Logo program
 */
public class LogoLine implements Line {

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
    public Point getA() {
        return a;
    }

    @Override
    public Point getB() {
        return b;
    }

    @Override
    public boolean intersectsWith(Line other) {
        if (a.checkPositionOnX(b) < 0 && b.checkPositionOnX(other.getA()) < 0 && other.getA().checkPositionOnX(other.getB()) < 0)
            return false;
        if (a.checkPositionOnY(b) < 0 && b.checkPositionOnY(other.getA()) < 0 && other.getA().checkPositionOnY(other.getB()) < 0)
            return false;

        if (a.checkPositionOnX(b) > 0 && b.checkPositionOnX(other.getA()) > 0 && other.getA().checkPositionOnX(other.getB()) > 0)
            return false;
        if (a.checkPositionOnY(b) > 0 && b.checkPositionOnY(other.getA()) > 0 && other.getA().checkPositionOnY(other.getB()) > 0)
            return false;

        return true;
    }

    @Override
    public boolean isConnectedTo(Line other) {
        if (this.equals(other))
            return false;
        return false;
    }
}
