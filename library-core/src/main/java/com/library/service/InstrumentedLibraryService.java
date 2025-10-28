package com.library.service;

import com.library.core.Book;
import com.library.core.Member;
import java.util.Objects;

import java.util.List;

// Item 18: Favor composition over inheritance
// Wraps a LibraryService instead of extending it
// Item 19: Final class — use composition to add behavior, not inheritance.
public final class InstrumentedLibraryService implements LibraryService {

    private final LibraryService delegate;
    private int bookRegistrationCount = 0;
    private int memberRegistrationCount = 0;

    public InstrumentedLibraryService(LibraryService delegate) {
        this.delegate = Objects.requireNonNull(delegate);
    }

    @Override
    public void registerBook(Book book) {
        bookRegistrationCount++;
        delegate.registerBook(book);
    }

    @Override
    public void registerMember(Member member) {
        memberRegistrationCount++;
        delegate.registerMember(member);
    }

    @Override
    public List<Book> listAllBooks() {
        return delegate.listAllBooks();
    }

    @Override
    public List<Member> listAllMembers() {
        return delegate.listAllMembers();
    }

    // Instrumentation methods
    public int getBookRegistrationCount() {
        return bookRegistrationCount;
    }

    public int getMemberRegistrationCount() {
        return memberRegistrationCount;
    }
}