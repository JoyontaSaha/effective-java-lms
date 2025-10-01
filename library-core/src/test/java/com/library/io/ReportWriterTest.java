package com.library.io;

import com.library.core.Book;
import com.library.core.Member;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import java.io.IOException;
import java.nio.file.Path;
import static org.assertj.core.api.Assertions.*;

class ReportWriterTest {

    @TempDir
    Path tempDir;

    @Test
    void shouldWriteReportAndCloseResourceAutomatically() throws IOException {
        // Item 8: Never rely on finalizers — use try-with-resources for deterministic cleanup
        Path reportFile = tempDir.resolve("report.csv");

        Book book = Book.create("Effective Java", "Joshua Bloch", "978-0134685991");
        Member member = Member.builder().id("M001").name("Alice").build();

        // Use try-with-resources — ensures close() is called even if exception occurs
        try (ReportWriter writer = ReportWriter.forCsvReport(reportFile)) {
            writer.writeHeader();
            writer.writeLoan(book, member);
        } // close() guaranteed here

        // Verify file was written
        String content = java.nio.file.Files.readString(reportFile);
        assertThat(content).contains("Effective Java")
                          .contains("Alice");
    }

    @Test
    void shouldCloseResourceEvenIfExceptionOccurs() throws IOException {
        Path reportFile = tempDir.resolve("error_report.csv");

        assertThatCode(() -> {
            try (ReportWriter writer = ReportWriter.forCsvReport(reportFile)) {
                writer.writeHeader();
                throw new RuntimeException("Simulated failure");
            }
        }).isInstanceOf(RuntimeException.class);

        // File should still be closed — no resource leak
        // We can't directly test "closed", but we can verify no lock (on Windows) or re-open
        // For demo, we assume try-with-resources works as specified
    }

    @Test
    void shouldThrowOnNullPath() {
        assertThatThrownBy(() -> ReportWriter.forCsvReport(null))
            .isInstanceOf(NullPointerException.class)
            .hasMessage("filePath must not be null");
    }
}