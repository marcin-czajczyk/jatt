package com.czajczykmarcin.jatt.core.analyzers;

import com.czajczykmarcin.jatt.core.*;
import com.czajczykmarcin.jatt.core.request.CaseMode;

public abstract class AbstractFrequencyAnalyzer<P extends ProcessContext> implements FrequencyAnalyzer<Request<String, String>> {

    @Override
    public Response analyse(final KeyCharacters keyCharacters, final Request<String, String> request) {
        validate(keyCharacters, request);
        var processContext = createProcessContext(keyCharacters, request.getCaseMode());
        request.getInput()
                .codePoints()
                .forEach(character -> process(processContext, character));
        processNextWord(processContext);
        return createResponse(processContext);
    }

    protected void validate(KeyCharacters keyCharacters, Request<String, String> request) {
    }

    protected abstract P createProcessContext(final KeyCharacters keyCharacters, CaseMode caseMode);

    protected void process(final P processContext, final int character) {
        switch (getCharacterAnalyzer().check(character)) {
            case NEXT_WORD:
                processNextWord(processContext);
                break;
            case PROCESS:
                processSupportedCharacter(processContext, character);
                break;
            case SKIP:
            default:
        }
    }

    protected abstract void processNextWord(final P processContext);

    protected abstract void processSupportedCharacter(final P processContext, final int character);

    protected abstract Response createResponse(P processContext);

    protected abstract CharacterAnalyzer getCharacterAnalyzer();

}
