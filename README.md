# Requirements Lib

_Java parameter checking and validation._

- tiny, much smaller than Commons or Guava
- no dependencies
- no reflection
- extremely fast
- no object allocation
- no varargs
- no String formatting

## `CheckIf`

```java
CheckIfString.notBlank(name).orElse(default);
```

## About Null Checks

Our opinionated philosophy is that _nulls are a code smell_.
Unlike Jakarta Bean Validation, which treats nulls as valid unless
annotated with `@NotNull`, we treat null parameters as invalid for all checks.

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
// Without Requirements:
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
