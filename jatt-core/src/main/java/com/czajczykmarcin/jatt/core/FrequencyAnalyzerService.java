package com.czajczykmarcin.jatt.core;

import java.io.Reader;

public interface FrequencyAnalyzerService {

    Response processAscii(Request<String, String> request);

    Response processUnicode(Request<String, String> request);

    Response processUnicodeReader(Request<String, Reader> request);
}
