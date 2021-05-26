package com.czajczykmarcin.jatt.core;

import com.czajczykmarcin.jatt.core.request.CaseMode;

public interface Request<K,I> {

    K getKeyWord();

    I getInput();

    CaseMode getCaseMode();

}
