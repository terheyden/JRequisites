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
import static com.terheyden.require.Checks.containsRegex;
import static com.terheyden.require.Checks.isBetween;
import static com.terheyden.require.Checks.isBlank;
import static com.terheyden.require.Checks.isEmpty;
import static com.terheyden.require.Checks.isFuture;
import static com.terheyden.require.Checks.isGreaterOrEqualTo;
import static com.terheyden.require.Checks.isGreaterThan;
import static com.terheyden.require.Checks.isLessOrEqualTo;
import static com.terheyden.require.Checks.isLessThan;
import static com.terheyden.require.Checks.isNotBlank;
import static com.terheyden.require.Checks.isNowOrFuture;
import static com.terheyden.require.Checks.isNowOrPast;
import static com.terheyden.require.Checks.isNullOrBlank;
import static com.terheyden.require.Checks.isPast;
import static com.terheyden.require.Checks.isSize;
import static com.terheyden.require.Checks.isSizeBetween;
import static com.terheyden.require.Checks.isSizeGreaterOrEqualTo;
import static com.terheyden.require.Checks.isSizeGreaterThan;
import static com.terheyden.require.Checks.isSizeLessOrEqualTo;
import static com.terheyden.require.Checks.isSizeLessThan;
import static com.terheyden.require.Checks.matchesRegex;
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
    private final Iterable<String> goodIter = goodList;
    private final Iterable<String> emptyIter = emptyList;
    private final Iterable<String> nullIter = null;
    private final Map<String, Integer> goodMap = new HashMap() {{
        put("a", 1);
        put("b", 2);
    }};
    private final Map<String, String> emptyMap = Collections.emptyMap();
    private final Map<String, String> nullMap = null;
    private final String[] goodArray = new String[] { "a", "b", "c" };
    private final String[] emptyArray = new String[0];
    private final String[] nullArray = null;
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
    private final Duration oneMin = Duration.ofMinutes(1);
    private final Duration fiveMins = Duration.ofMinutes(5);
    private final Duration tenMins = Duration.ofMinutes(10);
    private final Duration fifteenMins = Duration.ofMinutes(15);
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
    void isNotNull() {
        assertThat(Checks.isNotNull(goodStr)).isTrue();
        assertThat(Checks.isNotNull(nullStr)).isFalse();
    }

    @Test
    void testIsDigitsOnly() {
        assertThat(Checks.isDigitsOnly("123")).isTrue();
        assertThat(Checks.isDigitsOnly("123.")).isFalse();
        assertThat(Checks.isDigitsOnly("")).isTrue();
        assertThat(Checks.isDigitsOnly(null)).isFalse();
    }

    @Test
    void testIsAlphaOnly() {
        assertThat(Checks.isAlphaOnly("abc")).isTrue();
        assertThat(Checks.isAlphaOnly("abc123")).isFalse();
        assertThat(Checks.isAlphaOnly("")).isTrue();
        assertThat(Checks.isAlphaOnly(null)).isFalse();
    }

    @Test
    void testIsAlphaNumericOnly() {
        assertThat(Checks.isAlphaNumericOnly("abc123")).isTrue();
        assertThat(Checks.isAlphaNumericOnly("123")).isTrue();
        assertThat(Checks.isAlphaNumericOnly("abc")).isTrue();
        assertThat(Checks.isAlphaNumericOnly("abc123.")).isFalse();
        assertThat(Checks.isAlphaNumericOnly("")).isTrue();
        assertThat(Checks.isAlphaNumericOnly(null)).isFalse();
    }

    @Test
    void isEmpty_string() {
        assertThat(isEmpty(goodStr)).isFalse();
        assertThat(isEmpty(blankStr)).isFalse();
        assertThat(isEmpty(emptyStr)).isTrue();
        assertThat(isEmpty(nullStr)).isFalse();
    }

    @Test
    void isEmpty_collection() {
        assertThat(isEmpty(goodList)).isFalse();
        assertThat(isEmpty(emptyList)).isTrue();
        assertThat(isEmpty(nullList)).isFalse();
    }

    @Test
    void isEmpty_iterable() {
        assertThat(isEmpty(goodIter)).isFalse();
        assertThat(isEmpty(emptyIter)).isTrue();
        assertThat(isEmpty(nullIter)).isFalse();
    }

    @Test
    void isEmpty_map() {
        assertThat(isEmpty(goodMap)).isFalse();
        assertThat(isEmpty(emptyMap)).isTrue();
        assertThat(isEmpty(nullMap)).isFalse();
    }

    @Test
    void isEmpty_array() {
        assertThat(isEmpty(goodArray)).isFalse();
        assertThat(isEmpty(emptyArray)).isTrue();
        assertThat(isEmpty(nullArray)).isFalse();
        assertThat(isEmpty(goodPrimitiveArray)).isFalse();
        assertThat(isEmpty(emptyPrimitiveArray)).isTrue();
        assertThat(isEmpty(nullPrimitiveArray)).isFalse();
    }

    @Test
    void isNotEmpty_string() {
        assertThat(Checks.isNotEmpty(goodStr)).isTrue();
        assertThat(Checks.isNotEmpty(blankStr)).isTrue();
        assertThat(Checks.isNotEmpty(emptyStr)).isFalse();
        assertThat(Checks.isNotEmpty(nullStr)).isFalse();
    }

    @Test
    void isNotEmpty_collection() {
        assertThat(Checks.isNotEmpty(goodList)).isTrue();
        assertThat(Checks.isNotEmpty(emptyList)).isFalse();
        assertThat(Checks.isNotEmpty(nullList)).isFalse();
    }

    @Test
    void isNotEmpty_iterable() {
        assertThat(Checks.isNotEmpty(goodIter)).isTrue();
        assertThat(Checks.isNotEmpty(emptyIter)).isFalse();
        assertThat(Checks.isNotEmpty(nullIter)).isFalse();
    }

    @Test
    void isNotEmpty_map() {
        assertThat(Checks.isNotEmpty(goodMap)).isTrue();
        assertThat(Checks.isNotEmpty(emptyMap)).isFalse();
        assertThat(Checks.isNotEmpty(nullMap)).isFalse();
    }

    @Test
    void isNotEmpty_array() {
        assertThat(Checks.isNotEmpty(goodArray)).isTrue();
        assertThat(Checks.isNotEmpty(emptyArray)).isFalse();
        assertThat(Checks.isNotEmpty(nullArray)).isFalse();
        assertThat(Checks.isNotEmpty(goodPrimitiveArray)).isTrue();
        assertThat(Checks.isNotEmpty(emptyPrimitiveArray)).isFalse();
        assertThat(Checks.isNotEmpty(nullPrimitiveArray)).isFalse();
    }

    @Test
    void nullOrEmpty_string() {
        assertThat(Checks.isNullOrEmpty(goodStr)).isFalse();
        assertThat(Checks.isNullOrEmpty(blankStr)).isFalse();
        assertThat(Checks.isNullOrEmpty(emptyStr)).isTrue();
        assertThat(Checks.isNullOrEmpty(nullStr)).isTrue();
    }

    @Test
    void nullOrEmpty_collection() {
        assertThat(Checks.isNullOrEmpty(goodList)).isFalse();
        assertThat(Checks.isNullOrEmpty(emptyList)).isTrue();
        assertThat(Checks.isNullOrEmpty(nullList)).isTrue();
    }

    @Test
    void nullOrEmpty_iterable() {
        assertThat(Checks.isNullOrEmpty(goodIter)).isFalse();
        assertThat(Checks.isNullOrEmpty(emptyIter)).isTrue();
        assertThat(Checks.isNullOrEmpty(nullIter)).isTrue();
    }

    @Test
    void nullOrEmpty_map() {
        assertThat(Checks.isNullOrEmpty(goodMap)).isFalse();
        assertThat(Checks.isNullOrEmpty(emptyMap)).isTrue();
        assertThat(Checks.isNullOrEmpty(nullMap)).isTrue();
    }

    @Test
    void nullOrEmpty_array() {
        assertThat(Checks.isNullOrEmpty(goodArray)).isFalse();
        assertThat(Checks.isNullOrEmpty(emptyArray)).isTrue();
        assertThat(Checks.isNullOrEmpty(nullArray)).isTrue();
        assertThat(Checks.isNullOrEmpty(goodPrimitiveArray)).isFalse();
        assertThat(Checks.isNullOrEmpty(emptyPrimitiveArray)).isTrue();
        assertThat(Checks.isNullOrEmpty(nullPrimitiveArray)).isTrue();
    }

    @Test
    void testIsGreaterThan() {
        assertThat(isGreaterThan(0, 1)).isTrue();
        assertThat(isGreaterThan(1, 1)).isFalse();
        assertThat(isGreaterThan(2, 1)).isFalse();
        assertThat(isGreaterThan(0L, 1L)).isTrue();
        assertThat(isGreaterThan(1L, 1L)).isFalse();
        assertThat(isGreaterThan(2L, 1L)).isFalse();
        assertThat(isGreaterThan(0.0, 1.0)).isTrue();
        assertThat(isGreaterThan(1.0, 1.0)).isFalse();
        assertThat(isGreaterThan(2.0, 1.0)).isFalse();
        assertThat(isGreaterThan(0.0f, 1.0f)).isTrue();
        assertThat(isGreaterThan(1.0f, 1.0f)).isFalse();
        assertThat(isGreaterThan(2.0f, 1.0f)).isFalse();
        assertThat(isGreaterThan(oneMin, fiveMins)).isTrue();
        assertThat(isGreaterThan(fiveMins, fiveMins)).isFalse();
        assertThat(isGreaterThan(oneMin, nullDuration)).isFalse();
        assertThat(isGreaterThan(nullDuration, nullDuration)).isFalse();
    }

    @Test
    void testIsGreaterOrEqualTo() {
        assertThat(isGreaterOrEqualTo(0, 1)).isTrue();
        assertThat(isGreaterOrEqualTo(1, 1)).isTrue();
        assertThat(isGreaterOrEqualTo(2, 1)).isFalse();
        assertThat(isGreaterOrEqualTo(0L, 1L)).isTrue();
        assertThat(isGreaterOrEqualTo(1L, 1L)).isTrue();
        assertThat(isGreaterOrEqualTo(2L, 1L)).isFalse();
        assertThat(isGreaterOrEqualTo(0.0, 1.0)).isTrue();
        assertThat(isGreaterOrEqualTo(1.0, 1.0)).isTrue();
        assertThat(isGreaterOrEqualTo(2.0, 1.0)).isFalse();
        assertThat(isGreaterOrEqualTo(0.0f, 1.0f)).isTrue();
        assertThat(isGreaterOrEqualTo(1.0f, 1.0f)).isTrue();
        assertThat(isGreaterOrEqualTo(2.0f, 1.0f)).isFalse();
        assertThat(isGreaterOrEqualTo(oneMin, fiveMins)).isTrue();
        assertThat(isGreaterOrEqualTo(fiveMins, fiveMins)).isTrue();
        assertThat(isGreaterOrEqualTo(oneMin, nullDuration)).isFalse();
        assertThat(isGreaterOrEqualTo(nullDuration, nullDuration)).isFalse();
    }

    @Test
    void testIsLessThan() {
        assertThat(isLessThan(0, 1)).isFalse();
        assertThat(isLessThan(1, 1)).isFalse();
        assertThat(isLessThan(2, 1)).isTrue();
        assertThat(isLessThan(0L, 1L)).isFalse();
        assertThat(isLessThan(1L, 1L)).isFalse();
        assertThat(isLessThan(2L, 1L)).isTrue();
        assertThat(isLessThan(0.0, 1.0)).isFalse();
        assertThat(isLessThan(1.0, 1.0)).isFalse();
        assertThat(isLessThan(2.0, 1.0)).isTrue();
        assertThat(isLessThan(0.0f, 1.0f)).isFalse();
        assertThat(isLessThan(1.0f, 1.0f)).isFalse();
        assertThat(isLessThan(2.0f, 1.0f)).isTrue();
        assertThat(isLessThan(oneMin, fiveMins)).isFalse();
        assertThat(isLessThan(fiveMins, fiveMins)).isFalse();
        assertThat(isLessThan(oneMin, nullDuration)).isFalse();
        assertThat(isLessThan(nullDuration, nullDuration)).isFalse();
    }

    @Test
    void testIsLessOrEqualTo() {
        assertThat(isLessOrEqualTo(0, 1)).isFalse();
        assertThat(isLessOrEqualTo(1, 1)).isTrue();
        assertThat(isLessOrEqualTo(2, 1)).isTrue();
        assertThat(isLessOrEqualTo(0L, 1L)).isFalse();
        assertThat(isLessOrEqualTo(1L, 1L)).isTrue();
        assertThat(isLessOrEqualTo(2L, 1L)).isTrue();
        assertThat(isLessOrEqualTo(0.0, 1.0)).isFalse();
        assertThat(isLessOrEqualTo(1.0, 1.0)).isTrue();
        assertThat(isLessOrEqualTo(2.0, 1.0)).isTrue();
        assertThat(isLessOrEqualTo(0.0f, 1.0f)).isFalse();
        assertThat(isLessOrEqualTo(1.0f, 1.0f)).isTrue();
        assertThat(isLessOrEqualTo(2.0f, 1.0f)).isTrue();
        assertThat(isLessOrEqualTo(oneMin, fiveMins)).isFalse();
        assertThat(isLessOrEqualTo(fiveMins, fiveMins)).isTrue();
        assertThat(isLessOrEqualTo(oneMin, nullDuration)).isFalse();
        assertThat(isLessOrEqualTo(nullDuration, nullDuration)).isFalse();
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
    void isBlank_string() {
        assertThat(isBlank(goodStr)).isFalse();
        assertThat(isBlank(blankStr)).isTrue();
        assertThat(isBlank(emptyStr)).isTrue();
        assertThat(isBlank(nullStr)).isFalse(); // a null cannot be blank
    }

    @Test
    void nullOrBlank_string() {
        assertThat(isNullOrBlank(goodStr)).isFalse();
        assertThat(isNullOrBlank(blankStr)).isTrue();
        assertThat(isNullOrBlank(emptyStr)).isTrue();
        assertThat(isNullOrBlank(nullStr)).isTrue();
    }

    @Test
    void notBlank_string() {
        assertThat(isNotBlank(goodStr)).isTrue();
        assertThat(isNotBlank(blankStr)).isFalse();
        assertThat(isNotBlank(emptyStr)).isFalse();
        assertThat(isNotBlank(nullStr)).isFalse();
    }

    @Test
    void hasLength_string() {

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

        assertThat(Checks.isSize(goodList.size(), goodList)).isTrue();
        assertThat(Checks.isSize(goodList.size() + 1, goodList)).isFalse();
        assertThat(Checks.isSize(goodList.size() - 1, goodList)).isFalse();
        assertThat(Checks.isSize(0, emptyList)).isTrue();
        assertThat(Checks.isSize(1, emptyList)).isFalse();
        assertThat(Checks.isSize(1, nullList)).isFalse();
        assertThat(Checks.isSize(0, nullList)).isFalse(); // null has no size, it is null

        assertThat(Checks.isSizeGreaterThan(goodList.size() - 1, goodList)).isTrue();
        assertThat(Checks.isSizeGreaterThan(goodList.size(), goodList)).isFalse();
        assertThat(Checks.isSizeGreaterThan(goodList.size() + 1, goodList)).isFalse();
        assertThat(Checks.isSizeGreaterThan(1, emptyList)).isFalse();
        assertThat(Checks.isSizeGreaterThan(1, nullList)).isFalse();
        assertThat(Checks.isSizeGreaterThan(0, nullList)).isFalse(); // null has no size, it is null

        assertThat(Checks.isSizeGreaterOrEqualTo(goodList.size(), goodList)).isTrue();
        assertThat(Checks.isSizeGreaterOrEqualTo(goodList.size() + 1, goodList)).isFalse();
        assertThat(Checks.isSizeGreaterOrEqualTo(goodList.size() - 1, goodList)).isTrue();
        assertThat(Checks.isSizeGreaterOrEqualTo(1, goodList)).isTrue();
        assertThat(Checks.isSizeGreaterOrEqualTo(1, emptyList)).isFalse();
        assertThat(Checks.isSizeGreaterOrEqualTo(0, emptyList)).isTrue();
        assertThat(Checks.isSizeGreaterOrEqualTo(1, nullList)).isFalse();
        assertThat(Checks.isSizeGreaterOrEqualTo(0, nullList)).isFalse(); // null has no size, it is null

        assertThat(Checks.isSizeLessThan(goodList.size() + 1, goodList)).isTrue();
        assertThat(Checks.isSizeLessThan(goodList.size(), goodList)).isFalse();
        assertThat(Checks.isSizeLessThan(goodList.size() - 1, goodList)).isFalse();
        assertThat(Checks.isSizeLessThan(1, emptyList)).isTrue();
        assertThat(Checks.isSizeLessThan(1, nullList)).isFalse();
        assertThat(Checks.isSizeLessThan(0, nullList)).isFalse(); // null has no size, it is null

        assertThat(Checks.isSizeLessOrEqualTo(goodList.size(), goodList)).isTrue();
        assertThat(Checks.isSizeLessOrEqualTo(goodList.size() + 1, goodList)).isTrue();
        assertThat(Checks.isSizeLessOrEqualTo(goodList.size() - 1, goodList)).isFalse();
        assertThat(Checks.isSizeLessOrEqualTo(1, emptyList)).isTrue();
        assertThat(Checks.isSizeLessOrEqualTo(1, nullList)).isFalse();
        assertThat(Checks.isSizeLessOrEqualTo(0, nullList)).isFalse(); // null has no size, it is null

        assertThat(Checks.isSizeBetween(goodList.size(), goodList.size(), goodList)).isTrue();
        assertThat(Checks.isSizeBetween(0, goodList.size(), goodList)).isTrue();
        assertThat(Checks.isSizeBetween(goodList.size(), goodList.size() + 1, goodList)).isTrue();
        assertThat(Checks.isSizeBetween(goodList.size() + 1, goodList.size(), goodList)).isFalse();
        assertThat(Checks.isSizeBetween(1, goodList.size() - 1, goodList)).isFalse();
        assertThat(Checks.isSizeBetween(0, 0, emptyList)).isTrue();
        assertThat(Checks.isSizeBetween(1, 1, emptyList)).isFalse();
        assertThat(Checks.isSizeBetween(0, 0, nullList)).isFalse(); // null has no size, it is null
    }

    @Test
    void hasSize_iterable() {

        assertThat(Checks.isSize(goodList.size(), goodIter)).isTrue();
        assertThat(Checks.isSize(goodList.size() + 1, goodIter)).isFalse();
        assertThat(Checks.isSize(goodList.size() - 1, goodIter)).isFalse();
        assertThat(Checks.isSize(0, emptyIter)).isTrue();
        assertThat(Checks.isSize(1, emptyIter)).isFalse();
        assertThat(Checks.isSize(1, nullIter)).isFalse();
        assertThat(Checks.isSize(0, nullIter)).isFalse(); // null has no size, it is null

        assertThat(Checks.isSizeGreaterThan(goodList.size() - 1, goodIter)).isTrue();
        assertThat(Checks.isSizeGreaterThan(goodList.size(), goodIter)).isFalse();
        assertThat(Checks.isSizeGreaterThan(goodList.size() + 1, goodIter)).isFalse();
        assertThat(Checks.isSizeGreaterThan(1, emptyIter)).isFalse();
        assertThat(Checks.isSizeGreaterThan(1, nullIter)).isFalse();
        assertThat(Checks.isSizeGreaterThan(0, nullIter)).isFalse(); // null has no size, it is null

        assertThat(Checks.isSizeGreaterOrEqualTo(goodList.size(), goodIter)).isTrue();
        assertThat(Checks.isSizeGreaterOrEqualTo(goodList.size() + 1, goodIter)).isFalse();
        assertThat(Checks.isSizeGreaterOrEqualTo(goodList.size() - 1, goodIter)).isTrue();
        assertThat(Checks.isSizeGreaterOrEqualTo(1, goodIter)).isTrue();
        assertThat(Checks.isSizeGreaterOrEqualTo(1, emptyIter)).isFalse();
        assertThat(Checks.isSizeGreaterOrEqualTo(0, emptyIter)).isTrue();
        assertThat(Checks.isSizeGreaterOrEqualTo(1, nullIter)).isFalse();
        assertThat(Checks.isSizeGreaterOrEqualTo(0, nullIter)).isFalse(); // null has no size, it is null

        assertThat(Checks.isSizeLessThan(goodList.size() + 1, goodIter)).isTrue();
        assertThat(Checks.isSizeLessThan(goodList.size(), goodIter)).isFalse();
        assertThat(Checks.isSizeLessThan(goodList.size() - 1, goodIter)).isFalse();
        assertThat(Checks.isSizeLessThan(1, emptyIter)).isTrue();
        assertThat(Checks.isSizeLessThan(1, nullIter)).isFalse();
        assertThat(Checks.isSizeLessThan(0, nullIter)).isFalse(); // null has no size, it is null

        assertThat(Checks.isSizeLessOrEqualTo(goodList.size(), goodIter)).isTrue();
        assertThat(Checks.isSizeLessOrEqualTo(goodList.size() + 1, goodIter)).isTrue();
        assertThat(Checks.isSizeLessOrEqualTo(goodList.size() - 1, goodIter)).isFalse();
        assertThat(Checks.isSizeLessOrEqualTo(1, emptyIter)).isTrue();
        assertThat(Checks.isSizeLessOrEqualTo(1, nullIter)).isFalse();
        assertThat(Checks.isSizeLessOrEqualTo(0, nullIter)).isFalse(); // null has no size, it is null

        assertThat(Checks.isSizeBetween(goodList.size(), goodList.size(), goodIter)).isTrue();
        assertThat(Checks.isSizeBetween(0, goodList.size(), goodIter)).isTrue();
        assertThat(Checks.isSizeBetween(goodList.size(), goodList.size() + 1, goodIter)).isTrue();
        assertThat(Checks.isSizeBetween(goodList.size() + 1, goodList.size(), goodIter)).isFalse();
        assertThat(Checks.isSizeBetween(1, goodList.size() - 1, goodIter)).isFalse();
        assertThat(Checks.isSizeBetween(0, 0, emptyIter)).isTrue();
        assertThat(Checks.isSizeBetween(1, 1, emptyIter)).isFalse();
        assertThat(Checks.isSizeBetween(0, 0, nullIter)).isFalse(); // null has no size, it is null
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
        assertThat(Checks.isNowOrFuture(zdtFuture)).isTrue();
        assertThat(Checks.isNowOrFuture(zdtPast)).isFalse();
        assertThat(Checks.isNowOrFuture(zdtNull)).isFalse();
    }

    @Test
    void isFuture_offsetDateTime() {
        assertThat(isFuture(odtFuture)).isTrue();
        assertThat(isFuture(odtPast)).isFalse();
        assertThat(isFuture(odtNull)).isFalse();
        assertThat(Checks.isNowOrFuture(odtFuture)).isTrue();
        assertThat(Checks.isNowOrFuture(odtPast)).isFalse();
        assertThat(Checks.isNowOrFuture(odtNull)).isFalse();
    }

    @Test
    void isFuture_localDateTime() {
        assertThat(isFuture(ldtFuture)).isTrue();
        assertThat(isFuture(ldtPast)).isFalse();
        assertThat(isFuture(ldtNull)).isFalse();
        assertThat(Checks.isNowOrFuture(ldtFuture)).isTrue();
        assertThat(Checks.isNowOrFuture(ldtPast)).isFalse();
        assertThat(Checks.isNowOrFuture(ldtNull)).isFalse();
    }

    @Test
    void isFuture_localDate() {
        assertThat(isFuture(ldFuture)).isTrue();
        assertThat(isFuture(ldPast)).isFalse();
        assertThat(isFuture(ldNull)).isFalse();
        assertThat(Checks.isNowOrFuture(ldFuture)).isTrue();
        assertThat(Checks.isNowOrFuture(ldPast)).isFalse();
        assertThat(Checks.isNowOrFuture(ldNull)).isFalse();
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
        assertThat(Checks.isNowOrPast(zdtFuture)).isFalse();
        assertThat(Checks.isNowOrPast(zdtPast)).isTrue();
        assertThat(Checks.isNowOrPast(zdtNull)).isFalse();
    }

    @Test
    void isPast_offsetDateTime() {
        assertThat(isPast(odtFuture)).isFalse();
        assertThat(isPast(odtPast)).isTrue();
        assertThat(isPast(odtNull)).isFalse();
        assertThat(Checks.isNowOrPast(odtFuture)).isFalse();
        assertThat(Checks.isNowOrPast(odtPast)).isTrue();
        assertThat(Checks.isNowOrPast(odtNull)).isFalse();
    }

    @Test
    void isPast_localDateTime() {
        assertThat(isPast(ldtFuture)).isFalse();
        assertThat(isPast(ldtPast)).isTrue();
        assertThat(isPast(ldtNull)).isFalse();
        assertThat(Checks.isNowOrPast(ldtFuture)).isFalse();
        assertThat(Checks.isNowOrPast(ldtPast)).isTrue();
        assertThat(Checks.isNowOrPast(ldtNull)).isFalse();
    }

    @Test
    void isPast_localDate() {
        assertThat(isPast(ldFuture)).isFalse();
        assertThat(isPast(ldPast)).isTrue();
        assertThat(isPast(ldNull)).isFalse();
        assertThat(Checks.isNowOrPast(ldFuture)).isFalse();
        assertThat(Checks.isNowOrPast(ldPast)).isTrue();
        assertThat(Checks.isNowOrPast(ldNull)).isFalse();
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
        assertThat(Checks.isNotInstanceOf(CharSequence.class, goodStr)).isFalse();
        assertThat(Checks.isNotInstanceOf(Map.class, goodStr)).isTrue();
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
}
