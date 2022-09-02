package it.unicam.cs.pa.jlogo.instructions;

import it.unicam.cs.pa.jlogo.model.Canvas;
import it.unicam.cs.pa.jlogo.model.Instruction;

import java.util.List;
import java.util.Objects;

public class RepeatInstruction implements Instruction {

    private final int num;
    private final List<Instruction> instructions;


    /**
     * Creates a new repeat instruction
     *
     * @param num the number of repetitions
     * @param instructions the instructions to repeat
     *
     * @throws IllegalArgumentException if num is less than 0 or instructions
     * is empty
     * @throws NullPointerException if instructions is <code>null</code>
     */
    public RepeatInstruction(int num, List<Instruction> instructions) {
        if (Objects.requireNonNull(instructions).isEmpty() || num <= 0)
            throw new IllegalArgumentException();

        this.num = num;
        this.instructions = instructions;
    }


    @Override
    public void execute(Canvas canvas) {
        for (int i = num; i > 0; i--) {
            instructions.forEach(instruction -> instruction.execute(canvas));
        }
    }
}
