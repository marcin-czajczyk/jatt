package com.czajczykmarcin.jatt.core.exceptions;

import java.io.IOException;

public class IORuntimeException extends RuntimeException {

    public IORuntimeException(IOException ioException) {
        super(ioException);
    }
}
