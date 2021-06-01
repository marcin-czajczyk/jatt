package com.czajczykmarcin.jatt.cmd;

public interface Console {

    String readLogicWord(String defaultLogicWord);

    String readText(String defaultText);

    void write(String text);

}
