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

## Naming

### Parameter Order
Every effort has been made to make method usage as easy to read as possible.
This is why constant parameters / limits come first. Compare:
```java
// Bad: reading is disjointed: "Is length SSN 9? Oh I see, is the length of the ssn var equal to 9."
isLength(ssn, 9);
// Better: reading is consistent: "Is length 9? Of ssn?"
isLength(9, ssn);
```

### Consistent Naming
- most throwing checks begin with `require`
- most boolean checks begin with `is`
- most Optional checks begin with `check`

Even if it might make more sense (in English) to have e.g. `hasLength()`,
we chose `isLength()` for consistency.

### Inclusive and Exclusive Ranges

For simplicity, JRequisites uses _closed_ ranges, meaning that both the beginning and end are inclusive.
For example:
```java
isLengthBetween(3, 8, "cat"); // is length between 3-8 (inclusive)? Yes.
```

### Stricter Definitions
JRequisites uses more precise definitions than you may be used to from other libraries.
Compare:
```java
// JRequisites:
isEmpty(null);     // false — a null value is not empty, it is invalid
nullOrEmpty(null); // true

// Apache Commons Validation:
isEmpty(null); // true
```

## Comparison to Other Libraries

| [Jakarta + Hibernate](https://docs.jboss.org/hibernate/stable/validator/reference/en-US/html_single/#section-builtin-constraints) | JRequisites                           |
|-----------------------------------------------------------------------------------------------------------------------------------|---------------------------------------|
| `@AssertFalse`                                                                                                                    | `isFalse()`                           |
| `@AssertTrue`                                                                                                                     | `isTrue()`                            |
| `@DecimalMax`                                                                                                                     | `isLessThan()`                        |
| `@DecimalMin`                                                                                                                     | `isGreaterThan()`                     |
| `@Digits`                                                                                                                         | `isDigits()`                          |
| `@Email`                                                                                                                          | `isEmail()`                           |
| `@Future`                                                                                                                         | `isFuture()`                          |
| `@FutureOrPresent`                                                                                                                | `isNowOrFuture()`                     |
| `@Max`                                                                                                                            | `isLessOrEqualTo()`                   |
| `@Min`                                                                                                                            | `isGreaterOrEqualTo()`                |
| `@NotBlank`                                                                                                                       | `isNotBlank()`                        |
| `@NotEmpty`                                                                                                                       | `isNotEmpty()`                        |
| `@NotNull`                                                                                                                        | `isNotNull()`                         |
| `@Negative`                                                                                                                       | `isLessThan()`                        |
| `@NegativeOrZero`                                                                                                                 | `isLessOrEqualTo()`                   |
| `@Null`                                                                                                                           | `isNull()`                            |
| `@Past`                                                                                                                           | `isPast()`                            |
| `@PastOrPresent`                                                                                                                  | `isNowOrPast()`                       |
| `@Pattern`                                                                                                                        | `isMatch()`                           |
| `@Positive`                                                                                                                       | `isGreaterThan()`                     |
| `@PositiveOrZero`                                                                                                                 | `isGreaterOrEqualTo()`                |
| `@Size`                                                                                                                           | `isSize() / isLength()`               |
| `@CreditCardNumber`                                                                                                               | ---                |
| `@Currency`                                                                                                                       | ---                        |
| `@DurationMax`                                                                                                                    | `isDurationLessThan()`                |
| `@DurationMin`                                                                                                                    | `isDurationGreaterThan()`             |
| `@EAN`                                                                                                                            | ---                             |
| `@ISBN`                                                                                                                           | ---                            |
| `@Length`                                                                                                                         | `isLengthBetween() / isSizeBetween()` |
| `@CodePointLength`                                                                                                                | ---                                   |
| `@LuhnCheck`                                                                                                                      | ---                                   |
| `@Mod10Check`                                                                                                                     | ---                                   |
| `@Mod11Check`                                                                                                                     | ---                                   |
| `@Normalized`                                                                                                                     | ---                                   |
| `@Range`                                                                                                                          | `isLengthBetween(), isSizeBetween()`  |
| `@ScriptAssert`                                                                                                                   | ---                                   |
| `@UniqueElements`                                                                                                                 | `isUniqueElements()`                  |
| `@URL`                                                                                                                            | `isURL()`                             |
| `@UUID`                                                                                                                           | `isUUID()`                            |


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

```java
if (isEmpty(users)) { ... }

this.users = requireNotEmpty(users, "users");

return ifNotEmpty(users)
    .map(u -> u.size())
    .orElse(0);
```
