package com.czajczykmarcin.jatt.core.service;

import com.czajczykmarcin.jatt.core.Request;
import com.czajczykmarcin.jatt.core.Response;
import com.czajczykmarcin.jatt.core.analyzers.AsciiCharacterAnalyzer;
import com.czajczykmarcin.jatt.core.analyzers.FrequencyAnalyzerOne;
import com.czajczykmarcin.jatt.core.analyzers.FrequencyAnalyzerSet;
import com.czajczykmarcin.jatt.core.analyzers.UnicodeCharacterAnalyzer;
import com.czajczykmarcin.jatt.core.helpers.KeyCharactersHelper;
import com.czajczykmarcin.jatt.core.request.BufferReaderRequest;
import com.czajczykmarcin.jatt.core.response.EmptyResponse;
import com.czajczykmarcin.jatt.core.FrequencyAnalyzerService;
import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.StringReader;
import java.util.ResourceBundle;

import static com.czajczykmarcin.jatt.core.messages.ErrorMessages.KEY_WORD_NULL_OR_EMPTY;

public class FrequencyAnalyzerServiceImpl implements FrequencyAnalyzerService {

    //TODO make as constructor or function parameter
    private final ResourceBundle messages = ResourceBundle.getBundle("messages");
    private final KeyCharactersHelper keyCharactersHelper = new KeyCharactersHelper();
    private final AsciiCharacterAnalyzer asciiCharacterAnalyzer = new AsciiCharacterAnalyzer();
    private final UnicodeCharacterAnalyzer unicodeCharacterAnalyzer = new UnicodeCharacterAnalyzer();
    private final FrequencyAnalyzerOne asciiOne = new FrequencyAnalyzerOne(asciiCharacterAnalyzer);
    private final FrequencyAnalyzerOne unicodeOne = new FrequencyAnalyzerOne(unicodeCharacterAnalyzer);
    private final FrequencyAnalyzerSet asciiSet = new FrequencyAnalyzerSet(asciiCharacterAnalyzer);
    private final FrequencyAnalyzerSet unicodeSet = new FrequencyAnalyzerSet(unicodeCharacterAnalyzer);

    @Override
    public Response processAscii(Request<String, String> request) {
        return process(request, asciiOne, asciiSet);
    }

    @Override
    public Response processUnicode(Request<String, String> request) {
        return process(request, unicodeOne, unicodeSet);
    }

    @Override
    public Response processUnicodeReader(Request<String, BufferedReader> request) {
        return processReader(request, unicodeOne, unicodeSet);
    }

    private Response process(Request<String, String> request, FrequencyAnalyzerOne one, FrequencyAnalyzerSet set) {
        if (StringUtils.isEmpty(request.getInput())) {
            return EmptyResponse.INSTANCE;
        }
        return processReader(toBufferedReaderRequest(request), one, set);
    }


    private Response processReader(Request<String, BufferedReader> request, FrequencyAnalyzerOne one, FrequencyAnalyzerSet set) {
        validateKeyWord(request.getKeyWord());
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

    private Request<String, BufferedReader> toBufferedReaderRequest(Request<String, String> request) {
        return new BufferReaderRequest(
                request.getKeyWord(),
                new BufferedReader(new StringReader(request.getInput())),
                request.getCaseMode(),
                request.getCharacterOrder()
        );
    }


}
