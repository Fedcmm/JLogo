package it.unicam.cs.pa.jlogo;

import it.unicam.cs.pa.jlogo.model.Canvas;
import it.unicam.cs.pa.jlogo.model.Cursor;
import it.unicam.cs.pa.jlogo.model.Line;

import java.awt.Color;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class LogoCanvas implements Canvas {

    private final int width;
    private final int height;
    private final Cursor cursor;

    private Color backColor;


    public LogoCanvas(int width, int height, Cursor cursor) {
        this.width = width;
        this.height = height;
        this.cursor = cursor; // new LogoCursor(this);

        backColor = Color.WHITE;
    }


    @Override
    public void clear() {

    }

    @Override
    public void moveCursor(int distance) {
        Optional<Line> result = cursor.move(distance);
        if (result.isEmpty())
            return;

        Line line = result.get();
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
}
