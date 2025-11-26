package com.library.util;

import java.util.*;

/**
 * Returns the maximum element in the iterable.
 * 
 * Item 30: Generic method with bounded type.
 * Uses <? super T> to accept comparators of supertypes (e.g., List<Apple> with Comparable<Fruit>).
 */
public final class GenericUtils {

    private GenericUtils() { }

    // Item 30: Generic method with bounded type parameter
    public static <T extends Comparable<? super T>> T max(Iterable<T> iterable) {
        Iterator<T> iterator = iterable.iterator();
        if (!iterator.hasNext()) {
            throw new IllegalArgumentException("Empty iterable");
        }
        T max = iterator.next();
        while (iterator.hasNext()) {
            T next = iterator.next();
            if (next.compareTo(max) > 0) {
                max = next;
            }
        }
        return max;
    }

    /**
     * Swaps elements at indices i and j in the list.
     * 
     * Item 30: Generic method for mutation — type-safe, no casts.
     */
    public static <T> void swap(List<T> list, int i, int j) {
        if (i < 0 || i >= list.size() || j < 0 || j >= list.size()) {
            throw new IndexOutOfBoundsException();
        }
        T temp = list.get(i);
        list.set(i, list.get(j));
        list.set(j, temp);
    }

    // Keep earlier methods (firstNonNull, listOf)
    @SafeVarargs
    public static <T> T firstNonNull(T... values) {
        for (T value : values) {
            if (value != null) return value;
        }
        return null;
    }

    @SafeVarargs
    public static <T> List<T> listOf(T... elements) {
        return List.copyOf(Arrays.asList(elements));
    }
}