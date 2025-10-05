package com.library.core;

// Item 1: Static factory preferred over public constructor
// Benefits:
// 1. Named creation method (create vs new Book(...))
// 2. Can later return cached instance or subtype without client change
// 3. Enforces validation before construction
// 4. Hides constructor — clients cannot bypass factory logic

public final class Book {

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
     * Returns a string representation of this Book.
     * Item 10: Includes all significant fields for debugging and logging.
     * Format: Book{title='...', author='...', isbn='...'}
    */
    @Override
    public String toString() {
        return String.format("Book{title='%s', author='%s', isbn='%s'}", title, author, isbn);
    }
}