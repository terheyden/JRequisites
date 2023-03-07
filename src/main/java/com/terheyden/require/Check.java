package com.terheyden.require;

import javax.annotation.Nullable;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
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
import java.util.Optional;

/**
 * For performing parameter checks and returning {@link Optional}s.
 */
@SuppressWarnings("SizeReplaceableByIsEmpty")
public final class Check {

    private Check() {
        // Private constructor since this shouldn't be instantiated.
    }

    public static <T> Optional<T> check(T obj, boolean expression) {

        if (!expression) {
            return Optional.empty();
        }

        return Optional.of(obj);
    }

    public static <T> Optional<T> checkState(T obj, boolean expression) {

        if (!expression) {
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
            .filter(s -> s.length() >= minLength)
            .filter(s -> s.length() <= maxLength);
    }

    public static <T extends CharSequence> Optional<T> checkLength(@Nullable T str, int minLength) {
        return checkLength(str, minLength, Integer.MAX_VALUE);
    }

    public static <T extends Collection<?>> Optional<T> checkSize(
        @Nullable T collection,
        int minSize,
        int maxSize) {

        return checkNotNull(collection)
            .filter(c -> c.size() >= minSize)
            .filter(c -> c.size() <= maxSize);
    }

    public static <T extends Collection<?>> Optional<T> checkSize(@Nullable T collection, int minSize) {
        return checkSize(collection, minSize, Integer.MAX_VALUE);
    }

    public static <T extends Map<?, ?>> Optional<T> checkSize(
        @Nullable T map,
        int minSize,
        int maxSize) {

        return checkNotNull(map)
            .filter(m -> m.size() >= minSize)
            .filter(m -> m.size() <= maxSize);
    }

    public static <T extends Map<?, ?>> Optional<T> checkSize(@Nullable T map, int minSize) {
        return checkSize(map, minSize, Integer.MAX_VALUE);
    }

    public static <T> Optional<T[]> checkSize(@Nullable T[] array, int minSize, int maxSize) {

        return checkNotNull(array)
            .filter(a -> a.length >= minSize)
            .filter(a -> a.length <= maxSize);
    }

    public static <T> Optional<T[]> checkSize(@Nullable T[] array, int minSize) {
        return checkSize(array, minSize, Integer.MAX_VALUE);
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
        return checkMin(value, minValue).flatMap(v -> checkMax(v, maxValue));
    }

    public static Optional<File> checkRegularFile(@Nullable File file) {
        return Optional
            .ofNullable(file)
            .filter(File::isFile);
    }

    public static Optional<Path> checkRegularFile(@Nullable Path file) {
        return Optional
            .ofNullable(file)
            .filter(Files::isRegularFile);
    }

    public static Optional<Path> checkRegularFile(@Nullable String filePath) {
        return Optional
            .ofNullable(filePath)
            .flatMap(RequireUtils::safeGetPath)
            .filter(Files::isRegularFile);
    }

    public static Optional<File> checkDirectory(@Nullable File directory) {
        return Optional
            .ofNullable(directory)
            .filter(File::isDirectory);
    }

    public static Optional<Path> checkDirectory(@Nullable Path directory) {
        return Optional
            .ofNullable(directory)
            .filter(Files::isDirectory);
    }

    public static Optional<Path> checkDirectory(@Nullable String directoryPath) {
        return Optional
            .ofNullable(directoryPath)
            .flatMap(RequireUtils::safeGetPath)
            .filter(Files::isDirectory);
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
            .flatMap(RequireUtils::safeGetPath)
            .flatMap(Check::checkExists);
    }

    public static Optional<File> checkNotExists(@Nullable File path) {
        return Optional
            .ofNullable(path)
            .filter(file -> !file.exists());
    }

    public static Optional<Path> checkNotExists(@Nullable Path path) {
        return Optional
            .ofNullable(path)
            .filter(Files::notExists);
    }

    public static Optional<Path> checkNotExists(@Nullable String path) {
        return Optional
            .ofNullable(path)
            .flatMap(RequireUtils::safeGetPath)
            .filter(Files::notExists);
    }

    public static Optional<ZonedDateTime> checkZonedDateTime(
        @Nullable DateTimeFormatter formatter,
        @Nullable String zonedDateTime) {

        if (formatter == null) {
            return Optional.empty();
        }

        if (zonedDateTime == null) {
            return Optional.empty();
        }

        try {

            return Optional.of(ZonedDateTime.parse(zonedDateTime, formatter));

        } catch (DateTimeParseException ignore) {
            return Optional.empty();
        }
    }

    public static Optional<OffsetDateTime> checkOffsetDateTime(
        @Nullable DateTimeFormatter formatter,
        @Nullable String offsetDateTime) {

        if (formatter == null) {
            return Optional.empty();
        }

        if (offsetDateTime == null) {
            return Optional.empty();
        }

        try {

            return Optional.of(OffsetDateTime.parse(offsetDateTime, formatter));

        } catch (DateTimeParseException ignore) {
            // TODO: these should be logged
            return Optional.empty();
        }
    }

    public static Optional<LocalDateTime> checkLocalDateTime(
        @Nullable DateTimeFormatter formatter,
        @Nullable String localDateTime) {

        if (formatter == null) {
            return Optional.empty();
        }

        if (localDateTime == null) {
            return Optional.empty();
        }

        try {

            return Optional.of(LocalDateTime.parse(localDateTime, formatter));

        } catch (DateTimeParseException ignore) {
            return Optional.empty();
        }
    }

    public static Optional<LocalDate> checkLocalDate(
        @Nullable DateTimeFormatter formatter,
        @Nullable String localDate) {

        if (formatter == null) {
            return Optional.empty();
        }

        if (localDate == null) {
            return Optional.empty();
        }

        try {

            return Optional.of(LocalDate.parse(localDate, formatter));

        } catch (DateTimeParseException ignore) {
            return Optional.empty();
        }
    }

    public static Optional<LocalTime> checkLocalTime(
        @Nullable DateTimeFormatter formatter,
        @Nullable String localTime) {

        if (formatter == null) {
            return Optional.empty();
        }

        if (localTime == null) {
            return Optional.empty();
        }

        try {

            return Optional.of(LocalTime.parse(localTime, formatter));

        } catch (DateTimeParseException ignore) {
            return Optional.empty();
        }
    }

    public static Optional<Instant> checkInstant(@Nullable String instant) {

        if (instant == null) {
            return Optional.empty();
        }

        try {

            return Optional.of(Instant.parse(instant));

        } catch (DateTimeParseException ignore) {
            return Optional.empty();
        }
    }
}
