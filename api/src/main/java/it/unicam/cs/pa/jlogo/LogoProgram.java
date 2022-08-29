package it.unicam.cs.pa.jlogo;

import it.unicam.cs.pa.jlogo.model.Instruction;
import it.unicam.cs.pa.jlogo.model.Program;

import java.util.List;
import java.util.Objects;

/**
 * Instances of this class represent a Logo program, that is, a sequence of instructions
 * read from a file that are ready to be executed
 */
public class LogoProgram implements Program {

    private final List<Instruction> instructions;

    private int currentIndex = 0;


    public LogoProgram(List<Instruction> instructions) {
        this.instructions = Objects.requireNonNull(instructions);
    }

    public LogoProgram(Instruction... instructions) {
        this(List.of(instructions));
    }


    @Override
    public Instruction next() {
        return instructions.get(currentIndex++);
    }

    @Override
    public boolean hasNext() {
        return currentIndex != instructions.size();
    }

    @Override
    public void reset() {
        currentIndex = 0;
    }
}
