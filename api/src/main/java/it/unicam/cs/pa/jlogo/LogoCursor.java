package it.unicam.cs.pa.jlogo;

import it.unicam.cs.pa.jlogo.model.Canvas;
import it.unicam.cs.pa.jlogo.model.Cursor;
import it.unicam.cs.pa.jlogo.model.Line;
import it.unicam.cs.pa.jlogo.model.OnClosedAreaDrawnListener;
import it.unicam.cs.pa.jlogo.util.CircularList;

import java.awt.Color;
import java.util.Optional;

public class LogoCursor implements Cursor {

    private final Canvas canvas;
    private final CircularList<Line> currentLines = new CircularList<>(Line::isConnectedTo);

    private Point position;
    private int direction;
    private int penSize;
    private boolean plotting;
    private Color lineColor;
    private Color areaColor;

    private OnClosedAreaDrawnListener areaListener;
    private int maxDistance;


    public LogoCursor(Canvas canvas) {
        this.canvas = canvas;

        position = canvas.getHome();
        direction = 0;
        penSize = 5;
        plotting = true;
        lineColor = Color.BLACK;
        areaColor = Color.WHITE;

        maxDistance = calculateMaxDistance();
    }


    @Override
    public Optional<Line> move(int distance) {
        if (distance == 0)
            return Optional.empty();

        if (distance > maxDistance) distance = maxDistance;
        Point initialPosition = position;
        position = calculateNextPosition(distance);

        return plotting ? draw(initialPosition, position) : Optional.empty();
    }

    @Override
    public void rotate(int degrees) {
        direction = (direction + degrees) % 360;
        maxDistance = calculateMaxDistance();
    }

    @Override
    public void setPenSize(int size) {
        penSize = size;
    }

    @Override
    public void setPlotting(boolean plotting) {
        if (this.plotting && !plotting) {
            callListener();
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

    @Override
    public int getDistanceFromHome() {
        return position.getDistanceFrom(canvas.getHome());
    }

    private Point calculateNextPosition(int distance) {
        double angle = Math.toRadians(direction);

        double vDist = distance * Math.round(Math.sin(angle));
        double hDist = distance * Math.round(Math.cos(angle));

        double x = hDist + position.x();
        double y = vDist + position.y();
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

    private int calculateMaxDistance() {
        if (direction == 90)
            return position.getDistanceFrom(new Point(position.x(), canvas.getHeight()));
        if (direction == 270)
            return position.getDistanceFrom(new Point(position.x(), 0));

        double m = Math.tan(Math.toRadians(direction));
        double intersVertSide;

        if (direction > 90 && direction < 270) {
            intersVertSide = -m * position.x() + position.y();
            if (intersVertSide > 0 && intersVertSide < canvas.getHeight())
                return position.getDistanceFrom(new Point(0, intersVertSide));
        } else {
            intersVertSide = m * canvas.getWidth() - m * position.x() + position.y();
            if (intersVertSide > 0 && intersVertSide < canvas.getHeight())
                return position.getDistanceFrom(new Point(canvas.getWidth(), intersVertSide));
        }

        if (intersVertSide > canvas.getHeight()) {
            double intersAbove = (canvas.getHeight() - position.y() + m * position.x()) / m;
            return position.getDistanceFrom(new Point(intersAbove, canvas.getHeight()));
        }
        if (intersVertSide < 0) {
            double intersBelow = (-position.y() + m * position.x()) / m;
            return position.getDistanceFrom(new Point(intersBelow, 0));
        }
        return 0;
    }

    private void callListener() {
        if (areaListener != null) {
            areaListener.closedAreaDrawn(new LogoClosedArea(currentLines, areaColor));
            currentLines.clear();
        }
    }
}
