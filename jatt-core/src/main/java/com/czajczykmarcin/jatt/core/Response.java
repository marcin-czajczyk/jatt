package com.czajczykmarcin.jatt.core;

import com.czajczykmarcin.jatt.core.response.Occurrence;

import java.util.List;

public interface Response {

    List<Occurrence> getOccurrences();

    long getTotalCount();

    long getKeyCharactersTotalCount();

    boolean isEmpty();
}
