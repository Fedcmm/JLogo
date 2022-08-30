package it.unicam.cs.pa.jlogo.instructions;

import it.unicam.cs.pa.jlogo.model.Canvas;
import it.unicam.cs.pa.jlogo.model.Instruction;

import java.util.List;

public class RepeatInstruction implements Instruction {

    private final int num;
    private final List<Instruction> instructions;


    public RepeatInstruction(int num, List<Instruction> instructions) {
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
