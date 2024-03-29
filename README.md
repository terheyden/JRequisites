# JRequisites

_Java argument checking and validation._

## What Is It?

JRequisites is a fast, tiny library for validating method arguments (parameters).

There are 3 primary uses:

- Validate method arguments, or throw an `IllegalArgumentException`
- Validate method arguments, or return `Optional` for more nuanced handling (e.g. default values)
- Validate method arguments, or return `false` (for use in streams or conditionals)

Let's take a look at some simple examples!

```java
// The 'Require' class contains validations that can throw an IllegalArgumentException:
this.name = Require.requireNotNull(name);

// The validations in the 'CheckIf' class return an Optional, for flexibility:
this.name = CheckIf.ifNotNull(name).orElse("(no name)");

// Validations in the 'Check' class return a boolean, so we can use it in streams or conditionals:
users.stream()
    .filter(Check::notNull)
    .collect(Collectors.toList());
```

## Why Use It?

### Unique Features

- A wider range of validations than other libraries
- Includes validations for a wider range of variable types
- Support for returning `Optional` or `boolean` for a wider variety of use cases

### Tiny

Much smaller than Apache Commons or Guava Preconditions:

- JRequisites: 16 kb
- Apache Commons: 573 kb
- Google Guava: 3 MB

### Extremely Fast

- No object allocation
- No varargs
- No String formatting
- No reflection
- No external dependencies

### Awesome Error Messages

```java
// Apache Commons:
notNull(name, "Name is null");

// Guava Preconditions:
checkNotNull(name, "Name is null");

// JRequisites:
requireNotNull(name, "Name"); // Outputs: "Name is null"
```

### Compiled with Java 1.8
Compatible with Java 1.8 and above.

### Lots of Useful Checks


| JRequisites            | Apache Commons | Guava Preconditions |
| ---------------------- | -------------- | ------------------- |
| `requireNotNull()`     | `notNull()`    | `checkNotNull()`    |
| `requireNotEmpty()`    | `notEmpty()`   | --                  |
| `requireNotBlank()`    | `notBlank()`   | --                  |
| `requireLength()`      | --             | --                  |
| `requireRegularFile()` | --             | --                  |
| `requireDirectory()`   | --             | --                  |
| `requireFuture()`      | --             | --                  |
| `requirePast()`        | --             | --                  |

### `Optional` Support

Want something more nuanced than throwing an exception?
Use the `CheckIf` class:

```java
String name = ifNotNull(userId, "User ID")
    .map(UserService::findById)
    .map(User::getName)
    .orElse("(no name)");
```

## Comparison to Other Libraries

- [Jakarta Bean Validation](https://docs.jboss.org/hibernate/stable/validator/reference/en-US/html_single/#section-builtin-constraints)
- [Google Guava Preconditions]()
- [Apache Commons Validation](https://commons.apache.org/proper/commons-validator/)


| Jakarta + Hibernate | JRequisites                           |
| ------------------- | ------------------------------------- |
| `@AssertFalse`      | `isFalse()`                           |
| `@AssertTrue`       | `isTrue()`                            |
| `@DecimalMax`       | `isLessThan()`                        |
| `@DecimalMin`       | `isGreaterThan()`                     |
| `@Digits`           | `isDigits()`                          |
| `@Email`            | `isEmail()`                           |
| `@Future`           | `isFuture()`                          |
| `@FutureOrPresent`  | `isNowOrFuture()`                     |
| `@Max`              | `isLessOrEqualTo()`                   |
| `@Min`              | `isGreaterOrEqualTo()`                |
| `@NotBlank`         | `isNotBlank()`                        |
| `@NotEmpty`         | `isNotEmpty()`                        |
| `@NotNull`          | `isNotNull()`                         |
| `@Negative`         | `isLessThan()`                        |
| `@NegativeOrZero`   | `isLessOrEqualTo()`                   |
| `@Null`             | `isNull()`                            |
| `@Past`             | `isPast()`                            |
| `@PastOrPresent`    | `isNowOrPast()`                       |
| `@Pattern`          | `isMatch()`                           |
| `@Positive`         | `isGreaterThan()`                     |
| `@PositiveOrZero`   | `isGreaterOrEqualTo()`                |
| `@Size`             | `isSize() / isLength()`               |
| `@CreditCardNumber` | ---                                   |
| `@Currency`         | ---                                   |
| `@DurationMax`      | `isDurationLessThan()`                |
| `@DurationMin`      | `isDurationGreaterThan()`             |
| `@EAN`              | ---                                   |
| `@ISBN`             | ---                                   |
| `@Length`           | `isLengthBetween() / isSizeBetween()` |
| `@CodePointLength`  | ---                                   |
| `@LuhnCheck`        | ---                                   |
| `@Mod10Check`       | ---                                   |
| `@Mod11Check`       | ---                                   |
| `@Normalized`       | ---                                   |
| `@Range`            | `isLengthBetween(), isSizeBetween()`  |
| `@ScriptAssert`     | ---                                   |
| `@UniqueElements`   | `isUniqueElements()`                  |
| `@URL`              | `isURL()`                             |
| `@UUID`             | `isUUID()`                            |

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

## Design Decisions

### All Checks

#### Removing the primitive array checks

- autocomplete for methods shouldn't be overwhelming, it's too confusing
- I think I need to trim back the APIs in general

### Just for Check

#### Negative usage vs. positive usage

- the boolean checks should have different behavior depending on if they're being used to qualify or disqualify
- this seems to only be tricksy for the `not` methods

```java
// Positive usage — should return true if password is null:
if (notLengthGreaterThan(8, password)) { return SUCCESS; }
// Negative usage — should return false if password is null:
if (notLengthGreaterThan(3, password)) { return FAIL; }
```

#### Except for `isNull`, `isEmpty`, and `isBlank`, "positive" checks, e.g. `isLength`, require non-null

- `isLength` = false if null
- `notLength` = true if null
- by and large, the `not` version is always `!isVersion`
