package it.unicam.cs.pa.jlogo;

import it.unicam.cs.pa.jlogo.model.ClosedArea;
import it.unicam.cs.pa.jlogo.model.Line;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class DrawingsManager {

    private final List<Line> lines = new ArrayList<>();
    private final Set<ClosedArea> areas = new HashSet<>();


    public void clear() {
        lines.clear();
        areas.clear();
    }

    public void addLine(Line line) {
        // TODO: 12/08/22 Create areas and check overlaps
        lines.add(line);
    }

    public List<Line> getLines() {
        return lines;
    }

    public Set<ClosedArea> getAreas() {
        return areas;
    }
}
