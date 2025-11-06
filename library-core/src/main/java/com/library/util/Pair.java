package com.library.util;

import java.util.Objects;

// Item 25: Prefer generic types — type-safe, no casts
public final class Pair<T, U> {
    private final T first;
    private final U second;

    public Pair(T first, U second) {
        this.first = Objects.requireNonNull(first);
        this.second = Objects.requireNonNull(second);
    }

    public T first() {
        return first;
    }

    public U second() {
        return second;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pair<?, ?> pair = (Pair<?, ?>) o;
        return Objects.equals(first, pair.first) &&
               Objects.equals(second, pair.second);
    }

    @Override
    public int hashCode() {
        return Objects.hash(first, second);
    }

    @Override
    public String toString() {
        return "Pair{" + first + ", " + second + '}';
    }
}