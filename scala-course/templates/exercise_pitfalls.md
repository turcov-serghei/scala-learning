# Exercise File Pitfalls

Reference for Rule 13 in `scala-course/LEARNING_STYLE.md`. These are project-level checks that apply to every exercise file. Pitfalls tied to a specific topic live in the exercise's **Technical Requirements**.

- **Imports** go at the top of the file, before any definitions, as is usual in Scala. An import inside an object also compiles; it is fine when it is needed only there.
- **All referenced types** must be imported or defined in the file.
- **Object name = file name** (see Rule 10), otherwise the code will not compile.
- **No name clashes across files:** all exercises share one source directory, so a top-level case class with a common name (domain names like `Wallet` or `Account` are typical) can clash with one in another file. Define such types inside the exercise object.
