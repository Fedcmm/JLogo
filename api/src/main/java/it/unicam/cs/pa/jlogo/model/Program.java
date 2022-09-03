package it.unicam.cs.pa.jlogo.model;

import java.util.NoSuchElementException;

/**
 * Represents a Logo program that can return its instructions one by one
 */
public interface Program {

    /**
     * @return the next instruction in this program
     *
     * @throws NoSuchElementException if this program has no more instructions
     */
    Instruction next();

    /**
     * Checks if this program has more instructions
     *
     * @return <code>true</code> if there is at least one instruction that can be
     * returned by {@link Program#next()}, <code>false</code> otherwise
     */
    boolean hasNext();

    /**
     * Resets the execution of this program to the first instruction
     */
    void reset();
}
