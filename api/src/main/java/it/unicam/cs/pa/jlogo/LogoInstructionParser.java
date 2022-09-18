package it.unicam.cs.pa.jlogo;

import it.unicam.cs.pa.jlogo.model.Canvas;
import it.unicam.cs.pa.jlogo.model.Instruction;
import it.unicam.cs.pa.jlogo.model.InstructionParser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
            case "HOME" -> Canvas::moveCursorToHome;
            case "PENUP" -> canvas -> canvas.getCursor().setPlotting(false);
            case "PENDOWN" -> canvas -> canvas.getCursor().setPlotting(true);
            case "SETPENCOLOR" -> parseSetPenColor(instArgs);
            case "SETFILLCOLOR" -> parseSetFillColor(instArgs);
            case "SETSCREENCOLOR" -> parseSetScreenColor(instArgs);
            case "SETPENSIZE" -> parseSetPenSize(instArgs);
            case "REPEAT" -> parseRepeat(s.split(" ", 3));

            default -> throw new IOException("\"" + instArgs[0] + "\" is not a valid instruction");
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
            return canvas -> canvas.getCursor().setFillColor(r, g, b);
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


    private Instruction parseRepeat(String[] args) throws IOException {
        try {
            int num = Integer.parseInt(args[1]);

            String commands = args[2].substring(args[2].indexOf('[') + 1, args[2].lastIndexOf(']')).trim();
            List<Instruction> instructions = parseCommands(commands);
            return new RepeatInstruction(num, instructions);
        } catch (Exception e) {
            return throwException(args);
        }
    }

    /**
     * Parses the list of commands of a {@link RepeatInstruction}
     */
    private List<Instruction> parseCommands(String commands) throws IOException {
        List<Instruction> instructions = new ArrayList<>();

        while (!commands.isEmpty()) {
            if (commands.startsWith("REPEAT")) {
                instructions.add(parse(commands.substring(0, commands.indexOf(']') + 1)));
                commands = commands.substring(commands.indexOf(']') + 2);
                continue;
            }
            String[] spilt = commands.split(";", 2);
            instructions.add(parse(spilt[0].trim()));
            commands = spilt[1].trim();
        }
        return instructions;
    }


    private Instruction throwException(String[] args) throws IOException {
        throw new IOException(
                "Wrong syntax for instruction \""
                        .concat(Arrays.stream(args).reduce((s, s2) -> s.concat(" ").concat(s2)).orElse(""))
                        .concat("\"")
        );
    }
}
