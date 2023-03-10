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
 * For performing argument checks and returning {@link Optional}s.
 */
@SuppressWarnings("SizeReplaceableByIsEmpty")
public final class Check {

    private Check() {
        // Private constructor since this shouldn't be instantiated.
    }

    public static <T> Optional<T> check(T obj, boolean condition) {

        if (!condition) {
            return Optional.empty();
        }

        return Optional.of(obj);
    }

    public static <T> Optional<T> checkState(T obj, boolean condition) {

        if (!condition) {
            return Optional.empty();
        }

        return Optional.of(obj);
    }

    public static <T> Optional<T> checkNotNull(@Nullable T obj) {

        if (obj == null) {
            return Optional.empty();
        }

        return Optional.of(obj);
    }

    public static <T extends CharSequence> Optional<T> checkNotEmpty(@Nullable T str) {

        return checkNotNull(str)
            .filter(s -> s.length() > 0);
    }

    public static <T extends Collection<?>> Optional<T> checkNotEmpty(@Nullable T collection) {

        return checkNotNull(collection)
            .filter(c -> !c.isEmpty());
    }

    public static <T extends Map<?, ?>> Optional<T> checkNotEmpty(@Nullable T map) {

        return checkNotNull(map)
            .filter(m -> !m.isEmpty());
    }

    public static <T> Optional<T[]> checkNotEmpty(@Nullable T[] array) {

        return checkNotNull(array)
            .filter(a -> a.length > 0);
    }

    public static <T extends CharSequence> Optional<T> checkNotBlank(@Nullable T str) {

        return checkNotNull(str)
            .flatMap(Check::checkNotEmpty)
            .filter(s -> s.toString().trim().length() > 0);
    }

    public static <T extends CharSequence> Optional<T> checkLength(
        @Nullable T str,
        int minLength,
        int maxLength) {

        return checkNotNull(str)
            .filter(s -> checkMinMax(s.length(), minLength, maxLength).isPresent());
    }

    public static <T extends CharSequence> Optional<T> checkLength(
        @Nullable T str,
        int minLength) {

        return checkLength(str, minLength, Integer.MAX_VALUE);
    }

    public static <T> Optional<T[]> checkLength(@Nullable T[] array, int minLength, int maxLength) {

        return checkNotNull(array)
            .filter(a -> checkMinMax(a.length, minLength, maxLength).isPresent());
    }

    public static <T> Optional<T[]> checkLength(@Nullable T[] array, int minSize) {
        return checkLength(array, minSize, Integer.MAX_VALUE);
    }

    public static <T extends Collection<?>> Optional<T> checkSize(
        @Nullable T collection,
        int minSize,
        int maxSize) {

        return checkNotNull(collection)
            .filter(c -> checkMinMax(c.size(), minSize, maxSize).isPresent());
    }

    public static <T extends Collection<?>> Optional<T> checkSize(@Nullable T collection, int minSize) {
        return checkSize(collection, minSize, Integer.MAX_VALUE);
    }

    public static <T extends Map<?, ?>> Optional<T> checkSize(
        @Nullable T map,
        int minSize,
        int maxSize) {

        return checkNotNull(map)
            .filter(m -> checkMinMax(m.size(), minSize, maxSize).isPresent());
    }

    public static <T extends Map<?, ?>> Optional<T> checkSize(@Nullable T map, int minSize) {
        return checkSize(map, minSize, Integer.MAX_VALUE);
    }

    public static Optional<Integer> checkMin(int value, int minValue) {

        if (value < minValue) {
            return Optional.empty();
        }

        return Optional.of(value);
    }

    public static Optional<Integer> checkMax(int value, int maxValue) {

        if (value > maxValue) {
            return Optional.empty();
        }

        return Optional.of(value);
    }

    public static Optional<Integer> checkMinMax(int value, int minValue, int maxValue) {
        return checkMin(value, minValue)
            .flatMap(v -> checkMax(v, maxValue));
    }

    public static Optional<Path> checkExists(@Nullable Path path) {

        return checkNotNull(path)
            .filter(Files::exists);
    }

    public static Optional<File> checkExists(@Nullable File file) {

        return checkNotNull(file)
            .filter(File::exists);
    }

    public static Optional<Path> checkExists(@Nullable String path) {

        return checkNotNull(path)
            .flatMap(RequireUtils::pathGetOptional)
            .flatMap(Check::checkExists);
    }

    public static Optional<File> checkNotExists(@Nullable File path) {

        return checkNotNull(path)
            .filter(f -> !f.exists());
    }

    public static Optional<Path> checkNotExists(@Nullable Path path) {

        return checkNotNull(path)
            .filter(Files::notExists);
    }

    public static Optional<Path> checkNotExists(@Nullable String path) {

        return checkNotNull(path)
            .flatMap(RequireUtils::pathGetOptional)
            .flatMap(Check::checkNotExists);
    }

    public static Optional<File> checkRegularFile(@Nullable File file) {

        return checkNotNull(file)
            .filter(f -> checkExists(f).isPresent())
            .filter(File::isFile);
    }

    public static Optional<Path> checkRegularFile(@Nullable Path file) {

        return checkNotNull(file)
            .filter(f -> checkExists(f).isPresent())
            .filter(Files::isRegularFile);
    }

    public static Optional<Path> checkRegularFile(@Nullable String filePath) {

        return checkNotNull(filePath)
            .flatMap(RequireUtils::pathGetOptional)
            .flatMap(Check::checkRegularFile);
    }

    public static Optional<File> checkDirectory(@Nullable File directory) {

        return checkNotNull(directory)
            .filter(f -> checkExists(f).isPresent())
            .filter(File::isDirectory);
    }

    public static Optional<Path> checkDirectory(@Nullable Path directory) {

        return checkNotNull(directory)
            .filter(f -> checkExists(f).isPresent())
            .filter(Files::isDirectory);
    }

    public static Optional<Path> checkDirectory(@Nullable String directoryPath) {

        return checkNotNull(directoryPath)
            .flatMap(RequireUtils::pathGetOptional)
            .flatMap(Check::checkDirectory);
    }

    public static Optional<ZonedDateTime> checkFuture(@Nullable ZonedDateTime dateTime) {

        return checkNotNull(dateTime)
            .filter(d -> d.isAfter(ZonedDateTime.now()));
    }

    public static Optional<OffsetDateTime> checkFuture(@Nullable OffsetDateTime dateTime) {

        return checkNotNull(dateTime)
            .filter(d -> d.isAfter(OffsetDateTime.now()));
    }

    public static Optional<LocalDateTime> checkFuture(@Nullable LocalDateTime dateTime) {

        return checkNotNull(dateTime)
            .filter(d -> d.isAfter(LocalDateTime.now()));
    }

    public static Optional<LocalDate> checkFuture(@Nullable LocalDate date) {

        return checkNotNull(date)
            .filter(d -> d.isAfter(LocalDate.now()));
    }

    public static Optional<LocalTime> checkFuture(@Nullable LocalTime time) {

        return checkNotNull(time)
            .filter(t -> t.isAfter(LocalTime.now()));
    }

    public static Optional<ZonedDateTime> checkPast(@Nullable ZonedDateTime dateTime) {

        return checkNotNull(dateTime)
            .filter(d -> d.isBefore(ZonedDateTime.now()));
    }

    public static Optional<OffsetDateTime> checkPast(@Nullable OffsetDateTime dateTime) {

        return checkNotNull(dateTime)
            .filter(d -> d.isBefore(OffsetDateTime.now()));
    }

    public static Optional<LocalDateTime> checkPast(@Nullable LocalDateTime dateTime) {

        return checkNotNull(dateTime)
            .filter(d -> d.isBefore(LocalDateTime.now()));
    }

    public static Optional<LocalDate> checkPast(@Nullable LocalDate date) {

        return checkNotNull(date)
            .filter(d -> d.isBefore(LocalDate.now()));
    }

    public static Optional<LocalTime> checkPast(@Nullable LocalTime time) {

        return checkNotNull(time)
            .filter(t -> t.isBefore(LocalTime.now()));
    }
}
