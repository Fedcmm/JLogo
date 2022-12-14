package it.unicam.cs.pa.jlogo;

import it.unicam.cs.pa.jlogo.model.Line;

import java.awt.Color;
import java.util.Objects;

/**
 * Default implementation of {@link Line}, represents a straight line
 */
@SuppressWarnings("ClassCanBeRecord")
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
    public int getSize() {
        return size;
    }

    @Override
    public Color getColor() {
        return color;
    }

    @Override
    public boolean isConnectedTo(Line other) {
        if (Objects.requireNonNull(other).equals(this))
            return false;

        return this.a.equals(other.getA()) || this.a.equals(other.getB()) ||
                this.b.equals(other.getA()) || this.b.equals(other.getB());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LogoLine logoLine)) return false;

        return this.a.equals(logoLine.a) && this.b.equals(logoLine.b);
    }

    @Override
    public int hashCode() {
        return Objects.hash(a, b);
    }
}
