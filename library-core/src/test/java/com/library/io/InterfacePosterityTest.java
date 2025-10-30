package com.library.io;

import com.library.core.Book;
import com.library.core.Member;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;

import static org.assertj.core.api.Assertions.*;

// Simulates a third-party implementation that cannot be modified
class ThirdPartyExporter implements ReportExporter {
    private final StringBuilder output = new StringBuilder();

    @Override
    public void exportHeader(Writer writer) throws IOException {
        output.append("HEADER\n");
        writer.write("HEADER\n");
    }

    @Override
    public void exportLoan(Writer writer, Book book, Member member) throws IOException {
        output.append("LOAN: ").append(book.getTitle()).append("\n");
        writer.write("LOAN: " + book.getTitle() + "\n");
    }

    // Does NOT implement exportFooter() — relies on default
}

class InterfacePosterityTest {

    @Test
    void thirdPartyImplementationShouldWorkWithDefaultMethods() throws IOException {
        // Item 21: Interfaces must be evolvable via default methods
        ReportExporter exporter = new ThirdPartyExporter();
        StringWriter writer = new StringWriter();

        exporter.exportHeader(writer);
        exporter.exportLoan(
            writer,
            Book.create("Book", "Author", "123"),
            Member.builder().id("M1").name("Alice").build()
        );
        exporter.exportFooter(writer); // Uses default implementation

        String output = writer.toString();
        assertThat(output).contains("HEADER").contains("LOAN:");
        // No crash — default method worked
    }

    @Test
    void interfaceShouldBeMinimalAndFocused() {
        // Item 21: Avoid "kitchen sink" interfaces
        // ReportExporter has only 2 required methods + 1 optional default
        assertThat(ReportExporter.class.getMethods())
            .extracting(java.lang.reflect.Method::getName)
            .containsExactlyInAnyOrder("exportHeader", "exportLoan", "exportFooter");
    }
}