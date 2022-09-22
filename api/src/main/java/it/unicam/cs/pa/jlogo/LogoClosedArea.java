package it.unicam.cs.pa.jlogo;

import it.unicam.cs.pa.jlogo.model.ClosedArea;
import it.unicam.cs.pa.jlogo.model.Line;
import it.unicam.cs.pa.jlogo.util.CircularList;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LogoClosedArea implements ClosedArea {

    private final CircularList<Line> lines;
    private final Color fillColor;


    public LogoClosedArea(CircularList<Line> lines, Color fillColor) {
        this.lines = lines;
        this.fillColor = fillColor;
    }


    @Override
    public void addLine(Line line) {
        lines.add(line);
    }

    @Override
    public Color getFillColor() {
        return fillColor;
    }

    @Override
    public boolean isComplete() {
        return lines.isComplete();
    }

    @Override
    public Line getFirstLine() {
        return lines.getFirst();
    }

    @Override
    public Line getLastLine() {
        return lines.getLast();
    }

    @Override
    public List<Line> getLines() {
        return List.copyOf(lines);
    }

    @Override
    public ClosedArea join(ClosedArea other) {
        if (!this.isConnectedTo(other))
            throw new IllegalArgumentException("Can't join two non-connected areas");

        List<Line> otherLines = new ArrayList<>(other.getLines());
        if (this.getFirstLine().isConnectedTo(other.getFirstLine()) || this.getLastLine().isConnectedTo(other.getLastLine()))
            Collections.reverse(otherLines);

        ClosedArea newArea = new LogoClosedArea(lines, fillColor);
        otherLines.forEach(newArea::addLine);
        return newArea;
    }
}
