package com.czajczykmarcin.jatt.core.helpers;

import java.util.HashMap;
import java.util.Map;

public class Result {

    private final String key;

    private Map<Counter, Counter> countByWordSize;

    private Map<Integer, Result> subResultByCharacter;

    public Result(String key) {
        this.key = key;
    }

    public Result(String key, Counter wordSize, Counter keyCharactersCount) {
        this(key);
        countByWordSize.put(wordSize, keyCharactersCount);
    }

    public void addKeyCharactersCount(Counter wordSize, Counter keyCharactersCount) {
        //todo lazy
        if (countByWordSize == null) {
            countByWordSize = new HashMap<>();
        }
        countByWordSize.computeIfAbsent(wordSize, k -> new Counter()).add(keyCharactersCount);
    }

    public Result getSubResult(int character) {
        //todo lazy
        if (subResultByCharacter == null) {
            subResultByCharacter = new HashMap<>();
        }
        return subResultByCharacter.computeIfAbsent(character, c -> new Result(key + ", " + String.valueOf(Character.toChars(c))));
    }

    public Map<Integer, Result> getSubResultByCharacter() {
        return subResultByCharacter;
    }

    public Map<Counter, Counter> getCountByWordSize() {
        return countByWordSize;
    }

    public String getKey() {
        return key;
    }
}
