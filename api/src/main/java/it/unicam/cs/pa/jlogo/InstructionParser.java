package it.unicam.cs.pa.jlogo;

import java.io.IOException;

/**
 * This class parses Logo instructions from strings into {@link Instruction} objects
 */
public class InstructionParser {

    /**
     * Parses a string into an {@link Instruction}
     *
     * @param s the string containing the instruction text
     * @return the parsed instruction
     *
     * @throws IOException if the string is malformed or contains no instruction
     */
    public Instruction parse(String s) throws IOException {
        String[] instArgs = s.split(" ");
        return switch (instArgs[0]) {
            case "FORWARD" -> parseForward(instArgs);

            default -> throw new IOException("No parseable instruction found");
        };
    }

    private ForwardInstruction parseForward(String[] args) {
        return null;
    }
}
