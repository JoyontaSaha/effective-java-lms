package com.library.core;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;

class BookTest {

    @Test
    void shouldCreateBookUsingStaticFactoryMethod() {
        // Item 1: Prefer static factory methods over constructors
        // We want to enforce creation via Book.create(...), not 'new Book(...)'

        Book book = Book.create("Effective Java", "Joshua Bloch", "978-0134685991");

        assertThat(book).isNotNull();
        assertThat(book.getTitle()).isEqualTo("Effective Java");
        assertThat(book.getAuthor()).isEqualTo("Joshua Bloch");
        assertThat(book.getIsbn()).isEqualTo("978-0134685991");
    }

    @Test
    void shouldHaveMeaningfulToString() {
        // Item 10: Always override toString() — include all significant fields
        Book book = Book.create("Effective Java", "Joshua Bloch", "978-0134685991");
        String expected = "Book{title='Effective Java', author='Joshua Bloch', isbn='978-0134685991'}";
        assertThat(book.toString()).isEqualTo(expected);
    }
}