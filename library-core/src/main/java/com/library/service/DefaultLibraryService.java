package com.library.service;

import com.library.core.Book;
import com.library.core.Member;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

// Item 5: Injectable, stateful service — not a singleton
// Each instance manages its own catalog and members
// Enables test isolation without resetForTesting()

// Item 5: Prefer dependency injection over hardcoded singletons
// Why? Singletons:
// - Make testing hard (shared state)
// - Prevent multiple instances (e.g., multi-tenant libraries)
// - Hide dependencies (not explicit in API)
// This class is:
// - Instantiable on demand
// - State isolated per instance
// - Injectable into higher-level services (e.g., CheckoutService)

// Also Item 52: Implements interface — clients depend on abstraction
// Mutable service class — state (catalog, members) changes over time. 

// Item 17: Mutability is justified because:
// - Library state evolves (books added, members registered)
// - Not a value object — represents a live system
// - Clients should not share instances (unlike Book/Member identity)

// Item 19: Final and package-private — not designed for inheritance.
// Clients should depend on LibraryService interface.
final class DefaultLibraryService implements LibraryService {

    private final List<Book> catalog = new ArrayList<>();
    private final List<Member> members = new ArrayList<>();

    @Override
    public void registerBook(Book book) {
        if (book == null) {
            throw new IllegalArgumentException("Book cannot be null");
        }
        catalog.add(book);
    }

    @Override
    public void registerAllBooks(List<? extends Book> books) {
        Objects.requireNonNull(books);
        for (Book book : books) {
            registerBook(book); // Book is upper bound — safe
        }
    }

    @Override
    public void registerMember(Member member) {
        if (member == null) {
            throw new IllegalArgumentException("Member cannot be null");
        }
        members.add(member);
    }

    @Override
    public List<Book> listAllBooks() {
        return List.copyOf(catalog); // Item 15: defensive copy
    }

    @Override
    public List<Member> listAllMembers() {
        return List.copyOf(members); // Item 15
    }
}