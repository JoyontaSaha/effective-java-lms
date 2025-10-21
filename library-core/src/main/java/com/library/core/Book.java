package com.library.core;

import java.util.Comparator;

// Item 1: Static factory preferred over public constructor
// Benefits:
// 1. Named creation method (create vs new Book(...))
// 2. Can later return cached instance or subtype without client change
// 3. Enforces validation before construction
// 4. Hides constructor — clients cannot bypass factory logic

// Item 12: Implement Comparable with natural ordering consistent with equals()
// Item 14: All fields are private — accessed via getter methods.
// This preserves encapsulation and allows future evolution (e.g., computed fields).
// Item 15: Item 13/EJ.3e:: Does NOT implement Cloneable or clone().
// Reason: clone() is broken; immutable objects don't need copying.
// If a copy is needed (e.g., for mutable wrappers), use Book.create(...).
// For immutable objects like Book, copying is unnecessary — just share the instance.

// Item 17: Fully immutable — all fields final, no setters, class final.
// Safe to share, thread-safe, and failure-atomic.
public final class Book implements Comparable<Book> {

    // Item 17 (foreshadowed): Fields are final → immutable object
    private final String title;
    private final String author;
    private final String isbn;

    // Private constructor — Item 1 enforcement: no direct 'new'
    // Also supports Item 17: Immutability — fields assigned once
    private Book(String title, String author, String isbn) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
    }

    // Static factory — clear, fluent, extensible
    public static Book create(String title, String author, String isbn) {
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("Title required"); // Item 49: Fail fast
        }
        if (author == null || author.trim().isEmpty()) {
            throw new IllegalArgumentException("Author required");
        }
        if (isbn == null || isbn.trim().isEmpty()) {
            throw new IllegalArgumentException("ISBN required");
        }
        return new Book(title, author, isbn);
    }

    // Accessors — no setters (immutable per Item 17)
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public String getIsbn() { return isbn; }

    /**
     * Compares this book to another for equality.
     * Item 11: Two books are equal if their ISBNs are equal.
     * This ensures correct behavior in hash-based collections.
     *
     * @return {@code true} if ISBNs match, {@code false} otherwise
     */
    @Override
    public boolean equals(Object o) {
        // Item 11: Standard equals implementation
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return isbn.equals(book.isbn); // ISBN is non-null (validated at creation)
    }

    /**
     * Returns a hash code based on the ISBN.
     * Item 11: Consistent with equals() — equal books have equal hash codes.
     */
    @Override
    public int hashCode() {
        // Item 11: hashCode consistent with equals — based on ISBN
        return isbn.hashCode();
    }

    /**
     * Compares this book to another by ISBN.
     * Item 12: Consistent with equals() — ensures correct behavior in sorted collections.
     * Note: Natural ordering is by ISBN (not title) to maintain consistency with equals().
     */
    @Override
    public int compareTo(Book other) {
        // Item 12: Fully consistent with equals() — compare by ISBN only
        // This ensures (x.compareTo(y) == 0) == x.equals(y)
        return this.isbn.compareTo(other.isbn);
    }

    // Item 12: For UI/display purposes, use this comparator — not compareTo()
    public static final Comparator<Book> BY_TITLE_THEN_AUTHOR = 
    Comparator.comparing(Book::getTitle)
            .thenComparing(Book::getAuthor)
            .thenComparing(Book::getIsbn);

    /**
     * Returns a string representation of this Book.
     * Item 10: Includes all significant fields for debugging and logging.
     * Format: Book{title='...', author='...', isbn='...'}
     */
    @Override
    public String toString() {
        return String.format("Book{title='%s', author='%s', isbn='%s'}", title, author, isbn);
    }
}