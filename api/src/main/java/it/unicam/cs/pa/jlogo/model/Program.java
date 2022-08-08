package it.unicam.cs.pa.jlogo.model;

/**
 * Represents a Logo program that can return its instructions one by one
 */
public interface Program {

    /**
     * Returns the next instruction in this program
     *
     * @return the next instruction in this program
     */
    Instruction next();

    /**
     * Checks if this program has more instructions
     *
     * @return <code>true</code> if there is at least one instruction that can be
     * returned by {@link Program#next()}, <code>false</code> otherwise
     */
    boolean hasNext();
}
