package com.czajczykmarcin.jatt.core.response;

import com.czajczykmarcin.jatt.core.Response;
import lombok.Getter;
import java.util.List;

@Getter
public class ResponseImpl implements Response {

    private final List<Occurrence> occurrences;
    private final long totalCount;
    private final long keyCharactersTotalCount;

    public ResponseImpl(List<Occurrence> occurrences, long totalCount, long keyCharactersTotalCount) {
        this.occurrences = occurrences;
        this.totalCount = totalCount;
        this.keyCharactersTotalCount = keyCharactersTotalCount;
    }

    @Override
    public boolean isEmpty() {
        return occurrences.isEmpty();
    }
}
