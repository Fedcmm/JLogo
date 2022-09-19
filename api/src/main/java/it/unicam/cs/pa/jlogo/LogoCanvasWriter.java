package it.unicam.cs.pa.jlogo;

import it.unicam.cs.pa.jlogo.model.Canvas;
import it.unicam.cs.pa.jlogo.model.CanvasWriter;
import it.unicam.cs.pa.jlogo.model.ClosedArea;
import it.unicam.cs.pa.jlogo.model.Line;

import java.awt.Color;

/**
 * This class writes a canvas into a string with the following structure:
 *
 * <pre>{@code
 *      SIZE <w> <h> <r> <g> <b>
 *      <lines>
 *      <areas>
 * }</pre>
 *
 * The first line contains width and height of the canvas, followed by the component values
 * of the background color. Under that there are the lines contained in the canvas, formatted as
 *
 * <pre>{@code
 *      LINE <x1> <y1> <x2> <y2> <r> <g> <b> <size>
 * }</pre>
 *
 * where the first four values are the coordinates of the points of the line, followed by the color and
 * then the size.
 * At last, the string contains the closed areas of the canvas, printed as follows:
 *
 * <pre>{@code
 *      POLYGON <n> <r> <g> <b>
 *      <x0> <y0> <r0> <g0> <b0> <size0>
 *      <x1> <y1> <r1> <g1> <b1> <size1>
 *      ...
 * }</pre>
 *
 * with the first line containing the number of lines in the area and the fill color, then
 * a representation of all the lines
 */
public class LogoCanvasWriter implements CanvasWriter {

    @Override
    public String write(Canvas canvas) {
        return "SIZE " + canvas.getWidth() + " " + canvas.getHeight() + " " + colorToString(canvas.getBackColor()) +
                canvas.getLines().stream()
                        .map(this::lineToString)
                        .reduce(String::concat).orElse("") +
                canvas.getClosedAreas().stream()
                        .map(this::closedAreaToString)
                        .reduce(String::concat).orElse("");
    }

    private String colorToString(Color color) {
        return String.format("%d %d %d", color.getRed(), color.getGreen(), color.getBlue());
    }

    private String lineToString(Line line) {
        return String.format("\nLINE %.1f %.1f %.1f %.1f %s %d",
                line.getA().x(), line.getA().y(), line.getB().x(), line.getB().y(),
                colorToString(line.getColor()), line.getSize());
    }

    private String closedAreaToString(ClosedArea area) {
        return String.format("\nPOLYGON %d %s", area.getLines().size(), colorToString(area.getFillColor()))
                        .concat(
                                area.getLines().stream()
                                        .map(this::areaLineToString)
                                        .reduce(String::concat).orElse("")
                        );
    }

    private String areaLineToString(Line line) {
        return String.format("\n%.1f %.1f %s %d",
                line.getA().x(), line.getA().y(), colorToString(line.getColor()), line.getSize());
    }
}
