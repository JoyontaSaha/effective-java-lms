package com.library.core;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// Item 2: Builder Pattern — when constructor has many parameters (esp. optional ones)
// Advantages:
// - Readable, fluent client code
// - Can enforce required fields at build-time (not compile-time — tradeoff)
// - Immutable result (Item 17)
// - Extensible — easy to add new optional fields without breaking clients

// Item 7: This class manages its own memory (borrowedBooks list)
// Therefore, we must eliminate obsolete references when books are returned.
// Failure to do so would cause memory leaks — books couldn't be GC'd even if unused elsewhere.

public final class Member {

    // Item 17: All fields final → immutable object
    private final String id;      // required
    private final String name;    // required
    private final String email;   // optional
    private final String phone;   // optional

    // Item 7: Manage own memory — list of borrowed books
    // Must eliminate obsolete references when books are returned
    private final List<Book> borrowedBooks = new ArrayList<>();

    // Item 1 + Item 2: Private constructor — only Builder can instantiate
    // Prevents inconsistent or partial object creation
    private Member(String id, String name, String email, String phone) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
    }

    // Item 2: Static factory method returning Builder — named, fluent entry point
    public static Builder builder() {
        return new Builder();
    }

    // Item 2: Builder is static nested class — has access to private constructor
    // Follows Bloch’s recommended structure
    public static class Builder {
        private String id;
        private String name;
        private String email;
        private String phone;

        // Item 2: Setter-like methods return 'this' for chaining
        public Builder id(String id) {
            this.id = id;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Builder phone(String phone) {
            this.phone = phone;
            return this;
        }

        // Item 2: Build method validates state before construction
        // Throws IllegalStateException — standard for builder violations
        public Member build() {
            if (id == null || id.trim().isEmpty()) {
                throw new IllegalStateException("id is required"); // Item 49: Validate parameters
            }
            if (name == null || name.trim().isEmpty()) {
                throw new IllegalStateException("name is required");
            }
            // Optional fields allowed to be null — by design
            return new Member(id, name, email, phone);
        }
    }

    // Standard accessors — no setters (immutable)
    public String getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getPhone() { return phone; }

    // Item 7: Checkout adds book to list
    public void checkoutBook(Book book) {
        if (book == null) {
            throw new IllegalArgumentException("Book cannot be null");
        }
        borrowedBooks.add(book);
    }

    // Item 7: When book is returned, remove from list — eliminates reference
    // ArrayList.remove() shifts elements and nulls out the last slot (in OpenJDK)
    // This ensures no loitering object remains
    /**
     * Returns a borrowed book, eliminating the reference to it.
     * Item 7: Prevents memory leaks by removing obsolete object reference.
    */
    public void returnBook(Book book) {
        if (book == null) {
            return; // or throw — but safe to ignore
        }
        borrowedBooks.remove(book); // ArrayList.remove() eliminates reference
    }

    // Item 15: Defensive copy — don't expose internal list
    public List<Book> getBorrowedBooks() {
        return List.copyOf(borrowedBooks);
    }

    // Item 10: Always override toString() — human-readable representation
    @Override
    public String toString() {
        return "Member{id='" + id + "', name='" + name + "', email='" + email + "', phone='" + phone + "'}";
    }

    // Item 10/11: Will override equals() and hashCode() in future items
}