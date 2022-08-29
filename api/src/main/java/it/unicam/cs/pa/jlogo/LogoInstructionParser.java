package it.unicam.cs.pa.jlogo;

import it.unicam.cs.pa.jlogo.instructions.RepeatInstruction;
import it.unicam.cs.pa.jlogo.model.Canvas;
import it.unicam.cs.pa.jlogo.model.Instruction;
import it.unicam.cs.pa.jlogo.model.InstructionParser;

import java.io.IOException;
import java.util.Arrays;

/**
 * This class can parse the standard set of Logo instructions
 */
public class LogoInstructionParser implements InstructionParser {

    @Override
    public Instruction parse(String s) throws IOException {
        String[] instArgs = s.split(" ");
        return switch (instArgs[0]) {
            case "FORWARD" -> parseForward(instArgs);
            case "BACKWARD" -> parseBackward(instArgs);
            case "LEFT" -> parseLeft(instArgs);
            case "RIGHT" -> parseRight(instArgs);
            case "CLEARSCREEN" -> Canvas::clear;
            // TODO: 29/08/22 HOME
            case "PENUP" -> canvas -> canvas.getCursor().setPlotting(false);
            case "PENDOWN" -> canvas -> canvas.getCursor().setPlotting(true);
            case "SETPENCOLOR" -> parseSetPenColor(instArgs);
            case "SETFILLCOLOR" -> parseSetFillColor(instArgs);
            case "SETSCREENCOLOR" -> parseSetScreenColor(instArgs);
            case "SETPENSIZE" -> parseSetPenSize(instArgs);
            case "REPEAT" -> parseRepeat(s.split("\n"));

            default -> throw new IOException("No parseable instruction found");
        };
    }

    private Instruction parseForward(String[] args) throws IOException {
        try {
            int dist = Integer.parseInt(args[1]);
            return canvas -> canvas.moveCursor(dist);
        } catch (Exception e) {
            return throwException(args);
        }
    }

    private Instruction parseBackward(String[] args) throws IOException {
        try {
            int dist = -(Integer.parseInt(args[1]));
            return canvas -> canvas.moveCursor(dist);
        } catch (Exception e) {
            return throwException(args);
        }
    }

    private Instruction parseLeft(String[] args) throws IOException {
        try {
            int degrees = Integer.parseInt(args[1]);
            return canvas -> canvas.getCursor().rotate(degrees);
        } catch (Exception e) {
            return throwException(args);
        }
    }

    private Instruction parseRight(String[] args) throws IOException {
        try {
            int degrees = -(Integer.parseInt(args[1]));
            return canvas -> canvas.getCursor().rotate(degrees);
        } catch (Exception e) {
            return throwException(args);
        }
    }

    private Instruction parseSetPenColor(String[] args) throws IOException{
        try {
            int r = Integer.parseInt(args[1]);
            int g = Integer.parseInt(args[2]);
            int b = Integer.parseInt(args[3]);
            return canvas -> canvas.getCursor().setLineColor(r, g, b);
        } catch (Exception e) {
            return throwException(args);
        }
    }

    private Instruction parseSetFillColor(String[] args) throws IOException{
        try {
            int r = Integer.parseInt(args[1]);
            int g = Integer.parseInt(args[2]);
            int b = Integer.parseInt(args[3]);
            return canvas -> canvas.getCursor().setAreaColor(r, g, b);
        } catch (Exception e) {
            return throwException(args);
        }
    }

    private Instruction parseSetScreenColor(String[] args) throws IOException{
        try {
            int r = Integer.parseInt(args[1]);
            int g = Integer.parseInt(args[2]);
            int b = Integer.parseInt(args[3]);
            return canvas -> canvas.setBackColor(r, g, b);
        } catch (Exception e) {
            return throwException(args);
        }
    }

    private Instruction parseSetPenSize(String[] args) throws IOException {
        try {
            int size = Integer.parseInt(args[1]);
            return canvas -> canvas.getCursor().setPenSize(size);
        } catch (Exception e) {
            return throwException(args);
        }
    }

    private RepeatInstruction parseRepeat(String[] args) {
        int num = Integer.parseInt(args[0].split(" ")[1]);
        for (int i = 1; i < args.length - 1; i++) {
        }
        return null;
    }

    private Instruction throwException(String[] args) throws IOException {
        throw new IOException(
                "Wrong syntax for instruction \""
                        .concat(Arrays.stream(args).reduce(String::concat).orElse(""))
                        .concat("\"")
        );
    }
}
