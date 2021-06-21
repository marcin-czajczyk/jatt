package com.czajczykmarcin.jatt.core.analyzers;

import com.czajczykmarcin.jatt.core.*;
import com.czajczykmarcin.jatt.core.helpers.CodePointsIterator;
import com.czajczykmarcin.jatt.core.request.CaseMode;

import java.io.BufferedReader;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.StreamSupport;

public abstract class AbstractFrequencyAnalyzer<P extends ProcessContext> implements FrequencyAnalyzer<Request<String, BufferedReader>> {

    @Override
    public Response analyse(final KeyCharacters keyCharacters, final Request<String, BufferedReader> request) {
        validate(keyCharacters, request);
        var processContext = createProcessContext(keyCharacters, request.getCaseMode());

        StreamSupport.stream(
                Spliterators.spliteratorUnknownSize(new CodePointsIterator(request.getInput()), Spliterator.ORDERED), false)
                .forEach(character -> process(processContext, character));
        processNextWord(processContext);
        return createResponse(processContext);
    }

    protected void validate(KeyCharacters keyCharacters, Request<String, BufferedReader> request) {
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
