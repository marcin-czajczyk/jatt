package com.czajczykmarcin.jatt.core.context;

import com.czajczykmarcin.jatt.core.KeyCharacters;
import com.czajczykmarcin.jatt.core.ProcessContext;
import com.czajczykmarcin.jatt.core.dto.Counter;
import com.czajczykmarcin.jatt.core.dto.Result;
import com.czajczykmarcin.jatt.core.request.CaseMode;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public class ProcessContextSet implements ProcessContext {

    private final KeyCharacters keyCharacters;
    private final CaseMode caseMode;
    private final Map<Integer, Counter> countByKeyCharacter;

    private final Counter totalCount = new Counter();
    private final Counter keyCharactersTotalCount = new Counter();
    private final Counter wordSize = new Counter();

    private final Map<Integer, Result> resultByCharacter;

    public ProcessContextSet(KeyCharacters keyCharacters, CaseMode caseMode) {
        this.keyCharacters = keyCharacters;
        this.caseMode = caseMode;
        this.countByKeyCharacter = keyCharacters.createCounterMap();
        this.resultByCharacter = new HashMap<>(keyCharacters.size());
    }

}
