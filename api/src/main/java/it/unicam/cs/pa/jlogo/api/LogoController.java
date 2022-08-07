package it.unicam.cs.pa.jlogo.api;

/**
 * Controls the execution of a Logo program
 */
public class LogoController {

    private final Canvas canvas;
    private final Cursor cursor;

    private final ProgramReader reader;

    private LogoProgram program;


    public LogoController(Canvas canvas, Cursor cursor, ProgramReader reader) {
        this.canvas = canvas;
        this.cursor = cursor;
        this.reader = reader;
    }
}
