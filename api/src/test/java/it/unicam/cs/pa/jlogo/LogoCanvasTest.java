package it.unicam.cs.pa.jlogo;

import it.unicam.cs.pa.jlogo.model.Cursor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LogoCanvasTest {
    
    private LogoCanvas canvas;
    private Cursor cursor;
    
    
    @BeforeEach
    void setUp() {
        canvas = new LogoCanvas(600, 400);
        cursor = canvas.getCursor();
    }

    @Test
    void completeAreaTest() {
        drawCompleteArea();
        assertTrue(canvas.getLines().isEmpty());
        assertEquals(1, canvas.getClosedAreas().size());

        drawIncompleteArea();
        assertEquals(1, canvas.getClosedAreas().size());
    }

    @Test
    void moveCursorToHomeTest() {
        canvas.moveCursor(40);
        canvas.moveCursorToHome();
        assertEquals(canvas.getHome(), cursor.getPosition());
        
        randomMovements();
        canvas.moveCursorToHome();
        assertEquals(canvas.getHome(), cursor.getPosition());
    }


    private void drawCompleteArea() {
        canvas.moveCursor(50);
        cursor.rotate(90);
        canvas.moveCursor(50);
        cursor.rotate(90);
        canvas.moveCursor(50);
        cursor.rotate(90);
        canvas.moveCursor(50);
    }

    private void drawIncompleteArea() {
        canvas.moveCursor(50);
        cursor.rotate(30);
        canvas.moveCursor(30);
        cursor.setPlotting(false);
    }
    
    private void randomMovements() {
        cursor.rotate(171);
        canvas.moveCursor(40);
        cursor.rotate(-28);
        canvas.moveCursor(100);
    }
}