package com.library.architecture;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

@AnalyzeClasses(packages = "com.library")
public class ArchitectureTest {

    @ArchTest
    void servicesShouldNotExposeImplementations(JavaClasses classes) {
        noClasses()
            .that().resideInAPackage("..service..")
            .and().haveSimpleNameContaining("Default")
            .should().bePublic()
            .check(classes);
    }
}
