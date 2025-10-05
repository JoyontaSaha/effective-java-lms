package com.library.io;

import org.junit.jupiter.api.Test;
import java.io.IOException;
import static org.assertj.core.api.Assertions.*;

/**
 * Demonstrates why Item 9: Prefer try-with-resources.
 * This class shows how try-finally can lose exceptions and leak resources.
 */
class TryFinallyDangerDemoTest {

    @Test
    void tryFinallyCanLosePrimaryException() {
        BadResource resource = new BadResource();

        Throwable thrown = catchThrowable(() -> {
            try {
                resource.work();
            } finally {
                resource.close();
            }
        });

        // Primary exception (IllegalStateException) is lost!
        assertThat(thrown)
            .isInstanceOf(IOException.class)
            .hasMessage("Close failed");
    }

    @Test
    void tryWithResourcesPreservesPrimaryException() {
        BadResource resource = new BadResource();

        Throwable thrown = catchThrowable(() -> {
            try (BadResource r = resource) {
                r.work();
            }
        });

        // Primary exception preserved
        assertThat(thrown)
            .isInstanceOf(IllegalStateException.class)
            .hasMessage("Work failed");

        // Suppressed exceptions
        Throwable[] suppressed = thrown.getSuppressed();
        assertThat(suppressed)
            .hasSize(1);

        assertThat(suppressed[0])
            .isInstanceOf(IOException.class)
            .hasMessage("Close failed");
    }

    static class BadResource implements AutoCloseable {
        void work() {
            throw new IllegalStateException("Work failed");
        }

        @Override
        public void close() throws IOException {
            throw new IOException("Close failed");
        }
    }
}