package com.terheyden.require;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

import static com.terheyden.require.Check.contains;
import static com.terheyden.require.Check.containsKey;
import static com.terheyden.require.Check.containsNull;
import static com.terheyden.require.Check.containsRegex;
import static com.terheyden.require.Check.containsUniqueElements;
import static com.terheyden.require.Check.containsValue;
import static com.terheyden.require.Check.isBlank;
import static com.terheyden.require.Check.isDurationGreaterOrEqualTo;
import static com.terheyden.require.Check.isDurationGreaterThan;
import static com.terheyden.require.Check.isDurationLessOrEqualTo;
import static com.terheyden.require.Check.isDurationLessThan;
import static com.terheyden.require.Check.isDurationLessThanDays;
import static com.terheyden.require.Check.isDurationLessThanHours;
import static com.terheyden.require.Check.isDurationLessThanMillis;
import static com.terheyden.require.Check.isDurationLessThanMins;
import static com.terheyden.require.Check.isDurationLessThanNanos;
import static com.terheyden.require.Check.isDurationLessThanSecs;
import static com.terheyden.require.Check.isEmail;
import static com.terheyden.require.Check.isFuture;
import static com.terheyden.require.Check.isIPv6Address;
import static com.terheyden.require.Check.isIpAddress;
import static com.terheyden.require.Check.isJson;
import static com.terheyden.require.Check.isPast;
import static com.terheyden.require.Check.isSize;
import static com.terheyden.require.Check.isSizeGreaterOrEqualTo;
import static com.terheyden.require.Check.isSizeGreaterThan;
import static com.terheyden.require.Check.isSizeLessOrEqualTo;
import static com.terheyden.require.Check.isSizeLessThan;
import static com.terheyden.require.Check.isUUID;
import static com.terheyden.require.Check.isUrl;
import static com.terheyden.require.Check.isXml;
import static com.terheyden.require.Check.matchesRegex;
import static com.terheyden.require.Check.notBlank;
import static com.terheyden.require.Check.notContainsNull;
import static com.terheyden.require.Check.notExists;
import static com.terheyden.require.Check.pathExists;
import static java.util.Collections.emptyList;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * RequireTest unit tests.
 */
@SuppressWarnings("ConstantValue")
class CheckTest {

    private static final String GOOD_DIR_STR = "src/test/resources";
    private static final String GOOD_FILE_STR = "src/test/resources/empty.txt";
    private static final String BAD = "bad";

    private final String goodStr = "good";
    private final String emptyStr = "";
    private final String blankStr = " \t";
    private final String nullStr = null;
    private final List<String> goodList = Arrays.asList("a", "b", "c");
    private final List<String> emptyList = emptyList();
    private final List<String> nullList = null;
    private final List<String> nullInsideList = Arrays.asList("a", null, "c");
    private final Iterable<String> goodIter = goodList;
    private final Iterable<String> emptyIter = emptyList;
    private final Iterable<String> nullIter = null;
    private final Iterable<String> nullInsideIter = nullInsideList;
    private final Map<String, Integer> goodMap = new HashMap() {{
        put("a", 1);
        put("b", 2);
    }};
    private final Map<String, String> emptyMap = Collections.emptyMap();
    private final Map<String, String> nullMap = null;
    private final Map<String, Integer> nullInsideMapKey = new HashMap() {{
        put(null, 1);
        put("b", 2);
    }};
    private final Map<String, Integer> nullInsideMapValue = new HashMap() {{
        put("a", 1);
        put("b", null);
    }};
    private final String[] goodArray = new String[] { "a", "b", "c" };
    private final String[] emptyArray = new String[0];
    private final String[] nullArray = null;
    private final String[] nullInsideArray = new String[] { "a", null, "c" };
    private final Path goodPathDir = Paths.get(GOOD_DIR_STR);
    private final Path goodPathFile = Paths.get(GOOD_FILE_STR);
    private final Path badPathDir = Paths.get(BAD);
    private final Path badPathFile = badPathDir;
    private final Path nullPath = null;
    private final File goodFileDir = goodPathDir.toFile();
    private final File goodFileFile = goodPathFile.toFile();
    private final File badFileDir = badPathDir.toFile();
    private final File badFileFile = badPathFile.toFile();
    private final File nullFile = null;

    private final ZonedDateTime zdtFuture = ZonedDateTime.now().plusDays(1);
    private final OffsetDateTime odtFuture = OffsetDateTime.now().plusDays(1);
    private final LocalDateTime ldtFuture = LocalDateTime.now().plusDays(1);
    private final LocalDate ldFuture = LocalDate.now().plusDays(1);
    private final LocalTime ltFuture = LocalTime.now().plusHours(1);
    private final ZonedDateTime zdtPast = ZonedDateTime.now().minusDays(1);
    private final OffsetDateTime odtPast = OffsetDateTime.now().minusDays(1);
    private final LocalDateTime ldtPast = LocalDateTime.now().minusDays(1);
    private final LocalDate ldPast = LocalDate.now().minusDays(1);
    private final LocalTime ltPast = LocalTime.now().minusHours(1);
    private final ZonedDateTime zdtNull = null;
    private final OffsetDateTime odtNull = null;
    private final LocalDateTime ldtNull = null;
    private final LocalDate ldNull = null;
    private final LocalTime ltNull = null;
    private final Duration oneNs = Duration.ofNanos(1);
    private final Duration fiveNs = Duration.ofNanos(5);
    private final Duration oneMs = Duration.ofMillis(1);
    private final Duration fiveMs = Duration.ofMillis(5);
    private final Duration oneSec = Duration.ofSeconds(1);
    private final Duration fiveSecs = Duration.ofSeconds(5);
    private final Duration oneMin = Duration.ofMinutes(1);
    private final Duration fiveMins = Duration.ofMinutes(5);
    private final Duration oneHour = Duration.ofHours(1);
    private final Duration fiveHours = Duration.ofHours(5);
    private final Duration oneDay = Duration.ofDays(1);
    private final Duration fiveDays = Duration.ofDays(5);
    private final Duration nullDuration = null;

    @Test
    void isTrue() {
        assertThat(Check.isTrue(true)).isTrue();
        assertThat(Check.isTrue(false)).isFalse();
    }

    @Test
    void isFalse() {
        assertThat(Check.isFalse(true)).isFalse();
        assertThat(Check.isFalse(false)).isTrue();
    }

    @Test
    void isNull() {
        assertThat(Check.isNull(nullStr)).isTrue();
        assertThat(Check.isNull(goodStr)).isFalse();
    }

    @Test
    void notNull() {
        assertThat(Check.notNull(goodStr)).isTrue();
        assertThat(Check.notNull(nullStr)).isFalse();
    }

    @Test
    void testIsDigitsOnly() {
        assertThat(Check.numbersOnly("123")).isTrue();
        assertThat(Check.numbersOnly("123.")).isFalse();
        assertThat(Check.numbersOnly("")).isTrue();
        assertThat(Check.numbersOnly(null)).isFalse();
    }

    @Test
    void testIsAlphaOnly() {
        assertThat(Check.alphasOnly("abc")).isTrue();
        assertThat(Check.alphasOnly("abc123")).isFalse();
        assertThat(Check.alphasOnly("")).isTrue();
        assertThat(Check.alphasOnly(null)).isFalse();
    }

    @Test
    void testIsAlphaNumericOnly() {
        assertThat(Check.alphaNumericOnly("abc123")).isTrue();
        assertThat(Check.alphaNumericOnly("123")).isTrue();
        assertThat(Check.alphaNumericOnly("abc")).isTrue();
        assertThat(Check.alphaNumericOnly("abc123.")).isFalse();
        assertThat(Check.alphaNumericOnly("")).isTrue();
        assertThat(Check.alphaNumericOnly(null)).isFalse();
    }

    @Test
    void isEmpty_string() {
        assertThat(Check.isEmpty(goodStr)).isFalse();
        assertThat(Check.isEmpty(blankStr)).isFalse();
        assertThat(Check.isEmpty(emptyStr)).isTrue();
        assertThat(Check.isEmpty(nullStr)).isTrue();
    }

    @Test
    void isEmpty_collection() {
        assertThat(Check.isEmpty(goodList)).isFalse();
        assertThat(Check.isEmpty(emptyList)).isTrue();
        assertThat(Check.isEmpty(nullList)).isTrue();
    }

    @Test
    void isEmpty_iterable() {
        assertThat(Check.isEmpty(goodIter)).isFalse();
        assertThat(Check.isEmpty(emptyIter)).isTrue();
        assertThat(Check.isEmpty(nullIter)).isTrue();
    }

    @Test
    void isEmpty_map() {
        assertThat(Check.isEmpty(goodMap)).isFalse();
        assertThat(Check.isEmpty(emptyMap)).isTrue();
        assertThat(Check.isEmpty(nullMap)).isTrue();
    }

    @Test
    void isEmpty_array() {
        assertThat(Check.isEmpty(goodArray)).isFalse();
        assertThat(Check.isEmpty(emptyArray)).isTrue();
        assertThat(Check.isEmpty(nullArray)).isTrue();
    }

    @Test
    void notEmpty_string() {
        assertThat(Check.notEmpty(goodStr)).isTrue();
        assertThat(Check.notEmpty(blankStr)).isTrue();
        assertThat(Check.notEmpty(emptyStr)).isFalse();
        assertThat(Check.notEmpty(nullStr)).isFalse();
    }

    @Test
    void notEmpty_collection() {
        assertThat(Check.notEmpty(goodList)).isTrue();
        assertThat(Check.notEmpty(emptyList)).isFalse();
        assertThat(Check.notEmpty(nullList)).isFalse();
    }

    @Test
    void notEmpty_iterable() {
        assertThat(Check.notEmpty(goodIter)).isTrue();
        assertThat(Check.notEmpty(emptyIter)).isFalse();
        assertThat(Check.notEmpty(nullIter)).isFalse();
    }

    @Test
    void notEmpty_map() {
        assertThat(Check.notEmpty(goodMap)).isTrue();
        assertThat(Check.notEmpty(emptyMap)).isFalse();
        assertThat(Check.notEmpty(nullMap)).isFalse();
    }

    @Test
    void notEmpty_array() {
        assertThat(Check.notEmpty(goodArray)).isTrue();
        assertThat(Check.notEmpty(emptyArray)).isFalse();
        assertThat(Check.notEmpty(nullArray)).isFalse();
    }

    @Test
    void nullOrEmpty_string() {
        assertThat(Check.isEmpty(goodStr)).isFalse();
        assertThat(Check.isEmpty(blankStr)).isFalse();
        assertThat(Check.isEmpty(emptyStr)).isTrue();
        assertThat(Check.isEmpty(nullStr)).isTrue();
    }

    @Test
    void nullOrEmpty_collection() {
        assertThat(Check.isEmpty(goodList)).isFalse();
        assertThat(Check.isEmpty(emptyList)).isTrue();
        assertThat(Check.isEmpty(nullList)).isTrue();
    }

    @Test
    void nullOrEmpty_iterable() {
        assertThat(Check.isEmpty(goodIter)).isFalse();
        assertThat(Check.isEmpty(emptyIter)).isTrue();
        assertThat(Check.isEmpty(nullIter)).isTrue();
    }

    @Test
    void nullOrEmpty_map() {
        assertThat(Check.isEmpty(goodMap)).isFalse();
        assertThat(Check.isEmpty(emptyMap)).isTrue();
        assertThat(Check.isEmpty(nullMap)).isTrue();
    }

    @Test
    void nullOrEmpty_array() {
        assertThat(Check.isEmpty(goodArray)).isFalse();
        assertThat(Check.isEmpty(emptyArray)).isTrue();
        assertThat(Check.isEmpty(nullArray)).isTrue();
    }

    @Test
    void testIsDurationGreaterThan() {
        assertThat(isDurationGreaterThan(fiveMins, oneMin)).isTrue();
        assertThat(isDurationGreaterThan(fiveMins, fiveMins)).isFalse();
        assertThat(isDurationGreaterThan(nullDuration, oneMin)).isFalse();
        assertThat(isDurationGreaterThan(nullDuration, nullDuration)).isFalse();
    }

    @Test
    void testDurationIsGreaterOrEqualTo() {
        assertThat(isDurationGreaterOrEqualTo(fiveMins, oneMin)).isTrue();
        assertThat(isDurationGreaterOrEqualTo(fiveMins, fiveMins)).isTrue();
        assertThat(isDurationGreaterOrEqualTo(nullDuration, oneMin)).isFalse();
        assertThat(isDurationGreaterOrEqualTo(nullDuration, nullDuration)).isFalse();
    }

    @Test
    void testDurationIsLessThan() {
        assertThat(isDurationLessThan(fiveMins, oneMin)).isFalse();
        assertThat(isDurationLessThan(fiveMins, fiveMins)).isFalse();
        assertThat(isDurationLessThan(nullDuration, oneMin)).isFalse();
        assertThat(isDurationLessThan(nullDuration, nullDuration)).isFalse();
    }

    @Test
    void testDurationIsLessOrEqualTo() {
        assertThat(isDurationLessOrEqualTo(fiveMins, oneMin)).isFalse();
        assertThat(isDurationLessOrEqualTo(fiveMins, fiveMins)).isTrue();
        assertThat(isDurationLessOrEqualTo(nullDuration, oneMin)).isFalse();
        assertThat(isDurationLessOrEqualTo(nullDuration, nullDuration)).isFalse();
    }

    @Test
    void greaterThan_duration() {
        assertThat(Check.isDurationGreaterThanNanos(oneNs, 1)).isFalse();
        assertThat(Check.isDurationGreaterThanNanos(fiveNs, 1)).isTrue();
        assertThat(Check.isDurationGreaterThanNanos(oneMs, 1)).isTrue();
        assertThat(Check.isDurationGreaterThanMillis(oneMs, 1)).isFalse();
        assertThat(Check.isDurationGreaterThanMillis(fiveMs, 1)).isTrue();
        assertThat(Check.isDurationGreaterThanMillis(oneSec, 1)).isTrue();
        assertThat(Check.isDurationGreaterThanSecs(oneSec, 1)).isFalse();
        assertThat(Check.isDurationGreaterThanSecs(fiveSecs, 1)).isTrue();
        assertThat(Check.isDurationGreaterThanSecs(oneMin, 1)).isTrue();
        assertThat(Check.isDurationGreaterThanMins(oneMin, 1)).isFalse();
        assertThat(Check.isDurationGreaterThanMins(fiveMins, 1)).isTrue();
        assertThat(Check.isDurationGreaterThanMins(oneHour, 1)).isTrue();
        assertThat(Check.isDurationGreaterThanHours(oneHour, 1)).isFalse();
        assertThat(Check.isDurationGreaterThanHours(fiveHours, 1)).isTrue();
        assertThat(Check.isDurationGreaterThanHours(oneDay, 1)).isTrue();
        assertThat(Check.isDurationGreaterThanDays(oneDay, 1)).isFalse();
        assertThat(Check.isDurationGreaterThanDays(fiveDays, 1)).isTrue();
    }

    @Test
    void greaterOrEqualTo_duration() {
        assertThat(Check.isDurationGreaterOrEqualToNanos(oneNs, 1)).isTrue();
        assertThat(Check.isDurationGreaterOrEqualToNanos(fiveNs, 10)).isFalse();
        assertThat(Check.isDurationGreaterOrEqualToNanos(oneMs, 1)).isTrue();
        assertThat(Check.isDurationGreaterOrEqualToMillis(oneMs, 1)).isTrue();
        assertThat(Check.isDurationGreaterOrEqualToMillis(fiveMs, 10)).isFalse();
        assertThat(Check.isDurationGreaterOrEqualToMillis(oneSec, 1)).isTrue();
        assertThat(Check.isDurationGreaterOrEqualToSecs(oneSec, 1)).isTrue();
        assertThat(Check.isDurationGreaterOrEqualToSecs(fiveSecs, 10)).isFalse();
        assertThat(Check.isDurationGreaterOrEqualToSecs(oneMin, 1)).isTrue();
        assertThat(Check.isDurationGreaterOrEqualToMins(oneMin, 1)).isTrue();
        assertThat(Check.isDurationGreaterOrEqualToMins(fiveMins, 10)).isFalse();
        assertThat(Check.isDurationGreaterOrEqualToMins(oneHour, 1)).isTrue();
        assertThat(Check.isDurationGreaterOrEqualToHours(oneHour, 1)).isTrue();
        assertThat(Check.isDurationGreaterOrEqualToHours(fiveHours, 10)).isFalse();
        assertThat(Check.isDurationGreaterOrEqualToHours(oneDay, 1)).isTrue();
        assertThat(Check.isDurationGreaterOrEqualToDays(oneDay, 1)).isTrue();
        assertThat(Check.isDurationGreaterOrEqualToDays(fiveDays, 10)).isFalse();
    }

    @Test
    void lessThan_duration() {
        assertThat(isDurationLessThanNanos(oneNs, 1)).isFalse();
        assertThat(isDurationLessThanNanos(fiveNs, 1)).isFalse();
        assertThat(isDurationLessThanNanos(oneMs, 1)).isFalse();
        assertThat(isDurationLessThanMillis(oneMs, 1)).isFalse();
        assertThat(isDurationLessThanMillis(fiveMs, 1)).isFalse();
        assertThat(isDurationLessThanMillis(oneSec, 1)).isFalse();
        assertThat(isDurationLessThanSecs(oneSec, 1)).isFalse();
        assertThat(isDurationLessThanSecs(fiveSecs, 1)).isFalse();
        assertThat(isDurationLessThanSecs(oneMin, 1)).isFalse();
        assertThat(isDurationLessThanMins(oneMin, 1)).isFalse();
        assertThat(isDurationLessThanMins(fiveMins, 1)).isFalse();
        assertThat(isDurationLessThanMins(oneHour, 1)).isFalse();
        assertThat(isDurationLessThanHours(oneHour, 1)).isFalse();
        assertThat(isDurationLessThanHours(fiveHours, 1)).isFalse();
        assertThat(isDurationLessThanHours(oneDay, 1)).isFalse();
        assertThat(isDurationLessThanDays(oneDay, 1)).isFalse();
        assertThat(isDurationLessThanDays(fiveDays, 1)).isFalse();
    }

    @Test
    void lessOrEqualTo_duration() {
        assertThat(Check.isDurationLessOrEqualToNanos(oneNs, 1)).isTrue();
        assertThat(Check.isDurationLessOrEqualToNanos(fiveNs, 10)).isTrue();
        assertThat(Check.isDurationLessOrEqualToMillis(oneMs, 1)).isTrue();
        assertThat(Check.isDurationLessOrEqualToMillis(fiveMs, 10)).isTrue();
        assertThat(Check.isDurationLessOrEqualToMillis(oneNs, 1)).isTrue();
        assertThat(Check.isDurationLessOrEqualToSecs(oneSec, 1)).isTrue();
        assertThat(Check.isDurationLessOrEqualToSecs(fiveSecs, 10)).isTrue();
        assertThat(Check.isDurationLessOrEqualToSecs(oneMs, 1)).isTrue();
        assertThat(Check.isDurationLessOrEqualToMins(oneMin, 1)).isTrue();
        assertThat(Check.isDurationLessOrEqualToMins(fiveMins, 10)).isTrue();
        assertThat(Check.isDurationLessOrEqualToMins(oneSec, 1)).isTrue();
        assertThat(Check.isDurationLessOrEqualToHours(oneHour, 1)).isTrue();
        assertThat(Check.isDurationLessOrEqualToHours(fiveHours, 10)).isTrue();
        assertThat(Check.isDurationLessOrEqualToHours(oneMin, 1)).isTrue();
        assertThat(Check.isDurationLessOrEqualToDays(oneDay, 1)).isTrue();
        assertThat(Check.isDurationLessOrEqualToDays(fiveDays, 10)).isTrue();
        assertThat(Check.isDurationLessOrEqualToDays(oneHour, 1)).isTrue();
    }

    @Test
    void isBlank_string() {
        assertThat(isBlank(goodStr)).isFalse();
        assertThat(isBlank(blankStr)).isTrue();
        assertThat(isBlank(emptyStr)).isTrue();
        assertThat(isBlank(nullStr)).isTrue();
    }

    @Test
    void notBlank_string() {
        assertThat(notBlank(goodStr)).isTrue();
        assertThat(notBlank(blankStr)).isFalse();
        assertThat(notBlank(emptyStr)).isFalse();
        assertThat(notBlank(nullStr)).isFalse();
    }

    @Test
    void isLength_string() {

        assertThat(Check.isLength(goodStr, goodStr.length())).isTrue();
        assertThat(Check.isLength(goodStr, goodStr.length() + 1)).isFalse();
        assertThat(Check.isLength(goodStr, goodStr.length() - 1)).isFalse();
        assertThat(Check.isLength(emptyStr, 0)).isTrue();
        assertThat(Check.isLength(emptyStr, 1)).isFalse();
        assertThat(Check.isLength(nullStr, 1)).isFalse();
        assertThat(Check.isLength(nullStr, 0)).isFalse(); // null has no length, it is null

        assertThat(Check.isLengthGreaterThan(goodStr, goodStr.length() - 1)).isTrue();
        assertThat(Check.isLengthGreaterThan(goodStr, goodStr.length())).isFalse();
        assertThat(Check.isLengthGreaterThan(goodStr, goodStr.length() + 1)).isFalse();
        assertThat(Check.isLengthGreaterThan(blankStr, 1)).isTrue();
        assertThat(Check.isLengthGreaterThan(emptyStr, 0)).isFalse();
        assertThat(Check.isLengthGreaterThan(nullStr, 1)).isFalse();
        assertThat(Check.isLengthGreaterThan(nullStr, 0)).isFalse(); // null has no length, it is null

        assertThat(Check.isLengthGreaterOrEqualTo(goodStr, goodStr.length())).isTrue();
        assertThat(Check.isLengthGreaterOrEqualTo(goodStr, goodStr.length() + 1)).isFalse();
        assertThat(Check.isLengthGreaterOrEqualTo(goodStr, goodStr.length() - 1)).isTrue();
        assertThat(Check.isLengthGreaterOrEqualTo(blankStr, 1)).isTrue();
        assertThat(Check.isLengthGreaterOrEqualTo(emptyStr, 1)).isFalse();
        assertThat(Check.isLengthGreaterOrEqualTo(nullStr, 1)).isFalse();
        assertThat(Check.isLengthGreaterOrEqualTo(nullStr, 0)).isFalse(); // null has no length, it is null

        assertThat(Check.isLengthLessThan(goodStr, goodStr.length() + 1)).isTrue();
        assertThat(Check.isLengthLessThan(goodStr, goodStr.length())).isFalse();
        assertThat(Check.isLengthLessThan(goodStr, goodStr.length() - 1)).isFalse();
        assertThat(Check.isLengthLessThan(blankStr, 1)).isFalse();
        assertThat(Check.isLengthLessThan(emptyStr, 1)).isTrue();
        assertThat(Check.isLengthLessThan(emptyStr, 0)).isFalse();
        assertThat(Check.isLengthLessThan(nullStr, 1)).isFalse();
        assertThat(Check.isLengthLessThan(nullStr, 0)).isFalse(); // null has no length, it is null

        assertThat(Check.isLengthLessOrEqualTo(goodStr, goodStr.length())).isTrue();
        assertThat(Check.isLengthLessOrEqualTo(goodStr, goodStr.length() + 1)).isTrue();
        assertThat(Check.isLengthLessOrEqualTo(goodStr, goodStr.length() - 1)).isFalse();
        assertThat(Check.isLengthLessOrEqualTo(blankStr, 1)).isFalse();
        assertThat(Check.isLengthLessOrEqualTo(emptyStr, 1)).isTrue();
        assertThat(Check.isLengthLessOrEqualTo(nullStr, 1)).isFalse();
        assertThat(Check.isLengthLessOrEqualTo(nullStr, 0)).isFalse(); // null has no length, it is null
    }

    @Test
    void hasLength_array() {

        assertThat(Check.isLength(goodArray, goodArray.length)).isTrue(); // test max inclusive
        assertThat(Check.isLength(goodArray, goodArray.length + 1)).isFalse(); // plus or minus 1
        assertThat(Check.isLength(goodArray, goodArray.length - 1)).isFalse(); // is wrong
        assertThat(Check.isLength(emptyArray, 0)).isTrue(); // test empty
        assertThat(Check.isLength(emptyArray, 1)).isFalse();
        assertThat(Check.isLength(nullArray, 0)).isFalse(); // null has no length, it is null
        assertThat(Check.isLength(nullArray, 1)).isFalse(); // test null
        assertThat(Check.isLengthGreaterThan(goodArray, goodArray.length - 1)).isTrue();
        assertThat(Check.isLengthGreaterThan(goodArray, goodArray.length)).isFalse();
        assertThat(Check.isLengthGreaterThan(emptyArray, 0)).isFalse();
        assertThat(Check.isLengthGreaterThan(nullArray, 0)).isFalse(); // null has no length, it is null

        assertThat(Check.isLengthGreaterOrEqualTo(goodArray, goodArray.length)).isTrue();
        assertThat(Check.isLengthGreaterOrEqualTo(goodArray, goodArray.length + 1)).isFalse();
        assertThat(Check.isLengthGreaterOrEqualTo(goodArray, goodArray.length - 1)).isTrue();
        assertThat(Check.isLengthGreaterOrEqualTo(emptyArray, 0)).isTrue();
        assertThat(Check.isLengthGreaterOrEqualTo(emptyArray, 1)).isFalse();
        assertThat(Check.isLengthGreaterOrEqualTo(nullArray, 0)).isFalse(); // null has no length, it is null

        assertThat(Check.isLengthLessThan(goodArray, goodArray.length + 1)).isTrue();
        assertThat(Check.isLengthLessThan(goodArray, goodArray.length)).isFalse();
        assertThat(Check.isLengthLessThan(emptyArray, 1)).isTrue();
        assertThat(Check.isLengthLessThan(emptyArray, 0)).isFalse();
        assertThat(Check.isLengthLessThan(nullArray, 1)).isFalse();
        assertThat(Check.isLengthLessThan(nullArray, 0)).isFalse(); // null has no length, it is null

        assertThat(Check.isLengthLessOrEqualTo(goodArray, goodArray.length)).isTrue();
        assertThat(Check.isLengthLessOrEqualTo(goodArray, goodArray.length + 1)).isTrue();
        assertThat(Check.isLengthLessOrEqualTo(goodArray, goodArray.length - 1)).isFalse();
        assertThat(Check.isLengthLessOrEqualTo(emptyArray, 1)).isTrue();
        assertThat(Check.isLengthLessOrEqualTo(emptyArray, 0)).isTrue();
        assertThat(Check.isLengthLessOrEqualTo(nullArray, 1)).isFalse();
        assertThat(Check.isLengthLessOrEqualTo(nullArray, 0)).isFalse(); // null has no length, it is null
    }

    @Test
    void hasSize_collection() {

        assertThat(isSize(goodList, goodList.size())).isTrue();
        assertThat(isSize(goodList, goodList.size() + 1)).isFalse();
        assertThat(isSize(goodList, goodList.size() - 1)).isFalse();
        assertThat(isSize(emptyList, 0)).isTrue();
        assertThat(isSize(emptyList, 1)).isFalse();
        assertThat(isSize(nullList, 1)).isFalse();
        assertThat(isSize(nullList, 0)).isFalse(); // null has no size, it is null

        assertThat(isSizeGreaterThan(goodList, goodList.size() - 1)).isTrue();
        assertThat(isSizeGreaterThan(goodList, goodList.size())).isFalse();
        assertThat(isSizeGreaterThan(goodList, goodList.size() + 1)).isFalse();
        assertThat(isSizeGreaterThan(emptyList, 1)).isFalse();
        assertThat(isSizeGreaterThan(nullList, 1)).isFalse();
        assertThat(isSizeGreaterThan(nullList, 0)).isFalse(); // null has no size, it is null

        assertThat(isSizeGreaterOrEqualTo(goodList, goodList.size())).isTrue();
        assertThat(isSizeGreaterOrEqualTo(goodList, goodList.size() + 1)).isFalse();
        assertThat(isSizeGreaterOrEqualTo(goodList, goodList.size() - 1)).isTrue();
        assertThat(isSizeGreaterOrEqualTo(goodList, 1)).isTrue();
        assertThat(isSizeGreaterOrEqualTo(emptyList, 1)).isFalse();
        assertThat(isSizeGreaterOrEqualTo(emptyList, 0)).isTrue();
        assertThat(isSizeGreaterOrEqualTo(nullList, 1)).isFalse();
        assertThat(isSizeGreaterOrEqualTo(nullList, 0)).isFalse(); // null has no size, it is null

        assertThat(isSizeLessThan(goodList, goodList.size() + 1)).isTrue();
        assertThat(isSizeLessThan(goodList, goodList.size())).isFalse();
        assertThat(isSizeLessThan(goodList, goodList.size() - 1)).isFalse();
        assertThat(isSizeLessThan(emptyList, 1)).isTrue();
        assertThat(isSizeLessThan(nullList, 1)).isFalse();
        assertThat(isSizeLessThan(nullList, 0)).isFalse(); // null has no size, it is null

        assertThat(isSizeLessOrEqualTo(goodList, goodList.size())).isTrue();
        assertThat(isSizeLessOrEqualTo(goodList, goodList.size() + 1)).isTrue();
        assertThat(isSizeLessOrEqualTo(goodList, goodList.size() - 1)).isFalse();
        assertThat(isSizeLessOrEqualTo(emptyList, 1)).isTrue();
        assertThat(isSizeLessOrEqualTo(nullList, 1)).isFalse();
        assertThat(isSizeLessOrEqualTo(nullList, 0)).isFalse(); // null has no size, it is null
    }

    @Test
    void hasSize_iterable() {

        assertThat(isSize(goodIter, goodList.size())).isTrue();
        assertThat(isSize(goodIter, goodList.size() + 1)).isFalse();
        assertThat(isSize(goodIter, goodList.size() - 1)).isFalse();
        assertThat(isSize(emptyIter, 0)).isTrue();
        assertThat(isSize(emptyIter, 1)).isFalse();
        assertThat(isSize(nullIter, 1)).isFalse();
        assertThat(isSize(nullIter, 0)).isFalse(); // null has no size, it is null

        assertThat(isSizeGreaterThan(goodIter, goodList.size() - 1)).isTrue();
        assertThat(isSizeGreaterThan(goodIter, goodList.size())).isFalse();
        assertThat(isSizeGreaterThan(goodIter, goodList.size() + 1)).isFalse();
        assertThat(isSizeGreaterThan(emptyIter, 1)).isFalse();
        assertThat(isSizeGreaterThan(nullIter, 1)).isFalse();
        assertThat(isSizeGreaterThan(nullIter, 0)).isFalse(); // null has no size, it is null

        assertThat(isSizeGreaterOrEqualTo(goodIter, goodList.size())).isTrue();
        assertThat(isSizeGreaterOrEqualTo(goodIter, goodList.size() + 1)).isFalse();
        assertThat(isSizeGreaterOrEqualTo(goodIter, goodList.size() - 1)).isTrue();
        assertThat(isSizeGreaterOrEqualTo(goodIter, 1)).isTrue();
        assertThat(isSizeGreaterOrEqualTo(emptyIter, 1)).isFalse();
        assertThat(isSizeGreaterOrEqualTo(emptyIter, 0)).isTrue();
        assertThat(isSizeGreaterOrEqualTo(nullIter, 1)).isFalse();
        assertThat(isSizeGreaterOrEqualTo(nullIter, 0)).isFalse(); // null has no size, it is null

        assertThat(isSizeLessThan(goodIter, goodList.size() + 1)).isTrue();
        assertThat(isSizeLessThan(goodIter, goodList.size())).isFalse();
        assertThat(isSizeLessThan(goodIter, goodList.size() - 1)).isFalse();
        assertThat(isSizeLessThan(emptyIter, 1)).isTrue();
        assertThat(isSizeLessThan(nullIter, 1)).isFalse();
        assertThat(isSizeLessThan(nullIter, 0)).isFalse(); // null has no size, it is null

        assertThat(isSizeLessOrEqualTo(goodIter, goodList.size())).isTrue();
        assertThat(isSizeLessOrEqualTo(goodIter, goodList.size() + 1)).isTrue();
        assertThat(isSizeLessOrEqualTo(goodIter, goodList.size() - 1)).isFalse();
        assertThat(isSizeLessOrEqualTo(emptyIter, 1)).isTrue();
        assertThat(isSizeLessOrEqualTo(nullIter, 1)).isFalse();
        assertThat(isSizeLessOrEqualTo(nullIter, 0)).isFalse(); // null has no size, it is null
    }

    @Test
    void hasSize_map() {

        assertThat(isSize(goodMap, goodMap.size())).isTrue();
        assertThat(isSize(goodMap, goodMap.size() + 1)).isFalse();
        assertThat(isSize(goodMap, goodMap.size() - 1)).isFalse();
        assertThat(isSize(emptyMap, 0)).isTrue();
        assertThat(isSize(emptyMap, 1)).isFalse();
        assertThat(isSize(nullMap, 1)).isFalse();
        assertThat(isSize(nullMap, 0)).isFalse(); // null has no size, it is null

        assertThat(isSizeGreaterThan(goodMap, goodMap.size() - 1)).isTrue();
        assertThat(isSizeGreaterThan(goodMap, goodMap.size())).isFalse();
        assertThat(isSizeGreaterThan(goodMap, goodMap.size() + 1)).isFalse();
        assertThat(isSizeGreaterThan(emptyMap, 1)).isFalse();
        assertThat(isSizeGreaterThan(nullMap, 1)).isFalse();
        assertThat(isSizeGreaterThan(nullMap, 0)).isFalse(); // null has no size, it is null

        assertThat(isSizeGreaterOrEqualTo(goodMap, goodMap.size())).isTrue();
        assertThat(isSizeGreaterOrEqualTo(goodMap, goodMap.size() + 1)).isFalse();
        assertThat(isSizeGreaterOrEqualTo(goodMap, goodMap.size() - 1)).isTrue();
        assertThat(isSizeGreaterOrEqualTo(goodMap, 1)).isTrue();
        assertThat(isSizeGreaterOrEqualTo(emptyMap, 0)).isTrue();
        assertThat(isSizeGreaterOrEqualTo(emptyMap, 1)).isFalse();
        assertThat(isSizeGreaterOrEqualTo(nullMap, 1)).isFalse();
        assertThat(isSizeGreaterOrEqualTo(nullMap, 0)).isFalse(); // null has no size, it is null

        assertThat(isSizeLessThan(goodMap, goodMap.size() + 1)).isTrue();
        assertThat(isSizeLessThan(goodMap, goodMap.size())).isFalse();
        assertThat(isSizeLessThan(goodMap, goodMap.size() - 1)).isFalse();
        assertThat(isSizeLessThan(emptyMap, 1)).isTrue();
        assertThat(isSizeLessThan(nullMap, 1)).isFalse();
        assertThat(isSizeLessThan(nullMap, 0)).isFalse(); // null has no size, it is null

        assertThat(isSizeLessOrEqualTo(goodMap, goodMap.size())).isTrue();
        assertThat(isSizeLessOrEqualTo(goodMap, goodMap.size() + 1)).isTrue();
        assertThat(isSizeLessOrEqualTo(goodMap, goodMap.size() - 1)).isFalse();
        assertThat(isSizeLessOrEqualTo(emptyMap, 1)).isTrue();
        assertThat(isSizeLessOrEqualTo(emptyMap, 0)).isTrue();
        assertThat(isSizeLessOrEqualTo(nullMap, 1)).isFalse();
        assertThat(isSizeLessOrEqualTo(nullMap, 0)).isFalse(); // null has no size, it is null
    }

    @Test
    void pathExists_path() {
        assertThat(Check.pathExists(goodPathFile)).isTrue();
        assertThat(Check.pathExists(goodPathDir)).isTrue();
        assertThat(Check.pathExists(badPathFile)).isFalse();
        assertThat(Check.pathExists(nullPath)).isFalse();
    }

    @Test
    void pathExists_file() {
        assertThat(Check.pathExists(goodFileFile)).isTrue();
        assertThat(Check.pathExists(goodFileDir)).isTrue();
        assertThat(Check.pathExists(badFileFile)).isFalse();
        assertThat(Check.pathExists(nullFile)).isFalse();
    }

    @Test
    void pathExists_string() {
        assertThat(pathExists(GOOD_FILE_STR)).isTrue();
        assertThat(pathExists(GOOD_DIR_STR)).isTrue();
        assertThat(pathExists(BAD)).isFalse();
        assertThat(pathExists(nullStr)).isFalse();
    }

    @Test
    void pathNotExists_path() {
        assertThat(notExists(goodPathFile)).isFalse();
        assertThat(notExists(goodPathDir)).isFalse();
        assertThat(notExists(badPathFile)).isTrue();
        assertThat(notExists(nullPath)).isTrue();
    }

    @Test
    void pathNotExists_file() {
        assertThat(notExists(goodFileFile)).isFalse();
        assertThat(notExists(goodFileDir)).isFalse();
        assertThat(notExists(badFileFile)).isTrue();
        assertThat(notExists(nullFile)).isTrue();
    }

    @Test
    void pathNotExists_string() {
        assertThat(notExists(GOOD_FILE_STR)).isFalse();
        assertThat(notExists(GOOD_DIR_STR)).isFalse();
        assertThat(notExists(BAD)).isTrue();
        assertThat(notExists(nullStr)).isTrue();
    }

    @Test
    void isRegularFile_path() {
        assertThat(Check.isRegularFile(goodPathFile)).isTrue();
        assertThat(Check.isRegularFile(goodPathDir)).isFalse();
        assertThat(Check.isRegularFile(badPathFile)).isFalse();
        assertThat(Check.isRegularFile(nullPath)).isFalse();
    }

    @Test
    void isRegularFile_file() {
        assertThat(Check.isRegularFile(goodFileFile)).isTrue();
        assertThat(Check.isRegularFile(goodFileDir)).isFalse();
        assertThat(Check.isRegularFile(badFileFile)).isFalse();
        assertThat(Check.isRegularFile(nullFile)).isFalse();
    }

    @Test
    void isRegularFile_string() {
        assertThat(Check.isRegularFile(GOOD_FILE_STR)).isTrue();
        assertThat(Check.isRegularFile(GOOD_DIR_STR)).isFalse();
        assertThat(Check.isRegularFile(BAD)).isFalse();
        assertThat(Check.isRegularFile(nullStr)).isFalse();
    }

    @Test
    void isDirectory_path() {
        assertThat(Check.isDirectory(goodPathFile)).isFalse();
        assertThat(Check.isDirectory(goodPathDir)).isTrue();
        assertThat(Check.isDirectory(badPathFile)).isFalse();
        assertThat(Check.isDirectory(nullPath)).isFalse();
    }

    @Test
    void isDirectory_file() {
        assertThat(Check.isDirectory(goodFileDir)).isTrue();
        assertThat(Check.isDirectory(badFileDir)).isFalse();
        assertThat(Check.isDirectory(nullFile)).isFalse();
        assertThat(Check.isDirectory(goodFileFile)).isFalse();
    }

    @Test
    void isDirectory_string() {
        assertThat(Check.isDirectory(GOOD_FILE_STR)).isFalse();
        assertThat(Check.isDirectory(GOOD_DIR_STR)).isTrue();
        assertThat(Check.isDirectory(BAD)).isFalse();
        assertThat(Check.isDirectory(nullStr)).isFalse();
    }

    @Test
    void isFuture_zonedDateTime() {
        assertThat(isFuture(zdtFuture)).isTrue();
        assertThat(isFuture(zdtPast)).isFalse();
        assertThat(isFuture(zdtNull)).isFalse();
    }

    @Test
    void isFuture_offsetDateTime() {
        assertThat(isFuture(odtFuture)).isTrue();
        assertThat(isFuture(odtPast)).isFalse();
        assertThat(isFuture(odtNull)).isFalse();
    }

    @Test
    void isFuture_localDateTime() {
        assertThat(isFuture(ldtFuture)).isTrue();
        assertThat(isFuture(ldtPast)).isFalse();
        assertThat(isFuture(ldtNull)).isFalse();
    }

    @Test
    void isFuture_localDate() {
        assertThat(isFuture(ldFuture)).isTrue();
        assertThat(isFuture(ldPast)).isFalse();
        assertThat(isFuture(ldNull)).isFalse();
    }

    @Test
    void isFuture_localTime() {
        assertThat(isFuture(ltFuture)).isTrue();
        assertThat(isFuture(ltPast)).isFalse();
        assertThat(isFuture(ltNull)).isFalse();
    }

    @Test
    void isPast_zonedDateTime() {
        assertThat(isPast(zdtFuture)).isFalse();
        assertThat(isPast(zdtPast)).isTrue();
        assertThat(isPast(zdtNull)).isFalse();
    }

    @Test
    void isPast_offsetDateTime() {
        assertThat(isPast(odtFuture)).isFalse();
        assertThat(isPast(odtPast)).isTrue();
        assertThat(isPast(odtNull)).isFalse();
    }

    @Test
    void isPast_localDateTime() {
        assertThat(isPast(ldtFuture)).isFalse();
        assertThat(isPast(ldtPast)).isTrue();
        assertThat(isPast(ldtNull)).isFalse();
    }

    @Test
    void isPast_localDate() {
        assertThat(isPast(ldFuture)).isFalse();
        assertThat(isPast(ldPast)).isTrue();
        assertThat(isPast(ldNull)).isFalse();
    }

    @Test
    void isPast_localTime() {
        assertThat(isPast(ltFuture)).isFalse();
        assertThat(isPast(ltPast)).isTrue();
        assertThat(isPast(ltNull)).isFalse();
    }

    @Test
    public void testIsAssignableFrom() {

        // String is a subclass of CharSequence.
        assertThat(goodStr).isInstanceOf(String.class);
        assertThat(goodStr).isInstanceOf(CharSequence.class);

        CharSequence charSeq = new StringBuilder();
        assertThat(charSeq).isNotInstanceOf(String.class);

        // String is a subclass of CharSequence.
        assertThat(CharSequence.class.isAssignableFrom(String.class)).isTrue();
        assertThat(String.class.isAssignableFrom(CharSequence.class)).isFalse();
        // What about same type?
        assertThat(CharSequence.class.isAssignableFrom(CharSequence.class)).isTrue();
    }

    @Test
    void instanceOf() {
        assertThat(Check.isInstanceOf(goodStr, CharSequence.class)).isTrue();
        assertThat(Check.isInstanceOf(goodStr, Map.class)).isFalse();
    }

    @Test
    void notInstanceOf() {
        assertThat(Check.notInstanceOf(goodStr, CharSequence.class)).isFalse();
        assertThat(Check.notInstanceOf(goodStr, Map.class)).isTrue();
    }

    @Test
    void contains_string() {
        assertThat(contains(goodStr, "")).isTrue();
        assertThat(contains(goodStr, "good")).isTrue();
        assertThat(contains(goodStr, "go")).isTrue();
        assertThat(contains(goodStr, "od")).isTrue();
        assertThat(contains(goodStr, "x")).isFalse();
        assertThat(contains(emptyStr, "")).isTrue();
        assertThat(contains(nullStr, "")).isFalse();
    }

    @Test
    void testContainsAnyMatch() {
        assertThat(Check.containsElement(elem -> elem.equals("a"), goodList)).isTrue();
        assertThat(Check.containsElement(elem -> elem.equals("BAD"), goodList)).isFalse();
        assertThat(Check.containsElement(elem -> elem.equals("a"), nullList)).isFalse();
    }

    @Test
    void testContainsRegex() {
        assertThat(containsRegex(goodStr, "")).isTrue();
        assertThat(containsRegex(goodStr, "o{2,}")).isTrue();
        assertThat(containsRegex(goodStr, "good")).isTrue();
        assertThat(containsRegex(goodStr, "go")).isTrue();
        assertThat(containsRegex(goodStr, "od")).isTrue();
        assertThat(containsRegex(goodStr, "x")).isFalse();
        assertThat(containsRegex(goodStr, "[x]")).isFalse();
        assertThat(containsRegex(goodStr, "")).isTrue();
        assertThat(containsRegex(emptyStr, "")).isTrue();
        assertThat(containsRegex(nullStr, "")).isFalse();
    }

    @Test
    void testMatchesRegex() {
        assertThat(matchesRegex(goodStr, "go+d")).isTrue();
        assertThat(matchesRegex(goodStr, "o+")).isFalse();
        assertThat(matchesRegex(goodStr, "")).isFalse();
        assertThat(matchesRegex(emptyStr, "")).isTrue(); // Interesting — empty str matches empty regex
        assertThat(matchesRegex(nullStr, "")).isFalse();
    }

    @Test
    void contains_map() {
        assertThat(containsKey(goodMap, "a")).isTrue();
        assertThat(containsKey(goodMap, "x")).isFalse();
        assertThat(containsKey(nullMap, "x")).isFalse();
        assertThat(containsKey(goodMap, null)).isFalse();
        assertThat(containsValue(goodMap, 1)).isTrue();
        assertThat(containsValue(goodMap, 9)).isFalse();
        assertThat(containsValue(nullMap, 9)).isFalse();
    }

    @Test
    void testContainsUniqueElements() {
        assertThat(containsUniqueElements(Arrays.asList(1, 2, 3))).isTrue();
        assertThat(containsUniqueElements(Arrays.asList(1, 2, 3, 1))).isFalse();
        assertThat(containsUniqueElements(Arrays.asList(null, 2, 3, null))).isFalse();
        assertThat(containsUniqueElements(nullList)).isFalse();
    }

    @Test
    void testContainsNull_collection() {
        assertThat(containsNull(Arrays.asList(1, 2, 3))).isFalse();
        assertThat(containsNull(Arrays.asList(1, 2, 3, null))).isTrue();
        assertThat(containsNull(nullList)).isFalse();
    }

    @Test
    void containsNull_iterable() {
        assertThat(containsNull((Iterable<?>) Arrays.asList(1, 2, 3))).isFalse();
        assertThat(containsNull((Iterable<?>) Arrays.asList(1, 2, 3, null))).isTrue();
        assertThat(containsNull((Iterable<?>) nullList)).isFalse();
    }

    @Test
    void containsNull_array() {
        assertThat(containsNull(new Integer[] {1, 2, 3})).isFalse();
        assertThat(containsNull(new Integer[] {1, 2, 3, null})).isTrue();
        assertThat(containsNull(nullArray)).isFalse();
    }

    @Test
    void containsNull_map() {
        assertThat(containsNull(nullInsideMapKey)).isTrue();
        assertThat(containsNull(goodMap)).isFalse();
        assertThat(containsNull(nullInsideMapValue)).isTrue();
        assertThat(containsNull(nullMap)).isFalse();
    }

    @Test
    void notContainsNull_collection() {
        assertThat(notContainsNull(Arrays.asList(1, 2, 3))).isTrue();
        assertThat(notContainsNull(Arrays.asList(1, 2, 3, null))).isFalse();
        assertThat(notContainsNull(nullList)).isFalse();
    }

    @Test
    void notContainsNull_iterable() {
        assertThat(notContainsNull((Iterable<?>) Arrays.asList(1, 2, 3))).isTrue();
        assertThat(notContainsNull((Iterable<?>) Arrays.asList(1, 2, 3, null))).isFalse();
        assertThat(notContainsNull((Iterable<?>) nullList)).isFalse();
    }

    @Test
    void notContainsNull_array() {
        assertThat(notContainsNull(new Integer[] {1, 2, 3})).isTrue();
        assertThat(notContainsNull(new Integer[] {1, 2, 3, null})).isFalse();
        assertThat(notContainsNull(nullArray)).isFalse();
    }

    @Test
    void notContainsNull_map() {
        assertThat(notContainsNull(nullInsideMapKey)).isFalse();
        assertThat(notContainsNull(goodMap)).isTrue();
        assertThat(notContainsNull(nullInsideMapValue)).isFalse();
        assertThat(notContainsNull(nullMap)).isFalse();
    }

    @Test
    void testIsEmail() {
        assertThat(isEmail("x")).isFalse();
        assertThat(isEmail("x@")).isFalse();
        assertThat(isEmail("x@y")).isFalse();
        assertThat(isEmail("hello@@world.com")).isFalse();
        assertThat(isEmail("hello@world..com")).isFalse();
        assertThat(isEmail("hello@world.com")).isTrue();
        assertThat(isEmail("hello@world.co.uk")).isTrue();
    }

    @Test
    void testIsUrl() {
        assertThat(isUrl("www.google.com")).isFalse();
        assertThat(isUrl("http://")).isFalse();
        assertThat(isUrl("ttp://www.google.com")).isFalse();
        assertThat(isUrl("http://www.google.com")).isTrue();
        assertThat(isUrl("https://www.google.com")).isTrue();
    }

    @Test
    void testIsIpAddress() {
        assertThat(isIpAddress("0.0.0.0")).isTrue();
        assertThat(isIpAddress("255.255.255.255")).isTrue();
        assertThat(isIpAddress("0.0.0.")).isFalse();
        assertThat(isIpAddress("255.255.255.")).isFalse();
    }

    @Test
    void testIsIpv6Address() {
        assertThat(isIPv6Address("2001:0db8:85a3:0000:0000:8a2e:0370:7334")).isTrue();
        assertThat(isIPv6Address("2001:db8:85a3:0:0:8a2e:370:7334")).isTrue();
        assertThat(isIPv6Address("2001:db8:85a3::8a2e:370:7334")).isTrue();
        assertThat(isIPv6Address("2001:db8:85a3::8a2e:370.7334")).isFalse();
        assertThat(isIPv6Address("2001:db8:85a3::8a2e:370:")).isFalse();
    }

    @Test
    void testIsJson() {
        assertThat(isJson("")).isFalse();
        assertThat(isJson(" ")).isFalse();
        assertThat(isJson("x")).isFalse();
        assertThat(isJson("[]")).isTrue();
        assertThat(isJson("{}")).isTrue();
        assertThat(isJson("[1,2,3]")).isTrue();
        assertThat(isJson("{\"a\":1,\"b\":2}")).isTrue();
    }

    @Test
    void testIsXml() {
        assertThat(isXml("")).isFalse();
        assertThat(isXml(" ")).isFalse();
        assertThat(isXml("x")).isFalse();
        assertThat(isXml("<a></a>")).isTrue();
        assertThat(isXml("<a><b></b></a>")).isTrue();
    }

    @Test
    void testIsUUID() {
        assertThat(isUUID("")).isFalse();
        assertThat(isUUID(" ")).isFalse();
        assertThat(isUUID("x")).isFalse();
        assertThat(isUUID("00000000-0000-0000-0000-000000000000")).isTrue();
        assertThat(isUUID("00000000-0000-0000-0000-00000000000")).isFalse();
        assertThat(isUUID("00000000-0000-0000-0000-0000000000000")).isFalse();
        assertThat(isUUID("00000000-0000-0000-0000-00000000000x")).isFalse();
    }
}
