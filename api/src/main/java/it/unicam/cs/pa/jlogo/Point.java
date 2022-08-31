package it.unicam.cs.pa.jlogo;

/**
 * Holds coordinates for a point in a canvas
 *
 * @param x the x coordinate
 * @param y the y coordinate
 */
public record Point(double x, double y) {

    /**
     * Compares the x coordinate of this point with the other
     *
     * @param other the other point to compare with
     * @return a value less than 0 if this point is to the left of the other,
     * a value greater than 0 if this point is to the right of the other, and
     * the value 0 if the two points are on the same vertical axis
     */
    public int checkPositionOnX(Point other) {
        return Double.compare(this.x, other.x);
    }

    /**
     * Compares the y coordinate of this point with the other
     *
     * @param other the other point to compare with
     * @return a value less than 0 if this point is below the other,
     * a value greater than 0 if this point is above the other, and
     * the value 0 if the two points are on the same horizontal axis
     */
    public int checkPositionOnY(Point other) {
        return Double.compare(this.y, other.y);
    }

    public int getDistanceFrom(Point other) {
        double dX = Math.abs(this.x - other.x);
        double dY = Math.abs(this.y - other.y);
        return (int) Math.round(Math.sqrt(dX*dX + dY*dY));
    }
}
