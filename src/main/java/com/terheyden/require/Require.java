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

    @Contract(value = "null, _ -> fail; !null, _ -> param1", pure = true)
    public static <T> T requireNotNull(@Nullable T obj, @Nullable String label) {

        if (obj == null) {
            throwNPE(label);
        }

        return obj;
    }

    @Contract(value = "null -> fail; !null -> param1", pure = true)
    public static <T> T requireNotNull(@Nullable T obj) {
        return requireNotNull(obj, null);
    }

    public static <T extends CharSequence> T requireNotEmpty(
        @Nullable T str,
        @Nullable String label) {

        requireNotNull(str, getLabel(label, "String"));

        if (str.length() == 0) {
            throwIAE(label, "String", " is empty");
        }

        return str;
    }

    public static <T extends CharSequence> T requireNotEmpty(@Nullable T str) {
        return requireNotEmpty(str, null);
    }

    public static <T extends Collection<?>> T requireNotEmpty(
        @Nullable T collection,
        @Nullable String label) {

        requireNotNull(collection, getLabel(label, "Collection"));

        if (collection.isEmpty()) {
            throwIAE(label, "Collection", " is empty");
        }

        return collection;
    }

    public static <T extends Collection<?>> T requireNotEmpty(@Nullable T collection) {
        return requireNotEmpty(collection, null);
    }

    public static <T extends Map<?, ?>> T requireNotEmpty(
        @Nullable T map,
        @Nullable String label) {

        requireNotNull(map, getLabel(label, "Map"));

        if (map.isEmpty()) {
            throwIAE(label, "Map", " is empty");
        }

        return map;
    }

    public static <T extends Map<?, ?>> T requireNotEmpty(@Nullable T map) {
        return requireNotEmpty(map, null);
    }

    public static <T> T[] requireNotEmpty(@Nullable T[] array, @Nullable String label) {

        requireNotNull(array, getLabel(label, "Array"));

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
        int minLength,
        int maxLength,
        @Nullable String label) {

        requireNotNull(str, getLabel(label, "String"));
        requireMinMaxContainers(str.length(), minLength, maxLength, getLabel(label, "String length"), str);
        return str;
    }

    public static <T extends CharSequence> T requireLength(@Nullable T str, int minLength, int maxLength) {
        return requireLength(str, minLength, maxLength, null);
    }

    public static <T extends CharSequence> T requireLength(
        @Nullable T str,
        int minLength,
        @Nullable String label) {

        return requireLength(str, minLength, Integer.MAX_VALUE, label);
    }

    public static <T extends CharSequence> T requireLength(@Nullable T str, int minLength) {
        return requireLength(str, minLength, Integer.MAX_VALUE, null);
    }

    public static <T extends Collection<?>> T requireSize(
        @Nullable T collection,
        int minSize,
        int maxSize,
        @Nullable String label) {

        requireNotNull(collection, getLabel(label, "Collection"));
        requireMinMaxContainers(collection.size(), minSize, maxSize, getLabel(label, "Collection size"), collection);
        return collection;
    }

    public static <T extends Collection<?>> T requireSize(@Nullable T collection, int minSize, int maxSize) {
        return requireSize(collection, minSize, maxSize, null);
    }

    public static <T extends Collection<?>> T requireSize(@Nullable T collection, int minSize, String label) {
        return requireSize(collection, minSize, Integer.MAX_VALUE, label);
    }

    public static <T extends Collection<?>> T requireSize(@Nullable T collection, int minSize) {
        return requireSize(collection, minSize, Integer.MAX_VALUE, null);
    }

    public static <T extends Map<?, ?>> T requireSize(
        @Nullable T map,
        int minSize,
        int maxSize,
        @Nullable String label) {

        requireNotNull(map, getLabel(label, "Map"));
        requireMinMaxContainers(map.size(), minSize, maxSize, getLabel(label, "Map size"), map);
        return map;
    }

    public static <T extends Map<?, ?>> T requireSize(@Nullable T map, int minSize, int maxSize) {
        return requireSize(map, minSize, maxSize, null);
    }

    public static <T extends Map<?, ?>> T requireSize(@Nullable T map, int minSize, String label) {
        return requireSize(map, minSize, Integer.MAX_VALUE, label);
    }

    public static <T extends Map<?, ?>> T requireSize(@Nullable T map, int minSize) {
        return requireSize(map, minSize, Integer.MAX_VALUE, null);
    }

    public static <T> T[] requireLength(@Nullable T[] array, int minLength, int maxLength, @Nullable String label) {

        requireNotNull(array, getLabel(label, "Array"));
        requireMinMaxContainers(array.length, minLength, maxLength, getLabel(label, "Array length"), Arrays.toString(array));
        return array;
    }

    public static <T> T[] requireLength(@Nullable T[] array, int minSize, int maxSize) {
        return requireLength(array, minSize, maxSize, null);
    }

    public static <T> T[] requireLength(@Nullable T[] array, int minSize, String label) {
        return requireLength(array, minSize, Integer.MAX_VALUE, label);
    }

    public static <T> T[] requireLength(@Nullable T[] array, int minSize) {
        return requireLength(array, minSize, Integer.MAX_VALUE, null);
    }

    public static int requireMin(int value, int minValue, @Nullable String label) {

        if (value < minValue) {
            throwIAE(label, "Value", " (" + value + ") is less than minimum: " + minValue);
        }

        return value;
    }

    public static int requireMin(int value, int minValue) {
        return requireMin(value, minValue, null);
    }

    public static int requireMax(int value, int maxValue, @Nullable String label) {

        if (value > maxValue) {
            throwIAE(label, "Value", " (" + value + ") is greater than maximum: " + maxValue);
        }

        return value;
    }

    public static int requireMax(int value, int maxValue) {
        return requireMax(value, maxValue, null);
    }

    public static int requireMinMax(int value, int minValue, int maxValue, @Nullable String label) {
        return requireMin(requireMax(value, maxValue, label), minValue, label);
    }

    public static int requireMinMax(int value, int minValue, int maxValue) {
        return requireMinMax(value, minValue, maxValue, null);
    }

    @Contract(value = "null, _ -> fail; !null, _ -> param1", pure = true)
    public static Path requireExists(@Nullable Path path, @Nullable String label) {

        requireNotNull(path, getLabel(label, "Path"));

        if (Files.notExists(path)) {
            throwIAE(label, "Path", " does not exist: " + path.toAbsolutePath());
        }

        return path;
    }

    @Contract(value = "null -> fail; !null -> param1", pure = true)
    public static Path requireExists(@Nullable Path path) {
        return requireExists(path, null);
    }

    public static File requireExists(@Nullable File file, @Nullable String label) {

        requireNotNull(file, getLabel(label, "Path"));

        if (!file.exists()) {
            throwIAE(label, "Path", " does not exist: " + file.getAbsolutePath());
        }

        return file;
    }

    public static File requireExists(@Nullable File file) {
        return requireExists(file, null);
    }

    public static Path requireExists(@Nullable String path, @Nullable String label) {

        requireNotNull(path, getLabel(label, "Path"));

        return requireExists(Paths.get(path), label);
    }

    public static Path requireExists(@Nullable String path) {
        return requireExists(path, null);
    }

    public static File requireNotExists(@Nullable File path, @Nullable String label) {

        requireNotNull(path, getLabel(label, "Path"));

        if (path.exists()) {
            throwIAE(label, "Path", " exists: " + path.getAbsolutePath());
        }

        return path;
    }

    public static File requireNotExists(@Nullable File file) {
        return requireNotExists(file, null);
    }

    @Contract(value = "null, _ -> fail; !null, _ -> param1", pure = true)
    public static Path requireNotExists(@Nullable Path path, @Nullable String label) {

        requireNotNull(path, getLabel(label, "Path"));

        // Note that Files.exists() and Files.notExists() are not the same thing.
        if (!Files.notExists(path)) {
            throwIAE(label, "Path", " exists: " + path.toAbsolutePath());
        }

        return path;
    }

    public static Path requireNotExists(@Nullable Path path) {
        return requireNotExists(path, null);
    }

    public static Path requireNotExists(@Nullable String path, @Nullable String label) {

        requireNotNull(path, getLabel(label, "Path"));

        return requireNotExists(Paths.get(path), label);
    }

    public static Path requireNotExists(@Nullable String filePath) {
        return requireNotExists(filePath, null);
    }

    public static File requireRegularFile(@Nullable File file, @Nullable String label) {

        requireNotNull(file, getLabel(label, "File"));
        requireExists(file, getLabel(label, "File"));

        if (!file.isFile()) {
            throwIAE(label, "File", " is not a regular file: " + file.getAbsolutePath());
        }

        return file;
    }

    public static File requireRegularFile(@Nullable File file) {
        return requireRegularFile(file, null);
    }

    @Contract(value = "null, _ -> fail; !null, _ -> param1", pure = true)
    public static Path requireRegularFile(@Nullable Path file, @Nullable String label) {

        requireNotNull(file, getLabel(label, "File"));
        requireExists(file, getLabel(label, "File"));

        if (!Files.isRegularFile(file)) {
            throwIAE(label, "File", " is not a regular file: " + file.toAbsolutePath());
        }

        return file;
    }

    public static Path requireRegularFile(@Nullable Path file) {
        return requireRegularFile(file, null);
    }

    public static Path requireRegularFile(@Nullable String filePath, @Nullable String label) {

        requireNotNull(filePath, getLabel(label, "File path"));
        return requireRegularFile(Paths.get(filePath), label);
    }

    public static Path requireRegularFile(@Nullable String filePath) {
        return requireRegularFile(filePath, null);
    }

    public static File requireDirectory(@Nullable File directory, @Nullable String label) {

        requireNotNull(directory, getLabel(label, "Directory"));
        requireExists(directory, getLabel(label, "Directory"));

        if (!directory.isDirectory()) {
            throwIAE(label, "Directory", " is not a directory: " + directory.getAbsolutePath());
        }

        return directory;
    }

    public static File requireDirectory(@Nullable File directory) {
        return requireDirectory(directory, null);
    }

    @Contract(value = "null, _ -> fail; !null, _ -> param1", pure = true)
    public static Path requireDirectory(@Nullable Path directory, @Nullable String label) {

        requireNotNull(directory, getLabel(label, "Directory"));
        requireExists(directory, getLabel(label, "Directory"));

        if (!Files.isDirectory(directory)) {
            throwIAE(label, "Directory", " is not a directory: " + directory.toAbsolutePath());
        }

        return directory;
    }

    public static Path requireDirectory(@Nullable Path directory) {
        return requireDirectory(directory, null);
    }

    public static Path requireDirectory(@Nullable String directoryPath, @Nullable String label) {

        requireNotNull(directoryPath, getLabel(label, "Directory path"));
        return requireDirectory(Paths.get(directoryPath), label);
    }

    public static Path requireDirectory(@Nullable String directoryPath) {
        return requireDirectory(directoryPath, null);
    }

    public static ZonedDateTime requireFuture(@Nullable ZonedDateTime dateTime, @Nullable String label) {

        requireNotNull(dateTime, getLabel(label, "Date Time"));

        if (dateTime.isBefore(ZonedDateTime.now())) {
            throwIAE(label, "Date Time", " is not in the future: " + dateTime + " vs " + ZonedDateTime.now());
        }

        return dateTime;
    }

    public static ZonedDateTime requireFuture(@Nullable ZonedDateTime dateTime) {
        return requireFuture(dateTime, null);
    }

    public static OffsetDateTime requireFuture(@Nullable OffsetDateTime dateTime, @Nullable String label) {

        requireNotNull(dateTime, getLabel(label, "Date Time"));

        if (dateTime.isBefore(OffsetDateTime.now())) {
            throwIAE(label, "Date Time", " is not in the future: " + dateTime + " vs " + OffsetDateTime.now());
        }

        return dateTime;
    }

    public static OffsetDateTime requireFuture(@Nullable OffsetDateTime dateTime) {
        return requireFuture(dateTime, null);
    }

    public static LocalDateTime requireFuture(@Nullable LocalDateTime dateTime, @Nullable String label) {

        requireNotNull(dateTime, getLabel(label, "Date Time"));

        if (dateTime.isBefore(LocalDateTime.now())) {
            throwIAE(label, "Date Time", " is not in the future: " + dateTime + " vs " + LocalDateTime.now());
        }

        return dateTime;
    }

    public static LocalDateTime requireFuture(@Nullable LocalDateTime dateTime) {
        return requireFuture(dateTime, null);
    }

    public static LocalDate requireFuture(@Nullable LocalDate date, @Nullable String label) {

        requireNotNull(date, getLabel(label, "Date"));

        if (date.isBefore(LocalDate.now())) {
            throwIAE(label, "Date", " is not in the future: " + date + " vs " + LocalDate.now());
        }

        return date;
    }

    public static LocalDate requireFuture(@Nullable LocalDate date) {
        return requireFuture(date, null);
    }

    public static LocalTime requireFuture(@Nullable LocalTime time, @Nullable String label) {

        requireNotNull(time, getLabel(label, "Time"));

        if (time.isBefore(LocalTime.now())) {
            throwIAE(label, "Time", " is not in the future: " + time + " vs " + LocalTime.now());
        }

        return time;
    }

    public static LocalTime requireFuture(@Nullable LocalTime time) {
        return requireFuture(time, null);
    }

    public static ZonedDateTime requirePast(@Nullable ZonedDateTime dateTime, @Nullable String label) {

        requireNotNull(dateTime, getLabel(label, "Date Time"));

        if (dateTime.isAfter(ZonedDateTime.now())) {
            throwIAE(label, "Date Time", " is not in the past: " + dateTime + " vs " + ZonedDateTime.now());
        }

        return dateTime;
    }

    public static ZonedDateTime requirePast(@Nullable ZonedDateTime dateTime) {
        return requirePast(dateTime, null);
    }

    public static OffsetDateTime requirePast(@Nullable OffsetDateTime dateTime, @Nullable String label) {

        requireNotNull(dateTime, getLabel(label, "Date Time"));

        if (dateTime.isAfter(OffsetDateTime.now())) {
            throwIAE(label, "Date Time", " is not in the past: " + dateTime + " vs " + OffsetDateTime.now());
        }

        return dateTime;
    }

    public static OffsetDateTime requirePast(@Nullable OffsetDateTime dateTime) {
        return requirePast(dateTime, null);
    }

    public static LocalDateTime requirePast(@Nullable LocalDateTime dateTime, @Nullable String label) {

        requireNotNull(dateTime, getLabel(label, "Date Time"));

        if (dateTime.isAfter(LocalDateTime.now())) {
            throwIAE(label, "Date Time", " is not in the past: " + dateTime + " vs " + LocalDateTime.now());
        }

        return dateTime;
    }

    public static LocalDateTime requirePast(@Nullable LocalDateTime dateTime) {
        return requirePast(dateTime, null);
    }

    public static LocalDate requirePast(@Nullable LocalDate date, @Nullable String label) {

        requireNotNull(date, getLabel(label, "Date"));

        if (date.isAfter(LocalDate.now())) {
            throwIAE(label, "Date", " is not in the past: " + date + " vs " + LocalDate.now());
        }

        return date;
    }

    public static LocalDate requirePast(@Nullable LocalDate date) {
        return requirePast(date, null);
    }

    public static LocalTime requirePast(@Nullable LocalTime time, @Nullable String label) {

        requireNotNull(time, getLabel(label, "Time"));

        if (time.isAfter(LocalTime.now())) {
            throwIAE(label, "Time", " is not in the past: " + time + " vs " + LocalTime.now());
        }

        return time;
    }

    public static LocalTime requirePast(@Nullable LocalTime time) {
        return requirePast(time, null);
    }

    private static int requireMinContainers(int value, int minValue, @Nullable String label, Object container) {

        if (value < minValue) {
            throwIAE(label, "Value",
                " (" + value + ") is less than minimum: " + minValue + " — contents: " + container);
        }

        return value;
    }


    private static int requireMaxContainers(int value, int maxValue, @Nullable String label, Object container) {

        if (value > maxValue) {
            throwIAE(label, "Value",
                " (" + value + ") is greater than maximum: " + maxValue + " — contents: " + container);
        }

        return value;
    }

    private static int requireMinMaxContainers(int value, int minValue, int maxValue, @Nullable String label, Object container) {
        return requireMinContainers(requireMaxContainers(value, maxValue, label, container), minValue, label, container);
    }

    /**
     * <pre>
     * {@code
     * setupName("The settings", "File") -> "The settings"
     * setupName(null, "File") -> "File"
     * }
     * </pre>
     */
    private static String getLabel(@Nullable String name, String defaultLabel) {
        return name != null ? name : defaultLabel;
    }

    private static void throwNPE(@Nullable String label) {
        throw new NullPointerException(getLabel(label, "Object") + " is null");
    }

    private static void throwIAE(@Nullable String label, String defaultLabel, String appendMessage) {
        throw new IllegalArgumentException(getLabel(label, defaultLabel) + appendMessage);
    }
}
