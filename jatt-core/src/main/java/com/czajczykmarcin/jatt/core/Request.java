package com.czajczykmarcin.jatt.core;

import com.czajczykmarcin.jatt.core.request.CaseMode;
import com.czajczykmarcin.jatt.core.request.CharacterOrder;

public interface Request<K,I> {

    K getKeyWord();

    I getInput();

    CaseMode getCaseMode();

    CharacterOrder getCharacterOrder();

}
