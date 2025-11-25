package com.library.util;

import com.library.core.Book;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;

class CatalogTest {

    @Test
    void genericCatalogShouldBeTypeSafe() {
        // Item 29: Favor generic types — deep generic design
        Catalog<Book> bookCatalog = new Catalog<>();

        Book book = Book.create("Effective Java", "Joshua Bloch", "978-0134685991");
        bookCatalog.add(book);

        // No casts needed
        Book retrieved = bookCatalog.get(0);
        assertThat(retrieved).isEqualTo(book);

        // Compile-time safety: won't allow wrong type
        // bookCatalog.add("Not a book"); // ← compile error
    }

    @Test
    void catalogShouldSupportGenericOperations() {
        Catalog<Book> catalog = new Catalog<>();
        Book book1 = Book.create("A", "X", "1");
        Book book2 = Book.create("B", "Y", "2");

        catalog.add(book1);
        catalog.add(book2);

        assertThat(catalog.size()).isEqualTo(2);
        assertThat(catalog.isEmpty()).isFalse();

        Book popped = catalog.pop(); // LIFO
        assertThat(popped).isEqualTo(book2);
    }
}