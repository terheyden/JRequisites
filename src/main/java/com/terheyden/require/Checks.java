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
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

import org.jetbrains.annotations.Contract;

import static com.terheyden.require.RequireUtils.hasAtLeastSize;

/**
 * Methods in this class will never throw an exception, only return true or false.
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

    public static boolean isEmpty(@Nullable Iterable<?> iterable) {
        return isNull(iterable) || !iterable.iterator().hasNext();
    }

    public static boolean isEmpty(@Nullable Map<?, ?> map) {
        return isNull(map) || map.isEmpty();
    }

    public static boolean isEmpty(@Nullable Object[] array) {
        return isNull(array) || array.length == 0;
    }

    public static boolean isEmpty(@Nullable int[] array) {
        return isNull(array) || array.length == 0;
    }

    public static boolean isEmpty(@Nullable byte[] array) {
        return isNull(array) || array.length == 0;
    }

    public static boolean isEmpty(@Nullable short[] array) {
        return isNull(array) || array.length == 0;
    }

    public static boolean isEmpty(@Nullable long[] array) {
        return isNull(array) || array.length == 0;
    }

    public static boolean isEmpty(@Nullable float[] array) {
        return isNull(array) || array.length == 0;
    }

    public static boolean isEmpty(@Nullable double[] array) {
        return isNull(array) || array.length == 0;
    }

    public static boolean isEmpty(@Nullable boolean[] array) {
        return isNull(array) || array.length == 0;
    }

    public static boolean isEmpty(@Nullable char[] array) {
        return isNull(array) || array.length == 0;
    }

    /**
     * Require that a string argument is not null and not empty (zero length). For example:
     * <pre>
     * {@code
     *     notEmpty("cat"); // true
     *     notEmpty("");    // false
     *     notEmpty(null);  // false
     * }
     * </pre>
     *
     * @param str the string to check
     * @return the string, if it's not null, for chaining
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

    public static boolean notEmpty(@Nullable int[] array) {
        return notNull(array) && array.length > 0;
    }

    public static boolean notEmpty(@Nullable byte[] array) {
        return notNull(array) && array.length > 0;
    }

    public static boolean notEmpty(@Nullable short[] array) {
        return notNull(array) && array.length > 0;
    }

    public static boolean notEmpty(@Nullable long[] array) {
        return notNull(array) && array.length > 0;
    }

    public static boolean notEmpty(@Nullable float[] array) {
        return notNull(array) && array.length > 0;
    }

    public static boolean notEmpty(@Nullable double[] array) {
        return notNull(array) && array.length > 0;
    }

    public static boolean notEmpty(@Nullable boolean[] array) {
        return notNull(array) && array.length > 0;
    }

    public static boolean notEmpty(@Nullable char[] array) {
        return notNull(array) && array.length > 0;
    }

    public static boolean greaterThan(int lowValue, int valueToCheck) {
        return valueToCheck > lowValue;
    }

    public static boolean greaterThan(long lowValue, long valueToCheck) {
        return valueToCheck > lowValue;
    }

    public static boolean greaterThan(float lowValue, float valueToCheck) {
        return valueToCheck > lowValue;
    }

    public static boolean greaterThan(double lowValue, double valueToCheck) {
        return valueToCheck > lowValue;
    }

    public static boolean greaterThan(
        @Nullable Duration lowDurationLimit,
        @Nullable Duration durationToCheck) {

        return notNull(lowDurationLimit)
            && notNull(durationToCheck)
            && lowDurationLimit.compareTo(durationToCheck) < 0;
    }

    /**
     * True if the duration is greater than the specified nanoseconds.
     */
    public static boolean greaterThanNanos(long lowValueNs, @Nullable Duration durationToCheck) {
        return notNull(durationToCheck) && durationToCheck.toNanos() > lowValueNs;
    }

    /**
     * True if the duration is greater than the specified milliseconds.
     */
    public static boolean greaterThanMillis(long lowValueMs, @Nullable Duration durationToCheck) {
        return notNull(durationToCheck) && durationToCheck.toMillis() > lowValueMs;
    }

    /**
     * True if the duration is greater than the specified seconds.
     */
    public static boolean greaterThanSecs(long lowValueSecs, @Nullable Duration durationToCheck) {
        return notNull(durationToCheck) && durationToCheck.getSeconds() > lowValueSecs;
    }

    /**
     * True if the duration is greater than the specified minutes.
     */
    public static boolean greaterThanMins(long lowValueMins, @Nullable Duration durationToCheck) {
        return notNull(durationToCheck) && durationToCheck.toMinutes() > lowValueMins;
    }

    /**
     * True if the duration is greater than the specified hours.
     */
    public static boolean greaterThanHours(long lowValueHours, @Nullable Duration durationToCheck) {
        return notNull(durationToCheck) && durationToCheck.toHours() > lowValueHours;
    }

     /**
     * True if the duration is greater than the specified days.
     */
    public static boolean greaterThanDays(long lowValueDays, @Nullable Duration durationToCheck) {
        return notNull(durationToCheck) && durationToCheck.toDays() > lowValueDays;
    }

    public static boolean greaterOrEqualTo(int minValue, int valueToCheck) {
        return valueToCheck >= minValue;
    }

    public static boolean greaterOrEqualTo(long minValue, long valueToCheck) {
        return valueToCheck >= minValue;
    }

    public static boolean greaterOrEqualTo(float minValue, float valueToCheck) {
        return valueToCheck >= minValue;
    }

    public static boolean greaterOrEqualTo(double minValue, double valueToCheck) {
        return valueToCheck >= minValue;
    }

    public static boolean greaterOrEqualTo(
        @Nullable Duration minDuration,
        @Nullable Duration durationToCheck) {

        return notNull(minDuration)
            && notNull(durationToCheck)
            && minDuration.compareTo(durationToCheck) <= 0;
    }

    /**
     * True if the duration is greater than the specified nanoseconds.
     */
    public static boolean greaterOrEqualToNanos(long lowValueNs, @Nullable Duration durationToCheck) {
        return notNull(durationToCheck) && durationToCheck.toNanos() >= lowValueNs;
    }

    /**
     * True if the duration is greater than the specified milliseconds.
     */
    public static boolean greaterOrEqualToMillis(long lowValueMs, @Nullable Duration durationToCheck) {
        return notNull(durationToCheck) && durationToCheck.toMillis() >= lowValueMs;
    }

    /**
     * True if the duration is greater than the specified seconds.
     */
    public static boolean greaterOrEqualToSecs(long lowValueSecs, @Nullable Duration durationToCheck) {
        return notNull(durationToCheck) && durationToCheck.getSeconds() >= lowValueSecs;
    }

    /**
     * True if the duration is greater than the specified minutes.
     */
    public static boolean greaterOrEqualToMins(long lowValueMins, @Nullable Duration durationToCheck) {
        return notNull(durationToCheck) && durationToCheck.toMinutes() >= lowValueMins;
    }

    /**
     * True if the duration is greater than the specified hours.
     */
    public static boolean greaterOrEqualToHours(long lowValueHours, @Nullable Duration durationToCheck) {
        return notNull(durationToCheck) && durationToCheck.toHours() >= lowValueHours;
    }

     /**
     * True if the duration is greater than the specified days.
     */
    public static boolean greaterOrEqualToDays(long lowValueDays, @Nullable Duration durationToCheck) {
        return notNull(durationToCheck) && durationToCheck.toDays() >= lowValueDays;
    }

    public static boolean lessThan(int highValue, int valueToCheck) {
        return valueToCheck < highValue;
    }

    public static boolean lessThan(long highValue, long valueToCheck) {
        return valueToCheck < highValue;
    }

    public static boolean lessThan(float highValue, float valueToCheck) {
        return valueToCheck < highValue;
    }

    public static boolean lessThan(double highValue, double valueToCheck) {
        return valueToCheck < highValue;
    }

    public static boolean lessThan(
        @Nullable Duration highDurationLimit,
        @Nullable Duration durationToCheck) {

        return notNull(highDurationLimit)
            && notNull(durationToCheck)
            && highDurationLimit.compareTo(durationToCheck) > 0;
    }

    /**
     * True if the duration is less than the specified nanoseconds.
     */
    public static boolean lessThanNanos(long highValueNs, @Nullable Duration durationToCheck) {
        return notNull(durationToCheck) && durationToCheck.toNanos() < highValueNs;
    }

    /**
     * True if the duration is less than the specified milliseconds.
     */
    public static boolean lessThanMillis(long highValueMs, @Nullable Duration durationToCheck) {
        return notNull(durationToCheck) && durationToCheck.toMillis() < highValueMs;
    }

    /**
     * True if the duration is less than the specified seconds.
     */
    public static boolean lessThanSecs(long highValueSecs, @Nullable Duration durationToCheck) {
        return notNull(durationToCheck) && durationToCheck.getSeconds() < highValueSecs;
    }

    /**
     * True if the duration is less than the specified minutes.
     */
    public static boolean lessThanMins(long highValueMins, @Nullable Duration durationToCheck) {
        return notNull(durationToCheck) && durationToCheck.toMinutes() < highValueMins;
    }

     /**
     * True if the duration is less than the specified hours.
     */
    public static boolean lessThanHours(long highValueHours, @Nullable Duration durationToCheck) {
        return notNull(durationToCheck) && durationToCheck.toHours() < highValueHours;
    }

      /**
     * True if the duration is less than the specified days.
     */
    public static boolean lessThanDays(long highValueDays, @Nullable Duration durationToCheck) {
        return notNull(durationToCheck) && durationToCheck.toDays() < highValueDays;
    }

    public static boolean lessOrEqualTo(int maxValue, int valueToCheck) {
        return valueToCheck <= maxValue;
    }

    public static boolean lessOrEqualTo(long maxValue, long valueToCheck) {
        return valueToCheck <= maxValue;
    }

    public static boolean lessOrEqualTo(float maxValue, float valueToCheck) {
        return valueToCheck <= maxValue;
    }

    public static boolean lessOrEqualTo(double maxValue, double valueToCheck) {
        return valueToCheck <= maxValue;
    }

    public static boolean lessOrEqualTo(
        @Nullable Duration maxDuration,
        @Nullable Duration durationToCheck) {

        return notNull(maxDuration)
            && notNull(durationToCheck)
            && maxDuration.compareTo(durationToCheck) >= 0;
    }

    /**
     * True if the duration is less than the specified nanoseconds.
     */
    public static boolean lessOrEqualToNanos(long maxNanos, @Nullable Duration durationToCheck) {
        return notNull(durationToCheck) && durationToCheck.toNanos() <= maxNanos;
    }

    /**
     * True if the duration is less than the specified milliseconds.
     */
    public static boolean lessOrEqualToMillis(long maxMillis, @Nullable Duration durationToCheck) {
        return notNull(durationToCheck) && durationToCheck.toMillis() <= maxMillis;
    }

    /**
     * True if the duration is less than the specified seconds.
     */
    public static boolean lessOrEqualToSecs(long maxSecs, @Nullable Duration durationToCheck) {
        return notNull(durationToCheck) && durationToCheck.getSeconds() <= maxSecs;
    }

    /**
     * True if the duration is less than the specified minutes.
     */
    public static boolean lessOrEqualToMins(long maxMins, @Nullable Duration durationToCheck) {
        return notNull(durationToCheck) && durationToCheck.toMinutes() <= maxMins;
    }

    /**
     * True if the duration is less than the specified hours.
     */
    public static boolean lessOrEqualToHours(long maxHours, @Nullable Duration durationToCheck) {
        return notNull(durationToCheck) && durationToCheck.toHours() <= maxHours;
    }

    /**
     * True if the duration is less than the specified days.
     */
    public static boolean lessOrEqualToDays(long maxDays, @Nullable Duration durationToCheck) {
        return notNull(durationToCheck) && durationToCheck.toDays() <= maxDays;
    }

    public static boolean isBetween(int minValueInclusive, int highValueInclusive, int valueToCheck) {
        return greaterOrEqualTo(minValueInclusive, valueToCheck) && lessOrEqualTo(highValueInclusive, valueToCheck);
    }

    public static boolean isBetween(long minValueInclusive, long highValueInclusive, long valueToCheck) {
        return greaterOrEqualTo(minValueInclusive, valueToCheck) && lessOrEqualTo(highValueInclusive, valueToCheck);
    }

    public static boolean isBetween(float minValueInclusive, float highValueInclusive, float valueToCheck) {
        return greaterOrEqualTo(minValueInclusive, valueToCheck) && lessOrEqualTo(highValueInclusive, valueToCheck);
    }

    public static boolean isBetween(double minValueInclusive, double highValueInclusive, double valueToCheck) {
        return greaterOrEqualTo(minValueInclusive, valueToCheck) && lessOrEqualTo(highValueInclusive, valueToCheck);
    }

    public static boolean isBetween(
        @Nullable Duration minDurationInclusive,
        @Nullable Duration maxDurationInclusive,
        @Nullable Duration durationToCheck) {

        return notNull(minDurationInclusive)
            && notNull(maxDurationInclusive)
            && notNull(durationToCheck)
            && minDurationInclusive.compareTo(durationToCheck) <= 0
            && maxDurationInclusive.compareTo(durationToCheck) >= 0;
    }

    /**
     * True if the duration is between the specified nanoseconds.
     */
    public static boolean isBetweenNanos(
        long minNanosInclusive,
        long maxNanosInclusive,
        @Nullable Duration durationToCheck) {

        return notNull(durationToCheck)
            && durationToCheck.toNanos() >= minNanosInclusive
            && durationToCheck.toNanos() <= maxNanosInclusive;
    }

    /**
     * True if the duration is between the specified milliseconds.
     */
    public static boolean isBetweenMillis(
        long minMillisInclusive,
        long maxMillisInclusive,
        @Nullable Duration durationToCheck) {

        return notNull(durationToCheck)
            && durationToCheck.toMillis() >= minMillisInclusive
            && durationToCheck.toMillis() <= maxMillisInclusive;
    }

    /**
     * True if the duration is between the specified seconds.
     */
    public static boolean isBetweenSecs(
        long minSecsInclusive,
        long maxSecsInclusive,
        @Nullable Duration durationToCheck) {

        return notNull(durationToCheck)
            && durationToCheck.getSeconds() >= minSecsInclusive
            && durationToCheck.getSeconds() <= maxSecsInclusive;
    }

    /**
     * True if the duration is between the specified minutes.
     */
    public static boolean isBetweenMins(
        long minimumMinsInclusive,
        long maximumMinsInclusive,
        @Nullable Duration durationToCheck) {

        return notNull(durationToCheck)
            && durationToCheck.toMinutes() >= minimumMinsInclusive
            && durationToCheck.toMinutes() <= maximumMinsInclusive;
    }

    /**
     * True if the duration is between the specified hours.
     */
    public static boolean isBetweenHours(
        long minHoursInclusive,
        long maxHoursInclusive,
        @Nullable Duration durationToCheck) {

        return notNull(durationToCheck)
            && durationToCheck.toHours() >= minHoursInclusive
            && durationToCheck.toHours() <= maxHoursInclusive;
    }

    /**
     * True if the duration is between the specified days.
     */
    public static boolean isBetweenDays(
        long minDaysInclusive,
        long maxDaysInclusive,
        @Nullable Duration durationToCheck) {

        return notNull(durationToCheck)
            && durationToCheck.toDays() >= minDaysInclusive
            && durationToCheck.toDays() <= maxDaysInclusive;
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
        return notNull(str) && str.toString().trim().length() > 0;
    }

    /**
     * True if the string contains only numeric digits. Examples:
     * <pre>
     * {@code
     *    digitsOnly(null); // false
     *    digitsOnly("");   // false
     *    digitsOnly("  123"); // false
     *    digitsOnly("hi123"); // false
     *    digitsOnly("123"); // true
     *    digitsOnly("123.0"); // false
     *    digitsOnly("123,0"); // false
     * }
     * </pre>
     */
    @Contract("null -> false")
    public static boolean digitsOnly(@Nullable CharSequence str) {

        if (isNull(str)) {
            return false;
        }

        return IntStream.range(0, str.length()).allMatch(i -> Character.isDigit(str.charAt(i)));
    }

    @Contract("null -> false")
    public static boolean alphaOnly(@Nullable CharSequence str) {

        if (isNull(str)) {
            return false;
        }

        return IntStream.range(0, str.length()).allMatch(i -> Character.isLetter(str.charAt(i)));
    }

    @Contract("null -> false")
    public static boolean alphaNumericOnly(@Nullable CharSequence str) {

        if (isNull(str)) {
            return false;
        }

        return IntStream.range(0, str.length()).allMatch(i -> Character.isLetterOrDigit(str.charAt(i)));
    }

    public static boolean isLength(int length, @Nullable CharSequence str) {
        return notNull(str) && str.length() == length;
    }

    public static boolean isLength(int length, @Nullable Object[] array) {
        return notNull(array) && array.length == length;
    }

    public static boolean isLength(int length, @Nullable int[] array) {
        return notNull(array) && array.length == length;
    }

    public static boolean isLength(int length, @Nullable byte[] array) {
        return notNull(array) && array.length == length;
    }

    public static boolean isLength(int length, @Nullable short[] array) {
        return notNull(array) && array.length == length;
    }

    public static boolean isLength(int length, @Nullable long[] array) {
        return notNull(array) && array.length == length;
    }

    public static boolean isLength(int length, @Nullable float[] array) {
        return notNull(array) && array.length == length;
    }

    public static boolean isLength(int length, @Nullable double[] array) {
        return notNull(array) && array.length == length;
    }

    public static boolean isLength(int length, @Nullable boolean[] array) {
        return notNull(array) && array.length == length;
    }

    public static boolean isLength(int length, @Nullable char[] array) {
        return notNull(array) && array.length == length;
    }

    public static boolean isLengthGreaterThan(int lowValue, @Nullable CharSequence str) {
        return notNull(str) && str.length() > lowValue;
    }

    public static boolean isLengthGreaterThan(int lowValue, @Nullable Object[] array) {
        return notNull(array) && array.length > lowValue;
    }

    public static boolean isLengthGreaterThan(int lowValue, @Nullable int[] array) {
        return notNull(array) && array.length > lowValue;
    }

    public static boolean isLengthGreaterThan(int lowValue, @Nullable byte[] array) {
        return notNull(array) && array.length > lowValue;
    }

    public static boolean isLengthGreaterThan(int lowValue, @Nullable short[] array) {
        return notNull(array) && array.length > lowValue;
    }

    public static boolean isLengthGreaterThan(int lowValue, @Nullable long[] array) {
        return notNull(array) && array.length > lowValue;
    }

    public static boolean isLengthGreaterThan(int lowValue, @Nullable float[] array) {
        return notNull(array) && array.length > lowValue;
    }

    public static boolean isLengthGreaterThan(int lowValue, @Nullable double[] array) {
        return notNull(array) && array.length > lowValue;
    }

    public static boolean isLengthGreaterThan(int lowValue, @Nullable boolean[] array) {
        return notNull(array) && array.length > lowValue;
    }

    public static boolean isLengthGreaterThan(int lowValue, @Nullable char[] array) {
        return notNull(array) && array.length > lowValue;
    }

    public static boolean isLengthGreaterOrEqualTo(int minLength, @Nullable CharSequence str) {
        return notNull(str) && str.length() >= minLength;
    }

    public static boolean isLengthGreaterOrEqualTo(int minLength, @Nullable Object[] array) {
        return notNull(array) && array.length >= minLength;
    }

    public static boolean isLengthGreaterOrEqualTo(int minLength, @Nullable int[] array) {
        return notNull(array) && array.length >= minLength;
    }

    public static boolean isLengthGreaterOrEqualTo(int minLength, @Nullable byte[] array) {
        return notNull(array) && array.length >= minLength;
    }

    public static boolean isLengthGreaterOrEqualTo(int minLength, @Nullable short[] array) {
        return notNull(array) && array.length >= minLength;
    }

    public static boolean isLengthGreaterOrEqualTo(int minLength, @Nullable long[] array) {
        return notNull(array) && array.length >= minLength;
    }

    public static boolean isLengthGreaterOrEqualTo(int minLength, @Nullable float[] array) {
        return notNull(array) && array.length >= minLength;
    }

    public static boolean isLengthGreaterOrEqualTo(int minLength, @Nullable double[] array) {
        return notNull(array) && array.length >= minLength;
    }

    public static boolean isLengthGreaterOrEqualTo(int minLength, @Nullable boolean[] array) {
        return notNull(array) && array.length >= minLength;
    }

    public static boolean isLengthGreaterOrEqualTo(int minLength, @Nullable char[] array) {
        return notNull(array) && array.length >= minLength;
    }

    public static boolean isLengthLessThan(int highValue, @Nullable CharSequence str) {
        return notNull(str) && str.length() < highValue;
    }

    public static boolean isLengthLessThan(int highValue, @Nullable Object[] array) {
        return notNull(array) && array.length < highValue;
    }

    public static boolean isLengthLessThan(int highValue, @Nullable int[] array) {
        return notNull(array) && array.length < highValue;
    }

    public static boolean isLengthLessThan(int highValue, @Nullable byte[] array) {
        return notNull(array) && array.length < highValue;
    }

    public static boolean isLengthLessThan(int highValue, @Nullable short[] array) {
        return notNull(array) && array.length < highValue;
    }

    public static boolean isLengthLessThan(int highValue, @Nullable long[] array) {
        return notNull(array) && array.length < highValue;
    }

    public static boolean isLengthLessThan(int highValue, @Nullable float[] array) {
        return notNull(array) && array.length < highValue;
    }

    public static boolean isLengthLessThan(int highValue, @Nullable double[] array) {
        return notNull(array) && array.length < highValue;
    }

    public static boolean isLengthLessThan(int highValue, @Nullable boolean[] array) {
        return notNull(array) && array.length < highValue;
    }

    public static boolean isLengthLessThan(int highValue, @Nullable char[] array) {
        return notNull(array) && array.length < highValue;
    }

    public static boolean isLengthLessOrEqualTo(int maxLength, @Nullable CharSequence str) {
        return notNull(str) && str.length() <= maxLength;
    }

    public static boolean isLengthLessOrEqualTo(int maxLength, @Nullable Object[] array) {
        return notNull(array) && array.length <= maxLength;
    }

    public static boolean isLengthLessOrEqualTo(int maxLength, @Nullable int[] array) {
        return notNull(array) && array.length <= maxLength;
    }

    public static boolean isLengthLessOrEqualTo(int maxLength, @Nullable byte[] array) {
        return notNull(array) && array.length <= maxLength;
    }

    public static boolean isLengthLessOrEqualTo(int maxLength, @Nullable short[] array) {
        return notNull(array) && array.length <= maxLength;
    }

    public static boolean isLengthLessOrEqualTo(int maxLength, @Nullable long[] array) {
        return notNull(array) && array.length <= maxLength;
    }

    public static boolean isLengthLessOrEqualTo(int maxLength, @Nullable float[] array) {
        return notNull(array) && array.length <= maxLength;
    }

    public static boolean isLengthLessOrEqualTo(int maxLength, @Nullable double[] array) {
        return notNull(array) && array.length <= maxLength;
    }

    public static boolean isLengthLessOrEqualTo(int maxLength, @Nullable boolean[] array) {
        return notNull(array) && array.length <= maxLength;
    }

    public static boolean isLengthLessOrEqualTo(int maxLength, @Nullable char[] array) {
        return notNull(array) && array.length <= maxLength;
    }

    public static boolean isLengthBetween(int minLengthInclusive, int maxLengthInclusive, @Nullable CharSequence str) {
        return notNull(str) && str.length() >= minLengthInclusive && str.length() <= maxLengthInclusive;
    }

    public static boolean isLengthBetween(int minLengthInclusive, int maxLengthInclusive, @Nullable Object[] array) {
        return notNull(array) && array.length >= minLengthInclusive && array.length <= maxLengthInclusive;
    }

    public static boolean isLengthBetween(int minLengthInclusive, int maxLengthInclusive, @Nullable int[] array) {
        return notNull(array) && array.length >= minLengthInclusive && array.length <= maxLengthInclusive;
    }

    public static boolean isLengthBetween(int minLengthInclusive, int maxLengthInclusive, @Nullable byte[] array) {
        return notNull(array) && array.length >= minLengthInclusive && array.length <= maxLengthInclusive;
    }

    public static boolean isLengthBetween(int minLengthInclusive, int maxLengthInclusive, @Nullable short[] array) {
        return notNull(array) && array.length >= minLengthInclusive && array.length <= maxLengthInclusive;
    }

    public static boolean isLengthBetween(int minLengthInclusive, int maxLengthInclusive, @Nullable long[] array) {
        return notNull(array) && array.length >= minLengthInclusive && array.length <= maxLengthInclusive;
    }

    public static boolean isLengthBetween(int minLengthInclusive, int maxLengthInclusive, @Nullable float[] array) {
        return notNull(array) && array.length >= minLengthInclusive && array.length <= maxLengthInclusive;
    }

    public static boolean isLengthBetween(int minLengthInclusive, int maxLengthInclusive, @Nullable double[] array) {
        return notNull(array) && array.length >= minLengthInclusive && array.length <= maxLengthInclusive;
    }

    public static boolean isLengthBetween(int minLengthInclusive, int maxLengthInclusive, @Nullable boolean[] array) {
        return notNull(array) && array.length >= minLengthInclusive && array.length <= maxLengthInclusive;
    }

    public static boolean isLengthBetween(int minLengthInclusive, int maxLengthInclusive, @Nullable char[] array) {
        return notNull(array) && array.length >= minLengthInclusive && array.length <= maxLengthInclusive;
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

    public static boolean isSizeBetween(int minSizeInclusive, int maxSizeInclusive, @Nullable Collection<?> collection) {
        return notNull(collection) && collection.size() >= minSizeInclusive && collection.size() <= maxSizeInclusive;
    }

    public static boolean isSizeBetween(int minSizeInclusive, int maxSizeInclusive, @Nullable Iterable<?> iterable) {

        if (iterable == null) {
            return false;
        }

        Optional<? extends Iterator<?>> optIterator = hasAtLeastSize(iterable, minSizeInclusive);
        if (!optIterator.isPresent()) {
            // Too short.
            return false;
        }

        Iterator<?> iterator = optIterator.get();

        for (int length = minSizeInclusive + 1; length <= maxSizeInclusive; length++) {

            if (!iterator.hasNext()) {
                // Greater than min but less than max, that's a match.
                return true;
            }

            iterator.next();
        }

        return !iterator.hasNext();
    }

    public static boolean isSizeBetween(int minSizeInclusive, int maxSizeInclusive, @Nullable Map<?, ?> map) {
        return notNull(map) && map.size() >= minSizeInclusive && map.size() <= maxSizeInclusive;
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
     * Since nulls cannot be checked and are considered an invalid state,
     * this method returns false if the path is null.
     */
    public static boolean pathNotExists(@Nullable Path path) {
        // Remember that !Files.exists(path) is not the same as Files.notExists(path).
        return notNull(path) && Files.notExists(path);
    }

    /**
     * Since nulls cannot be checked and are considered an invalid state,
     * this method returns false if the path is null.
     */
    public static boolean pathNotExists(@Nullable File path) {
        return notNull(path) && !path.exists();
    }

    /**
     * Since nulls cannot be checked and are considered an invalid state,
     * this method returns false if the path is null.
     */
    public static boolean pathNotExists(@Nullable String path) {

        return RequireUtils
            .pathGetOptional(path)
            .map(Checks::pathNotExists)
            .orElse(false);
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

    public static boolean isInstanceOf(@Nullable Class<?> classType, @Nullable Object object) {
        return notNull(classType) && notNull(object) && classType.isInstance(object);
    }

    public static boolean notInstanceOf(@Nullable Class<?> classType, @Nullable Object object) {
        return notNull(classType) && notNull(object) && !classType.isInstance(object);
    }

    public static boolean contains(@Nullable String contains, @Nullable CharSequence stringToCheck) {
        return notNull(contains) && notNull(stringToCheck) && stringToCheck.toString().contains(contains);
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

        if (isNull(collectionToCheck)) {
            return false;
        }

        if (collectionToCheck.isEmpty()) {
            // Empty collections do not contain nulls.
            return true;
        }

        try {
            // contains(null) will throw if the collection doesn't support nulls.
            return !collectionToCheck.contains(null);
        } catch (ClassCastException | NullPointerException ignore) {
            // The collection does not support nulls.
            return true;
        }
    }

    public static boolean notContainsNull(@Nullable Iterable<?> iterableToCheck) {

        if (isNull(iterableToCheck)) {
            return false;
        }

        if (iterableToCheck instanceof Collection) {
            return notContainsNull((Collection<?>) iterableToCheck);
        }

        for (Object element : iterableToCheck) {
            if (isNull(element)) {
                return false;
            }
        }

        return true;
    }

    /**
     * True if the map does not contain null keys or values.
     * @throws IllegalStateException if the map does not support null keys or values
     */
    public static boolean notContainsNull(@Nullable Map<?, ?> mapToCheck) {
        return notNull(mapToCheck) && notContainsNull(mapToCheck.keySet()) && notContainsNull(mapToCheck.values());
    }

    public static boolean notContainsNull(@Nullable Object[] arrayToCheck) {
        if (isNull(arrayToCheck)) return false;
        for (Object element : arrayToCheck) if (isNull(element)) return false;
        return true;
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

    public static boolean isUUID(@Nullable CharSequence uuid) {
        return notNull(uuid) && UUID_PATTERN.matcher(uuid).matches();
    }
}
