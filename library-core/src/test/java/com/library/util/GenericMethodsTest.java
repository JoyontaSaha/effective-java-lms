package com.library.util;

import com.library.core.Book;
import org.junit.jupiter.api.Test;
import java.util.*;

import static org.assertj.core.api.Assertions.*;

class GenericMethodsTest {

    @Test
    void genericMaxShouldWorkWithAnyComparable() {
        // Item 30: Generic method with bounded type
        List<Integer> numbers = Arrays.asList(3, 1, 4, 1, 5);
        Integer max = GenericUtils.max(numbers);
        assertThat(max).isEqualTo(5);

        List<String> words = Arrays.asList("apple", "banana", "cherry");
        String longest = GenericUtils.max(words);
        assertThat(longest).isEqualTo("cherry");
    }

    @Test
    void genericSwapShouldWorkOnAnyList() {
        // Item 30: Generic method for mutation
        List<Book> books = new ArrayList<>();
        Book book1 = Book.create("A", "X", "1");
        Book book2 = Book.create("B", "Y", "2");
        books.add(book1);
        books.add(book2);

        GenericUtils.swap(books, 0, 1);

        assertThat(books).containsExactly(book2, book1);
    }

    @Test
    void nonGenericMaxWouldRequireDuplication() {
        // ANTI-PATTERN: Separate methods for each type
        // Integer maxInt(List<Integer> list) { ... }
        // String maxStr(List<String> list) { ... }
        // → Code duplication, maintenance nightmare
    }
}