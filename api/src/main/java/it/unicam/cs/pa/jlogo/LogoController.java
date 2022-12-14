package it.unicam.cs.pa.jlogo;

import it.unicam.cs.pa.jlogo.model.Canvas;
import it.unicam.cs.pa.jlogo.model.CanvasWriter;
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
    private final CanvasWriter writer;

    private Program program;


    /**
     * Creates a new empty controller with the given canvas and default {@link LogoProgramReader}
     * and {@link LogoCanvasWriter}
     *
     * @param canvas the canvas where the execution of the program will take place
     */
    public LogoController(Canvas canvas) {
        this(canvas, new LogoProgramReader(new LogoInstructionParser()), new LogoCanvasWriter());
    }

    /**
     * Creates a new empty controller with the given canvas, reader and writer
     *
     * @param canvas the canvas where the execution of the program will take place
     * @param reader the reader used to load the programs
     * @param writer the writer used to write the canvas
     */
    public LogoController(Canvas canvas, ProgramReader reader, CanvasWriter writer) {
        this.canvas = Objects.requireNonNull(canvas);
        this.reader = Objects.requireNonNull(reader);
        this.writer = Objects.requireNonNull(writer);
    }


    /**
     * Loads a program from the given file
     *
     * @param file the file with the program instructions
     *
     * @throws IOException if there are problems reading the file or the instructions
     * syntax is wrong
     */
    public void loadProgram(File file) throws IOException {
        program = reader.read(file);
        canvas.reset();
    }

    /**
     * Saves the result of the execution of the program into a file
     *
     * @param file the file to write to
     *
     * @throws IOException if an I/O error occurs while writing to the file
     */
    public void save(File file) throws IOException {
        writer.write(canvas, file);
    }

    /**
     * Executes the next instruction in the currently loaded program
     *
     * @return <code>false</code> if the program is finished
     */
    public boolean executeNext() {
        if (!program.hasNext())
            return false;

        program.next().execute(canvas);
        return true;
    }

    /**
     * Resets the current program and the canvas
     */
    public void reset() {
        canvas.reset();
        program.reset();
    }
}
