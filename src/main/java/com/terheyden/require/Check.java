package com.terheyden.require;

import javax.annotation.Nullable;
import javax.xml.parsers.DocumentBuilder;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.stream.IntStream;
import java.util.stream.StreamSupport;

import org.jetbrains.annotations.Contract;

import static com.terheyden.require.RequireUtils.hasAtLeastSize;

/**
 * Methods in this class will never throw an exception, only return true or false.
 * <p>
 * "Positive" (qualifying) methods, like {@link #isEmail(String)} will return false if
 * the input is null. "Negative" (disqualifying) methods, like {@link #isNotEmail(String)}
 * will return true if the input is null.
 */
@SuppressWarnings({ "SizeReplaceableByIsEmpty", "BooleanMethodNameMustStartWithQuestion" })
public final class Check {

    /**
     * We use a very lenient email regex. We're not trying to be fully RFC 822 compliant,
     * we just want to validate it looks like a typical email address, looking out for
     * accidental typos like double @, double ., etc.
     *
     * @see <a href="https://stackoverflow.com/questions/201323/how-can-i-validate-an-email-address-using-a-regular-expression" target="_top">Stack Overflow</a>
     */
    private static final String EMAIL_REGEX = "^[^\\s@]+@([^\\s@.,]+\\.)+[^\\s@.,]{2,}$";
    private static final String URL_REGEX = "^https?://.+$";
    private static final String HOSTNAME_REGEX = "^[a-z0-9.-]+$";
    private static final String HOST_PORT_REGEX = "^[a-z0-9.-]+:\\d+$";
    // https://stackoverflow.com/questions/5284147/validating-ipv4-addresses-with-regexp
    private static final String IP4_REGEX = "^((25[0-5]|(2[0-4]|1\\d|[1-9]|)\\d)\\.?\\b){4}$";
    // https://stackoverflow.com/questions/53497/regular-expression-that-matches-valid-ipv6-addresses
    private static final String IP6_REGEX = "^([a-f0-9:]+:+)+[a-f0-9]+$";
    private static final String UUID_REGEX = "^[a-f0-9]{8}-[a-f0-9]{4}-[a-f0-9]{4}-[a-f0-9]{4}-[a-f0-9]{12}$";
    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX, Pattern.CASE_INSENSITIVE);
    private static final Pattern URL_PATTERN = Pattern.compile(URL_REGEX, Pattern.CASE_INSENSITIVE);
    private static final Pattern HOSTNAME_PATTERN = Pattern.compile(HOSTNAME_REGEX, Pattern.CASE_INSENSITIVE);
    private static final Pattern HOST_PORT_PATTERN = Pattern.compile(HOST_PORT_REGEX, Pattern.CASE_INSENSITIVE);
    private static final Pattern IP4_ADDRESS_PATTERN = Pattern.compile(IP4_REGEX);
    private static final Pattern IP6_ADDRESS_PATTERN = Pattern.compile(IP6_REGEX);
    private static final Pattern UUID_PATTERN = Pattern.compile(UUID_REGEX, Pattern.CASE_INSENSITIVE);

    private Check() {
        // Private constructor since this shouldn't be instantiated.
    }

    /**
     * Checks that the given condition is true. Example:
     * <pre>
     * {@code
     *     isTrue("cat".length() > 0); // true
     * }
     * </pre>
     */
    public static boolean isTrue(boolean condition) {
        return condition;
    }

    public static boolean isFalse(boolean condition) {
        return !condition;
    }

    /**
     * Returns true if the given object is null. Examples:
     * <pre>
     * {@code
     *     isNull("");   // false
     *     isNull(null); // true
     * }
     * </pre>
     */
    public static boolean isNull(@Nullable Object obj) {
        return obj == null;
    }

    /**
     * True if the given object is not null. Examples:
     * <pre>
     * {@code
     *     notNull("");   // true
     *     notNull(null); // false
     * }
     * </pre>
     */
    public static boolean notNull(@Nullable Object obj) {
        return obj != null;
    }

    /**
     * True if the given string is null or empty (has length of 0). Examples:
     * <pre>
     * {@code
     *     isEmpty("");    // true
     *     isEmpty("  ");  // false
     *     isEmpty(null);  // true
     * }
     * </pre>
     */
    @Contract("null -> true")
    public static boolean isEmpty(@Nullable CharSequence str) {
        return isNull(str) || str.length() == 0;
    }

    public static boolean isEmpty(@Nullable Collection<?> collection) {
        return isNull(collection) || collection.isEmpty();
    }

    @Contract("null -> true")
    public static boolean isEmpty(@Nullable Iterable<?> iterable) {
        return isNull(iterable) || !iterable.iterator().hasNext();
    }

    @Contract("null -> true")
    public static boolean isEmpty(@Nullable Map<?, ?> map) {
        return isNull(map) || map.isEmpty();
    }

    @Contract("null -> true")
    public static boolean isEmpty(@Nullable Object[] array) {
        return isNull(array) || array.length == 0;
    }

    /**
     * True if the given string is not null and not empty (zero length). For example:
     * <pre>
     * {@code
     *     notEmpty("cat"); // true
     *     notEmpty("");    // false
     *     notEmpty(null);  // false
     * }
     * </pre>
     */
    public static boolean notEmpty(@Nullable CharSequence str) {
        return notNull(str) && str.length() > 0;
    }

    /**
     * True if the collection is not null and not empty (zero length). For example:
     * <pre>
     * {@code
     *     notEmpty(List.of("cat")); // true
     *     notEmpty(List.of());      // false
     *     notEmpty(null);           // false
     * }
     * </pre>
     *
     * @param collection the collection to check
     * @return the collection, if it's not null, for chaining
     */
    public static boolean notEmpty(@Nullable Collection<?> collection) {
        return notNull(collection) && !collection.isEmpty();
    }

    public static boolean notEmpty(@Nullable Iterable<?> iterable) {
        return notNull(iterable) && iterable.iterator().hasNext();
    }

    public static boolean notEmpty(@Nullable Map<?, ?> map) {
        return notNull(map) && !map.isEmpty();
    }

    public static boolean notEmpty(@Nullable Object[] array) {
        return notNull(array) && array.length > 0;
    }

    /**
     * True if the string is null or blank (empty or containing only whitespace). Examples:
     * <pre>
     * {@code
     *     isBlank(null); // true
     *     isBlank("");   // true
     *     isBlank("  "); // true
     *     isBlank("hi"); // false
     * }
     * </pre>
     */
    @Contract("null -> true")
    public static boolean isBlank(@Nullable CharSequence str) {
        return isEmpty(str) || str.toString().trim().length() == 0;
    }

    /**
     * Check that the given string is not null, not empty, and doesn't contain only whitespace. Examples:
     * <pre>
     * {@code
     *     notBlank(null); // false
     *     notBlank("");   // false
     *     notBlank("  "); // false
     *     notBlank("hi"); // true
     * }
     * </pre>
     */
    public static boolean notBlank(@Nullable CharSequence str) {
        return !isBlank(str);
    }

    @Contract("null, _ -> false")
    public static boolean isLength(@Nullable CharSequence str, int length) {
        return notNull(str) && str.length() == length;
    }

    @Contract("null, _ -> false")
    public static boolean isLength(@Nullable Object[] array, int length) {
        return notNull(array) && array.length == length;
    }

    @Contract("null, _ -> true")
    public static boolean notLength(@Nullable CharSequence str, int length) {
        return !isLength(str, length);
    }

    @Contract("null, _ -> true")
    public static boolean notLength(@Nullable Object[] array, int length) {
        return !isLength(array, length);
    }

    @Contract("null, _ -> false")
    public static boolean isLengthGreaterThan(@Nullable CharSequence str, int minLength) {
        return notNull(str) && str.length() > minLength;
    }

    public static boolean isLengthGreaterThan(@Nullable Object[] array, int minLength) {
        return notNull(array) && array.length > minLength;
    }

    public static boolean isLengthGreaterOrEqualTo(@Nullable CharSequence str, int minLength) {
        return notNull(str) && str.length() >= minLength;
    }

    public static boolean isLengthGreaterOrEqualTo(@Nullable Object[] array, int minLength) {
        return notNull(array) && array.length >= minLength;
    }

    public static boolean isLengthLessThan(@Nullable CharSequence str, int highValue) {
        return notNull(str) && str.length() < highValue;
    }

    public static boolean isLengthLessThan(@Nullable Object[] array, int highValue) {
        return notNull(array) && array.length < highValue;
    }

    public static boolean isLengthLessOrEqualTo(@Nullable CharSequence str, int maxLength) {
        return notNull(str) && str.length() <= maxLength;
    }

    public static boolean isLengthLessOrEqualTo(@Nullable Object[] array, int maxLength) {
        return notNull(array) && array.length <= maxLength;
    }

    public static boolean isSize(@Nullable Collection<?> collection, int size) {
        return notNull(collection) && collection.size() == size;
    }

    public static boolean isSize(@Nullable Iterable<?> iterable, int size) {
        return notNull(iterable) && hasAtLeastSize(iterable, size)
            .map(iter -> !iter.hasNext()) // Changes "at least length" to "exact length"
            .orElse(false);
    }

    public static boolean isSize(@Nullable Map<?, ?> map, int size) {
        return notNull(map) && map.size() == size;
    }

    public static boolean isSizeGreaterThan(@Nullable Collection<?> collection, int minSize) {
        return notNull(collection) && collection.size() > minSize;
    }

    public static boolean isSizeGreaterThan(@Nullable Iterable<?> iterable, int minSize) {
        return notNull(iterable) && hasAtLeastSize(iterable, minSize + 1).isPresent();
    }

    public static boolean isSizeGreaterThan(@Nullable Map<?, ?> map, int minSize) {
        return notNull(map) && map.size() > minSize;
    }

    public static boolean isSizeGreaterOrEqualTo(@Nullable Collection<?> collection, int minSize) {
        return notNull(collection) && collection.size() >= minSize;
    }

    public static boolean isSizeGreaterOrEqualTo(@Nullable Iterable<?> iterable, int minSize) {
        return notNull(iterable) && hasAtLeastSize(iterable, minSize).isPresent();
    }

    public static boolean isSizeGreaterOrEqualTo(@Nullable Map<?, ?> map, int minSize) {
        return notNull(map) && map.size() >= minSize;
    }

    public static boolean isSizeLessThan(@Nullable Collection<?> collection, int highValue) {
        return notNull(collection) && collection.size() < highValue;
    }

    public static boolean isSizeLessThan(@Nullable Iterable<?> iterable, int highValue) {
        return notNull(iterable) && hasAtLeastSize(iterable, highValue - 1)
            .map(iter -> !iter.hasNext())  // if the iter has next, it has too high a value
            .orElse(true);                 // if it's empty that means it's nice and short
    }

    public static boolean isSizeLessThan(@Nullable Map<?, ?> map, int highValue) {
        return notNull(map) && map.size() < highValue;
    }

    public static boolean isSizeLessOrEqualTo(@Nullable Collection<?> collection, int maxSize) {
        return notNull(collection) && collection.size() <= maxSize;
    }

    public static boolean isSizeLessOrEqualTo(@Nullable Iterable<?> iterable, int maxSize) {
        return notNull(iterable) && hasAtLeastSize(iterable, maxSize)
            .map(iter -> !iter.hasNext())  // if the iter has next, it has too high a value
            .orElse(true);                // if it's empty that means it's nice and short
    }

    public static boolean isSizeLessOrEqualTo(@Nullable Map<?, ?> map, int maxSize) {
        return notNull(map) && map.size() <= maxSize;
    }

    /**
     * True if the duration is greater than the specified low limit. For example:
     * <pre>
     * {@code
     *    isDurationGreaterThan(Duration.ofSeconds(10), deliverTime); // "greater than 10 seconds?"
     * }
     * </pre>
     *
     * @return true if the duration is greater than the low limit, false if either duration is null
     */
    @Contract(value = "null, _ -> false; _, null -> false", pure = true)
    public static boolean isDurationGreaterThan(@Nullable Duration durationToCheck, @Nullable Duration minDuration) {
        return notNull(minDuration) && notNull(durationToCheck) && minDuration.compareTo(durationToCheck) < 0;
    }

    /**
     * True if the duration is greater than the specified nanoseconds.
     */
    @Contract(value = "null, _ -> false", pure = true)
    public static boolean isDurationGreaterThanNanos(@Nullable Duration durationToCheck, long minNanos) {
        return notNull(durationToCheck) && durationToCheck.toNanos() > minNanos;
    }

    /**
     * True if the duration is greater than the specified milliseconds.
     */
    @Contract(value = "null, _ -> false", pure = true)
    public static boolean isDurationGreaterThanMillis(@Nullable Duration durationToCheck, long minMs) {
        return notNull(durationToCheck) && durationToCheck.toMillis() > minMs;
    }

    /**
     * True if the duration is greater than the specified seconds.
     */
    @Contract(value = "null, _ -> false", pure = true)
    public static boolean isDurationGreaterThanSecs(@Nullable Duration durationToCheck, long minSecs) {
        return notNull(durationToCheck) && durationToCheck.getSeconds() > minSecs;
    }

    /**
     * True if the duration is greater than the specified minutes.
     */
    @Contract(value = "null, _ -> false", pure = true)
    public static boolean isDurationGreaterThanMins(@Nullable Duration durationToCheck, long minMins) {
        return notNull(durationToCheck) && durationToCheck.toMinutes() > minMins;
    }

    /**
     * True if the duration is greater than the specified hours.
     */
    @Contract(value = "null, _ -> false", pure = true)
    public static boolean isDurationGreaterThanHours(@Nullable Duration durationToCheck, long minHours) {
        return notNull(durationToCheck) && durationToCheck.toHours() > minHours;
    }

    /**
     * True if the duration is greater than the specified days.
     */
    @Contract(value = "null, _ -> false", pure = true)
    public static boolean isDurationGreaterThanDays(@Nullable Duration durationToCheck, long minDays) {
        return notNull(durationToCheck) && durationToCheck.toDays() > minDays;
    }

    @Contract(value = "null, _ -> false", pure = true)
    public static boolean isDurationGreaterOrEqualTo(@Nullable Duration durationToCheck, @Nullable Duration minDuration) {
        return notNull(minDuration) && notNull(durationToCheck) && minDuration.compareTo(durationToCheck) <= 0;
    }

    /**
     * True if the duration is greater than the specified nanoseconds.
     */
    @Contract(value = "null, _ -> false", pure = true)
    public static boolean isDurationGreaterOrEqualToNanos(@Nullable Duration durationToCheck, long minNanos) {
        return notNull(durationToCheck) && durationToCheck.toNanos() >= minNanos;
    }

    /**
     * True if the duration is greater than the specified milliseconds.
     */
    @Contract(value = "null, _ -> false", pure = true)
    public static boolean isDurationGreaterOrEqualToMillis(@Nullable Duration durationToCheck, long minMs) {
        return notNull(durationToCheck) && durationToCheck.toMillis() >= minMs;
    }

    /**
     * True if the duration is greater than the specified seconds.
     */
    @Contract(value = "null, _ -> false", pure = true)
    public static boolean isDurationGreaterOrEqualToSecs(@Nullable Duration durationToCheck, long minSecs) {
        return notNull(durationToCheck) && durationToCheck.getSeconds() >= minSecs;
    }

    /**
     * True if the duration is greater than the specified minutes.
     */
    @Contract(value = "null, _ -> false", pure = true)
    public static boolean isDurationGreaterOrEqualToMins(@Nullable Duration durationToCheck, long minMins) {
        return notNull(durationToCheck) && durationToCheck.toMinutes() >= minMins;
    }

    /**
     * True if the duration is greater than the specified hours.
     */
    @Contract(value = "null, _ -> false", pure = true)
    public static boolean isDurationGreaterOrEqualToHours(@Nullable Duration durationToCheck, long minHours) {
        return notNull(durationToCheck) && durationToCheck.toHours() >= minHours;
    }

    /**
     * True if the duration is greater than the specified days.
     */
    @Contract(value = "null, _ -> false", pure = true)
    public static boolean isDurationGreaterOrEqualToDays(@Nullable Duration durationToCheck, long minDays) {
        return notNull(durationToCheck) && durationToCheck.toDays() >= minDays;
    }

    @Contract(value = "null, _ -> false", pure = true)
    public static boolean isDurationLessThan(@Nullable Duration durationToCheck, @Nullable Duration highDurationLimit) {
        return notNull(highDurationLimit) && notNull(durationToCheck) && highDurationLimit.compareTo(durationToCheck) > 0;
    }

    /**
     * True if the duration is less than the specified nanoseconds.
     */
    @Contract(value = "null, _ -> false", pure = true)
    public static boolean isDurationLessThanNanos(@Nullable Duration durationToCheck, long highValueNs) {
        return notNull(durationToCheck) && durationToCheck.toNanos() < highValueNs;
    }

    /**
     * True if the duration is less than the specified milliseconds.
     */
    @Contract(value = "null, _ -> false", pure = true)
    public static boolean isDurationLessThanMillis(@Nullable Duration durationToCheck, long highValueMs) {
        return notNull(durationToCheck) && durationToCheck.toMillis() < highValueMs;
    }

    /**
     * True if the duration is less than the specified seconds.
     */
    @Contract(value = "null, _ -> false", pure = true)
    public static boolean isDurationLessThanSecs(@Nullable Duration durationToCheck, long highValueSecs) {
        return notNull(durationToCheck) && durationToCheck.getSeconds() < highValueSecs;
    }

    /**
     * True if the duration is less than the specified minutes.
     */
    @Contract(value = "null, _ -> false", pure = true)
    public static boolean isDurationLessThanMins(@Nullable Duration durationToCheck, long highValueMins) {
        return notNull(durationToCheck) && durationToCheck.toMinutes() < highValueMins;
    }

    /**
     * True if the duration is less than the specified hours.
     */
    @Contract(value = "null, _ -> false", pure = true)
    public static boolean isDurationLessThanHours(@Nullable Duration durationToCheck, long highValueHours) {
        return notNull(durationToCheck) && durationToCheck.toHours() < highValueHours;
    }

    /**
     * True if the duration is less than the specified days.
     */
    @Contract(value = "null, _ -> false", pure = true)
    public static boolean isDurationLessThanDays(@Nullable Duration durationToCheck, long highValueDays) {
        return notNull(durationToCheck) && durationToCheck.toDays() < highValueDays;
    }

    @Contract(value = "null, _ -> false", pure = true)
    public static boolean isDurationLessOrEqualTo(@Nullable Duration durationToCheck, @Nullable Duration maxDuration) {
        return notNull(maxDuration) && notNull(durationToCheck) && maxDuration.compareTo(durationToCheck) >= 0;
    }

    /**
     * True if the duration is less than the specified nanoseconds.
     */
    @Contract(value = "null, _ -> false", pure = true)
    public static boolean isDurationLessOrEqualToNanos(@Nullable Duration durationToCheck, long maxNanos) {
        return notNull(durationToCheck) && durationToCheck.toNanos() <= maxNanos;
    }

    /**
     * True if the duration is less than the specified milliseconds.
     */
    @Contract(value = "null, _ -> false", pure = true)
    public static boolean isDurationLessOrEqualToMillis(@Nullable Duration durationToCheck, long maxMillis) {
        return notNull(durationToCheck) && durationToCheck.toMillis() <= maxMillis;
    }

    /**
     * True if the duration is less than the specified seconds.
     */
    @Contract(value = "null, _ -> false", pure = true)
    public static boolean isDurationLessOrEqualToSecs(@Nullable Duration durationToCheck, long maxSecs) {
        return notNull(durationToCheck) && durationToCheck.getSeconds() <= maxSecs;
    }

    /**
     * True if the duration is less than the specified minutes.
     */
    @Contract(value = "null, _ -> false", pure = true)
    public static boolean isDurationLessOrEqualToMins(@Nullable Duration durationToCheck, long maxMins) {
        return notNull(durationToCheck) && durationToCheck.toMinutes() <= maxMins;
    }

    /**
     * True if the duration is less than the specified hours.
     */
    @Contract(value = "null, _ -> false", pure = true)
    public static boolean isDurationLessOrEqualToHours(@Nullable Duration durationToCheck, long maxHours) {
        return notNull(durationToCheck) && durationToCheck.toHours() <= maxHours;
    }

    /**
     * True if the duration is less than the specified days.
     */
    @Contract(value = "null, _ -> false", pure = true)
    public static boolean isDurationLessOrEqualToDays(@Nullable Duration durationToCheck, long maxDays) {
        return notNull(durationToCheck) && durationToCheck.toDays() <= maxDays;
    }

    @Contract(value = "null -> false", pure = true)
    public static boolean pathExists(@Nullable Path path) {
        return notNull(path) && Files.exists(path);
    }

    @Contract(value = "null -> false", pure = true)
    public static boolean pathExists(@Nullable File path) {
        return notNull(path) && path.exists();
    }

    @Contract(value = "null -> false", pure = true)
    public static boolean pathExists(@Nullable String path) {
        return RequireUtils
            .pathGetOptional(path)
            .map(Check::pathExists)
            .orElse(false);
    }

    /**
     * It's expected that this method will be used as a guard clause.
     * Therefore this method returns true if the path is null.
     */
    @Contract(value = "null -> true", pure = true)
    public static boolean notExists(@Nullable Path path) {
        // Remember that !Files.exists(path) is not the same as Files.notExists(path).
        return isNull(path) || Files.notExists(path);
    }

    @Contract(value = "null -> true", pure = true)
    public static boolean notExists(@Nullable File path) {
        return !pathExists(path);
    }

    @Contract(value = "null -> true", pure = true)
    public static boolean notExists(@Nullable String path) {

        return RequireUtils
            .pathGetOptional(path)
            .map(Check::notExists)
            .orElse(true);
    }

    @Contract(value = "null -> false", pure = true)
    public static boolean isRegularFile(@Nullable Path file) {
        return notNull(file) && Files.isRegularFile(file);
    }

    @Contract(value = "null -> false", pure = true)
    public static boolean isRegularFile(@Nullable File file) {
        return notNull(file) && file.isFile();
    }

    @Contract(value = "null -> false", pure = true)
    public static boolean isRegularFile(@Nullable String filePath) {
        return RequireUtils
            .pathGetOptional(filePath)
            .map(Check::isRegularFile)
            .orElse(false);
    }

    @Contract(value = "null -> true", pure = true)
    public static boolean notRegularFile(@Nullable Path file) {
        return !isRegularFile(file);
    }

    @Contract(value = "null -> true", pure = true)
    public static boolean notRegularFile(@Nullable File file) {
        return !isRegularFile(file);
    }

    @Contract(value = "null -> true", pure = true)
    public static boolean notRegularFile(@Nullable String filePath) {
        return !isRegularFile(filePath);
    }

    @Contract(value = "null -> false", pure = true)
    public static boolean isDirectory(@Nullable Path directory) {
        return notNull(directory) && Files.isDirectory(directory);
    }

    @Contract(value = "null -> false", pure = true)
    public static boolean isDirectory(@Nullable File directory) {
        return notNull(directory) && directory.isDirectory();
    }

    @Contract(value = "null -> false", pure = true)
    public static boolean isDirectory(@Nullable String directoryPath) {
        return RequireUtils
            .pathGetOptional(directoryPath)
            .map(Check::isDirectory)
            .orElse(false);
    }

    @Contract(value = "null -> true", pure = true)
    public static boolean notDirectory(@Nullable Path directory) {
        return !isDirectory(directory);
    }

    @Contract(value = "null -> true", pure = true)
    public static boolean notDirectory(@Nullable File directory) {
        return !isDirectory(directory);
    }

    @Contract(value = "null -> true", pure = true)
    public static boolean notDirectory(@Nullable String directoryPath) {
        return !isDirectory(directoryPath);
    }

    @Contract(value = "null -> false", pure = true)
    public static boolean isFuture(@Nullable ZonedDateTime dateTime) {
        return notNull(dateTime) && dateTime.isAfter(ZonedDateTime.now());
    }

    @Contract(value = "null -> false", pure = true)
    public static boolean isFuture(@Nullable OffsetDateTime dateTime) {
        return notNull(dateTime) && dateTime.isAfter(OffsetDateTime.now());
    }

    @Contract(value = "null -> false", pure = true)
    public static boolean isFuture(@Nullable LocalDateTime dateTime) {
        return notNull(dateTime) && dateTime.isAfter(LocalDateTime.now());
    }

    @Contract(value = "null -> false", pure = true)
    public static boolean isFuture(@Nullable LocalDate date) {
        return notNull(date) && date.isAfter(LocalDate.now());
    }

    @Contract(value = "null -> false", pure = true)
    public static boolean isFuture(@Nullable LocalTime time) {
        return notNull(time) && time.isAfter(LocalTime.now());
    }

    @Contract(value = "null -> false", pure = true)
    public static boolean isPast(@Nullable ZonedDateTime dateTime) {
        return notNull(dateTime) && dateTime.isBefore(ZonedDateTime.now());
    }

    @Contract(value = "null -> false", pure = true)
    public static boolean isPast(@Nullable OffsetDateTime dateTime) {
        return notNull(dateTime) && dateTime.isBefore(OffsetDateTime.now());
    }

    @Contract(value = "null -> false", pure = true)
    public static boolean isPast(@Nullable LocalDateTime dateTime) {
        return notNull(dateTime) && dateTime.isBefore(LocalDateTime.now());
    }

    @Contract(value = "null -> false", pure = true)
    public static boolean isPast(@Nullable LocalDate date) {
        return notNull(date) && date.isBefore(LocalDate.now());
    }

    @Contract(value = "null -> false", pure = true)
    public static boolean isPast(@Nullable LocalTime time) {
        return notNull(time) && time.isBefore(LocalTime.now());
    }

    @Contract(value = "null, _ -> false; _, null -> false", pure = true)
    public static boolean isInstanceOf(@Nullable Object object, @Nullable Class<?> classType) {
        return notNull(classType) && notNull(object) && classType.isInstance(object);
    }

    @Contract(value = "null, _ -> true; _, null -> true", pure = true)
    public static boolean notInstanceOf(@Nullable Object object, @Nullable Class<?> classType) {
        return !isInstanceOf(object, classType);
    }

    @Contract(value = "null, _ -> false; _, null -> false", pure = true)
    public static boolean contains(@Nullable CharSequence stringToCheck, @Nullable CharSequence contains) {
        return notNull(contains) && notNull(stringToCheck) && stringToCheck.toString().contains(contains);
    }

    @Contract(value = "null, _ -> false; _, null -> false", pure = true)
    public static <T> boolean contains(@Nullable Collection<T> collectionToCheck, @Nullable T contains) {
        return notNull(contains) && notNull(collectionToCheck) && collectionToCheck.contains(contains);
    }

    @Contract(value = "null, _ -> false; _, null -> false", pure = true)
    public static <T> boolean contains(@Nullable T[] arrayToCheck, @Nullable T contains) {
        return notNull(contains) && notNull(arrayToCheck) && Arrays.asList(arrayToCheck).contains(contains);
    }

    @Contract(value = "null, _ -> false; _, null -> false", pure = true)
    public static <T> boolean contains(@Nullable Iterable<T> iterableToCheck, @Nullable T contains) {
        return notNull(contains) && notNull(iterableToCheck) && StreamSupport
            .stream(iterableToCheck.spliterator(), false).anyMatch(contains::equals);
    }

    @Contract(value = "null, _ -> true; _, null -> true", pure = true)
    public static boolean notContains(@Nullable CharSequence stringToCheck, @Nullable CharSequence contains) {
        return !contains(stringToCheck, contains);
    }

    @Contract(value = "null, _ -> true; _, null -> true", pure = true)
    public static <T> boolean notContains(@Nullable Collection<T> collectionToCheck, @Nullable T contains) {
        return !contains(collectionToCheck, contains);
    }

    @Contract(value = "null, _ -> true; _, null -> true", pure = true)
    public static <T> boolean notContains(@Nullable T[] arrayToCheck, @Nullable T contains) {
        return !contains(arrayToCheck, contains);
    }

    @Contract(value = "null, _ -> true; _, null -> true", pure = true)
    public static <T> boolean notContains(@Nullable Iterable<T> iterableToCheck, @Nullable T contains) {
        return !contains(iterableToCheck, contains);
    }

    /**
     * True if the collection contains at least one element that matches the predicate.
     * Example:
     * <pre>
     * {@code
     * containsElement(u -> u.getAccessLevel() == AccessLevel.ADMIN, users);
     * }
     * </pre>
     */
    public static <T> boolean containsElement(
        @Nullable Predicate<? super T> predicate,
        @Nullable Collection<T> collectionToCheck) {

        return notNull(predicate) && notNull(collectionToCheck) && collectionToCheck.stream().anyMatch(predicate);
    }

    public static <T> boolean containsElement(@Nullable T[] arrayToCheck, @Nullable Predicate<? super T> predicate) {
        return notNull(predicate) && notNull(arrayToCheck) && Arrays.stream(arrayToCheck).anyMatch(predicate);
    }

    public static <T> boolean containsElement(
        @Nullable Predicate<? super T> predicate,
        @Nullable Iterable<T> iterableToCheck) {

        return notNull(predicate)
            && notNull(iterableToCheck)
            && StreamSupport.stream(iterableToCheck.spliterator(), false).anyMatch(predicate);
    }

    public static <T> boolean notContainsElement(
        @Nullable Predicate<? super T> predicate,
        @Nullable Collection<T> collectionToCheck) {

        return !containsElement(predicate, collectionToCheck);
    }

    public static <T> boolean notContainsElement(@Nullable T[] arrayToCheck, @Nullable Predicate<? super T> predicate) {
        return !containsElement(arrayToCheck, predicate);
    }

    public static <T> boolean notContainsElement(
        @Nullable Predicate<? super T> predicate,
        @Nullable Iterable<T> iterableToCheck) {

        return !containsElement(predicate, iterableToCheck);
    }

    public static boolean containsRegex(@Nullable CharSequence stringToCheck, @Nullable Pattern regex) {
        return notNull(regex) && notNull(stringToCheck) && regex.matcher(stringToCheck).find();
    }

    public static boolean containsRegex(@Nullable CharSequence stringToCheck, @Nullable String regex) {
        return notNull(regex) && containsRegex(stringToCheck, Pattern.compile(regex));
    }

    public static boolean matchesRegex(@Nullable CharSequence stringToCheck, @Nullable Pattern regex) {
        return notNull(regex) && notNull(stringToCheck) && regex.matcher(stringToCheck).matches();
    }

    public static boolean matchesRegex(@Nullable CharSequence stringToCheck, @Nullable String regex) {
        return notNull(regex) && matchesRegex(stringToCheck, Pattern.compile(regex));
    }

    public static boolean containsKey(@Nullable Map<?, ?> mapToCheck, @Nullable Object key) {
        return notNull(key) && notNull(mapToCheck) && mapToCheck.containsKey(key);
    }

    public static boolean containsValue(@Nullable Map<?, ?> mapToCheck, @Nullable Object value) {
        return notNull(value) && notNull(mapToCheck) && mapToCheck.containsValue(value);
    }

    public static boolean containsUniqueElements(@Nullable Collection<?> collectionToCheck) {
        return notNull(collectionToCheck)
            && collectionToCheck.size() == new HashSet<>(collectionToCheck).size();
    }

    public static boolean containsNull(@Nullable Collection<?> collectionToCheck) {

        if (isNull(collectionToCheck)) {
            return false;
        }

        if (collectionToCheck.isEmpty()) {
            // Empty collections do not contain nulls.
            return false;
        }

        try {
            // contains(null) will throw if the collection doesn't support nulls.
            return collectionToCheck.contains(null);
        } catch (ClassCastException | NullPointerException ignore) {
            // The collection does not support nulls.
            return false;
        }
    }

    public static boolean containsNull(@Nullable Iterable<?> iterableToCheck) {

        if (isNull(iterableToCheck)) {
            return false;
        }

        if (iterableToCheck instanceof Collection) {
            return containsNull((Collection<?>) iterableToCheck);
        }

        for (Object element : iterableToCheck) {
            if (isNull(element)) {
                return true;
            }
        }

        return false;
    }

    public static boolean containsNull(@Nullable Object[] arrayToCheck) {
        if (isNull(arrayToCheck)) return false;
        for (Object element : arrayToCheck) if (isNull(element)) return true;
        return false;
    }

    public static boolean containsNull(@Nullable Map<?, ?> mapToCheck) {
        return notNull(mapToCheck) && (containsNull(mapToCheck.keySet()) || containsNull(mapToCheck.values()));
    }

    /**
     * True if the collection is not null, and does not contain any null elements.
     */
    public static boolean notContainsNull(@Nullable Collection<?> collectionToCheck) {
        return !containsNull(collectionToCheck);
    }

    public static boolean notContainsNull(@Nullable Iterable<?> iterableToCheck) {
        return !containsNull(iterableToCheck);
    }

    /**
     * True if the map does not contain null keys or values.
     * @throws IllegalStateException if the map does not support null keys or values
     */
    public static boolean notContainsNull(@Nullable Map<?, ?> mapToCheck) {
        return isNull(mapToCheck) || notContainsNull(mapToCheck.keySet()) && notContainsNull(mapToCheck.values());
    }

    public static boolean notContainsNull(@Nullable Object[] arrayToCheck) {
        return !containsNull(arrayToCheck);
    }

    /**
     * True if the string contains only numeric digits. Examples:
     * <pre>
     * {@code
     *    numbersOnly(null); // false
     *    numbersOnly("");   // false
     *    numbersOnly("  123"); // false
     *    numbersOnly("hi123"); // false
     *    numbersOnly("123"); // true
     *    numbersOnly("123.0"); // false
     *    numbersOnly("123,0"); // false
     * }
     * </pre>
     */
    @Contract("null -> false")
    public static boolean numbersOnly(@Nullable CharSequence str) {

        if (isNull(str)) {
            return false;
        }

        return IntStream.range(0, str.length()).allMatch(i -> Character.isDigit(str.charAt(i)));
    }

    public static boolean notNumbersOnly(@Nullable CharSequence str) {
        return !numbersOnly(str);
    }

    @Contract("null -> false")
    public static boolean alphasOnly(@Nullable CharSequence str) {

        if (isNull(str)) {
            return false;
        }

        return IntStream.range(0, str.length()).allMatch(i -> Character.isLetter(str.charAt(i)));
    }

    public static boolean notAlphasOnly(@Nullable CharSequence str) {
        return !alphasOnly(str);
    }

    @Contract("null -> false")
    public static boolean alphaNumericOnly(@Nullable CharSequence str) {

        if (isNull(str)) {
            return false;
        }

        return IntStream.range(0, str.length()).allMatch(i -> Character.isLetterOrDigit(str.charAt(i)));
    }

    public static boolean notAlphaNumericOnly(@Nullable CharSequence str) {
        return !alphaNumericOnly(str);
    }

    public static boolean isEmail(@Nullable CharSequence email) {
        return notNull(email) && EMAIL_PATTERN.matcher(email).matches();
    }

    /**
     * True if the value begins with {@code http://} or {@code https://} and has any sort of hostname or IP address.
     */
    public static boolean isUrl(@Nullable CharSequence url) {
        return notNull(url) && URL_PATTERN.matcher(url).matches();
    }

    /**
     * True if this is a valid IPv4 or IPv6 address.
     * If you want to check for a specific version, use {@link #isIPv4Address(CharSequence)} or
     * {@link #isIPv6Address(CharSequence)}.
     */
    public static boolean isIpAddress(@Nullable CharSequence ipAddress) {
        return isIPv4Address(ipAddress) || isIPv6Address(ipAddress);
    }

    public static boolean isIPv4Address(@Nullable CharSequence ip4Address) {
        return notNull(ip4Address) && IP4_ADDRESS_PATTERN.matcher(ip4Address).matches();
    }

    public static boolean isIPv6Address(@Nullable CharSequence ip6Address) {
        return notNull(ip6Address) && IP6_ADDRESS_PATTERN.matcher(ip6Address).matches();
    }

    public static boolean notIPAddress(@Nullable CharSequence ipAddress) {
        return !isIpAddress(ipAddress);
    }

    public static boolean notIPv4Address(@Nullable CharSequence ip4Address) {
        return !isIPv4Address(ip4Address);
    }

    public static boolean notIPv6Address(@Nullable CharSequence ip6Address) {
        return !isIPv6Address(ip6Address);
    }

    /**
     * True if the value is not empty, and looks like JSON.
     * This means starting and ending with curly braces or square brackets.
     */
    public static boolean isJson(@Nullable CharSequence json) {

        if (isEmpty(json)) {
            return false;
        }

        return RequireUtils.isJson(json.toString());
    }

    public static boolean notJson(@Nullable CharSequence json) {
        return !isJson(json);
    }

    /**
     * True if the value is not empty and can be parsed as XML without errors.
     * Uses {@link DocumentBuilder} to parse the XML.
     */
    public static boolean isXml(@Nullable CharSequence xml) {

        if (isEmpty(xml)) {
            return false;
        }

        return RequireUtils.isXml(xml.toString());
    }

    public static boolean notXml(@Nullable CharSequence xml) {
        return !isXml(xml);
    }

    public static boolean isUUID(@Nullable CharSequence uuid) {
        return notNull(uuid) && UUID_PATTERN.matcher(uuid).matches();
    }

    public static boolean notUUID(@Nullable CharSequence uuid) {
        return !isUUID(uuid);
    }
}
