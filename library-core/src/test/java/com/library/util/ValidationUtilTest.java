package com.library.util;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.regex.Pattern;

class ValidationUtilTest {

    @Test
    void shouldValidateEmailFormat() {
        // Item 4: Utility class with static methods
        assertThat(ValidationUtil.isValidEmail("alice@example.com")).isTrue();
        assertThat(ValidationUtil.isValidEmail("invalid-email")).isFalse();
        assertThat(ValidationUtil.isValidEmail("")).isFalse();
        assertThat(ValidationUtil.isValidEmail(null)).isFalse();
    }

    @Test
    void shouldValidateISBN13Format() {
        // ISBN-13: 13 digits, optionally with hyphens
        assertThat(ValidationUtil.isValidISBN("978-0134685991")).isTrue();  // Effective Java
        assertThat(ValidationUtil.isValidISBN("9780134685991")).isTrue();
        assertThat(ValidationUtil.isValidISBN("123")).isFalse();
        assertThat(ValidationUtil.isValidISBN("")).isFalse();
        assertThat(ValidationUtil.isValidISBN(null)).isFalse();
    }

    @Test
    void shouldNotAllowInstantiation() {
        // Item 4: Utility class must be noninstantiable — even via reflection
        assertThatCode(() -> {
            Constructor<ValidationUtil> constructor = ValidationUtil.class.getDeclaredConstructor();
            constructor.setAccessible(true);
            constructor.newInstance();
        }).isInstanceOf(InvocationTargetException.class)
          .satisfies(e -> 
              assertThat(e.getCause())
                  .isInstanceOf(AssertionError.class)
                  .hasMessage("Utility class")
          );
    }

    @Test
    void shouldReuseCompiledEmailPattern() {
        // Item 6: Avoid unnecessary object creation — cache expensive immutable objects
        // Pattern is thread-safe and immutable — safe to reuse
        // We expose internal pattern for testing via package-private method

        Pattern pattern1 = ValidationUtil.getEmailPatternForTesting();
        Pattern pattern2 = ValidationUtil.getEmailPatternForTesting();

        // Same instance reused — no new Pattern created on each call
        assertThat(pattern1).isSameAs(pattern2);
    }

    @Test
    @Disabled("Performance demo only")
    void demonstratePatternReusePerformance() {
        String email = "user@example.com";
        long start = System.nanoTime();
        for (int i = 0; i < 100_000; i++) {
            ValidationUtil.isValidEmail(email);
        }
        long duration = System.nanoTime() - start;
        System.out.println("Validation time (cached): " + duration / 1_000_000 + " ms");
        // Compare to uncached version — would be 10-100x slower
    }
}