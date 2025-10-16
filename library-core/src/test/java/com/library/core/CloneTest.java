package com.library.core;

import org.junit.jupiter.api.Test;
import java.lang.reflect.Method;
import static org.assertj.core.api.Assertions.*;

class CloneTest {

    @Test
    void shouldNotImplementCloneBecauseItIsBroken() {
        // Item 13: Cloneable is broken — do not implement clone()
        // Instead, use copy constructors or static factories

        Book original = Book.create("Effective Java", "Joshua Bloch", "978-0134685991");

        // We do NOT provide a clone() method — intentional
        // This test documents the decision

        // Preferred approach: use the same factory to create a "copy" if needed
        // (Though Book is immutable, so copying is unnecessary)
        Book copy = Book.create(original.getTitle(), original.getAuthor(), original.getIsbn());

        assertThat(copy).isEqualTo(original);
        assertThat(copy).isNotSameAs(original);
    }

    @Test
    void shouldDemonstrateWhyCloneIsDangerous() {
        // Hypothetical: if we tried to implement clone(), we'd face issues:
        // - clone() returns Object — requires casting
        // - No compile-time safety
        // - Final fields cannot be set in clone()
        // - Inheritance breaks symmetry

        // Book has final fields — impossible to reassign in clone()
        // This is why immutable classes should NEVER implement clone()

        // Enforce: no clone() method exists
        Method[] methods = Book.class.getDeclaredMethods();
        assertThat(methods)
                .extracting(Method::getName)
                .doesNotContain("clone");
    }
}