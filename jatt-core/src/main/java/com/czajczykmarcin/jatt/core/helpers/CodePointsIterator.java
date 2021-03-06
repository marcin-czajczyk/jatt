package com.czajczykmarcin.jatt.core.helpers;

import com.czajczykmarcin.jatt.core.exceptions.IORuntimeException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class CodePointsIterator implements Iterator<Integer> {

    private final BufferedReader input;
    private Integer next;
    private Integer nextNext;

    public CodePointsIterator(Reader input) {
        if (input instanceof BufferedReader) {
            this.input = (BufferedReader) input;
        }
        else {
            this.input = new BufferedReader(input);
        }
    }

    @Override
    public boolean hasNext() {
        if (nextNext != null) {
            next = nextNext;
            nextNext = null;
        }
        try {
            int i1;
            if ((i1 = input.read()) != -1) {
                char c1 = (char) i1;
                if (Character.isHighSurrogate(c1)) {
                    int i2 = input.read();
                    if (i2 != -1) {
                        char c2 = (char) i2;
                        if (Character.isLowSurrogate(c2)) {
                            next = Character.toCodePoint(c1, c2);
                        }
                        else {
                            next = i1;
                            nextNext = i2;
                        }
                    }
                }
                else {
                    next = i1;
                }
            }
            else {
                next = null;
                return false;
            }
        }
        catch(IOException e) {
            throw new IORuntimeException(e);
        }
        return true;
    }

    @Override
    public Integer next() {
        if (next == null) {
            throw new NoSuchElementException();
        }
        return next;
    }
}
