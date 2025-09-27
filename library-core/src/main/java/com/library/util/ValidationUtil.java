package com.library.util;

// Item 4: Enforce noninstantiability with a private constructor
// Utility classes (e.g., java.util.Collections, java.util.Arrays) should never be instantiated.
// Without a private constructor, Java provides a public default one — misleading clients.
// Solution: Declare explicit private constructor that throws AssertionError.
// Also: Mark class 'final' to prevent subclassing (which could expose instantiation).

public final class ValidationUtil {

    // Item 4: Explicit private constructor that throws AssertionError
    // - Prevents accidental 'new ValidationUtil()'
    // - Fails even under reflection (unlike just making it private without body)
    // - Message explains why instantiation is disallowed
    private ValidationUtil() {
        throw new AssertionError("Utility class");
    }

    // Item 49 (foreshadowed): Validate parameters — but these methods are null-safe by design

    /**
     * Validates email format (simplified for demo).
     * Item 4: Static utility method — stateless, pure function.
     *
     * @return {@code true} if email contains '@' and '.', {@code false} otherwise
     */
    public static boolean isValidEmail(String email) {
        if (email == null || email.isEmpty()) {
            return false;
        }
        return email.contains("@") && email.contains(".");
    }

    /**
     * Validates ISBN-13 format (13 digits, hyphens allowed).
     * Item 4: Utility method for domain validation.
     *
     * @return {@code true} if valid ISBN-13, {@code false} otherwise
     */
    public static boolean isValidISBN(String isbn) {
        if (isbn == null || isbn.isEmpty()) {
            return false;
        }
        String clean = isbn.replace("-", "");
        if (clean.length() != 13) {
            return false;
        }
        return clean.chars().allMatch(Character::isDigit);
    }

    // Future: Add more validators (e.g., phone, member ID) as needed
    // All will follow Item 4: static, stateless, in noninstantiable class
}