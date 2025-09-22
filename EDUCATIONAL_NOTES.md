1. library-core/src/test/java/com/library/core/BookTest.java

We’re testing the intent of Item 1 — static factories provide controlled instantiation, better naming, and future flexibility (e.g., caching, subtype return).

2. library-core/src/test/java/com/library/core/MemberTest.java

Builder pattern (Item 2) shines when you have optional parameters and want readable, fluent construction.
We enforce required fields at build-time, not construction-time — safer and clearer. 