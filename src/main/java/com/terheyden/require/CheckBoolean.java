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
import java.util.Iterator;
import java.util.Map;
import java.util.Optional;

import static com.terheyden.require.RequireUtils.hasAtLeastSize;

/**
 * Methods in this class will never throw an exception, only return true or false.
 */
@SuppressWarnings("SizeReplaceableByIsEmpty")
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
     *
     * @param condition the condition to check
     */
    public static boolean isTrue(boolean condition) {
        return condition;
    }

    public static boolean isFalse(boolean condition) {
        return !condition;
    }

    /**
     * Returns true if the given object is null.
     */
    public static boolean isNull(@Nullable Object obj) {
        return obj == null;
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
     *
     * @param obj the object to check
     * @return the object, if it's not null, for chaining
     */
    public static boolean isNotNull(@Nullable Object obj) {
        return obj != null;
    }

    /**
     * Since nulls cannot be checked and are considered an invalid state,
     * this method returns false if the path is null.
     */
    public static boolean isEmpty(@Nullable CharSequence str) {
        return isNotNull(str) && str.length() == 0;
    }

    public static boolean isEmpty(@Nullable Collection<?> collection) {
        return isNotNull(collection) && collection.isEmpty();
    }

    public static boolean isEmpty(@Nullable Iterable<?> iterable) {
        return isNotNull(iterable) && !iterable.iterator().hasNext();
    }

    public static boolean isEmpty(@Nullable Map<?, ?> map) {
        return isNotNull(map) && map.isEmpty();
    }

    public static boolean isEmpty(@Nullable Object[] array) {
        return isNotNull(array) && array.length == 0;
    }

    public static boolean isEmpty(@Nullable int[] array) {
        return isNotNull(array) && array.length == 0;
    }

    public static boolean isEmpty(@Nullable byte[] array) {
        return isNotNull(array) && array.length == 0;
    }

    public static boolean isEmpty(@Nullable short[] array) {
        return isNotNull(array) && array.length == 0;
    }

    public static boolean isEmpty(@Nullable long[] array) {
        return isNotNull(array) && array.length == 0;
    }

    public static boolean isEmpty(@Nullable float[] array) {
        return isNotNull(array) && array.length == 0;
    }

    public static boolean isEmpty(@Nullable double[] array) {
        return isNotNull(array) && array.length == 0;
    }

    public static boolean isEmpty(@Nullable boolean[] array) {
        return isNotNull(array) && array.length == 0;
    }

    public static boolean isEmpty(@Nullable char[] array) {
        return isNotNull(array) && array.length == 0;
    }

    /**
     * Require that a string argument is not null and not empty (zero length). For example:
     * <pre>
     * {@code
     *     // If null or zero length, throws: "Name is empty"
     *     isNotEmpty(name, "Name");
     * }
     * </pre>
     *
     * @param str   the string to check
     * @param label the label to use in the error message if the string is empty
     * @return the string, if it's not null, for chaining
     */
    public static boolean isNotEmpty(@Nullable CharSequence str) {
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
     *
     * @param collection the collection to check
     * @return the collection, if it's not null, for chaining
     */
    public static boolean isNotEmpty(@Nullable Collection<?> collection) {
        return isNotNull(collection) && !collection.isEmpty();
    }

    public static boolean isNotEmpty(@Nullable Iterable<?> iterable) {
        return isNotNull(iterable) && iterable.iterator().hasNext();
    }

    public static boolean isNotEmpty(@Nullable Map<?, ?> map) {
        return isNotNull(map) && !map.isEmpty();
    }

    public static boolean isNotEmpty(@Nullable Object[] array) {
        return isNotNull(array) && array.length > 0;
    }

    public static boolean isNotEmpty(@Nullable int[] array) {
        return isNotNull(array) && array.length > 0;
    }

    public static boolean isNotEmpty(@Nullable byte[] array) {
        return isNotNull(array) && array.length > 0;
    }

    public static boolean isNotEmpty(@Nullable short[] array) {
        return isNotNull(array) && array.length > 0;
    }

    public static boolean isNotEmpty(@Nullable long[] array) {
        return isNotNull(array) && array.length > 0;
    }

    public static boolean isNotEmpty(@Nullable float[] array) {
        return isNotNull(array) && array.length > 0;
    }

    public static boolean isNotEmpty(@Nullable double[] array) {
        return isNotNull(array) && array.length > 0;
    }

    public static boolean isNotEmpty(@Nullable boolean[] array) {
        return isNotNull(array) && array.length > 0;
    }

    public static boolean isNotEmpty(@Nullable char[] array) {
        return isNotNull(array) && array.length > 0;
    }

    public static boolean isNotBlank(@Nullable CharSequence str) {
        return isNotNull(str) && str.toString().trim().length() > 0;
    }

    public static boolean hasLength(@Nullable CharSequence str, int length) {
        return isNotNull(str) && str.length() == length;
    }

    public static boolean hasLength(@Nullable Object[] array, int length) {
        return isNotNull(array) && array.length == length;
    }

    public static boolean hasLength(@Nullable int[] array, int length) {
        return isNotNull(array) && array.length == length;
    }

    public static boolean hasLength(@Nullable byte[] array, int length) {
        return isNotNull(array) && array.length == length;
    }

    public static boolean hasLength(@Nullable short[] array, int length) {
        return isNotNull(array) && array.length == length;
    }

    public static boolean hasLength(@Nullable long[] array, int length) {
        return isNotNull(array) && array.length == length;
    }

    public static boolean hasLength(@Nullable float[] array, int length) {
        return isNotNull(array) && array.length == length;
    }

    public static boolean hasLength(@Nullable double[] array, int length) {
        return isNotNull(array) && array.length == length;
    }

    public static boolean hasLength(@Nullable boolean[] array, int length) {
        return isNotNull(array) && array.length == length;
    }

    public static boolean hasLength(@Nullable char[] array, int length) {
        return isNotNull(array) && array.length == length;
    }

    public static boolean hasLengthGreaterThan(@Nullable CharSequence str, int lowValue) {
        return isNotNull(str) && str.length() > lowValue;
    }

    public static boolean hasLengthGreaterThan(@Nullable Object[] array, int lowValue) {
        return isNotNull(array) && array.length > lowValue;
    }

    public static boolean hasLengthGreaterThan(@Nullable int[] array, int lowValue) {
        return isNotNull(array) && array.length > lowValue;
    }

    public static boolean hasLengthGreaterThan(@Nullable byte[] array, int lowValue) {
        return isNotNull(array) && array.length > lowValue;
    }

    public static boolean hasLengthGreaterThan(@Nullable short[] array, int lowValue) {
        return isNotNull(array) && array.length > lowValue;
    }

    public static boolean hasLengthGreaterThan(@Nullable long[] array, int lowValue) {
        return isNotNull(array) && array.length > lowValue;
    }

    public static boolean hasLengthGreaterThan(@Nullable float[] array, int lowValue) {
        return isNotNull(array) && array.length > lowValue;
    }

    public static boolean hasLengthGreaterThan(@Nullable double[] array, int lowValue) {
        return isNotNull(array) && array.length > lowValue;
    }

    public static boolean hasLengthGreaterThan(@Nullable boolean[] array, int lowValue) {
        return isNotNull(array) && array.length > lowValue;
    }

    public static boolean hasLengthGreaterThan(@Nullable char[] array, int lowValue) {
        return isNotNull(array) && array.length > lowValue;
    }

    public static boolean hasLengthGreaterOrEqualTo(@Nullable CharSequence str, int minLength) {
        return isNotNull(str) && str.length() >= minLength;
    }

    public static boolean hasLengthGreaterOrEqualTo(@Nullable Object[] array, int minLength) {
        return isNotNull(array) && array.length >= minLength;
    }

    public static boolean hasLengthGreaterOrEqualTo(@Nullable int[] array, int minLength) {
        return isNotNull(array) && array.length >= minLength;
    }

    public static boolean hasLengthGreaterOrEqualTo(@Nullable byte[] array, int minLength) {
        return isNotNull(array) && array.length >= minLength;
    }

    public static boolean hasLengthGreaterOrEqualTo(@Nullable short[] array, int minLength) {
        return isNotNull(array) && array.length >= minLength;
    }

    public static boolean hasLengthGreaterOrEqualTo(@Nullable long[] array, int minLength) {
        return isNotNull(array) && array.length >= minLength;
    }

    public static boolean hasLengthGreaterOrEqualTo(@Nullable float[] array, int minLength) {
        return isNotNull(array) && array.length >= minLength;
    }

    public static boolean hasLengthGreaterOrEqualTo(@Nullable double[] array, int minLength) {
        return isNotNull(array) && array.length >= minLength;
    }

    public static boolean hasLengthGreaterOrEqualTo(@Nullable boolean[] array, int minLength) {
        return isNotNull(array) && array.length >= minLength;
    }

    public static boolean hasLengthGreaterOrEqualTo(@Nullable char[] array, int minLength) {
        return isNotNull(array) && array.length >= minLength;
    }

    public static boolean hasLengthLessThan(@Nullable CharSequence str, int highValue) {
        return isNotNull(str) && str.length() < highValue;
    }

    public static boolean hasLengthLessThan(@Nullable Object[] array, int highValue) {
        return isNotNull(array) && array.length < highValue;
    }

    public static boolean hasLengthLessThan(@Nullable int[] array, int highValue) {
        return isNotNull(array) && array.length < highValue;
    }

    public static boolean hasLengthLessThan(@Nullable byte[] array, int highValue) {
        return isNotNull(array) && array.length < highValue;
    }

    public static boolean hasLengthLessThan(@Nullable short[] array, int highValue) {
        return isNotNull(array) && array.length < highValue;
    }

    public static boolean hasLengthLessThan(@Nullable long[] array, int highValue) {
        return isNotNull(array) && array.length < highValue;
    }

    public static boolean hasLengthLessThan(@Nullable float[] array, int highValue) {
        return isNotNull(array) && array.length < highValue;
    }

    public static boolean hasLengthLessThan(@Nullable double[] array, int highValue) {
        return isNotNull(array) && array.length < highValue;
    }

    public static boolean hasLengthLessThan(@Nullable boolean[] array, int highValue) {
        return isNotNull(array) && array.length < highValue;
    }

    public static boolean hasLengthLessThan(@Nullable char[] array, int highValue) {
        return isNotNull(array) && array.length < highValue;
    }

    public static boolean hasLengthLessOrEqualTo(@Nullable CharSequence str, int maxLength) {
        return isNotNull(str) && str.length() <= maxLength;
    }

    public static boolean hasLengthLessOrEqualTo(@Nullable Object[] array, int maxLength) {
        return isNotNull(array) && array.length <= maxLength;
    }

    public static boolean hasLengthLessOrEqualTo(@Nullable int[] array, int maxLength) {
        return isNotNull(array) && array.length <= maxLength;
    }

    public static boolean hasLengthLessOrEqualTo(@Nullable byte[] array, int maxLength) {
        return isNotNull(array) && array.length <= maxLength;
    }

    public static boolean hasLengthLessOrEqualTo(@Nullable short[] array, int maxLength) {
        return isNotNull(array) && array.length <= maxLength;
    }

    public static boolean hasLengthLessOrEqualTo(@Nullable long[] array, int maxLength) {
        return isNotNull(array) && array.length <= maxLength;
    }

    public static boolean hasLengthLessOrEqualTo(@Nullable float[] array, int maxLength) {
        return isNotNull(array) && array.length <= maxLength;
    }

    public static boolean hasLengthLessOrEqualTo(@Nullable double[] array, int maxLength) {
        return isNotNull(array) && array.length <= maxLength;
    }

    public static boolean hasLengthLessOrEqualTo(@Nullable boolean[] array, int maxLength) {
        return isNotNull(array) && array.length <= maxLength;
    }

    public static boolean hasLengthLessOrEqualTo(@Nullable char[] array, int maxLength) {
        return isNotNull(array) && array.length <= maxLength;
    }

    public static boolean hasLengthBetween(@Nullable CharSequence str, int minLength, int maxLength) {
        return isNotNull(str) && str.length() >= minLength && str.length() <= maxLength;
    }

    public static boolean hasLengthBetween(@Nullable Object[] array, int minLength, int maxLength) {
        return isNotNull(array) && array.length >= minLength && array.length <= maxLength;
    }

    public static boolean hasLengthBetween(@Nullable int[] array, int minLength, int maxLength) {
        return isNotNull(array) && array.length >= minLength && array.length <= maxLength;
    }

    public static boolean hasLengthBetween(@Nullable byte[] array, int minLength, int maxLength) {
        return isNotNull(array) && array.length >= minLength && array.length <= maxLength;
    }

    public static boolean hasLengthBetween(@Nullable short[] array, int minLength, int maxLength) {
        return isNotNull(array) && array.length >= minLength && array.length <= maxLength;
    }

    public static boolean hasLengthBetween(@Nullable long[] array, int minLength, int maxLength) {
        return isNotNull(array) && array.length >= minLength && array.length <= maxLength;
    }

    public static boolean hasLengthBetween(@Nullable float[] array, int minLength, int maxLength) {
        return isNotNull(array) && array.length >= minLength && array.length <= maxLength;
    }

    public static boolean hasLengthBetween(@Nullable double[] array, int minLength, int maxLength) {
        return isNotNull(array) && array.length >= minLength && array.length <= maxLength;
    }

    public static boolean hasLengthBetween(@Nullable boolean[] array, int minLength, int maxLength) {
        return isNotNull(array) && array.length >= minLength && array.length <= maxLength;
    }

    public static boolean hasLengthBetween(@Nullable char[] array, int minLength, int maxLength) {
        return isNotNull(array) && array.length >= minLength && array.length <= maxLength;
    }

    public static boolean hasSize(@Nullable Collection<?> collection, int size) {
        return isNotNull(collection) && collection.size() == size;
    }

    public static boolean hasSize(@Nullable Iterable<?> iterable, int size) {
        return hasAtLeastSize(iterable, size)
            .map(iter -> !iter.hasNext()) // Changes "at least length" to "exact length"
            .orElse(false);
    }

    public static boolean hasSize(@Nullable Map<?, ?> map, int size) {
        return isNotNull(map) && map.size() == size;
    }

    public static boolean hasSizeGreaterThan(@Nullable Collection<?> collection, int minSize) {
        return isNotNull(collection) && collection.size() > minSize;
    }

    public static boolean hasSizeGreaterThan(@Nullable Iterable<?> iterable, int lowValue) {
        return hasAtLeastSize(iterable, lowValue + 1).isPresent();
    }

    public static boolean hasSizeGreaterThan(@Nullable Map<?, ?> map, int minSize) {
        return isNotNull(map) && map.size() > minSize;
    }

    public static boolean hasSizeGreaterOrEqualTo(@Nullable Collection<?> collection, int minSize) {
        return isNotNull(collection) && collection.size() >= minSize;
    }

    public static boolean hasSizeGreaterOrEqualTo(@Nullable Iterable<?> iterable, int minSize) {
        return hasAtLeastSize(iterable, minSize).isPresent();
    }

    public static boolean hasSizeGreaterOrEqualTo(@Nullable Map<?, ?> map, int minSize) {
        return isNotNull(map) && map.size() >= minSize;
    }

    public static boolean hasSizeLessThan(@Nullable Collection<?> collection, int highValue) {
        return isNotNull(collection) && collection.size() < highValue;
    }

    public static boolean hasSizeLessThan(@Nullable Iterable<?> iterable, int highValue) {

        if (iterable == null) {
            return false;
        }

        return hasAtLeastSize(iterable, highValue - 1)
            .map(iter -> !iter.hasNext())  // if the iter has next, it has too high a value
            .orElse(true);                 // if it's empty that means it's nice and short
    }

    public static boolean hasSizeLessThan(@Nullable Map<?, ?> map, int highValue) {
        return isNotNull(map) && map.size() < highValue;
    }

    public static boolean hasSizeLessOrEqualTo(@Nullable Collection<?> collection, int maxSize) {
        return isNotNull(collection) && collection.size() <= maxSize;
    }

    public static boolean hasSizeLessOrEqualTo(@Nullable Iterable<?> iterable, int maxSize) {

        if (iterable == null) {
            return false;
        }

        return hasAtLeastSize(iterable, maxSize)
            .map(iter -> !iter.hasNext())  // if the iter has next, it has too high a value
            .orElse(true);                // if it's empty that means it's nice and short
    }

    public static boolean hasSizeLessOrEqualTo(@Nullable Map<?, ?> map, int maxSize) {
        return isNotNull(map) && map.size() <= maxSize;
    }

    public static boolean hasSizeBetween(@Nullable Collection<?> collection, int minSize, int maxSize) {
        return isNotNull(collection) && collection.size() >= minSize && collection.size() <= maxSize;
    }

    public static boolean hasSizeBetween(@Nullable Iterable<?> iterable, int minSize, int maxSize) {

        if (iterable == null) {
            return false;
        }

        Optional<? extends Iterator<?>> optIterator = hasAtLeastSize(iterable, minSize);
        if (!optIterator.isPresent()) {
            // Too short.
            return false;
        }

        Iterator<?> iterator = optIterator.get();

        for (int length = minSize + 1; length <= maxSize; length++) {

            if (!iterator.hasNext()) {
                // Greater than min but less than max, that's a match.
                return true;
            }

            iterator.next();
        }

        return !iterator.hasNext();
    }

    public static boolean hasSizeBetween(@Nullable Map<?, ?> map, int minSize, int maxSize) {
        return isNotNull(map) && map.size() >= minSize && map.size() <= maxSize;
    }

    public static boolean pathExists(@Nullable Path path) {
        return isNotNull(path) && Files.exists(path);
    }

    public static boolean pathExists(@Nullable File path) {
        return isNotNull(path) && path.exists();
    }

    public static boolean pathExists(@Nullable String path) {
        return RequireUtils
            .pathGetOptional(path)
            .map(CheckBoolean::pathExists)
            .orElse(false);
    }

    /**
     * Since nulls cannot be checked and are considered an invalid state,
     * this method returns false if the path is null.
     */
    public static boolean pathNotExists(@Nullable Path path) {
        // Remember that !Files.exists(path) is not the same as Files.notExists(path).
        return isNotNull(path) && Files.notExists(path);
    }

    /**
     * Since nulls cannot be checked and are considered an invalid state,
     * this method returns false if the path is null.
     */
    public static boolean pathNotExists(@Nullable File path) {
        return isNotNull(path) && !path.exists();
    }

    /**
     * Since nulls cannot be checked and are considered an invalid state,
     * this method returns false if the path is null.
     */
    public static boolean pathNotExists(@Nullable String path) {

        return RequireUtils
            .pathGetOptional(path)
            .map(CheckBoolean::pathNotExists)
            .orElse(false);
    }

    public static boolean isRegularFile(@Nullable Path file) {
        return isNotNull(file) && Files.isRegularFile(file);
    }

    public static boolean isRegularFile(@Nullable File file) {
        return isNotNull(file) && file.isFile();
    }

    public static boolean isRegularFile(@Nullable String filePath) {
        return RequireUtils
            .pathGetOptional(filePath)
            .map(CheckBoolean::isRegularFile)
            .orElse(false);
    }

    public static boolean isDirectory(@Nullable Path directory) {
        return isNotNull(directory) && Files.isDirectory(directory);
    }

    public static boolean isDirectory(@Nullable File directory) {
        return isNotNull(directory) && directory.isDirectory();
    }

    public static boolean isDirectory(@Nullable String directoryPath) {
        return RequireUtils
            .pathGetOptional(directoryPath)
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

    public static boolean isNowOrFuture(@Nullable ZonedDateTime dateTime) {

        if (isNull(dateTime)) {
            return false;
        }

        ZonedDateTime now = ZonedDateTime.now();
        return dateTime.equals(now) || dateTime.isAfter(now);
    }

    public static boolean isNowOrFuture(@Nullable OffsetDateTime dateTime) {

        if (isNull(dateTime)) {
            return false;
        }

        OffsetDateTime now = OffsetDateTime.now();
        return dateTime.equals(now) || dateTime.isAfter(now);
    }

    public static boolean isNowOrFuture(@Nullable LocalDateTime dateTime) {

        if (isNull(dateTime)) {
            return false;
        }

        LocalDateTime now = LocalDateTime.now();
        return dateTime.equals(now) || dateTime.isAfter(now);
    }

    public static boolean isNowOrFuture(@Nullable LocalDate date) {

        if (isNull(date)) {
            return false;
        }

        LocalDate now = LocalDate.now();
        return date.equals(now) || date.isAfter(now);
    }

    public static boolean isNowOrFuture(@Nullable LocalTime time) {

        if (isNull(time)) {
            return false;
        }

        LocalTime now = LocalTime.now();
        return time.equals(now) || time.isAfter(now);
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

    public static boolean isNowOrPast(@Nullable ZonedDateTime dateTime) {

        if (isNull(dateTime)) {
            return false;
        }

        ZonedDateTime now = ZonedDateTime.now();
        return dateTime.equals(now) || dateTime.isBefore(now);
    }

    public static boolean isNowOrPast(@Nullable OffsetDateTime dateTime) {

        if (isNull(dateTime)) {
            return false;
        }

        OffsetDateTime now = OffsetDateTime.now();
        return dateTime.equals(now) || dateTime.isBefore(now);
    }

    public static boolean isNowOrPast(@Nullable LocalDateTime dateTime) {

        if (isNull(dateTime)) {
            return false;
        }

        LocalDateTime now = LocalDateTime.now();
        return dateTime.equals(now) || dateTime.isBefore(now);
    }

    public static boolean isNowOrPast(@Nullable LocalDate date) {

        if (isNull(date)) {
            return false;
        }

        LocalDate now = LocalDate.now();
        return date.equals(now) || date.isBefore(now);
    }

    public static boolean isNowOrPast(@Nullable LocalTime time) {

        if (isNull(time)) {
            return false;
        }

        LocalTime now = LocalTime.now();
        return time.equals(now) || time.isBefore(now);
    }

    public static boolean isInstanceOf(@Nullable Object object, Class<?> classType) {
        return isNotNull(object) && classType.isInstance(object);
    }

    public static boolean isNotInstanceOf(@Nullable Object object, Class<?> classType) {
        return isNotNull(object) && !classType.isInstance(object);
    }
}
