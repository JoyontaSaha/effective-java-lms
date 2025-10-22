package com.library.util;

import org.junit.jupiter.api.Test;
import java.util.*;

import static org.assertj.core.api.Assertions.*;
import com.library.core.Book;
import com.library.core.Member;
import com.library.service.InstrumentedLibraryService;
import com.library.service.LibraryService;
// DefaultLibraryService is package-private — so we can't use it directly from 'util' package

class CompositionOverInheritanceTest {

    // Anti-pattern: Inheritance from concrete class
    static class BrokenInstrumentedHashSet<E> extends HashSet<E> {
        private int addCount = 0;

        @Override
        public boolean add(E e) {
            addCount++;
            return super.add(e);
        }

        @Override
        public boolean addAll(Collection<? extends E> c) {
            addCount += c.size(); // ❌ BUG: super.addAll() calls add() internally!
            return super.addAll(c);
        }

        public int getAddCount() {
            return addCount;
        }
    }

    // Correct pattern: Composition
    static class SafeInstrumentedSet<E> {
        private final Set<E> set;
        private int addCount = 0;

        public SafeInstrumentedSet(Set<E> set) {
            this.set = set;
        }

        public boolean add(E e) {
            addCount++;
            return set.add(e);
        }

        public boolean addAll(Collection<? extends E> c) {
            addCount += c.size();
            return set.addAll(c);
        }

        public int getAddCount() {
            return addCount;
        }

        public int size() {
            return set.size();
        }
    }

    @Test
    void inheritanceApproachIsFragile() {
        // Item 18: Inheritance from concrete class is fragile
        BrokenInstrumentedHashSet<String> set = new BrokenInstrumentedHashSet<>();
        set.addAll(List.of("A", "B", "C"));

        // BUG: addCount = 6 (3 from addAll + 3 from internal add calls)
        // But expected: 3
        assertThat(set.getAddCount()).isEqualTo(6); // demonstrates broken counting
    }

    @Test
    void compositionApproachIsRobust() {
        // Item 18: Composition is robust and clear
        SafeInstrumentedSet<String> set = new SafeInstrumentedSet<>(new HashSet<>());
        set.addAll(List.of("A", "B", "C"));

        assertThat(set.getAddCount()).isEqualTo(3);
        assertThat(set.size()).isEqualTo(3);
    }
}