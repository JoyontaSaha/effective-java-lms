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