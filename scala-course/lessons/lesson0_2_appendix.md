# Lesson 0.2 — Appendix: Scala vs Java Lambdas in foldLeft

## Вопрос

Почему Scala лямбда в `foldLeft` выглядит иначе чем в Java?

```scala
acc.flatMap { list =>
  item.map { value =>
    list :+ value
  }
}
```

## 1. Блочный синтаксис `{ }` вместо `( )`

В Scala лямбда может быть:
- **Короткая:** `x => x + 1` (как Java `x -> x + 1`)
- **Блочная:** `{ x => ... }` (несколько строк, для читаемости)

Ваш код использует блочный синтаксис для вложенности.

**Java эквивалент:**
```java
acc.flatMap(list ->
    item.map(value -> {
        List<A> newList = new ArrayList<>(list);
        newList.add(value);
        return Optional.of(newList);
    })
);
```

## 2. Автоматический возврат без `return`

В Scala функция возвращает **последнее выражение** автоматически:

```scala
// Scala
acc.flatMap { list =>
  item.map { value =>
    list :+ value  // ← последнее выражение, автоматически возвращается
  }
}
```

```java
// Java
acc.flatMap(list ->
    item.map(value -> {
        return Optional.of(newList);  // ← явный return
    })
);
```

## 3. `map` на `Option` автоматически обёртывает результат

В Scala `item.map { value => list :+ value }` возвращает `Option[List[A]]` автоматически:
- Если `item = None` → результат `None`
- Если `item = Some(value)` → результат `Some(list :+ value)`

В Java `item.map(...)` тоже возвращает `Optional`, но вам нужно явно создать новый список и обёрнуть его:

```java
return Optional.of(newList);  // Java — явная обёртка
list :+ value                 // Scala — автоматически Some(...)
```

## 4. Протрассировка

```
acc = Some(List(100, 200))
item = Some(300)

acc.flatMap { list =>
  // list = List(100, 200) (извлечено из Some)
  
  item.map { value =>
    // value = 300 (извлечено из Some)
    
    list :+ value
    // = List(100, 200) :+ 300
    // = List(100, 200, 300)
    // автоматически обёрнуто в Some → Some(List(100, 200, 300))
  }
  // результат: Some(List(100, 200, 300))
}
// результат: Some(List(100, 200, 300))
```

## 5. Сравнение Java vs Scala

| Аспект | Java | Scala |
|--------|------|-------|
| Синтаксис лямбды | `list -> ...` | `{ list => ... }` |
| Возврат значения | `return Optional.of(...)` | Последнее выражение автоматически |
| Вложенность | `item.map(value -> { ... return ... })` | `item.map { value => ... }` |
| Создание нового списка | `new ArrayList<>(list); list.add(value)` | `list :+ value` (новый immutable список) |
| Обёртка результата | `Optional.of(newList)` | Автоматически `Some(...)` |

## 6. Ключевое отличие

**В Scala `map` на `Option` автоматически обёртывает результат в `Some`.** В Java `map` на `Optional` тоже обёртывает, но вам нужно явно создать новый объект (список) и вернуть его.

В Scala:
```scala
item.map { value =>
  list :+ value  // автоматически становится Some(List(...))
}
```

В Java:
```java
item.map(value -> {
    List<A> newList = new ArrayList<>(list);
    newList.add(value);
    return Optional.of(newList);  // явная обёртка
})
```

**Scala короче потому что:**
1. Нет `return`
2. Нет `new ArrayList` — `:+` создаёт новый immutable список
3. Нет `Optional.of()` — `map` на `Option` обёртывает автоматически
