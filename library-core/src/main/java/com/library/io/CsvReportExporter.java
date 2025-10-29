package com.library.io;

import com.library.core.Book;
import com.library.core.Member;
import java.io.IOException;
import java.io.Writer;

// Item 20: Concrete implementation of interface — no inheritance
public final class CsvReportExporter implements ReportExporter {

    @Override
    public void exportHeader(Writer writer) throws IOException {
        writer.write("Book Title,Author,ISBN,Borrowed By\n");
    }

    @Override
    public void exportLoan(Writer writer, Book book, Member member) throws IOException {
        String line = String.format("%s,%s,%s,%s%n",
                book.getTitle(),
                book.getAuthor(),
                book.getIsbn(),
                member.getName());
        writer.write(line);
    }
}