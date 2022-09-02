package it.unicam.cs.pa.jlogo;

import it.unicam.cs.pa.jlogo.model.Canvas;
import it.unicam.cs.pa.jlogo.model.Program;
import it.unicam.cs.pa.jlogo.model.ProgramReader;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

/**
 * Controls the execution of a Logo program
 */
public class LogoController {

    private final Canvas canvas;
    private final ProgramReader reader;

    private Program program;


    public LogoController(Canvas canvas, ProgramReader reader) {
        this.canvas = Objects.requireNonNull(canvas);
        this.reader = Objects.requireNonNull(reader);
    }


    public void openProgram(File file) throws IOException {
        program = reader.read(file);
    }

    public void executeNext() {
        program.next().execute(canvas);
    }

    public void reset() {
        program.reset();
    }
}
