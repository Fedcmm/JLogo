package it.unicam.cs.pa.jlogo.instructions;

import it.unicam.cs.pa.jlogo.LogoProgram;
import it.unicam.cs.pa.jlogo.model.Canvas;
import it.unicam.cs.pa.jlogo.model.Instruction;

public class RepeatInstruction implements Instruction {

    private final int num;
    private final LogoProgram instructions;


    public RepeatInstruction(int num, LogoProgram instructions) {
        this.num = num;
        this.instructions = instructions;
    }


    @Override
    public void execute(Canvas canvas) {
        for (int i = num; i > 0; i--) {
            while (instructions.hasNext()) {
                instructions.next().execute(canvas);
            }
            instructions.reset();
        }
    }
}
