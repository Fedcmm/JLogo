package it.unicam.cs.pa.jlogo;

import it.unicam.cs.pa.jlogo.model.Cursor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;

class LogoCanvasWriterTest {

    private final LogoCanvasWriter writer = new LogoCanvasWriter();
    private final String cursorString = "SIZE 400 400 255 255 255";
    private final String closedAreaString = """

            POLYGON 4 255 255 255
            200.0 200.0 0 0 0 1
            250.0 200.0 0 0 0 1
            250.0 250.0 0 0 0 1
            200.0 250.0 0 0 0 1""";

    private LogoCanvas canvas;


    @BeforeEach
    void setUp() {
        Locale.setDefault(Locale.US);
        canvas = new LogoCanvas(400, 400);
    }

    @Test
    void writeEmptyCanvasTest() {
        assertEquals(cursorString, writer.write(canvas));
    }

    @Test
    void writeOneLineCanvasTest() {
        canvas.moveCursor(40);
        String expected = cursorString + "\nLINE 200.0 200.0 240.0 200.0 0 0 0 1";
        assertEquals(expected, writer.write(canvas));
    }

    @Test
    void writeOneAreaCanvasTest() {
        drawCompleteArea();
        String expected = cursorString + closedAreaString;
        assertEquals(expected, writer.write(canvas));
    }

    @Test
    void writeAllTest() {
        drawCompleteArea();
        canvas.moveCursor(100);
        canvas.getCursor().rotate(90);
        canvas.moveCursor(100);
        String expected = cursorString +
                "\nLINE 200.0 200.0 200.0 100.0 0 0 0 1" +
                "\nLINE 200.0 100.0 300.0 100.0 0 0 0 1" +
                closedAreaString;
        assertEquals(expected, writer.write(canvas));
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