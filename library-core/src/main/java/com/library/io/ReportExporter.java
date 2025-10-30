package com.library.io;

import com.library.core.Book;
import com.library.core.Member;
import java.io.IOException;
import java.io.Writer;

// Item 20: Prefer interfaces to abstract classes
// Defines a type for report export functionality

// Item 21: Designed for posterity — minimal, stable, and evolvable.
// - Only essential methods are required
// - Optional behavior (footer) provided via default method
// - Implementations should be stateless and thread-safe
// Implementations must not throw {@link NullPointerException} if given valid inputs.

public interface ReportExporter {
    void exportHeader(Writer writer) throws IOException;
    void exportLoan(Writer writer, Book book, Member member) throws IOException;
    default void exportFooter(Writer writer) throws IOException {
        // Item 20 + Item 21: Default methods allow interface evolution
    }
}