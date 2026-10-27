# Exercise File Pitfalls

Reference for Rule 13 in `scala-course/LEARNING_STYLE.md`. These are project-level checks that apply to every exercise file. Pitfalls tied to a specific topic live in that lesson's **Review points**.

- **Imports** go inside the file before any definitions (not outside objects). If case classes live inside an object, the imports go inside too.
- **All referenced types** must be imported or defined in the file.
- **Object name = file name** (see Rule 10), otherwise the code will not compile.
- **No name clashes across files:** all exercises share one source directory, so a top-level case class with a common name (domain names like `Wallet` or `Account` are typical) can clash with one in another file. Define such types inside the exercise object.
