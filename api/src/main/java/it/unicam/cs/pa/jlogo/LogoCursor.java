package it.unicam.cs.pa.jlogo;

import it.unicam.cs.pa.jlogo.model.ClosedArea;
import it.unicam.cs.pa.jlogo.model.Cursor;
import it.unicam.cs.pa.jlogo.model.Line;
import it.unicam.cs.pa.jlogo.util.CircularList;

import java.awt.Color;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;

/**
 * Default implementation of {@link Cursor}
 */
public class LogoCursor implements Cursor {

    private final CircularList<Line> currentLines = new CircularList<>(Line::isConnectedTo);

    private Point position;
    private double direction;
    private int penSize;
    private boolean plotting;
    private Color lineColor;
    private Color fillColor;

    private Consumer<ClosedArea> areaListener;


    /**
     * Creates a new cursor located in the specified position
     *
     * @param initialPosition the initial position of the cursor
     */
    public LogoCursor(Point initialPosition) {
        position = initialPosition;
        direction = 0;
        penSize = 1;
        plotting = true;
        lineColor = Color.BLACK;
        fillColor = Color.WHITE;
    }


    @Override
    public Optional<Line> move(double distance) {
        if (distance == 0)
            return Optional.empty();

        Point initialPosition = position;
        position = calculateNextPosition(distance);

        return plotting ? draw(initialPosition, position) : Optional.empty();
    }

    @Override
    public Point getPosition() {
        return position;
    }

    @Override
    public void rotate(double degrees) {
        direction = (direction + degrees) % 360;
        if (direction < 0) direction += 360;
    }

    @Override
    public double getDirection() {
        return direction;
    }

    @Override
    public void setPenSize(int size) {
        if (size < 1)
            throw new IllegalArgumentException("Pen size can't be less than 1");

        penSize = size;
    }

    @Override
    public void setPlotting(boolean plotting) {
        if (this.plotting && !plotting)
            callListener();
        this.plotting = plotting;
    }

    @Override
    public void setLineColor(Color color) {
        lineColor = Objects.requireNonNull(color);
    }

    @Override
    public void setFillColor(Color color) {
        fillColor = Objects.requireNonNull(color);
    }

    @Override
    public void setOnClosedAreaDrawnListener(Consumer<ClosedArea> listener) {
        areaListener = listener;
    }

    private Point calculateNextPosition(double distance) {
        double angle = Math.toRadians(getDirection());

        double vDist = distance * Math.sin(angle);
        double hDist = distance * Math.cos(angle);

        double x = hDist + getPosition().x();
        double y = vDist + getPosition().y();
        return new Point(x, y);
    }

    private Optional<Line> draw(Point a, Point b) {
        Line line = new LogoLine(a, b, penSize, lineColor);
        if (!currentLines.contains(line)) currentLines.add(line);

        if (currentLines.isComplete()) {
            callListener();
            return Optional.empty();
        }
        return Optional.of(line);
    }

    private void callListener() {
        if (areaListener != null && !currentLines.isEmpty()) {
            areaListener.accept(new LogoClosedArea(currentLines, fillColor));
            currentLines.clear();
        }
    }
}
