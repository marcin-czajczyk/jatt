package com.czajczykmarcin.jatt.core.util;

import com.czajczykmarcin.jatt.core.request.CaseMode;

public final class CharacterUtil {

    public static int caseConverter(int codePoint, CaseMode mode) {
        switch(mode) {
            case LOWERCASE:
                return Character.toLowerCase(codePoint);
            case UPPERCASE:
                return Character.toUpperCase(codePoint);
            default:
                return codePoint;
        }
    }

    private CharacterUtil() {
        throw new UnsupportedOperationException("Cannot create CharacterUtil instance");
    }
}
