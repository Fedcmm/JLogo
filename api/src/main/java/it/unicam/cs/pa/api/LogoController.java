package it.unicam.cs.pa.api;

import java.util.List;

/**
 * Controls the execution of a Logo program
 */
public class LogoController {

    private final Canvas canvas;
    private final Cursor cursor;
    private final List<Instruction> instructions;


    public LogoController(Canvas canvas, Cursor cursor, List<Instruction> instructions) {
        this.canvas = canvas;
        this.cursor = cursor;
        this.instructions = instructions;
    }
}
