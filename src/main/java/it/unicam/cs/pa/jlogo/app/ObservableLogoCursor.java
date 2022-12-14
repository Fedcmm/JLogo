package it.unicam.cs.pa.jlogo.app;

import it.unicam.cs.pa.jlogo.LogoCursor;
import it.unicam.cs.pa.jlogo.Point;
import it.unicam.cs.pa.jlogo.model.Line;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;

import java.awt.Color;
import java.util.Optional;

/**
 * A cursor which stores position, direction and line color in properties
 */
public class ObservableLogoCursor extends LogoCursor {

    private final ObjectProperty<Point> position = new SimpleObjectProperty<>(this, "position");
    private final DoubleProperty direction = new SimpleDoubleProperty(this, "direction");
    private final ObjectProperty<Color> lineColor = new SimpleObjectProperty<>(this, "lineColor");


    /**
     * Creates a new cursor located in the specified position
     *
     * @param initialPosition the initial position of the cursor
     */
    public ObservableLogoCursor(Point initialPosition) {
        super(initialPosition);

        position.set(initialPosition);
        direction.set(0);
        lineColor.set(Color.BLACK);
    }


    @Override
    public Optional<Line> move(double distance) {
        Optional<Line> result = super.move(distance);
        position.set(getPosition());
        return result;
    }

    @Override
    public void rotate(double degrees) {
        double newDir = (getDirection() + degrees) % 360;
        if (newDir < 0) newDir += 360;
        direction.set(newDir);
    }

    @Override
    public double getDirection() {
        return direction.get();
    }

    @Override
    public void setLineColor(Color color) {
        super.setLineColor(color);
        lineColor.set(color);
    }

    /**
     * @return the property holding the position of this cursor
     */
    public ObjectProperty<Point> positionProperty() {
        return position;
    }

    /**
     * @return the property holding the direction of this cursor
     */
    public DoubleProperty directionProperty() {
        return direction;
    }

    /**
     * @return the property holding the line color of this cursor
     */
    public ObjectProperty<Color> lineColorProperty() {
        return lineColor;
    }
}
