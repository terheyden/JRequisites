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

    // TODO: isNull

    @Test
    void isNotNull() {
        assertThat(CheckBoolean.isNotNull(goodStr)).isTrue();
        assertThat(CheckBoolean.isNotNull(nullStr)).isFalse();
    }

    // TODO: isEmpty

    @Test
    void isNotEmpty_string() {
        assertThat(CheckBoolean.isNotEmpty(goodStr)).isTrue();
        assertThat(CheckBoolean.isNotEmpty(blankStr)).isTrue();
        assertThat(CheckBoolean.isNotEmpty(emptyStr)).isFalse();
        assertThat(CheckBoolean.isNotEmpty(nullStr)).isFalse();
    }

    @Test
    void isNotEmpty_collection() {
        assertThat(CheckBoolean.isNotEmpty(goodList)).isTrue();
        assertThat(CheckBoolean.isNotEmpty(emptyList)).isFalse();
        assertThat(CheckBoolean.isNotEmpty(nullList)).isFalse();
    }

    @Test
    void isNotEmpty_map() {
        assertThat(CheckBoolean.isNotEmpty(goodMap)).isTrue();
        assertThat(CheckBoolean.isNotEmpty(emptyMap)).isFalse();
        assertThat(CheckBoolean.isNotEmpty(nullMap)).isFalse();
    }

    @Test
    void isNotEmpty_array() {
        assertThat(CheckBoolean.isNotEmpty(goodArray)).isTrue();
        assertThat(CheckBoolean.isNotEmpty(emptyArray)).isFalse();
        assertThat(CheckBoolean.isNotEmpty(nullArray)).isFalse();
    }

    @Test
    void isNotBlank_string() {
        assertThat(CheckBoolean.isNotBlank(goodStr)).isTrue();
        assertThat(CheckBoolean.isNotBlank(blankStr)).isFalse();
        assertThat(CheckBoolean.isNotBlank(emptyStr)).isFalse();
        assertThat(CheckBoolean.isNotBlank(nullStr)).isFalse();
    }

    @Test
    void hasLength_string() {
        assertThat(CheckBoolean.hasMinLength(goodStr, goodStr.length())).isTrue();
        assertThat(CheckBoolean.hasMinLength(goodStr, goodStr.length() + 1)).isFalse();
        assertThat(CheckBoolean.hasMinLength(goodStr, goodStr.length() - 1)).isTrue();
        assertThat(CheckBoolean.hasMinLength(blankStr, 1)).isTrue();
        assertThat(CheckBoolean.hasMinLength(emptyStr, 1)).isFalse();
        assertThat(CheckBoolean.hasMinLength(nullStr, 1)).isFalse();
        assertThat(CheckBoolean.hasMinLength(nullStr, 0)).isFalse(); // null has no length, it is null
        // TODO: hasMaxLength
        assertThat(CheckBoolean.hasLengthBetween(goodStr, goodStr.length(), goodStr.length())).isTrue();
        assertThat(CheckBoolean.hasLengthBetween(goodStr, 0,                goodStr.length())).isTrue();
        assertThat(CheckBoolean.hasLengthBetween(goodStr, goodStr.length(), goodStr.length() + 1)).isTrue();
        assertThat(CheckBoolean.hasLengthBetween(goodStr, goodStr.length() + 1, goodStr.length())).isFalse();
        assertThat(CheckBoolean.hasLengthBetween(emptyStr, 0, 0)).isTrue();
        assertThat(CheckBoolean.hasLengthBetween(emptyStr, 1, 1)).isFalse();
        assertThat(CheckBoolean.hasLengthBetween(nullStr, 0, 0)).isFalse(); // null has no length, it is null
    }

    @Test
    void hasLength_array() {
        assertThat(CheckBoolean.hasMinLength(goodArray, goodArray.length)).isTrue();
        assertThat(CheckBoolean.hasMinLength(goodArray, goodArray.length + 1)).isFalse();
        assertThat(CheckBoolean.hasMinLength(goodArray, goodArray.length - 1)).isTrue();
        assertThat(CheckBoolean.hasMinLength(goodArray, 1)).isTrue();
        assertThat(CheckBoolean.hasMinLength(emptyArray, 1)).isFalse();
        assertThat(CheckBoolean.hasMinLength(nullArray, 1)).isFalse();
        assertThat(CheckBoolean.hasMinLength(nullArray, 0)).isFalse(); // null has no length, it is null
        // TODO: hasMaxLength
        assertThat(CheckBoolean.hasLengthBetween(goodArray, goodArray.length, goodArray.length)).isTrue();
        assertThat(CheckBoolean.hasLengthBetween(goodArray, 0,                goodArray.length)).isTrue();
        assertThat(CheckBoolean.hasLengthBetween(goodArray, goodArray.length, goodArray.length + 1)).isTrue();
        assertThat(CheckBoolean.hasLengthBetween(goodArray, goodArray.length + 1, goodArray.length)).isFalse();
        assertThat(CheckBoolean.hasLengthBetween(emptyArray, 0, 0)).isTrue();
        assertThat(CheckBoolean.hasLengthBetween(emptyArray, 1, 1)).isFalse();
        assertThat(CheckBoolean.hasLengthBetween(nullArray, 0, 0)).isFalse(); // null has no length, it is null
    }

    @Test
    void hasSize_collection() {
        assertThat(CheckBoolean.hasMinSize(goodList, goodList.size())).isTrue();
        assertThat(CheckBoolean.hasMinSize(goodList, goodList.size() + 1)).isFalse();
        assertThat(CheckBoolean.hasMinSize(goodList, goodList.size() - 1)).isTrue();
        assertThat(CheckBoolean.hasMinSize(goodList, 1)).isTrue();
        assertThat(CheckBoolean.hasMinSize(emptyList, 1)).isFalse();
        assertThat(CheckBoolean.hasMinSize(nullList, 1)).isFalse();
        assertThat(CheckBoolean.hasMinSize(nullList, 0)).isFalse(); // null has no size, it is null
        // TODO: hasMaxSize
        assertThat(CheckBoolean.hasSizeBetween(goodList, goodList.size(), goodList.size())).isTrue();
        assertThat(CheckBoolean.hasSizeBetween(goodList, 0,                goodList.size())).isTrue();
        assertThat(CheckBoolean.hasSizeBetween(goodList, goodList.size(), goodList.size() + 1)).isTrue();
        assertThat(CheckBoolean.hasSizeBetween(goodList, goodList.size() + 1, goodList.size())).isFalse();
        assertThat(CheckBoolean.hasSizeBetween(emptyList, 0, 0)).isTrue();
        assertThat(CheckBoolean.hasSizeBetween(emptyList, 1, 1)).isFalse();
        assertThat(CheckBoolean.hasSizeBetween(nullList, 0, 0)).isFalse(); // null has no size, it is null
    }

    @Test
    void hasSize_map() {
        assertThat(CheckBoolean.hasMinSize(goodMap, goodMap.size())).isTrue();
        assertThat(CheckBoolean.hasMinSize(goodMap, goodMap.size() + 1)).isFalse();
        assertThat(CheckBoolean.hasMinSize(goodMap, goodMap.size() - 1)).isTrue();
        assertThat(CheckBoolean.hasMinSize(goodMap, 1)).isTrue();
        assertThat(CheckBoolean.hasMinSize(emptyMap, 1)).isFalse();
        assertThat(CheckBoolean.hasMinSize(nullMap, 1)).isFalse();
        assertThat(CheckBoolean.hasMinSize(nullMap, 0)).isFalse(); // null has no size, it is null
        // TODO: hasMaxSize
        assertThat(CheckBoolean.hasSizeBetween(goodMap, goodMap.size(), goodMap.size())).isTrue();
        assertThat(CheckBoolean.hasSizeBetween(goodMap, 0,                goodMap.size())).isTrue();
        assertThat(CheckBoolean.hasSizeBetween(goodMap, goodMap.size(), goodMap.size() + 1)).isTrue();
        assertThat(CheckBoolean.hasSizeBetween(goodMap, goodMap.size() + 1, goodMap.size())).isFalse();
        assertThat(CheckBoolean.hasSizeBetween(emptyMap, 0, 0)).isTrue();
        assertThat(CheckBoolean.hasSizeBetween(emptyMap, 1, 1)).isFalse();
        assertThat(CheckBoolean.hasSizeBetween(nullMap, 0, 0)).isFalse(); // null has no size, it is null
    }

    @Test
    void hasValue() {
        assertThat(CheckBoolean.hasMinValue(1, 1)).isTrue();
        assertThat(CheckBoolean.hasMinValue(1, 2)).isFalse();
        assertThat(CheckBoolean.hasMinValue(2, 1)).isTrue();
        assertThat(CheckBoolean.hasMaxValue(1, 1)).isTrue();
        assertThat(CheckBoolean.hasMaxValue(1, 2)).isTrue();
        assertThat(CheckBoolean.hasMaxValue(2, 1)).isFalse();
        assertThat(CheckBoolean.hasValueBetween(1, 1, 1)).isTrue();
        assertThat(CheckBoolean.hasValueBetween(1, 1, 2)).isTrue();
        assertThat(CheckBoolean.hasValueBetween(1, 2, 1)).isFalse();
        assertThat(CheckBoolean.hasValueBetween(1, 2, 3)).isFalse();
    }

    @Test
    void pathExists_path() {
        assertThat(CheckBoolean.pathExists(goodPathFile)).isTrue();
        assertThat(CheckBoolean.pathExists(goodPathDir)).isTrue();
        assertThat(CheckBoolean.pathExists(badPathFile)).isFalse();
        assertThat(CheckBoolean.pathExists(nullPath)).isFalse();
    }

    @Test
    void pathExists_file() {
        assertThat(CheckBoolean.pathExists(goodFileFile)).isTrue();
        assertThat(CheckBoolean.pathExists(goodFileDir)).isTrue();
        assertThat(CheckBoolean.pathExists(badFileFile)).isFalse();
        assertThat(CheckBoolean.pathExists(nullFile)).isFalse();
    }

    @Test
    void pathExists_string() {
        assertThat(CheckBoolean.pathExists(GOOD_FILE_STR)).isTrue();
        assertThat(CheckBoolean.pathExists(GOOD_DIR_STR)).isTrue();
        assertThat(CheckBoolean.pathExists(BAD)).isFalse();
        assertThat(CheckBoolean.pathExists(nullStr)).isFalse();
    }

    @Test
    void pathNotExists_path() {
        assertThat(CheckBoolean.pathNotExists(goodPathFile)).isFalse();
        assertThat(CheckBoolean.pathNotExists(goodPathDir)).isFalse();
        assertThat(CheckBoolean.pathNotExists(badPathFile)).isTrue();
        assertThat(CheckBoolean.pathNotExists(nullPath)).isTrue();
    }

    @Test
    void pathNotExists_file() {
        assertThat(CheckBoolean.pathNotExists(goodFileFile)).isFalse();
        assertThat(CheckBoolean.pathNotExists(goodFileDir)).isFalse();
        assertThat(CheckBoolean.pathNotExists(badFileFile)).isTrue();
        assertThat(CheckBoolean.pathNotExists(nullFile)).isTrue();
    }

    @Test
    void pathNotExists_string() {
        assertThat(CheckBoolean.pathNotExists(GOOD_FILE_STR)).isFalse();
        assertThat(CheckBoolean.pathNotExists(GOOD_DIR_STR)).isFalse();
        assertThat(CheckBoolean.pathNotExists(BAD)).isTrue();
        assertThat(CheckBoolean.pathNotExists(nullStr)).isTrue();
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
        assertThat(CheckBoolean.isDirectory(goodFileFile)).isFalse();
        assertThat(CheckBoolean.isDirectory(goodFileDir)).isTrue();
        assertThat(CheckBoolean.isDirectory(badFileFile)).isFalse();
        assertThat(CheckBoolean.isDirectory(nullFile)).isFalse();
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
        assertThat(CheckBoolean.isFuture(zdtFuture)).isTrue();
        assertThat(CheckBoolean.isFuture(zdtPast)).isFalse();
        assertThat(CheckBoolean.isFuture(zdtNull)).isFalse();
    }

    @Test
    void isFuture_offsetDateTime() {
        assertThat(CheckBoolean.isFuture(odtFuture)).isTrue();
        assertThat(CheckBoolean.isFuture(odtPast)).isFalse();
        assertThat(CheckBoolean.isFuture(odtNull)).isFalse();
    }

    @Test
    void isFuture_localDateTime() {
        assertThat(CheckBoolean.isFuture(ldtFuture)).isTrue();
        assertThat(CheckBoolean.isFuture(ldtPast)).isFalse();
        assertThat(CheckBoolean.isFuture(ldtNull)).isFalse();
    }

    @Test
    void isFuture_localDate() {
        assertThat(CheckBoolean.isFuture(ldFuture)).isTrue();
        assertThat(CheckBoolean.isFuture(ldPast)).isFalse();
        assertThat(CheckBoolean.isFuture(ldNull)).isFalse();
    }

    @Test
    void isFuture_localTime() {
        assertThat(CheckBoolean.isFuture(ltFuture)).isTrue();
        assertThat(CheckBoolean.isFuture(ltPast)).isFalse();
        assertThat(CheckBoolean.isFuture(ltNull)).isFalse();
    }

    @Test
    void isPast_zonedDateTime() {
        assertThat(CheckBoolean.isPast(zdtFuture)).isFalse();
        assertThat(CheckBoolean.isPast(zdtPast)).isTrue();
        assertThat(CheckBoolean.isPast(zdtNull)).isFalse();
    }

    @Test
    void isPast_offsetDateTime() {
        assertThat(CheckBoolean.isPast(odtFuture)).isFalse();
        assertThat(CheckBoolean.isPast(odtPast)).isTrue();
        assertThat(CheckBoolean.isPast(odtNull)).isFalse();
    }

    @Test
    void isPast_localDateTime() {
        assertThat(CheckBoolean.isPast(ldtFuture)).isFalse();
        assertThat(CheckBoolean.isPast(ldtPast)).isTrue();
        assertThat(CheckBoolean.isPast(ldtNull)).isFalse();
    }

    @Test
    void isPast_localDate() {
        assertThat(CheckBoolean.isPast(ldFuture)).isFalse();
        assertThat(CheckBoolean.isPast(ldPast)).isTrue();
        assertThat(CheckBoolean.isPast(ldNull)).isFalse();
    }

    @Test
    void isPast_localTime() {
        assertThat(CheckBoolean.isPast(ltFuture)).isFalse();
        assertThat(CheckBoolean.isPast(ltPast)).isTrue();
        assertThat(CheckBoolean.isPast(ltNull)).isFalse();
    }
}
