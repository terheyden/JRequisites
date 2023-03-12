package com.terheyden.require;

import javax.annotation.Nullable;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;

import static java.util.Objects.isNull;

/**
 * Require class.
 */
public final class CheckBoolean {

    private CheckBoolean() {
        // Private constructor since this shouldn't be instantiated.
    }

    /**
     * Require that some argument condition is true. For example:
     * <pre>
     * {@code
     *     // Throws if false:
     *     check(user.isAdmin(), "User is not an admin");
     * }
     * </pre>
     * {@code check()} throws an IAE, and {@code checkState()} throws an ISE.
     * @param condition the condition to check
     */
    public static boolean isTrue(boolean condition) {
        return condition;
    }

    public static boolean isFalse(boolean condition) {
        return !condition;
    }

    /**
     * Require that some argument is not null. For example:
     * <pre>
     * {@code
     *     // If null, throws: "User is null"
     *     isNotNull(user, "User");
     * }
     * </pre>
     * {@code isNotNull()} throws an NPE, and {@code checkNonNullState()} throws an ISE.
     * @param obj the object to check
     * @param <T> the type of the object
     * @return the object, if it's not null, for chaining
     */
    public static boolean isNotNull(@Nullable Object obj) {
        return obj != null;
    }

    /**
     * Require that a string argument is not null and not empty (zero length). For example:
     * <pre>
     * {@code
     *     // If null or zero length, throws: "Name is empty"
     *     isNotEmpty(name, "Name");
     * }
     * </pre>
     * @param str the string to check
     * @param label the label to use in the error message if the string is empty
     * @param <T> the type of the string
     * @return the string, if it's not null, for chaining
     */
    public static <T extends CharSequence> boolean isNotEmpty(@Nullable T str) {
        return isNotNull(str) && str.length() > 0;
    }

    /**
     * Require that a collection argument is not null and not empty (zero length). For example:
     * <pre>
     * {@code
     *     // If null or zero length, throws: "List of users is empty"
     *     isNotEmpty(users, "List of users");
     * }
     * </pre>
     * @param collection the collection to check
     * @param <T> the type of the collection
     * @return the collection, if it's not null, for chaining
     */
    public static <T extends Collection<?>> boolean isNotEmpty(@Nullable T collection) {
        return isNotNull(collection) && !collection.isEmpty();
    }

    public static <T extends Map<?, ?>> boolean isNotEmpty(@Nullable T map) {
        return isNotNull(map) && !map.isEmpty();
    }

    public static boolean isNotEmpty(@Nullable Object[] array) {
        return isNotNull(array) && array.length > 0;
    }

    public static <T extends CharSequence> boolean isNotBlank(@Nullable T str) {
        return isNotNull(str) && str.toString().trim().length() > 0;
    }

    public static <T extends CharSequence> boolean hasLengthBetween(@Nullable T str, int minLength, int maxLength) {
        return isNotNull(str) && str.length() >= minLength && str.length() <= maxLength;
    }

    public static <T extends CharSequence> boolean hasMinLength(@Nullable T str, int minLength) {
        return isNotNull(str) && str.length() >= minLength;
    }

    public static boolean hasLengthBetween(@Nullable Object[] array, int minLength, int maxLength) {
        return isNotNull(array) && array.length >= minLength && array.length <= maxLength;
    }

    public static boolean hasMinLength(@Nullable Object[] array, int minLength) {
        return hasLengthBetween(array, minLength, Integer.MAX_VALUE);
    }

    public static boolean hasMaxLength(@Nullable Object[] array, int maxLength) {
        return hasLengthBetween(array, 0, maxLength);
    }

    public static boolean hasMinSize(@Nullable Collection<?> collection, int minSize) {
        return isNotNull(collection) && collection.size() >= minSize;
    }

    public static boolean hasMinSize(@Nullable Map<?, ?> map, int minSize) {
        return isNotNull(map) && map.size() >= minSize;
    }

    public static boolean hasMaxSize(@Nullable Collection<?> collection, int maxSize) {
        return isNotNull(collection) && collection.size() <= maxSize;
    }

    public static boolean hasMaxSize(@Nullable Map<?, ?> map, int maxSize) {
        return isNotNull(map) && map.size() <= maxSize;
    }

    public static <T extends Collection<?>> boolean hasSizeBetween(@Nullable T collection, int minSize, int maxSize) {
        return isNotNull(collection) && collection.size() >= minSize && collection.size() <= maxSize;
    }

    public static <T extends Map<?, ?>> boolean hasSizeBetween(@Nullable T map, int minSize, int maxSize) {
        return isNotNull(map) && map.size() >= minSize && map.size() <= maxSize;
    }

    public static boolean hasMinValue(int value, int minValue) {
        return value >= minValue;
    }

    public static boolean hasMaxValue(int value, int maxValue) {
        return value <= maxValue;
    }

    public static boolean hasValueBetween(int value, int minValue, int maxValue) {
        return value >= minValue && value <= maxValue;
    }

    public static boolean pathExists(@Nullable Path path) {
        return isNotNull(path) && Files.exists(path);
    }

    public static boolean pathExists(@Nullable File file) {
        return isNotNull(file) && file.exists();
    }

    public static boolean pathExists(@Nullable String path) {
        return Optional.ofNullable(path)
            .flatMap(RequireUtils::pathGetOptional)
            .map(CheckBoolean::pathExists)
            .orElse(false);
    }

    public static boolean pathNotExists(@Nullable File path) {
        return isNull(path) || !path.exists();
    }

    public static boolean pathNotExists(@Nullable Path path) {
        return isNull(path) || Files.notExists(path);
    }

    public static boolean pathNotExists(@Nullable String path) {

        if (path == null) {
            return true;
        }

        return Optional.of(path)
            .flatMap(RequireUtils::pathGetOptional)
            .map(CheckBoolean::pathNotExists)
            .orElse(false);
    }

    public static boolean isRegularFile(@Nullable File file) {
        return isNotNull(file) && file.isFile();
    }

    public static boolean isRegularFile(@Nullable Path file) {
        return isNotNull(file) && Files.isRegularFile(file);
    }

    public static boolean isRegularFile(@Nullable String filePath) {
        return Optional.ofNullable(filePath)
            .flatMap(RequireUtils::pathGetOptional)
            .map(CheckBoolean::isRegularFile)
            .orElse(false);
    }

    public static boolean isDirectory(@Nullable File directory) {
        return isNotNull(directory) && directory.isDirectory();
    }

    public static boolean isDirectory(@Nullable Path directory) {
        return isNotNull(directory) && Files.isDirectory(directory);
    }

    public static boolean isDirectory(@Nullable String directoryPath) {
        return Optional.ofNullable(directoryPath)
            .flatMap(RequireUtils::pathGetOptional)
            .map(CheckBoolean::isDirectory)
            .orElse(false);
    }

    public static boolean isFuture(@Nullable ZonedDateTime dateTime) {
        return isNotNull(dateTime) && dateTime.isAfter(ZonedDateTime.now());
    }

    public static boolean isFuture(@Nullable OffsetDateTime dateTime) {
        return isNotNull(dateTime) && dateTime.isAfter(OffsetDateTime.now());
    }

    public static boolean isFuture(@Nullable LocalDateTime dateTime) {
        return isNotNull(dateTime) && dateTime.isAfter(LocalDateTime.now());
    }

    public static boolean isFuture(@Nullable LocalDate date) {
        return isNotNull(date) && date.isAfter(LocalDate.now());
    }

    public static boolean isFuture(@Nullable LocalTime time) {
        return isNotNull(time) && time.isAfter(LocalTime.now());
    }

    public static boolean isPast(@Nullable ZonedDateTime dateTime) {
        return isNotNull(dateTime) && dateTime.isBefore(ZonedDateTime.now());
    }

    public static boolean isPast(@Nullable OffsetDateTime dateTime) {
        return isNotNull(dateTime) && dateTime.isBefore(OffsetDateTime.now());
    }

    public static boolean isPast(@Nullable LocalDateTime dateTime) {
        return isNotNull(dateTime) && dateTime.isBefore(LocalDateTime.now());
    }

    public static boolean isPast(@Nullable LocalDate date) {
        return isNotNull(date) && date.isBefore(LocalDate.now());
    }

    public static boolean isPast(@Nullable LocalTime time) {
        return isNotNull(time) && time.isBefore(LocalTime.now());
    }
}
