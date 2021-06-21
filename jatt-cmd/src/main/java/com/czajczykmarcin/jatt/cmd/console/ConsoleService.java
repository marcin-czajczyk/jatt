package com.czajczykmarcin.jatt.cmd.console;

import com.czajczykmarcin.jatt.cmd.Console;
import com.czajczykmarcin.jatt.core.Response;
import com.czajczykmarcin.jatt.core.request.CaseMode;
import com.czajczykmarcin.jatt.core.request.CharacterOrder;
import com.czajczykmarcin.jatt.core.request.StringRequest;
import com.czajczykmarcin.jatt.core.response.Occurrence;
import com.czajczykmarcin.jatt.core.service.FrequencyAnalyzerService;
import org.apache.commons.collections4.CollectionUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Comparator;
import java.util.function.Function;

public class ConsoleService {

    private final String defaultLogicWord;
    private final String defaultText;
    private final Function<StringRequest, Response> analyzerService;
    private final Console console;
    private final String[] values;

    public ConsoleService(String defaultLogicWord, String defaultText, String[] values, Function<StringRequest, Response> analyzerService, Console console) {
        this.defaultLogicWord = defaultLogicWord;
        this.defaultText = defaultText;
        this.values = values;
        this.analyzerService = analyzerService;
        this.console = console;
    }

    public void run() {
        var logicWord = values.length > 0 ? values[0] : console.readLogicWord(defaultLogicWord);
        var text = values.length > 1 ? values[1] : console.readText(defaultText);
        var response = analyzerService.apply(new StringRequest(logicWord, text, CaseMode.LOWERCASE, CharacterOrder.INPUT));
        console.write(toConsoleString(response));
    }

    private String toConsoleString(Response response) {
        var sb = new StringBuilder();
        CollectionUtils
                .emptyIfNull(response.getOccurrences())
                .stream()
                .sorted(Comparator.comparing(Occurrence::getKeyCharactersCount)
                        .thenComparing(Occurrence::getKeyLength)
                        .thenComparing(Occurrence::getOrder)
                        .thenComparing(Occurrence::getWordSize)
                )
                .forEach(o -> toConsoleString(sb, o, response.getKeyCharactersTotalCount()) );
        sb.append("TOTAL Frequency: ");
        return appendFrequency(sb, response.getKeyCharactersTotalCount(), response.getTotalCount())
                .toString();

    }

    private void toConsoleString(StringBuilder sb, Occurrence o, long totalCount) {
        sb
                .append("{(")
                .append(o.getKey())
                .append("), ")
                .append(o.getWordSize())
                .append("} = ");
        appendFrequency(sb, o.getKeyCharactersCount(), totalCount)
                .append(System.lineSeparator());
    }

    private StringBuilder appendFrequency(StringBuilder sb, long count, long totalCount) {
        if (totalCount == 0L) {
            sb.append("0 as input is empty or doesn't contains any non-special character");
        }
        sb
                .append(calculateFrequency(count, totalCount))
                .append(" (")
                .append(count)
                .append("/")
                .append(totalCount)
                .append(")");
        return sb;
    }

    private BigDecimal calculateFrequency(long count, long totalCount) {
        return BigDecimal
                .valueOf(count)
                .divide(BigDecimal.valueOf(totalCount), 2, RoundingMode.HALF_UP);
    }
}
