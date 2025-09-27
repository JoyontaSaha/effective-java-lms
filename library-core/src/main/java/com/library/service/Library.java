package com.library.service;

import com.library.core.Book;
import com.library.core.Member;

import java.util.ArrayList;
import java.util.List;

// Item 3: Singleton — implemented via enum (Bloch’s preferred method)
// Advantages:
// - Guaranteed singleton by JVM — even under serialization or reflection
// - Thread-safe without synchronization
// - Concise and clear intent
// - Cannot be subclassed — sealed by enum nature
// Compare to: double-checked locking (error-prone), static factory with private constructor (less safe)
/**
    * @deprecated ( We keep it for now to avoid breaking existing tests, but new features will use LibraryService)
*/
@Deprecated(since = "1.0", forRemoval = true)
public enum Library {

    // Item 3: The single enum constant — the one and only instance
    // JVM creates this during class loading — thread-safe and eager (acceptable for Library)
    INSTANCE;

    // Item 15 (foreshadowed): Internal mutable state — but exposed via immutable views
    // We use defensive copying in accessors to prevent external mutation
    private final List<Book> catalog = new ArrayList<>();
    private final List<Member> members = new ArrayList<>();

    // Item 3: No constructor needed — enum provides implicit private constructor
    // Attempting 'new Library()' won't compile — compile-time safety

    // Item 3 + Item 1 synergy: INSTANCE acts as static factory — global access point

    // Item 15: Mutator — adds book to internal list
    // Future: Consider using Set<Book> if ISBN is unique (Item 52: Refer to objects by interface)
    public void registerBook(Book book) {
        // Item 49: Validate parameter — though Book.create() already ensures non-null
        if (book == null) {
            throw new IllegalArgumentException("Book cannot be null");
        }
        catalog.add(book);
    }

    public void registerMember(Member member) {
        if (member == null) {
            throw new IllegalArgumentException("Member cannot be null");
        }
        members.add(member);
    }

    // Item 15: Accessor returns defensive copy — prevents external mutation of internal state
    // Also Item 52: Return interface type (List) not implementation (ArrayList)
    public List<Book> listAllBooks() {
        return List.copyOf(catalog); // JDK 10+ — immutable copy
    }

    public List<Member> listAllMembers() {
        return List.copyOf(members); // immutable copy
    }

    // Item 10: Override toString() for debugging
    @Override
    public String toString() {
        return "Library{books=" + catalog.size() + ", members=" + members.size() + "}";
    }

    // Item 11/12: Will override equals/hashCode if needed — but singleton rarely needs it
    // Item 76: Consider using serialization proxy if we implement Serializable later
}