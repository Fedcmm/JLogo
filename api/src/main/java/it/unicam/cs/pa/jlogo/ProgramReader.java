package it.unicam.cs.pa.jlogo;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

/**
 * This class reads a sequence of commands from a file
 */
public interface ProgramReader {

    List<Instruction> read(String s) throws IOException;

    default List<Instruction> read(Path path) throws IOException {
        return read(Files.readString(path));
    }

    default List<Instruction> read(File file) throws IOException {
        return read(file.toPath());
    }
}
