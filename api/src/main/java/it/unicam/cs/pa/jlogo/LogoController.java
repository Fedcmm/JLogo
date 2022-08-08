package it.unicam.cs.pa.jlogo;

import java.io.File;
import java.io.IOException;

/**
 * Controls the execution of a Logo program
 */
public class LogoController {

    private final Canvas canvas;
    private final Cursor cursor;

    private final ProgramReader reader;

    private Program program;


    public LogoController(Canvas canvas, Cursor cursor, ProgramReader reader) {
        this.canvas = canvas;
        this.cursor = cursor;
        this.reader = reader;
    }


    public void openProgram(File file) throws IOException {
        program = reader.read(file);
    }
}
