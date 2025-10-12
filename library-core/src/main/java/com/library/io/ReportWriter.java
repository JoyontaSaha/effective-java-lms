package com.library.io;

import com.library.core.Book;
import com.library.core.Member;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

// Item 8: Avoid finalizers — use AutoCloseable
// Item 1: Use static factory for clearer, extensible creation
// Item 49: Validate parameters explicitly and fail fast
// Item 9: ALWAYS use try-with-resources for AutoCloseable. Never use try-finally — it is verbose, error-prone, and loses exceptions.
public class ReportWriter implements AutoCloseable {

    private final BufferedWriter writer;

    // Item 1: Private constructor — enforce creation via static factory
    private ReportWriter(Path filePath) throws IOException {
        // Item 49: Explicit null check with clear message
        Objects.requireNonNull(filePath, "filePath must not be null");
        this.writer = Files.newBufferedWriter(filePath);
    }

    // Item 1: Static factory method — named, readable, extensible
    public static ReportWriter forCsvReport(Path filePath) throws IOException {
        return new ReportWriter(filePath);
    }

    public void writeHeader() throws IOException {
        writer.write("Book Title,Author,ISBN,Borrowed By\n");
    }

    public void writeLoan(Book book, Member member) throws IOException {
        // Optional: validate book/member (Item 49) — though domain objects are already validated
        Objects.requireNonNull(book, "book must not be null");
        Objects.requireNonNull(member, "member must not be null");

        String line = String.format("%s,%s,%s,%s%n",
                book.getTitle(),
                book.getAuthor(),
                book.getIsbn(),
                member.getName());
        writer.write(line);
    }

    @Override
    public void close() throws IOException {
        writer.close();
    }
}