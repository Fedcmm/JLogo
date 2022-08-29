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

        return plotting ? draw(initialPosition, position) : Optional.empty();
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

    private Point calculateNextPosition(int distance) {
        double angle = Math.toRadians(direction);

        int vDist = distance * (int) Math.round(Math.sin(angle));
        int hDist = distance * (int) Math.round(Math.cos(angle));

        double x = hDist + position.x();
        double y = vDist + position.y();
        return validateCoordinates(x, y);
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

    // x -x1       y -y1
    //-------  =  -------
    // x2-x1       y2-y1
    private Point validateCoordinates(double x, double y) { // TODO: 27/08/22 Improve
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

    private void callListener() {
        if (areaListener != null) {
            areaListener.closedAreaDrawn(new LogoClosedArea(currentLines, areaColor));
            currentLines.clear();
        }
    }
}
