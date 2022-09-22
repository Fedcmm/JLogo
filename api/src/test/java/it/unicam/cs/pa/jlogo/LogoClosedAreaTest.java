package it.unicam.cs.pa.jlogo;

import it.unicam.cs.pa.jlogo.model.Line;
import it.unicam.cs.pa.jlogo.util.CircularList;
import org.junit.jupiter.api.Test;

import java.awt.Color;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

class LogoClosedAreaTest {

    private final Line line1 = new LogoLine(new Point(1, 1), new Point(3, 1), 1, Color.BLACK);
    private final Line line2 = new LogoLine(new Point(3, 1), new Point(3, 3), 1, Color.BLACK);
    private final Line line3 = new LogoLine(new Point(3, 3), new Point(1, 3), 1, Color.BLACK);
    private final Line line4 = new LogoLine(new Point(1, 3), new Point(1, 4), 1, Color.BLACK);


    @Test
    void shouldJoinAreas() {
        LogoClosedArea area1 = new LogoClosedArea(getFirstAreaLines(), Color.BLACK);
        LogoClosedArea area2 = new LogoClosedArea(getSecondAreaLines(), Color.BLACK);

        List<Line> joinedLines = area1.join(area2).getLines();
        assertTrue(joinedLines.contains(line1));
        assertTrue(joinedLines.contains(line2));
        assertTrue(joinedLines.contains(line3));
        assertTrue(joinedLines.contains(line4));
    }

    @Test
    void shouldJoinAreasWithReversedLines() {
        LogoClosedArea area1 = new LogoClosedArea(getFirstAreaLines(), Color.BLACK);
        LogoClosedArea area2 = new LogoClosedArea(getSecondAreaReversedLines(), Color.BLACK);

        List<Line> joinedLines = area1.join(area2).getLines();
        assertTrue(joinedLines.contains(line1));
        assertTrue(joinedLines.contains(line2));
        assertTrue(joinedLines.contains(line3));
        assertTrue(joinedLines.contains(line4));
    }

    private CircularList<Line> getFirstAreaLines() {
        CircularList<Line> lines = new CircularList<>(Line::isConnectedTo);
        lines.add(line1);
        lines.add(line2);
        return lines;
    }

    private CircularList<Line> getSecondAreaLines() {
        CircularList<Line> lines = new CircularList<>(Line::isConnectedTo);
        lines.add(line3);
        lines.add(line4);
        return lines;
    }

    private CircularList<Line> getSecondAreaReversedLines() {
        CircularList<Line> lines = new CircularList<>(Line::isConnectedTo);
        lines.add(line4);
        lines.add(line3);
        return lines;
    }
}