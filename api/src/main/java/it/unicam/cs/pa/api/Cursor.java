package it.unicam.cs.pa.api;

import java.awt.Color;

/**
 * Represents a cursor in a Logo program
 */
public interface Cursor {

    void move(Point point);

    void setDirection(int direction);

    void setPlotting(boolean plotting);

    void setPenSize(int size);

    void setLineColor(Color color);

    void setAreaColor(Color color);
}
