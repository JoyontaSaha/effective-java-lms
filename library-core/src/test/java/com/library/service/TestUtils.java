package com.library.service;

import java.lang.reflect.Field;
import java.util.List;

import org.junit.jupiter.api.Disabled;

/**
 * Utility for test code to reset singleton state of Library.
 * Never use in production.
 */
@Disabled("For education purpose only.")
public final class TestUtils {

    private TestUtils() {
        // prevent instantiation
    }

    // S3011 = Reflection setAccessible; safe here for test-only code
    // tells Sonar to ignore
    @SuppressWarnings({"java:S3011", "unchecked"}) 
    public static void clearLibrary() {
        try {
            // Grab the private 'catalog' field from Library
            Field catalogField = Library.class.getDeclaredField("catalog");
            catalogField.setAccessible(true);// allowed in test code
            @SuppressWarnings("unchecked")
            List<?> catalog = (List<?>) catalogField.get(Library.INSTANCE);
            catalog.clear();

            // Same for 'members'
            Field membersField = Library.class.getDeclaredField("members");
            membersField.setAccessible(true);// allowed in test code
            @SuppressWarnings("unchecked")
            List<?> members = (List<?>) membersField.get(Library.INSTANCE);
            members.clear();

        } catch (ReflectiveOperationException e) {
            throw new TestSetupException("Failed to clear Library singleton for test", e);
        }
    }
}
