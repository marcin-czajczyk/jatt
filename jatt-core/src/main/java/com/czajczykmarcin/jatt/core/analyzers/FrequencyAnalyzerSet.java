package com.czajczykmarcin.jatt.core.analyzers;

import com.czajczykmarcin.jatt.core.CharacterAnalyzer;
import com.czajczykmarcin.jatt.core.KeyCharacters;
import com.czajczykmarcin.jatt.core.Response;
import com.czajczykmarcin.jatt.core.context.ProcessContextSet;
import com.czajczykmarcin.jatt.core.dto.Counter;
import com.czajczykmarcin.jatt.core.dto.Result;
import com.czajczykmarcin.jatt.core.request.CaseMode;
import com.czajczykmarcin.jatt.core.response.Occurrence;
import com.czajczykmarcin.jatt.core.response.ResponseImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.czajczykmarcin.jatt.core.util.CharacterUtil.caseConverter;
import static java.util.Objects.requireNonNull;

public class FrequencyAnalyzerSet extends AbstractFrequencyAnalyzer<ProcessContextSet> {

    private final CharacterAnalyzer characterAnalyzer;

    public FrequencyAnalyzerSet(CharacterAnalyzer characterAnalyzer) {
        this.characterAnalyzer = requireNonNull(characterAnalyzer);
    }

    @Override
    protected ProcessContextSet createProcessContext(KeyCharacters keyCharacters, CaseMode caseMode) {
        return new ProcessContextSet(keyCharacters, caseMode);
    }

    @Override
    protected void processNextWord(final ProcessContextSet processContext) {
        storeWord(processContext);
        processContext.getTotalCount().add(processContext.getWordSize());
        processContext.getWordSize().reset();
        processContext.getCountByKeyCharacter().forEach((integer, counter) -> counter.reset());
    }

    @Override
    protected void processSupportedCharacter(final ProcessContextSet processContext, final int character) {
        processContext.getWordSize().increment();
        final int characterConverted = caseConverter(character, processContext.getCaseMode());
        if (processContext.getKeyCharacters().contains(characterConverted)) {
            processContext.getKeyCharactersTotalCount().increment();
            processContext.getCountByKeyCharacter().get(characterConverted).increment();
        }
    }

    @Override
    protected Response createResponse(ProcessContextSet processContext) {
        List<Occurrence> occurrences = new ArrayList<>();
        Map<Integer, Result> resultByCharacter = processContext.getResultByCharacter();
        List<Integer> sortedCharacters = processContext.getKeyCharacters().getSorted();
        for (Integer character : processContext.getKeyCharacters().getSorted()) {
            processResult(resultByCharacter.get(character), occurrences, sortedCharacters);
        }
        return new ResponseImpl(occurrences, processContext.getTotalCount().get(), processContext.getKeyCharactersTotalCount().get());
    }

    @Override
    protected CharacterAnalyzer getCharacterAnalyzer() {
        return characterAnalyzer;
    }

    private void storeWord(final ProcessContextSet processContext) {
        Result result = null;
        Counter sum = null;
        var order = 0;
        for (Integer character : processContext.getKeyCharacters().getSorted()) {
            var counter = processContext.getCountByKeyCharacter().get(character);
            if (counter.isNonZero()) {
                if (result == null) {
                    int o = order;
                    result = processContext.getResultByCharacter().computeIfAbsent(character, c -> new Result(String.valueOf(Character.toChars(c)), o));
                    sum = new Counter(counter.get());
                }
                else {
                    result = result.getSubResult(character, order);
                    sum.add(counter);
                }
            }
            order++;
        }
        if (result != null) {
            result.addKeyCharactersCount(processContext.getWordSize().createCopy(), sum);
        }
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
                    .forEach(entry -> occurrences.add(new Occurrence(result.getKey(), result.getOrder(), entry.getKey().get(), entry.getValue().get())));
        }
        if (result.getSubResultByCharacter() != null) {
            for (Integer character : sortedCharacters) {
                processResult(result.getSubResultByCharacter().get(character), occurrences, sortedCharacters);
            }
        }
    }
}
