package com.library.service;

import com.library.core.Book;
import com.library.core.Member;
import com.library.io.ReportWriter;
import com.library.util.ValidationUtil;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

class InheritanceTest {

    // List all concrete classes in the system
    private static final List<Class<?>> CONCRETE_CLASSES = Arrays.asList(
        Book.class,
        Member.class,
        ValidationUtil.class,
        ReportWriter.class,
        DefaultLibraryService.class,
        InstrumentedLibraryService.class
        // LibraryService is interface → excluded
    );

    @Test
    void allConcreteClassesShouldBeFinalUnlessDesignedForInheritance() {
        // Item 19: Prohibit inheritance unless explicitly designed for it
        for (Class<?> clazz : CONCRETE_CLASSES) {
            boolean isFinal = Modifier.isFinal(clazz.getModifiers());
            assertThat(isFinal)
                .withFailMessage("Class %s is not final — violates Item 19", clazz.getSimpleName())
                .isTrue();
        }
    }
}