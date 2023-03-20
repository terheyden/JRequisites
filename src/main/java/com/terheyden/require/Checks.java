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
     *     isNotNull("");   // true
     *     isNotNull(null); // false
     * }
     * </pre>
     */
    public static boolean isNotNull(@Nullable Object obj) {
        return obj != null;
    }

    /**
     * Checks that the given string is not null but is empty (has length of 0). Examples:
     * <pre>
     * {@code
     *     isEmpty("");    // true
     *     isEmpty("  ");  // false
     *     isEmpty(null);  // false
     * }
     * </pre>
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

    /**
     * Checks that the given string is null or empty (has length of 0). Examples:
     * <pre>
     * {@code
     *     isNullOrEmpty(null);  // true
     *     isNullOrEmpty("");    // true
     *     isNullOrEmpty("  ");  // false
     * }
     * </pre>
     */
    @Contract("null -> true")
    public static boolean isNullOrEmpty(@Nullable CharSequence str) {
        return isNull(str) || isEmpty(str);
    }

    public static boolean isNullOrEmpty(@Nullable Collection<?> collection) {
        return isNull(collection) || isEmpty(collection);
    }

    public static boolean isNullOrEmpty(@Nullable Iterable<?> iterable) {
        return isNull(iterable) || isEmpty(iterable);
    }

    public static boolean isNullOrEmpty(@Nullable Map<?, ?> map) {
        return isNull(map) || isEmpty(map);
    }

    public static boolean isNullOrEmpty(@Nullable Object[] array) {
        return isNull(array) || isEmpty(array);
    }

    public static boolean isNullOrEmpty(@Nullable int[] array) {
        return isNull(array) || isEmpty(array);
    }

    public static boolean isNullOrEmpty(@Nullable byte[] array) {
        return isNull(array) || isEmpty(array);
    }

    public static boolean isNullOrEmpty(@Nullable short[] array) {
        return isNull(array) || isEmpty(array);
    }

    public static boolean isNullOrEmpty(@Nullable long[] array) {
        return isNull(array) || isEmpty(array);
    }

    public static boolean isNullOrEmpty(@Nullable float[] array) {
        return isNull(array) || isEmpty(array);
    }

    public static boolean isNullOrEmpty(@Nullable double[] array) {
        return isNull(array) || isEmpty(array);
    }

    public static boolean isNullOrEmpty(@Nullable boolean[] array) {
        return isNull(array) || isEmpty(array);
    }

    public static boolean isNullOrEmpty(@Nullable char[] array) {
        return isNull(array) || isEmpty(array);
    }

    public static boolean isGreaterThan(int lowValue, int valueToCheck) {
        return valueToCheck > lowValue;
    }

    public static boolean isGreaterThan(long lowValue, long valueToCheck) {
        return valueToCheck > lowValue;
    }

    public static boolean isGreaterThan(float lowValue, float valueToCheck) {
        return valueToCheck > lowValue;
    }

    public static boolean isGreaterThan(double lowValue, double valueToCheck) {
        return valueToCheck > lowValue;
    }

    public static boolean isGreaterThan(
        @Nullable Duration lowDurationLimit,
        @Nullable Duration durationToCheck) {

        return isNotNull(lowDurationLimit)
            && isNotNull(durationToCheck)
            && lowDurationLimit.compareTo(durationToCheck) < 0;
    }

    public static boolean isGreaterOrEqualTo(int minValue, int valueToCheck) {
        return valueToCheck >= minValue;
    }

    public static boolean isGreaterOrEqualTo(long minValue, long valueToCheck) {
        return valueToCheck >= minValue;
    }

    public static boolean isGreaterOrEqualTo(float minValue, float valueToCheck) {
        return valueToCheck >= minValue;
    }

    public static boolean isGreaterOrEqualTo(double minValue, double valueToCheck) {
        return valueToCheck >= minValue;
    }

    public static boolean isGreaterOrEqualTo(
        @Nullable Duration minDuration,
        @Nullable Duration durationToCheck) {

        return isNotNull(minDuration)
            && isNotNull(durationToCheck)
            && minDuration.compareTo(durationToCheck) <= 0;
    }

    public static boolean isLessThan(int highValue, int valueToCheck) {
        return valueToCheck < highValue;
    }

    public static boolean isLessThan(long highValue, long valueToCheck) {
        return valueToCheck < highValue;
    }

    public static boolean isLessThan(float highValue, float valueToCheck) {
        return valueToCheck < highValue;
    }

    public static boolean isLessThan(double highValue, double valueToCheck) {
        return valueToCheck < highValue;
    }

    public static boolean isLessThan(
        @Nullable Duration highDurationLimit,
        @Nullable Duration durationToCheck) {

        return isNotNull(highDurationLimit)
            && isNotNull(durationToCheck)
            && highDurationLimit.compareTo(durationToCheck) > 0;
    }

    public static boolean isLessOrEqualTo(int maxValue, int valueToCheck) {
        return valueToCheck <= maxValue;
    }

    public static boolean isLessOrEqualTo(long maxValue, long valueToCheck) {
        return valueToCheck <= maxValue;
    }

    public static boolean isLessOrEqualTo(float maxValue, float valueToCheck) {
        return valueToCheck <= maxValue;
    }

    public static boolean isLessOrEqualTo(double maxValue, double valueToCheck) {
        return valueToCheck <= maxValue;
    }

    public static boolean isLessOrEqualTo(
        @Nullable Duration maxDuration,
        @Nullable Duration durationToCheck) {

        return isNotNull(maxDuration)
            && isNotNull(durationToCheck)
            && maxDuration.compareTo(durationToCheck) >= 0;
    }

    public static boolean isBetween(int lowValueInclusive, int highValueInclusive, int valueToCheck) {
        return isGreaterOrEqualTo(lowValueInclusive, valueToCheck) && isLessOrEqualTo(highValueInclusive, valueToCheck);
    }

    public static boolean isBetween(long lowValueInclusive, long highValueInclusive, long valueToCheck) {
        return isGreaterOrEqualTo(lowValueInclusive, valueToCheck) && isLessOrEqualTo(highValueInclusive, valueToCheck);
    }

    public static boolean isBetween(float lowValueInclusive, float highValueInclusive, float valueToCheck) {
        return isGreaterOrEqualTo(lowValueInclusive, valueToCheck) && isLessOrEqualTo(highValueInclusive, valueToCheck);
    }

    public static boolean isBetween(double lowValueInclusive, double highValueInclusive, double valueToCheck) {
        return isGreaterOrEqualTo(lowValueInclusive, valueToCheck) && isLessOrEqualTo(highValueInclusive, valueToCheck);
    }

    public static boolean isBetween(
        @Nullable Duration minDurationInclusive,
        @Nullable Duration maxDurationInclusive,
        @Nullable Duration durationToCheck) {

        return isNotNull(minDurationInclusive)
            && isNotNull(maxDurationInclusive)
            && isNotNull(durationToCheck)
            && minDurationInclusive.compareTo(durationToCheck) <= 0
            && maxDurationInclusive.compareTo(durationToCheck) >= 0;
    }

    /**
     * Check that the given string is not null, but is blank (empty or containing only whitespace). Examples:
     * <pre>
     * {@code
     *     isBlank(null); // false, it cannot be blank if it is null
     *     isBlank("");   // true
     *     isBlank("  "); // true
     * }
     * </pre>
     */
    public static boolean isBlank(@Nullable CharSequence str) {
        return isNotNull(str) && str.toString().trim().length() == 0;
    }

    /**
     * Check that the given string is not null, not empty, and doesn't contain only whitespace. Examples:
     * <pre>
     * {@code
     *     isNotBlank(null); // false
     *     isNotBlank("");   // false
     *     isNotBlank("  "); // false
     *     isNotBlank("hi"); // true
     * }
     * </pre>
     */
    public static boolean isNotBlank(@Nullable CharSequence str) {
        return isNotNull(str) && str.toString().trim().length() > 0;
    }

    /**
     * True if the given string is null or empty or contains only whitespace. Examples:
     * <pre>
     * {@code
     *     isNullOrBlank(null); // true
     *     isNullOrBlank("");   // true
     *     isNullOrBlank("  "); // true
     *     isNullOrBlank("hi"); // false
     * }
     * </pre>
     */
    public static boolean isNullOrBlank(@Nullable CharSequence str) {
        return isNull(str) || isBlank(str);
    }

    public static boolean isDigitsOnly(@Nullable CharSequence str) {

        if (isNull(str)) {
            return false;
        }

        return IntStream.range(0, str.length()).allMatch(i -> Character.isDigit(str.charAt(i)));
    }

    public static boolean isAlphaOnly(@Nullable CharSequence str) {

        if (isNull(str)) {
            return false;
        }

        return IntStream.range(0, str.length()).allMatch(i -> Character.isLetter(str.charAt(i)));
    }

    public static boolean isAlphaNumericOnly(@Nullable CharSequence str) {

        if (isNull(str)) {
            return false;
        }

        return IntStream.range(0, str.length()).allMatch(i -> Character.isLetterOrDigit(str.charAt(i)));
    }

    public static boolean isLength(int length, @Nullable CharSequence str) {
        return isNotNull(str) && str.length() == length;
    }

    public static boolean isLength(int length, @Nullable Object[] array) {
        return isNotNull(array) && array.length == length;
    }

    public static boolean isLength(int length, @Nullable int[] array) {
        return isNotNull(array) && array.length == length;
    }

    public static boolean isLength(int length, @Nullable byte[] array) {
        return isNotNull(array) && array.length == length;
    }

    public static boolean isLength(int length, @Nullable short[] array) {
        return isNotNull(array) && array.length == length;
    }

    public static boolean isLength(int length, @Nullable long[] array) {
        return isNotNull(array) && array.length == length;
    }

    public static boolean isLength(int length, @Nullable float[] array) {
        return isNotNull(array) && array.length == length;
    }

    public static boolean isLength(int length, @Nullable double[] array) {
        return isNotNull(array) && array.length == length;
    }

    public static boolean isLength(int length, @Nullable boolean[] array) {
        return isNotNull(array) && array.length == length;
    }

    public static boolean isLength(int length, @Nullable char[] array) {
        return isNotNull(array) && array.length == length;
    }

    public static boolean isLengthGreaterThan(int lowValue, @Nullable CharSequence str) {
        return isNotNull(str) && str.length() > lowValue;
    }

    public static boolean isLengthGreaterThan(int lowValue, @Nullable Object[] array) {
        return isNotNull(array) && array.length > lowValue;
    }

    public static boolean isLengthGreaterThan(int lowValue, @Nullable int[] array) {
        return isNotNull(array) && array.length > lowValue;
    }

    public static boolean isLengthGreaterThan(int lowValue, @Nullable byte[] array) {
        return isNotNull(array) && array.length > lowValue;
    }

    public static boolean isLengthGreaterThan(int lowValue, @Nullable short[] array) {
        return isNotNull(array) && array.length > lowValue;
    }

    public static boolean isLengthGreaterThan(int lowValue, @Nullable long[] array) {
        return isNotNull(array) && array.length > lowValue;
    }

    public static boolean isLengthGreaterThan(int lowValue, @Nullable float[] array) {
        return isNotNull(array) && array.length > lowValue;
    }

    public static boolean isLengthGreaterThan(int lowValue, @Nullable double[] array) {
        return isNotNull(array) && array.length > lowValue;
    }

    public static boolean isLengthGreaterThan(int lowValue, @Nullable boolean[] array) {
        return isNotNull(array) && array.length > lowValue;
    }

    public static boolean isLengthGreaterThan(int lowValue, @Nullable char[] array) {
        return isNotNull(array) && array.length > lowValue;
    }

    public static boolean isLengthGreaterOrEqualTo(int minLength, @Nullable CharSequence str) {
        return isNotNull(str) && str.length() >= minLength;
    }

    public static boolean isLengthGreaterOrEqualTo(int minLength, @Nullable Object[] array) {
        return isNotNull(array) && array.length >= minLength;
    }

    public static boolean isLengthGreaterOrEqualTo(int minLength, @Nullable int[] array) {
        return isNotNull(array) && array.length >= minLength;
    }

    public static boolean isLengthGreaterOrEqualTo(int minLength, @Nullable byte[] array) {
        return isNotNull(array) && array.length >= minLength;
    }

    public static boolean isLengthGreaterOrEqualTo(int minLength, @Nullable short[] array) {
        return isNotNull(array) && array.length >= minLength;
    }

    public static boolean isLengthGreaterOrEqualTo(int minLength, @Nullable long[] array) {
        return isNotNull(array) && array.length >= minLength;
    }

    public static boolean isLengthGreaterOrEqualTo(int minLength, @Nullable float[] array) {
        return isNotNull(array) && array.length >= minLength;
    }

    public static boolean isLengthGreaterOrEqualTo(int minLength, @Nullable double[] array) {
        return isNotNull(array) && array.length >= minLength;
    }

    public static boolean isLengthGreaterOrEqualTo(int minLength, @Nullable boolean[] array) {
        return isNotNull(array) && array.length >= minLength;
    }

    public static boolean isLengthGreaterOrEqualTo(int minLength, @Nullable char[] array) {
        return isNotNull(array) && array.length >= minLength;
    }

    public static boolean isLengthLessThan(int highValue, @Nullable CharSequence str) {
        return isNotNull(str) && str.length() < highValue;
    }

    public static boolean isLengthLessThan(int highValue, @Nullable Object[] array) {
        return isNotNull(array) && array.length < highValue;
    }

    public static boolean isLengthLessThan(int highValue, @Nullable int[] array) {
        return isNotNull(array) && array.length < highValue;
    }

    public static boolean isLengthLessThan(int highValue, @Nullable byte[] array) {
        return isNotNull(array) && array.length < highValue;
    }

    public static boolean isLengthLessThan(int highValue, @Nullable short[] array) {
        return isNotNull(array) && array.length < highValue;
    }

    public static boolean isLengthLessThan(int highValue, @Nullable long[] array) {
        return isNotNull(array) && array.length < highValue;
    }

    public static boolean isLengthLessThan(int highValue, @Nullable float[] array) {
        return isNotNull(array) && array.length < highValue;
    }

    public static boolean isLengthLessThan(int highValue, @Nullable double[] array) {
        return isNotNull(array) && array.length < highValue;
    }

    public static boolean isLengthLessThan(int highValue, @Nullable boolean[] array) {
        return isNotNull(array) && array.length < highValue;
    }

    public static boolean isLengthLessThan(int highValue, @Nullable char[] array) {
        return isNotNull(array) && array.length < highValue;
    }

    public static boolean isLengthLessOrEqualTo(int maxLength, @Nullable CharSequence str) {
        return isNotNull(str) && str.length() <= maxLength;
    }

    public static boolean isLengthLessOrEqualTo(int maxLength, @Nullable Object[] array) {
        return isNotNull(array) && array.length <= maxLength;
    }

    public static boolean isLengthLessOrEqualTo(int maxLength, @Nullable int[] array) {
        return isNotNull(array) && array.length <= maxLength;
    }

    public static boolean isLengthLessOrEqualTo(int maxLength, @Nullable byte[] array) {
        return isNotNull(array) && array.length <= maxLength;
    }

    public static boolean isLengthLessOrEqualTo(int maxLength, @Nullable short[] array) {
        return isNotNull(array) && array.length <= maxLength;
    }

    public static boolean isLengthLessOrEqualTo(int maxLength, @Nullable long[] array) {
        return isNotNull(array) && array.length <= maxLength;
    }

    public static boolean isLengthLessOrEqualTo(int maxLength, @Nullable float[] array) {
        return isNotNull(array) && array.length <= maxLength;
    }

    public static boolean isLengthLessOrEqualTo(int maxLength, @Nullable double[] array) {
        return isNotNull(array) && array.length <= maxLength;
    }

    public static boolean isLengthLessOrEqualTo(int maxLength, @Nullable boolean[] array) {
        return isNotNull(array) && array.length <= maxLength;
    }

    public static boolean isLengthLessOrEqualTo(int maxLength, @Nullable char[] array) {
        return isNotNull(array) && array.length <= maxLength;
    }

    public static boolean isLengthBetween(int minLengthInclusive, int maxLengthInclusive, @Nullable CharSequence str) {
        return isNotNull(str) && str.length() >= minLengthInclusive && str.length() <= maxLengthInclusive;
    }

    public static boolean isLengthBetween(int minLengthInclusive, int maxLengthInclusive, @Nullable Object[] array) {
        return isNotNull(array) && array.length >= minLengthInclusive && array.length <= maxLengthInclusive;
    }

    public static boolean isLengthBetween(int minLengthInclusive, int maxLengthInclusive, @Nullable int[] array) {
        return isNotNull(array) && array.length >= minLengthInclusive && array.length <= maxLengthInclusive;
    }

    public static boolean isLengthBetween(int minLengthInclusive, int maxLengthInclusive, @Nullable byte[] array) {
        return isNotNull(array) && array.length >= minLengthInclusive && array.length <= maxLengthInclusive;
    }

    public static boolean isLengthBetween(int minLengthInclusive, int maxLengthInclusive, @Nullable short[] array) {
        return isNotNull(array) && array.length >= minLengthInclusive && array.length <= maxLengthInclusive;
    }

    public static boolean isLengthBetween(int minLengthInclusive, int maxLengthInclusive, @Nullable long[] array) {
        return isNotNull(array) && array.length >= minLengthInclusive && array.length <= maxLengthInclusive;
    }

    public static boolean isLengthBetween(int minLengthInclusive, int maxLengthInclusive, @Nullable float[] array) {
        return isNotNull(array) && array.length >= minLengthInclusive && array.length <= maxLengthInclusive;
    }

    public static boolean isLengthBetween(int minLengthInclusive, int maxLengthInclusive, @Nullable double[] array) {
        return isNotNull(array) && array.length >= minLengthInclusive && array.length <= maxLengthInclusive;
    }

    public static boolean isLengthBetween(int minLengthInclusive, int maxLengthInclusive, @Nullable boolean[] array) {
        return isNotNull(array) && array.length >= minLengthInclusive && array.length <= maxLengthInclusive;
    }

    public static boolean isLengthBetween(int minLengthInclusive, int maxLengthInclusive, @Nullable char[] array) {
        return isNotNull(array) && array.length >= minLengthInclusive && array.length <= maxLengthInclusive;
    }

    public static boolean isSize(int size, @Nullable Collection<?> collection) {
        return isNotNull(collection) && collection.size() == size;
    }

    public static boolean isSize(int size, @Nullable Iterable<?> iterable) {
        return isNotNull(iterable) && hasAtLeastSize(iterable, size)
            .map(iter -> !iter.hasNext()) // Changes "at least length" to "exact length"
            .orElse(false);
    }

    public static boolean isSize(int size, @Nullable Map<?, ?> map) {
        return isNotNull(map) && map.size() == size;
    }

    public static boolean isSizeGreaterThan(int minSize, @Nullable Collection<?> collection) {
        return isNotNull(collection) && collection.size() > minSize;
    }

    public static boolean isSizeGreaterThan(int lowValue, @Nullable Iterable<?> iterable) {
        return isNotNull(iterable) && hasAtLeastSize(iterable, lowValue + 1).isPresent();
    }

    public static boolean isSizeGreaterThan(int minSize, @Nullable Map<?, ?> map) {
        return isNotNull(map) && map.size() > minSize;
    }

    public static boolean isSizeGreaterOrEqualTo(int minSize, @Nullable Collection<?> collection) {
        return isNotNull(collection) && collection.size() >= minSize;
    }

    public static boolean isSizeGreaterOrEqualTo(int minSize, @Nullable Iterable<?> iterable) {
        return isNotNull(iterable) && hasAtLeastSize(iterable, minSize).isPresent();
    }

    public static boolean isSizeGreaterOrEqualTo(int minSize, @Nullable Map<?, ?> map) {
        return isNotNull(map) && map.size() >= minSize;
    }

    public static boolean isSizeLessThan(int highValue, @Nullable Collection<?> collection) {
        return isNotNull(collection) && collection.size() < highValue;
    }

    public static boolean isSizeLessThan(int highValue, @Nullable Iterable<?> iterable) {
        return isNotNull(iterable) && hasAtLeastSize(iterable, highValue - 1)
            .map(iter -> !iter.hasNext())  // if the iter has next, it has too high a value
            .orElse(true);                 // if it's empty that means it's nice and short
    }

    public static boolean isSizeLessThan(int highValue, @Nullable Map<?, ?> map) {
        return isNotNull(map) && map.size() < highValue;
    }

    public static boolean isSizeLessOrEqualTo(int maxSize, @Nullable Collection<?> collection) {
        return isNotNull(collection) && collection.size() <= maxSize;
    }

    public static boolean isSizeLessOrEqualTo(int maxSize, @Nullable Iterable<?> iterable) {
        return isNotNull(iterable) && hasAtLeastSize(iterable, maxSize)
            .map(iter -> !iter.hasNext())  // if the iter has next, it has too high a value
            .orElse(true);                // if it's empty that means it's nice and short
    }

    public static boolean isSizeLessOrEqualTo(int maxSize, @Nullable Map<?, ?> map) {
        return isNotNull(map) && map.size() <= maxSize;
    }

    public static boolean isSizeBetween(int minSizeInclusive, int maxSizeInclusive, @Nullable Collection<?> collection) {
        return isNotNull(collection) && collection.size() >= minSizeInclusive && collection.size() <= maxSizeInclusive;
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
        return isNotNull(map) && map.size() >= minSizeInclusive && map.size() <= maxSizeInclusive;
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
            .map(Checks::pathExists)
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
            .map(Checks::pathNotExists)
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
            .map(Checks::isRegularFile)
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
            .map(Checks::isDirectory)
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

    public static boolean isInstanceOf(@Nullable Class<?> classType, @Nullable Object object) {
        return isNotNull(classType) && isNotNull(object) && classType.isInstance(object);
    }

    public static boolean isNotInstanceOf(@Nullable Class<?> classType, @Nullable Object object) {
        return isNotNull(classType) && isNotNull(object) && !classType.isInstance(object);
    }

    public static boolean contains(@Nullable String contains, @Nullable CharSequence stringToCheck) {
        return isNotNull(contains) && isNotNull(stringToCheck) && stringToCheck.toString().contains(contains);
    }

    public static boolean containsRegex(@Nullable Pattern regex, @Nullable CharSequence stringToCheck) {
        return isNotNull(regex) && isNotNull(stringToCheck) && regex.matcher(stringToCheck).find();
    }

    public static boolean containsRegex(@Nullable String regex, @Nullable CharSequence stringToCheck) {
        return isNotNull(regex) && containsRegex(Pattern.compile(regex), stringToCheck);
    }

    public static boolean matchesRegex(@Nullable Pattern regex, @Nullable CharSequence stringToCheck) {
        return isNotNull(regex) && isNotNull(stringToCheck) && regex.matcher(stringToCheck).matches();
    }

    public static boolean matchesRegex(@Nullable String regex, @Nullable CharSequence stringToCheck) {
        return isNotNull(regex) && matchesRegex(Pattern.compile(regex), stringToCheck);
    }

    public static boolean containsKey(@Nullable Object key, @Nullable Map<?, ?> mapToCheck) {
        return isNotNull(key) && isNotNull(mapToCheck) && mapToCheck.containsKey(key);
    }

    public static boolean containsValue(@Nullable Object value, @Nullable Map<?, ?> mapToCheck) {
        return isNotNull(value) && isNotNull(mapToCheck) && mapToCheck.containsValue(value);
    }

    public static boolean containsUniqueElements(@Nullable Collection<?> collectionToCheck) {
        return isNotNull(collectionToCheck)
            && collectionToCheck.size() == new HashSet<>(collectionToCheck).size();
    }

    public static boolean notContainsNull(@Nullable Collection<?> collectionToCheck) {
        try {
            // contains(null) will throw if the collection doesn't support nulls.
            return isNotNull(collectionToCheck) && collectionToCheck.contains(null);
        } catch (Exception e) {
            throw new IllegalStateException(
                "You do not need to call notContainsNull() since this collection does not allow nulls: " + collectionToCheck,
                e);
        }
    }

    public static boolean notContainsNull(@Nullable Map<?, ?> mapToCheck) {

        if (isNull(mapToCheck)) {
            return false;
        }

        // Check the keys.
        try {
            // containsKey(null) will throw if the map doesn't support nulls.
            if (mapToCheck.containsKey(null)) {
                return false;
            }
        } catch (Exception e) {
            throw new IllegalStateException(
                "You do not need to call notContainsNull() since this map does not allow null keys: " + mapToCheck,
                e);
        }

        // Check the values.
        try {
            // containsValue(null) will throw if the map doesn't support nulls.
            if (mapToCheck.containsValue(null)) {
                return false;
            }
        } catch (Exception e) {
            throw new IllegalStateException(
                "You do not need to call notContainsNull() since this map does not allow null values: " + mapToCheck,
                e);
        }

        // Looks good.
        return true;
    }

    public static boolean notContainsNull(@Nullable Object[] arrayToCheck) {
        return isNotNull(arrayToCheck) && notContainsNull(Arrays.asList(arrayToCheck));
    }

    public static boolean notContainsNull(@Nullable int[] arrayToCheck) {
        return isNotNull(arrayToCheck) && notContainsNull(Arrays.stream(arrayToCheck).boxed().toArray());
    }

    public static boolean isEmail(@Nullable CharSequence email) {
        return isNotNull(email) && EMAIL_PATTERN.matcher(email).matches();
    }

    /**
     * True if the value begins with {@code http://} or {@code https://} and has any sort of hostname or IP address.
     */
    public static boolean isUrl(@Nullable CharSequence url) {
        return isNotNull(url) && URL_PATTERN.matcher(url).matches();
    }

    /**
     * True if the value is a hostname (including {@code localhost}) or an IP address.
     */
    public static boolean isHostname(@Nullable CharSequence hostOrIp) {
        return isNotNull(hostOrIp) && HOSTNAME_PATTERN.matcher(hostOrIp).matches();
    }

    /**
     * True if the value is a hostname (including {@code localhost})
     * or IP address, with a port number separated by a colon.
     */
    public static boolean isHostAndPort(@Nullable CharSequence hostOrIp) {
        return isNotNull(hostOrIp) && HOST_PORT_PATTERN.matcher(hostOrIp).matches();
    }

    /**
     * True if this is a valid IPv4 or IPv6 address.
     * If you want to check for a specific version, use {@link #isIp4Address(CharSequence)} or
     * {@link #isIp6Address(CharSequence)}.
     */
    public static boolean isIpAddress(@Nullable CharSequence ipAddress) {
        return isIp4Address(ipAddress) || isIp6Address(ipAddress);
    }

    public static boolean isIp4Address(@Nullable CharSequence ip4Address) {
        return isNotNull(ip4Address) && IP4_ADDRESS_PATTERN.matcher(ip4Address).matches();
    }

    public static boolean isIp6Address(@Nullable CharSequence ip6Address) {
        return isNotNull(ip6Address) && IP6_ADDRESS_PATTERN.matcher(ip6Address).matches();
    }

    /**
     * True if the value is not empty, and looks like JSON.
     * This means starting and ending with curly braces or square brackets.
     */
    public static boolean isJson(@Nullable CharSequence json) {

        if (isNullOrEmpty(json)) {
            return false;
        }

        return RequireUtils.isJson(json.toString());
    }

    /**
     * True if the value is not empty and can be parsed as XML without errors.
     * Uses {@link DocumentBuilder} to parse the XML.
     */
    public static boolean isXml(@Nullable CharSequence xml) {

        if (isNullOrEmpty(xml)) {
            return false;
        }

        return RequireUtils.isXml(xml.toString());
    }

    public static boolean isUUID(@Nullable CharSequence uuid) {
        return isNotNull(uuid) && UUID_PATTERN.matcher(uuid).matches();
    }
}
