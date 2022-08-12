package it.unicam.cs.pa.jlogo;

import it.unicam.cs.pa.jlogo.model.ClosedArea;
import it.unicam.cs.pa.jlogo.model.Line;

import java.util.HashSet;
import java.util.Set;

public class DrawingsManager {

    private final Set<Line> lines = new HashSet<>();
    private final Set<ClosedArea> areas = new HashSet<>();


    public void clear() {
        lines.clear();
        areas.clear();
    }

    public void addLine(Line line) {
        // TODO: 12/08/22 Create areas and check overlaps
        lines.add(line);
    }

    public Set<Line> getLines() {
        return lines;
    }

    public Set<ClosedArea> getAreas() {
        return areas;
    }
}
