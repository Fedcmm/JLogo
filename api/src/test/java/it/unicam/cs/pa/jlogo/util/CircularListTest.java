package it.unicam.cs.pa.jlogo.util;

import it.unicam.cs.pa.jlogo.LogoLine;
import it.unicam.cs.pa.jlogo.Point;
import it.unicam.cs.pa.jlogo.model.Line;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.Color;
import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

class CircularListTest {

    CircularList<Line> circularList;


    @BeforeEach
    void setUp() {
        circularList = new CircularList<>(Line::isConnectedTo);
    }

    @Test
    void iteratorShouldThrow() {
        Iterator<Line> iterator = circularList.iterator();
        assertFalse(iterator.hasNext());
        assertThrows(NoSuchElementException.class, iterator::next);
    }

    @Test
    void iteratorShouldReturnThreeElements() {
        putThree();
        Iterator<Line> iterator = circularList.iterator();
        assertTrue(iterator.hasNext());
        assertDoesNotThrow(iterator::next);
        assertTrue(iterator.hasNext());
        assertDoesNotThrow(iterator::next);
        assertTrue(iterator.hasNext());
        assertDoesNotThrow(iterator::next);
        assertFalse(iterator.hasNext());
        assertThrows(NoSuchElementException.class, iterator::next);
    }

    private void putThree() {
        circularList.add(new LogoLine(new Point(1, 1), new Point(1, 2), 1, Color.BLACK));
        circularList.add(new LogoLine(new Point(1, 2), new Point(1, 3), 1, Color.BLACK));
        circularList.add(new LogoLine(new Point(1, 3), new Point(1, 4), 1, Color.BLACK));
    }
}