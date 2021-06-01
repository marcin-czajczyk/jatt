package com.czajczykmarcin.jatt.cmd.console;

import com.czajczykmarcin.jatt.cmd.Console;
import org.apache.commons.lang3.StringUtils;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

public class ConsoleImpl implements Console {

    private final Scanner scanner;
    private final PrintStream printStream;

    public ConsoleImpl(InputStream inputStream, PrintStream printStream) {
        this.scanner = new Scanner(inputStream);
        this.printStream = printStream;
    }

    @Override
    public String readLogicWord(String defaultLogicWord) {
        var logicWord = readLine(String.format("Please provide logic word (default: '%s'): ", defaultLogicWord));
        return StringUtils.isEmpty(logicWord) ? defaultLogicWord : logicWord;
    }

    @Override
    public String readText(String defaultText) {
        var text = readLine(String.format("Please provide the text (default: '%s'): ", defaultText));
        return StringUtils.isEmpty(text) ? defaultText : text;
    }

    @Override
    public void write(String text) {
        printStream.println(text);
    }

    private String readLine(String text) {
        printStream.print(text);
        return scanner.nextLine();
    }
}
