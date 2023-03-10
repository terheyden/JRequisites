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
class CheckTest {

    private static final String ERROR = "error";
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
    void checkArg() {
        assertThat(Check.check(goodStr, true)).isNotEmpty();
        assertThat(Check.check(goodStr, false)).isEmpty();
    }

    @Test
    void checkState() {
        assertThat(Check.checkState(goodStr, true)).isNotEmpty();
        assertThat(Check.checkState(goodStr, false)).isEmpty();
    }

    @Test
    void checkNotNull() {
        assertThat(Check.checkNotNull(goodStr)).isNotEmpty();
        assertThat(Check.checkNotNull(nullStr)).isEmpty();
    }

    @Test
    void checkNotEmpty_string() {
        assertThat(Check.checkNotEmpty(goodStr)).isNotEmpty();
        assertThat(Check.checkNotEmpty(nullStr)).isEmpty();
        assertThat(Check.checkNotEmpty(emptyStr)).isEmpty();
    }

    @Test
    void checkNotEmpty_collection() {
        assertThat(Check.checkNotEmpty(goodList)).isNotEmpty();
        assertThat(Check.checkNotEmpty(nullList)).isEmpty();
        assertThat(Check.checkNotEmpty(emptyList)).isEmpty();
    }

    @Test
    void checkNotEmpty_map() {
        assertThat(Check.checkNotEmpty(goodMap)).isNotEmpty();
        assertThat(Check.checkNotEmpty(nullMap)).isEmpty();
        assertThat(Check.checkNotEmpty(emptyMap)).isEmpty();
    }

    @Test
    void checkNotEmpty_array() {
        assertThat(Check.checkNotEmpty(goodArray)).isNotEmpty();
        assertThat(Check.checkNotEmpty(nullArray)).isEmpty();
        assertThat(Check.checkNotEmpty(emptyArray)).isEmpty();
    }

    @Test
    void checkNotBlank() {
        assertThat(Check.checkNotBlank(goodStr)).isNotEmpty();
        assertThat(Check.checkNotBlank(nullStr)).isEmpty();
        assertThat(Check.checkNotBlank(emptyStr)).isEmpty();
        assertThat(Check.checkNotBlank(blankStr)).isEmpty();
    }

    @Test
    void checkLength_array() {
        assertThat(Check.checkMinLength(goodArray, goodArray.length)).isNotEmpty();
        assertThat(Check.checkMinLength(nullArray, 1)).isEmpty();
        assertThat(Check.checkMinLength(goodArray, goodArray.length + 1)).isEmpty();
    }

    @Test
    void checkLength_string() {
        assertThat(Check.checkMinLength(goodStr, goodStr.length())).isNotEmpty();
        assertThat(Check.checkMinLength(nullStr, 1)).isEmpty();
        assertThat(Check.checkMinLength(goodStr, goodStr.length() + 1)).isEmpty();
    }

    @Test
    void checkSize_collection() {
        assertThat(Check.checkMinSize(goodList, goodList.size())).isNotEmpty();
        assertThat(Check.checkMinSize(nullList, 1)).isEmpty();
        assertThat(Check.checkMinSize(goodList, goodList.size() + 1)).isEmpty();
    }

    @Test
    void checkSize_map() {
        assertThat(Check.checkMinSize(goodMap, goodMap.size())).isNotEmpty();
        assertThat(Check.checkMinSize(nullMap, 1)).isEmpty();
        assertThat(Check.checkMinSize(goodMap, goodMap.size() + 1)).isEmpty();
    }

    @Test
    void checkPathExists_path() {
        assertThat(Check.checkPathExists(goodPathDir)).isNotEmpty();
        assertThat(Check.checkPathExists(goodPathFile)).isNotEmpty();
        assertThat(Check.checkPathExists(badPathDir)).isEmpty();
        assertThat(Check.checkPathExists(badPathFile)).isEmpty();
        assertThat(Check.checkPathExists(nullPath)).isEmpty();
    }

    @Test
    void checkPathExists_file() {
        assertThat(Check.checkPathExists(goodFileDir)).isNotEmpty();
        assertThat(Check.checkPathExists(goodFileFile)).isNotEmpty();
        assertThat(Check.checkPathExists(badFileDir)).isEmpty();
        assertThat(Check.checkPathExists(badFileFile)).isEmpty();
        assertThat(Check.checkPathExists(nullFile)).isEmpty();
    }

    @Test
    void checkPathExists_string() {
        assertThat(Check.checkPathExists(GOOD_DIR_STR)).isNotEmpty();
        assertThat(Check.checkPathExists(GOOD_FILE_STR)).isNotEmpty();
        assertThat(Check.checkPathExists(BAD)).isEmpty();
        assertThat(Check.checkPathExists(nullStr)).isEmpty();
    }

    @Test
    void checkPathNotExists_path() {
        assertThat(Check.checkPathNotExists(goodPathDir)).isEmpty();
        assertThat(Check.checkPathNotExists(goodPathFile)).isEmpty();
        assertThat(Check.checkPathNotExists(badPathDir)).isNotEmpty();
        assertThat(Check.checkPathNotExists(badPathFile)).isNotEmpty();
        assertThat(Check.checkPathNotExists(nullPath)).isEmpty();
    }

    @Test
    void checkPathNotExists_file() {
        assertThat(Check.checkPathNotExists(goodFileDir)).isEmpty();
        assertThat(Check.checkPathNotExists(goodFileFile)).isEmpty();
        assertThat(Check.checkPathNotExists(badFileDir)).isNotEmpty();
        assertThat(Check.checkPathNotExists(badFileFile)).isNotEmpty();
        assertThat(Check.checkPathNotExists(nullFile)).isEmpty();
    }

    @Test
    void checkPathNotExists_string() {
        assertThat(Check.checkPathNotExists(GOOD_DIR_STR)).isEmpty();
        assertThat(Check.checkPathNotExists(GOOD_FILE_STR)).isEmpty();
        assertThat(Check.checkPathNotExists(BAD)).isNotEmpty();
        assertThat(Check.checkPathNotExists(nullStr)).isEmpty();
    }

    @Test
    void checkRegularFile_path() {
        assertThat(Check.checkRegularFile(goodPathFile)).isNotEmpty();
        assertThat(Check.checkRegularFile(goodPathDir)).isEmpty();
        assertThat(Check.checkRegularFile(badPathFile)).isEmpty();
        assertThat(Check.checkRegularFile(badPathDir)).isEmpty();
        assertThat(Check.checkRegularFile(nullPath)).isEmpty();
    }

    @Test
    void checkRegularFile_file() {
        assertThat(Check.checkRegularFile(goodFileFile)).isNotEmpty();
        assertThat(Check.checkRegularFile(goodFileDir)).isEmpty();
        assertThat(Check.checkRegularFile(badFileFile)).isEmpty();
        assertThat(Check.checkRegularFile(badFileDir)).isEmpty();
        assertThat(Check.checkRegularFile(nullFile)).isEmpty();
    }

    @Test
    void checkRegularFile_string() {
        assertThat(Check.checkRegularFile(GOOD_FILE_STR)).isNotEmpty();
        assertThat(Check.checkRegularFile(GOOD_DIR_STR)).isEmpty();
        assertThat(Check.checkRegularFile(BAD)).isEmpty();
        assertThat(Check.checkRegularFile(nullStr)).isEmpty();
    }

    @Test
    void checkDirectory_path() {
        assertThat(Check.checkDirectory(goodPathDir)).isNotEmpty();
        assertThat(Check.checkDirectory(goodPathFile)).isEmpty();
        assertThat(Check.checkDirectory(badPathDir)).isEmpty();
        assertThat(Check.checkDirectory(badPathFile)).isEmpty();
        assertThat(Check.checkDirectory(nullPath)).isEmpty();
    }

    @Test
    void checkDirectory_file() {
        assertThat(Check.checkDirectory(goodFileDir)).isNotEmpty();
        assertThat(Check.checkDirectory(goodFileFile)).isEmpty();
        assertThat(Check.checkDirectory(badFileDir)).isEmpty();
        assertThat(Check.checkDirectory(badFileFile)).isEmpty();
        assertThat(Check.checkDirectory(nullFile)).isEmpty();
    }

    @Test
    void checkDirectory_string() {
        assertThat(Check.checkDirectory(GOOD_DIR_STR)).isNotEmpty();
        assertThat(Check.checkDirectory(GOOD_FILE_STR)).isEmpty();
        assertThat(Check.checkDirectory(BAD)).isEmpty();
        assertThat(Check.checkDirectory(nullStr)).isEmpty();
    }

    @Test
    void checkFuture_zonedDateTime() {
        assertThat(Check.checkFuture(zdtFuture)).isNotEmpty();
        assertThat(Check.checkFuture(zdtPast)).isEmpty();
        assertThat(Check.checkFuture(zdtNull)).isEmpty();
    }

    @Test
    void checkFuture_offsetDateTime() {
        assertThat(Check.checkFuture(odtFuture)).isNotEmpty();
        assertThat(Check.checkFuture(odtPast)).isEmpty();
        assertThat(Check.checkFuture(odtNull)).isEmpty();
    }

    @Test
    void checkFuture_localDateTime() {
        assertThat(Check.checkFuture(ldtFuture)).isNotEmpty();
        assertThat(Check.checkFuture(ldtPast)).isEmpty();
        assertThat(Check.checkFuture(ldtNull)).isEmpty();
    }

    @Test
    void checkFuture_localDate() {
        assertThat(Check.checkFuture(ldFuture)).isNotEmpty();
        assertThat(Check.checkFuture(ldPast)).isEmpty();
        assertThat(Check.checkFuture(ldNull)).isEmpty();
    }

    @Test
    void checkFuture_localTime() {
        assertThat(Check.checkFuture(ltFuture)).isNotEmpty();
        assertThat(Check.checkFuture(ltPast)).isEmpty();
        assertThat(Check.checkFuture(ltNull)).isEmpty();
    }

    @Test
    void checkPast_zonedDateTime() {
        assertThat(Check.checkPast(zdtFuture)).isEmpty();
        assertThat(Check.checkPast(zdtPast)).isNotEmpty();
        assertThat(Check.checkPast(zdtNull)).isEmpty();
    }

    @Test
    void checkPast_offsetDateTime() {
        assertThat(Check.checkPast(odtFuture)).isEmpty();
        assertThat(Check.checkPast(odtPast)).isNotEmpty();
        assertThat(Check.checkPast(odtNull)).isEmpty();
    }

    @Test
    void checkPast_localDateTime() {
        assertThat(Check.checkPast(ldtFuture)).isEmpty();
        assertThat(Check.checkPast(ldtPast)).isNotEmpty();
        assertThat(Check.checkPast(ldtNull)).isEmpty();
    }

    @Test
    void checkPast_localDate() {
        assertThat(Check.checkPast(ldFuture)).isEmpty();
        assertThat(Check.checkPast(ldPast)).isNotEmpty();
        assertThat(Check.checkPast(ldNull)).isEmpty();
    }

    @Test
    void checkPast_localTime() {
        assertThat(Check.checkPast(ltFuture)).isEmpty();
        assertThat(Check.checkPast(ltPast)).isNotEmpty();
        assertThat(Check.checkPast(ltNull)).isEmpty();
    }
}
