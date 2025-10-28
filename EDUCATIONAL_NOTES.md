1. Consider static factory methods instead of constructors 

    library-core/src/test/java/com/library/core/BookTest.java

We’re testing the intent of Item 1 — static factories provide controlled instantiation, better naming, and future flexibility (e.g., caching, subtype return).

2. Consider a builder when faced with many constructor parameters 

    library-core/src/test/java/com/library/core/MemberTest.java

Builder pattern (Item 2) shines when you have optional parameters and want readable, fluent construction.
We enforce required fields at build-time, not construction-time — safer and clearer.

3. Enforce the singleton property with a private constructor or an enum type 

    library-core/src/test/java/com/library/service/LibraryTest.java

Item 3 recommends enum singleton because: 

Concise, serialization-safe, thread-safe, and reflection-safe
Prevents multiple instantiation even under serialization or reflection attacks
JVM guarantees singleton-ness

4. Enforce noninstantiability with a private constructor

    library-core/src/test/java/com/library/util/ValidationUtilTest.java

Item 4 states: 

A utility class is a class that contains only static methods and static fields. It should not be instantiated.
To enforce this, declare a private constructor that throws an exception — making instantiation impossible even via reflection. 

5. Prefer dependency injection to hardwiring resources

    library-core/src/test/java/com/library/service/LibraryServiceTest.java

Item 5 states: 

Static utility classes and singletons are inappropriate for classes whose behavior depends on state.
The Library has mutable state (catalog, members) — so it should not be a singleton.
DI gives us testability, reusability, and configurability.

6. Avoid creating unnecessary objects

    library-core/src/test/java/com/library/util/ValidationUtilTest.java

java.util.regex.Pattern is immutable and thread-safe — perfect candidate for reuse (Item 6).
Compiling a regex is expensive; matching is cheap.

7. Eliminate obsolete object references

    library-core/src/test/java/com/library/core/MemberLoanTest.java

Item 7 warns that "whenever a class manages its own memory, the programmer should be alert for memory leaks."
A Member managing a list of borrowed books is managing memory — so we must null out or remove obsolete references. 
In ArrayList, remove() already nulls the last element in the internal array (OpenJDK implementation), so no extra nulling needed.
But if we used a fixed-size array or stack, we’d need to explicitly null the slot (as in Bloch’s Stack example).

8. Avoid finalizers and cleaners

    library-core/src/test/java/com/library/io/ReportWriterTest.java

Item 8 states: 

Finalizers are unpredictable, often dangerous, and unnecessary.
Cleaners are better but still non-deterministic.
Explicit cleanup (like close()) is reliable, fast, and clear.

9. Prefer try-with-resources to try-finally

   library-core/src/test/java/com/library/io/TryFinallyDangerDemoTest.java
   library-core/src/test/java/com/library/io/ReportWriterTest.java

In try-finally, if an exception is thrown in the try block and another in finally, the first is lost.
try-with-resources suppresses secondary exceptions and preserves the primary.

10. Always override toString [Correction: Item 13 in Effective Java 3e]
    
    library-core/src/test/java/com/library/core/BookTest.java
    library-core/src/test/java/com/library/core/MemberTest.java

Item 10 states: 

Provide a good toString implementation makes your class more pleasant to use and easier to debug.
The format should be concise, unambiguous, and include all fields that affect logical equality.

11. Obey the general contract when overriding equals [Correction: Item 10 in Effective Java 3e]

    Always override hashCode when you override equals [Correction: Item 11 in Effective Java 3e]

    library-core/src/test/java/com/library/core/BookTest.java

Item 11 states: 

You must override hashCode in every class that overrides equals, or you will violate the general contract for hashCode.
Violation causes silent bugs in hash-based collections.

12. Consider implementing Comparable [Correction: Item 14 in Effective Java 3e]

    Always override `compareTo consistently with `equals` when implementing `Comparable`

    library-core/src/test/java/com/library/core/BookTest.java

Item 12 warns: 

The relation enforced by compareTo should be consistent with equals… violating this can cause strange behavior in sorted collections.

13. Minimize the accessibility of classes and members [Correction: Item 15 in Effective Java 3e]
    
    library-core/src/main/java/com/library/service/DefaultLibraryService.java
    library-core/src/main/java/com/library/core/Member.java

Make each class or member as inaccessible as possible.

14. In public classes, use accessor methods, not public fields [Correction: Item 16 in Effective Java 3e]

    library-core/src/test/java/com/library/architecture/ArchitectureTest.java
    library-core/src/test/java/com/library/core/PublicClassFieldTest.java

In public classes, use accessor methods, not public fields.

Item 14 permits public static final fields because they are true constants — they don’t break encapsulation or prevent evolution.

However, ensure the referenced object is immutable: 

✅ String, Integer, stateless Comparator → safe
❌ Mutable object (e.g., public static final List = new ArrayList()) → dangerous

15. Override clone judiciously [Correction: Item 13 in Effective Java 3e]
    
    library-core/src/test/java/com/library/core/CloneTest.java

Since Book is immutable, cloning is unnecessary — clients can safely share instances.
For mutable objects, copy factories (from(Book other)) are preferred.

The Cloneable interface is a mistake.

16. Go to 14

17. Minimize mutability by ensuring classes are immutable whenever possible, and justifying mutability when necessary. 

    library-core/src/test/java/com/library/core/ImmutabilityTest.java

    Immutable objects are simple, thread-safe, and can be shared freely.

18. Favor composition over inheritance

    Favor composition over inheritance” to avoid the fragility of inheritance-based designs.

    library-core/src/test/java/com/library/service/InstrumentedLibraryServiceTest.java
    library-core/src/test/java/com/library/util/CompositionOverInheritanceTest.java
    
    HashSet.addAll() internally calls add() — so overriding both breaks counting.

    Inheritance is a powerful way to achieve code reuse, but it is often misused.

     “Has-a” relationship is more honest than “is-a”.

19. Design and document for inheritance or else prohibit it.

    library-core/src/test/java/com/library/service/InheritanceTest.java

    Inheritance is fragile unless the superclass was explicitly designed to be extended.

    Rule: If a class is not final, it must be documented as safe for inheritance — otherwise, make it final.

    The designer of a class must decide whether to allow inheritance.

    If in doubt, leave it out.— Make it final.