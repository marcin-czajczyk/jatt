package com.czajczykmarcin.jatt.core.analyzers;

import com.czajczykmarcin.jatt.core.KeyCharacters;
import com.czajczykmarcin.jatt.core.Request;
import com.czajczykmarcin.jatt.core.Response;
import com.czajczykmarcin.jatt.core.context.ProcessContextOne;
import com.czajczykmarcin.jatt.core.exceptions.UnsupportedKeyCharacters;
import com.czajczykmarcin.jatt.core.request.CaseMode;
import com.czajczykmarcin.jatt.core.response.Occurrence;
import com.czajczykmarcin.jatt.core.response.ResponseImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.czajczykmarcin.jatt.core.util.CharacterUtil.caseConverter;

public class SimpleAsciiFrequencyAnalyzerOne extends AsciiFrequencyAnalyzer<ProcessContextOne> {

    @Override
    protected void validate(KeyCharacters keyCharacters, Request<String, String> request) {
        if (keyCharacters == null || keyCharacters.size() != 1) {
            throw new UnsupportedKeyCharacters();
        }
    }

    @Override
    protected ProcessContextOne createProcessContext(KeyCharacters keyCharacters, CaseMode caseMode) {
        return new ProcessContextOne(keyCharacters.getSorted().get(0), caseMode);
    }

    @Override
    protected void processNextWord(final ProcessContextOne pc) {
        storeWord(pc);
        pc.getTotalCounter().add(pc.getWordSize());
        pc.getWordSize().reset();

    }

    @Override
    protected void processSupportedCharacter(final ProcessContextOne pc, final int character) {
        pc.getWordSize().increment();
        final int characterConverted = caseConverter(character, pc.getCaseMode());
        if (characterConverted == pc.getKeyCharacter()) {
            pc.getKeyCharacterCounter().increment();
        }
    }

    @Override
    protected Response createResponse(ProcessContextOne pc) {
        List<Occurrence> occurrences = new ArrayList<>();
        var result = pc.getResult();
        if (result.getCountByWordSize() != null) {
            result.getCountByWordSize()
                    .entrySet()
                    .stream()
                    .sorted(Map.Entry.comparingByKey())
                    .forEach(entry -> occurrences.add(new Occurrence(result.getKey(), entry.getKey().get(), entry.getValue().get())));
        }
        return new ResponseImpl(occurrences, pc.getTotalCounter().get(), pc.getTotalKeyCharacterCounter().get());
    }

    private void storeWord(final ProcessContextOne pc) {
        if (pc.getKeyCharacterCounter().isNonZero()) {
            pc.getResult()
                    .addKeyCharactersCount(pc.getWordSize().createCopy(), pc.getKeyCharacterCounter().createCopy());
            pc.getTotalKeyCharacterCounter()
                    .add(pc.getKeyCharacterCounter());
            pc.getKeyCharacterCounter()
                    .reset();
        }
    }
}
