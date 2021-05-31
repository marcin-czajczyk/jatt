package com.czajczykmarcin.jatt.core.response;

import lombok.Getter;

@Getter
public class Occurrence {

    private final String key;
    private final int keyLength;
    private final long wordSize;
    private final long keyCharactersCount;

    public Occurrence(String key, long wordSize, long keyCharactersCount) {
        this.key = key;
        this.keyLength = key == null ? 0 : key.length();
        this.wordSize = wordSize;
        this.keyCharactersCount = keyCharactersCount;
    }


}
