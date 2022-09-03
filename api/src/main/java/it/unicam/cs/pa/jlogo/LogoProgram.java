package it.unicam.cs.pa.jlogo;

import it.unicam.cs.pa.jlogo.model.Instruction;
import it.unicam.cs.pa.jlogo.model.Program;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * Instances of this class represent a Logo program, that is, a sequence of instructions
 * read from a file that are ready to be executed
 */
public class LogoProgram implements Program {

    private final List<Instruction> instructions;

    private int currentIndex = 0;


    /**
     * Creates a program with the given list of instructions
     *
     * @param instructions the instructions of the program
     *
     * @throws IllegalArgumentException if instructions is empty
     * @throws NullPointerException if instructions is null
     */
    public LogoProgram(List<Instruction> instructions) {
        if (Objects.requireNonNull(instructions).isEmpty())
            throw new IllegalArgumentException("Can't create a program with no instructions");

        this.instructions = instructions;
    }

    /**
     * Creates a program with the given instructions
     *
     * @param instructions the instructions of the program
     *
     * @throws IllegalArgumentException if instructions is empty
     * @throws NullPointerException if instructions is null
     */
    public LogoProgram(Instruction... instructions) {
        this(List.of(instructions));
    }


    @Override
    public Instruction next() {
        if (currentIndex >= instructions.size())
            throw new NoSuchElementException("The program has no more instructions");

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
