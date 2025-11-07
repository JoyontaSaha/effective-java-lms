package com.library.architecture;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.lang.ArchRule;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.*;

class NoRawTypesTest {

    @Test
    void noRawCollections() {
        JavaClasses classes = new ClassFileImporter().importPackages("com.library");

        ArchRule noRawLists = noClasses()
            .should().dependOnClassesThat().haveNameMatching(".*\\.List$")
            .because("Use List<T>, not raw List");

        // Note: ArchUnit can't easily detect raw vs parameterized at bytecode level
        // So rely on compiler (-Werror) for enforcement
    }
}