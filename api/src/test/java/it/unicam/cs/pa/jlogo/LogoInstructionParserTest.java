package it.unicam.cs.pa.jlogo;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class LogoInstructionParserTest {

    @Test
    void parseRepeatTest() throws IOException {
        LogoInstructionParser parser = new LogoInstructionParser();

        assertThrows(IOException.class, () -> parser.parse("REPEAT 3 []"));
        assertThrows(IOException.class, () -> parser.parse("REPEAT -3 [ FORWARD 89; SETFILLCOLOR 3 3 3; ]"));
        assertThrows(IOException.class, () -> parser.parse("REPEAT 3 [ FORWRD 89; ]"));

        assertInstanceOf(RepeatInstruction.class, parser.parse("REPEAT 3 [ FORWARD 89; SETFILLCOLOR 3 3 3; ]"));
        assertInstanceOf(RepeatInstruction.class, parser.parse("REPEAT 3 [ FORWARD 89; REPEAT 9 [ CLEARSCREEN; ]; ]"));
    }
}