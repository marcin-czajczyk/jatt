package com.czajczykmarcin.jatt.core.service;

import com.czajczykmarcin.jatt.core.Request;
import com.czajczykmarcin.jatt.core.Response;
import com.czajczykmarcin.jatt.core.exceptions.IllegalCharacter;
import com.czajczykmarcin.jatt.core.request.CaseMode;
import com.czajczykmarcin.jatt.core.request.CharacterOrder;
import com.czajczykmarcin.jatt.core.request.StringRequest;
import com.czajczykmarcin.jatt.core.service.impl.FrequencyAnalyzerServiceImpl;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class FrequencyAnalyzerServiceImplTest {

    @Test
    public void testProcessAscii() {
    }

    private static final FrequencyAnalyzerServiceImpl ANALYZER = new FrequencyAnalyzerServiceImpl();

    @DataProvider(name = "testProcessAsciiDP")
    public Object[][] testProcessAsciiDP() {
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

    @Test(dataProvider = "testProcessAsciiDP")
    public void testProcessAscii(Request<String, String> request) {
        Response response = ANALYZER.processAscii(request);
        assertNotNull(response);
        assertNotNull(response.getOccurrences());
    }

    @Test(expectedExceptions = IllegalCharacter.class)
    public void testProcessAscii_IllegalCharacter() {
        ANALYZER.processAscii(new StringRequest("LOGIC", "I love to work in global logic! Øñɀ˽ΦϾ҉Ӌ", CaseMode.LOWERCASE, CharacterOrder.ALPHABET));
    }
}
