# JRequisites Library

_Java argument checking and validation._

## What Is It?

JRequisites is a fast, tiny library for validating method arguments.
It offers two classes:

### 'Require' Class
Brings in `requireXYZ()` methods, which throw `IllegalArgumentException` (mostly):
```java
String name = "Cora";
int age = 19;

return new User(
    requireNotBlank(name, "Name"),
    requireMinimum(age, 21, "Age")); // Throws: "Age must be at least 21"
```

### 'Check' Class
Brings in `checkXYZ()` methods, which return `Optional<>`:
```java
UUID userId; // assume this is valid ...

String userName = checkNotNull(userId, "User ID")
    .map(UserService::findById)
    .map(User::getName)
    .orElse("(no name)");
```

## Why Use It?

### Tiny
Much smaller than Apache Commons or Guava Preconditions:
- JRequisites: 16 kb
- Apache Commons: 573 kb
- Google Guava: 3 MB

### Extremely Fast
  - no object allocation
  - no varargs
  - no String formatting
  - no reflection
  - no external dependencies

### Clear Naming Convention
```java
// Apache Commons:
notNull(name); // are you asking me or telling me?

// Guava Preconditions:
checkNotNull(name); // checking what, exactly?

// JRequisites:
requireNotNull(name); // Perfectly clear.
```

### Awesome Error Messages
```java
// Apache Commons:
notNull(name, "Name is null");

// Guava Preconditions:
checkNotNull(name, "Name is null");

// JRequisites:
requireNotNull(name, "Name"); // Outputs: "Name is null"
```

### Lots of Useful Checks

| JRequisites            | Apache Commons | Guava Preconditions |
|------------------------|----------------|---------------------|
| `requireNotNull()`     | `notNull()`    | `checkNotNull()`    |
| `requireNotEmpty()`    | `notEmpty()`   | --                  |
| `requireNotBlank()`    | `notBlank()`   | --                  |
| `requireLength()`      | --             | --                  |
| `requireRegularFile()` | --             | --                  |
| `requireDirectory()`   | --             | --                  |
| `requireFuture()`      | --             | --                  |
| `requirePast()`        | --             | --                  |

### `Optional<>` Support
Want something more nuanced than throwing an exception?
Use the `Check` class:
```java
String name = checkNotNull(userId, "User ID")
    .map(UserService::findById)
    .map(User::getName)
    .orElse("(no name)");
```

## How `null` is Handled

Our opinionated philosophy is that _nulls are a code smell_.
Nulls always throw or return false.

Consider:
```java
return notExists(filePath);
```
When `filePath` is null, do we return `true` or `false`?
It's true that a null path does not exist, but returning true
would be misleading; it implies that the check was successful
when it was not. Nulls are always false.

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
