package it.unicam.cs.pa.jlogo.model;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Interface for writing the contents of a {@link Canvas} into a string
 */
public interface CanvasWriter {

    /**
     * Returns the drawings of the given canvas as a string
     *
     * @param canvas a canvas
     * @return a string representing all the drawing in the canvas
     */
    String write(Canvas canvas);

    /**
     * Writes the drawings of a canvas in the file specified by the given path as a string
     *
     * @param canvas a canvas
     * @param path the path of the file where to write the canvas
     *
     * @throws IOException if an I/O error occurs while writing to the file
     */
    default void write(Canvas canvas, Path path) throws IOException {
        Files.write(path, write(canvas).getBytes());
    }

    /**
     * Writes the drawings of a canvas in the specified file
     *
     * @param canvas a canvas
     * @param file the file where to write the canvas
     *
     * @throws IOException if an I/O error occurs while writing to the file
     */
    default void write(Canvas canvas, File file) throws IOException {
        write(canvas, file.toPath());
    }
}
