package com.library.util;

import org.junit.jupiter.api.Test;
import java.util.*;

import static org.assertj.core.api.Assertions.*;

class UncheckedWarningDemoTest {

    // ANTI-PATTERN: Unchecked cast
    @SuppressWarnings("unchecked")
    static <T> T[] toArray(Collection<T> c, T[] a) {
        // Unsafe: assumes a has correct runtime type
        return (T[]) c.toArray(a); // ← unchecked warning
    }

    @Test
    void unsafeToArrayCausesUncheckedWarning() {
        // This compiles but issues warning
        List<String> list = Arrays.asList("A", "B");
        String[] array = toArray(list, new String[0]);
        assertThat(array).containsExactly("A", "B");
    }

    @Test
    void safeToArrayUsesGenericsCorrectly() {
        // Item 27: Eliminate unchecked warnings — use Collection.toArray(T[])
        List<String> list = Arrays.asList("A", "B");
        String[] array = list.toArray(new String[0]); // ✅ Safe and warning-free
        assertThat(array).containsExactly("A", "B");
    }
}