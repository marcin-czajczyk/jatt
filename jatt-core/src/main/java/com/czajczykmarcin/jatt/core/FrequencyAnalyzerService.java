package com.czajczykmarcin.jatt.core;

import java.io.BufferedReader;

public interface FrequencyAnalyzerService {

    Response processAscii(Request<String, String> request);

    Response processUnicode(Request<String, String> request);

    Response processUnicodeReader(Request<String, BufferedReader> request);
}
