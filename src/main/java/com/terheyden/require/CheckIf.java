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

    public static <T extends CharSequence> Optional<T> ifLength(@Nullable T str, int length) {
        return Check.isLength(str, length) ? Optional.of(str) : Optional.empty();
    }

    public static <T> Optional<T[]> ifLength(@Nullable T[] array, int length) {
        return Check.isLength(array, length) ? Optional.of(array) : Optional.empty();
    }

    public static <T extends CharSequence> Optional<T> ifLengthGreaterThan(@Nullable T str, int lowValue) {
        return Check.isLengthGreaterThan(str, lowValue) ? Optional.ofNullable(str) : Optional.empty();
    }

    public static <T> Optional<T[]> ifLengthGreaterThan(@Nullable T[] array, int lowValue) {
        return Check.isLengthGreaterThan(array, lowValue) ? Optional.ofNullable(array) : Optional.empty();
    }

    public static <T extends CharSequence> Optional<T> ifLengthGreaterOrEqualTo(@Nullable T str, int minLength) {
        return Check.isLengthGreaterOrEqualTo(str, minLength) ? Optional.ofNullable(str) : Optional.empty();
    }

    public static <T> Optional<T[]> ifLengthGreaterOrEqualTo(@Nullable T[] array, int minLength) {
        return Check.isLengthGreaterOrEqualTo(array, minLength) ? Optional.ofNullable(array) : Optional.empty();
    }

    public static <T extends CharSequence> Optional<T> ifLengthLessThan(@Nullable T str, int maxValue) {
        return Check.isLengthLessThan(str, maxValue) ? Optional.ofNullable(str) : Optional.empty();
    }

    public static <T> Optional<T[]> ifLengthLessThan(@Nullable T[] array, int maxValue) {
        return Check.isLengthLessThan(array, maxValue) ? Optional.ofNullable(array) : Optional.empty();
    }

    public static <T extends CharSequence> Optional<T> ifLengthLessOrEqualTo(@Nullable T str, int maxLength) {
        return Check.isLengthLessOrEqualTo(str, maxLength) ? Optional.ofNullable(str) : Optional.empty();
    }

    public static <T> Optional<T[]> ifLengthLessOrEqualTo(@Nullable T[] array, int maxLength) {
        return Check.isLengthLessOrEqualTo(array, maxLength) ? Optional.ofNullable(array) : Optional.empty();
    }

    public static <T extends Collection<?>> Optional<T> ifSize(@Nullable T collection, int size) {
        return Check.isSize(collection, size) ? Optional.ofNullable(collection) : Optional.empty();
    }

    public static <T extends Iterable<?>> Optional<T> ifSize(@Nullable T iterable, int size) {
        return Check.isSize(iterable, size) ? Optional.ofNullable(iterable) : Optional.empty();
    }

    public static <T extends Map<?, ?>> Optional<T> ifSize(@Nullable T map, int size) {
        return Check.isSize(map, size) ? Optional.ofNullable(map) : Optional.empty();
    }

    public static <T extends Collection<?>> Optional<T> ifSizeGreaterThan(@Nullable T collection, int minSize) {
        return Check.isSizeGreaterThan(collection, minSize) ? Optional.ofNullable(collection) : Optional.empty();
    }

    public static <T extends Iterable<?>> Optional<T> ifSizeGreaterThan(@Nullable T iterable, int minSize) {
        return Check.isSizeGreaterThan(iterable, minSize) ? Optional.ofNullable(iterable) : Optional.empty();
    }

    public static <T extends Map<?, ?>> Optional<T> ifSizeGreaterThan(@Nullable T map, int minSize) {
        return Check.isSizeGreaterThan(map, minSize) ? Optional.ofNullable(map) : Optional.empty();
    }

    public static <T extends Collection<?>> Optional<T> ifSizeGreaterOrEqualTo(@Nullable T collection, int minSize) {
        return Check.isSizeGreaterOrEqualTo(collection, minSize) ? Optional.ofNullable(collection) : Optional.empty();
    }

    public static <T extends Iterable<?>> Optional<T> ifSizeGreaterOrEqualTo(@Nullable T iterable, int minSize) {
        return Check.isSizeGreaterOrEqualTo(iterable, minSize) ? Optional.ofNullable(iterable) : Optional.empty();
    }

    public static <T extends Map<?, ?>> Optional<T> ifSizeGreaterOrEqualTo(@Nullable T map, int minSize) {
        return Check.isSizeGreaterOrEqualTo(map, minSize) ? Optional.ofNullable(map) : Optional.empty();
    }

    public static <T extends Collection<?>> Optional<T> ifSizeLessThan(@Nullable T collection, int maxSize) {
        return Check.isSizeLessThan(collection, maxSize) ? Optional.ofNullable(collection) : Optional.empty();
    }

    public static <T extends Iterable<?>> Optional<T> ifSizeLessThan(@Nullable T iterable, int maxSize) {
        return Check.isSizeLessThan(iterable, maxSize) ? Optional.ofNullable(iterable) : Optional.empty();
    }

    public static <T extends Map<?, ?>> Optional<T> ifSizeLessThan(@Nullable T map, int maxSize) {
        return Check.isSizeLessThan(map, maxSize) ? Optional.ofNullable(map) : Optional.empty();
    }

    public static <T extends Collection<?>> Optional<T> ifSizeLessOrEqualTo(@Nullable T collection, int maxSize) {
        return Check.isSizeLessOrEqualTo(collection, maxSize) ? Optional.ofNullable(collection) : Optional.empty();
    }

    public static <T extends Iterable<?>> Optional<T> ifSizeLessOrEqualTo(@Nullable T iterable, int maxSize) {
        return Check.isSizeLessOrEqualTo(iterable, maxSize) ? Optional.ofNullable(iterable) : Optional.empty();
    }

    public static <T extends Map<?, ?>> Optional<T> ifSizeLessOrEqualTo(@Nullable T map, int maxSize) {
        return Check.isSizeLessOrEqualTo(map, maxSize) ? Optional.ofNullable(map) : Optional.empty();
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
    @Contract(value = "null, _ -> !null; _, null -> !null", pure = true)
    public static Optional<Duration> ifDurationGreaterThan(@Nullable Duration durationToCheck, @Nullable Duration minDuration) {
        return Check.isDurationGreaterThan(durationToCheck, minDuration) ? Optional.of(durationToCheck) : Optional.empty();
    }

    /**
     * True if the duration is greater than the specified nanoseconds.
     */
    @Contract(value = "null, _ -> !null", pure = true)
    public static Optional<Duration> ifDurationGreaterThanNanos(@Nullable Duration durationToCheck, long minValueNs) {
        return Check.isDurationGreaterThanNanos(durationToCheck, minValueNs) ? Optional.of(durationToCheck) : Optional.empty();
    }

    /**
     * True if the duration is greater than the specified milliseconds.
     */
    @Contract(value = "null, _ -> !null", pure = true)
    public static Optional<Duration> ifDurationGreaterThanMillis(@Nullable Duration durationToCheck, long minValueMs) {
        return Check.isDurationGreaterThanMillis(durationToCheck, minValueMs) ? Optional.of(durationToCheck) : Optional.empty();
    }

    /**
     * True if the duration is greater than the specified seconds.
     */
    @Contract(value = "null, _ -> !null", pure = true)
    public static Optional<Duration> ifDurationGreaterThanSecs(@Nullable Duration durationToCheck, long minValueSecs) {
        return Check.isDurationGreaterThanSecs(durationToCheck, minValueSecs) ? Optional.of(durationToCheck) : Optional.empty();
    }

    /**
     * True if the duration is greater than the specified minutes.
     */
    @Contract(value = "null, _ -> !null", pure = true)
    public static Optional<Duration> ifDurationGreaterThanMins(@Nullable Duration durationToCheck, long minValueMins) {
        return Check.isDurationGreaterThanMins(durationToCheck, minValueMins) ? Optional.of(durationToCheck) : Optional.empty();
    }

    /**
     * True if the duration is greater than the specified hours.
     */
    @Contract(value = "null, _ -> !null", pure = true)
    public static Optional<Duration> ifDurationGreaterThanHours(@Nullable Duration durationToCheck, long minValueHours) {
        return Check.isDurationGreaterThanHours(durationToCheck, minValueHours) ? Optional.of(durationToCheck) : Optional.empty();
    }

    /**
     * True if the duration is greater than the specified days.
     */
    @Contract(value = "null, _ -> !null", pure = true)
    public static Optional<Duration> ifDurationGreaterThanDays(@Nullable Duration durationToCheck, long minValueDays) {
        return Check.isDurationGreaterThanDays(durationToCheck, minValueDays) ? Optional.of(durationToCheck) : Optional.empty();
    }

    @Contract(value = "null, _ -> !null", pure = true)
    public static Optional<Duration> ifDurationGreaterOrEqualTo(@Nullable Duration durationToCheck, @Nullable Duration minDuration) {
        return Check.isDurationGreaterOrEqualTo(durationToCheck, minDuration) ? Optional.of(durationToCheck) : Optional.empty();
    }

    /**
     * True if the duration is greater than the specified nanoseconds.
     */
    @Contract(value = "null, _ -> !null", pure = true)
    public static Optional<Duration> ifDurationGreaterOrEqualToNanos(@Nullable Duration durationToCheck, long minValueNs) {
        return Check.isDurationGreaterOrEqualToNanos(durationToCheck, minValueNs) ? Optional.of(durationToCheck) : Optional.empty();
    }

    /**
     * True if the duration is greater than the specified milliseconds.
     */
    @Contract(value = "null, _ -> !null", pure = true)
    public static Optional<Duration> ifDurationGreaterOrEqualToMillis(@Nullable Duration durationToCheck, long minValueMs) {
        return Check.isDurationGreaterOrEqualToMillis(durationToCheck, minValueMs) ? Optional.of(durationToCheck) : Optional.empty();
    }

    /**
     * True if the duration is greater than the specified seconds.
     */
    @Contract(value = "null, _ -> !null", pure = true)
    public static Optional<Duration> ifDurationGreaterOrEqualToSecs(@Nullable Duration durationToCheck, long minValueSecs) {
        return Check.isDurationGreaterOrEqualToSecs(durationToCheck, minValueSecs) ? Optional.of(durationToCheck) : Optional.empty();
    }

    /**
     * True if the duration is greater than the specified minutes.
     */
    @Contract(value = "null, _ -> !null", pure = true)
    public static Optional<Duration> ifDurationGreaterOrEqualToMins(@Nullable Duration durationToCheck, long minValueMins) {
        return Check.isDurationGreaterOrEqualToMins(durationToCheck, minValueMins) ? Optional.of(durationToCheck) : Optional.empty();
    }

    /**
     * True if the duration is greater than the specified hours.
     */
    @Contract(value = "null, _ -> !null", pure = true)
    public static Optional<Duration> ifDurationGreaterOrEqualToHours(@Nullable Duration durationToCheck, long minValueHours) {
        return Check.isDurationGreaterOrEqualToHours(durationToCheck, minValueHours) ? Optional.of(durationToCheck) : Optional.empty();
    }

    /**
     * True if the duration is greater than the specified days.
     */
    @Contract(value = "null, _ -> !null", pure = true)
    public static Optional<Duration> ifDurationGreaterOrEqualToDays(@Nullable Duration durationToCheck, long minValueDays) {
        return Check.isDurationGreaterOrEqualToDays(durationToCheck, minValueDays) ? Optional.of(durationToCheck) : Optional.empty();
    }

    @Contract(value = "null, _ -> !null", pure = true)
    public static Optional<Duration> ifDurationLessThan(@Nullable Duration durationToCheck, @Nullable Duration maxDurationLimit) {
        return Check.isDurationLessThan(durationToCheck, maxDurationLimit) ? Optional.of(durationToCheck) : Optional.empty();
    }

    /**
     * True if the duration is less than the specified nanoseconds.
     */
    @Contract(value = "null, _ -> !null", pure = true)
    public static Optional<Duration> ifDurationLessThanNanos(@Nullable Duration durationToCheck, long maxValueNs) {
        return Check.isDurationLessThanNanos(durationToCheck, maxValueNs) ? Optional.of(durationToCheck) : Optional.empty();
    }

    /**
     * True if the duration is less than the specified milliseconds.
     */
    @Contract(value = "null, _ -> !null", pure = true)
    public static Optional<Duration> ifDurationLessThanMillis(@Nullable Duration durationToCheck, long maxValueMs) {
        return Check.isDurationLessThanMillis(durationToCheck, maxValueMs) ? Optional.of(durationToCheck) : Optional.empty();
    }

    /**
     * True if the duration is less than the specified seconds.
     */
    @Contract(value = "null, _ -> !null", pure = true)
    public static Optional<Duration> ifDurationLessThanSecs(@Nullable Duration durationToCheck, long maxValueSecs) {
        return Check.isDurationLessThanSecs(durationToCheck, maxValueSecs) ? Optional.of(durationToCheck) : Optional.empty();
    }

    /**
     * True if the duration is less than the specified minutes.
     */
    @Contract(value = "null, _ -> !null", pure = true)
    public static Optional<Duration> ifDurationLessThanMins(@Nullable Duration durationToCheck, long maxValueMins) {
        return Check.isDurationLessThanMins(durationToCheck, maxValueMins) ? Optional.of(durationToCheck) : Optional.empty();
    }

    /**
     * True if the duration is less than the specified hours.
     */
    @Contract(value = "null, _ -> !null", pure = true)
    public static Optional<Duration> ifDurationLessThanHours(@Nullable Duration durationToCheck, long maxValueHours) {
        return Check.isDurationLessThanHours(durationToCheck, maxValueHours) ? Optional.of(durationToCheck) : Optional.empty();
    }

    /**
     * True if the duration is less than the specified days.
     */
    @Contract(value = "null, _ -> !null", pure = true)
    public static Optional<Duration> ifDurationLessThanDays(@Nullable Duration durationToCheck, long maxValueDays) {
        return Check.isDurationLessThanDays(durationToCheck, maxValueDays) ? Optional.of(durationToCheck) : Optional.empty();
    }

    @Contract(value = "null, _ -> !null", pure = true)
    public static Optional<Duration> ifDurationLessOrEqualTo(@Nullable Duration durationToCheck, @Nullable Duration maxDuration) {
        return Check.isDurationLessOrEqualTo(durationToCheck, maxDuration) ? Optional.of(durationToCheck) : Optional.empty();
    }

    /**
     * True if the duration is less than the specified nanoseconds.
     */
    @Contract(value = "null, _ -> !null", pure = true)
    public static Optional<Duration> ifDurationLessOrEqualToNanos(@Nullable Duration durationToCheck, long maxNanos) {
        return Check.isDurationLessOrEqualToNanos(durationToCheck, maxNanos) ? Optional.of(durationToCheck) : Optional.empty();
    }

    /**
     * True if the duration is less than the specified milliseconds.
     */
    @Contract(value = "null, _ -> !null", pure = true)
    public static Optional<Duration> ifDurationLessOrEqualToMillis(@Nullable Duration durationToCheck, long maxMillis) {
        return Check.isDurationLessOrEqualToMillis(durationToCheck, maxMillis) ? Optional.of(durationToCheck) : Optional.empty();
    }

    /**
     * True if the duration is less than the specified seconds.
     */
    @Contract(value = "null, _ -> !null", pure = true)
    public static Optional<Duration> ifDurationLessOrEqualToSecs(@Nullable Duration durationToCheck, long maxSecs) {
        return Check.isDurationLessOrEqualToSecs(durationToCheck, maxSecs) ? Optional.of(durationToCheck) : Optional.empty();
    }

    /**
     * True if the duration is less than the specified minutes.
     */
    @Contract(value = "null, _ -> !null", pure = true)
    public static Optional<Duration> ifDurationLessOrEqualToMins(@Nullable Duration durationToCheck, long maxMins) {
        return Check.isDurationLessOrEqualToMins(durationToCheck, maxMins) ? Optional.of(durationToCheck) : Optional.empty();
    }

    /**
     * True if the duration is less than the specified hours.
     */
    @Contract(value = "null, _ -> !null", pure = true)
    public static Optional<Duration> ifDurationLessOrEqualToHours(@Nullable Duration durationToCheck, long maxHours) {
        return Check.isDurationLessOrEqualToHours(durationToCheck, maxHours) ? Optional.of(durationToCheck) : Optional.empty();
    }

    /**
     * True if the duration is less than the specified days.
     */
    @Contract(value = "null, _ -> !null", pure = true)
    public static Optional<Duration> ifDurationLessOrEqualToDays(@Nullable Duration durationToCheck, long maxDays) {
        return Check.isDurationLessOrEqualToDays(durationToCheck, maxDays) ? Optional.of(durationToCheck) : Optional.empty();
    }

    public static Optional<Path> ifPathExists(@Nullable Path path) {
        return Check.pathExists(path) ? Optional.of(path) : Optional.empty();
    }

    public static Optional<File> ifPathExists(@Nullable File path) {
        return Check.pathExists(path) ? Optional.of(path) : Optional.empty();
    }

    public static Optional<String> ifPathExists(@Nullable String path) {
        return Check.pathExists(path) ? Optional.of(path) : Optional.empty();
    }

    public static Optional<Path> ifRegularFile(@Nullable Path file) {
        return Check.isRegularFile(file) ? Optional.of(file) : Optional.empty();
    }

    public static Optional<File> ifRegularFile(@Nullable File file) {
        return Check.isRegularFile(file) ? Optional.of(file) : Optional.empty();
    }

    public static Optional<String> ifRegularFile(@Nullable String filePath) {
        return Check.isRegularFile(filePath) ? Optional.of(filePath) : Optional.empty();
    }

    public static Optional<Path> ifDirectory(@Nullable Path directory) {
        return Check.isDirectory(directory) ? Optional.of(directory) : Optional.empty();
    }

    public static Optional<File> ifDirectory(@Nullable File directory) {
        return Check.isDirectory(directory) ? Optional.of(directory) : Optional.empty();
    }

    public static Optional<String> ifDirectory(@Nullable String directoryPath) {
        return Check.isDirectory(directoryPath) ? Optional.of(directoryPath) : Optional.empty();
    }

    public static Optional<ZonedDateTime> ifFuture(@Nullable ZonedDateTime dateTime) {
        return Check.isFuture(dateTime) ? Optional.of(dateTime) : Optional.empty();
    }

    public static Optional<OffsetDateTime> ifFuture(@Nullable OffsetDateTime dateTime) {
        return Check.isFuture(dateTime) ? Optional.of(dateTime) : Optional.empty();
    }

    public static Optional<LocalDateTime> ifFuture(@Nullable LocalDateTime dateTime) {
        return Check.isFuture(dateTime) ? Optional.of(dateTime) : Optional.empty();
    }

    public static Optional<LocalDate> ifFuture(@Nullable LocalDate date) {
        return Check.isFuture(date) ? Optional.of(date) : Optional.empty();
    }

    public static Optional<LocalTime> ifFuture(@Nullable LocalTime time) {
        return Check.isFuture(time) ? Optional.of(time) : Optional.empty();
    }

    public static Optional<ZonedDateTime> ifPast(@Nullable ZonedDateTime dateTime) {
        return Check.isPast(dateTime) ? Optional.of(dateTime) : Optional.empty();
    }

    public static Optional<OffsetDateTime> ifPast(@Nullable OffsetDateTime dateTime) {
        return Check.isPast(dateTime) ? Optional.of(dateTime) : Optional.empty();
    }

    public static Optional<LocalDateTime> ifPast(@Nullable LocalDateTime dateTime) {
        return Check.isPast(dateTime) ? Optional.of(dateTime) : Optional.empty();
    }

    public static Optional<LocalDate> ifPast(@Nullable LocalDate date) {
        return Check.isPast(date) ? Optional.of(date) : Optional.empty();
    }

    public static Optional<LocalTime> ifPast(@Nullable LocalTime time) {
        return Check.isPast(time) ? Optional.of(time) : Optional.empty();
    }

    public static <T> Optional<T> ifInstanceOf(@Nullable T object, @Nullable Class<?> classType) {
        return Check.isInstanceOf(object, classType) ? Optional.of(object) : Optional.empty();
    }

    public static <T extends CharSequence> Optional<T> ifContains(@Nullable T stringToCheck, @Nullable CharSequence contains) {
        return Check.contains(stringToCheck, contains) ? Optional.of(stringToCheck) : Optional.empty();
    }

    public static <T, C extends Collection<T>> Optional<C> ifContains(@Nullable C collectionToCheck, @Nullable T contains) {
        return Check.contains(collectionToCheck, contains) ? Optional.of(collectionToCheck) : Optional.empty();
    }

    public static <T> Optional<T[]> ifContains(@Nullable T[] arrayToCheck, @Nullable T contains) {
        return Check.contains(arrayToCheck, contains) ? Optional.of(arrayToCheck) : Optional.empty();
    }

    public static <T, C extends Iterable<T>> Optional<C> ifContains(@Nullable C iterableToCheck, @Nullable T contains) {
        return Check.contains(iterableToCheck, contains) ? Optional.of(iterableToCheck) : Optional.empty();
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
    public static <T, C extends Collection<T>> Optional<C> ifContainsElement(@Nullable C collectionToCheck, @Nullable Predicate<? super T> predicate) {
        return Check.containsElement(predicate, collectionToCheck) ? Optional.of(collectionToCheck) : Optional.empty();
    }

    public static <T> Optional<T[]> ifContainsElement(@Nullable T[] arrayToCheck, @Nullable Predicate<? super T> predicate) {
        return Check.containsElement(arrayToCheck, predicate) ? Optional.of(arrayToCheck) : Optional.empty();
    }

    public static <T, C extends Iterable<T>> Optional<C> ifContainsElement(@Nullable C iterableToCheck, @Nullable Predicate<? super T> predicate) {
        return Check.containsElement(predicate, iterableToCheck) ? Optional.of(iterableToCheck) : Optional.empty();
    }

    public static <T extends CharSequence> Optional<T> ifContainsRegex(@Nullable T stringToCheck, @Nullable Pattern regex) {
        return Check.containsRegex(stringToCheck, regex) ? Optional.of(stringToCheck) : Optional.empty();
    }

    public static <T extends CharSequence> Optional<T> ifContainsRegex(@Nullable T stringToCheck, @Nullable String regex) {
        return Check.containsRegex(stringToCheck, regex) ? Optional.of(stringToCheck) : Optional.empty();
    }

    public static <T extends CharSequence> Optional<T> matchesRegex(@Nullable T stringToCheck, @Nullable Pattern regex) {
        return Check.matchesRegex(stringToCheck, regex) ? Optional.of(stringToCheck) : Optional.empty();
    }

    public static <T extends CharSequence> Optional<T> matchesRegex(@Nullable T stringToCheck, @Nullable String regex) {
        return Check.matchesRegex(stringToCheck, regex) ? Optional.of(stringToCheck) : Optional.empty();
    }

    public static <T extends Map<?, ?>> Optional<T> ifContainsKey(@Nullable T mapToCheck, @Nullable Object key) {
        return Check.containsKey(mapToCheck, key) ? Optional.of(mapToCheck) : Optional.empty();
    }

    public static <T extends Map<?, ?>> Optional<T> ifContainsValue(@Nullable T mapToCheck, @Nullable Object value) {
        return Check.containsValue(mapToCheck, value) ? Optional.of(mapToCheck) : Optional.empty();
    }

    public static <T extends Collection<?>> Optional<T> ifContainsUniqueElements(@Nullable T collectionToCheck) {
        return Check.containsUniqueElements(collectionToCheck) ? Optional.of(collectionToCheck) : Optional.empty();
    }

    public static <T extends Collection<?>> Optional<T> ifContainsNull(@Nullable T collectionToCheck) {
        return Check.containsNull(collectionToCheck) ? Optional.of(collectionToCheck) : Optional.empty();
    }

    public static <T extends Iterable<?>> Optional<T> ifContainsNull(@Nullable T iterableToCheck) {
        return Check.containsNull(iterableToCheck) ? Optional.of(iterableToCheck) : Optional.empty();
    }

    public static <T> Optional<T[]> ifContainsNull(@Nullable T[] arrayToCheck) {
        return Check.containsNull(arrayToCheck) ? Optional.of(arrayToCheck) : Optional.empty();
    }

    public static <T extends Map<?, ?>> Optional<T> ifContainsNull(@Nullable T mapToCheck) {
        return Check.containsNull(mapToCheck) ? Optional.of(mapToCheck) : Optional.empty();
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
    public static <T extends CharSequence> Optional<T> ifNumbersOnly(@Nullable T str) {
        return Check.numbersOnly(str) ? Optional.ofNullable(str) : Optional.empty();
    }

    public static <T extends CharSequence> Optional<T> ifAlphasOnly(@Nullable T str) {
        return Check.alphasOnly(str) ? Optional.ofNullable(str) : Optional.empty();
    }

    public static <T extends CharSequence> Optional<T> ifAlphaNumericOnly(@Nullable T str) {
        return Check.alphaNumericOnly(str) ? Optional.ofNullable(str) : Optional.empty();
    }

    public static <T extends CharSequence> Optional<T> ifEmail(@Nullable T email) {
        return Check.isEmail(email) ? Optional.of(email) : Optional.empty();
    }

    /**
     * True if the value begins with {@code http://} or {@code https://} and has any sort of hostname or IP address.
     */
    public static <T extends CharSequence> Optional<T> ifUrl(@Nullable T url) {
        return Check.isUrl(url) ? Optional.of(url) : Optional.empty();
    }

    /**
     * True if this is a valid IPv4 or IPv6 address.
     * If you want to check for a specific version, use {@link #isIPv4Address(CharSequence)} or
     * {@link #isIPv6Address(CharSequence)}.
     */
    public static <T extends CharSequence> Optional<T> ifIpAddress(@Nullable T ipAddress) {
        return Check.isIpAddress(ipAddress) ? Optional.of(ipAddress) : Optional.empty();
    }

    public static <T extends CharSequence> Optional<T> ifIPv4Address(@Nullable T ip4Address) {
        return Check.isIPv4Address(ip4Address) ? Optional.of(ip4Address) : Optional.empty();
    }

    public static <T extends CharSequence> Optional<T> ifIPv6Address(@Nullable T ip6Address) {
        return Check.isIPv6Address(ip6Address) ? Optional.of(ip6Address) : Optional.empty();
    }

    /**
     * True if the value is not empty, and looks like JSON.
     * This means starting and ending with curly braces or square brackets.
     */
    public static <T extends CharSequence> Optional<T> ifJson(@Nullable T json) {
        return Check.isJson(json) ? Optional.of(json) : Optional.empty();
    }

    /**
     * True if the value is not empty and can be parsed as XML without errors.
     * Uses {@link DocumentBuilder} to parse the XML.
     */
    public static <T extends CharSequence> Optional<T> ifXml(@Nullable T xml) {
        return Check.isXml(xml) ? Optional.of(xml) : Optional.empty();
    }

    public static <T extends CharSequence> Optional<T> ifUUID(@Nullable T uuid) {
        return Check.isUUID(uuid) ? Optional.of(uuid) : Optional.empty();
    }
}
