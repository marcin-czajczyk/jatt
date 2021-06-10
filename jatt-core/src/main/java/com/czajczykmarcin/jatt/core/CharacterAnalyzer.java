package com.czajczykmarcin.jatt.core;

public interface CharacterAnalyzer {

    enum Result {
        NEXT_WORD, PROCESS, SKIP
    }

    Result check(final int character);

}
