package com.library.io;

import com.library.core.Book;
import com.library.core.Member;
import java.io.IOException;
import java.io.Writer;

public final class JsonReportExporter implements ReportExporter {
    private boolean first = true;

    @Override
    public void exportHeader(Writer writer) throws IOException {
        writer.write("[\n");
        first = true;
    }

    @Override
    public void exportLoan(Writer writer, Book book, Member member) throws IOException {
        if (!first) {
            writer.write(",\n");
        }
        String json = String.format(
            "  {\"title\":\"%s\",\"author\":\"%s\",\"isbn\":\"%s\",\"borrowedBy\":\"%s\"}",
            escape(book.getTitle()),
            escape(book.getAuthor()),
            escape(book.getIsbn()),
            escape(member.getName())
        );
        writer.write(json);
        first = false;
    }

    @Override
    public void exportFooter(Writer writer) throws IOException {
        writer.write("\n]\n");
    }

    private String escape(String s) {
        return s.replace("\"", "\\\"");
    }
}