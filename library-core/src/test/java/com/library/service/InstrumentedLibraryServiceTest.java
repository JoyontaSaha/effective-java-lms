package com.library.service;

import com.library.core.Book;
import com.library.core.Member;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;

class InstrumentedLibraryServiceTest {

    @Test
    void instrumentedLibraryServiceUsesComposition() {
        LibraryService base = new DefaultLibraryService(); // ✅ accessible in same package
        InstrumentedLibraryService instrumented = new InstrumentedLibraryService(base);

        Book book = Book.create("Book", "Author", "123");
        Member member = Member.builder().id("M1").name("Alice").build();

        instrumented.registerBook(book);
        instrumented.registerMember(member);

        assertThat(instrumented.getBookRegistrationCount()).isEqualTo(1);
        assertThat(instrumented.getMemberRegistrationCount()).isEqualTo(1);
        assertThat(instrumented.listAllBooks()).containsExactly(book);
    }
}