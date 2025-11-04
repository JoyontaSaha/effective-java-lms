package com.library.domain;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;

// ANTI-PATTERN: Tagged class (Item 23 violation)
class TaggedReport {
    enum Format { CSV, JSON }
    final Format format;
    final String title;
    final String author;
    final String isbn;
    final String borrower;

    private TaggedReport(Format format, String title, String author, String isbn, String borrower) {
        this.format = format;
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.borrower = borrower;
    }

    public static TaggedReport csv(String title, String author, String isbn, String borrower) {
        return new TaggedReport(Format.CSV, title, author, isbn, borrower);
    }

    public static TaggedReport json(String title, String author, String isbn, String borrower) {
        return new TaggedReport(Format.JSON, title, author, isbn, borrower);
    }

    public String export() {
        switch (format) {
            case CSV:
                return String.format("%s,%s,%s,%s", title, author, isbn, borrower);
            case JSON:
                return String.format("{\"title\":\"%s\",\"author\":\"%s\",\"isbn\":\"%s\",\"borrower\":\"%s\"}",
                    title, author, isbn, borrower);
            default:
                throw new AssertionError("Unknown format: " + format);
        }
    }
}

// CORRECT: Class hierarchy
interface Report {
    String export();
}

class CsvReport implements Report {
    private final String title, author, isbn, borrower;
    CsvReport(String title, String author, String isbn, String borrower) {
        this.title = title; this.author = author; this.isbn = isbn; this.borrower = borrower;
    }
    public String export() {
        return String.format("%s,%s,%s,%s", title, author, isbn, borrower);
    }
}

class JsonReport implements Report {
    private final String title, author, isbn, borrower;
    JsonReport(String title, String author, String isbn, String borrower) {
        this.title = title; this.author = author; this.isbn = isbn; this.borrower = borrower;
    }
    public String export() {
        return String.format("{\"title\":\"%s\",\"author\":\"%s\",\"isbn\":\"%s\",\"borrower\":\"%s\"}",
            title, author, isbn, borrower);
    }
}

class TaggedClassTest {

    @Test
    void taggedClassIsVerboseAndErrorProne() {
        // Item 23: Tagged classes require switch statements everywhere
        TaggedReport csv = TaggedReport.csv("Book", "Author", "123", "Alice");
        TaggedReport json = TaggedReport.json("Book", "Author", "123", "Alice");

        assertThat(csv.export()).contains(",");
        assertThat(json.export()).contains("\"title\"");
    }

    @Test
    void classHierarchyIsCleanerAndTypeSafe() {
        // Item 23: Class hierarchy avoids tags and switches
        Report csv = new CsvReport("Book", "Author", "123", "Alice");
        Report json = new JsonReport("Book", "Author", "123", "Alice");

        assertThat(csv.export()).contains(",");
        assertThat(json.export()).contains("\"title\"");

        // Bonus: Can add new report types without modifying existing code (Open/Closed)
    }

    @Test
    void taggedClassWastesMemory() {
        // Item 23: Tagged classes carry unused fields
        // Both CSV and JSON carry all fields — no waste here, but in complex cases, yes
        // Demonstrates conceptual flaw
    }
}