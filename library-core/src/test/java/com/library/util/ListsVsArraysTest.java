package com.library.util;

import com.library.core.Book;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.assertj.core.api.Assertions.*;

class ListsVsArraysTest {

    @Test
    void arraysAreCovariantAndDangerous() {
        // Item 28: Arrays are covariant — allows unsafe assignments
        Object[] objects = new String[1];

        // This compiles, but fails at runtime
        assertThatThrownBy(() -> objects[0] = 42)
            .isInstanceOf(ArrayStoreException.class);
    }

    @Test
    void listsAreInvariantAndSafe() {
        // Item 28: Lists are invariant — unsafe assignment caught at compile time
        // List<Object> objects = new ArrayList<String>(); // ← won't compile

        // Safe alternative
        List<String> strings = new ArrayList<>();
        strings.add("Hello");
        // strings.add(42); // ← compile-time error
    }

    @Test
    void varargsAreArraysUnderTheHood() {
        // Item 28 + Item 32: Varargs create arrays — potential heap pollution
        Book book1 = Book.create("Book1", "Author", "123");
        Book book2 = Book.create("Book2", "Author", "456");

        // Safe usage
        List<Book> books = Arrays.asList(book1, book2);
        assertThat(books).hasSize(2);
    }
}