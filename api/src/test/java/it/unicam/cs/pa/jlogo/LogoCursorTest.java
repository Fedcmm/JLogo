package it.unicam.cs.pa.jlogo;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class LogoCursorTest {

    private LogoCursor cursor;


    @BeforeEach
    void setUp() {
        cursor = new LogoCursor(new Point(400, 400));
    }

    @Test
    void shouldDraw() {
        assertTrue(cursor.move(150).isPresent());
    }

    @Test
    void shouldNotDraw() {
        assertTrue(cursor.move(0).isEmpty());

        cursor.setPlotting(false);
        assertTrue(cursor.move(200).isEmpty());
    }

    @Test
    void shouldCallListenerOnCompleteArea() {
        cursor.setOnClosedAreaDrawnListener(area -> assertTrue(area.isComplete()));
        drawCompleteArea();
    }

    @Test
    void shouldCallListenerOnStopPlotting() {
        cursor.setOnClosedAreaDrawnListener(Assertions::assertNotNull);
        cursor.move(50);
        cursor.setPlotting(false);
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