package it.unicam.cs.pa.jlogo.model;

import java.io.IOException;

/**
 * Classes implementing this interface can parse a string into an {@link Instruction} object
 */
public interface InstructionParser {

    /**
     * Parses a string into an {@link Instruction}
     *
     * @param s the string containing the instruction text
     * @return the parsed instruction
     *
     * @throws IOException if the string is malformed or contains no instruction
     */
    Instruction parse(String s) throws IOException;
}
