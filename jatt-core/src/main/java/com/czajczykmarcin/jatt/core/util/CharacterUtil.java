package com.czajczykmarcin.jatt.core.util;

import com.czajczykmarcin.jatt.core.request.CaseMode;
import com.czajczykmarcin.jatt.core.request.CharacterOrder;

import java.text.Collator;
import java.util.*;
import java.util.stream.Collectors;

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

    public static List<Integer> sort(int codePoint1, int codePoint2, CharacterOrder order) {
        switch (order) {
            case ALPHABET:
                var string1 = String.valueOf(Character.toChars(codePoint1));
                var string2 = String.valueOf(Character.toChars(codePoint2));
                return Collator.getInstance().compare(string1, string2) <= 0 ?
                        List.of(codePoint1, codePoint2) : List.of(codePoint2, codePoint1);
            case NUMERIC:
                return codePoint1 <= codePoint2 ? List.of(codePoint1, codePoint2) : List.of(codePoint2, codePoint1);
            case INPUT:
                return List.of(codePoint1, codePoint2);
            default:
                throw new UnsupportedOperationException("Unsupported value");
        }
    }



    public static List<Integer> sort(Collection<Integer> collection, CharacterOrder order) {
        switch (order) {
            case ALPHABET:
                var collator = Collator.getInstance();
                return collection.stream()
                    .map(Character::toChars)
                    .map(String::valueOf)
                    .sorted(collator)
                    .map(s -> s.codePointAt(0))
                    .collect(Collectors.toList());
            case NUMERIC:
                return collection.stream()
                        .sorted()
                        .collect(Collectors.toList());
            case INPUT:
                return List.copyOf(collection);
            default: throw new UnsupportedOperationException("Unsupported value");
        }

    }

    private CharacterUtil() {
        throw new UnsupportedOperationException("Cannot create CharacterUtil instance");
    }
}
