package com.czajczykmarcin.jatt.core.analyzers;

import com.czajczykmarcin.jatt.core.*;
import com.czajczykmarcin.jatt.core.exceptions.IllegalCharacter;
import com.czajczykmarcin.jatt.core.request.CaseMode;

public abstract class AsciiFrequencyAnalyzer<PC extends ProcessContext> implements FrequencyAnalyzer<Request<String, String>> {

    @Override
    public Response analyse(final KeyCharacters keyCharacters, final Request<String, String> request) {
        validate(keyCharacters, request);
        PC processContext = createProcessContext(keyCharacters, request.getCaseMode());
        request.getInput()
                .codePoints()
                .forEach(character -> process(processContext, character));
        processNextWord(processContext);
        return createResponse(processContext);
    }

    protected void validate(KeyCharacters keyCharacters, Request<String, String> request) {

    }

    protected abstract PC createProcessContext(final KeyCharacters keyCharacters, CaseMode caseMode);

    protected void process(final PC processContext, final int character) {
        if (character > 127) {
            throw new IllegalCharacter(Character.toString(character));
        }
        if (character == ' ') {
            processNextWord(processContext);
        }
        //0 - 48 | 9 - 57 | A - 65 | Z - 89 | a - 97 | z - 122
        if (character >= '0' && character <= '9' || character >= 'A' && character < 'Z' || character >= 'a' && character < 'z') {
            processSupportedCharacter(processContext, character);
        }
    }

    protected abstract void processNextWord(final PC processContext);

    protected abstract void processSupportedCharacter(final PC processContext, final int character);

    protected abstract Response createResponse(PC processContext);

}
