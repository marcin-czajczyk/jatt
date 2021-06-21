package com.czajczykmarcin.jatt.cmd;

import com.czajczykmarcin.jatt.cmd.console.ConsoleImpl;
import com.czajczykmarcin.jatt.cmd.console.ConsoleService;
import com.czajczykmarcin.jatt.core.FrequencyAnalyzerService;
import com.czajczykmarcin.jatt.core.request.CaseMode;
import com.czajczykmarcin.jatt.core.request.CharacterOrder;
import com.czajczykmarcin.jatt.core.service.FrequencyAnalyzerServiceImpl;

import static com.czajczykmarcin.jatt.cmd.console.ConsoleConstants.DEFAULT_LOGIC_WORD;
import static com.czajczykmarcin.jatt.cmd.console.ConsoleConstants.DEFAULT_TEXT;

public class Jatt {

    private static final FrequencyAnalyzerService frequencyAnalyzerService = new FrequencyAnalyzerServiceImpl();

    public static void main(String[] args) {
        new ConsoleService(
                DEFAULT_LOGIC_WORD,
                DEFAULT_TEXT,
                args,
                frequencyAnalyzerService::processAscii,
                new ConsoleImpl(System.in, System.out),
                CaseMode.LOWERCASE,
                CharacterOrder.INPUT)
                .run();
    }
}
