package com.czajczykmarcin.jatt.core.analyzers;

import com.czajczykmarcin.jatt.core.Request;
import com.czajczykmarcin.jatt.core.Response;
import com.czajczykmarcin.jatt.core.exceptions.IllegalCharacter;
import com.czajczykmarcin.jatt.core.request.CaseMode;
import com.czajczykmarcin.jatt.core.request.CharacterOrder;
import com.czajczykmarcin.jatt.core.request.StringRequest;
import com.czajczykmarcin.jatt.core.service.FrequencyAnalyzerService;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class SimpleAsciiFrequencyAnalyzerTest {

    private static final FrequencyAnalyzerService ANALYZER = new FrequencyAnalyzerService();

    @DataProvider(name = "testProcessDP")
    public Object[][] testProcessDP() {
        return new Object[][] {
                {new StringRequest("LOGIC", "I love to work in global logic!", CaseMode.LOWERCASE, CharacterOrder.ALPHABET)},
                {new StringRequest("L", "I love to work in global logic!", CaseMode.LOWERCASE, CharacterOrder.ALPHABET)},
                {new StringRequest("logic", "glo gol log", CaseMode.LOWERCASE, CharacterOrder.ALPHABET)},
                {new StringRequest("logic", "lll ooo lololo lll ooo lol oll", CaseMode.LOWERCASE, CharacterOrder.ALPHABET)},
                {new StringRequest("logic", "()#$()@# @#)@#)@# #*_)@#*)@#)", CaseMode.LOWERCASE, CharacterOrder.ALPHABET)},
                {new StringRequest("logic", "", CaseMode.LOWERCASE, CharacterOrder.ALPHABET)},
                {new StringRequest("logic", null, CaseMode.LOWERCASE, CharacterOrder.ALPHABET)}
        };
    }

    @Test(dataProvider = "testProcessDP")
    public void testProcess(Request<String, String> request) {
        Response response = ANALYZER.process(request);
        assertNotNull(response);
        assertNotNull(response.getOccurrences());
    }

    @Test(expectedExceptions = IllegalCharacter.class)
    public void testProcess_IllegalCharacter() {
        ANALYZER.process(new StringRequest("LOGIC", "I love to work in global logic! Øñɀ˽ΦϾ҉Ӌ", CaseMode.LOWERCASE, CharacterOrder.ALPHABET));
    }

}
