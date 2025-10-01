package com.library.core;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;

class MemberLoanTest {

    @Test
    void shouldClearBookReferenceWhenReturned() {
        // Item 7: Eliminate obsolete object references to prevent memory leaks
        // When a book is returned, member should no longer hold reference to it

        Member member = Member.builder().id("M001").name("Alice").build();
        Book book = Book.create("Effective Java", "Joshua Bloch", "978-0134685991");

        // Simulate checkout
        member.checkoutBook(book);

        assertThat(member.getBorrowedBooks()).containsExactly(book);

        // Simulate return
        member.returnBook(book);

        // Item 7: After return, book reference must be eliminated
        assertThat(member.getBorrowedBooks()).isEmpty();
    }

    @Test
    void shouldNotRemoveOtherBooksWhenReturningOne() {
        Member member = Member.builder().id("M001").name("Alice").build();
        Book book1 = Book.create("Book 1", "Author 1", "111");
        Book book2 = Book.create("Book 2", "Author 2", "222");

        member.checkoutBook(book1);
        member.checkoutBook(book2);

        member.returnBook(book1);

        assertThat(member.getBorrowedBooks()).containsExactly(book2);
    }
}