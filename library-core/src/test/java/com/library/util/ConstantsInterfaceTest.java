package com.library.util;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;

// ANTI-PATTERN: Constant interface (Item 22 violation)
interface LibraryConstants {
    String DEFAULT_ENCODING = "UTF-8";
    int MAX_BOOK_TITLE_LENGTH = 255;
}

// CORRECT: Constants in a utility class
final class LibraryConfig {
    private LibraryConfig() { /* prevent instantiation */ }

    public static final String DEFAULT_ENCODING = "UTF-8";
    public static final int MAX_BOOK_TITLE_LENGTH = 255;
}

class ConstantsInterfaceTest {

    @Test
    void constantInterfaceIsAnAntiPattern() {
        // Item 22: Interfaces should define types, not constants
        // Using LibraryConstants as a namespace is a misuse
        String encoding = LibraryConstants.DEFAULT_ENCODING;
        assertThat(encoding).isEqualTo("UTF-8");

        // But this "works" — which is the problem!
        // It pollutes the implementing class's namespace
    }

    @Test
    void constantsShouldBeInUtilityClass() {
        // Item 22 + Item 4: Use noninstantiable utility class for constants
        String encoding = LibraryConfig.DEFAULT_ENCODING;
        int maxLen = LibraryConfig.MAX_BOOK_TITLE_LENGTH;

        assertThat(encoding).isEqualTo("UTF-8");
        assertThat(maxLen).isEqualTo(255);
    }

    // Demonstrate namespace pollution
    static class FakeBook implements LibraryConstants {
        // Now FakeBook has DEFAULT_ENCODING in its namespace
        // Even if it has nothing to do with encoding!
    }

    @Test
    void constantInterfaceCausesNamespacePollution() {
        FakeBook book = new FakeBook();
        // This compiles, but makes no sense:
        String encoding = book.DEFAULT_ENCODING;
        assertThat(encoding).isEqualTo("UTF-8");
    }
}