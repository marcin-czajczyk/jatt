package com.czajczykmarcin.jatt.core.request;

import com.czajczykmarcin.jatt.core.Request;

public class StringRequest implements Request<String, String> {

    private final String keyWord;
    private final String input;
    private final CaseMode caseMode;

    public StringRequest(String keyWord, String input, CaseMode caseMode) {
        this.keyWord = keyWord;
        this.input = input;
        this.caseMode = caseMode;
    }

    @Override
    public String getKeyWord() {
        return keyWord;
    }

    @Override
    public String getInput() {
        return input;
    }

    @Override
    public CaseMode getCaseMode() {
        return caseMode;
    }
}
