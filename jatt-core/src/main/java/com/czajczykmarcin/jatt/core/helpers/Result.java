package com.czajczykmarcin.jatt.core.helpers;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public class Result {

    private final String key;

    private final int order;

    private Map<Counter, Counter> countByWordSize;

    private Map<Integer, Result> subResultByCharacter;

    public Result(String key, int order) {
        this.key = key;
        this.order = order;
    }

    public Result(String key, int order, Counter wordSize, Counter keyCharactersCount) {
        this(key, order);
        countByWordSize.put(wordSize, keyCharactersCount);
    }

    public void addKeyCharactersCount(Counter wordSize, Counter keyCharactersCount) {
        //todo lazy
        if (countByWordSize == null) {
            countByWordSize = new HashMap<>();
        }
        countByWordSize.computeIfAbsent(wordSize, k -> new Counter()).add(keyCharactersCount);
    }

    public Result getSubResult(final int character, final int order) {
        //todo lazy
        if (subResultByCharacter == null) {
            subResultByCharacter = new HashMap<>();
        }
        return subResultByCharacter.computeIfAbsent(character, c -> new Result(key + ", " + String.valueOf(Character.toChars(c)), order));
    }

}
