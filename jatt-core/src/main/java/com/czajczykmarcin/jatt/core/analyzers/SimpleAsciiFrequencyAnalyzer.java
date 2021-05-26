package com.czajczykmarcin.jatt.core.analyzers;

import com.czajczykmarcin.jatt.core.FrequencyAnalyzer;
import com.czajczykmarcin.jatt.core.Response;
import com.czajczykmarcin.jatt.core.context.ProcessContext;
import com.czajczykmarcin.jatt.core.helpers.Counter;
import com.czajczykmarcin.jatt.core.helpers.Result;
import com.czajczykmarcin.jatt.core.request.StringRequest;
import com.czajczykmarcin.jatt.core.response.EmptyResponse;
import com.czajczykmarcin.jatt.core.response.Occurrence;
import com.czajczykmarcin.jatt.core.response.ResponseImpl;
import com.czajczykmarcin.jatt.core.exceptions.IllegalCharacter;
import com.czajczykmarcin.jatt.core.helpers.AsciiKeyCharactersHelper;
import org.apache.commons.lang3.StringUtils;

import java.util.*;

import static com.czajczykmarcin.jatt.core.messages.ErrorMessages.KEY_WORD_NULL_OR_EMPTY;
import static com.czajczykmarcin.jatt.core.util.CharacterUtil.caseConverter;

public class SimpleAsciiFrequencyAnalyzer implements FrequencyAnalyzer<StringRequest> {

    //TODO make as constructor or function parameter
    private final ResourceBundle messages = ResourceBundle.getBundle("messages");
    private final AsciiKeyCharactersHelper keyCharactersHelper = new AsciiKeyCharactersHelper();

    @Override
    public Response analyse(final StringRequest request) {
        validateKeyWord(request.getKeyWord());
        if (StringUtils.isEmpty(request.getInput())) {
            return EmptyResponse.INSTANCE;
        }
        final var processContext = new ProcessContext(keyCharactersHelper.create(request.getKeyWord(), request.getCaseMode()), request.getCaseMode());
        request.getInput()
                .codePoints()
                .forEach(character -> process(processContext, character));
        storeWord(processContext);
        processContext.getTotalCount().add(processContext.getWordSize());
        return createResponse(processContext);
    }

    private void process(final ProcessContext processContext, final int character) {
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

    private void processNextWord(final ProcessContext processContext) {
        storeWord(processContext);
        processContext.getTotalCount().add(processContext.getWordSize());
        processContext.getWordSize().reset();
        processContext.getCountByKeyCharacter().forEach((integer, counter) -> counter.reset());;
    }

    private void storeWord(final ProcessContext processContext) {
        Result result = null;
        Counter sum = null;
        for (Integer character : processContext.getKeyCharacters().getSorted()) {
            var counter = processContext.getCountByKeyCharacter().get(character);
            if (counter.isNonZero()) {
                if (result == null) {
                    result = processContext.getResultByCharacter().computeIfAbsent(character, c -> new Result(String.valueOf(Character.toChars(c))));
                    sum = new Counter(counter.get());
                }
                else {
                    result = result.getSubResult(character);
                    sum.add(counter);
                }
            }
        }
        if (result != null) {
            result.addKeyCharactersCount(processContext.getWordSize().clone(), sum);
        }
    }

    private void processSupportedCharacter(final ProcessContext processContext, final int character) {
        processContext.getWordSize().increment();
        final int characterConverted = caseConverter(character, processContext.getCaseMode());
        if (processContext.getKeyCharacters().contains(characterConverted)) {
            processContext.getKeyCharactersTotalCount().increment();
            processContext.getCountByKeyCharacter().get(characterConverted).increment();
        }
    }

    private void validateKeyWord(String keyWord) {
        if (StringUtils.isEmpty(keyWord)) {
            throw new IllegalArgumentException(messages.getString(KEY_WORD_NULL_OR_EMPTY));
        }
    }

    private Response createResponse(ProcessContext processContext) {
        List<Occurrence> occurrences = new ArrayList<>();
        Map<Integer, Result> resultByCharacter = processContext.getResultByCharacter();
        List<Integer> sortedCharacters = processContext.getKeyCharacters().getSorted();
        for (Integer character : processContext.getKeyCharacters().getSorted()) {
            processResult(resultByCharacter.get(character), occurrences, sortedCharacters);
        }
        return new ResponseImpl(occurrences, processContext.getTotalCount().get(), processContext.getKeyCharactersTotalCount().get());
    }

    private void processResult(final Result result, final List<Occurrence> occurrences, List<Integer> sortedCharacters) {
        if (result == null) {
            return;
        }
        if (result.getCountByWordSize() != null) {
            result.getCountByWordSize()
                    .entrySet()
                    .stream()
                    .sorted(Map.Entry.comparingByKey())
                    .forEach(entry -> occurrences.add(new Occurrence(result.getKey(), entry.getKey().get(), entry.getValue().get())));
        }
        if (result.getSubResultByCharacter() != null) {
            for (Integer character : sortedCharacters) {
                processResult(result.getSubResultByCharacter().get(character), occurrences, sortedCharacters);
            }
        }
    }
}
