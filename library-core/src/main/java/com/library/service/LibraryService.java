package com.library.service;

import com.library.core.Book;
import com.library.core.Member;
import java.util.List;

// Item 5: Program to interface, not implementation (also Item 52)
// Enables mocking, swapping implementations, and clear contracts
public interface LibraryService {
    void registerBook(Book book);
    void registerMember(Member member);
    List<Book> listAllBooks();
    List<Member> listAllMembers();
    // Item 31: Add flexible bulk registration
    void registerAllBooks(List<? extends Book> books);
}