package it.unicam.cs.pa.jlogo;

import it.unicam.cs.pa.jlogo.instructions.RepeatInstruction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class LogoInstructionParserTest {

    private LogoInstructionParser parser;


    @BeforeEach
    void setUp() {
        parser = new LogoInstructionParser();
    }

    @Test
    void parseRepeatTest() throws IOException {
        assertThrows(IllegalArgumentException.class, () -> parser.parse("REPEAT 3 []"));
        assertThrows(IllegalArgumentException.class, () -> parser.parse("REPEAT -3 [ FORWARD 89; SETFILLCOLOR 3 3 3; ]"));
        assertThrows(IOException.class, () -> parser.parse("REPEAT 3 [ FORWRD 89; ]"));
        assertInstanceOf(RepeatInstruction.class, parser.parse("REPEAT 3 [ FORWARD 89; SETFILLCOLOR 3 3 3; ]"));
        assertInstanceOf(RepeatInstruction.class, parser.parse("REPEAT 3 [ FORWARD 89; REPEAT 9 [ CLEARSCREEN; ]; ]"));
    }
}