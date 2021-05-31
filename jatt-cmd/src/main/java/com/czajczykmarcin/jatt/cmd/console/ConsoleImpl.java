package com.czajczykmarcin.jatt.cmd.console;

import com.czajczykmarcin.jatt.cmd.Console;

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
    public String readLine(String text) {
        printStream.print(text);
        return scanner.nextLine();
    }

    @Override
    public void write(String text) {
        printStream.println(text);
    }
}
