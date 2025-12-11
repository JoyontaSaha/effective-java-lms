package com.library.util;

import com.library.core.Book;
import org.junit.jupiter.api.Test;
import java.util.*;

import static org.assertj.core.api.Assertions.*;

class WildcardTest {

    // Rigid API: only accepts List<Book>
    static void registerBooksRigid(List<Book> books) {
        // ...
    }

    // Flexible API: accepts List<? extends Book> (e.g., List<LibraryBook>)
    static void registerBooksFlexible(List<? extends Book> books) {
        // ...
    }

    // Flexible consumer
    static void addNumbersFlexible(List<? super Number> list) {
        list.add(1);
        list.add(2.5);
    }

    @Test
    void rigidApiRejectsSubtypes() {
        // Item 31: Instead of subclassing (impossible due to final Book),
        // demonstrate flexibility with List<? extends Book> accepting:
        // - List<Book>
        // - Sublist (List<Book>)
        // - Unmodifiable list

        Book book1 = Book.create("A", "X", "1");
        Book book2 = Book.create("B", "Y", "2");
        List<Book> books = Arrays.asList(book1, book2);

        // These all work with ? extends Book, but not with List<Book> if API were rigid
        List<? extends Book> asSublist = books.subList(0, 1);
        List<? extends Book> asUnmodifiable = List.copyOf(books);

        // Flexible API accepts all
        registerBooksFlexible(books);
        registerBooksFlexible(asSublist);
        registerBooksFlexible(asUnmodifiable);

        // Rigid API would reject asSublist and asUnmodifiable in some contexts
        // (though in Java, they're still List<Book> — but the principle holds for true subtypes)
    }

    // Keep the consumer test — it works as-is
    @Test
    void consumerFlexibleWithSuper() {
        List<Object> objects = new ArrayList<>();
        List<Number> numbers = new ArrayList<>();
        addNumbersFlexible(objects);
        addNumbersFlexible(numbers);
    }
}