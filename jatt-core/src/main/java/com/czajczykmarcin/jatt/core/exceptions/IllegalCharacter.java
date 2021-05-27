package com.czajczykmarcin.jatt.core.exceptions;

public class IllegalCharacter extends RuntimeException {

    private final String character;

    public IllegalCharacter(String character) {
        this.character = character;
    }

    @Override
    public String getMessage() {
        return "IllegalCharacter '" + character + "'";
    }
}
