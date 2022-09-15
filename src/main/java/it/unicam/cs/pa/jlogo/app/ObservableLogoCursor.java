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

public class ObservableLogoCursor extends LogoCursor {

    private final ObjectProperty<Point> position = new SimpleObjectProperty<>(this, "position");
    private final DoubleProperty direction = new SimpleDoubleProperty(this, "direction");
    private final ObjectProperty<Color> lineColor = new SimpleObjectProperty<>(this, "lineColor");


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
        direction.set((getDirection() + degrees) % 360);
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

    public ObjectProperty<Point> positionProperty() {
        return position;
    }

    public DoubleProperty directionProperty() {
        return direction;
    }

    public ObjectProperty<Color> lineColorProperty() {
        return lineColor;
    }
}
