package com.library.architecture;

import org.junit.jupiter.api.Test;
import java.lang.reflect.Modifier;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.*;

class InterfaceDesignTest {

    @Test
    void interfacesShouldDefineTypesNotConstants() {
        // Item 22: Scan all interfaces — they must have at least one abstract method
        Class<?>[] interfaces = {
            com.library.service.LibraryService.class,
            com.library.io.ReportExporter.class
            // Add new interfaces here
        };

        for (Class<?> iface : interfaces) {
            long abstractMethodCount = Arrays.stream(iface.getDeclaredMethods())
                .filter(m -> Modifier.isAbstract(m.getModifiers()))
                .count();

            assertThat(abstractMethodCount)
                .withFailMessage("Interface %s has no abstract methods — violates Item 22 (constant interface?)", iface.getSimpleName())
                .isGreaterThan(0);
        }
    }
}