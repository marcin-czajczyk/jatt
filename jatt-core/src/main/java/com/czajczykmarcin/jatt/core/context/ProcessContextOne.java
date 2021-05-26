package com.czajczykmarcin.jatt.core.context;

import com.czajczykmarcin.jatt.core.ProcessContext;
import com.czajczykmarcin.jatt.core.helpers.Counter;
import com.czajczykmarcin.jatt.core.helpers.Result;
import com.czajczykmarcin.jatt.core.request.CaseMode;
import lombok.Getter;

@Getter
public class ProcessContextOne implements ProcessContext {

    private final int keyCharacter;
    private final CaseMode caseMode;

    private final Counter keyCharacterCounter = new Counter();
    private final Counter totalCounter = new Counter();
    private final Counter totalKeyCharacterCounter = new Counter();
    private final Counter wordSize = new Counter();

    private final Result result;

    public ProcessContextOne(int keyCharacter, CaseMode caseMode) {
        this.keyCharacter = keyCharacter;
        this.caseMode = caseMode;
        this.result = new Result(String.valueOf(Character.toChars(keyCharacter)));
    }

}
