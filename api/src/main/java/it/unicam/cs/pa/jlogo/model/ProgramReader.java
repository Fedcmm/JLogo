package it.unicam.cs.pa.jlogo.model;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Classes implementing this interface can read a {@link Program} from a string
 */
public interface ProgramReader {

    /**
     * Reads a program from a string
     *
     * @param s the string with the program
     * @return a {@link Program} object
     *
     * @throws IOException if the string is malformed or the instructions
     * syntax is wrong
     */
    Program read(String s) throws IOException;

    /**
     * Reads a program from the file at the given {@link Path}
     *
     * @param path the path of the file with the instructions
     * @return a {@link Program} object
     *
     * @throws IOException if there are problems reading the file or the instructions
     * syntax is wrong
     */
    default Program read(Path path) throws IOException {
        return read(Files.readString(path));
    }

    /**
     * Reads a program from the given {@link File}
     *
     * @param file the file with the instructions
     * @return a {@link Program} object
     *
     * @throws IOException if there are problems reading the file or the instructions
     * syntax is wrong
     */
    default Program read(File file) throws IOException {
        return read(file.toPath());
    }
}
