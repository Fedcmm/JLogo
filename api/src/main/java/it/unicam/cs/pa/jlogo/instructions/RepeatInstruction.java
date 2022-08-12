package it.unicam.cs.pa.jlogo.instructions;

import it.unicam.cs.pa.jlogo.model.Canvas;
import it.unicam.cs.pa.jlogo.model.Instruction;

public class RepeatInstruction implements Instruction {

    private final int num;


    public RepeatInstruction(int num) {
        this.num = num;
    }


    @Override
    public void execute(Canvas canvas) {
        // TODO: 08/08/22 List or LogoProgram?
    }
}
