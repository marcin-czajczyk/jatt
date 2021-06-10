package com.czajczykmarcin.jatt.core.request;

import com.czajczykmarcin.jatt.core.Request;
import lombok.Getter;

@Getter
public class StringRequest implements Request<String, String> {

    private final String keyWord;
    private final String input;
    private final CaseMode caseMode;
    private final CharacterOrder characterOrder;

    public StringRequest(String keyWord, String input, CaseMode caseMode, CharacterOrder characterOrder) {
        this.keyWord = keyWord;
        this.input = input;
        this.caseMode = caseMode;
        this.characterOrder = characterOrder;

    }
}
