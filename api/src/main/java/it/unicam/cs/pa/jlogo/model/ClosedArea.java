package it.unicam.cs.pa.jlogo.model;

import java.awt.Color;
import java.util.List;

/**
 * Represents a closed area in a Logo program, that is, a sequence of lines
 * that start and end in the same point
 */
public interface ClosedArea {

    void addLine(Line line);

    Color getFillColor();

    boolean isComplete();

    Line getLast();

    List<Line> getLines();
}
