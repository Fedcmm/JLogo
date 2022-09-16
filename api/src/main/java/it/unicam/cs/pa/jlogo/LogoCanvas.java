package it.unicam.cs.pa.jlogo;

import it.unicam.cs.pa.jlogo.model.Canvas;
import it.unicam.cs.pa.jlogo.model.ClosedArea;
import it.unicam.cs.pa.jlogo.model.Cursor;
import it.unicam.cs.pa.jlogo.model.Line;
import it.unicam.cs.pa.jlogo.model.OnClosedAreaDrawnListener;
import it.unicam.cs.pa.jlogo.model.OnLineDrawnListener;

import java.awt.Color;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Default implementation of {@link Canvas}
 */
public class LogoCanvas implements Canvas {

    private final int width;
    private final int height;
    private final Cursor cursor;

    private final Set<Line> lines = new HashSet<>();
    private final Set<ClosedArea> areas = new HashSet<>();

    private Color backColor;

    private OnLineDrawnListener lineListener;
    private OnClosedAreaDrawnListener areaListener;


    /**
     * Creates a new canvas with the specified dimensions and gets the cursor from
     * the specified generator function
     *
     * @param width     the width of the canvas
     * @param height    the height of the canvas
     * @param generator a function that takes a {@link Point} as input and returns
     *                  a {@link Cursor}
     */
    public LogoCanvas(int width, int height, Function<Point, Cursor> generator) {
        this.width = width;
        this.height = height;
        this.cursor = Objects.requireNonNull(generator).apply(getHome());

        backColor = Color.WHITE;
        cursor.setOnClosedAreaDrawnListener(this::receiveClosedArea);
    }

    /**
     * Creates a new canvas with the specified dimensions and a default {@link LogoCursor}
     *
     * @param width  the width of the canvas
     * @param height the height of the canvas
     */
    public LogoCanvas(int width, int height) {
        this(width, height, LogoCursor::new);
    }


    @Override
    public void setBackColor(Color color) {
        backColor = Objects.requireNonNull(color);
    }

    @Override
    public Color getBackColor() {
        return backColor;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public List<Line> getLines() {
        return List.copyOf(lines);
    }

    @Override
    public List<ClosedArea> getClosedAreas() {
        return areas.stream().filter(ClosedArea::isComplete).toList();
    }

    @Override
    public Cursor getCursor() {
        return cursor;
    }

    @Override
    public void moveCursor(double distance) {
        Optional<Line> result = cursor.move(Math.min(distance, calculateMaxDistance()));
        if (result.isEmpty())
            return;

        Line line = result.get();
        if (areas.stream().map(ClosedArea::getLines).noneMatch(lines1 -> lines1.contains(line))) {
            lines.add(line);
            if (lineListener != null) lineListener.lineDrawn(line);
        }
    }

    @Override
    public void moveCursorToHome() {
        Point home = getHome();
        Point position = cursor.getPosition();
        if (home.equals(position)) return;

        double dX = home.x() - position.x();
        double dY = home.y() - position.y();
        double lineAngle = Math.atan(dY / dX);

        double finalAngle;
        if (Double.isInfinite(lineAngle))
            finalAngle = dY > 0 ? Math.PI/2 : -Math.PI/2;
        else
            finalAngle = dX < 0 ? Math.PI + lineAngle : lineAngle;
        double angleDiff = Math.toDegrees(finalAngle) - cursor.getDirection();

        cursor.rotate(angleDiff);
        moveCursor(Math.sqrt(dX*dX + dY*dY)); // No need to call Point.distanceFrom and calculate d's again
        cursor.rotate(-angleDiff);
    }

    @Override
    public void clear() {
        lines.clear();
        areas.clear();
    }

    @Override
    public void reset() {
        cursor.setPlotting(false);
        moveCursorToHome();
        cursor.setPlotting(true);
        cursor.rotate(-cursor.getDirection());
        cursor.setPenSize(1);
        cursor.setLineColor(Color.BLACK);
        cursor.setFillColor(Color.WHITE);

        setBackColor(Color.WHITE);
        clear();
    }

    /**
     * Registers a callback to be invoked when a new line is drawn
     *
     * @param listener the callback
     */
    public void setOnLineDrawnListener(OnLineDrawnListener listener) {
        lineListener = listener;
    }

    /**
     * Registers a callback to be invoked when a new <b>complete</b> closed area is created
     *
     * @param listener the callback
     */
    public void setOnClosedAreaDrawnListener(OnClosedAreaDrawnListener listener) {
        areaListener = listener;
    }

    private double calculateMaxDistance() {
        double direction = cursor.getDirection();
        Point position = cursor.getPosition();

        if (direction == 90)
            return position.distanceFrom(new Point(position.x(), getHeight()));
        if (direction == 270)
            return position.distanceFrom(new Point(position.x(), 0));

        double m = Math.tan(Math.toRadians(direction));
        double intersVertSide;

        if (direction > 90 && direction < 270) {
            intersVertSide = -m * position.x() + position.y();
            if (intersVertSide > 0 && intersVertSide < getHeight())
                return position.distanceFrom(new Point(0, intersVertSide));
        } else {
            intersVertSide = m * getWidth() - m * position.x() + position.y();
            if (intersVertSide > 0 && intersVertSide < getHeight())
                return position.distanceFrom(new Point(getWidth(), intersVertSide));
        }

        if (intersVertSide > getHeight()) {
            double intersAbove = (getHeight() - position.y() + m * position.x()) / m;
            return position.distanceFrom(new Point(intersAbove, getHeight()));
        }
        if (intersVertSide < 0) {
            double intersBelow = (-position.y() + m * position.x()) / m;
            return position.distanceFrom(new Point(intersBelow, 0));
        }
        return 0;
    }

    /**
     * Handles closed areas received from the cursor
     */
    private void receiveClosedArea(ClosedArea area) {
        areas.add(joinAreas(area));

        if (area.isComplete()) {
            area.getLines().forEach(lines::remove);
            if (areaListener != null) areaListener.closedAreaDrawn(area);
        }
    }

    /**
     * Finds all the areas connected to the given one and joins them
     */
    private ClosedArea joinAreas(ClosedArea area) {
        List<ClosedArea> connectedAreas = areas.stream()
                .filter(Predicate.not(ClosedArea::isComplete))
                .filter(area::isConnectedTo).toList();

        areas.removeIf(area::isConnectedTo);
        for (ClosedArea a : connectedAreas) {
            area = area.join(a);
        }

        return area;
    }
}
