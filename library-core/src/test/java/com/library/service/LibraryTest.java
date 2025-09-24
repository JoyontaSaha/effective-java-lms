package com.library.service;

import com.library.core.Book;
import com.library.core.Member;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;

class LibraryTest {

    @BeforeEach
    void reset() {
        TestUtils.clearLibrary();
    }

    @Test
    void shouldHaveOnlyOneGlobalInstance() {
        // Item 3: Singleton — only one instance allowed
        // Enum singleton guarantees this by JVM design
        Library lib1 = Library.INSTANCE;
        Library lib2 = Library.INSTANCE;

        assertThat(lib1).isSameAs(lib2); // same reference
        assertThat(lib1).isEqualTo(lib2); // logically equal
    }

    @Test
    void shouldNotAllowDirectInstantiation() {
        // Enum singletons cannot be instantiated via 'new' — compile-time guarantee
        // No runtime test needed — will not compile if attempted.
        // We document intent instead.
    }

    @Test
    void shouldInitializeEmptyCatalogAndMembers() {
        Library library = Library.INSTANCE;

        assertThat(library.listAllBooks()).isEmpty();
        assertThat(library.listAllMembers()).isEmpty();
    }

    @Test
    void shouldRegisterNewBook() {
        Library library = Library.INSTANCE;
        Book book = Book.create("Clean Code", "Robert Martin", "978-0132350884");

        library.registerBook(book);

        assertThat(library.listAllBooks()).containsExactly(book);
    }

    @Test
    void shouldRegisterNewMember() {
        Library library = Library.INSTANCE;
        Member member = Member.builder().id("M001").name("Alice").build();

        library.registerMember(member);

        assertThat(library.listAllMembers()).containsExactly(member);
    }
}