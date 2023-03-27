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
    void testIsGreaterThan() {
        assertThat(isDurationGreaterThan(oneMin, fiveMins)).isTrue();
        assertThat(isDurationGreaterThan(fiveMins, fiveMins)).isFalse();
        assertThat(isDurationGreaterThan(oneMin, nullDuration)).isFalse();
        assertThat(isDurationGreaterThan(nullDuration, nullDuration)).isFalse();
    }

    @Test
    void testDurationIsGreaterOrEqualTo() {
        assertThat(isDurationGreaterOrEqualTo(oneMin, fiveMins)).isTrue();
        assertThat(isDurationGreaterOrEqualTo(fiveMins, fiveMins)).isTrue();
        assertThat(isDurationGreaterOrEqualTo(oneMin, nullDuration)).isFalse();
        assertThat(isDurationGreaterOrEqualTo(nullDuration, nullDuration)).isFalse();
    }

    @Test
    void testDurationIsLessThan() {
        assertThat(isDurationLessThan(oneMin, fiveMins)).isFalse();
        assertThat(isDurationLessThan(fiveMins, fiveMins)).isFalse();
        assertThat(isDurationLessThan(oneMin, nullDuration)).isFalse();
        assertThat(isDurationLessThan(nullDuration, nullDuration)).isFalse();
    }

    @Test
    void testDurationIsLessOrEqualTo() {
        assertThat(isDurationLessOrEqualTo(oneMin, fiveMins)).isFalse();
        assertThat(isDurationLessOrEqualTo(fiveMins, fiveMins)).isTrue();
        assertThat(isDurationLessOrEqualTo(oneMin, nullDuration)).isFalse();
        assertThat(isDurationLessOrEqualTo(nullDuration, nullDuration)).isFalse();
    }

    @Test
    void greaterThan_duration() {
        assertThat(Check.isDurationGreaterThanNanos(1, oneNs)).isFalse();
        assertThat(Check.isDurationGreaterThanNanos(1, fiveNs)).isTrue();
        assertThat(Check.isDurationGreaterThanNanos(1, oneMs)).isTrue();
        assertThat(Check.isDurationGreaterThanMillis(1, oneMs)).isFalse();
        assertThat(Check.isDurationGreaterThanMillis(1, fiveMs)).isTrue();
        assertThat(Check.isDurationGreaterThanMillis(1, oneSec)).isTrue();
        assertThat(Check.isDurationGreaterThanSecs(1, oneSec)).isFalse();
        assertThat(Check.isDurationGreaterThanSecs(1, fiveSecs)).isTrue();
        assertThat(Check.isDurationGreaterThanSecs(1, oneMin)).isTrue();
        assertThat(Check.isDurationGreaterThanMins(1, oneMin)).isFalse();
        assertThat(Check.isDurationGreaterThanMins(1, fiveMins)).isTrue();
        assertThat(Check.isDurationGreaterThanMins(1, oneHour)).isTrue();
        assertThat(Check.isDurationGreaterThanHours(1, oneHour)).isFalse();
        assertThat(Check.isDurationGreaterThanHours(1, fiveHours)).isTrue();
        assertThat(Check.isDurationGreaterThanHours(1, oneDay)).isTrue();
        assertThat(Check.isDurationGreaterThanDays(1, oneDay)).isFalse();
        assertThat(Check.isDurationGreaterThanDays(1, fiveDays)).isTrue();
    }

    @Test
    void greaterOrEqualTo_duration() {
        assertThat(Check.isDurationGreaterOrEqualToNanos(1, oneNs)).isTrue();
        assertThat(Check.isDurationGreaterOrEqualToNanos(10, fiveNs)).isFalse();
        assertThat(Check.isDurationGreaterOrEqualToNanos(1, oneMs)).isTrue();
        assertThat(Check.isDurationGreaterOrEqualToMillis(1, oneMs)).isTrue();
        assertThat(Check.isDurationGreaterOrEqualToMillis(10, fiveMs)).isFalse();
        assertThat(Check.isDurationGreaterOrEqualToMillis(1, oneSec)).isTrue();
        assertThat(Check.isDurationGreaterOrEqualToSecs(1, oneSec)).isTrue();
        assertThat(Check.isDurationGreaterOrEqualToSecs(10, fiveSecs)).isFalse();
        assertThat(Check.isDurationGreaterOrEqualToSecs(1, oneMin)).isTrue();
        assertThat(Check.isDurationGreaterOrEqualToMins(1, oneMin)).isTrue();
        assertThat(Check.isDurationGreaterOrEqualToMins(10, fiveMins)).isFalse();
        assertThat(Check.isDurationGreaterOrEqualToMins(1, oneHour)).isTrue();
        assertThat(Check.isDurationGreaterOrEqualToHours(1, oneHour)).isTrue();
        assertThat(Check.isDurationGreaterOrEqualToHours(10, fiveHours)).isFalse();
        assertThat(Check.isDurationGreaterOrEqualToHours(1, oneDay)).isTrue();
        assertThat(Check.isDurationGreaterOrEqualToDays(1, oneDay)).isTrue();
        assertThat(Check.isDurationGreaterOrEqualToDays(10, fiveDays)).isFalse();
    }

    @Test
    void lessThan_duration() {
        assertThat(isDurationLessThanNanos(1, oneNs)).isFalse();
        assertThat(isDurationLessThanNanos(1, fiveNs)).isFalse();
        assertThat(isDurationLessThanNanos(1, oneMs)).isFalse();
        assertThat(isDurationLessThanMillis(1, oneMs)).isFalse();
        assertThat(isDurationLessThanMillis(1, fiveMs)).isFalse();
        assertThat(isDurationLessThanMillis(1, oneSec)).isFalse();
        assertThat(isDurationLessThanSecs(1, oneSec)).isFalse();
        assertThat(isDurationLessThanSecs(1, fiveSecs)).isFalse();
        assertThat(isDurationLessThanSecs(1, oneMin)).isFalse();
        assertThat(isDurationLessThanMins(1, oneMin)).isFalse();
        assertThat(isDurationLessThanMins(1, fiveMins)).isFalse();
        assertThat(isDurationLessThanMins(1, oneHour)).isFalse();
        assertThat(isDurationLessThanHours(1, oneHour)).isFalse();
        assertThat(isDurationLessThanHours(1, fiveHours)).isFalse();
        assertThat(isDurationLessThanHours(1, oneDay)).isFalse();
        assertThat(isDurationLessThanDays(1, oneDay)).isFalse();
        assertThat(isDurationLessThanDays(1, fiveDays)).isFalse();
    }

    @Test
    void lessOrEqualTo_duration() {
        assertThat(Check.isDurationLessOrEqualToNanos(1, oneNs)).isTrue();
        assertThat(Check.isDurationLessOrEqualToNanos(10, fiveNs)).isTrue();
        assertThat(Check.isDurationLessOrEqualToMillis(1, oneMs)).isTrue();
        assertThat(Check.isDurationLessOrEqualToMillis(10, fiveMs)).isTrue();
        assertThat(Check.isDurationLessOrEqualToMillis(1, oneNs)).isTrue();
        assertThat(Check.isDurationLessOrEqualToSecs(1, oneSec)).isTrue();
        assertThat(Check.isDurationLessOrEqualToSecs(10, fiveSecs)).isTrue();
        assertThat(Check.isDurationLessOrEqualToSecs(1, oneMs)).isTrue();
        assertThat(Check.isDurationLessOrEqualToMins(1, oneMin)).isTrue();
        assertThat(Check.isDurationLessOrEqualToMins(10, fiveMins)).isTrue();
        assertThat(Check.isDurationLessOrEqualToMins(1, oneSec)).isTrue();
        assertThat(Check.isDurationLessOrEqualToHours(1, oneHour)).isTrue();
        assertThat(Check.isDurationLessOrEqualToHours(10, fiveHours)).isTrue();
        assertThat(Check.isDurationLessOrEqualToHours(1, oneMin)).isTrue();
        assertThat(Check.isDurationLessOrEqualToDays(1, oneDay)).isTrue();
        assertThat(Check.isDurationLessOrEqualToDays(10, fiveDays)).isTrue();
        assertThat(Check.isDurationLessOrEqualToDays(1, oneHour)).isTrue();
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

        assertThat(Check.isLength(goodStr.length(), goodStr)).isTrue();
        assertThat(Check.isLength(goodStr.length() + 1, goodStr)).isFalse();
        assertThat(Check.isLength(goodStr.length() - 1, goodStr)).isFalse();
        assertThat(Check.isLength(0, emptyStr)).isTrue();
        assertThat(Check.isLength(1, emptyStr)).isFalse();
        assertThat(Check.isLength(1, nullStr)).isFalse();
        assertThat(Check.isLength(0, nullStr)).isFalse(); // null has no length, it is null

        assertThat(Check.isLengthGreaterThan(goodStr.length() - 1, goodStr)).isTrue();
        assertThat(Check.isLengthGreaterThan(goodStr.length(), goodStr)).isFalse();
        assertThat(Check.isLengthGreaterThan(goodStr.length() + 1, goodStr)).isFalse();
        assertThat(Check.isLengthGreaterThan(1, blankStr)).isTrue();
        assertThat(Check.isLengthGreaterThan(0, emptyStr)).isFalse();
        assertThat(Check.isLengthGreaterThan(1, nullStr)).isFalse();
        assertThat(Check.isLengthGreaterThan(0, nullStr)).isFalse(); // null has no length, it is null

        assertThat(Check.isLengthGreaterOrEqualTo(goodStr.length(), goodStr)).isTrue();
        assertThat(Check.isLengthGreaterOrEqualTo(goodStr.length() + 1, goodStr)).isFalse();
        assertThat(Check.isLengthGreaterOrEqualTo(goodStr.length() - 1, goodStr)).isTrue();
        assertThat(Check.isLengthGreaterOrEqualTo(1, blankStr)).isTrue();
        assertThat(Check.isLengthGreaterOrEqualTo(1, emptyStr)).isFalse();
        assertThat(Check.isLengthGreaterOrEqualTo(1, nullStr)).isFalse();
        assertThat(Check.isLengthGreaterOrEqualTo(0, nullStr)).isFalse(); // null has no length, it is null

        assertThat(Check.isLengthLessThan(goodStr.length() + 1, goodStr)).isTrue();
        assertThat(Check.isLengthLessThan(goodStr.length(), goodStr)).isFalse();
        assertThat(Check.isLengthLessThan(goodStr.length() - 1, goodStr)).isFalse();
        assertThat(Check.isLengthLessThan(1, blankStr)).isFalse();
        assertThat(Check.isLengthLessThan(1, emptyStr)).isTrue();
        assertThat(Check.isLengthLessThan(0, emptyStr)).isFalse();
        assertThat(Check.isLengthLessThan(1, nullStr)).isFalse();
        assertThat(Check.isLengthLessThan(0, nullStr)).isFalse(); // null has no length, it is null

        assertThat(Check.isLengthLessOrEqualTo(goodStr.length(), goodStr)).isTrue();
        assertThat(Check.isLengthLessOrEqualTo(goodStr.length() + 1, goodStr)).isTrue();
        assertThat(Check.isLengthLessOrEqualTo(goodStr.length() - 1, goodStr)).isFalse();
        assertThat(Check.isLengthLessOrEqualTo(1, blankStr)).isFalse();
        assertThat(Check.isLengthLessOrEqualTo(1, emptyStr)).isTrue();
        assertThat(Check.isLengthLessOrEqualTo(1, nullStr)).isFalse();
        assertThat(Check.isLengthLessOrEqualTo(0, nullStr)).isFalse(); // null has no length, it is null
    }

    @Test
    void hasLength_array() {

        assertThat(Check.isLength(goodArray.length, goodArray)).isTrue(); // test max inclusive
        assertThat(Check.isLength(goodArray.length + 1, goodArray)).isFalse(); // plus or minus 1
        assertThat(Check.isLength(goodArray.length - 1, goodArray)).isFalse(); // is wrong
        assertThat(Check.isLength(0, emptyArray)).isTrue(); // test empty
        assertThat(Check.isLength(1, emptyArray)).isFalse();
        assertThat(Check.isLength(0, nullArray)).isFalse(); // null has no length, it is null
        assertThat(Check.isLength(1, nullArray)).isFalse(); // test null
        assertThat(Check.isLengthGreaterThan(goodArray.length - 1, goodArray)).isTrue();
        assertThat(Check.isLengthGreaterThan(goodArray.length, goodArray)).isFalse();
        assertThat(Check.isLengthGreaterThan(0, emptyArray)).isFalse();
        assertThat(Check.isLengthGreaterThan(0, nullArray)).isFalse(); // null has no length, it is null

        assertThat(Check.isLengthGreaterOrEqualTo(goodArray.length, goodArray)).isTrue();
        assertThat(Check.isLengthGreaterOrEqualTo(goodArray.length + 1, goodArray)).isFalse();
        assertThat(Check.isLengthGreaterOrEqualTo(goodArray.length - 1, goodArray)).isTrue();
        assertThat(Check.isLengthGreaterOrEqualTo(0, emptyArray)).isTrue();
        assertThat(Check.isLengthGreaterOrEqualTo(1, emptyArray)).isFalse();
        assertThat(Check.isLengthGreaterOrEqualTo(0, nullArray)).isFalse(); // null has no length, it is null

        assertThat(Check.isLengthLessThan(goodArray.length + 1, goodArray)).isTrue();
        assertThat(Check.isLengthLessThan(goodArray.length, goodArray)).isFalse();
        assertThat(Check.isLengthLessThan(1, emptyArray)).isTrue();
        assertThat(Check.isLengthLessThan(0, emptyArray)).isFalse();
        assertThat(Check.isLengthLessThan(1, nullArray)).isFalse();
        assertThat(Check.isLengthLessThan(0, nullArray)).isFalse(); // null has no length, it is null

        assertThat(Check.isLengthLessOrEqualTo(goodArray.length, goodArray)).isTrue();
        assertThat(Check.isLengthLessOrEqualTo(goodArray.length + 1, goodArray)).isTrue();
        assertThat(Check.isLengthLessOrEqualTo(goodArray.length - 1, goodArray)).isFalse();
        assertThat(Check.isLengthLessOrEqualTo(1, emptyArray)).isTrue();
        assertThat(Check.isLengthLessOrEqualTo(0, emptyArray)).isTrue();
        assertThat(Check.isLengthLessOrEqualTo(1, nullArray)).isFalse();
        assertThat(Check.isLengthLessOrEqualTo(0, nullArray)).isFalse(); // null has no length, it is null
    }

    @Test
    void hasSize_collection() {

        assertThat(isSize(goodList.size(), goodList)).isTrue();
        assertThat(isSize(goodList.size() + 1, goodList)).isFalse();
        assertThat(isSize(goodList.size() - 1, goodList)).isFalse();
        assertThat(isSize(0, emptyList)).isTrue();
        assertThat(isSize(1, emptyList)).isFalse();
        assertThat(isSize(1, nullList)).isFalse();
        assertThat(isSize(0, nullList)).isFalse(); // null has no size, it is null

        assertThat(isSizeGreaterThan(goodList.size() - 1, goodList)).isTrue();
        assertThat(isSizeGreaterThan(goodList.size(), goodList)).isFalse();
        assertThat(isSizeGreaterThan(goodList.size() + 1, goodList)).isFalse();
        assertThat(isSizeGreaterThan(1, emptyList)).isFalse();
        assertThat(isSizeGreaterThan(1, nullList)).isFalse();
        assertThat(isSizeGreaterThan(0, nullList)).isFalse(); // null has no size, it is null

        assertThat(isSizeGreaterOrEqualTo(goodList.size(), goodList)).isTrue();
        assertThat(isSizeGreaterOrEqualTo(goodList.size() + 1, goodList)).isFalse();
        assertThat(isSizeGreaterOrEqualTo(goodList.size() - 1, goodList)).isTrue();
        assertThat(isSizeGreaterOrEqualTo(1, goodList)).isTrue();
        assertThat(isSizeGreaterOrEqualTo(1, emptyList)).isFalse();
        assertThat(isSizeGreaterOrEqualTo(0, emptyList)).isTrue();
        assertThat(isSizeGreaterOrEqualTo(1, nullList)).isFalse();
        assertThat(isSizeGreaterOrEqualTo(0, nullList)).isFalse(); // null has no size, it is null

        assertThat(isSizeLessThan(goodList.size() + 1, goodList)).isTrue();
        assertThat(isSizeLessThan(goodList.size(), goodList)).isFalse();
        assertThat(isSizeLessThan(goodList.size() - 1, goodList)).isFalse();
        assertThat(isSizeLessThan(1, emptyList)).isTrue();
        assertThat(isSizeLessThan(1, nullList)).isFalse();
        assertThat(isSizeLessThan(0, nullList)).isFalse(); // null has no size, it is null

        assertThat(isSizeLessOrEqualTo(goodList.size(), goodList)).isTrue();
        assertThat(isSizeLessOrEqualTo(goodList.size() + 1, goodList)).isTrue();
        assertThat(isSizeLessOrEqualTo(goodList.size() - 1, goodList)).isFalse();
        assertThat(isSizeLessOrEqualTo(1, emptyList)).isTrue();
        assertThat(isSizeLessOrEqualTo(1, nullList)).isFalse();
        assertThat(isSizeLessOrEqualTo(0, nullList)).isFalse(); // null has no size, it is null
    }

    @Test
    void hasSize_iterable() {

        assertThat(isSize(goodList.size(), goodIter)).isTrue();
        assertThat(isSize(goodList.size() + 1, goodIter)).isFalse();
        assertThat(isSize(goodList.size() - 1, goodIter)).isFalse();
        assertThat(isSize(0, emptyIter)).isTrue();
        assertThat(isSize(1, emptyIter)).isFalse();
        assertThat(isSize(1, nullIter)).isFalse();
        assertThat(isSize(0, nullIter)).isFalse(); // null has no size, it is null

        assertThat(isSizeGreaterThan(goodList.size() - 1, goodIter)).isTrue();
        assertThat(isSizeGreaterThan(goodList.size(), goodIter)).isFalse();
        assertThat(isSizeGreaterThan(goodList.size() + 1, goodIter)).isFalse();
        assertThat(isSizeGreaterThan(1, emptyIter)).isFalse();
        assertThat(isSizeGreaterThan(1, nullIter)).isFalse();
        assertThat(isSizeGreaterThan(0, nullIter)).isFalse(); // null has no size, it is null

        assertThat(isSizeGreaterOrEqualTo(goodList.size(), goodIter)).isTrue();
        assertThat(isSizeGreaterOrEqualTo(goodList.size() + 1, goodIter)).isFalse();
        assertThat(isSizeGreaterOrEqualTo(goodList.size() - 1, goodIter)).isTrue();
        assertThat(isSizeGreaterOrEqualTo(1, goodIter)).isTrue();
        assertThat(isSizeGreaterOrEqualTo(1, emptyIter)).isFalse();
        assertThat(isSizeGreaterOrEqualTo(0, emptyIter)).isTrue();
        assertThat(isSizeGreaterOrEqualTo(1, nullIter)).isFalse();
        assertThat(isSizeGreaterOrEqualTo(0, nullIter)).isFalse(); // null has no size, it is null

        assertThat(isSizeLessThan(goodList.size() + 1, goodIter)).isTrue();
        assertThat(isSizeLessThan(goodList.size(), goodIter)).isFalse();
        assertThat(isSizeLessThan(goodList.size() - 1, goodIter)).isFalse();
        assertThat(isSizeLessThan(1, emptyIter)).isTrue();
        assertThat(isSizeLessThan(1, nullIter)).isFalse();
        assertThat(isSizeLessThan(0, nullIter)).isFalse(); // null has no size, it is null

        assertThat(isSizeLessOrEqualTo(goodList.size(), goodIter)).isTrue();
        assertThat(isSizeLessOrEqualTo(goodList.size() + 1, goodIter)).isTrue();
        assertThat(isSizeLessOrEqualTo(goodList.size() - 1, goodIter)).isFalse();
        assertThat(isSizeLessOrEqualTo(1, emptyIter)).isTrue();
        assertThat(isSizeLessOrEqualTo(1, nullIter)).isFalse();
        assertThat(isSizeLessOrEqualTo(0, nullIter)).isFalse(); // null has no size, it is null
    }

    @Test
    void hasSize_map() {

        assertThat(isSize(goodMap.size(), goodMap)).isTrue();
        assertThat(isSize(goodMap.size() + 1, goodMap)).isFalse();
        assertThat(isSize(goodMap.size() - 1, goodMap)).isFalse();
        assertThat(isSize(0, emptyMap)).isTrue();
        assertThat(isSize(1, emptyMap)).isFalse();
        assertThat(isSize(1, nullMap)).isFalse();
        assertThat(isSize(0, nullMap)).isFalse(); // null has no size, it is null

        assertThat(isSizeGreaterThan(goodMap.size() - 1, goodMap)).isTrue();
        assertThat(isSizeGreaterThan(goodMap.size(), goodMap)).isFalse();
        assertThat(isSizeGreaterThan(goodMap.size() + 1, goodMap)).isFalse();
        assertThat(isSizeGreaterThan(1, emptyMap)).isFalse();
        assertThat(isSizeGreaterThan(1, nullMap)).isFalse();
        assertThat(isSizeGreaterThan(0, nullMap)).isFalse(); // null has no size, it is null

        assertThat(isSizeGreaterOrEqualTo(goodMap.size(), goodMap)).isTrue();
        assertThat(isSizeGreaterOrEqualTo(goodMap.size() + 1, goodMap)).isFalse();
        assertThat(isSizeGreaterOrEqualTo(goodMap.size() - 1, goodMap)).isTrue();
        assertThat(isSizeGreaterOrEqualTo(1, goodMap)).isTrue();
        assertThat(isSizeGreaterOrEqualTo(0, emptyMap)).isTrue();
        assertThat(isSizeGreaterOrEqualTo(1, emptyMap)).isFalse();
        assertThat(isSizeGreaterOrEqualTo(1, nullMap)).isFalse();
        assertThat(isSizeGreaterOrEqualTo(0, nullMap)).isFalse(); // null has no size, it is null

        assertThat(isSizeLessThan(goodMap.size() + 1, goodMap)).isTrue();
        assertThat(isSizeLessThan(goodMap.size(), goodMap)).isFalse();
        assertThat(isSizeLessThan(goodMap.size() - 1, goodMap)).isFalse();
        assertThat(isSizeLessThan(1, emptyMap)).isTrue();
        assertThat(isSizeLessThan(1, nullMap)).isFalse();
        assertThat(isSizeLessThan(0, nullMap)).isFalse(); // null has no size, it is null

        assertThat(isSizeLessOrEqualTo(goodMap.size(), goodMap)).isTrue();
        assertThat(isSizeLessOrEqualTo(goodMap.size() + 1, goodMap)).isTrue();
        assertThat(isSizeLessOrEqualTo(goodMap.size() - 1, goodMap)).isFalse();
        assertThat(isSizeLessOrEqualTo(1, emptyMap)).isTrue();
        assertThat(isSizeLessOrEqualTo(0, emptyMap)).isTrue();
        assertThat(isSizeLessOrEqualTo(1, nullMap)).isFalse();
        assertThat(isSizeLessOrEqualTo(0, nullMap)).isFalse(); // null has no size, it is null
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
        assertThat(Check.isInstanceOf(CharSequence.class, goodStr)).isTrue();
        assertThat(Check.isInstanceOf(Map.class, goodStr)).isFalse();
    }

    @Test
    void notInstanceOf() {
        assertThat(Check.notInstanceOf(CharSequence.class, goodStr)).isFalse();
        assertThat(Check.notInstanceOf(Map.class, goodStr)).isTrue();
    }

    @Test
    void contains_string() {
        assertThat(contains("", goodStr)).isTrue();
        assertThat(contains("good", goodStr)).isTrue();
        assertThat(contains("go", goodStr)).isTrue();
        assertThat(contains("od", goodStr)).isTrue();
        assertThat(contains("x", goodStr)).isFalse();
        assertThat(contains("", emptyStr)).isTrue();
        assertThat(contains("", nullStr)).isFalse();
    }

    @Test
    void testContainsAnyMatch() {
        assertThat(Check.containsElement(elem -> elem.equals("a"), goodList)).isTrue();
        assertThat(Check.containsElement(elem -> elem.equals("BAD"), goodList)).isFalse();
        assertThat(Check.containsElement(elem -> elem.equals("a"), nullList)).isFalse();
    }

    @Test
    void testContainsRegex() {
        assertThat(containsRegex("", goodStr)).isTrue();
        assertThat(containsRegex("o{2,}", goodStr)).isTrue();
        assertThat(containsRegex("good", goodStr)).isTrue();
        assertThat(containsRegex("go", goodStr)).isTrue();
        assertThat(containsRegex("od", goodStr)).isTrue();
        assertThat(containsRegex("x", goodStr)).isFalse();
        assertThat(containsRegex("[x]", goodStr)).isFalse();
        assertThat(containsRegex("", goodStr)).isTrue();
        assertThat(containsRegex("", emptyStr)).isTrue();
        assertThat(containsRegex("", nullStr)).isFalse();
    }

    @Test
    void testMatchesRegex() {
        assertThat(matchesRegex("go+d", goodStr)).isTrue();
        assertThat(matchesRegex("o+", goodStr)).isFalse();
        assertThat(matchesRegex("", goodStr)).isFalse();
        assertThat(matchesRegex("", emptyStr)).isTrue(); // Interesting — empty str matches empty regex
        assertThat(matchesRegex("", nullStr)).isFalse();
    }

    @Test
    void contains_map() {
        assertThat(containsKey("a", goodMap)).isTrue();
        assertThat(containsKey("x", goodMap)).isFalse();
        assertThat(containsKey("x", nullMap)).isFalse();
        assertThat(containsKey(null, goodMap)).isFalse();
        assertThat(containsValue(1, goodMap)).isTrue();
        assertThat(containsValue(9, goodMap)).isFalse();
        assertThat(containsValue(9, nullMap)).isFalse();
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
