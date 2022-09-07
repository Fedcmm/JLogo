package it.unicam.cs.pa.jlogo;

import it.unicam.cs.pa.jlogo.model.Instruction;
import it.unicam.cs.pa.jlogo.model.InstructionParser;
import it.unicam.cs.pa.jlogo.model.Program;
import it.unicam.cs.pa.jlogo.model.ProgramReader;

import java.io.IOException;

/**
 * This class reads a program from a string with one instruction per line
 */
public class LogoProgramReader implements ProgramReader {

    private final InstructionParser parser;


    /**
     * Creates a new reader with the given {@link InstructionParser}
     *
     * @param parser the parser to parse instructions
     */
    public LogoProgramReader(InstructionParser parser) {
        this.parser = parser;
    }


    @Override
    public Program read(String s) throws IOException {
        String[] instArray = s.split("\n");

        Instruction[] instructions = new Instruction[instArray.length];
        for (int i = 0; i < instArray.length; i++) {
            try {
                instructions[i] = parser.parse(instArray[i]);
            } catch (IOException e) {
                throw new IOException("Syntax error at line " + (i + 1) + ": " + e.getMessage(), e);
            }
        }
        return new LogoProgram(instructions);
    }
}
