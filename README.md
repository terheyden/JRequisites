# JRequisites Library

_Java argument checking and validation._

## What Is It?

JRequisites is a fast, tiny library for validating method arguments.
It offers two classes:

### Require Class
Brings in `requireXYZ()` methods, which throw `IllegalArgumentException` (mostly):
```java
String name = "Cora";
int age = 19;

return new User(
    requireNotBlank(name, "Name"),
    requireMinimum(age, 21, "Age")); // Throws: "Age must be at least 21"
```

### Check Class
Brings in `checkXYZ()` methods, which return `Optional<>`:
```java
UUID userId; // assume this is valid ...

String userName = checkNotNull(userId, "User ID")
    .map(UserService::findById)
    .map(User::getName)
    .orElse("(no name)");
```

## Why Use It?
- tiny — much smaller than Commons or Guava
- extremely fast
  - no object allocation
  - no varargs
  - no String formatting
  - no reflection
- no dependencies

## Comparison

- [Apache Commons Validation](https://commons.apache.org/proper/commons-lang/javadocs/api-release/index.html)
- [Google Guava](https://guava.dev/releases/30.1-jre/api/docs/com/google/common/base/Preconditions.html)

| Feature              | JRequisites       | Apache Commons     | Google Guava |
|----------------------|-------------------|--------------------|--------------|
| Jar Size             | 16 kb             | 573 kb             | 3 MB       |
| Supports: Strings   | ✔️                | ✔️                 | ✔️           |
| Supports: Integers  | ✔️                | ✔️                 | ✔️           |
| Supports: Longs     | ✔️                | ✔️                 | ✔️           |
| Supports: Doubles   | ✔️                | ✔️                 | ✔️           |
| Supports: Booleans  | ✔️                | ✔️                 | ✔️           |
| Supports: Collections | ✔️                | ✔️                 | ✔️           |
| Supports: Maps       | ✔️                | ✔️                 | ✔️           |
| Supports: Arrays     | ✔️                | ✔️                 | ✔️           |
| Supports: Date/Time  | ✔️                | ✔️                 | ✔️           |
| Conditional Check    | require()️        | isTrue️()          | ✔️           |
| State Check          | requireState()️   | validState()️      | ✔️           |
| Null Check           | requireNotNull()️ | notNull()️         | ✔️           |
| Empty Check          | requireNotEmpty()️ | notEmpty()️        | ✔️           |
| Blank Check          | requireNotBlank()️ | notBlank()️        | ✔️           |
| Length Check         | ✔️                | ❌️                 | ✔️           |
| Optional Check       | ✔️                | ❌                  | ❌            |
| No Dependencies      | ✔️                | ❌                  | ❌            |
| No Reflection        | ✔️                | ❌                  | ❌            |
| No Varargs           | ✔️                | ❌                  | ❌            |
| Null Elements        | requireNotNull()️ | noNullElements()   | ❌            |"
| No String Formatting | ✔️                | ❌                  | ❌            |
| Exclusive Between   | ❌️                | exclusiveBetween() | ❌            |
| Inclusive Between   | ❌️                | inclusiveBetween() | ❌            |
| Minimum              | ✔️                | ❌                  | ❌            |
| Maximum              | ✔️                | ❌                  | ❌            |
| Index Check         | ❌                 | validIndex()       | ❌            |
| Finite / NaN         | ❌️                | finite()           | ❌            |
| Infinite / NaN       | ❌✔️                | notNaN()           | ❌            |
| Positive             | ✔️                | ❌                  | ❌            |
| Negative             | ✔️                | ❌                  | ❌            |
| Zero                 | ✔️                | ❌                  | ❌            |
| Non-Zero             | ✔️                | ❌                  | ❌            |
| Positive Or Zero     | ✔️                | ❌                  | ❌            |
| Assignable From      | ✔️                | isAssignableFrom() | ❌            |
| Instance Of          | ✔️                | isInstanceOf()     | ❌            |
| Matches Regex        | ✔️                | matchesPattern()   | ❌            |
| Contains             | ✔️                | ❌                  | ❌            |
| Contains Only        | ✔️                | ❌                  | ❌            |
| Contains None        | ✔️                | ❌                  | ❌            |

## Why Use It?

JRequisites is a tiny library that does one thing — argument validation.
It's designed to be used in production code, not just tests.

## How To Use It


## `CheckIf`

```java
CheckIfString.notBlank(name).orElse(default);
```

## About Null Checks

Our opinionated philosophy is that _nulls are a code smell_.
Unlike Jakarta Bean Validation, which treats nulls as valid unless
annotated with `@NotNull`, we treat null arguments as invalid for all checks.

## Contents

### Validating State (Boolean Expression)
```java
require(age >= 21, "Age must be at least 21");
```

### Null Check
```java
requireNotNull(name, "Name is null");
```

### Empty Check
Works for:
- `String`
- `CharSequence`
- `Collection`
- `Map`
- `Array`
- `Iterable`
- `Enumeration`

```java
requireNotEmpty(description, "Description is required");
requireNotEmpty(users, "No users found");

requireEmpty(messages, "There are ", messages.size(), " messages remaining");
```

### Blank (Only Whitespace) Check
Works for:
- `String`
- `CharSequence`

```java
requireNotBlank(username, "Username is required");
```

### Length Check
Works for:
- `String`
- `CharSequence`
- `Collection`
- `Map`
- `Array`

```java
requireSize(users, 2, "Require exactly 2 users to play");
requireMinSize(users, 2, "Require at least 2 users to play");
requireMaxLength(password, 32, "Password must be at most 32 characters");
```

```java
// Without JRequisites:
private static Map<String, Object> getSettingsMap(Path settingsFile) {

    if (settingsFile == null) {
    throw new IllegalArgumentException("Settings file not specified.");
    }

    if (!Files.isRegularFile(settingsFile)) {
    throw new IllegalArgumentException("Settings file is not a file: " + settingsFile);
    }

    return YamlMapper.yaml2Map(FileUtils2.readFile(settingsFile));
}

// Becomes:
private static Map<String, Object> getSettingsMap(Path settingsFile) {
    return YamlMapper.yaml2Map(readFile(requireRegularFile(settingsFile)));
}
```
