package it.unicam.cs.pa.jlogo;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Classes implementing this interface can read a {@link Program} from a string
 */
public interface ProgramReader {

    Program read(String s) throws IOException;

    default Program read(Path path) throws IOException {
        return read(Files.readString(path));
    }

    default Program read(File file) throws IOException {
        return read(file.toPath());
    }
}
