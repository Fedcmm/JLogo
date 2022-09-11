package it.unicam.cs.pa.jlogo;

import it.unicam.cs.pa.jlogo.model.Cursor;
import it.unicam.cs.pa.jlogo.model.Line;
import it.unicam.cs.pa.jlogo.model.OnClosedAreaDrawnListener;
import it.unicam.cs.pa.jlogo.util.CircularList;

import java.awt.Color;
import java.util.Optional;

public class LogoCursor implements Cursor {

    private Point position;
    private int direction;
    private int penSize;
    private boolean plotting;
    private Color lineColor;
    private Color areaColor;

    private OnClosedAreaDrawnListener areaListener;
    private CircularList<Line> currentLines = new CircularList<>(Line::isConnectedTo);


    public LogoCursor(Point initialPosition) {
        position = initialPosition;
        direction = 0;
        penSize = 1;
        plotting = true;
        lineColor = Color.BLACK;
        areaColor = Color.WHITE;
    }


    @Override
    public Optional<Line> move(int distance) {
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
    public void rotate(int degrees) {
        direction = (direction + degrees) % 360;
    }

    @Override
    public int getDirection() {
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
        lineColor = color;
    }

    @Override
    public void setAreaColor(Color color) {
        areaColor = color;
    }

    @Override
    public void setOnClosedAreaDrawnListener(OnClosedAreaDrawnListener listener) {
        areaListener = listener;
    }

    private Point calculateNextPosition(int distance) {
        double angle = Math.toRadians(getDirection());

        double vDist = distance * Math.sin(angle);
        double hDist = distance * Math.cos(angle);

        double x = hDist + getPosition().x();
        double y = vDist + getPosition().y();
        return new Point(x, y);
    }

    private Optional<Line> draw(Point a, Point b) {
        Line line = new LogoLine(a, b, penSize, lineColor);
        currentLines.add(line);

        if (currentLines.isComplete()) {
            callListener();
            return Optional.empty();
        }
        return Optional.of(line);
    }

    private void callListener() {
        if (areaListener != null && !currentLines.isEmpty()) {
            areaListener.closedAreaDrawn(new LogoClosedArea(currentLines, areaColor));
            currentLines = new CircularList<>(Line::isConnectedTo);
        }
    }
}
