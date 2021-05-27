package com.czajczykmarcin.jatt.core.service;

import com.czajczykmarcin.jatt.core.Request;
import com.czajczykmarcin.jatt.core.Response;
import com.czajczykmarcin.jatt.core.analyzers.SimpleAsciiFrequencyAnalyzerOne;
import com.czajczykmarcin.jatt.core.analyzers.SimpleAsciiFrequencyAnalyzerSet;
import com.czajczykmarcin.jatt.core.helpers.AsciiKeyCharactersHelper;
import com.czajczykmarcin.jatt.core.response.EmptyResponse;
import org.apache.commons.lang3.StringUtils;

import java.util.ResourceBundle;

import static com.czajczykmarcin.jatt.core.messages.ErrorMessages.KEY_WORD_NULL_OR_EMPTY;

public class FrequencyAnalyzerService {

    //TODO make as constructor or function parameter
    private final ResourceBundle messages = ResourceBundle.getBundle("messages");
    private final AsciiKeyCharactersHelper keyCharactersHelper = new AsciiKeyCharactersHelper();
    private final SimpleAsciiFrequencyAnalyzerOne one = new SimpleAsciiFrequencyAnalyzerOne();
    private final SimpleAsciiFrequencyAnalyzerSet set = new SimpleAsciiFrequencyAnalyzerSet();

    public Response process(Request<String, String> request) {
        validateKeyWord(request.getKeyWord());
        if (StringUtils.isEmpty(request.getInput())) {
            return EmptyResponse.INSTANCE;
        }
        var keyCharacters = keyCharactersHelper.create(request.getKeyWord(), request.getCaseMode());
        if (keyCharacters.size() == 1) {
            return one.analyse(keyCharacters, request);
        }
        return set.analyse(keyCharacters, request);
    }

    private void validateKeyWord(String keyWord) {
        if (StringUtils.isEmpty(keyWord)) {
            throw new IllegalArgumentException(messages.getString(KEY_WORD_NULL_OR_EMPTY));
        }
    }

}
