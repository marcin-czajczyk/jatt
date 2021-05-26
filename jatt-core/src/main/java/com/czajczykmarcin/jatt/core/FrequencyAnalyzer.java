package com.czajczykmarcin.jatt.core;

public interface FrequencyAnalyzer<T> {

    Response analyse(KeyCharacters keyCharacters, T request);
}
