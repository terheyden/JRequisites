package com.terheyden.require;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
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

import static com.terheyden.require.CheckBoolean.hasLength;
import static com.terheyden.require.CheckBoolean.hasLengthBetween;
import static com.terheyden.require.CheckBoolean.hasLengthGreaterOrEqualTo;
import static com.terheyden.require.CheckBoolean.hasLengthGreaterThan;
import static com.terheyden.require.CheckBoolean.hasLengthLessOrEqualTo;
import static com.terheyden.require.CheckBoolean.hasLengthLessThan;
import static com.terheyden.require.CheckBoolean.hasSize;
import static com.terheyden.require.CheckBoolean.hasSizeBetween;
import static com.terheyden.require.CheckBoolean.hasSizeGreaterOrEqualTo;
import static com.terheyden.require.CheckBoolean.hasSizeGreaterThan;
import static com.terheyden.require.CheckBoolean.hasSizeLessOrEqualTo;
import static com.terheyden.require.CheckBoolean.hasSizeLessThan;
import static com.terheyden.require.CheckBoolean.isEmpty;
import static com.terheyden.require.CheckBoolean.isFuture;
import static com.terheyden.require.CheckBoolean.isNotBlank;
import static com.terheyden.require.CheckBoolean.isNotEmpty;
import static com.terheyden.require.CheckBoolean.isNowOrFuture;
import static com.terheyden.require.CheckBoolean.isNowOrPast;
import static com.terheyden.require.CheckBoolean.isPast;
import static com.terheyden.require.CheckBoolean.pathExists;
import static com.terheyden.require.CheckBoolean.pathNotExists;
import static java.util.Collections.emptyList;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * RequireTest unit tests.
 */
@SuppressWarnings("ConstantValue")
class CheckBooleanTest {

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

    @Test
    void isTrue() {
        assertThat(CheckBoolean.isTrue(true)).isTrue();
        assertThat(CheckBoolean.isTrue(false)).isFalse();
    }

    @Test
    void isFalse() {
        assertThat(CheckBoolean.isFalse(true)).isFalse();
        assertThat(CheckBoolean.isFalse(false)).isTrue();
    }

    @Test
    public void isNull() {
        assertThat(CheckBoolean.isNull(nullStr)).isTrue();
        assertThat(CheckBoolean.isNull(goodStr)).isFalse();
    }

    @Test
    void isNotNull() {
        assertThat(CheckBoolean.isNotNull(goodStr)).isTrue();
        assertThat(CheckBoolean.isNotNull(nullStr)).isFalse();
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
        assertThat(isNotEmpty(goodStr)).isTrue();
        assertThat(isNotEmpty(blankStr)).isTrue();
        assertThat(isNotEmpty(emptyStr)).isFalse();
        assertThat(isNotEmpty(nullStr)).isFalse();
    }

    @Test
    void isNotEmpty_collection() {
        assertThat(isNotEmpty(goodList)).isTrue();
        assertThat(isNotEmpty(emptyList)).isFalse();
        assertThat(isNotEmpty(nullList)).isFalse();
    }

    @Test
    void isNotEmpty_iterable() {
        assertThat(isNotEmpty(goodIter)).isTrue();
        assertThat(isNotEmpty(emptyIter)).isFalse();
        assertThat(isNotEmpty(nullIter)).isFalse();
    }

    @Test
    void isNotEmpty_map() {
        assertThat(isNotEmpty(goodMap)).isTrue();
        assertThat(isNotEmpty(emptyMap)).isFalse();
        assertThat(isNotEmpty(nullMap)).isFalse();
    }

    @Test
    void isNotEmpty_array() {
        assertThat(isNotEmpty(goodArray)).isTrue();
        assertThat(isNotEmpty(emptyArray)).isFalse();
        assertThat(isNotEmpty(nullArray)).isFalse();
        assertThat(isNotEmpty(goodPrimitiveArray)).isTrue();
        assertThat(isNotEmpty(emptyPrimitiveArray)).isFalse();
        assertThat(isNotEmpty(nullPrimitiveArray)).isFalse();
    }

    @Test
    void isNotBlank_string() {
        assertThat(isNotBlank(goodStr)).isTrue();
        assertThat(isNotBlank(blankStr)).isFalse();
        assertThat(isNotBlank(emptyStr)).isFalse();
        assertThat(isNotBlank(nullStr)).isFalse();
    }

    @Test
    void hasLength_string() {

        assertThat(hasLength(goodStr, goodStr.length())).isTrue();
        assertThat(hasLength(goodStr, goodStr.length() + 1)).isFalse();
        assertThat(hasLength(goodStr, goodStr.length() - 1)).isFalse();
        assertThat(hasLength(emptyStr, 0)).isTrue();
        assertThat(hasLength(emptyStr, 1)).isFalse();
        assertThat(hasLength(nullStr, 1)).isFalse();
        assertThat(hasLength(nullStr, 0)).isFalse(); // null has no length, it is null

        assertThat(hasLengthGreaterThan(goodStr, goodStr.length() - 1)).isTrue();
        assertThat(hasLengthGreaterThan(goodStr, goodStr.length())).isFalse();
        assertThat(hasLengthGreaterThan(goodStr, goodStr.length() + 1)).isFalse();
        assertThat(hasLengthGreaterThan(blankStr, 1)).isTrue();
        assertThat(hasLengthGreaterThan(emptyStr, 0)).isFalse();
        assertThat(hasLengthGreaterThan(nullStr, 1)).isFalse();
        assertThat(hasLengthGreaterThan(nullStr, 0)).isFalse(); // null has no length, it is null

        assertThat(hasLengthGreaterOrEqualTo(goodStr, goodStr.length())).isTrue();
        assertThat(hasLengthGreaterOrEqualTo(goodStr, goodStr.length() + 1)).isFalse();
        assertThat(hasLengthGreaterOrEqualTo(goodStr, goodStr.length() - 1)).isTrue();
        assertThat(hasLengthGreaterOrEqualTo(blankStr, 1)).isTrue();
        assertThat(hasLengthGreaterOrEqualTo(emptyStr, 1)).isFalse();
        assertThat(hasLengthGreaterOrEqualTo(nullStr, 1)).isFalse();
        assertThat(hasLengthGreaterOrEqualTo(nullStr, 0)).isFalse(); // null has no length, it is null

        assertThat(hasLengthLessThan(goodStr, goodStr.length() + 1)).isTrue();
        assertThat(hasLengthLessThan(goodStr, goodStr.length())).isFalse();
        assertThat(hasLengthLessThan(goodStr, goodStr.length() - 1)).isFalse();
        assertThat(hasLengthLessThan(blankStr, 1)).isFalse();
        assertThat(hasLengthLessThan(emptyStr, 1)).isTrue();
        assertThat(hasLengthLessThan(emptyStr, 0)).isFalse();
        assertThat(hasLengthLessThan(nullStr, 1)).isFalse();
        assertThat(hasLengthLessThan(nullStr, 0)).isFalse(); // null has no length, it is null

        assertThat(hasLengthLessOrEqualTo(goodStr, goodStr.length())).isTrue();
        assertThat(hasLengthLessOrEqualTo(goodStr, goodStr.length() + 1)).isTrue();
        assertThat(hasLengthLessOrEqualTo(goodStr, goodStr.length() - 1)).isFalse();
        assertThat(hasLengthLessOrEqualTo(blankStr, 1)).isFalse();
        assertThat(hasLengthLessOrEqualTo(emptyStr, 1)).isTrue();
        assertThat(hasLengthLessOrEqualTo(nullStr, 1)).isFalse();
        assertThat(hasLengthLessOrEqualTo(nullStr, 0)).isFalse(); // null has no length, it is null

        assertThat(hasLengthBetween(goodStr, goodStr.length(), goodStr.length())).isTrue();
        assertThat(hasLengthBetween(goodStr, 0, goodStr.length())).isTrue();
        assertThat(hasLengthBetween(goodStr, goodStr.length(), goodStr.length() + 1)).isTrue();
        assertThat(hasLengthBetween(goodStr, goodStr.length() + 1, goodStr.length())).isFalse();
        assertThat(hasLengthBetween(emptyStr, 0, 0)).isTrue();
        assertThat(hasLengthBetween(emptyStr, 1, 1)).isFalse();
        assertThat(hasLengthBetween(nullStr, 0, 0)).isFalse(); // null has no length, it is null
    }

    @Test
    void hasLength_array() {

        assertThat(hasLength(goodArray, goodArray.length)).isTrue(); // test max inclusive
        assertThat(hasLength(goodArray, goodArray.length + 1)).isFalse(); // plus or minus 1
        assertThat(hasLength(goodArray, goodArray.length - 1)).isFalse(); // is wrong
        assertThat(hasLength(emptyArray, 0)).isTrue(); // test empty
        assertThat(hasLength(emptyArray, 1)).isFalse();
        assertThat(hasLength(nullArray, 0)).isFalse(); // null has no length, it is null
        assertThat(hasLength(nullArray, 1)).isFalse(); // test null

        assertThat(hasLength(goodPrimitiveArray, goodPrimitiveArray.length)).isTrue(); // test max inclusive
        assertThat(hasLength(goodPrimitiveArray, goodPrimitiveArray.length + 1)).isFalse(); // plus or minus 1
        assertThat(hasLength(goodPrimitiveArray, goodPrimitiveArray.length - 1)).isFalse(); // is wrong
        assertThat(hasLength(emptyPrimitiveArray, 0)).isTrue(); // test empty
        assertThat(hasLength(emptyPrimitiveArray, 1)).isFalse();
        assertThat(hasLength(nullPrimitiveArray, 0)).isFalse(); // null has no length, it is null
        assertThat(hasLength(nullPrimitiveArray, 1)).isFalse(); // test null

        assertThat(hasLengthGreaterThan(goodArray, goodArray.length - 1)).isTrue();
        assertThat(hasLengthGreaterThan(goodArray, goodArray.length)).isFalse();
        assertThat(hasLengthGreaterThan(emptyArray, 0)).isFalse();
        assertThat(hasLengthGreaterThan(nullArray, 0)).isFalse(); // null has no length, it is null

        assertThat(hasLengthGreaterThan(goodPrimitiveArray, goodPrimitiveArray.length - 1)).isTrue();
        assertThat(hasLengthGreaterThan(goodPrimitiveArray, goodPrimitiveArray.length)).isFalse();
        assertThat(hasLengthGreaterThan(emptyPrimitiveArray, 0)).isFalse();
        assertThat(hasLengthGreaterThan(nullPrimitiveArray, 0)).isFalse(); // null has no length, it is null

        assertThat(hasLengthGreaterOrEqualTo(goodArray, goodArray.length)).isTrue();
        assertThat(hasLengthGreaterOrEqualTo(goodArray, goodArray.length + 1)).isFalse();
        assertThat(hasLengthGreaterOrEqualTo(goodArray, goodArray.length - 1)).isTrue();
        assertThat(hasLengthGreaterOrEqualTo(emptyArray, 0)).isTrue();
        assertThat(hasLengthGreaterOrEqualTo(emptyArray, 1)).isFalse();
        assertThat(hasLengthGreaterOrEqualTo(nullArray, 0)).isFalse(); // null has no length, it is null

        assertThat(hasLengthGreaterOrEqualTo(goodPrimitiveArray, goodPrimitiveArray.length)).isTrue();
        assertThat(hasLengthGreaterOrEqualTo(goodPrimitiveArray, goodPrimitiveArray.length + 1)).isFalse();
        assertThat(hasLengthGreaterOrEqualTo(goodPrimitiveArray, goodPrimitiveArray.length - 1)).isTrue();
        assertThat(hasLengthGreaterOrEqualTo(emptyPrimitiveArray, 0)).isTrue();
        assertThat(hasLengthGreaterOrEqualTo(emptyPrimitiveArray, 1)).isFalse();
        assertThat(hasLengthGreaterOrEqualTo(nullPrimitiveArray, 0)).isFalse(); // null has no length, it is null

        assertThat(hasLengthLessThan(goodArray, goodArray.length + 1)).isTrue();
        assertThat(hasLengthLessThan(goodArray, goodArray.length)).isFalse();
        assertThat(hasLengthLessThan(emptyArray, 1)).isTrue();
        assertThat(hasLengthLessThan(emptyArray, 0)).isFalse();
        assertThat(hasLengthLessThan(nullArray, 1)).isFalse();
        assertThat(hasLengthLessThan(nullArray, 0)).isFalse(); // null has no length, it is null

        assertThat(hasLengthLessThan(goodPrimitiveArray, goodPrimitiveArray.length + 1)).isTrue();
        assertThat(hasLengthLessThan(goodPrimitiveArray, goodPrimitiveArray.length)).isFalse();
        assertThat(hasLengthLessThan(emptyPrimitiveArray, 1)).isTrue();
        assertThat(hasLengthLessThan(emptyPrimitiveArray, 0)).isFalse();
        assertThat(hasLengthLessThan(nullPrimitiveArray, 1)).isFalse();
        assertThat(hasLengthLessThan(nullPrimitiveArray, 0)).isFalse(); // null has no length, it is null

        assertThat(hasLengthLessOrEqualTo(goodArray, goodArray.length)).isTrue();
        assertThat(hasLengthLessOrEqualTo(goodArray, goodArray.length + 1)).isTrue();
        assertThat(hasLengthLessOrEqualTo(goodArray, goodArray.length - 1)).isFalse();
        assertThat(hasLengthLessOrEqualTo(emptyArray, 1)).isTrue();
        assertThat(hasLengthLessOrEqualTo(emptyArray, 0)).isTrue();
        assertThat(hasLengthLessOrEqualTo(nullArray, 1)).isFalse();
        assertThat(hasLengthLessOrEqualTo(nullArray, 0)).isFalse(); // null has no length, it is null

        assertThat(hasLengthLessOrEqualTo(goodPrimitiveArray, goodPrimitiveArray.length)).isTrue();
        assertThat(hasLengthLessOrEqualTo(goodPrimitiveArray, goodPrimitiveArray.length + 1)).isTrue();
        assertThat(hasLengthLessOrEqualTo(goodPrimitiveArray, goodPrimitiveArray.length - 1)).isFalse();
        assertThat(hasLengthLessOrEqualTo(emptyPrimitiveArray, 1)).isTrue();
        assertThat(hasLengthLessOrEqualTo(emptyPrimitiveArray, 0)).isTrue();
        assertThat(hasLengthLessOrEqualTo(nullPrimitiveArray, 1)).isFalse();
        assertThat(hasLengthLessOrEqualTo(nullPrimitiveArray, 0)).isFalse(); // null has no length, it is null

        assertThat(hasLengthBetween(goodArray, goodArray.length, goodArray.length)).isTrue(); // length is the same
        assertThat(hasLengthBetween(goodArray, 0, goodArray.length)).isTrue(); // length of upper bound
        assertThat(hasLengthBetween(goodArray, goodArray.length, goodArray.length + 1)).isTrue(); // length is lower bound
        assertThat(hasLengthBetween(goodArray, goodArray.length + 1, goodArray.length + 2)).isFalse(); // length is out of bounds
        assertThat(hasLengthBetween(goodArray, 0, goodArray.length - 1)).isFalse(); // length is out of bounds
        assertThat(hasLengthBetween(emptyArray, 0, 0)).isTrue();
        assertThat(hasLengthBetween(emptyArray, 0, 1)).isTrue();
        assertThat(hasLengthBetween(emptyArray, 1, 1)).isFalse();
        assertThat(hasLengthBetween(nullArray, 0, 0)).isFalse(); // null has no length, it is null

        assertThat(hasLengthBetween(goodPrimitiveArray, goodPrimitiveArray.length, goodPrimitiveArray.length)).isTrue(); // length is the same
        assertThat(hasLengthBetween(goodPrimitiveArray, 0, goodPrimitiveArray.length)).isTrue(); // length of upper bound
        assertThat(hasLengthBetween(goodPrimitiveArray, goodPrimitiveArray.length, goodPrimitiveArray.length + 1)).isTrue(); // length is lower bound
        assertThat(hasLengthBetween(goodPrimitiveArray, goodPrimitiveArray.length + 1, goodPrimitiveArray.length + 2)).isFalse(); // length is out of bounds
        assertThat(hasLengthBetween(goodPrimitiveArray, 0, goodPrimitiveArray.length - 1)).isFalse(); // length is out of bounds
        assertThat(hasLengthBetween(emptyPrimitiveArray, 0, 0)).isTrue();
        assertThat(hasLengthBetween(emptyPrimitiveArray, 0, 1)).isTrue();
        assertThat(hasLengthBetween(emptyPrimitiveArray, 1, 1)).isFalse();
        assertThat(hasLengthBetween(nullPrimitiveArray, 0, 0)).isFalse(); // null has no length, it is null
    }

    @Test
    void hasSize_collection() {

        assertThat(hasSize(goodList, goodList.size())).isTrue();
        assertThat(hasSize(goodList, goodList.size() + 1)).isFalse();
        assertThat(hasSize(goodList, goodList.size() - 1)).isFalse();
        assertThat(hasSize(emptyList, 0)).isTrue();
        assertThat(hasSize(emptyList, 1)).isFalse();
        assertThat(hasSize(nullList, 1)).isFalse();
        assertThat(hasSize(nullList, 0)).isFalse(); // null has no size, it is null

        assertThat(hasSizeGreaterThan(goodList, goodList.size() - 1)).isTrue();
        assertThat(hasSizeGreaterThan(goodList, goodList.size())).isFalse();
        assertThat(hasSizeGreaterThan(goodList, goodList.size() + 1)).isFalse();
        assertThat(hasSizeGreaterThan(emptyList, 1)).isFalse();
        assertThat(hasSizeGreaterThan(nullList, 1)).isFalse();
        assertThat(hasSizeGreaterThan(nullList, 0)).isFalse(); // null has no size, it is null

        assertThat(hasSizeGreaterOrEqualTo(goodList, goodList.size())).isTrue();
        assertThat(hasSizeGreaterOrEqualTo(goodList, goodList.size() + 1)).isFalse();
        assertThat(hasSizeGreaterOrEqualTo(goodList, goodList.size() - 1)).isTrue();
        assertThat(hasSizeGreaterOrEqualTo(goodList, 1)).isTrue();
        assertThat(hasSizeGreaterOrEqualTo(emptyList, 1)).isFalse();
        assertThat(hasSizeGreaterOrEqualTo(emptyList, 0)).isTrue();
        assertThat(hasSizeGreaterOrEqualTo(nullList, 1)).isFalse();
        assertThat(hasSizeGreaterOrEqualTo(nullList, 0)).isFalse(); // null has no size, it is null

        assertThat(hasSizeLessThan(goodList, goodList.size() + 1)).isTrue();
        assertThat(hasSizeLessThan(goodList, goodList.size())).isFalse();
        assertThat(hasSizeLessThan(goodList, goodList.size() - 1)).isFalse();
        assertThat(hasSizeLessThan(emptyList, 1)).isTrue();
        assertThat(hasSizeLessThan(nullList, 1)).isFalse();
        assertThat(hasSizeLessThan(nullList, 0)).isFalse(); // null has no size, it is null

        assertThat(hasSizeLessOrEqualTo(goodList, goodList.size())).isTrue();
        assertThat(hasSizeLessOrEqualTo(goodList, goodList.size() + 1)).isTrue();
        assertThat(hasSizeLessOrEqualTo(goodList, goodList.size() - 1)).isFalse();
        assertThat(hasSizeLessOrEqualTo(emptyList, 1)).isTrue();
        assertThat(hasSizeLessOrEqualTo(nullList, 1)).isFalse();
        assertThat(hasSizeLessOrEqualTo(nullList, 0)).isFalse(); // null has no size, it is null

        assertThat(hasSizeBetween(goodList, goodList.size(), goodList.size())).isTrue();
        assertThat(hasSizeBetween(goodList, 0, goodList.size())).isTrue();
        assertThat(hasSizeBetween(goodList, goodList.size(), goodList.size() + 1)).isTrue();
        assertThat(hasSizeBetween(goodList, goodList.size() + 1, goodList.size())).isFalse();
        assertThat(hasSizeBetween(goodList, 1, goodList.size() - 1)).isFalse();
        assertThat(hasSizeBetween(emptyList, 0, 0)).isTrue();
        assertThat(hasSizeBetween(emptyList, 1, 1)).isFalse();
        assertThat(hasSizeBetween(nullList, 0, 0)).isFalse(); // null has no size, it is null
    }

    @Test
    void hasSize_iterable() {

        assertThat(hasSize(goodIter, goodList.size())).isTrue();
        assertThat(hasSize(goodIter, goodList.size() + 1)).isFalse();
        assertThat(hasSize(goodIter, goodList.size() - 1)).isFalse();
        assertThat(hasSize(emptyIter, 0)).isTrue();
        assertThat(hasSize(emptyIter, 1)).isFalse();
        assertThat(hasSize(nullIter, 1)).isFalse();
        assertThat(hasSize(nullIter, 0)).isFalse(); // null has no size, it is null

        assertThat(hasSizeGreaterThan(goodIter, goodList.size() - 1)).isTrue();
        assertThat(hasSizeGreaterThan(goodIter, goodList.size())).isFalse();
        assertThat(hasSizeGreaterThan(goodIter, goodList.size() + 1)).isFalse();
        assertThat(hasSizeGreaterThan(emptyIter, 1)).isFalse();
        assertThat(hasSizeGreaterThan(nullIter, 1)).isFalse();
        assertThat(hasSizeGreaterThan(nullIter, 0)).isFalse(); // null has no size, it is null

        assertThat(hasSizeGreaterOrEqualTo(goodIter, goodList.size())).isTrue();
        assertThat(hasSizeGreaterOrEqualTo(goodIter, goodList.size() + 1)).isFalse();
        assertThat(hasSizeGreaterOrEqualTo(goodIter, goodList.size() - 1)).isTrue();
        assertThat(hasSizeGreaterOrEqualTo(goodIter, 1)).isTrue();
        assertThat(hasSizeGreaterOrEqualTo(emptyIter, 1)).isFalse();
        assertThat(hasSizeGreaterOrEqualTo(emptyIter, 0)).isTrue();
        assertThat(hasSizeGreaterOrEqualTo(nullIter, 1)).isFalse();
        assertThat(hasSizeGreaterOrEqualTo(nullIter, 0)).isFalse(); // null has no size, it is null

        assertThat(hasSizeLessThan(goodIter, goodList.size() + 1)).isTrue();
        assertThat(hasSizeLessThan(goodIter, goodList.size())).isFalse();
        assertThat(hasSizeLessThan(goodIter, goodList.size() - 1)).isFalse();
        assertThat(hasSizeLessThan(emptyIter, 1)).isTrue();
        assertThat(hasSizeLessThan(nullIter, 1)).isFalse();
        assertThat(hasSizeLessThan(nullIter, 0)).isFalse(); // null has no size, it is null

        assertThat(hasSizeLessOrEqualTo(goodIter, goodList.size())).isTrue();
        assertThat(hasSizeLessOrEqualTo(goodIter, goodList.size() + 1)).isTrue();
        assertThat(hasSizeLessOrEqualTo(goodIter, goodList.size() - 1)).isFalse();
        assertThat(hasSizeLessOrEqualTo(emptyIter, 1)).isTrue();
        assertThat(hasSizeLessOrEqualTo(nullIter, 1)).isFalse();
        assertThat(hasSizeLessOrEqualTo(nullIter, 0)).isFalse(); // null has no size, it is null

        assertThat(hasSizeBetween(goodIter, goodList.size(), goodList.size())).isTrue();
        assertThat(hasSizeBetween(goodIter, 0, goodList.size())).isTrue();
        assertThat(hasSizeBetween(goodIter, goodList.size(), goodList.size() + 1)).isTrue();
        assertThat(hasSizeBetween(goodIter, goodList.size() + 1, goodList.size())).isFalse();
        assertThat(hasSizeBetween(goodIter, 1, goodList.size() - 1)).isFalse();
        assertThat(hasSizeBetween(emptyIter, 0, 0)).isTrue();
        assertThat(hasSizeBetween(emptyIter, 1, 1)).isFalse();
        assertThat(hasSizeBetween(nullIter, 0, 0)).isFalse(); // null has no size, it is null
    }

    @Test
    void hasSize_map() {

        assertThat(hasSize(goodMap, goodMap.size())).isTrue();
        assertThat(hasSize(goodMap, goodMap.size() + 1)).isFalse();
        assertThat(hasSize(goodMap, goodMap.size() - 1)).isFalse();
        assertThat(hasSize(emptyMap, 0)).isTrue();
        assertThat(hasSize(emptyMap, 1)).isFalse();
        assertThat(hasSize(nullMap, 1)).isFalse();
        assertThat(hasSize(nullMap, 0)).isFalse(); // null has no size, it is null

        assertThat(hasSizeGreaterThan(goodMap, goodMap.size() - 1)).isTrue();
        assertThat(hasSizeGreaterThan(goodMap, goodMap.size())).isFalse();
        assertThat(hasSizeGreaterThan(goodMap, goodMap.size() + 1)).isFalse();
        assertThat(hasSizeGreaterThan(emptyMap, 1)).isFalse();
        assertThat(hasSizeGreaterThan(nullMap, 1)).isFalse();
        assertThat(hasSizeGreaterThan(nullMap, 0)).isFalse(); // null has no size, it is null

        assertThat(hasSizeGreaterOrEqualTo(goodMap, goodMap.size())).isTrue();
        assertThat(hasSizeGreaterOrEqualTo(goodMap, goodMap.size() + 1)).isFalse();
        assertThat(hasSizeGreaterOrEqualTo(goodMap, goodMap.size() - 1)).isTrue();
        assertThat(hasSizeGreaterOrEqualTo(goodMap, 1)).isTrue();
        assertThat(hasSizeGreaterOrEqualTo(emptyMap, 0)).isTrue();
        assertThat(hasSizeGreaterOrEqualTo(emptyMap, 1)).isFalse();
        assertThat(hasSizeGreaterOrEqualTo(nullMap, 1)).isFalse();
        assertThat(hasSizeGreaterOrEqualTo(nullMap, 0)).isFalse(); // null has no size, it is null

        assertThat(hasSizeLessThan(goodMap, goodMap.size() + 1)).isTrue();
        assertThat(hasSizeLessThan(goodMap, goodMap.size())).isFalse();
        assertThat(hasSizeLessThan(goodMap, goodMap.size() - 1)).isFalse();
        assertThat(hasSizeLessThan(emptyMap, 1)).isTrue();
        assertThat(hasSizeLessThan(nullMap, 1)).isFalse();
        assertThat(hasSizeLessThan(nullMap, 0)).isFalse(); // null has no size, it is null

        assertThat(hasSizeLessOrEqualTo(goodMap, goodMap.size())).isTrue();
        assertThat(hasSizeLessOrEqualTo(goodMap, goodMap.size() + 1)).isTrue();
        assertThat(hasSizeLessOrEqualTo(goodMap, goodMap.size() - 1)).isFalse();
        assertThat(hasSizeLessOrEqualTo(emptyMap, 1)).isTrue();
        assertThat(hasSizeLessOrEqualTo(emptyMap, 0)).isTrue();
        assertThat(hasSizeLessOrEqualTo(nullMap, 1)).isFalse();
        assertThat(hasSizeLessOrEqualTo(nullMap, 0)).isFalse(); // null has no size, it is null

        assertThat(hasSizeBetween(goodMap, goodMap.size(), goodMap.size())).isTrue();
        assertThat(hasSizeBetween(goodMap, 0, goodMap.size())).isTrue();
        assertThat(hasSizeBetween(goodMap, goodMap.size(), goodMap.size() + 1)).isTrue();
        assertThat(hasSizeBetween(goodMap, goodMap.size() + 1, goodMap.size())).isFalse();
        assertThat(hasSizeBetween(goodMap, 1, goodMap.size() - 1)).isFalse();
        assertThat(hasSizeBetween(emptyMap, 0, 0)).isTrue();
        assertThat(hasSizeBetween(emptyMap, 1, 1)).isFalse();
        assertThat(hasSizeBetween(nullMap, 0, 0)).isFalse(); // null has no size, it is null
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
        assertThat(CheckBoolean.isRegularFile(goodPathFile)).isTrue();
        assertThat(CheckBoolean.isRegularFile(goodPathDir)).isFalse();
        assertThat(CheckBoolean.isRegularFile(badPathFile)).isFalse();
        assertThat(CheckBoolean.isRegularFile(nullPath)).isFalse();
    }

    @Test
    void isRegularFile_file() {
        assertThat(CheckBoolean.isRegularFile(goodFileFile)).isTrue();
        assertThat(CheckBoolean.isRegularFile(goodFileDir)).isFalse();
        assertThat(CheckBoolean.isRegularFile(badFileFile)).isFalse();
        assertThat(CheckBoolean.isRegularFile(nullFile)).isFalse();
    }

    @Test
    void isRegularFile_string() {
        assertThat(CheckBoolean.isRegularFile(GOOD_FILE_STR)).isTrue();
        assertThat(CheckBoolean.isRegularFile(GOOD_DIR_STR)).isFalse();
        assertThat(CheckBoolean.isRegularFile(BAD)).isFalse();
        assertThat(CheckBoolean.isRegularFile(nullStr)).isFalse();
    }

    @Test
    void isDirectory_path() {
        assertThat(CheckBoolean.isDirectory(goodPathFile)).isFalse();
        assertThat(CheckBoolean.isDirectory(goodPathDir)).isTrue();
        assertThat(CheckBoolean.isDirectory(badPathFile)).isFalse();
        assertThat(CheckBoolean.isDirectory(nullPath)).isFalse();
    }

    @Test
    void isDirectory_file() {
        assertThat(CheckBoolean.isDirectory(goodFileDir)).isTrue();
        assertThat(CheckBoolean.isDirectory(badFileDir)).isFalse();
        assertThat(CheckBoolean.isDirectory(nullFile)).isFalse();
        assertThat(CheckBoolean.isDirectory(goodFileFile)).isFalse();
    }

    @Test
    void isDirectory_string() {
        assertThat(CheckBoolean.isDirectory(GOOD_FILE_STR)).isFalse();
        assertThat(CheckBoolean.isDirectory(GOOD_DIR_STR)).isTrue();
        assertThat(CheckBoolean.isDirectory(BAD)).isFalse();
        assertThat(CheckBoolean.isDirectory(nullStr)).isFalse();
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
    void isInstanceOf() {
        assertThat(CheckBoolean.isInstanceOf(goodStr, CharSequence.class)).isTrue();
        assertThat(CheckBoolean.isInstanceOf(goodStr, Map.class)).isFalse();
        assertThat(CheckBoolean.isNotInstanceOf(goodStr, CharSequence.class)).isFalse();
        assertThat(CheckBoolean.isNotInstanceOf(goodStr, Map.class)).isTrue();
    }
}
