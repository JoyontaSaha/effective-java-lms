package com.library.architecture;

import com.library.core.Member;
import org.junit.jupiter.api.Test;
import java.lang.reflect.Modifier;
import static org.assertj.core.api.Assertions.*;

class StaticMemberClassTest {

    @Test
    void memberBuilderShouldBeStatic() {
        // Item 24: Favor static member classes over nonstatic
        // Member.Builder does not access Member.this — so it should be static
        Class<?> builderClass = null;
        for (Class<?> declaredClass : Member.class.getDeclaredClasses()) {
            if (declaredClass.getSimpleName().equals("Builder")) {
                builderClass = declaredClass;
                break;
            }
        }

        assertThat(builderClass)
            .withFailMessage("Builder class not found in Member")
            .isNotNull();

        boolean isStatic = Modifier.isStatic(builderClass.getModifiers());
        assertThat(isStatic)
            .withFailMessage("Member.Builder is not static — violates Item 24")
            .isTrue();
    }
}