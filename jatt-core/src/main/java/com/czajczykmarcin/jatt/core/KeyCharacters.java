package com.czajczykmarcin.jatt.core;

import com.czajczykmarcin.jatt.core.dto.Counter;

import java.util.List;
import java.util.Map;

public interface KeyCharacters {

    int size();

    boolean contains(int i);

    Map<Integer, Counter> createCounterMap();

    List<Integer> getSorted();
}
