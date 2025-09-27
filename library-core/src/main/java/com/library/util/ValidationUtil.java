package com.library.util;

import java.util.regex.Pattern;

// Item 4: Enforce noninstantiability with a private constructor
// Utility classes (e.g., java.util.Collections, java.util.Arrays) should never be instantiated.
// Without a private constructor, Java provides a public default one — misleading clients.
// Solution: Declare explicit private constructor that throws AssertionError.
// Also: Mark class 'final' to prevent subclassing (which could expose instantiation).

// Item 6: Utility class with cached immutable objects
public final class ValidationUtil {

    // Item 6: Avoid creating unnecessary objects
    // - Immutable objects (like Pattern) can and should be reused
    // - Recompiling regex on every call wastes CPU and creates garbage
    // - Static final field ensures one-time initialization (thread-safe in Java)
    // - This is safe because Pattern is immutable and thread-safe (Item 17, Item 83)
    private static final Pattern EMAIL_PATTERN = 
    Pattern.compile("^[A-Za-z0-9+_.-]+@([A-Za-z0-9.-]+\\.[A-Za-z]{2,})$");


    // Item 4: Explicit private constructor that throws AssertionError
    // - Prevents accidental 'new ValidationUtil()'
    // - Fails even under reflection (unlike just making it private without body)
    // - Message explains why instantiation is disallowed
    private ValidationUtil() {
        throw new AssertionError("Utility class");
    }

    // Item 49 (foreshadowed): Validate parameters — but these methods are null-safe by design

    /**
     * Validates email using precompiled regex pattern.
     * Item 4: Static utility method — stateless, pure function.
     * Item 6: Reuses static EMAIL_PATTERN — avoids costly recompilation.
     * Thread-safe and efficient for high-volume use (e.g., bulk member import).
     */
    public static boolean isValidEmail(String email) {
        if (email == null || email.isEmpty()) {
            return false;
        }
        return EMAIL_PATTERN.matcher(email).matches();
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

    // Item 6: Package-private accessor for testing cached pattern identity
    // NOT part of public API — for educational validation only
    static Pattern getEmailPatternForTesting() {
        return EMAIL_PATTERN;
    }

    // Future: Add more validators (e.g., phone, member ID) as needed
    // All will follow Item 4: static, stateless, in noninstantiable class
}