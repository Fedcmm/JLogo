package it.unicam.cs.pa.jlogo;

import it.unicam.cs.pa.jlogo.model.Cursor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LogoCanvasTest {
    
    private LogoCanvas canvas;
    
    
    @BeforeEach
    void setUp() {
        canvas = new LogoCanvas(600, 400);
    }

    @Test
    void completeAreaTest() {
        drawCompleteArea();
        assertTrue(canvas.getLines().isEmpty());
        assertEquals(canvas.getClosedAreas().size(), 1);
    }

    private void drawCompleteArea() {
        Cursor cursor = canvas.getCursor();
        canvas.moveCursor(50);
        cursor.rotate(90);
        canvas.moveCursor(50);
        cursor.rotate(90);
        canvas.moveCursor(50);
        cursor.rotate(90);
        canvas.moveCursor(50);
    }
}