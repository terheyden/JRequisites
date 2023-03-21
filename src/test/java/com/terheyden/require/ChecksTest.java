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
import static com.terheyden.require.Checks.greaterOrEqualTo;
import static com.terheyden.require.Checks.greaterThan;
import static com.terheyden.require.Checks.isBetween;
import static com.terheyden.require.Checks.isBlank;
import static com.terheyden.require.Checks.isEmail;
import static com.terheyden.require.Checks.isFuture;
import static com.terheyden.require.Checks.isIPv6Address;
import static com.terheyden.require.Checks.isIpAddress;
import static com.terheyden.require.Checks.isJson;
import static com.terheyden.require.Checks.isNowOrFuture;
import static com.terheyden.require.Checks.isNowOrPast;
import static com.terheyden.require.Checks.isPast;
import static com.terheyden.require.Checks.isSize;
import static com.terheyden.require.Checks.isSizeBetween;
import static com.terheyden.require.Checks.isSizeGreaterOrEqualTo;
import static com.terheyden.require.Checks.isSizeGreaterThan;
import static com.terheyden.require.Checks.isSizeLessOrEqualTo;
import static com.terheyden.require.Checks.isSizeLessThan;
import static com.terheyden.require.Checks.isUUID;
import static com.terheyden.require.Checks.isUrl;
import static com.terheyden.require.Checks.isXml;
import static com.terheyden.require.Checks.lessOrEqualTo;
import static com.terheyden.require.Checks.lessThan;
import static com.terheyden.require.Checks.matchesRegex;
import static com.terheyden.require.Checks.notBlank;
import static com.terheyden.require.Checks.notContainsNull;
import static com.terheyden.require.Checks.pathExists;
import static com.terheyden.require.Checks.pathNotExists;
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
    private final int[] goodPrimitiveArray = new int[] { 1, 2, 3 };
    private final int[] emptyPrimitiveArray = new int[0];
    private final int[] nullPrimitiveArray = null;

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
        assertThat(Checks.digitsOnly("123")).isTrue();
        assertThat(Checks.digitsOnly("123.")).isFalse();
        assertThat(Checks.digitsOnly("")).isTrue();
        assertThat(Checks.digitsOnly(null)).isFalse();
    }

    @Test
    void testIsAlphaOnly() {
        assertThat(Checks.alphaOnly("abc")).isTrue();
        assertThat(Checks.alphaOnly("abc123")).isFalse();
        assertThat(Checks.alphaOnly("")).isTrue();
        assertThat(Checks.alphaOnly(null)).isFalse();
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
        assertThat(Checks.isEmpty(goodPrimitiveArray)).isFalse();
        assertThat(Checks.isEmpty(emptyPrimitiveArray)).isTrue();
        assertThat(Checks.isEmpty(nullPrimitiveArray)).isTrue();
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
        assertThat(Checks.notEmpty(goodPrimitiveArray)).isTrue();
        assertThat(Checks.notEmpty(emptyPrimitiveArray)).isFalse();
        assertThat(Checks.notEmpty(nullPrimitiveArray)).isFalse();
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
        assertThat(Checks.isEmpty(goodPrimitiveArray)).isFalse();
        assertThat(Checks.isEmpty(emptyPrimitiveArray)).isTrue();
        assertThat(Checks.isEmpty(nullPrimitiveArray)).isTrue();
    }

    @Test
    void testIsGreaterThan() {
        assertThat(greaterThan(0, 1)).isTrue();
        assertThat(greaterThan(1, 1)).isFalse();
        assertThat(greaterThan(2, 1)).isFalse();
        assertThat(greaterThan(0L, 1L)).isTrue();
        assertThat(greaterThan(1L, 1L)).isFalse();
        assertThat(greaterThan(2L, 1L)).isFalse();
        assertThat(greaterThan(0.0, 1.0)).isTrue();
        assertThat(greaterThan(1.0, 1.0)).isFalse();
        assertThat(greaterThan(2.0, 1.0)).isFalse();
        assertThat(greaterThan(0.0f, 1.0f)).isTrue();
        assertThat(greaterThan(1.0f, 1.0f)).isFalse();
        assertThat(greaterThan(2.0f, 1.0f)).isFalse();
        assertThat(greaterThan(oneMin, fiveMins)).isTrue();
        assertThat(greaterThan(fiveMins, fiveMins)).isFalse();
        assertThat(greaterThan(oneMin, nullDuration)).isFalse();
        assertThat(greaterThan(nullDuration, nullDuration)).isFalse();
    }

    @Test
    void testIsGreaterOrEqualTo() {
        assertThat(greaterOrEqualTo(0, 1)).isTrue();
        assertThat(greaterOrEqualTo(1, 1)).isTrue();
        assertThat(greaterOrEqualTo(2, 1)).isFalse();
        assertThat(greaterOrEqualTo(0L, 1L)).isTrue();
        assertThat(greaterOrEqualTo(1L, 1L)).isTrue();
        assertThat(greaterOrEqualTo(2L, 1L)).isFalse();
        assertThat(greaterOrEqualTo(0.0, 1.0)).isTrue();
        assertThat(greaterOrEqualTo(1.0, 1.0)).isTrue();
        assertThat(greaterOrEqualTo(2.0, 1.0)).isFalse();
        assertThat(greaterOrEqualTo(0.0f, 1.0f)).isTrue();
        assertThat(greaterOrEqualTo(1.0f, 1.0f)).isTrue();
        assertThat(greaterOrEqualTo(2.0f, 1.0f)).isFalse();
        assertThat(greaterOrEqualTo(oneMin, fiveMins)).isTrue();
        assertThat(greaterOrEqualTo(fiveMins, fiveMins)).isTrue();
        assertThat(greaterOrEqualTo(oneMin, nullDuration)).isFalse();
        assertThat(greaterOrEqualTo(nullDuration, nullDuration)).isFalse();
    }

    @Test
    void testIsLessThan() {
        assertThat(lessThan(0, 1)).isFalse();
        assertThat(lessThan(1, 1)).isFalse();
        assertThat(lessThan(2, 1)).isTrue();
        assertThat(lessThan(0L, 1L)).isFalse();
        assertThat(lessThan(1L, 1L)).isFalse();
        assertThat(lessThan(2L, 1L)).isTrue();
        assertThat(lessThan(0.0, 1.0)).isFalse();
        assertThat(lessThan(1.0, 1.0)).isFalse();
        assertThat(lessThan(2.0, 1.0)).isTrue();
        assertThat(lessThan(0.0f, 1.0f)).isFalse();
        assertThat(lessThan(1.0f, 1.0f)).isFalse();
        assertThat(lessThan(2.0f, 1.0f)).isTrue();
        assertThat(lessThan(oneMin, fiveMins)).isFalse();
        assertThat(lessThan(fiveMins, fiveMins)).isFalse();
        assertThat(lessThan(oneMin, nullDuration)).isFalse();
        assertThat(lessThan(nullDuration, nullDuration)).isFalse();
    }

    @Test
    void testIsLessOrEqualTo() {
        assertThat(lessOrEqualTo(0, 1)).isFalse();
        assertThat(lessOrEqualTo(1, 1)).isTrue();
        assertThat(lessOrEqualTo(2, 1)).isTrue();
        assertThat(lessOrEqualTo(0L, 1L)).isFalse();
        assertThat(lessOrEqualTo(1L, 1L)).isTrue();
        assertThat(lessOrEqualTo(2L, 1L)).isTrue();
        assertThat(lessOrEqualTo(0.0, 1.0)).isFalse();
        assertThat(lessOrEqualTo(1.0, 1.0)).isTrue();
        assertThat(lessOrEqualTo(2.0, 1.0)).isTrue();
        assertThat(lessOrEqualTo(0.0f, 1.0f)).isFalse();
        assertThat(lessOrEqualTo(1.0f, 1.0f)).isTrue();
        assertThat(lessOrEqualTo(2.0f, 1.0f)).isTrue();
        assertThat(lessOrEqualTo(oneMin, fiveMins)).isFalse();
        assertThat(lessOrEqualTo(fiveMins, fiveMins)).isTrue();
        assertThat(lessOrEqualTo(oneMin, nullDuration)).isFalse();
        assertThat(lessOrEqualTo(nullDuration, nullDuration)).isFalse();
    }

    @Test
    void testIsBetween() {
        assertThat(isBetween(1, 3, 1)).isTrue();
        assertThat(isBetween(1, 3, 2)).isTrue();
        assertThat(isBetween(1, 3, 3)).isTrue();
        assertThat(isBetween(1, 3, 0)).isFalse();
        assertThat(isBetween(1, 3, 4)).isFalse();
        assertThat(isBetween(1L, 3L, 1L)).isTrue();
        assertThat(isBetween(1L, 3L, 2L)).isTrue();
        assertThat(isBetween(1L, 3L, 3L)).isTrue();
        assertThat(isBetween(1L, 3L, 0L)).isFalse();
        assertThat(isBetween(1L, 3L, 4L)).isFalse();
        assertThat(isBetween(1.0, 3.0, 1.0)).isTrue();
        assertThat(isBetween(1.0, 3.0, 2.0)).isTrue();
        assertThat(isBetween(1.0, 3.0, 3.0)).isTrue();
        assertThat(isBetween(1.0, 3.0, 0.0)).isFalse();
        assertThat(isBetween(1.0, 3.0, 4.0)).isFalse();
        assertThat(isBetween(1.0f, 3.0f, 1.0f)).isTrue();
        assertThat(isBetween(1.0f, 3.0f, 2.0f)).isTrue();
        assertThat(isBetween(1.0f, 3.0f, 3.0f)).isTrue();
        assertThat(isBetween(1.0f, 3.0f, 0.0f)).isFalse();
        assertThat(isBetween(1.0f, 3.0f, 4.0f)).isFalse();
        assertThat(isBetween(oneMin, fiveMins, oneMin)).isTrue();
        assertThat(isBetween(oneMin, fiveMins, fiveMins)).isTrue();
        assertThat(isBetween(oneMin, fiveMins, nullDuration)).isFalse();
        assertThat(isBetween(nullDuration, fiveMins, nullDuration)).isFalse();
        assertThat(isBetween(nullDuration, nullDuration, nullDuration)).isFalse();
    }

    @Test
    void greaterThan_duration() {
        assertThat(Checks.greaterThanNanos(1, oneNs)).isFalse();
        assertThat(Checks.greaterThanNanos(1, fiveNs)).isTrue();
        assertThat(Checks.greaterThanNanos(1, oneMs)).isTrue();
        assertThat(Checks.greaterThanMillis(1, oneMs)).isFalse();
        assertThat(Checks.greaterThanMillis(1, fiveMs)).isTrue();
        assertThat(Checks.greaterThanMillis(1, oneSec)).isTrue();
        assertThat(Checks.greaterThanSecs(1, oneSec)).isFalse();
        assertThat(Checks.greaterThanSecs(1, fiveSecs)).isTrue();
        assertThat(Checks.greaterThanSecs(1, oneMin)).isTrue();
        assertThat(Checks.greaterThanMins(1, oneMin)).isFalse();
        assertThat(Checks.greaterThanMins(1, fiveMins)).isTrue();
        assertThat(Checks.greaterThanMins(1, oneHour)).isTrue();
        assertThat(Checks.greaterThanHours(1, oneHour)).isFalse();
        assertThat(Checks.greaterThanHours(1, fiveHours)).isTrue();
        assertThat(Checks.greaterThanHours(1, oneDay)).isTrue();
        assertThat(Checks.greaterThanDays(1, oneDay)).isFalse();
        assertThat(Checks.greaterThanDays(1, fiveDays)).isTrue();
    }

    @Test
    void greaterOrEqualTo_duration() {
        assertThat(Checks.greaterOrEqualToNanos(1, oneNs)).isTrue();
        assertThat(Checks.greaterOrEqualToNanos(10, fiveNs)).isFalse();
        assertThat(Checks.greaterOrEqualToNanos(1, oneMs)).isTrue();
        assertThat(Checks.greaterOrEqualToMillis(1, oneMs)).isTrue();
        assertThat(Checks.greaterOrEqualToMillis(10, fiveMs)).isFalse();
        assertThat(Checks.greaterOrEqualToMillis(1, oneSec)).isTrue();
        assertThat(Checks.greaterOrEqualToSecs(1, oneSec)).isTrue();
        assertThat(Checks.greaterOrEqualToSecs(10, fiveSecs)).isFalse();
        assertThat(Checks.greaterOrEqualToSecs(1, oneMin)).isTrue();
        assertThat(Checks.greaterOrEqualToMins(1, oneMin)).isTrue();
        assertThat(Checks.greaterOrEqualToMins(10, fiveMins)).isFalse();
        assertThat(Checks.greaterOrEqualToMins(1, oneHour)).isTrue();
        assertThat(Checks.greaterOrEqualToHours(1, oneHour)).isTrue();
        assertThat(Checks.greaterOrEqualToHours(10, fiveHours)).isFalse();
        assertThat(Checks.greaterOrEqualToHours(1, oneDay)).isTrue();
        assertThat(Checks.greaterOrEqualToDays(1, oneDay)).isTrue();
        assertThat(Checks.greaterOrEqualToDays(10, fiveDays)).isFalse();
    }

    @Test
    void lessThan_duration() {
        assertThat(Checks.lessThanNanos(1, oneNs)).isFalse();
        assertThat(Checks.lessThanNanos(1, fiveNs)).isFalse();
        assertThat(Checks.lessThanNanos(1, oneMs)).isFalse();
        assertThat(Checks.lessThanMillis(1, oneMs)).isFalse();
        assertThat(Checks.lessThanMillis(1, fiveMs)).isFalse();
        assertThat(Checks.lessThanMillis(1, oneSec)).isFalse();
        assertThat(Checks.lessThanSecs(1, oneSec)).isFalse();
        assertThat(Checks.lessThanSecs(1, fiveSecs)).isFalse();
        assertThat(Checks.lessThanSecs(1, oneMin)).isFalse();
        assertThat(Checks.lessThanMins(1, oneMin)).isFalse();
        assertThat(Checks.lessThanMins(1, fiveMins)).isFalse();
        assertThat(Checks.lessThanMins(1, oneHour)).isFalse();
        assertThat(Checks.lessThanHours(1, oneHour)).isFalse();
        assertThat(Checks.lessThanHours(1, fiveHours)).isFalse();
        assertThat(Checks.lessThanHours(1, oneDay)).isFalse();
        assertThat(Checks.lessThanDays(1, oneDay)).isFalse();
        assertThat(Checks.lessThanDays(1, fiveDays)).isFalse();
    }

    @Test
    void lessOrEqualTo_duration() {
        assertThat(Checks.lessOrEqualToNanos(1, oneNs)).isTrue();
        assertThat(Checks.lessOrEqualToNanos(10, fiveNs)).isTrue();
        assertThat(Checks.lessOrEqualToMillis(1, oneMs)).isTrue();
        assertThat(Checks.lessOrEqualToMillis(10, fiveMs)).isTrue();
        assertThat(Checks.lessOrEqualToMillis(1, oneNs)).isTrue();
        assertThat(Checks.lessOrEqualToSecs(1, oneSec)).isTrue();
        assertThat(Checks.lessOrEqualToSecs(10, fiveSecs)).isTrue();
        assertThat(Checks.lessOrEqualToSecs(1, oneMs)).isTrue();
        assertThat(Checks.lessOrEqualToMins(1, oneMin)).isTrue();
        assertThat(Checks.lessOrEqualToMins(10, fiveMins)).isTrue();
        assertThat(Checks.lessOrEqualToMins(1, oneSec)).isTrue();
        assertThat(Checks.lessOrEqualToHours(1, oneHour)).isTrue();
        assertThat(Checks.lessOrEqualToHours(10, fiveHours)).isTrue();
        assertThat(Checks.lessOrEqualToHours(1, oneMin)).isTrue();
        assertThat(Checks.lessOrEqualToDays(1, oneDay)).isTrue();
        assertThat(Checks.lessOrEqualToDays(10, fiveDays)).isTrue();
        assertThat(Checks.lessOrEqualToDays(1, oneHour)).isTrue();
    }

    @Test
    void isBetween_duration() {
        assertThat(Checks.isBetweenNanos(1, 2, oneNs)).isTrue();
        assertThat(Checks.isBetweenNanos(2, 3, oneNs)).isFalse();
        assertThat(Checks.isBetweenMillis(1, 2, oneMs)).isTrue();
        assertThat(Checks.isBetweenMillis(2, 3, oneMs)).isFalse();
        assertThat(Checks.isBetweenSecs(1, 2, oneSec)).isTrue();
        assertThat(Checks.isBetweenSecs(2, 3, oneSec)).isFalse();
        assertThat(Checks.isBetweenMins(1, 2, oneMin)).isTrue();
        assertThat(Checks.isBetweenMins(2, 3, oneMin)).isFalse();
        assertThat(Checks.isBetweenHours(1, 2, oneHour)).isTrue();
        assertThat(Checks.isBetweenHours(2, 3, oneHour)).isFalse();
        assertThat(Checks.isBetweenDays(1, 2, oneDay)).isTrue();
        assertThat(Checks.isBetweenDays(2, 3, oneDay)).isFalse();
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

        assertThat(Checks.isLengthBetween(goodStr.length(), goodStr.length(), goodStr)).isTrue();
        assertThat(Checks.isLengthBetween(0, goodStr.length(), goodStr)).isTrue();
        assertThat(Checks.isLengthBetween(goodStr.length(), goodStr.length() + 1, goodStr)).isTrue();
        assertThat(Checks.isLengthBetween(goodStr.length() + 1, goodStr.length(), goodStr)).isFalse();
        assertThat(Checks.isLengthBetween(0, 0, emptyStr)).isTrue();
        assertThat(Checks.isLengthBetween(1, 1, emptyStr)).isFalse();
        assertThat(Checks.isLengthBetween(0, 0, nullStr)).isFalse(); // null has no length, it is null
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

        assertThat(Checks.isLength(goodPrimitiveArray.length, goodPrimitiveArray)).isTrue(); // test max inclusive
        assertThat(Checks.isLength(goodPrimitiveArray.length + 1, goodPrimitiveArray)).isFalse(); // plus or minus 1
        assertThat(Checks.isLength(goodPrimitiveArray.length - 1, goodPrimitiveArray)).isFalse(); // is wrong
        assertThat(Checks.isLength(0, emptyPrimitiveArray)).isTrue(); // test empty
        assertThat(Checks.isLength(1, emptyPrimitiveArray)).isFalse();
        assertThat(Checks.isLength(0, nullPrimitiveArray)).isFalse(); // null has no length, it is null
        assertThat(Checks.isLength(1, nullPrimitiveArray)).isFalse(); // test null

        assertThat(Checks.isLengthGreaterThan(goodArray.length - 1, goodArray)).isTrue();
        assertThat(Checks.isLengthGreaterThan(goodArray.length, goodArray)).isFalse();
        assertThat(Checks.isLengthGreaterThan(0, emptyArray)).isFalse();
        assertThat(Checks.isLengthGreaterThan(0, nullArray)).isFalse(); // null has no length, it is null

        assertThat(Checks.isLengthGreaterThan(goodPrimitiveArray.length - 1, goodPrimitiveArray)).isTrue();
        assertThat(Checks.isLengthGreaterThan(goodPrimitiveArray.length, goodPrimitiveArray)).isFalse();
        assertThat(Checks.isLengthGreaterThan(0, emptyPrimitiveArray)).isFalse();
        assertThat(Checks.isLengthGreaterThan(0, nullPrimitiveArray)).isFalse(); // null has no length, it is null

        assertThat(Checks.isLengthGreaterOrEqualTo(goodArray.length, goodArray)).isTrue();
        assertThat(Checks.isLengthGreaterOrEqualTo(goodArray.length + 1, goodArray)).isFalse();
        assertThat(Checks.isLengthGreaterOrEqualTo(goodArray.length - 1, goodArray)).isTrue();
        assertThat(Checks.isLengthGreaterOrEqualTo(0, emptyArray)).isTrue();
        assertThat(Checks.isLengthGreaterOrEqualTo(1, emptyArray)).isFalse();
        assertThat(Checks.isLengthGreaterOrEqualTo(0, nullArray)).isFalse(); // null has no length, it is null

        assertThat(Checks.isLengthGreaterOrEqualTo(goodPrimitiveArray.length, goodPrimitiveArray)).isTrue();
        assertThat(Checks.isLengthGreaterOrEqualTo(goodPrimitiveArray.length + 1, goodPrimitiveArray)).isFalse();
        assertThat(Checks.isLengthGreaterOrEqualTo(goodPrimitiveArray.length - 1, goodPrimitiveArray)).isTrue();
        assertThat(Checks.isLengthGreaterOrEqualTo(0, emptyPrimitiveArray)).isTrue();
        assertThat(Checks.isLengthGreaterOrEqualTo(1, emptyPrimitiveArray)).isFalse();
        assertThat(Checks.isLengthGreaterOrEqualTo(0, nullPrimitiveArray)).isFalse(); // null has no length, it is null

        assertThat(Checks.isLengthLessThan(goodArray.length + 1, goodArray)).isTrue();
        assertThat(Checks.isLengthLessThan(goodArray.length, goodArray)).isFalse();
        assertThat(Checks.isLengthLessThan(1, emptyArray)).isTrue();
        assertThat(Checks.isLengthLessThan(0, emptyArray)).isFalse();
        assertThat(Checks.isLengthLessThan(1, nullArray)).isFalse();
        assertThat(Checks.isLengthLessThan(0, nullArray)).isFalse(); // null has no length, it is null

        assertThat(Checks.isLengthLessThan(goodPrimitiveArray.length + 1, goodPrimitiveArray)).isTrue();
        assertThat(Checks.isLengthLessThan(goodPrimitiveArray.length, goodPrimitiveArray)).isFalse();
        assertThat(Checks.isLengthLessThan(1, emptyPrimitiveArray)).isTrue();
        assertThat(Checks.isLengthLessThan(0, emptyPrimitiveArray)).isFalse();
        assertThat(Checks.isLengthLessThan(1, nullPrimitiveArray)).isFalse();
        assertThat(Checks.isLengthLessThan(0, nullPrimitiveArray)).isFalse(); // null has no length, it is null

        assertThat(Checks.isLengthLessOrEqualTo(goodArray.length, goodArray)).isTrue();
        assertThat(Checks.isLengthLessOrEqualTo(goodArray.length + 1, goodArray)).isTrue();
        assertThat(Checks.isLengthLessOrEqualTo(goodArray.length - 1, goodArray)).isFalse();
        assertThat(Checks.isLengthLessOrEqualTo(1, emptyArray)).isTrue();
        assertThat(Checks.isLengthLessOrEqualTo(0, emptyArray)).isTrue();
        assertThat(Checks.isLengthLessOrEqualTo(1, nullArray)).isFalse();
        assertThat(Checks.isLengthLessOrEqualTo(0, nullArray)).isFalse(); // null has no length, it is null

        assertThat(Checks.isLengthLessOrEqualTo(goodPrimitiveArray.length, goodPrimitiveArray)).isTrue();
        assertThat(Checks.isLengthLessOrEqualTo(goodPrimitiveArray.length + 1, goodPrimitiveArray)).isTrue();
        assertThat(Checks.isLengthLessOrEqualTo(goodPrimitiveArray.length - 1, goodPrimitiveArray)).isFalse();
        assertThat(Checks.isLengthLessOrEqualTo(1, emptyPrimitiveArray)).isTrue();
        assertThat(Checks.isLengthLessOrEqualTo(0, emptyPrimitiveArray)).isTrue();
        assertThat(Checks.isLengthLessOrEqualTo(1, nullPrimitiveArray)).isFalse();
        assertThat(Checks.isLengthLessOrEqualTo(0, nullPrimitiveArray)).isFalse(); // null has no length, it is null

        assertThat(Checks.isLengthBetween(goodArray.length, goodArray.length, goodArray)).isTrue(); // length is the same
        assertThat(Checks.isLengthBetween(0, goodArray.length, goodArray)).isTrue(); // length of upper bound
        assertThat(Checks.isLengthBetween(goodArray.length, goodArray.length + 1, goodArray)).isTrue(); // length is lower bound
        assertThat(Checks.isLengthBetween(goodArray.length + 1, goodArray.length + 2, goodArray)).isFalse(); // length is out of bounds
        assertThat(Checks.isLengthBetween(0, goodArray.length - 1, goodArray)).isFalse(); // length is out of bounds
        assertThat(Checks.isLengthBetween(0, 0, emptyArray)).isTrue();
        assertThat(Checks.isLengthBetween(0, 1, emptyArray)).isTrue();
        assertThat(Checks.isLengthBetween(1, 1, emptyArray)).isFalse();
        assertThat(Checks.isLengthBetween(0, 0, nullArray)).isFalse(); // null has no length, it is null

        assertThat(Checks.isLengthBetween(goodPrimitiveArray.length, goodPrimitiveArray.length, goodPrimitiveArray)).isTrue(); // length is the same
        assertThat(Checks.isLengthBetween(0, goodPrimitiveArray.length, goodPrimitiveArray)).isTrue(); // length of upper bound
        assertThat(Checks.isLengthBetween(
            goodPrimitiveArray.length,
            goodPrimitiveArray.length + 1,
            goodPrimitiveArray)).isTrue(); // length is lower bound
        assertThat(Checks.isLengthBetween(goodPrimitiveArray.length + 1,
            goodPrimitiveArray.length + 2,
            goodPrimitiveArray)).isFalse(); // length is out of bounds
        assertThat(Checks.isLengthBetween(0, goodPrimitiveArray.length - 1, goodPrimitiveArray)).isFalse(); // length is out of bounds
        assertThat(Checks.isLengthBetween(0, 0, emptyPrimitiveArray)).isTrue();
        assertThat(Checks.isLengthBetween(0, 1, emptyPrimitiveArray)).isTrue();
        assertThat(Checks.isLengthBetween(1, 1, emptyPrimitiveArray)).isFalse();
        assertThat(Checks.isLengthBetween(0, 0, nullPrimitiveArray)).isFalse(); // null has no length, it is null
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

        assertThat(isSizeBetween(goodList.size(), goodList.size(), goodList)).isTrue();
        assertThat(isSizeBetween(0, goodList.size(), goodList)).isTrue();
        assertThat(isSizeBetween(goodList.size(), goodList.size() + 1, goodList)).isTrue();
        assertThat(isSizeBetween(goodList.size() + 1, goodList.size(), goodList)).isFalse();
        assertThat(isSizeBetween(1, goodList.size() - 1, goodList)).isFalse();
        assertThat(isSizeBetween(0, 0, emptyList)).isTrue();
        assertThat(isSizeBetween(1, 1, emptyList)).isFalse();
        assertThat(isSizeBetween(0, 0, nullList)).isFalse(); // null has no size, it is null
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

        assertThat(isSizeBetween(goodList.size(), goodList.size(), goodIter)).isTrue();
        assertThat(isSizeBetween(0, goodList.size(), goodIter)).isTrue();
        assertThat(isSizeBetween(goodList.size(), goodList.size() + 1, goodIter)).isTrue();
        assertThat(isSizeBetween(goodList.size() + 1, goodList.size(), goodIter)).isFalse();
        assertThat(isSizeBetween(1, goodList.size() - 1, goodIter)).isFalse();
        assertThat(isSizeBetween(0, 0, emptyIter)).isTrue();
        assertThat(isSizeBetween(1, 1, emptyIter)).isFalse();
        assertThat(isSizeBetween(0, 0, nullIter)).isFalse(); // null has no size, it is null
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

        assertThat(isSizeBetween(goodMap.size(), goodMap.size(), goodMap)).isTrue();
        assertThat(isSizeBetween(0, goodMap.size(), goodMap)).isTrue();
        assertThat(isSizeBetween(goodMap.size(), goodMap.size() + 1, goodMap)).isTrue();
        assertThat(isSizeBetween(goodMap.size() + 1, goodMap.size(), goodMap)).isFalse();
        assertThat(isSizeBetween(1, goodMap.size() - 1, goodMap)).isFalse();
        assertThat(isSizeBetween(0, 0, emptyMap)).isTrue();
        assertThat(isSizeBetween(1, 1, emptyMap)).isFalse();
        assertThat(isSizeBetween(0, 0, nullMap)).isFalse(); // null has no size, it is null
    }

    @Test
    void pathExists_path() {
        assertThat(pathExists(goodPathFile)).isTrue();
        assertThat(pathExists(goodPathDir)).isTrue();
        assertThat(pathExists(badPathFile)).isFalse();
        assertThat(pathExists(nullPath)).isFalse();
    }

    @Test
    void pathExists_file() {
        assertThat(pathExists(goodFileFile)).isTrue();
        assertThat(pathExists(goodFileDir)).isTrue();
        assertThat(pathExists(badFileFile)).isFalse();
        assertThat(pathExists(nullFile)).isFalse();
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
        assertThat(pathNotExists(goodPathFile)).isFalse();
        assertThat(pathNotExists(goodPathDir)).isFalse();
        assertThat(pathNotExists(badPathFile)).isTrue();
        assertThat(pathNotExists(nullPath)).isFalse(); // null cannot be tested so we return false
    }

    @Test
    void pathNotExists_file() {
        assertThat(pathNotExists(goodFileFile)).isFalse();
        assertThat(pathNotExists(goodFileDir)).isFalse();
        assertThat(pathNotExists(badFileFile)).isTrue();
        assertThat(pathNotExists(nullFile)).isFalse(); // null cannot be tested so we return false
    }

    @Test
    void pathNotExists_string() {
        assertThat(pathNotExists(GOOD_FILE_STR)).isFalse();
        assertThat(pathNotExists(GOOD_DIR_STR)).isFalse();
        assertThat(pathNotExists(BAD)).isTrue();
        assertThat(pathNotExists(nullStr)).isFalse(); // null cannot be tested so we return false
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
        assertThat(isNowOrFuture(zdtFuture)).isTrue();
        assertThat(isNowOrFuture(zdtPast)).isFalse();
        assertThat(isNowOrFuture(zdtNull)).isFalse();
    }

    @Test
    void isFuture_offsetDateTime() {
        assertThat(isFuture(odtFuture)).isTrue();
        assertThat(isFuture(odtPast)).isFalse();
        assertThat(isFuture(odtNull)).isFalse();
        assertThat(isNowOrFuture(odtFuture)).isTrue();
        assertThat(isNowOrFuture(odtPast)).isFalse();
        assertThat(isNowOrFuture(odtNull)).isFalse();
    }

    @Test
    void isFuture_localDateTime() {
        assertThat(isFuture(ldtFuture)).isTrue();
        assertThat(isFuture(ldtPast)).isFalse();
        assertThat(isFuture(ldtNull)).isFalse();
        assertThat(isNowOrFuture(ldtFuture)).isTrue();
        assertThat(isNowOrFuture(ldtPast)).isFalse();
        assertThat(isNowOrFuture(ldtNull)).isFalse();
    }

    @Test
    void isFuture_localDate() {
        assertThat(isFuture(ldFuture)).isTrue();
        assertThat(isFuture(ldPast)).isFalse();
        assertThat(isFuture(ldNull)).isFalse();
        assertThat(isNowOrFuture(ldFuture)).isTrue();
        assertThat(isNowOrFuture(ldPast)).isFalse();
        assertThat(isNowOrFuture(ldNull)).isFalse();
    }

    @Test
    void isFuture_localTime() {
        assertThat(isFuture(ltFuture)).isTrue();
        assertThat(isFuture(ltPast)).isFalse();
        assertThat(isFuture(ltNull)).isFalse();
        assertThat(isNowOrFuture(ltFuture)).isTrue();
        assertThat(isNowOrFuture(ltPast)).isFalse();
        assertThat(isNowOrFuture(ltNull)).isFalse();
    }

    @Test
    void isPast_zonedDateTime() {
        assertThat(isPast(zdtFuture)).isFalse();
        assertThat(isPast(zdtPast)).isTrue();
        assertThat(isPast(zdtNull)).isFalse();
        assertThat(isNowOrPast(zdtFuture)).isFalse();
        assertThat(isNowOrPast(zdtPast)).isTrue();
        assertThat(isNowOrPast(zdtNull)).isFalse();
    }

    @Test
    void isPast_offsetDateTime() {
        assertThat(isPast(odtFuture)).isFalse();
        assertThat(isPast(odtPast)).isTrue();
        assertThat(isPast(odtNull)).isFalse();
        assertThat(isNowOrPast(odtFuture)).isFalse();
        assertThat(isNowOrPast(odtPast)).isTrue();
        assertThat(isNowOrPast(odtNull)).isFalse();
    }

    @Test
    void isPast_localDateTime() {
        assertThat(isPast(ldtFuture)).isFalse();
        assertThat(isPast(ldtPast)).isTrue();
        assertThat(isPast(ldtNull)).isFalse();
        assertThat(isNowOrPast(ldtFuture)).isFalse();
        assertThat(isNowOrPast(ldtPast)).isTrue();
        assertThat(isNowOrPast(ldtNull)).isFalse();
    }

    @Test
    void isPast_localDate() {
        assertThat(isPast(ldFuture)).isFalse();
        assertThat(isPast(ldPast)).isTrue();
        assertThat(isPast(ldNull)).isFalse();
        assertThat(isNowOrPast(ldFuture)).isFalse();
        assertThat(isNowOrPast(ldPast)).isTrue();
        assertThat(isNowOrPast(ldNull)).isFalse();
    }

    @Test
    void isPast_localTime() {
        assertThat(isPast(ltFuture)).isFalse();
        assertThat(isPast(ltPast)).isTrue();
        assertThat(isPast(ltNull)).isFalse();
        assertThat(isNowOrPast(ltFuture)).isFalse();
        assertThat(isNowOrPast(ltPast)).isTrue();
        assertThat(isNowOrPast(ltNull)).isFalse();
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
