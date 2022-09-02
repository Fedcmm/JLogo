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
import java.util.Optional;
import java.util.Set;
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
     * Creates a new canvas with the specified dimensions
     *
     * @param width the width of the canvas
     * @param height the height of the canvas
     */
    public LogoCanvas(int width, int height) {
        this.width = width;
        this.height = height;
        this.cursor = new LogoCursor(this);

        backColor = Color.WHITE;
        cursor.setOnClosedAreaDrawnListener(this::receiveClosedArea);
    }


    @Override
    public void clear() {
        lines.clear();
        areas.clear();
    }

    @Override
    public void setBackColor(Color color) {
        backColor = color;
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
    public void moveCursor(int distance) {
        Optional<Line> result = cursor.move(distance);
        if (result.isEmpty())
            return;

        Line line = result.get();
        if (areas.stream().flatMap(area -> area.getLines().stream()).noneMatch(line::equals)) {
            lines.add(line);
            if (lineListener != null) lineListener.lineDrawn(line);
        }
    }

    @Override
    public void setOnLineDrawnListener(OnLineDrawnListener listener) {
        lineListener = listener;
    }

    @Override
    public void setOnClosedAreaDrawnListener(OnClosedAreaDrawnListener listener) {
        areaListener = listener;
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
     *
     * @param area the area to join
     * @return the new area created by joining all the connected ones,
     * or <code>area</code> if none was found
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
