package com.library.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * A simple generic catalog.
 * 
 * Item 29: Favor generic types — type parameter T used throughout API.
 * Provides compile-time safety and eliminates casts.
 */
public class Catalog<T> {
    private final List<T> items = new ArrayList<>();

    public void add(T item) {
        items.add(Objects.requireNonNull(item));
    }

    public T get(int index) {
        return items.get(index);
    }

    public T pop() {
        if (items.isEmpty()) {
            throw new IllegalStateException("Catalog is empty");
        }
        return items.remove(items.size() - 1);
    }

    public int size() {
        return items.size();
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }
}