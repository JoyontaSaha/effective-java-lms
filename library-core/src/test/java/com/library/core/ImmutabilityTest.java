package com.library.core;

import org.junit.jupiter.api.Test;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import static org.assertj.core.api.Assertions.*;

class ImmutabilityTest {

    @Test
    void bookShouldBeImmutable() {
        Book book = Book.create("Effective Java", "Joshua Bloch", "978-0134685991");

        // 1. No setter methods
        assertNoSetterMethods(Book.class, "setTitle", "setAuthor", "setIsbn");
    
        // 2. Class is final
        assertThat(java.lang.reflect.Modifier.isFinal(Book.class.getModifiers()))
            .isTrue();
    
        // 3. All NON-STATIC fields are final and private
        for (java.lang.reflect.Field field : Book.class.getDeclaredFields()) {
            if (java.lang.reflect.Modifier.isStatic(field.getModifiers())) {
                continue; // static constants are exempt
            }
            assertThat(java.lang.reflect.Modifier.isFinal(field.getModifiers()))
                .isTrue();
            assertThat(java.lang.reflect.Modifier.isPrivate(field.getModifiers()))
                .isTrue();
        }
    }

    @Test
    void memberShouldBeImmutableInIdentity() {
        Member member = Member.builder()
                .id("M001")
                .name("Alice")
                .email("alice@example.com")
                .build();

        // No setters for core fields
        assertNoSetterMethods(Member.class, "setId", "setName", "setEmail", "setPhone");

        assertThat(java.lang.reflect.Modifier.isFinal(Member.class.getModifiers()))
            .isTrue();

        // Check identity fields (exclude borrowedBooks)
        List<String> identityFields = Arrays.asList("id", "name", "email", "phone");
        for (java.lang.reflect.Field field : Member.class.getDeclaredFields()) {
            if (identityFields.contains(field.getName())) {
                assertThat(java.lang.reflect.Modifier.isFinal(field.getModifiers())).isTrue();
                assertThat(java.lang.reflect.Modifier.isPrivate(field.getModifiers())).isTrue();
            }
        }
    }

    @Test
    void borrowedBooksListShouldNotBeExposedDirectly() {
        Member member = Member.builder().id("M001").name("Alice").build();
        Book book1 = Book.create("Book1", "Author", "123");
        Book book2 = Book.create("Book2", "Author", "456");
    
        member.checkoutBook(book1);
        List<Book> externalList = member.getBorrowedBooks();
    
        member.checkoutBook(book2);
    
        assertThat(externalList).containsExactly(book1);
        assertThat(member.getBorrowedBooks()).containsExactly(book1, book2);
    }

    // Helper method to verify absence of setter methods
    private void assertNoSetterMethods(Class<?> clazz, String... expectedAbsentMethods) {
        List<String> methodNames = Arrays.stream(clazz.getDeclaredMethods())
            .map(Method::getName)
            .collect(Collectors.toList());

        for (String methodName : expectedAbsentMethods) {
            assertThat(methodNames)
                .withFailMessage("Setter method '%s' found in %s — violates Item 17", methodName, clazz.getSimpleName())
                .doesNotContain(methodName);
        }
    }
}