package com.czajczykmarcin.jatt.core.response;

import com.czajczykmarcin.jatt.core.Response;

import java.util.Collections;
import java.util.List;

public class EmptyResponse implements Response {

    public static final Response INSTANCE = new EmptyResponse();

    private EmptyResponse() {
    }

    @Override
    public List<Occurrence> getOccurrences() {
        return Collections.emptyList();
    }

    @Override
    public long getTotalCount() {
        return 0L;
    }

    @Override
    public long getKeyCharactersTotalCount() {
        return 0L;
    }
}
