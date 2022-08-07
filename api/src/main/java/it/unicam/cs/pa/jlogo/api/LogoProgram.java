package it.unicam.cs.pa.jlogo.api;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Instances of this class represent a Logo program, that is, a sequence of instructions
 * read from a file that are ready to be executed
 */
public class LogoProgram {

    private final List<Instruction> instructions = new ArrayList<>();

    private int currentIndex = 0;


    /**
     * Creates a new instance of this class from the file at the given path
     *
     * @param path the path to the file containing the program
     */
    public LogoProgram(String path) {
        this(new File(path));
    }

    /**
     * Creates a new instance of this class from the given file
     *
     * @param file the file containing the program
     */
    public LogoProgram(File file) {

    }


    public Instruction next() {
        return instructions.get(currentIndex++);
    }

    public boolean hasNext() {
        return currentIndex != instructions.size();
    }
}
