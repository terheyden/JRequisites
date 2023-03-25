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

import static com.terheyden.require.Checks.contains;
import static com.terheyden.require.Checks.containsKey;
import static com.terheyden.require.Checks.containsNull;
import static com.terheyden.require.Checks.containsRegex;
import static com.terheyden.require.Checks.containsUniqueElements;
import static com.terheyden.require.Checks.containsValue;
import static com.terheyden.require.Checks.isBlank;
import static com.terheyden.require.Checks.isDurationGreaterOrEqualTo;
import static com.terheyden.require.Checks.isDurationGreaterThan;
import static com.terheyden.require.Checks.isDurationLessOrEqualTo;
import static com.terheyden.require.Checks.isDurationLessThan;
import static com.terheyden.require.Checks.isDurationLessThanDays;
import static com.terheyden.require.Checks.isDurationLessThanHours;
import static com.terheyden.require.Checks.isDurationLessThanMillis;
import static com.terheyden.require.Checks.isDurationLessThanMins;
import static com.terheyden.require.Checks.isDurationLessThanNanos;
import static com.terheyden.require.Checks.isDurationLessThanSecs;
import static com.terheyden.require.Checks.isEmail;
import static com.terheyden.require.Checks.isFuture;
import static com.terheyden.require.Checks.isIPv6Address;
import static com.terheyden.require.Checks.isIpAddress;
import static com.terheyden.require.Checks.isJson;
import static com.terheyden.require.Checks.isPast;
import static com.terheyden.require.Checks.isSize;
import static com.terheyden.require.Checks.isSizeGreaterOrEqualTo;
import static com.terheyden.require.Checks.isSizeGreaterThan;
import static com.terheyden.require.Checks.isSizeLessOrEqualTo;
import static com.terheyden.require.Checks.isSizeLessThan;
import static com.terheyden.require.Checks.isUUID;
import static com.terheyden.require.Checks.isUrl;
import static com.terheyden.require.Checks.isXml;
import static com.terheyden.require.Checks.matchesRegex;
import static com.terheyden.require.Checks.notBlank;
import static com.terheyden.require.Checks.notContainsNull;
import static com.terheyden.require.Checks.notExists;
import static com.terheyden.require.Checks.pathExists;
import static java.util.Collections.emptyList;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * RequireTest unit tests.
 */
@SuppressWarnings("ConstantValue")
class ChecksTest {

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
        assertThat(Checks.isTrue(true)).isTrue();
        assertThat(Checks.isTrue(false)).isFalse();
    }

    @Test
    void isFalse() {
        assertThat(Checks.isFalse(true)).isFalse();
        assertThat(Checks.isFalse(false)).isTrue();
    }

    @Test
    void isNull() {
        assertThat(Checks.isNull(nullStr)).isTrue();
        assertThat(Checks.isNull(goodStr)).isFalse();
    }

    @Test
    void notNull() {
        assertThat(Checks.notNull(goodStr)).isTrue();
        assertThat(Checks.notNull(nullStr)).isFalse();
    }

    @Test
    void testIsDigitsOnly() {
        assertThat(Checks.numbersOnly("123")).isTrue();
        assertThat(Checks.numbersOnly("123.")).isFalse();
        assertThat(Checks.numbersOnly("")).isTrue();
        assertThat(Checks.numbersOnly(null)).isFalse();
    }

    @Test
    void testIsAlphaOnly() {
        assertThat(Checks.alphasOnly("abc")).isTrue();
        assertThat(Checks.alphasOnly("abc123")).isFalse();
        assertThat(Checks.alphasOnly("")).isTrue();
        assertThat(Checks.alphasOnly(null)).isFalse();
    }

    @Test
    void testIsAlphaNumericOnly() {
        assertThat(Checks.alphaNumericOnly("abc123")).isTrue();
        assertThat(Checks.alphaNumericOnly("123")).isTrue();
        assertThat(Checks.alphaNumericOnly("abc")).isTrue();
        assertThat(Checks.alphaNumericOnly("abc123.")).isFalse();
        assertThat(Checks.alphaNumericOnly("")).isTrue();
        assertThat(Checks.alphaNumericOnly(null)).isFalse();
    }

    @Test
    void isEmpty_string() {
        assertThat(Checks.isEmpty(goodStr)).isFalse();
        assertThat(Checks.isEmpty(blankStr)).isFalse();
        assertThat(Checks.isEmpty(emptyStr)).isTrue();
        assertThat(Checks.isEmpty(nullStr)).isTrue();
    }

    @Test
    void isEmpty_collection() {
        assertThat(Checks.isEmpty(goodList)).isFalse();
        assertThat(Checks.isEmpty(emptyList)).isTrue();
        assertThat(Checks.isEmpty(nullList)).isTrue();
    }

    @Test
    void isEmpty_iterable() {
        assertThat(Checks.isEmpty(goodIter)).isFalse();
        assertThat(Checks.isEmpty(emptyIter)).isTrue();
        assertThat(Checks.isEmpty(nullIter)).isTrue();
    }

    @Test
    void isEmpty_map() {
        assertThat(Checks.isEmpty(goodMap)).isFalse();
        assertThat(Checks.isEmpty(emptyMap)).isTrue();
        assertThat(Checks.isEmpty(nullMap)).isTrue();
    }

    @Test
    void isEmpty_array() {
        assertThat(Checks.isEmpty(goodArray)).isFalse();
        assertThat(Checks.isEmpty(emptyArray)).isTrue();
        assertThat(Checks.isEmpty(nullArray)).isTrue();
    }

    @Test
    void notEmpty_string() {
        assertThat(Checks.notEmpty(goodStr)).isTrue();
        assertThat(Checks.notEmpty(blankStr)).isTrue();
        assertThat(Checks.notEmpty(emptyStr)).isFalse();
        assertThat(Checks.notEmpty(nullStr)).isFalse();
    }

    @Test
    void notEmpty_collection() {
        assertThat(Checks.notEmpty(goodList)).isTrue();
        assertThat(Checks.notEmpty(emptyList)).isFalse();
        assertThat(Checks.notEmpty(nullList)).isFalse();
    }

    @Test
    void notEmpty_iterable() {
        assertThat(Checks.notEmpty(goodIter)).isTrue();
        assertThat(Checks.notEmpty(emptyIter)).isFalse();
        assertThat(Checks.notEmpty(nullIter)).isFalse();
    }

    @Test
    void notEmpty_map() {
        assertThat(Checks.notEmpty(goodMap)).isTrue();
        assertThat(Checks.notEmpty(emptyMap)).isFalse();
        assertThat(Checks.notEmpty(nullMap)).isFalse();
    }

    @Test
    void notEmpty_array() {
        assertThat(Checks.notEmpty(goodArray)).isTrue();
        assertThat(Checks.notEmpty(emptyArray)).isFalse();
        assertThat(Checks.notEmpty(nullArray)).isFalse();
    }

    @Test
    void nullOrEmpty_string() {
        assertThat(Checks.isEmpty(goodStr)).isFalse();
        assertThat(Checks.isEmpty(blankStr)).isFalse();
        assertThat(Checks.isEmpty(emptyStr)).isTrue();
        assertThat(Checks.isEmpty(nullStr)).isTrue();
    }

    @Test
    void nullOrEmpty_collection() {
        assertThat(Checks.isEmpty(goodList)).isFalse();
        assertThat(Checks.isEmpty(emptyList)).isTrue();
        assertThat(Checks.isEmpty(nullList)).isTrue();
    }

    @Test
    void nullOrEmpty_iterable() {
        assertThat(Checks.isEmpty(goodIter)).isFalse();
        assertThat(Checks.isEmpty(emptyIter)).isTrue();
        assertThat(Checks.isEmpty(nullIter)).isTrue();
    }

    @Test
    void nullOrEmpty_map() {
        assertThat(Checks.isEmpty(goodMap)).isFalse();
        assertThat(Checks.isEmpty(emptyMap)).isTrue();
        assertThat(Checks.isEmpty(nullMap)).isTrue();
    }

    @Test
    void nullOrEmpty_array() {
        assertThat(Checks.isEmpty(goodArray)).isFalse();
        assertThat(Checks.isEmpty(emptyArray)).isTrue();
        assertThat(Checks.isEmpty(nullArray)).isTrue();
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
        assertThat(Checks.isDurationGreaterThanNanos(1, oneNs)).isFalse();
        assertThat(Checks.isDurationGreaterThanNanos(1, fiveNs)).isTrue();
        assertThat(Checks.isDurationGreaterThanNanos(1, oneMs)).isTrue();
        assertThat(Checks.isDurationGreaterThanMillis(1, oneMs)).isFalse();
        assertThat(Checks.isDurationGreaterThanMillis(1, fiveMs)).isTrue();
        assertThat(Checks.isDurationGreaterThanMillis(1, oneSec)).isTrue();
        assertThat(Checks.isDurationGreaterThanSecs(1, oneSec)).isFalse();
        assertThat(Checks.isDurationGreaterThanSecs(1, fiveSecs)).isTrue();
        assertThat(Checks.isDurationGreaterThanSecs(1, oneMin)).isTrue();
        assertThat(Checks.isDurationGreaterThanMins(1, oneMin)).isFalse();
        assertThat(Checks.isDurationGreaterThanMins(1, fiveMins)).isTrue();
        assertThat(Checks.isDurationGreaterThanMins(1, oneHour)).isTrue();
        assertThat(Checks.isDurationGreaterThanHours(1, oneHour)).isFalse();
        assertThat(Checks.isDurationGreaterThanHours(1, fiveHours)).isTrue();
        assertThat(Checks.isDurationGreaterThanHours(1, oneDay)).isTrue();
        assertThat(Checks.isDurationGreaterThanDays(1, oneDay)).isFalse();
        assertThat(Checks.isDurationGreaterThanDays(1, fiveDays)).isTrue();
    }

    @Test
    void greaterOrEqualTo_duration() {
        assertThat(Checks.isDurationGreaterOrEqualToNanos(1, oneNs)).isTrue();
        assertThat(Checks.isDurationGreaterOrEqualToNanos(10, fiveNs)).isFalse();
        assertThat(Checks.isDurationGreaterOrEqualToNanos(1, oneMs)).isTrue();
        assertThat(Checks.isDurationGreaterOrEqualToMillis(1, oneMs)).isTrue();
        assertThat(Checks.isDurationGreaterOrEqualToMillis(10, fiveMs)).isFalse();
        assertThat(Checks.isDurationGreaterOrEqualToMillis(1, oneSec)).isTrue();
        assertThat(Checks.isDurationGreaterOrEqualToSecs(1, oneSec)).isTrue();
        assertThat(Checks.isDurationGreaterOrEqualToSecs(10, fiveSecs)).isFalse();
        assertThat(Checks.isDurationGreaterOrEqualToSecs(1, oneMin)).isTrue();
        assertThat(Checks.isDurationGreaterOrEqualToMins(1, oneMin)).isTrue();
        assertThat(Checks.isDurationGreaterOrEqualToMins(10, fiveMins)).isFalse();
        assertThat(Checks.isDurationGreaterOrEqualToMins(1, oneHour)).isTrue();
        assertThat(Checks.isDurationGreaterOrEqualToHours(1, oneHour)).isTrue();
        assertThat(Checks.isDurationGreaterOrEqualToHours(10, fiveHours)).isFalse();
        assertThat(Checks.isDurationGreaterOrEqualToHours(1, oneDay)).isTrue();
        assertThat(Checks.isDurationGreaterOrEqualToDays(1, oneDay)).isTrue();
        assertThat(Checks.isDurationGreaterOrEqualToDays(10, fiveDays)).isFalse();
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
        assertThat(Checks.isDurationLessOrEqualToNanos(1, oneNs)).isTrue();
        assertThat(Checks.isDurationLessOrEqualToNanos(10, fiveNs)).isTrue();
        assertThat(Checks.isDurationLessOrEqualToMillis(1, oneMs)).isTrue();
        assertThat(Checks.isDurationLessOrEqualToMillis(10, fiveMs)).isTrue();
        assertThat(Checks.isDurationLessOrEqualToMillis(1, oneNs)).isTrue();
        assertThat(Checks.isDurationLessOrEqualToSecs(1, oneSec)).isTrue();
        assertThat(Checks.isDurationLessOrEqualToSecs(10, fiveSecs)).isTrue();
        assertThat(Checks.isDurationLessOrEqualToSecs(1, oneMs)).isTrue();
        assertThat(Checks.isDurationLessOrEqualToMins(1, oneMin)).isTrue();
        assertThat(Checks.isDurationLessOrEqualToMins(10, fiveMins)).isTrue();
        assertThat(Checks.isDurationLessOrEqualToMins(1, oneSec)).isTrue();
        assertThat(Checks.isDurationLessOrEqualToHours(1, oneHour)).isTrue();
        assertThat(Checks.isDurationLessOrEqualToHours(10, fiveHours)).isTrue();
        assertThat(Checks.isDurationLessOrEqualToHours(1, oneMin)).isTrue();
        assertThat(Checks.isDurationLessOrEqualToDays(1, oneDay)).isTrue();
        assertThat(Checks.isDurationLessOrEqualToDays(10, fiveDays)).isTrue();
        assertThat(Checks.isDurationLessOrEqualToDays(1, oneHour)).isTrue();
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

        assertThat(Checks.isLength(goodStr.length(), goodStr)).isTrue();
        assertThat(Checks.isLength(goodStr.length() + 1, goodStr)).isFalse();
        assertThat(Checks.isLength(goodStr.length() - 1, goodStr)).isFalse();
        assertThat(Checks.isLength(0, emptyStr)).isTrue();
        assertThat(Checks.isLength(1, emptyStr)).isFalse();
        assertThat(Checks.isLength(1, nullStr)).isFalse();
        assertThat(Checks.isLength(0, nullStr)).isFalse(); // null has no length, it is null

        assertThat(Checks.isLengthGreaterThan(goodStr.length() - 1, goodStr)).isTrue();
        assertThat(Checks.isLengthGreaterThan(goodStr.length(), goodStr)).isFalse();
        assertThat(Checks.isLengthGreaterThan(goodStr.length() + 1, goodStr)).isFalse();
        assertThat(Checks.isLengthGreaterThan(1, blankStr)).isTrue();
        assertThat(Checks.isLengthGreaterThan(0, emptyStr)).isFalse();
        assertThat(Checks.isLengthGreaterThan(1, nullStr)).isFalse();
        assertThat(Checks.isLengthGreaterThan(0, nullStr)).isFalse(); // null has no length, it is null

        assertThat(Checks.isLengthGreaterOrEqualTo(goodStr.length(), goodStr)).isTrue();
        assertThat(Checks.isLengthGreaterOrEqualTo(goodStr.length() + 1, goodStr)).isFalse();
        assertThat(Checks.isLengthGreaterOrEqualTo(goodStr.length() - 1, goodStr)).isTrue();
        assertThat(Checks.isLengthGreaterOrEqualTo(1, blankStr)).isTrue();
        assertThat(Checks.isLengthGreaterOrEqualTo(1, emptyStr)).isFalse();
        assertThat(Checks.isLengthGreaterOrEqualTo(1, nullStr)).isFalse();
        assertThat(Checks.isLengthGreaterOrEqualTo(0, nullStr)).isFalse(); // null has no length, it is null

        assertThat(Checks.isLengthLessThan(goodStr.length() + 1, goodStr)).isTrue();
        assertThat(Checks.isLengthLessThan(goodStr.length(), goodStr)).isFalse();
        assertThat(Checks.isLengthLessThan(goodStr.length() - 1, goodStr)).isFalse();
        assertThat(Checks.isLengthLessThan(1, blankStr)).isFalse();
        assertThat(Checks.isLengthLessThan(1, emptyStr)).isTrue();
        assertThat(Checks.isLengthLessThan(0, emptyStr)).isFalse();
        assertThat(Checks.isLengthLessThan(1, nullStr)).isFalse();
        assertThat(Checks.isLengthLessThan(0, nullStr)).isFalse(); // null has no length, it is null

        assertThat(Checks.isLengthLessOrEqualTo(goodStr.length(), goodStr)).isTrue();
        assertThat(Checks.isLengthLessOrEqualTo(goodStr.length() + 1, goodStr)).isTrue();
        assertThat(Checks.isLengthLessOrEqualTo(goodStr.length() - 1, goodStr)).isFalse();
        assertThat(Checks.isLengthLessOrEqualTo(1, blankStr)).isFalse();
        assertThat(Checks.isLengthLessOrEqualTo(1, emptyStr)).isTrue();
        assertThat(Checks.isLengthLessOrEqualTo(1, nullStr)).isFalse();
        assertThat(Checks.isLengthLessOrEqualTo(0, nullStr)).isFalse(); // null has no length, it is null
    }

    @Test
    void hasLength_array() {

        assertThat(Checks.isLength(goodArray.length, goodArray)).isTrue(); // test max inclusive
        assertThat(Checks.isLength(goodArray.length + 1, goodArray)).isFalse(); // plus or minus 1
        assertThat(Checks.isLength(goodArray.length - 1, goodArray)).isFalse(); // is wrong
        assertThat(Checks.isLength(0, emptyArray)).isTrue(); // test empty
        assertThat(Checks.isLength(1, emptyArray)).isFalse();
        assertThat(Checks.isLength(0, nullArray)).isFalse(); // null has no length, it is null
        assertThat(Checks.isLength(1, nullArray)).isFalse(); // test null
        assertThat(Checks.isLengthGreaterThan(goodArray.length - 1, goodArray)).isTrue();
        assertThat(Checks.isLengthGreaterThan(goodArray.length, goodArray)).isFalse();
        assertThat(Checks.isLengthGreaterThan(0, emptyArray)).isFalse();
        assertThat(Checks.isLengthGreaterThan(0, nullArray)).isFalse(); // null has no length, it is null

        assertThat(Checks.isLengthGreaterOrEqualTo(goodArray.length, goodArray)).isTrue();
        assertThat(Checks.isLengthGreaterOrEqualTo(goodArray.length + 1, goodArray)).isFalse();
        assertThat(Checks.isLengthGreaterOrEqualTo(goodArray.length - 1, goodArray)).isTrue();
        assertThat(Checks.isLengthGreaterOrEqualTo(0, emptyArray)).isTrue();
        assertThat(Checks.isLengthGreaterOrEqualTo(1, emptyArray)).isFalse();
        assertThat(Checks.isLengthGreaterOrEqualTo(0, nullArray)).isFalse(); // null has no length, it is null

        assertThat(Checks.isLengthLessThan(goodArray.length + 1, goodArray)).isTrue();
        assertThat(Checks.isLengthLessThan(goodArray.length, goodArray)).isFalse();
        assertThat(Checks.isLengthLessThan(1, emptyArray)).isTrue();
        assertThat(Checks.isLengthLessThan(0, emptyArray)).isFalse();
        assertThat(Checks.isLengthLessThan(1, nullArray)).isFalse();
        assertThat(Checks.isLengthLessThan(0, nullArray)).isFalse(); // null has no length, it is null

        assertThat(Checks.isLengthLessOrEqualTo(goodArray.length, goodArray)).isTrue();
        assertThat(Checks.isLengthLessOrEqualTo(goodArray.length + 1, goodArray)).isTrue();
        assertThat(Checks.isLengthLessOrEqualTo(goodArray.length - 1, goodArray)).isFalse();
        assertThat(Checks.isLengthLessOrEqualTo(1, emptyArray)).isTrue();
        assertThat(Checks.isLengthLessOrEqualTo(0, emptyArray)).isTrue();
        assertThat(Checks.isLengthLessOrEqualTo(1, nullArray)).isFalse();
        assertThat(Checks.isLengthLessOrEqualTo(0, nullArray)).isFalse(); // null has no length, it is null
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
        assertThat(Checks.pathExists(goodPathFile)).isTrue();
        assertThat(Checks.pathExists(goodPathDir)).isTrue();
        assertThat(Checks.pathExists(badPathFile)).isFalse();
        assertThat(Checks.pathExists(nullPath)).isFalse();
    }

    @Test
    void pathExists_file() {
        assertThat(Checks.pathExists(goodFileFile)).isTrue();
        assertThat(Checks.pathExists(goodFileDir)).isTrue();
        assertThat(Checks.pathExists(badFileFile)).isFalse();
        assertThat(Checks.pathExists(nullFile)).isFalse();
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
        assertThat(Checks.isRegularFile(goodPathFile)).isTrue();
        assertThat(Checks.isRegularFile(goodPathDir)).isFalse();
        assertThat(Checks.isRegularFile(badPathFile)).isFalse();
        assertThat(Checks.isRegularFile(nullPath)).isFalse();
    }

    @Test
    void isRegularFile_file() {
        assertThat(Checks.isRegularFile(goodFileFile)).isTrue();
        assertThat(Checks.isRegularFile(goodFileDir)).isFalse();
        assertThat(Checks.isRegularFile(badFileFile)).isFalse();
        assertThat(Checks.isRegularFile(nullFile)).isFalse();
    }

    @Test
    void isRegularFile_string() {
        assertThat(Checks.isRegularFile(GOOD_FILE_STR)).isTrue();
        assertThat(Checks.isRegularFile(GOOD_DIR_STR)).isFalse();
        assertThat(Checks.isRegularFile(BAD)).isFalse();
        assertThat(Checks.isRegularFile(nullStr)).isFalse();
    }

    @Test
    void isDirectory_path() {
        assertThat(Checks.isDirectory(goodPathFile)).isFalse();
        assertThat(Checks.isDirectory(goodPathDir)).isTrue();
        assertThat(Checks.isDirectory(badPathFile)).isFalse();
        assertThat(Checks.isDirectory(nullPath)).isFalse();
    }

    @Test
    void isDirectory_file() {
        assertThat(Checks.isDirectory(goodFileDir)).isTrue();
        assertThat(Checks.isDirectory(badFileDir)).isFalse();
        assertThat(Checks.isDirectory(nullFile)).isFalse();
        assertThat(Checks.isDirectory(goodFileFile)).isFalse();
    }

    @Test
    void isDirectory_string() {
        assertThat(Checks.isDirectory(GOOD_FILE_STR)).isFalse();
        assertThat(Checks.isDirectory(GOOD_DIR_STR)).isTrue();
        assertThat(Checks.isDirectory(BAD)).isFalse();
        assertThat(Checks.isDirectory(nullStr)).isFalse();
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
        assertThat(Checks.isInstanceOf(CharSequence.class, goodStr)).isTrue();
        assertThat(Checks.isInstanceOf(Map.class, goodStr)).isFalse();
    }

    @Test
    void notInstanceOf() {
        assertThat(Checks.notInstanceOf(CharSequence.class, goodStr)).isFalse();
        assertThat(Checks.notInstanceOf(Map.class, goodStr)).isTrue();
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
        assertThat(Checks.containsElement(elem -> elem.equals("a"), goodList)).isTrue();
        assertThat(Checks.containsElement(elem -> elem.equals("BAD"), goodList)).isFalse();
        assertThat(Checks.containsElement(elem -> elem.equals("a"), nullList)).isFalse();
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
