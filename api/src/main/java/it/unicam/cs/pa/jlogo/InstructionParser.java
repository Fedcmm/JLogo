package it.unicam.cs.pa.jlogo;

import it.unicam.cs.pa.jlogo.instructions.MoveInstruction;
import it.unicam.cs.pa.jlogo.instructions.RepeatInstruction;
import it.unicam.cs.pa.jlogo.model.Instruction;

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
            case "BACKWARD" -> parseBackward(instArgs);
            case "REPEAT" -> parseRepeat(s.split("\n"));

            default -> throw new IOException("No parseable instruction found");
        };
    }

    // TODO: 08/08/22 Exceptions

    private MoveInstruction parseForward(String[] args) {
        return new MoveInstruction(Integer.parseInt(args[1]));
    }

    private MoveInstruction parseBackward(String[] args) {
        return new MoveInstruction(-(Integer.parseInt(args[1])));
    }

    private RepeatInstruction parseRepeat(String[] args) {
        int num = Integer.parseInt(args[0].split(" ")[1]);
        for (int i = 1; i < args.length - 1; i++) {
            // TODO: 08/08/22 List or LogoProgram?
        }
        return null;
    }
}
