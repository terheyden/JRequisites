package com.terheyden.require;

import javax.annotation.Nullable;
import javax.xml.parsers.DocumentBuilder;
import java.io.File;
import java.nio.file.Path;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.regex.Pattern;

import org.jetbrains.annotations.Contract;

/**
 * Methods in this class will never throw an exception, only return true or false.
 * <p>
 * "Positive" (qualifying) methods, like {@link #isEmail(String)} will return false if
 * the input is null. "Negative" (disqualifying) methods, like {@link #isNotEmail(String)}
 * will return true if the input is null.
 */
@SuppressWarnings({ "SizeReplaceableByIsEmpty", "BooleanMethodNameMustStartWithQuestion" })
public final class CheckIf {

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

    private CheckIf() {
        // Private constructor since this shouldn't be instantiated.
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
    public static <T> Optional<T> ifNotNull(@Nullable T obj) {
        return Optional.ofNullable(obj);
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
    public static <T extends CharSequence> Optional<T> ifNotEmpty(@Nullable T str) {
        return Check.notEmpty(str) ? Optional.ofNullable(str) : Optional.empty();
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
    public static <T extends Collection<?>> Optional<T> ifNotEmpty(@Nullable T collection) {
        return Check.notEmpty(collection) ? Optional.ofNullable(collection) : Optional.empty();
    }

    public static <T extends Iterable<?>> Optional<T> ifNotEmpty(@Nullable T iterable) {
        return Check.notEmpty(iterable) ? Optional.ofNullable(iterable) : Optional.empty();
    }

    public static <T extends Map<?, ?>> Optional<T> ifNotEmpty(@Nullable T map) {
        return Check.notEmpty(map) ? Optional.ofNullable(map) : Optional.empty();
    }

    public static <T> Optional<T[]> ifNotEmpty(@Nullable T[] array) {
        return Check.notEmpty(array) ? Optional.ofNullable(array) : Optional.empty();
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
    public static <T extends CharSequence> Optional<T> ifNotBlank(@Nullable T str) {
        return Check.notBlank(str) ? Optional.of(str) : Optional.empty();
    }

    public static <T extends CharSequence> Optional<T> ifLength(int length, @Nullable T str) {
        return Check.isLength(length, str) ? Optional.of(str) : Optional.empty();
    }

    public static <T> Optional<T[]> ifLength(int length, @Nullable T[] array) {
        return Check.isLength(length, array) ? Optional.of(array) : Optional.empty();
    }

    public static <T extends CharSequence> Optional<T> ifLengthGreaterThan(int lowValue, @Nullable T str) {
        return Check.isLengthGreaterThan(lowValue, str) ? Optional.ofNullable(str) : Optional.empty();
    }

    public static <T> Optional<T[]> ifLengthGreaterThan(int lowValue, @Nullable T[] array) {
        return Check.isLengthGreaterThan(lowValue, array) ? Optional.ofNullable(array) : Optional.empty();
    }

    public static <T extends CharSequence> Optional<T> ifLengthGreaterOrEqualTo(int minLength, @Nullable T str) {
        return Check.isLengthGreaterOrEqualTo(minLength, str) ? Optional.ofNullable(str) : Optional.empty();
    }

    public static <T> Optional<T[]> ifLengthGreaterOrEqualTo(int minLength, @Nullable T[] array) {
        return Check.isLengthGreaterOrEqualTo(minLength, array) ? Optional.ofNullable(array) : Optional.empty();
    }

    public static <T extends CharSequence> Optional<T> ifLengthLessThan(int maxValue, @Nullable T str) {
        return Check.isLengthLessThan(maxValue, str) ? Optional.ofNullable(str) : Optional.empty();
    }

    public static <T> Optional<T[]> ifLengthLessThan(int maxValue, @Nullable T[] array) {
        return Check.isLengthLessThan(maxValue, array) ? Optional.ofNullable(array) : Optional.empty();
    }

    public static <T extends CharSequence> Optional<T> ifLengthLessOrEqualTo(int maxLength, @Nullable T str) {
        return Check.isLengthLessOrEqualTo(maxLength, str) ? Optional.ofNullable(str) : Optional.empty();
    }

    public static <T> Optional<T[]> ifLengthLessOrEqualTo(int maxLength, @Nullable T[] array) {
        return Check.isLengthLessOrEqualTo(maxLength, array) ? Optional.ofNullable(array) : Optional.empty();
    }

    public static <T extends Collection<?>> Optional<T> ifSize(int size, @Nullable T collection) {
        return Check.isSize(size, collection) ? Optional.ofNullable(collection) : Optional.empty();
    }

    public static <T extends Iterable<?>> Optional<T> ifSize(int size, @Nullable T iterable) {
        return Check.isSize(size, iterable) ? Optional.ofNullable(iterable) : Optional.empty();
    }

    public static <T extends Map<?, ?>> Optional<T> ifSize(int size, @Nullable T map) {
        return Check.isSize(size, map) ? Optional.ofNullable(map) : Optional.empty();
    }

    public static <T extends Collection<?>> Optional<T> ifSizeGreaterThan(int minSize, @Nullable T collection) {
        return Check.isSizeGreaterThan(minSize, collection) ? Optional.ofNullable(collection) : Optional.empty();
    }

    public static <T extends Iterable<?>> Optional<T> ifSizeGreaterThan(int minSize, @Nullable T iterable) {
        return Check.isSizeGreaterThan(minSize, iterable) ? Optional.ofNullable(iterable) : Optional.empty();
    }

    public static <T extends Map<?, ?>> Optional<T> ifSizeGreaterThan(int minSize, @Nullable T map) {
        return Check.isSizeGreaterThan(minSize, map) ? Optional.ofNullable(map) : Optional.empty();
    }

    public static <T extends Collection<?>> Optional<T> ifSizeGreaterOrEqualTo(int minSize, @Nullable T collection) {
        return Check.isSizeGreaterOrEqualTo(minSize, collection) ? Optional.ofNullable(collection) : Optional.empty();
    }

    public static <T extends Iterable<?>> Optional<T> ifSizeGreaterOrEqualTo(int minSize, @Nullable T iterable) {
        return Check.isSizeGreaterOrEqualTo(minSize, iterable) ? Optional.ofNullable(iterable) : Optional.empty();
    }

    public static <T extends Map<?, ?>> Optional<T> ifSizeGreaterOrEqualTo(int minSize, @Nullable T map) {
        return Check.isSizeGreaterOrEqualTo(minSize, map) ? Optional.ofNullable(map) : Optional.empty();
    }

    public static <T extends Collection<?>> Optional<T> ifSizeLessThan(int maxSize, @Nullable T collection) {
        return Check.isSizeLessThan(maxSize, collection) ? Optional.ofNullable(collection) : Optional.empty();
    }

    public static <T extends Iterable<?>> Optional<T> ifSizeLessThan(int maxSize, @Nullable T iterable) {
        return Check.isSizeLessThan(maxSize, iterable) ? Optional.ofNullable(iterable) : Optional.empty();
    }

    public static <T extends Map<?, ?>> Optional<T> ifSizeLessThan(int maxSize, @Nullable T map) {
        return Check.isSizeLessThan(maxSize, map) ? Optional.ofNullable(map) : Optional.empty();
    }

    public static <T extends Collection<?>> Optional<T> ifSizeLessOrEqualTo(int maxSize, @Nullable T collection) {
        return Check.isSizeLessOrEqualTo(maxSize, collection) ? Optional.ofNullable(collection) : Optional.empty();
    }

    public static <T extends Iterable<?>> Optional<T> ifSizeLessOrEqualTo(int maxSize, @Nullable T iterable) {
        return Check.isSizeLessOrEqualTo(maxSize, iterable) ? Optional.ofNullable(iterable) : Optional.empty();
    }

    public static <T extends Map<?, ?>> Optional<T> ifSizeLessOrEqualTo(int maxSize, @Nullable T map) {
        return Check.isSizeLessOrEqualTo(maxSize, map) ? Optional.ofNullable(map) : Optional.empty();
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
    public static boolean ifDurationGreaterThan(@Nullable Duration minDurationLimit, @Nullable Duration durationToCheck) {
        return Check.isDurationGreaterThan(minDurationLimit, durationToCheck);
    }

    /**
     * True if the duration is greater than the specified nanoseconds.
     */
    public static boolean ifDurationGreaterThanNanos(long minValueNs, @Nullable Duration durationToCheck) {
        return Check.isDurationGreaterThanNanos(minValueNs, durationToCheck);
    }

    /**
     * True if the duration is greater than the specified milliseconds.
     */
    public static boolean ifDurationGreaterThanMillis(long minValueMs, @Nullable Duration durationToCheck) {
        return Check.isDurationGreaterThanMillis(minValueMs, durationToCheck);
    }

    /**
     * True if the duration is greater than the specified seconds.
     */
    public static boolean ifDurationGreaterThanSecs(long minValueSecs, @Nullable Duration durationToCheck) {
        return Check.isDurationGreaterThanSecs(minValueSecs, durationToCheck);
    }

    /**
     * True if the duration is greater than the specified minutes.
     */
    public static boolean ifDurationGreaterThanMins(long minValueMins, @Nullable Duration durationToCheck) {
        return Check.isDurationGreaterThanMins(minValueMins, durationToCheck);
    }

    /**
     * True if the duration is greater than the specified hours.
     */
    public static boolean ifDurationGreaterThanHours(long minValueHours, @Nullable Duration durationToCheck) {
        return Check.isDurationGreaterThanHours(minValueHours, durationToCheck);
    }

    /**
     * True if the duration is greater than the specified days.
     */
    public static boolean ifDurationGreaterThanDays(long minValueDays, @Nullable Duration durationToCheck) {
        return Check.isDurationGreaterThanDays(minValueDays, durationToCheck);
    }

    public static boolean ifDurationGreaterOrEqualTo(@Nullable Duration minDuration, @Nullable Duration durationToCheck) {
        return Check.isDurationGreaterOrEqualTo(minDuration, durationToCheck);
    }

    /**
     * True if the duration is greater than the specified nanoseconds.
     */
    public static boolean ifDurationGreaterOrEqualToNanos(long minValueNs, @Nullable Duration durationToCheck) {
        return Check.isDurationGreaterOrEqualToNanos(minValueNs, durationToCheck);
    }

    /**
     * True if the duration is greater than the specified milliseconds.
     */
    public static boolean ifDurationGreaterOrEqualToMillis(long minValueMs, @Nullable Duration durationToCheck) {
        return Check.isDurationGreaterOrEqualToMillis(minValueMs, durationToCheck);
    }

    /**
     * True if the duration is greater than the specified seconds.
     */
    public static boolean ifDurationGreaterOrEqualToSecs(long minValueSecs, @Nullable Duration durationToCheck) {
        return Check.isDurationGreaterOrEqualToSecs(minValueSecs, durationToCheck);
    }

    /**
     * True if the duration is greater than the specified minutes.
     */
    public static boolean ifDurationGreaterOrEqualToMins(long minValueMins, @Nullable Duration durationToCheck) {
        return Check.isDurationGreaterOrEqualToMins(minValueMins, durationToCheck);
    }

    /**
     * True if the duration is greater than the specified hours.
     */
    public static boolean ifDurationGreaterOrEqualToHours(long minValueHours, @Nullable Duration durationToCheck) {
        return Check.isDurationGreaterOrEqualToHours(minValueHours, durationToCheck);
    }

    /**
     * True if the duration is greater than the specified days.
     */
    public static boolean ifDurationGreaterOrEqualToDays(long minValueDays, @Nullable Duration durationToCheck) {
        return Check.isDurationGreaterOrEqualToDays(minValueDays, durationToCheck);
    }

    public static boolean ifDurationLessThan(@Nullable Duration maxDurationLimit, @Nullable Duration durationToCheck) {
        return Check.isDurationLessThan(maxDurationLimit, durationToCheck);
    }

    /**
     * True if the duration is less than the specified nanoseconds.
     */
    public static boolean ifDurationLessThanNanos(long maxValueNs, @Nullable Duration durationToCheck) {
        return Check.isDurationLessThanNanos(maxValueNs, durationToCheck);
    }

    /**
     * True if the duration is less than the specified milliseconds.
     */
    public static boolean ifDurationLessThanMillis(long maxValueMs, @Nullable Duration durationToCheck) {
        return Check.isDurationLessThanMillis(maxValueMs, durationToCheck);
    }

    /**
     * True if the duration is less than the specified seconds.
     */
    public static boolean ifDurationLessThanSecs(long maxValueSecs, @Nullable Duration durationToCheck) {
        return Check.isDurationLessThanSecs(maxValueSecs, durationToCheck);
    }

    /**
     * True if the duration is less than the specified minutes.
     */
    public static boolean ifDurationLessThanMins(long maxValueMins, @Nullable Duration durationToCheck) {
        return Check.isDurationLessThanMins(maxValueMins, durationToCheck);
    }

    /**
     * True if the duration is less than the specified hours.
     */
    public static boolean ifDurationLessThanHours(long maxValueHours, @Nullable Duration durationToCheck) {
        return Check.isDurationLessThanHours(maxValueHours, durationToCheck);
    }

    /**
     * True if the duration is less than the specified days.
     */
    public static boolean ifDurationLessThanDays(long maxValueDays, @Nullable Duration durationToCheck) {
        return Check.isDurationLessThanDays(maxValueDays, durationToCheck);
    }

    public static boolean ifDurationLessOrEqualTo(@Nullable Duration maxDuration, @Nullable Duration durationToCheck) {
        return Check.isDurationLessOrEqualTo(maxDuration, durationToCheck);
    }

    /**
     * True if the duration is less than the specified nanoseconds.
     */
    public static boolean ifDurationLessOrEqualToNanos(long maxNanos, @Nullable Duration durationToCheck) {
        return Check.isDurationLessOrEqualToNanos(maxNanos, durationToCheck);
    }

    /**
     * True if the duration is less than the specified milliseconds.
     */
    public static boolean ifDurationLessOrEqualToMillis(long maxMillis, @Nullable Duration durationToCheck) {
        return Check.isDurationLessOrEqualToMillis(maxMillis, durationToCheck);
    }

    /**
     * True if the duration is less than the specified seconds.
     */
    public static boolean ifDurationLessOrEqualToSecs(long maxSecs, @Nullable Duration durationToCheck) {
        return Check.isDurationLessOrEqualToSecs(maxSecs, durationToCheck);
    }

    /**
     * True if the duration is less than the specified minutes.
     */
    public static boolean ifDurationLessOrEqualToMins(long maxMins, @Nullable Duration durationToCheck) {
        return Check.isDurationLessOrEqualToMins(maxMins, durationToCheck);
    }

    /**
     * True if the duration is less than the specified hours.
     */
    public static boolean ifDurationLessOrEqualToHours(long maxHours, @Nullable Duration durationToCheck) {
        return Check.isDurationLessOrEqualToHours(maxHours, durationToCheck);
    }

    /**
     * True if the duration is less than the specified days.
     */
    public static boolean ifDurationLessOrEqualToDays(long maxDays, @Nullable Duration durationToCheck) {
        return Check.isDurationLessOrEqualToDays(maxDays, durationToCheck);
    }

    public static boolean pathExists(@Nullable Path path) {
        return Check.pathExists(path);
    }

    public static boolean pathExists(@Nullable File path) {
        return Check.pathExists(path);
    }

    public static boolean pathExists(@Nullable String path) {
        return Check.pathExists(path);
    }

    /**
     * It's expected that this method will be used as a guard clause.
     * Therefore this method returns true if the path is null.
     */
    public static boolean ifNotExists(@Nullable Path path) {
        return Check.notExists(path);
    }

    public static boolean ifNotExists(@Nullable File path) {
        return Check.notExists(path);
    }

    public static boolean ifNotExists(@Nullable String path) {
        return Check.notExists(path);
    }

    public static boolean ifRegularFile(@Nullable Path file) {
        return Check.isRegularFile(file);
    }

    public static boolean ifRegularFile(@Nullable File file) {
        return Check.isRegularFile(file);
    }

    public static boolean ifRegularFile(@Nullable String filePath) {
        return Check.isRegularFile(filePath);
    }

    public static boolean ifNotRegularFile(@Nullable Path file) {
        return Check.notRegularFile(file);
    }

    public static boolean ifNotRegularFile(@Nullable File file) {
        return Check.notRegularFile(file);
    }

    public static boolean ifNotRegularFile(@Nullable String filePath) {
        return Check.notRegularFile(filePath);
    }

    public static boolean ifDirectory(@Nullable Path directory) {
        return Check.isDirectory(directory);
    }

    public static boolean ifDirectory(@Nullable File directory) {
        return Check.isDirectory(directory);
    }

    public static boolean ifDirectory(@Nullable String directoryPath) {
        return Check.isDirectory(directoryPath);
    }

    public static boolean ifNotDirectory(@Nullable Path directory) {
        return Check.notDirectory(directory);
    }

    public static boolean ifNotDirectory(@Nullable File directory) {
        return Check.notDirectory(directory);
    }

    public static boolean ifNotDirectory(@Nullable String directoryPath) {
        return Check.notDirectory(directoryPath);
    }

    public static boolean ifFuture(@Nullable ZonedDateTime dateTime) {
        return Check.isFuture(dateTime);
    }

    public static boolean ifFuture(@Nullable OffsetDateTime dateTime) {
        return Check.isFuture(dateTime);
    }

    public static boolean ifFuture(@Nullable LocalDateTime dateTime) {
        return Check.isFuture(dateTime);
    }

    public static boolean ifFuture(@Nullable LocalDate date) {
        return Check.isFuture(date);
    }

    public static boolean ifFuture(@Nullable LocalTime time) {
        return Check.isFuture(time);
    }

    public static boolean ifPast(@Nullable ZonedDateTime dateTime) {
        return Check.isPast(dateTime);
    }

    public static boolean ifPast(@Nullable OffsetDateTime dateTime) {
        return Check.isPast(dateTime);
    }

    public static boolean ifPast(@Nullable LocalDateTime dateTime) {
        return Check.isPast(dateTime);
    }

    public static boolean ifPast(@Nullable LocalDate date) {
        return Check.isPast(date);
    }

    public static boolean ifPast(@Nullable LocalTime time) {
        return Check.isPast(time);
    }

    public static boolean ifInstanceOf(@Nullable Class<?> classType, @Nullable Object object) {
        return Check.isInstanceOf(classType, object);
    }

    public static boolean ifNotInstanceOf(@Nullable Class<?> classType, @Nullable Object object) {
        return Check.notInstanceOf(classType, object);
    }

    public static boolean contains(@Nullable CharSequence contains, @Nullable CharSequence stringToCheck) {
        return Check.contains(contains, stringToCheck);
    }

    public static <T> boolean contains(@Nullable T contains, @Nullable Collection<T> collectionToCheck) {
        return Check.contains(contains, collectionToCheck);
    }

    public static <T> boolean contains(@Nullable T contains, @Nullable T[] arrayToCheck) {
        return Check.contains(contains, arrayToCheck);
    }

    public static <T> boolean contains(@Nullable T contains, @Nullable Iterable<T> iterableToCheck) {
        return Check.contains(contains, iterableToCheck);
    }

    public static boolean ifNotContains(@Nullable CharSequence contains, @Nullable CharSequence stringToCheck) {
        return Check.notContains(contains, stringToCheck);
    }

    public static <T> boolean notContains(@Nullable T contains, @Nullable Collection<T> collectionToCheck) {
        return Check.notContains(contains, collectionToCheck);
    }

    public static <T> boolean notContains(@Nullable T contains, @Nullable T[] arrayToCheck) {
        return Check.notContains(contains, arrayToCheck);
    }

    public static <T> boolean notContains(@Nullable T contains, @Nullable Iterable<T> iterableToCheck) {
        return Check.notContains(contains, iterableToCheck);
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
        return Check.containsElement(predicate, collectionToCheck);
    }

    public static <T> boolean containsElement(@Nullable Predicate<? super T> predicate, @Nullable T[] arrayToCheck) {
        return Check.containsElement(predicate, arrayToCheck);
    }

    public static <T> boolean containsElement(
        @Nullable Predicate<? super T> predicate,
        @Nullable Iterable<T> iterableToCheck) {
        return Check.containsElement(predicate, iterableToCheck);
    }

    public static <T> boolean notContainsElement(
        @Nullable Predicate<? super T> predicate,
        @Nullable Collection<T> collectionToCheck) {
        return Check.notContainsElement(predicate, collectionToCheck);
    }

    public static <T> boolean notContainsElement(@Nullable Predicate<? super T> predicate, @Nullable T[] arrayToCheck) {
        return Check.notContainsElement(predicate, arrayToCheck);
    }

    public static <T> boolean notContainsElement(
        @Nullable Predicate<? super T> predicate,
        @Nullable Iterable<T> iterableToCheck) {
        return Check.notContainsElement(predicate, iterableToCheck);
    }

    public static boolean containsRegex(@Nullable Pattern regex, @Nullable CharSequence stringToCheck) {
        return Check.containsRegex(regex, stringToCheck);
    }

    public static boolean containsRegex(@Nullable String regex, @Nullable CharSequence stringToCheck) {
        return Check.containsRegex(regex, stringToCheck);
    }

    public static boolean matchesRegex(@Nullable Pattern regex, @Nullable CharSequence stringToCheck) {
        return Check.matchesRegex(regex, stringToCheck);
    }

    public static boolean matchesRegex(@Nullable String regex, @Nullable CharSequence stringToCheck) {
        return Check.matchesRegex(regex, stringToCheck);
    }

    public static boolean containsKey(@Nullable Object key, @Nullable Map<?, ?> mapToCheck) {
        return Check.containsKey(key, mapToCheck);
    }

    public static boolean containsValue(@Nullable Object value, @Nullable Map<?, ?> mapToCheck) {
        return Check.containsValue(value, mapToCheck);
    }

    public static boolean containsUniqueElements(@Nullable Collection<?> collectionToCheck) {
        return Check.containsUniqueElements(collectionToCheck);
    }

    public static boolean containsNull(@Nullable Collection<?> collectionToCheck) {
        return Check.containsNull(collectionToCheck);
    }

    public static boolean containsNull(@Nullable Iterable<?> iterableToCheck) {
        return Check.containsNull(iterableToCheck);
    }

    public static boolean containsNull(@Nullable Object[] arrayToCheck) {
        return Check.containsNull(arrayToCheck);
    }

    public static boolean containsNull(@Nullable Map<?, ?> mapToCheck) {
        return Check.containsNull(mapToCheck);
    }

    /**
     * True if the collection is not null, and does not contain any null elements.
     */
    public static boolean ifNotContainsNull(@Nullable Collection<?> collectionToCheck) {
        return Check.notContainsNull(collectionToCheck);
    }

    public static boolean ifNotContainsNull(@Nullable Iterable<?> iterableToCheck) {
        return Check.notContainsNull(iterableToCheck);
    }

    /**
     * True if the map does not contain null keys or values.
     * @throws IllegalStateException if the map does not support null keys or values
     */
    public static boolean ifNotContainsNull(@Nullable Map<?, ?> mapToCheck) {
        return Check.notContainsNull(mapToCheck);
    }

    public static boolean ifNotContainsNull(@Nullable Object[] arrayToCheck) {
        return Check.notContainsNull(arrayToCheck);
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
    public static <T extends CharSequence> Optional<T> numbersOnly(@Nullable T str) {
        return Check.numbersOnly(str) ? Optional.ofNullable(str) : Optional.empty();
    }

    public static <T extends CharSequence> Optional<T> ifNotNumbersOnly(@Nullable T str) {
        return Check.notNumbersOnly(str) ? Optional.ofNullable(str) : Optional.empty();
    }

    @Contract("null -> false")
    public static <T extends CharSequence> Optional<T> alphasOnly(@Nullable T str) {
        return Check.alphasOnly(str) ? Optional.ofNullable(str) : Optional.empty();
    }

    public static <T extends CharSequence> Optional<T> ifNotAlphasOnly(@Nullable T str) {
        return Check.notAlphasOnly(str) ? Optional.ofNullable(str) : Optional.empty();
    }

    @Contract("null -> false")
    public static <T extends CharSequence> Optional<T> alphaNumericOnly(@Nullable T str) {
        return Check.alphaNumericOnly(str) ? Optional.ofNullable(str) : Optional.empty();
    }

    public static <T extends CharSequence> Optional<T> ifNotAlphaNumericOnly(@Nullable T str) {
        return Check.notAlphaNumericOnly(str) ? Optional.ofNullable(str) : Optional.empty();
    }

    public static boolean ifEmail(@Nullable CharSequence email) {
        return Check.isEmail(email);
    }

    /**
     * True if the value begins with {@code http://} or {@code https://} and has any sort of hostname or IP address.
     */
    public static boolean ifUrl(@Nullable CharSequence url) {
        return Check.isUrl(url);
    }

    /**
     * True if this is a valid IPv4 or IPv6 address.
     * If you want to check for a specific version, use {@link #isIPv4Address(CharSequence)} or
     * {@link #isIPv6Address(CharSequence)}.
     */
    public static boolean ifIpAddress(@Nullable CharSequence ipAddress) {
        return Check.isIpAddress(ipAddress);
    }

    public static boolean ifIPv4Address(@Nullable CharSequence ip4Address) {
        return Check.isIPv4Address(ip4Address);
    }

    public static boolean ifIPv6Address(@Nullable CharSequence ip6Address) {
        return Check.isIPv6Address(ip6Address);
    }

    public static boolean ifNotIPAddress(@Nullable CharSequence ipAddress) {
        return Check.notIPAddress(ipAddress);
    }

    public static boolean ifNotIPv4Address(@Nullable CharSequence ip4Address) {
        return Check.notIPv4Address(ip4Address);
    }

    public static boolean ifNotIPv6Address(@Nullable CharSequence ip6Address) {
        return Check.notIPv6Address(ip6Address);
    }

    /**
     * True if the value is not empty, and looks like JSON.
     * This means starting and ending with curly braces or square brackets.
     */
    public static boolean ifJson(@Nullable CharSequence json) {
        return Check.isJson(json);
    }

    public static boolean ifNotJson(@Nullable CharSequence json) {
        return Check.notJson(json);
    }

    /**
     * True if the value is not empty and can be parsed as XML without errors.
     * Uses {@link DocumentBuilder} to parse the XML.
     */
    public static boolean ifXml(@Nullable CharSequence xml) {
        return Check.isXml(xml);
    }

    public static boolean ifNotXml(@Nullable CharSequence xml) {
        return Check.notXml(xml);
    }

    public static boolean ifUUID(@Nullable CharSequence uuid) {
        return Check.isUUID(uuid);
    }

    public static boolean ifNotUUID(@Nullable CharSequence uuid) {
        return Check.notUUID(uuid);
    }
}
