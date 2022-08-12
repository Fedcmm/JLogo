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

public class LogoCanvas implements Canvas {

    private final int width;
    private final int height;
    private final Cursor cursor;

    private final Set<Line> lines = new HashSet<>();
    private final Set<ClosedArea> areas = new HashSet<>();

    private Color backColor;

    private OnLineDrawnListener lineListener;
    private OnClosedAreaDrawnListener areaListener;


    public LogoCanvas(int width, int height) {
        this.width = width;
        this.height = height;
        this.cursor = new LogoCursor(this);

        backColor = Color.WHITE;
    }


    @Override
    public void clear() {
        lines.clear();
        areas.clear();
    }

    @Override
    public void moveCursor(int distance) {
        Optional<Line> result = cursor.move(distance);
        if (result.isEmpty())
            return;

        Line line = result.get();

        lineListener.lineDrawn(line);
    }

    @Override
    public void setBackColor(Color color) {
        backColor = color;
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
    public List<Line> getDrawings() {
        return null;
    }

    @Override
    public Cursor getCursor() {
        return cursor;
    }

    @Override
    public void setOnLineDrawnListener(OnLineDrawnListener listener) {
        lineListener = listener;
    }

    @Override
    public void setOnClosedAreaDrawnListener(OnClosedAreaDrawnListener listener) {
        areaListener = listener;
    }
}
