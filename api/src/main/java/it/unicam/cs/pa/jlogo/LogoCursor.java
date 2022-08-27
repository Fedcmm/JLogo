package it.unicam.cs.pa.jlogo;

import it.unicam.cs.pa.jlogo.model.Canvas;
import it.unicam.cs.pa.jlogo.model.Cursor;
import it.unicam.cs.pa.jlogo.model.Line;
import it.unicam.cs.pa.jlogo.model.OnClosedAreaDrawnListener;
import it.unicam.cs.pa.jlogo.model.OnPlottingStoppedListener;
import it.unicam.cs.pa.jlogo.util.CircularList;

import java.awt.Color;
import java.util.Optional;

public class LogoCursor implements Cursor {

    private final Canvas canvas;

    private Point position;
    private int direction;
    private int penSize;
    private boolean plotting;
    private Color lineColor;
    private Color areaColor;

    private CircularList<Line> currentLines;
    private OnClosedAreaDrawnListener areaListener;


    public LogoCursor(Canvas canvas) {
        this.canvas = canvas;

        position = canvas.getHome();
        direction = 0;
        penSize = 5;
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

        return draw(initialPosition, position);
    }

    @Override
    public void rotate(int degrees) {
        direction = (direction + degrees) % 360;
    }

    @Override
    public void setPenSize(int size) {
        penSize = size;
    }

    @Override
    public void setPlotting(boolean plotting) {
        if (this.plotting && !plotting && currentLines != null) {
            areaListener.closedAreaDrawn(new LogoClosedArea(currentLines, areaColor));
            currentLines = null; // TODO: 27/08/22 Use clear
        }
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
        double angle = Math.toRadians(direction);

        int vDist = distance * (int) Math.round(Math.sin(angle));
        int hDist = distance * (int) Math.round(Math.cos(angle));

        double x = hDist + position.x();
        double y = vDist + position.y();
        return validateCoordinates(x, y);
    }

    private Optional<Line> draw(Point a, Point b) {
        if (!plotting)
            return Optional.empty();

        Line line = new LogoLine(a, b, penSize, lineColor);
        if (currentLines == null) {
            currentLines = new CircularList<>(line, Line::isConnectedTo);
            return Optional.of(line);
        }
        currentLines.add(line);
        if (currentLines.isComplete()) {
            areaListener.closedAreaDrawn(new LogoClosedArea(currentLines, areaColor));
            return Optional.empty();
        }
        return Optional.of(line);
    }

    private Point validateCoordinates(double x, double y) {
        if (x < 0)
            x = 0;
        if (x > canvas.getWidth())
            x = canvas.getWidth();

        if (y < 0)
            y = 0;
        if (y > canvas.getHeight())
            y = canvas.getHeight();

        return new Point(x, y);
    }
}
