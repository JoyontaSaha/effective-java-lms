package com.library.io;

import com.library.core.Book;
import com.library.core.Member;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;

import static org.assertj.core.api.Assertions.*;

class ReportExporterTest {

    @Test
    void csvExporterShouldGenerateValidCsv() throws IOException {
        // Item 20: Use interface, not abstract class
        ReportExporter exporter = new CsvReportExporter();
        StringWriter writer = new StringWriter();

        Book book = Book.create("Effective Java", "Joshua Bloch", "978-0134685991");
        Member member = Member.builder().id("M001").name("Alice").build();

        exporter.exportHeader(writer);
        exporter.exportLoan(writer, book, member);

        String output = writer.toString();
        assertThat(output).contains("Effective Java")
                         .contains("Alice")
                         .contains(",");
    }

    @Test
    void jsonExporterShouldGenerateValidJson() throws IOException {
        ReportExporter exporter = new JsonReportExporter();
        StringWriter writer = new StringWriter();

        Book book = Book.create("Book", "Author", "123");
        Member member = Member.builder().id("M1").name("Bob").build();

        exporter.exportHeader(writer);
        exporter.exportLoan(writer, book, member);
        exporter.exportFooter(writer);

        String output = writer.toString();
        assertThat(output).contains("\"title\":\"Book\"")
                         .contains("\"borrowedBy\":\"Bob\"")
                         .startsWith("[")
                         .endsWith("]\n");
    }
}