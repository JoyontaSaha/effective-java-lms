package com.library.core;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;

import java.util.HashSet;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.Arrays;
import java.util.List;

class BookTest {

    @Test
    void shouldCreateBookUsingStaticFactoryMethod() {
        // Item 1: Prefer static factory methods over constructors
        // We want to enforce creation via Book.create(...), not 'new Book(...)'

        Book book = Book.create("Effective Java", "Joshua Bloch", "978-0134685991");

        assertThat(book).isNotNull();
        assertThat(book.getTitle()).isEqualTo("Effective Java");
        assertThat(book.getAuthor()).isEqualTo("Joshua Bloch");
        assertThat(book.getIsbn()).isEqualTo("978-0134685991");
    }

    @Test
    void shouldHaveMeaningfulToString() {
        // Item 10: Always override toString() — include all significant fields
        Book book = Book.create("Effective Java", "Joshua Bloch", "978-0134685991");
        String expected = "Book{title='Effective Java', author='Joshua Bloch', isbn='978-0134685991'}";
        assertThat(book.toString()).isEqualTo(expected);
    }

    @Test
    void shouldConsiderBooksEqualIfIsbnMatches() {
        // Item 11: equals() based on logical value (ISBN), not reference
        Book book1 = Book.create("Effective Java", "Joshua Bloch", "978-0134685991");
        Book book2 = Book.create("Different Title", "Another Author", "978-0134685991");

        assertThat(book1).isEqualTo(book2);
        assertThat(book1.hashCode()).isEqualTo(book2.hashCode());
    }

    @Test
    void shouldNotEqualIfIsbnDiffers() {
        Book book1 = Book.create("Book A", "Author A", "111");
        Book book2 = Book.create("Book A", "Author A", "222");

        assertThat(book1).isNotEqualTo(book2);
    }

    @Test
    void shouldWorkInHashSetWithoutDuplicates() {
        // Item 11: hashCode/equals must be consistent for hash collections
        Set<Book> catalog = new HashSet<>();
        Book book1 = Book.create("Effective Java", "Joshua Bloch", "978-0134685991");
        Book book2 = Book.create("Effective Java Vol 2", "Joshua Bloch", "978-0134685991"); // same ISBN

        catalog.add(book1);
        catalog.add(book2);

        assertThat(catalog).hasSize(1); // only one unique ISBN
    }

    @Test
    void shouldSatisfyEqualsContract() {
        // Reflexive
        Book book = Book.create("Test", "Author", "123");
        assertThat(book).isEqualTo(book);

        // Symmetric
        Book other = Book.create("Other", "Author", "123");
        assertThat(book).isEqualTo(other);
        assertThat(other).isEqualTo(book);

        // Transitive
        Book third = Book.create("Third", "Author", "123");
        assertThat(book).isEqualTo(third);
        assertThat(other).isEqualTo(third);

        // Consistent
        assertThat(book).isEqualTo(other);
        assertThat(book).isEqualTo(other); // repeated call

        // Non-nullity
        assertThat(book).isNotEqualTo(null);
    }

    @Test
    void shouldProvideComparatorForTitleBasedSorting() {
        // Item 12: Natural display ordering via static comparator (not compareTo)
        Book bookA = Book.create("Java", "Author A", "111");
        Book bookB = Book.create("Java", "Author B", "222");
        Book bookC = Book.create("Kotlin", "Author A", "333");
    
        List<Book> books = Arrays.asList(bookC, bookB, bookA);
        books.sort(Book.BY_TITLE_THEN_AUTHOR);
    
        assertThat(books).containsExactly(bookA, bookB, bookC);
    }
    
    @Test
    void shouldHaveCompareToConsistentWithEquals() {
        Book book1 = Book.create("Effective Java", "Joshua Bloch", "978-0134685991");
        Book book2 = Book.create("Effective Java Vol 2", "Joshua Bloch", "978-0134685991"); // same ISBN
    
        // Equal by ISBN → must be equal and compareTo == 0
        assertThat(book1.equals(book2)).isTrue();
        assertThat(book1.compareTo(book2)).isEqualTo(0);
    
        // Different ISBN → not equal and compareTo != 0
        Book book3 = Book.create("Clean Code", "Robert Martin", "123");
        assertThat(book1.equals(book3)).isFalse();
        assertThat(book1.compareTo(book3)).isNotEqualTo(0);
    }
    
    @Test
    void shouldWorkInTreeSetWithConsistentOrdering() {
        // Item 12: TreeSet relies on compareTo() — consistency avoids bugs
        SortedSet<Book> sortedCatalog = new TreeSet<>();
        Book book1 = Book.create("Java", "Author", "111");
        Book book2 = Book.create("Java", "Author", "111"); // same ISBN
    
        sortedCatalog.add(book1);
        sortedCatalog.add(book2);
    
        assertThat(sortedCatalog).hasSize(1); // duplicate removed via compareTo()
    }

    @Test
    void shouldWorkInTreeSetWithNoDuplicates() {
        SortedSet<Book> catalog = new TreeSet<>();
        Book book1 = Book.create("Effective Java", "Joshua Bloch", "978-0134685991");
        Book book2 = Book.create("Effective Java Vol 2", "Joshua Bloch", "978-0134685991"); // same ISBN

        catalog.add(book1);
        catalog.add(book2);

        assertThat(catalog).hasSize(1); // duplicate removed via compareTo() == 0
    }
}