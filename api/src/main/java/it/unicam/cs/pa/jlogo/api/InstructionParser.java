package it.unicam.cs.pa.jlogo.api;

/**
 * This class parses Logo instructions from strings into {@link Instruction} objects
 */
public class InstructionParser {

    /**
     * Parses a string into an {@link Instruction}
     *
     * @param s the string containing the instruction text
     * @return the parsed instruction, or <code>null</code> if parsing failed
     */
    public Instruction parse(String s) {
        String[] instParts = s.split(" ");
        return switch (instParts[0]) {
            //case "FORWARD" -> ;
            default -> null;
        };
    }
}
