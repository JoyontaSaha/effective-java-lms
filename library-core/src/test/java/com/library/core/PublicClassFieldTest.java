package com.library.core;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.List;

class PublicClassFieldTest {

    private static final List<Class<?>> PUBLIC_CLASSES = Arrays.asList(
        Book.class,
        Member.class
    );

    @Test
    void shouldNotExposePublicFieldsInPublicClassesExceptConstants() {
        for (Class<?> clazz : PUBLIC_CLASSES) {
            Field[] allFields = clazz.getDeclaredFields();
            for (Field field : allFields) {
                if (!field.isSynthetic() && Modifier.isPublic(field.getModifiers())) {
                    boolean isConstant = Modifier.isStatic(field.getModifiers()) &&
                                         Modifier.isFinal(field.getModifiers());
                    if (!isConstant) {
                        Assertions.fail("Non-constant public field found in " 
                            + clazz.getSimpleName() + ": " + field.getName());
                    }
                }
            }
        }
    }
}