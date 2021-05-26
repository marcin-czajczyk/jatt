package com.czajczykmarcin.jatt.core.response;

public class Occurrence {

    private final String key;
    private final long wordSize;
    private final long keyCharactersCount;

    public Occurrence(String key, long wordSize, long keyCharactersCount) {
        this.key = key;
        this.wordSize = wordSize;
        this.keyCharactersCount = keyCharactersCount;
    }

    public String getKey() {
        return key;
    }

    public long getWordSize() {
        return wordSize;
    }

    public long getKeyCharactersCount() {
        return keyCharactersCount;
    }
}
