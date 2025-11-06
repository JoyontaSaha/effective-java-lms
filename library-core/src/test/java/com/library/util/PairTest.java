package com.library.util;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;
import com.library.core.Book;
import com.library.core.Member;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

class PairTest {

    @Test
    void genericPairShouldProvideTypeSafety() {
        // Item 25: Prefer generic types over raw types or Object
        Pair<String, Integer> pair = new Pair<>("Hello", 42);

        // Compile-time type safety — no casts needed
        String first = pair.first();
        Integer second = pair.second();

        assertThat(first).isEqualTo("Hello");
        assertThat(second).isEqualTo(42);
    }

    @Test
    void nonGenericPairWouldRequireCasts() {
        // ANTI-PATTERN: Non-generic version (for comparison)
        // Would require: Object first = pair.first(); String s = (String) first;
        // → Risk of ClassCastException
    }

    @Test
    void pairShouldBeImmutableAndObeyHashContract() {
        // 1. No setter methods
        assertNoSetterMethods(Pair.class, "setFirst", "setSecond");

        // 2. Fields are final and private
        for (Field field : Pair.class.getDeclaredFields()) {
            if (Modifier.isStatic(field.getModifiers())) continue;
            assertThat(Modifier.isFinal(field.getModifiers())).isTrue();
            assertThat(Modifier.isPrivate(field.getModifiers())).isTrue();
        }
    
        Book book = Book.create("Book", "Author", "123");
        Member member = Member.builder().id("M1").name("Alice").build();
        Pair<Book, Member> loan = new Pair<>(book, member);
        Pair<Book, Member> copy = new Pair<>(book, member);

        // 1. Same object: hashCode is stable (idempotent)
        assertThat(loan.hashCode()).isEqualTo(loan.hashCode());

        // 2. Equal objects: same hashCode (Item 11 contract)
        assertThat(loan).isEqualTo(copy);
        assertThat(loan.hashCode()).isEqualTo(copy.hashCode());

        // 3. Non-equal objects: typically different hashCode (not required, but likely)
        Pair<Book, Member> different = new Pair<>(
            Book.create("Other", "Author", "456"),
            member
        );
        assertThat(loan).isNotEqualTo(different);
        // We don't assert hash inequality — it's allowed but not guaranteed
    }

    // Helper method (reusable)
    private void assertNoSetterMethods(Class<?> clazz, String... methodNames) {
        java.util.List<String> actualMethods = java.util.Arrays.stream(clazz.getDeclaredMethods())
            .map(java.lang.reflect.Method::getName)
            .toList();

        for (String name : methodNames) {
            assertThat(actualMethods)
                .withFailMessage("Setter method '%s' found in %s", name, clazz.getSimpleName())
                .doesNotContain(name);
        }
    }
}