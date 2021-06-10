package com.czajczykmarcin.jatt.core.service;

import com.czajczykmarcin.jatt.core.Request;
import com.czajczykmarcin.jatt.core.Response;
import com.czajczykmarcin.jatt.core.analyzers.AsciiCharacterAnalyzer;
import com.czajczykmarcin.jatt.core.analyzers.StringFrequencyAnalyzerOne;
import com.czajczykmarcin.jatt.core.analyzers.StringFrequencyAnalyzerSet;
import com.czajczykmarcin.jatt.core.analyzers.UnicodeCharacterAnalyzer;
import com.czajczykmarcin.jatt.core.helpers.KeyCharactersHelper;
import com.czajczykmarcin.jatt.core.response.EmptyResponse;
import org.apache.commons.lang3.StringUtils;

import java.util.ResourceBundle;

import static com.czajczykmarcin.jatt.core.messages.ErrorMessages.KEY_WORD_NULL_OR_EMPTY;

public class FrequencyAnalyzerService {

    //TODO make as constructor or function parameter
    private final ResourceBundle messages = ResourceBundle.getBundle("messages");
    private final KeyCharactersHelper keyCharactersHelper = new KeyCharactersHelper();
    private final AsciiCharacterAnalyzer asciiCharacterAnalyzer = new AsciiCharacterAnalyzer();
    private final UnicodeCharacterAnalyzer unicodeCharacterAnalyzer = new UnicodeCharacterAnalyzer();
    private final StringFrequencyAnalyzerOne asciiOne = new StringFrequencyAnalyzerOne(asciiCharacterAnalyzer);
    private final StringFrequencyAnalyzerOne unicodeOne = new StringFrequencyAnalyzerOne(unicodeCharacterAnalyzer);
    private final StringFrequencyAnalyzerSet asciiSet = new StringFrequencyAnalyzerSet(asciiCharacterAnalyzer);
    private final StringFrequencyAnalyzerSet unicodeSet = new StringFrequencyAnalyzerSet(unicodeCharacterAnalyzer);

    public Response processAscii(Request<String, String> request) {
        return process(request, asciiOne, asciiSet);
    }

    public Response processUnicode(Request<String, String> request) {
        return process(request, unicodeOne, unicodeSet);
    }

    private Response process(Request<String, String> request, StringFrequencyAnalyzerOne one, StringFrequencyAnalyzerSet set) {
        validateKeyWord(request.getKeyWord());
        if (StringUtils.isEmpty(request.getInput())) {
            return EmptyResponse.INSTANCE;
        }
        var keyCharacters = keyCharactersHelper.create(request.getKeyWord(), request.getCaseMode(), request.getCharacterOrder());
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
