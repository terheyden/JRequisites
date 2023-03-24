package com.terheyden.require;

import javax.annotation.Nullable;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

import org.jetbrains.annotations.Contract;

import static com.terheyden.require.Checks.isEmpty;

/**
 * Require class.
 */
@SuppressWarnings({
    "DuplicateStringLiteralInspection", // The strings are really short and make more sense in-line
    "java:S1192",                       // String literals should not be duplicated, same as above
    "SizeReplaceableByIsEmpty"          // BUG — isEmpty() isn't available in JDK 8 (only JDK 15+)
})
public final class Require {

    private Require() {
        // Private constructor since this shouldn't be instantiated.
    }

    /**
     * Require that some argument condition is true. For example:
     * <pre>
     * {@code
     *     // Throws if false:
     *     requireTrue(user.isAdmin(), "User is not an admin");
     * }
     * </pre>
     * {@code requireTrue()} throws an IAE, and {@code requireState()} throws an ISE.
     * @param condition the condition to check
     * @param errorMessage the error message to use if the condition is false
     * @throws IllegalArgumentException if the condition is false
     */
    public static void requireTrue(boolean condition, @Nullable String errorMessage) {
        if (!condition) throw new IllegalArgumentException(errorMessage == null ? "Condition is false" : errorMessage);
    }

    /**
     * Require that some argument condition is true. For example:
     * <pre>
     * {@code
     *     // If false, throws: "Condition is false"
     *     requireTrue(user.isAdmin());
     * }
     * </pre>
     * Note that:
     * <ul>
     *     <li>{@code requireTrue()} throws an {@code IllegalArgumentException}
     *     <li>{@code requireState()} throws an {@code IllegalStateException}
     * </ul>
     * {@code requireTrue()} throws an IAE, and {@code requireState()} throws an ISE.
     * @param condition the condition to check
     * @throws IllegalArgumentException if the condition is false
     */
    public static void requireTrue(boolean condition) {
        requireTrue(condition, null);
    }

    /**
     * Require that some state condition is true. For example:
     * <pre>
     * {@code
     *     // Throws if false:
     *     requireState(user.isAdmin(), "User is not an admin");
     * }
     * </pre>
     * Note that:
     * <ul>
     *     <li>{@code requireTrue()} throws an {@code IllegalArgumentException}
     *     <li>{@code requireState()} throws an {@code IllegalStateException}
     * </ul>
     * @param condition the condition to check
     * @param errorMessage the error message to use if the condition is false
     * @throws IllegalStateException if the condition is false
     */
    public static void requireState(boolean condition, @Nullable String errorMessage) {
        if (!condition) throw new IllegalStateException(errorMessage == null ? "Condition is false" : errorMessage);
    }

    /**
     * Require that some state or logic condition is true. For example:
     * <pre>
     * {@code
     *     // If false, throws: "Condition is false"
     *     requireState(user.isAdmin());
     * }
     * </pre>
     * {@code requireTrue()} throws an IAE, and {@code requireState()} throws an ISE.
     * @param condition the condition to check
     * @throws IllegalStateException if the condition is false
     */
    public static void requireState(boolean condition) {
        requireState(condition, null);
    }

    public static void requireFalse(boolean condition, @Nullable String errorMessage) {
        requireTrue(!condition, errorMessage == null ? "Condition is true" : errorMessage);
    }

    public static void requireFalse(boolean condition) {
        requireFalse(condition, "Condition is true");
    }

    /**
     * Require that some argument is not null, and provide a custom error message. For example:
     * <pre>
     * {@code
     *     // If null, throws: "User is null"
     *     requireNotNull(user, "User");
     * }
     * </pre>
     * {@code requireNotNull()} throws an NPE, and {@code requireNonNullState()} throws an ISE.
     * @param obj the object to check
     * @param label the label to use in the error message if the object is null
     * @param <T> the type of the object
     * @return the object, if it's not null, for chaining
     * @throws NullPointerException if the object is null
     * @throws IllegalArgumentException if the object is null
     */
    @Contract(value = "null, _ -> fail; !null, _ -> param1", pure = true)
    public static <T> T requireNotNull(@Nullable T obj, @Nullable String label) {
        if (obj == null) throwNPE(label);
        return obj;
    }

    /**
     * Require that some argument is not null. For example:
     * <pre>
     * {@code
     *     // If null, throws: "Object is null"
     *     requireNotNull(user);
     * }
     * </pre>
     * {@code requireNotNull()} throws an NPE, and {@code requireNonNullState()} throws an ISE.
     * @param obj the object to check
     * @param <T> the type of the object
     * @return the object, if it's not null, for chaining
     * @throws NullPointerException if the object is null
     * @throws IllegalArgumentException if the object is null
     */
    @Contract(value = "null -> fail; !null -> param1", pure = true)
    public static <T> T requireNotNull(@Nullable T obj) {
        return requireNotNull(obj, null);
    }

    /**
     * Require that a string argument is not null and not empty (zero length). For example:
     * <pre>
     * {@code
     *     // If null or zero length, throws: "Name is empty"
     *     requireNotEmpty(name, "Name");
     * }
     * </pre>
     * @param str the string to check
     * @param label the label to use in the error message if the string is empty
     * @param <T> the type of the string
     * @return the string, if it's not null, for chaining
     * @throws IllegalArgumentException if the string is null or empty
     */
    public static <T extends CharSequence> T requireNotEmpty(@Nullable T str, @Nullable String label) {
        requireNotNull(str, getName(label, "String"));
        if (str.length() == 0) throwIAE(label, "String", " is empty");
        return str;
    }

    /**
     * Require that a string argument is not null and not empty (zero length). For example:
     * <pre>
     * {@code
     *     // If null or zero length, throws: "String is empty"
     *     requireNotEmpty(name);
     * }
     * </pre>
     * @param str the string to check
     * @param <T> the type of the string
     * @return the string, if it's not null, for chaining
     * @throws IllegalArgumentException if the string is null or empty
     */
    public static <T extends CharSequence> T requireNotEmpty(@Nullable T str) {
        return requireNotEmpty(str, null);
    }

    /**
     * Require that a collection argument is not null and not empty (zero length). For example:
     * <pre>
     * {@code
     *     // If null or zero length, throws: "List of users is empty"
     *     requireNotEmpty(users, "List of users");
     * }
     * </pre>
     * @param collection the collection to check
     * @param label the label to use in the error message if the collection is empty
     * @param <T> the type of the collection
     * @return the collection, if it's not null, for chaining
     * @throws IllegalArgumentException if the collection is null or empty
     */
    public static <T extends Collection<?>> T requireNotEmpty(@Nullable T collection, @Nullable String label) {
        requireNotNull(collection, getName(label, "Collection"));
        if (collection.isEmpty()) throwIAE(label, "Collection", " is empty");
        return collection;
    }

    public static <T extends Collection<?>> T requireNotEmpty(@Nullable T collection) {
        return requireNotEmpty(collection, null);
    }

    @Contract(value = "null, _ -> fail; !null, _ -> param1", pure = true)
    public static <T extends Iterable<?>> T requireNotEmpty(@Nullable T iterable, @Nullable String label) {
        requireNotNull(iterable, getName(label, "Iterable"));
        if (isEmpty(iterable)) throwIAE(label, "Iterable", " is empty");
        return iterable;
    }

    public static <T extends Iterable<?>> T requireNotEmpty(@Nullable T iterable) {
        return requireNotEmpty(iterable, null);
    }

    /**
     * Require that a map argument is not null and not empty (zero length). For example:
     * <pre>
     * {@code
     *     // If null or zero length, throws: "Map of users is empty"
     *     requireNotEmpty(users, "Map of users");
     * }
     * </pre>
     * @param map the map to check
     * @param label the label to use in the error message if the map is empty
     * @param <T> the type of the map
     * @return the map, if it's not null, for chaining
     * @throws IllegalArgumentException if the map is null or empty
     */
    public static <T extends Map<?, ?>> T requireNotEmpty(
        @Nullable T map,
        @Nullable String label) {

        requireNotNull(map, getName(label, "Map"));

        if (map.isEmpty()) {
            throwIAE(label, "Map", " is empty");
        }

        return map;
    }

    public static <T extends Map<?, ?>> T requireNotEmpty(@Nullable T map) {
        return requireNotEmpty(map, null);
    }

    public static <T> T[] requireNotEmpty(@Nullable T[] array, @Nullable String label) {

        requireNotNull(array, getName(label, "Array"));

        if (array.length == 0) {
            throwIAE(label, "Array", " is empty");
        }

        return array;
    }

    public static <T> T[] requireNotEmpty(@Nullable T[] array) {
        return requireNotEmpty(array, null);
    }

    public static <T extends CharSequence> T requireNotBlank(
        @Nullable T str,
        @Nullable String label) {

        requireNotEmpty(str, label);

        if (str.toString().trim().length() == 0) {
            throwIAE(label, "String", " is blank");
        }

        return str;
    }

    public static <T extends CharSequence> T requireNotBlank(@Nullable T str) {
        return requireNotBlank(str, null);
    }

    public static <T extends CharSequence> T requireLength(
        @Nullable T str,
        int length,
        @Nullable String label) {

        requireNotNull(str, getName(label, "String"));
        requireContainerLength(str.length(), length, getName(label, "String length"), str);
        return str;
    }

    public static <T extends CharSequence> T requireLength(@Nullable T str, int length) {
        return requireLength(str, length, null);
    }

    public static <T> T[] requireLength(
        @Nullable T[] arrayToCheck,
        int requiredLength,
        @Nullable String label) {

        requireNotNull(arrayToCheck, getName(label, "Array"));
        requireContainerLength(arrayToCheck.length, requiredLength, getName(label, "Array length"), Arrays.toString(arrayToCheck));
        return arrayToCheck;
    }

    public static <T> T[] requireLength(@Nullable T[] arrayToCheck, int requiredLength) {
        return requireLength(arrayToCheck, requiredLength, null);
    }

    public static <T extends CharSequence> T requireMinLength(
        @Nullable T str,
        int minLength,
        @Nullable String label) {

        requireNotNull(str, getName(label, "String"));
        requireMinContainers(str.length(), minLength, getName(label, "String length"), str);
        return str;
    }

    public static <T extends CharSequence> T requireMinLength(@Nullable T str, int minLength) {
        return requireMinLength(str, minLength, null);
    }

    public static <T> T[] requireMinLength(@Nullable T[] array, int minLength, @Nullable String label) {
        requireNotNull(array, getName(label, "Array"));
        requireMinContainers(array.length, minLength, getName(label, "Array length"), Arrays.toString(array));
        return array;
    }

    public static <T> T[] requireMinLength(@Nullable T[] array, int minLength) {
        return requireMinLength(array, minLength, null);
    }

    public static <T extends Collection<?>> T requireMinSize(@Nullable T collection, int minSize, @Nullable String label) {
        requireNotNull(collection, getName(label, "Collection"));
        requireMinContainers(collection.size(), minSize, getName(label, "Collection size"), collection);
        return collection;
    }

    public static <T extends Collection<?>> T requireMinSize(@Nullable T collection, int minSize) {
        return requireMinSize(collection, minSize, null);
    }

    public static <T extends Map<?, ?>> T requireMinSize(@Nullable T map, int minSize, @Nullable String label) {
        requireNotNull(map, getName(label, "Map"));
        requireMinContainers(map.size(), minSize, getName(label, "Map size"), map);
        return map;
    }

    public static <T extends Map<?, ?>> T requireMinSize(@Nullable T map, int minSize) {
        return requireMinSize(map, minSize, null);
    }

    public static int requireMinValue(int value, int minValue, @Nullable String label) {
        if (value < minValue) throwIAE(label, "Value", " (" + value + ") is less than minimum: " + minValue);
        return value;
    }

    public static int requireMinValue(int value, int minValue) {
        return requireMinValue(value, minValue, null);
    }

    public static int requireMaxValue(int value, int maxValue, @Nullable String label) {
        if (value > maxValue) throwIAE(label, "Value", " (" + value + ") is greater than maximum: " + maxValue);
        return value;
    }

    public static int requireMaxValue(int value, int maxValue) {
        return requireMaxValue(value, maxValue, null);
    }

    @Contract(value = "null, _ -> fail; !null, _ -> param1", pure = true)
    public static Path requirePathExists(@Nullable Path path, @Nullable String label) {
        requireNotNull(path, getName(label, "Path"));
        if (Files.notExists(path)) throwIAE(label, "Path", " does not exist: " + path.toAbsolutePath());
        return path;
    }

    @Contract(value = "null -> fail; !null -> param1", pure = true)
    public static Path requirePathExists(@Nullable Path path) {
        return requirePathExists(path, null);
    }

    public static File requirePathExists(@Nullable File file, @Nullable String label) {
        requireNotNull(file, getName(label, "Path"));
        if (!file.exists()) throwIAE(label, "Path", " does not exist: " + file.getAbsolutePath());
        return file;
    }

    public static File requirePathExists(@Nullable File file) {
        return requirePathExists(file, null);
    }

    public static Path requirePathExists(@Nullable String path, @Nullable String label) {
        requireNotNull(path, getName(label, "Path"));
        return requirePathExists(Paths.get(path), label);
    }

    public static Path requirePathExists(@Nullable String path) {
        return requirePathExists(path, null);
    }

    public static File requirePathNotExists(@Nullable File path, @Nullable String label) {
        requireNotNull(path, getName(label, "Path"));
        if (path.exists()) throwIAE(label, "Path", " exists: " + path.getAbsolutePath());
        return path;
    }

    public static File requirePathNotExists(@Nullable File file) {
        return requirePathNotExists(file, null);
    }

    @Contract(value = "null, _ -> fail; !null, _ -> param1", pure = true)
    public static Path requirePathNotExists(@Nullable Path path, @Nullable String label) {
        requireNotNull(path, getName(label, "Path"));
        // Note that Files.exists() and Files.notExists() are not the same thing.
        if (!Files.notExists(path)) throwIAE(label, "Path", " exists: " + path.toAbsolutePath());
        return path;
    }

    public static Path requirePathNotExists(@Nullable Path path) {
        return requirePathNotExists(path, null);
    }

    public static Path requirePathNotExists(@Nullable String path, @Nullable String label) {
        requireNotNull(path, getName(label, "Path"));
        return requirePathNotExists(Paths.get(path), label);
    }

    public static Path requirePathNotExists(@Nullable String filePath) {
        return requirePathNotExists(filePath, null);
    }

    public static File requireRegularFile(@Nullable File file, @Nullable String label) {
        requireNotNull(file, getName(label, "File"));
        requirePathExists(file, getName(label, "File"));
        if (!file.isFile()) throwIAE(label, "File", " is not a regular file: " + file.getAbsolutePath());
        return file;
    }

    public static File requireRegularFile(@Nullable File file) {
        return requireRegularFile(file, null);
    }

    @Contract(value = "null, _ -> fail; !null, _ -> param1", pure = true)
    public static Path requireRegularFile(@Nullable Path file, @Nullable String label) {
        requireNotNull(file, getName(label, "File"));
        requirePathExists(file, getName(label, "File"));
        if (!Files.isRegularFile(file)) throwIAE(label, "File", " is not a regular file: " + file.toAbsolutePath());
        return file;
    }

    public static Path requireRegularFile(@Nullable Path file) {
        return requireRegularFile(file, null);
    }

    public static Path requireRegularFile(@Nullable String filePath, @Nullable String label) {
        requireNotNull(filePath, getName(label, "File path"));
        return requireRegularFile(Paths.get(filePath), label);
    }

    public static Path requireRegularFile(@Nullable String filePath) {
        return requireRegularFile(filePath, null);
    }

    public static File requireDirectory(@Nullable File directory, @Nullable String label) {
        requireNotNull(directory, getName(label, "Directory"));
        requirePathExists(directory, getName(label, "Directory"));
        if (!directory.isDirectory()) throwIAE(label, "Directory", " is not a directory: " + directory.getAbsolutePath());
        return directory;
    }

    public static File requireDirectory(@Nullable File directory) {
        return requireDirectory(directory, null);
    }

    @Contract(value = "null, _ -> fail; !null, _ -> param1", pure = true)
    public static Path requireDirectory(@Nullable Path directory, @Nullable String label) {
        requireNotNull(directory, getName(label, "Directory"));
        requirePathExists(directory, getName(label, "Directory"));
        if (!Files.isDirectory(directory)) throwIAE(label, "Directory", " is not a directory: " + directory.toAbsolutePath());
        return directory;
    }

    public static Path requireDirectory(@Nullable Path directory) {
        return requireDirectory(directory, null);
    }

    public static Path requireDirectory(@Nullable String directoryPath, @Nullable String label) {
        requireNotNull(directoryPath, getName(label, "Directory path"));
        return requireDirectory(Paths.get(directoryPath), label);
    }

    public static Path requireDirectory(@Nullable String directoryPath) {
        return requireDirectory(directoryPath, null);
    }

    public static ZonedDateTime requireFuture(@Nullable ZonedDateTime dateTime, @Nullable String label) {
        requireNotNull(dateTime, getName(label, "Date Time"));
        if (dateTime.isBefore(ZonedDateTime.now())) throwIAE(label, "Date Time", " is not in the future: " + dateTime + " vs " + ZonedDateTime.now());
        return dateTime;
    }

    public static ZonedDateTime requireFuture(@Nullable ZonedDateTime dateTime) {
        return requireFuture(dateTime, null);
    }

    public static OffsetDateTime requireFuture(@Nullable OffsetDateTime dateTime, @Nullable String label) {
        requireNotNull(dateTime, getName(label, "Date Time"));
        if (dateTime.isBefore(OffsetDateTime.now())) throwIAE(label, "Date Time", " is not in the future: " + dateTime + " vs " + OffsetDateTime.now());
        return dateTime;
    }

    public static OffsetDateTime requireFuture(@Nullable OffsetDateTime dateTime) {
        return requireFuture(dateTime, null);
    }

    public static LocalDateTime requireFuture(@Nullable LocalDateTime dateTime, @Nullable String label) {
        requireNotNull(dateTime, getName(label, "Date Time"));
        if (dateTime.isBefore(LocalDateTime.now())) throwIAE(label, "Date Time", " is not in the future: " + dateTime + " vs " + LocalDateTime.now());
        return dateTime;
    }

    public static LocalDateTime requireFuture(@Nullable LocalDateTime dateTime) {
        return requireFuture(dateTime, null);
    }

    public static LocalDate requireFuture(@Nullable LocalDate date, @Nullable String label) {
        requireNotNull(date, getName(label, "Date"));
        if (date.isBefore(LocalDate.now())) throwIAE(label, "Date", " is not in the future: " + date + " vs " + LocalDate.now());
        return date;
    }

    public static LocalDate requireFuture(@Nullable LocalDate date) {
        return requireFuture(date, null);
    }

    public static LocalTime requireFuture(@Nullable LocalTime time, @Nullable String label) {
        requireNotNull(time, getName(label, "Time"));
        if (time.isBefore(LocalTime.now())) throwIAE(label, "Time", " is not in the future: " + time + " vs " + LocalTime.now());
        return time;
    }

    public static LocalTime requireFuture(@Nullable LocalTime time) {
        return requireFuture(time, null);
    }

    public static ZonedDateTime requirePast(@Nullable ZonedDateTime dateTime, @Nullable String label) {
        requireNotNull(dateTime, getName(label, "Date Time"));
        if (dateTime.isAfter(ZonedDateTime.now())) throwIAE(label, "Date Time", " is not in the past: " + dateTime + " vs " + ZonedDateTime.now());
        return dateTime;
    }

    public static ZonedDateTime requirePast(@Nullable ZonedDateTime dateTime) {
        return requirePast(dateTime, null);
    }

    public static OffsetDateTime requirePast(@Nullable OffsetDateTime dateTime, @Nullable String label) {
        requireNotNull(dateTime, getName(label, "Date Time"));
        if (dateTime.isAfter(OffsetDateTime.now())) throwIAE(label, "Date Time", " is not in the past: " + dateTime + " vs " + OffsetDateTime.now());
        return dateTime;
    }

    public static OffsetDateTime requirePast(@Nullable OffsetDateTime dateTime) {
        return requirePast(dateTime, null);
    }

    public static LocalDateTime requirePast(@Nullable LocalDateTime dateTime, @Nullable String label) {
        requireNotNull(dateTime, getName(label, "Date Time"));
        if (dateTime.isAfter(LocalDateTime.now())) throwIAE(label, "Date Time", " is not in the past: " + dateTime + " vs " + LocalDateTime.now());
        return dateTime;
    }

    public static LocalDateTime requirePast(@Nullable LocalDateTime dateTime) {
        return requirePast(dateTime, null);
    }

    public static LocalDate requirePast(@Nullable LocalDate date, @Nullable String label) {
        requireNotNull(date, getName(label, "Date"));
        if (date.isAfter(LocalDate.now())) throwIAE(label, "Date", " is not in the past: " + date + " vs " + LocalDate.now());
        return date;
    }

    public static LocalDate requirePast(@Nullable LocalDate date) {
        return requirePast(date, null);
    }

    public static LocalTime requirePast(@Nullable LocalTime time, @Nullable String label) {
        requireNotNull(time, getName(label, "Time"));
        if (time.isAfter(LocalTime.now())) throwIAE(label, "Time", " is not in the past: " + time + " vs " + LocalTime.now());
        return time;
    }

    public static LocalTime requirePast(@Nullable LocalTime time) {
        return requirePast(time, null);
    }

    private static int requireMinContainers(int value, int minValue, @Nullable String label, Object container) {

        if (value < minValue) {
            throwIAE(label, "Value",
                " (" + value + ") is less than minimum: " + minValue + " — contains: " + container);
        }

        return value;
    }


    private static int requireMaxContainers(int value, int maxValue, @Nullable String label, Object container) {

        if (value > maxValue) {
            throwIAE(label, "Value",
                " (" + value + ") is greater than maximum: " + maxValue + " — contains: " + container);
        }

        return value;
    }

    private static int requireContainerLength(int lengthToCheck, int requiredLength, String label, Object container) {

        if (lengthToCheck != requiredLength) {
            throwIAE(label, "Value",
                " (" + lengthToCheck + ") is not equal to: " + requiredLength + " — contains: " + container);
        }

        return lengthToCheck;
    }

    /**
     * <pre>
     * {@code
     * setupName("The settings", "File") -> "The settings"
     * setupName(null, "File") -> "File"
     * }
     * </pre>
     */
    private static String getName(@Nullable String name, String defaultLabel) {
        return name != null ? name : defaultLabel;
    }

    private static void throwNPE(@Nullable String customLabel) {
        throw new NullPointerException(getName(customLabel, "Object") + " is null");
    }

    private static void throwIAE(@Nullable String label, String defaultLabel, String appendMessage) {
        throw new IllegalArgumentException(getName(label, defaultLabel) + appendMessage);
    }
}
