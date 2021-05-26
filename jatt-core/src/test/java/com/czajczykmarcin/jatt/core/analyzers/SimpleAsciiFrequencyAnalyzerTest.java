package com.czajczykmarcin.jatt.core.analyzers;

import com.czajczykmarcin.jatt.core.Response;
import com.czajczykmarcin.jatt.core.exceptions.IllegalCharacter;
import com.czajczykmarcin.jatt.core.request.CaseMode;
import com.czajczykmarcin.jatt.core.request.StringRequest;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class SimpleAsciiFrequencyAnalyzerTest {

    @Test
    public void testAnalyse() {
        SimpleAsciiFrequencyAnalyzer analyzer = new SimpleAsciiFrequencyAnalyzer();
        Response response = analyzer.analyse(new StringRequest("LOGIC", "I love to work in global logic!", CaseMode.LOWERCASE));
        assertNotNull(response);
        assertNotNull(response.getOccurrences());
    }

    @Test(expectedExceptions = IllegalCharacter.class)
    public void testAnalyse_IllegalCharacter() {
        SimpleAsciiFrequencyAnalyzer analyzer = new SimpleAsciiFrequencyAnalyzer();
        analyzer.analyse(new StringRequest("LOGIC", "Dziś wieczorem zjemy kolację!!! Øñɀ˽ΦϾ҉Ӌ", CaseMode.LOWERCASE));
    }

}
