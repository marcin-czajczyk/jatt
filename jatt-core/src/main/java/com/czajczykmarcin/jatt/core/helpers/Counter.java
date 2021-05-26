package com.czajczykmarcin.jatt.core.helpers;

public final class Counter implements Comparable<Counter>, Cloneable {

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

    @Override
    public Counter clone() {
        return new Counter(value);
    }

    @Override
    public int compareTo(Counter counter) {
        if (this == counter) {
            return 0;
        }
        return Long.compare(this.value, counter.value);
    }
}
