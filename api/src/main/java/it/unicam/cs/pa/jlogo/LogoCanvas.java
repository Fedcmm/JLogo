package it.unicam.cs.pa.jlogo;

import it.unicam.cs.pa.jlogo.model.Canvas;
import it.unicam.cs.pa.jlogo.model.Cursor;
import it.unicam.cs.pa.jlogo.model.Shape;

import java.awt.Color;
import java.util.List;

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
        // TODO: 09/08/22 Add result
        cursor.move(distance);
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
    public List<Shape<?>> getDrawings() {
        return null;
    }

    @Override
    public Cursor getCursor() {
        return cursor;
    }
}
