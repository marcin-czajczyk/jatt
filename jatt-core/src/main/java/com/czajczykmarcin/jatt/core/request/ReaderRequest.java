package com.czajczykmarcin.jatt.core.request;

import com.czajczykmarcin.jatt.core.Request;
import lombok.Getter;

import java.io.BufferedReader;
import java.io.Reader;

@Getter
public class ReaderRequest implements Request<String, Reader> {

    private final String keyWord;
    private final Reader input;
    private final CaseMode caseMode;
    private final CharacterOrder characterOrder;

    public ReaderRequest(String keyWord, Reader input, CaseMode caseMode, CharacterOrder characterOrder) {
        this.keyWord = keyWord;
        this.input = input;
        this.caseMode = caseMode;
        this.characterOrder = characterOrder;
    }
}
