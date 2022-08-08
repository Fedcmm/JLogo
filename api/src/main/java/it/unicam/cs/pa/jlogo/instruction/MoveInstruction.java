package it.unicam.cs.pa.jlogo.instruction;

import it.unicam.cs.pa.jlogo.model.Canvas;
import it.unicam.cs.pa.jlogo.model.Instruction;

public class MoveInstruction implements Instruction {

    private final int distance;

    public MoveInstruction(int distance) {
        this.distance = distance;
    }

    @Override
    public void execute(Canvas canvas) {
        canvas.moveCursor(distance);
    }
}
