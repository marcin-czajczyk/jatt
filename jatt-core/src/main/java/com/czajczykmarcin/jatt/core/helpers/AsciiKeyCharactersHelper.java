package com.czajczykmarcin.jatt.core.helpers;

import com.czajczykmarcin.jatt.core.KeyCharacters;
import com.czajczykmarcin.jatt.core.request.CaseMode;
import com.czajczykmarcin.jatt.core.request.CharacterOrder;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.czajczykmarcin.jatt.core.util.CharacterUtil.caseConverter;

public class AsciiKeyCharactersHelper {

    public KeyCharacters create(final String keyWord, final CaseMode mode, CharacterOrder characterOrder) {
        int[] characters = keyWord.codePoints().toArray();
        if (characters.length == 0) {
            return new AsciiKeyCharacters.Zero();
        }
        if (characters.length == 1) {
            return new AsciiKeyCharacters.One(caseConverter(characters[0], mode));
        }
        if (characters.length == 2) {
            characters[0] = caseConverter(characters[0], mode);
            characters[1] = caseConverter(characters[1], mode);
            if (characters[0] == characters[1]) {
                return new AsciiKeyCharacters.One(characters[0]);
            }
            return new AsciiKeyCharacters.Two(characters[0], characters[1], characterOrder);
        }
        List<Integer> values = Arrays.stream(characters)
                .map(i -> caseConverter(i,mode))
                .boxed()
                .distinct()
                .collect(Collectors.toList());
        return new AsciiKeyCharacters.Set(values, characterOrder);
    }

}
