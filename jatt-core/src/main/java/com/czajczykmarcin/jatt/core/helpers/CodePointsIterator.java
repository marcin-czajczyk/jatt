package com.czajczykmarcin.jatt.core.helpers;

import com.czajczykmarcin.jatt.core.exceptions.IORuntimeException;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Iterator;

public class CodePointsIterator implements Iterator<Integer> {

    private final BufferedReader input;
    private Integer next;
    private Integer nextNext;

    public CodePointsIterator(BufferedReader input) {
        this.input = input;
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
        return next;
    }
}
