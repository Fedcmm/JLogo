package it.unicam.cs.pa.jlogo;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class LogoCursorTest {

    private LogoCursor cursor;


    @BeforeEach
    void setUp() {
        cursor = new LogoCursor(new Point(400, 400));
    }

    @Test
    void moveTest() {
        assertTrue(cursor.move(150).isPresent());
        assertEquals(cursor.move(600).orElseThrow().getB().x(), 600);
        cursor.setPlotting(false);

        assertTrue(cursor.move(200).isEmpty());
    }

    @Test
    void listenerCallsTest() {
        cursor.setOnClosedAreaDrawnListener(area -> assertTrue(area.isComplete()));
        drawCompleteArea();

        cursor.setOnClosedAreaDrawnListener(Assertions::assertNotNull);
        cursor.move(50);
        cursor.setPlotting(false);
    }

    @Test
    void complexMovementsTest() {
        // TODO: 03/09/22 Write
    }

    private void drawCompleteArea() {
        cursor.move(50);
        cursor.rotate(90);
        cursor.move(50);
        cursor.rotate(90);
        cursor.move(50);
        cursor.rotate(90);
        cursor.move(50);
    }
}