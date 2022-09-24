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
    void shouldReturnArea() {
        drawCompleteArea();
        assertEquals(1, canvas.getClosedAreas().size());
        assertTrue(canvas.getLines().isEmpty());
    }

    @Test
    void shouldNotReturnArea() {
        drawIncompleteArea();
        assertEquals(0, canvas.getClosedAreas().size());
        assertEquals(2, canvas.getLines().size());
    }

    @Test
    void shouldMoveToHome() {
        canvas.moveCursor(40);
        canvas.moveCursorToHome();
        assertEquals(canvas.getHome(), cursor.getPosition());
        
        otherMovements();
        canvas.moveCursorToHome();
        assertEquals(canvas.getHome(), cursor.getPosition());
    }

    @Test
    void horizontalMovementShouldStopAtEdge() {
        canvas.moveCursor(1000);
        assertTrue(Math.abs(cursor.getPosition().x() - 600) < Point.EPSILON);

        canvas.moveCursor(-1000);
        assertTrue(Math.abs(cursor.getPosition().x()) < Point.EPSILON);
    }

    @Test
    void verticalMovementShouldStopAtEdge() {
        cursor.rotate(90);

        canvas.moveCursor(1000);
        assertTrue(Math.abs(cursor.getPosition().y() - 400) < Point.EPSILON);

        canvas.moveCursor(-1000);
        assertTrue(Math.abs(cursor.getPosition().y()) < Point.EPSILON);
    }

    @Test
    void diagonalMovementShouldStopAtVerticalEdge() {
        cursor.rotate(30);

        canvas.moveCursor(1000);
        assertTrue(Math.abs(cursor.getPosition().x() - 600) < Point.EPSILON);

        canvas.moveCursor(-1000);
        assertTrue(Math.abs(cursor.getPosition().x()) < Point.EPSILON);
    }

    @Test
    void diagonalMovementShouldStopAtHorizontalEdge() {
        cursor.rotate(80);

        canvas.moveCursor(1000);
        assertTrue(Math.abs(cursor.getPosition().y() - 400) < Point.EPSILON);

        canvas.moveCursor(-1000);
        assertTrue(Math.abs(cursor.getPosition().y()) < Point.EPSILON);
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
    
    private void otherMovements() {
        cursor.rotate(171);
        canvas.moveCursor(40);
        cursor.rotate(-28);
        canvas.moveCursor(100);
    }
}