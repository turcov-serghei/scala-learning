# Functional Style Review — General Frame

Reference for Rule 16 in `scala-course/LEARNING_STYLE.md`. This file holds only the course-wide principles. Concrete constructs to check live in the **Technical Requirements** of the exercise being reviewed (Rule 10) and in the theory of **completed lessons only**.

## Principles

- **Declarative over imperative:** results are expressions, not steps that mutate state.
- **Immutability:** no state changes where an immutable value would do.
- **Purity:** logic is separated from side effects (IO).
- **Single pass:** don't traverse the same data twice when one traversal suffices.
- **Totality:** absence and failure are values in the type, not `null` or exceptions used for flow control.

## How to review

1. Read the user's code and find where it departs from the principles above.
2. Check each departure against the exercise's Technical Requirements and the completed lessons. If the lesson theory presents a construct as idiomatic, it is NOT a violation.
3. Show the code with the Java-style sections highlighted, explain why each is imperative/OOP and why the functional version is better (composability, testability, single-pass efficiency).
4. Require the refactor. Do not offer it as optional.
