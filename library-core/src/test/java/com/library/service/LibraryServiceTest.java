package com.library.service;

import com.library.core.Book;
import com.library.core.Member;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;

class LibraryServiceTest {

    @Test
    void shouldRegisterAndListBooksIndependentlyPerInstance() {
        // Item 5: No shared global state — each service instance is independent
        LibraryService lib1 = new DefaultLibraryService();
        LibraryService lib2 = new DefaultLibraryService();

        Book book1 = Book.create("Book A", "Author A", "111");
        Book book2 = Book.create("Book B", "Author B", "222");

        lib1.registerBook(book1);
        lib2.registerBook(book2);

        // Each instance has its own state
        assertThat(lib1.listAllBooks()).containsExactly(book1);
        assertThat(lib2.listAllBooks()).containsExactly(book2);
        assertThat(lib1.listAllBooks()).doesNotContain(book2);
    }

    @Test
    void shouldRegisterAndListMembers() {
        LibraryService library = new DefaultLibraryService();
        Member member = Member.builder().id("M001").name("Alice").build();

        library.registerMember(member);

        assertThat(library.listAllMembers()).containsExactly(member);
    }

    @Test
    void shouldStartWithEmptyState() {
        LibraryService library = new DefaultLibraryService();
        assertThat(library.listAllBooks()).isEmpty();
        assertThat(library.listAllMembers()).isEmpty();
    }
}