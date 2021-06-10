package com.czajczykmarcin.jatt.core.dto;

public final class Counter implements Comparable<Counter> {

    private long value = 0L;

    public Counter() {
    }

    public Counter(long value) {
        this.value = value;
    }

    public void add(Counter counter) {
        value += counter.get();
    }

    public void increment() {
        value++;
    }

    public long get(){
        return value;
    }

    public boolean isNonZero() {
        return value != 0L;
    }

    public void reset() {
        value = 0L;
    }

    public Counter createCopy() {
        return new Counter(value);
    }

    @Override
    public int compareTo(Counter counter) {
        return Long.compare(this.value, counter.value);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof Counter) {
            return compareTo((Counter) obj) == 0;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Long.hashCode(value);
    }
}
