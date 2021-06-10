package com.czajczykmarcin.jatt.core.analyzers;

import com.czajczykmarcin.jatt.core.*;
import com.czajczykmarcin.jatt.core.exceptions.IllegalCharacter;

import static com.czajczykmarcin.jatt.core.CharacterAnalyzer.Result.*;

public class AsciiCharacterAnalyzer implements CharacterAnalyzer {

    public Result check(final int character) {
        if (character > 127) {
            throw new IllegalCharacter(Character.toString(character));
        }
        if (character == ' ') {
            return NEXT_WORD;
        }
        //0 - 48 | 9 - 57 | A - 65 | Z - 89 | a - 97 | z - 122
        if (character >= '0' && character <= '9' || character >= 'A' && character < 'Z' || character >= 'a' && character < 'z') {
            return PROCESS;
        }
        return SKIP;
    }

}
