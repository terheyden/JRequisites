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
    public static boolean check(boolean condition) {
        return condition;
    }

    /**
     * Require that some state condition is true. For example:
     * <pre>
     * {@code
     *     // Throws if false:
     *     checkState(user.isAdmin(), "User is not an admin");
     * }
     * </pre>
     * Note that:
     * <ul>
     *     <li>{@code check()} throws an {@code IllegalArgumentException}
     *     <li>{@code checkState()} throws an {@code IllegalStateException}
     * </ul>
     *
     * @param condition the condition to check
     */
    public static boolean checkState(boolean condition) {
        return condition;
    }

    /**
     * Require that some argument is not null. For example:
     * <pre>
     * {@code
     *     // If null, throws: "User is null"
     *     checkNotNull(user, "User");
     * }
     * </pre>
     * {@code checkNotNull()} throws an NPE, and {@code checkNonNullState()} throws an ISE.
     * @param obj the object to check
     * @param <T> the type of the object
     * @return the object, if it's not null, for chaining
     */
    public static <T> boolean checkNotNull(@Nullable T obj) {
        return obj != null;
    }

    /**
     * Require that a string argument is not null and not empty (zero length). For example:
     * <pre>
     * {@code
     *     // If null or zero length, throws: "Name is empty"
     *     checkNotEmpty(name, "Name");
     * }
     * </pre>
     * @param str the string to check
     * @param label the label to use in the error message if the string is empty
     * @param <T> the type of the string
     * @return the string, if it's not null, for chaining
     */
    public static <T extends CharSequence> boolean checkNotEmpty(@Nullable T str) {
        return checkNotNull(str) && str.length() > 0;
    }

    /**
     * Require that a collection argument is not null and not empty (zero length). For example:
     * <pre>
     * {@code
     *     // If null or zero length, throws: "List of users is empty"
     *     checkNotEmpty(users, "List of users");
     * }
     * </pre>
     * @param collection the collection to check
     * @param <T> the type of the collection
     * @return the collection, if it's not null, for chaining
     */
    public static <T extends Collection<?>> boolean checkNotEmpty(@Nullable T collection) {
        return checkNotNull(collection) && !collection.isEmpty();
    }

    public static <T extends Map<?, ?>> boolean checkNotEmpty(@Nullable T map) {
        return checkNotNull(map) && !map.isEmpty();
    }

    public static <T> boolean checkNotEmpty(@Nullable T[] array) {
        return checkNotNull(array) && array.length > 0;
    }

    public static <T extends CharSequence> boolean checkNotBlank(@Nullable T str) {
        return checkNotNull(str) && str.toString().trim().length() > 0;
    }

    public static <T extends CharSequence> boolean checkLength(@Nullable T str, int minLength, int maxLength) {
        return checkNotNull(str) && str.length() >= minLength && str.length() <= maxLength;
    }

    public static <T extends CharSequence> boolean checkLength(@Nullable T str, int minLength) {
        return checkNotNull(str) && str.length() >= minLength;
    }

    public static <T> boolean checkLength(@Nullable T[] array, int minLength, int maxLength) {
        return checkNotNull(array) && array.length >= minLength && array.length <= maxLength;
    }

    public static <T extends Collection<?>> boolean checkSize(@Nullable T collection, int minSize, int maxSize) {
        return checkNotNull(collection) && collection.size() >= minSize && collection.size() <= maxSize;
    }

    public static <T extends Map<?, ?>> boolean checkSize(@Nullable T map, int minSize, int maxSize) {
        return checkNotNull(map) && map.size() >= minSize && map.size() <= maxSize;
    }

    public static boolean checkMin(int value, int minValue) {
        return value >= minValue;
    }

    public static boolean checkMax(int value, int maxValue) {
        return value <= maxValue;
    }

    public static boolean checkMinMax(int value, int minValue, int maxValue) {
        return value >= minValue && value <= maxValue;
    }

    public static boolean checkExists(@Nullable Path path) {
        return checkNotNull(path) && Files.exists(path);
    }

    public static boolean checkExists(@Nullable File file) {
        return checkNotNull(file) && file.exists();
    }

    public static boolean checkExists(@Nullable String path) {
        return Optional.ofNullable(path)
            .flatMap(RequireUtils::pathGetOptional)
            .map(CheckBoolean::checkExists)
            .orElse(false);
    }

    public static boolean checkNotExists(@Nullable File path) {
        return checkNotNull(path) && !path.exists();
    }

    public static boolean checkNotExists(@Nullable Path path) {
        return checkNotNull(path) && Files.notExists(path);
    }

    public static boolean checkNotExists(@Nullable String path) {
        return Optional.ofNullable(path)
            .flatMap(RequireUtils::pathGetOptional)
            .map(CheckBoolean::checkNotExists)
            .orElse(false);
    }

    public static boolean checkRegularFile(@Nullable File file) {
        return checkNotNull(file) && file.isFile();
    }

    public static boolean checkRegularFile(@Nullable Path file) {
        return checkNotNull(file) && Files.isRegularFile(file);
    }

    public static boolean checkRegularFile(@Nullable String filePath) {
        return Optional.ofNullable(filePath)
            .flatMap(RequireUtils::pathGetOptional)
            .map(CheckBoolean::checkRegularFile)
            .orElse(false);
    }

    public static boolean checkDirectory(@Nullable File directory) {
        return checkNotNull(directory) && directory.isDirectory();
    }

    public static boolean checkDirectory(@Nullable Path directory) {
        return checkNotNull(directory) && Files.isDirectory(directory);
    }

    public static boolean checkDirectory(@Nullable String directoryPath) {
        return Optional.ofNullable(directoryPath)
            .flatMap(RequireUtils::pathGetOptional)
            .map(CheckBoolean::checkDirectory)
            .orElse(false);
    }

    public static boolean checkFuture(@Nullable ZonedDateTime dateTime) {
        return checkNotNull(dateTime) && dateTime.isAfter(ZonedDateTime.now());
    }

    public static boolean checkFuture(@Nullable OffsetDateTime dateTime) {
        return checkNotNull(dateTime) && dateTime.isAfter(OffsetDateTime.now());
    }

    public static boolean checkFuture(@Nullable LocalDateTime dateTime) {
        return checkNotNull(dateTime) && dateTime.isAfter(LocalDateTime.now());
    }

    public static boolean checkFuture(@Nullable LocalDate date) {
        return checkNotNull(date) && date.isAfter(LocalDate.now());
    }

    public static boolean checkFuture(@Nullable LocalTime time) {
        return checkNotNull(time) && time.isAfter(LocalTime.now());
    }

    public static boolean checkPast(@Nullable ZonedDateTime dateTime) {
        return checkNotNull(dateTime) && dateTime.isBefore(ZonedDateTime.now());
    }

    public static boolean checkPast(@Nullable OffsetDateTime dateTime) {
        return checkNotNull(dateTime) && dateTime.isBefore(OffsetDateTime.now());
    }

    public static boolean checkPast(@Nullable LocalDateTime dateTime) {
        return checkNotNull(dateTime) && dateTime.isBefore(LocalDateTime.now());
    }

    public static boolean checkPast(@Nullable LocalDate date) {
        return checkNotNull(date) && date.isBefore(LocalDate.now());
    }

    public static boolean checkPast(@Nullable LocalTime time) {
        return checkNotNull(time) && time.isBefore(LocalTime.now());
    }
}
