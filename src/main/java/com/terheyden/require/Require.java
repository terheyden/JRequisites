package com.terheyden.require;

import javax.annotation.Nullable;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Collection;
import java.util.Map;

import static com.terheyden.require.RequireUtils.throwUnchecked;

/**
 * Require class.
 */
public final class Require {

    private static final String STRING_IS_NULL = "String is null";
    private static final String STRING_IS_EMPTY = "String is empty";
    private static final String FILE_IS_NULL = "File is null";
    private static final String DATE_TIME_FORMATTER_IS_NULL = "DateTimeFormatter is null";
    private static final String DATE_TIME_STRING_IS_NULL = "Date time string is null";
    private static final String PATH_IS_NULL = "Path is null";
    private static final String PATH_ALREADY_EXISTS = "Path already exists";
    private static final String PATH_DOES_NOT_EXIST = "Path does not exist";

    private Require() {
        // Private constructor since this shouldn't be instantiated.
    }

    public static void require(boolean condition, @Nullable String errorMessage) {

        if (!condition) {
            throw new IllegalArgumentException(errorMessage == null ? "Condition is false" : errorMessage);
        }
    }

    public static void require(boolean condition) {
        require(condition, null);
    }

    public static void requireState(boolean condition, @Nullable String errorMessage) {

        if (!condition) {
            throw new IllegalStateException(errorMessage == null ? "Condition is false" : errorMessage);
        }
    }

    public static void requireState(boolean condition) {
        requireState(condition, null);
    }

    public static <T> T requireNotNull(@Nullable T obj, @Nullable String errorMessage) {

        if (obj == null) {
            throw new NullPointerException(errorMessage == null ? "Object is null" : errorMessage);
        }

        return obj;
    }

    public static <T> T requireNotNull(@Nullable T obj) {
        return requireNotNull(obj, null);
    }

    public static <T extends CharSequence> T requireNotEmpty(
        @Nullable T str,
        @Nullable String errorMessage) {

        if (str == null) {
            throw new NullPointerException(errorMessage == null ? STRING_IS_NULL : errorMessage);
        }

        if (str.length() == 0) {
            throw new IllegalArgumentException(errorMessage == null ? STRING_IS_EMPTY : errorMessage);
        }

        return str;
    }

    public static <T extends CharSequence> T requireNotEmpty(@Nullable T str) {
        return requireNotEmpty(str, null);
    }

    public static <T extends Collection<?>> T requireNotEmpty(
        @Nullable T collection,
        @Nullable String errorMessage) {

        if (collection == null) {
            throw new NullPointerException(errorMessage == null ? "Collection is null" : errorMessage);
        }

        if (collection.isEmpty()) {
            throw new IllegalArgumentException(errorMessage == null ? "Collection is empty" : errorMessage);
        }

        return collection;
    }

    public static <T extends Collection<?>> T requireNotEmpty(@Nullable T collection) {
        return requireNotEmpty(collection, null);
    }

    public static <T extends Map<?, ?>> T requireNotEmpty(
        @Nullable T map,
        @Nullable String errorMessage) {

        if (map == null) {
            throw new NullPointerException(errorMessage == null ? "Map is null" : errorMessage);
        }

        if (map.isEmpty()) {
            throw new IllegalArgumentException(errorMessage == null ? "Map is empty" : errorMessage);
        }

        return map;
    }

    public static <T extends Map<?, ?>> T requireNotEmpty(@Nullable T map) {
        return requireNotEmpty(map, null);
    }

    public static <T> T[] requireNotEmpty(@Nullable T[] array, @Nullable String errorMessage) {

        if (array == null) {
            throw new NullPointerException(errorMessage == null ? "Array is null" : errorMessage);
        }

        if (array.length == 0) {
            throw new IllegalArgumentException(errorMessage == null ? "Array is empty" : errorMessage);
        }

        return array;
    }

    public static <T> T[] requireNotEmpty(@Nullable T[] array) {
        return requireNotEmpty(array, null);
    }

    public static <T extends CharSequence> T requireNotBlank(
        @Nullable T str,
        @Nullable String errorMessage) {

        if (str == null) {
            throw new NullPointerException(errorMessage == null ? STRING_IS_NULL : errorMessage);
        }

        if (str.length() == 0) {
            throw new IllegalArgumentException(errorMessage == null ? STRING_IS_EMPTY : errorMessage);
        }

        if (str.toString().trim().length() == 0) {
            throw new IllegalArgumentException(errorMessage == null ? "String is blank" : errorMessage);
        }

        return str;
    }

    public static <T extends CharSequence> T requireNotBlank(@Nullable T str) {
        return requireNotBlank(str, null);
    }

    public static <T extends CharSequence> T requireLength(
        @Nullable T str,
        int minLength,
        int maxLength,
        @Nullable String errorMessage) {

        if (str == null) {
            throw new NullPointerException(errorMessage == null ? STRING_IS_NULL : errorMessage);
        }

        if (str.length() < minLength) {
            throw new IllegalArgumentException(errorMessage == null ? "String length is less than " + minLength : errorMessage);
        }

        if (str.length() > maxLength) {
            throw new IllegalArgumentException(errorMessage == null ? "String length is greater than " + maxLength : errorMessage);
        }

        return str;
    }

    public static <T extends CharSequence> T requireLength(@Nullable T str, int minLength, int maxLength) {
        return requireLength(str, minLength, maxLength, null);
    }

    public static <T extends CharSequence> T requireLength(
        @Nullable T str,
        int minLength,
        @Nullable String errorMessage) {

        return requireLength(str, minLength, Integer.MAX_VALUE, errorMessage);
    }

    public static <T extends CharSequence> T requireLength(@Nullable T str, int minLength) {
        return requireLength(str, minLength, Integer.MAX_VALUE, null);
    }

    public static <T extends Collection<?>> T requireSize(
        @Nullable T collection,
        int minSize,
        int maxSize,
        @Nullable String errorMessage) {

        if (collection == null) {
            throw new NullPointerException(errorMessage == null ? "Collection is null" : errorMessage);
        }

        if (collection.size() < minSize) {
            throw new IllegalArgumentException(errorMessage == null ? "Collection size is less than " + minSize : errorMessage);
        }

        if (collection.size() > maxSize) {
            throw new IllegalArgumentException(errorMessage == null ? "Collection size is greater than " + maxSize : errorMessage);
        }

        return collection;
    }

    public static <T extends Collection<?>> T requireSize(@Nullable T collection, int minSize, int maxSize) {
        return requireSize(collection, minSize, maxSize, null);
    }

    public static <T extends Collection<?>> T requireSize(@Nullable T collection, int minSize, String errorMessage) {
        return requireSize(collection, minSize, Integer.MAX_VALUE, errorMessage);
    }

    public static <T extends Collection<?>> T requireSize(@Nullable T collection, int minSize) {
        return requireSize(collection, minSize, Integer.MAX_VALUE, null);
    }

    public static <T extends Map<?, ?>> T requireSize(
        @Nullable T map,
        int minSize,
        int maxSize,
        @Nullable String errorMessage) {

        if (map == null) {
            throw new NullPointerException(errorMessage == null ? "Map is null" : errorMessage);
        }

        if (map.size() < minSize) {
            throw new IllegalArgumentException(errorMessage == null ? "Map size is less than " + minSize : errorMessage);
        }

        if (map.size() > maxSize) {
            throw new IllegalArgumentException(errorMessage == null ? "Map size is greater than " + maxSize : errorMessage);
        }

        return map;
    }

    public static <T extends Map<?, ?>> T requireSize(@Nullable T map, int minSize, int maxSize) {
        return requireSize(map, minSize, maxSize, null);
    }

    public static <T extends Map<?, ?>> T requireSize(@Nullable T map, int minSize, String errorMessage) {
        return requireSize(map, minSize, Integer.MAX_VALUE, errorMessage);
    }

    public static <T extends Map<?, ?>> T requireSize(@Nullable T map, int minSize) {
        return requireSize(map, minSize, Integer.MAX_VALUE, null);
    }

    public static <T> T[] requireSize(@Nullable T[] array, int minSize, int maxSize, @Nullable String errorMessage) {

        if (array == null) {
            throw new NullPointerException(errorMessage == null ? "Array is null" : errorMessage);
        }

        if (array.length < minSize) {
            throw new IllegalArgumentException(errorMessage == null ? "Array size is less than " + minSize : errorMessage);
        }

        if (array.length > maxSize) {
            throw new IllegalArgumentException(errorMessage == null ? "Array size is greater than " + maxSize : errorMessage);
        }

        return array;
    }

    public static <T> T[] requireSize(@Nullable T[] array, int minSize, int maxSize) {
        return requireSize(array, minSize, maxSize, null);
    }

    public static <T> T[] requireSize(@Nullable T[] array, int minSize, String errorMessage) {
        return requireSize(array, minSize, Integer.MAX_VALUE, errorMessage);
    }

    public static <T> T[] requireSize(@Nullable T[] array, int minSize) {
        return requireSize(array, minSize, Integer.MAX_VALUE, null);
    }

    public static int requireMinValue(int value, int minValue, @Nullable String errorMessage) {

        if (value < minValue) {
            throw new IllegalArgumentException(errorMessage == null ? "Value is less than " + minValue : errorMessage);
        }

        return value;
    }

    public static int requireMinValue(int value, int minValue) {
        return requireMinValue(value, minValue, null);
    }

    public static int requireLessThan(int value, int maxValue, @Nullable String errorMessage) {

        if (value > maxValue) {
            throw new IllegalArgumentException(errorMessage == null ? "Value is greater than " + maxValue : errorMessage);
        }

        return value;
    }

    public static int requireLessThan(int value, int maxValue) {
        return requireLessThan(value, maxValue, null);
    }

    public static File requireRegularFile(@Nullable File file, @Nullable String errorMessage) {

        if (file == null) {
            throw new NullPointerException(errorMessage == null ? FILE_IS_NULL : errorMessage);
        }

        if (!file.exists()) {
            throw new IllegalArgumentException(errorMessage == null ? "File does not exist" : errorMessage);
        }

        return file;
    }

    public static File requireRegularFile(@Nullable File file) {
        return requireRegularFile(file, null);
    }

    public static Path requireRegularFile(@Nullable Path file, @Nullable String errorMessage) {

        if (file == null) {
            throw new NullPointerException(errorMessage == null ? FILE_IS_NULL : errorMessage);
        }

        if (!Files.isRegularFile(file)) {
            throw new IllegalArgumentException(errorMessage == null ? "File does not exist" : errorMessage);
        }

        return file;
    }

    public static Path requireRegularFile(@Nullable Path file) {
        return requireRegularFile(file, null);
    }

    public static Path requireRegularFile(@Nullable String filePath, @Nullable String errorMessage) {

        if (filePath == null) {
            throw new NullPointerException(errorMessage == null ? "File path is null" : errorMessage);
        }

        return requireRegularFile(Paths.get(filePath), errorMessage);
    }

    public static Path requireRegularFile(@Nullable String filePath) {
        return requireRegularFile(filePath, null);
    }

    public static File requireDirectory(@Nullable File directory, @Nullable String errorMessage) {

        if (directory == null) {
            throw new NullPointerException(errorMessage == null ? "Directory is null" : errorMessage);
        }

        if (!directory.isDirectory()) {
            throw new IllegalArgumentException(errorMessage == null ? "Directory does not exist" : errorMessage);
        }

        return directory;
    }

    public static File requireDirectory(@Nullable File directory) {
        return requireDirectory(directory, null);
    }

    public static Path requireDirectory(@Nullable Path directory, @Nullable String errorMessage) {

        if (directory == null) {
            throw new NullPointerException(errorMessage == null ? "Directory is null" : errorMessage);
        }

        if (!Files.isDirectory(directory)) {
            throw new IllegalArgumentException(errorMessage == null ? "Directory does not exist" : errorMessage);
        }

        return directory;
    }

    public static Path requireDirectory(@Nullable Path directory) {
        return requireDirectory(directory, null);
    }

    public static Path requireDirectory(@Nullable String directoryPath, @Nullable String errorMessage) {

        if (directoryPath == null) {
            throw new NullPointerException(errorMessage == null ? "Directory path is null" : errorMessage);
        }

        return requireDirectory(Paths.get(directoryPath), errorMessage);
    }

    public static Path requireDirectory(@Nullable String directoryPath) {
        return requireDirectory(directoryPath, null);
    }

    public static Path requireExists(@Nullable Path path, @Nullable String errorMessage) {

        if (path == null) {
            throw new NullPointerException(errorMessage == null ? PATH_IS_NULL : errorMessage);
        }

        if (Files.notExists(path)) {
            throw new IllegalArgumentException(errorMessage == null ? PATH_DOES_NOT_EXIST : errorMessage);
        }

        return path;
    }

    public static Path requireExists(@Nullable Path path) {
        return requireExists(path, null);
    }

    public static File requireExists(@Nullable File file, @Nullable String errorMessage) {

        if (file == null) {
            throw new NullPointerException(errorMessage == null ? FILE_IS_NULL : errorMessage);
        }

        if (!file.exists()) {
            throw new IllegalArgumentException(errorMessage == null ? PATH_DOES_NOT_EXIST : errorMessage);
        }

        return file;
    }

    public static File requireExists(@Nullable File file) {
        return requireExists(file, null);
    }

    public static Path requireExists(@Nullable String path, @Nullable String errorMessage) {

        if (path == null) {
            throw new NullPointerException(errorMessage == null ? PATH_IS_NULL : errorMessage);
        }

        return requireExists(Paths.get(path), errorMessage);
    }

    public static Path requireExists(@Nullable String path) {
        return requireExists(path, null);
    }

    public static File requireNotExists(@Nullable File path, @Nullable String errorMessage) {

        if (path == null) {
            throw new NullPointerException(errorMessage == null ? PATH_IS_NULL : errorMessage);
        }

        if (path.exists()) {
            throw new IllegalArgumentException(errorMessage == null ? PATH_ALREADY_EXISTS : errorMessage);
        }

        return path;
    }

    public static File requireNotExists(@Nullable File file) {
        return requireNotExists(file, null);
    }

    public static Path requireNotExists(@Nullable Path path, @Nullable String errorMessage) {

        if (path == null) {
            throw new NullPointerException(errorMessage == null ? PATH_IS_NULL : errorMessage);
        }

        // Note that Files.exists() and Files.notExists() are not the same thing.
        if (!Files.notExists(path)) {
            throw new IllegalArgumentException(errorMessage == null ? PATH_ALREADY_EXISTS : errorMessage);
        }

        return path;
    }

    public static Path requireNotExists(@Nullable Path path) {
        return requireNotExists(path, null);
    }

    public static Path requireNotExists(@Nullable String path, @Nullable String errorMessage) {

        if (path == null) {
            throw new NullPointerException(errorMessage == null ? PATH_IS_NULL : errorMessage);
        }

        return requireNotExists(Paths.get(path), errorMessage);
    }

    public static Path requireNotExists(@Nullable String filePath) {
        return requireNotExists(filePath, null);
    }

    public static ZonedDateTime requireZonedDateTime(
        @Nullable DateTimeFormatter formatter,
        @Nullable String zonedDateTime,
        @Nullable String errorMessage) {

        if (formatter == null) {
            throw new NullPointerException(errorMessage == null ? DATE_TIME_FORMATTER_IS_NULL : errorMessage);
        }

        if (zonedDateTime == null) {
            throw new NullPointerException(errorMessage == null ? DATE_TIME_STRING_IS_NULL : errorMessage);
        }

        try {
            return ZonedDateTime.parse(zonedDateTime, formatter);
        } catch (DateTimeParseException e) {
            return throwUnchecked(e);
        }
    }

    public static ZonedDateTime requireZonedDateTime(
        @Nullable DateTimeFormatter formatter,
        @Nullable String zonedDateTime) {

        return requireZonedDateTime(formatter, zonedDateTime, null);
    }

    public static OffsetDateTime requireOffsetDateTime(
        @Nullable DateTimeFormatter formatter,
        @Nullable String offsetDateTime,
        @Nullable String errorMessage) {

        if (formatter == null) {
            throw new NullPointerException(errorMessage == null ? DATE_TIME_FORMATTER_IS_NULL : errorMessage);
        }

        if (offsetDateTime == null) {
            throw new NullPointerException(errorMessage == null ? DATE_TIME_STRING_IS_NULL : errorMessage);
        }

        try {
            return OffsetDateTime.parse(offsetDateTime, formatter);
        } catch (DateTimeParseException e) {
            return throwUnchecked(e);
        }
    }

    public static OffsetDateTime requireOffsetDateTime(
        @Nullable DateTimeFormatter formatter,
        @Nullable String offsetDateTime) {

        return requireOffsetDateTime(formatter, offsetDateTime, null);
    }

    public static LocalDateTime requireLocalDateTime(
        @Nullable DateTimeFormatter formatter,
        @Nullable String localDateTime,
        @Nullable String errorMessage) {

        if (formatter == null) {
            throw new NullPointerException(errorMessage == null ? DATE_TIME_FORMATTER_IS_NULL : errorMessage);
        }

        if (localDateTime == null) {
            throw new NullPointerException(errorMessage == null ? DATE_TIME_STRING_IS_NULL : errorMessage);
        }

        try {
            return LocalDateTime.parse(localDateTime, formatter);
        } catch (DateTimeParseException e) {
            return throwUnchecked(e);
        }
    }

    public static LocalDateTime requireLocalDateTime(
        @Nullable DateTimeFormatter formatter,
        @Nullable String localDateTime) {

        return requireLocalDateTime(formatter, localDateTime, null);
    }

    public static LocalDate requireLocalDate(
        @Nullable DateTimeFormatter formatter,
        @Nullable String localDate,
        @Nullable String errorMessage) {

        if (formatter == null) {
            throw new NullPointerException(errorMessage == null ? DATE_TIME_FORMATTER_IS_NULL : errorMessage);
        }

        if (localDate == null) {
            throw new NullPointerException(errorMessage == null ? DATE_TIME_STRING_IS_NULL : errorMessage);
        }

        try {
            return LocalDate.parse(localDate, formatter);
        } catch (DateTimeParseException e) {
            return throwUnchecked(e);
        }
    }

    public static LocalDate requireLocalDate(
        @Nullable DateTimeFormatter formatter,
        @Nullable String localDate) {

        return requireLocalDate(formatter, localDate, null);
    }

    public static LocalTime requireLocalTime(
        @Nullable DateTimeFormatter formatter,
        @Nullable String localTime,
        @Nullable String errorMessage) {

        if (formatter == null) {
            throw new NullPointerException(errorMessage == null ? DATE_TIME_FORMATTER_IS_NULL : errorMessage);
        }

        if (localTime == null) {
            throw new NullPointerException(errorMessage == null ? DATE_TIME_STRING_IS_NULL : errorMessage);
        }

        try {
            return LocalTime.parse(localTime, formatter);
        } catch (DateTimeParseException e) {
            return throwUnchecked(e);
        }
    }

    public static LocalTime requireLocalTime(
        @Nullable DateTimeFormatter formatter,
        @Nullable String localTime) {

        return requireLocalTime(formatter, localTime, null);
    }

    public static Instant requireInstant(
        @Nullable String instant,
        @Nullable String errorMessage) {

        if (instant == null) {
            throw new NullPointerException(errorMessage == null ? "Instant string is null" : errorMessage);
        }

        try {
            return Instant.parse(instant);
        } catch (DateTimeParseException e) {
            return throwUnchecked(e);
        }
    }

    public static Instant requireInstant(@Nullable String instant) {
        return requireInstant(instant, null);
    }
}
