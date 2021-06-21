package com.czajczykmarcin.jatt.core.service;

import com.czajczykmarcin.jatt.core.Request;
import com.czajczykmarcin.jatt.core.Response;

import java.io.BufferedReader;

public interface FrequencyAnalyzerService {

    Response processAscii(Request<String, String> request);

    Response processUnicode(Request<String, String> request);

    Response processUnicodeReader(Request<String, BufferedReader> request);
}
