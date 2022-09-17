package it.unicam.cs.pa.jlogo;

import java.util.Objects;

/**
 * Holds coordinates for a point in a canvas
 *
 * @param x the x coordinate
 * @param y the y coordinate
 */
public record Point(double x, double y) {

    public static final double EPSILON = 0.0000001d;

    public double distanceFrom(Point other) {
        double dX = this.x - other.x;
        double dY = this.y - other.y;
        return Math.sqrt(dX*dX + dY*dY);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Point other = (Point) o;
        return Math.abs(other.x - this.x) < EPSILON && Math.abs(other.y - this.y) < EPSILON;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
