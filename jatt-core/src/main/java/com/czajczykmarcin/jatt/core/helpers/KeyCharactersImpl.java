package com.czajczykmarcin.jatt.core.helpers;

import com.czajczykmarcin.jatt.core.KeyCharacters;
import com.czajczykmarcin.jatt.core.dto.Counter;
import com.czajczykmarcin.jatt.core.request.CharacterOrder;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.czajczykmarcin.jatt.core.util.CharacterUtil.sort;
import static java.util.function.Function.identity;

class KeyCharactersImpl {

    private KeyCharactersImpl() { throw new UnsupportedOperationException("Cannot create KeyCharacters instance"); }

    static final class Zero implements KeyCharacters {

        @Override
        public int size() {
            return 0;
        }

        @Override
        public boolean contains(int i) {
            return false;
        }

        @Override
        public Map<Integer, Counter> createCounterMap() {
            return Collections.emptyMap();
        }

        @Override
        public List<Integer> getSorted() {
            return Collections.emptyList();
        }
    }

    static final class One implements KeyCharacters {

        private final int value;

        private final List<Integer> sortedValues;

        One(int value) {
            this.value = value;
            this.sortedValues = Collections.singletonList(value);
        }

        @Override
        public int size() {
            return 1;
        }

        @Override
        public boolean contains(int input) {
            return value == input;
        }

        @Override
        public Map<Integer, Counter> createCounterMap() {
            return Collections.singletonMap(value, new Counter());
        }

        @Override
        public List<Integer> getSorted() {
            return sortedValues;
        }
    }

    static final class Two implements com.czajczykmarcin.jatt.core.KeyCharacters {

        private final int value1;
        private final int value2;

        private final List<Integer> sortedValues;

        Two(int value1, int value2, CharacterOrder characterOrder) {
            this.value1 = value1;
            this.value2 = value2;
            this.sortedValues = sort(value1, value2, characterOrder);
        }

        @Override
        public int size() {
            return 2;
        }

        @Override
        public boolean contains(int input) {
            return value1 == input || value2 == input;
        }

        @Override
        public Map<Integer, Counter> createCounterMap() {
            return Map.of(value1, new Counter(), value2, new Counter());
        }

        @Override
        public List<Integer> getSorted() {
            return sortedValues;
        }
    }

    static final class Set implements KeyCharacters {

        private final java.util.Set<Integer> data;

        private final List<Integer> sortedValues;

        Set(List<Integer> data, CharacterOrder characterOrder) {
            this.data = new HashSet<>(data);
            this.sortedValues = sort(data, characterOrder);
        }

        @Override
        public int size() {
            return data.size();
        }

        @Override
        public boolean contains(int input) {
            return data.contains(input);
        }

        @Override
        public Map<Integer, Counter> createCounterMap() {
            return data.stream()
                    .collect(Collectors.toUnmodifiableMap(identity(), v -> new Counter()));
        }

        @Override
        public List<Integer> getSorted() {
            return sortedValues;
        }

    }
}
