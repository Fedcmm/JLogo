package it.unicam.cs.pa.jlogo.model;

/**
 * Represents an instruction in a Logo program
 */
public interface Instruction {

    /**
     * Executes the instruction
     *
     * @param canvas the canvas on which to execute the instruction
     */
    void execute(Canvas canvas);
}
