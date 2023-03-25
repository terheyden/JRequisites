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
public final class Checks {

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

    private Checks() {
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

    public static boolean isLength(int length, @Nullable CharSequence str) {
        return notNull(str) && str.length() == length;
    }

    public static boolean isLength(int length, @Nullable Object[] array) {
        return notNull(array) && array.length == length;
    }

    public static boolean notLength(int length, @Nullable CharSequence str) {
        return notNull(str) && str.length() != length;
    }

    public static boolean notLength(int length, @Nullable Object[] array) {
        return notNull(array) && array.length != length;
    }

    public static boolean isLengthGreaterThan(int lowValue, @Nullable CharSequence str) {
        return notNull(str) && str.length() > lowValue;
    }

    public static boolean isLengthGreaterThan(int lowValue, @Nullable Object[] array) {
        return notNull(array) && array.length > lowValue;
    }

    public static boolean isLengthGreaterOrEqualTo(int minLength, @Nullable CharSequence str) {
        return notNull(str) && str.length() >= minLength;
    }

    public static boolean isLengthGreaterOrEqualTo(int minLength, @Nullable Object[] array) {
        return notNull(array) && array.length >= minLength;
    }

    public static boolean isLengthLessThan(int highValue, @Nullable CharSequence str) {
        return notNull(str) && str.length() < highValue;
    }

    public static boolean isLengthLessThan(int highValue, @Nullable Object[] array) {
        return notNull(array) && array.length < highValue;
    }

    public static boolean isLengthLessOrEqualTo(int maxLength, @Nullable CharSequence str) {
        return notNull(str) && str.length() <= maxLength;
    }

    public static boolean isLengthLessOrEqualTo(int maxLength, @Nullable Object[] array) {
        return notNull(array) && array.length <= maxLength;
    }

    public static boolean isSize(int size, @Nullable Collection<?> collection) {
        return notNull(collection) && collection.size() == size;
    }

    public static boolean isSize(int size, @Nullable Iterable<?> iterable) {
        return notNull(iterable) && hasAtLeastSize(iterable, size)
            .map(iter -> !iter.hasNext()) // Changes "at least length" to "exact length"
            .orElse(false);
    }

    public static boolean isSize(int size, @Nullable Map<?, ?> map) {
        return notNull(map) && map.size() == size;
    }

    public static boolean isSizeGreaterThan(int minSize, @Nullable Collection<?> collection) {
        return notNull(collection) && collection.size() > minSize;
    }

    public static boolean isSizeGreaterThan(int lowValue, @Nullable Iterable<?> iterable) {
        return notNull(iterable) && hasAtLeastSize(iterable, lowValue + 1).isPresent();
    }

    public static boolean isSizeGreaterThan(int minSize, @Nullable Map<?, ?> map) {
        return notNull(map) && map.size() > minSize;
    }

    public static boolean isSizeGreaterOrEqualTo(int minSize, @Nullable Collection<?> collection) {
        return notNull(collection) && collection.size() >= minSize;
    }

    public static boolean isSizeGreaterOrEqualTo(int minSize, @Nullable Iterable<?> iterable) {
        return notNull(iterable) && hasAtLeastSize(iterable, minSize).isPresent();
    }

    public static boolean isSizeGreaterOrEqualTo(int minSize, @Nullable Map<?, ?> map) {
        return notNull(map) && map.size() >= minSize;
    }

    public static boolean isSizeLessThan(int highValue, @Nullable Collection<?> collection) {
        return notNull(collection) && collection.size() < highValue;
    }

    public static boolean isSizeLessThan(int highValue, @Nullable Iterable<?> iterable) {
        return notNull(iterable) && hasAtLeastSize(iterable, highValue - 1)
            .map(iter -> !iter.hasNext())  // if the iter has next, it has too high a value
            .orElse(true);                 // if it's empty that means it's nice and short
    }

    public static boolean isSizeLessThan(int highValue, @Nullable Map<?, ?> map) {
        return notNull(map) && map.size() < highValue;
    }

    public static boolean isSizeLessOrEqualTo(int maxSize, @Nullable Collection<?> collection) {
        return notNull(collection) && collection.size() <= maxSize;
    }

    public static boolean isSizeLessOrEqualTo(int maxSize, @Nullable Iterable<?> iterable) {
        return notNull(iterable) && hasAtLeastSize(iterable, maxSize)
            .map(iter -> !iter.hasNext())  // if the iter has next, it has too high a value
            .orElse(true);                // if it's empty that means it's nice and short
    }

    public static boolean isSizeLessOrEqualTo(int maxSize, @Nullable Map<?, ?> map) {
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
    public static boolean isDurationGreaterThan(@Nullable Duration lowDurationLimit, @Nullable Duration durationToCheck) {
        return notNull(lowDurationLimit) && notNull(durationToCheck) && lowDurationLimit.compareTo(durationToCheck) < 0;
    }

    /**
     * True if the duration is greater than the specified nanoseconds.
     */
    public static boolean isDurationGreaterThanNanos(long lowValueNs, @Nullable Duration durationToCheck) {
        return notNull(durationToCheck) && durationToCheck.toNanos() > lowValueNs;
    }

    /**
     * True if the duration is greater than the specified milliseconds.
     */
    public static boolean isDurationGreaterThanMillis(long lowValueMs, @Nullable Duration durationToCheck) {
        return notNull(durationToCheck) && durationToCheck.toMillis() > lowValueMs;
    }

    /**
     * True if the duration is greater than the specified seconds.
     */
    public static boolean isDurationGreaterThanSecs(long lowValueSecs, @Nullable Duration durationToCheck) {
        return notNull(durationToCheck) && durationToCheck.getSeconds() > lowValueSecs;
    }

    /**
     * True if the duration is greater than the specified minutes.
     */
    public static boolean isDurationGreaterThanMins(long lowValueMins, @Nullable Duration durationToCheck) {
        return notNull(durationToCheck) && durationToCheck.toMinutes() > lowValueMins;
    }

    /**
     * True if the duration is greater than the specified hours.
     */
    public static boolean isDurationGreaterThanHours(long lowValueHours, @Nullable Duration durationToCheck) {
        return notNull(durationToCheck) && durationToCheck.toHours() > lowValueHours;
    }

    /**
     * True if the duration is greater than the specified days.
     */
    public static boolean isDurationGreaterThanDays(long lowValueDays, @Nullable Duration durationToCheck) {
        return notNull(durationToCheck) && durationToCheck.toDays() > lowValueDays;
    }

    public static boolean isDurationGreaterOrEqualTo(@Nullable Duration minDuration, @Nullable Duration durationToCheck) {
        return notNull(minDuration) && notNull(durationToCheck) && minDuration.compareTo(durationToCheck) <= 0;
    }

    /**
     * True if the duration is greater than the specified nanoseconds.
     */
    public static boolean isDurationGreaterOrEqualToNanos(long lowValueNs, @Nullable Duration durationToCheck) {
        return notNull(durationToCheck) && durationToCheck.toNanos() >= lowValueNs;
    }

    /**
     * True if the duration is greater than the specified milliseconds.
     */
    public static boolean isDurationGreaterOrEqualToMillis(long lowValueMs, @Nullable Duration durationToCheck) {
        return notNull(durationToCheck) && durationToCheck.toMillis() >= lowValueMs;
    }

    /**
     * True if the duration is greater than the specified seconds.
     */
    public static boolean isDurationGreaterOrEqualToSecs(long lowValueSecs, @Nullable Duration durationToCheck) {
        return notNull(durationToCheck) && durationToCheck.getSeconds() >= lowValueSecs;
    }

    /**
     * True if the duration is greater than the specified minutes.
     */
    public static boolean isDurationGreaterOrEqualToMins(long lowValueMins, @Nullable Duration durationToCheck) {
        return notNull(durationToCheck) && durationToCheck.toMinutes() >= lowValueMins;
    }

    /**
     * True if the duration is greater than the specified hours.
     */
    public static boolean isDurationGreaterOrEqualToHours(long lowValueHours, @Nullable Duration durationToCheck) {
        return notNull(durationToCheck) && durationToCheck.toHours() >= lowValueHours;
    }

    /**
     * True if the duration is greater than the specified days.
     */
    public static boolean isDurationGreaterOrEqualToDays(long lowValueDays, @Nullable Duration durationToCheck) {
        return notNull(durationToCheck) && durationToCheck.toDays() >= lowValueDays;
    }

    public static boolean isDurationLessThan(@Nullable Duration highDurationLimit, @Nullable Duration durationToCheck) {
        return notNull(highDurationLimit) && notNull(durationToCheck) && highDurationLimit.compareTo(durationToCheck) > 0;
    }

    /**
     * True if the duration is less than the specified nanoseconds.
     */
    public static boolean isDurationLessThanNanos(long highValueNs, @Nullable Duration durationToCheck) {
        return notNull(durationToCheck) && durationToCheck.toNanos() < highValueNs;
    }

    /**
     * True if the duration is less than the specified milliseconds.
     */
    public static boolean isDurationLessThanMillis(long highValueMs, @Nullable Duration durationToCheck) {
        return notNull(durationToCheck) && durationToCheck.toMillis() < highValueMs;
    }

    /**
     * True if the duration is less than the specified seconds.
     */
    public static boolean isDurationLessThanSecs(long highValueSecs, @Nullable Duration durationToCheck) {
        return notNull(durationToCheck) && durationToCheck.getSeconds() < highValueSecs;
    }

    /**
     * True if the duration is less than the specified minutes.
     */
    public static boolean isDurationLessThanMins(long highValueMins, @Nullable Duration durationToCheck) {
        return notNull(durationToCheck) && durationToCheck.toMinutes() < highValueMins;
    }

    /**
     * True if the duration is less than the specified hours.
     */
    public static boolean isDurationLessThanHours(long highValueHours, @Nullable Duration durationToCheck) {
        return notNull(durationToCheck) && durationToCheck.toHours() < highValueHours;
    }

    /**
     * True if the duration is less than the specified days.
     */
    public static boolean isDurationLessThanDays(long highValueDays, @Nullable Duration durationToCheck) {
        return notNull(durationToCheck) && durationToCheck.toDays() < highValueDays;
    }

    public static boolean isDurationLessOrEqualTo(@Nullable Duration maxDuration, @Nullable Duration durationToCheck) {
        return notNull(maxDuration) && notNull(durationToCheck) && maxDuration.compareTo(durationToCheck) >= 0;
    }

    /**
     * True if the duration is less than the specified nanoseconds.
     */
    public static boolean isDurationLessOrEqualToNanos(long maxNanos, @Nullable Duration durationToCheck) {
        return notNull(durationToCheck) && durationToCheck.toNanos() <= maxNanos;
    }

    /**
     * True if the duration is less than the specified milliseconds.
     */
    public static boolean isDurationLessOrEqualToMillis(long maxMillis, @Nullable Duration durationToCheck) {
        return notNull(durationToCheck) && durationToCheck.toMillis() <= maxMillis;
    }

    /**
     * True if the duration is less than the specified seconds.
     */
    public static boolean isDurationLessOrEqualToSecs(long maxSecs, @Nullable Duration durationToCheck) {
        return notNull(durationToCheck) && durationToCheck.getSeconds() <= maxSecs;
    }

    /**
     * True if the duration is less than the specified minutes.
     */
    public static boolean isDurationLessOrEqualToMins(long maxMins, @Nullable Duration durationToCheck) {
        return notNull(durationToCheck) && durationToCheck.toMinutes() <= maxMins;
    }

    /**
     * True if the duration is less than the specified hours.
     */
    public static boolean isDurationLessOrEqualToHours(long maxHours, @Nullable Duration durationToCheck) {
        return notNull(durationToCheck) && durationToCheck.toHours() <= maxHours;
    }

    /**
     * True if the duration is less than the specified days.
     */
    public static boolean isDurationLessOrEqualToDays(long maxDays, @Nullable Duration durationToCheck) {
        return notNull(durationToCheck) && durationToCheck.toDays() <= maxDays;
    }

    public static boolean pathExists(@Nullable Path path) {
        return notNull(path) && Files.exists(path);
    }

    public static boolean pathExists(@Nullable File path) {
        return notNull(path) && path.exists();
    }

    public static boolean pathExists(@Nullable String path) {
        return RequireUtils
            .pathGetOptional(path)
            .map(Checks::pathExists)
            .orElse(false);
    }

    /**
     * It's expected that this method will be used as a guard clause.
     * Therefore this method returns true if the path is null.
     */
    public static boolean notExists(@Nullable Path path) {
        // Remember that !Files.exists(path) is not the same as Files.notExists(path).
        return isNull(path) || Files.notExists(path);
    }

    public static boolean notExists(@Nullable File path) {
        return !pathExists(path);
    }

    public static boolean notExists(@Nullable String path) {

        return RequireUtils
            .pathGetOptional(path)
            .map(Checks::notExists)
            .orElse(true);
    }

    public static boolean isRegularFile(@Nullable Path file) {
        return notNull(file) && Files.isRegularFile(file);
    }

    public static boolean isRegularFile(@Nullable File file) {
        return notNull(file) && file.isFile();
    }

    public static boolean isRegularFile(@Nullable String filePath) {
        return RequireUtils
            .pathGetOptional(filePath)
            .map(Checks::isRegularFile)
            .orElse(false);
    }

    public static boolean notRegularFile(@Nullable Path file) {
        return !isRegularFile(file);
    }

    public static boolean notRegularFile(@Nullable File file) {
        return !isRegularFile(file);
    }

    public static boolean notRegularFile(@Nullable String filePath) {
        return !isRegularFile(filePath);
    }

    public static boolean isDirectory(@Nullable Path directory) {
        return notNull(directory) && Files.isDirectory(directory);
    }

    public static boolean isDirectory(@Nullable File directory) {
        return notNull(directory) && directory.isDirectory();
    }

    public static boolean isDirectory(@Nullable String directoryPath) {
        return RequireUtils
            .pathGetOptional(directoryPath)
            .map(Checks::isDirectory)
            .orElse(false);
    }

    public static boolean notDirectory(@Nullable Path directory) {
        return !isDirectory(directory);
    }

    public static boolean notDirectory(@Nullable File directory) {
        return !isDirectory(directory);
    }

    public static boolean notDirectory(@Nullable String directoryPath) {
        return !isDirectory(directoryPath);
    }

    public static boolean isFuture(@Nullable ZonedDateTime dateTime) {
        return notNull(dateTime) && dateTime.isAfter(ZonedDateTime.now());
    }

    public static boolean isFuture(@Nullable OffsetDateTime dateTime) {
        return notNull(dateTime) && dateTime.isAfter(OffsetDateTime.now());
    }

    public static boolean isFuture(@Nullable LocalDateTime dateTime) {
        return notNull(dateTime) && dateTime.isAfter(LocalDateTime.now());
    }

    public static boolean isFuture(@Nullable LocalDate date) {
        return notNull(date) && date.isAfter(LocalDate.now());
    }

    public static boolean isFuture(@Nullable LocalTime time) {
        return notNull(time) && time.isAfter(LocalTime.now());
    }

    public static boolean isPast(@Nullable ZonedDateTime dateTime) {
        return notNull(dateTime) && dateTime.isBefore(ZonedDateTime.now());
    }

    public static boolean isPast(@Nullable OffsetDateTime dateTime) {
        return notNull(dateTime) && dateTime.isBefore(OffsetDateTime.now());
    }

    public static boolean isPast(@Nullable LocalDateTime dateTime) {
        return notNull(dateTime) && dateTime.isBefore(LocalDateTime.now());
    }

    public static boolean isPast(@Nullable LocalDate date) {
        return notNull(date) && date.isBefore(LocalDate.now());
    }

    public static boolean isPast(@Nullable LocalTime time) {
        return notNull(time) && time.isBefore(LocalTime.now());
    }

    public static boolean isInstanceOf(@Nullable Class<?> classType, @Nullable Object object) {
        return notNull(classType) && notNull(object) && classType.isInstance(object);
    }

    public static boolean notInstanceOf(@Nullable Class<?> classType, @Nullable Object object) {
        return !isInstanceOf(classType, object);
    }

    public static boolean contains(@Nullable CharSequence contains, @Nullable CharSequence stringToCheck) {
        return notNull(contains) && notNull(stringToCheck) && stringToCheck.toString().contains(contains);
    }

    public static <T> boolean contains(@Nullable T contains, @Nullable Collection<T> collectionToCheck) {
        return notNull(contains) && notNull(collectionToCheck) && collectionToCheck.contains(contains);
    }

    public static <T> boolean contains(@Nullable T contains, @Nullable T[] arrayToCheck) {
        return notNull(contains) && notNull(arrayToCheck) && Arrays.asList(arrayToCheck).contains(contains);
    }

    public static <T> boolean contains(@Nullable T contains, @Nullable Iterable<T> iterableToCheck) {
        return notNull(contains) && notNull(iterableToCheck) && StreamSupport
            .stream(iterableToCheck.spliterator(), false).anyMatch(contains::equals);
    }

    public static boolean notContains(@Nullable CharSequence contains, @Nullable CharSequence stringToCheck) {
        return !contains(contains, stringToCheck);
    }

    public static <T> boolean notContains(@Nullable T contains, @Nullable Collection<T> collectionToCheck) {
        return !contains(contains, collectionToCheck);
    }

    public static <T> boolean notContains(@Nullable T contains, @Nullable T[] arrayToCheck) {
        return !contains(contains, arrayToCheck);
    }

    public static <T> boolean notContains(@Nullable T contains, @Nullable Iterable<T> iterableToCheck) {
        return !contains(contains, iterableToCheck);
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

    public static <T> boolean containsElement(@Nullable Predicate<? super T> predicate, @Nullable T[] arrayToCheck) {
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

    public static <T> boolean notContainsElement(@Nullable Predicate<? super T> predicate, @Nullable T[] arrayToCheck) {
        return !containsElement(predicate, arrayToCheck);
    }

    public static <T> boolean notContainsElement(
        @Nullable Predicate<? super T> predicate,
        @Nullable Iterable<T> iterableToCheck) {

        return !containsElement(predicate, iterableToCheck);
    }

    public static boolean containsRegex(@Nullable Pattern regex, @Nullable CharSequence stringToCheck) {
        return notNull(regex) && notNull(stringToCheck) && regex.matcher(stringToCheck).find();
    }

    public static boolean containsRegex(@Nullable String regex, @Nullable CharSequence stringToCheck) {
        return notNull(regex) && containsRegex(Pattern.compile(regex), stringToCheck);
    }

    public static boolean matchesRegex(@Nullable Pattern regex, @Nullable CharSequence stringToCheck) {
        return notNull(regex) && notNull(stringToCheck) && regex.matcher(stringToCheck).matches();
    }

    public static boolean matchesRegex(@Nullable String regex, @Nullable CharSequence stringToCheck) {
        return notNull(regex) && matchesRegex(Pattern.compile(regex), stringToCheck);
    }

    public static boolean containsKey(@Nullable Object key, @Nullable Map<?, ?> mapToCheck) {
        return notNull(key) && notNull(mapToCheck) && mapToCheck.containsKey(key);
    }

    public static boolean containsValue(@Nullable Object value, @Nullable Map<?, ?> mapToCheck) {
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
