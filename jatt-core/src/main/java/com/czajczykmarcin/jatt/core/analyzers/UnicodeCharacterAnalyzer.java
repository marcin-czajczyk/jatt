package com.czajczykmarcin.jatt.core.analyzers;

import com.czajczykmarcin.jatt.core.*;

import static com.czajczykmarcin.jatt.core.CharacterAnalyzer.Result.*;

public class UnicodeCharacterAnalyzer implements CharacterAnalyzer {

    public Result check(final int character) {
        if (character == ' ') {
            return NEXT_WORD;
        }
        if (Character.isAlphabetic(character) || Character.isDigit(character)) {
            return PROCESS;
        }
        return SKIP;
    }

}
