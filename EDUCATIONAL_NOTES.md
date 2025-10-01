1. library-core/src/test/java/com/library/core/BookTest.java

We’re testing the intent of Item 1 — static factories provide controlled instantiation, better naming, and future flexibility (e.g., caching, subtype return).

2. library-core/src/test/java/com/library/core/MemberTest.java

Builder pattern (Item 2) shines when you have optional parameters and want readable, fluent construction.
We enforce required fields at build-time, not construction-time — safer and clearer.

3. library-core/src/test/java/com/library/service/LibraryTest.java

Item 3 recommends enum singleton because: 

Concise, serialization-safe, thread-safe, and reflection-safe
Prevents multiple instantiation even under serialization or reflection attacks
JVM guarantees singleton-ness

4. library-core/src/test/java/com/library/util/ValidationUtilTest.java

Item 4 states: 

A utility class is a class that contains only static methods and static fields. It should not be instantiated.
To enforce this, declare a private constructor that throws an exception — making instantiation impossible even via reflection. 

5. library-core/src/test/java/com/library/service/LibraryServiceTest.java

Item 5 states: 

Static utility classes and singletons are inappropriate for classes whose behavior depends on state.
The Library has mutable state (catalog, members) — so it should not be a singleton.
DI gives us testability, reusability, and configurability.

6. library-core/src/test/java/com/library/util/ValidationUtilTest.java

java.util.regex.Pattern is immutable and thread-safe — perfect candidate for reuse (Item 6).
Compiling a regex is expensive; matching is cheap.

7. library-core/src/test/java/com/library/core/MemberLoanTest.java

Item 7 warns that "whenever a class manages its own memory, the programmer should be alert for memory leaks."
A Member managing a list of borrowed books is managing memory — so we must null out or remove obsolete references. 
In ArrayList, remove() already nulls the last element in the internal array (OpenJDK implementation), so no extra nulling needed.
But if we used a fixed-size array or stack, we’d need to explicitly null the slot (as in Bloch’s Stack example).