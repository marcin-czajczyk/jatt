package com.czajczykmarcin.jatt.core.request;

import com.czajczykmarcin.jatt.core.Request;
import lombok.Getter;

import java.io.BufferedReader;

@Getter
public class BufferReaderRequest implements Request<String, BufferedReader> {

    private final String keyWord;
    private final BufferedReader input;
    private final CaseMode caseMode;
    private final CharacterOrder characterOrder;

    public BufferReaderRequest(String keyWord, BufferedReader input, CaseMode caseMode, CharacterOrder characterOrder) {
        this.keyWord = keyWord;
        this.input = input;
        this.caseMode = caseMode;
        this.characterOrder = characterOrder;

    }
}
