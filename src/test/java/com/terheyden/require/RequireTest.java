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
import java.util.Random;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;

import static com.terheyden.require.Require.requireFalse;
import static com.terheyden.require.Require.requireTrue;
import static java.util.Collections.emptyList;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.assertj.core.api.Assertions.assertThatIllegalStateException;
import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatNullPointerException;

/**
 * RequireTest unit tests.
 */
@SuppressWarnings("LongLine")
class RequireTest {

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
    void testRequireTrue() {
        assertThatNoException().isThrownBy(() -> requireTrue(true));
        assertThatIllegalArgumentException().isThrownBy(() -> requireTrue(false)).withMessage("Condition is false");
        assertThatIllegalArgumentException().isThrownBy(() -> requireTrue(false, BAD)).withMessage(BAD);
    }

    @Test
    void testRequireState() {
        assertThatNoException().isThrownBy(() -> Require.requireState(true));
        assertThatIllegalStateException()
            .isThrownBy(() -> Require.requireState(false))
            .withMessage("Condition is false");
        assertThatIllegalStateException().isThrownBy(() -> Require.requireState(false, BAD)).withMessage(BAD);
    }

    @Test
    void testRequreFalse() {
        assertThatNoException().isThrownBy(() -> requireFalse(false));
        assertThatIllegalArgumentException().isThrownBy(() -> requireFalse(true)).withMessage("Condition is true");
        assertThatIllegalArgumentException().isThrownBy(() -> requireFalse(true, BAD)).withMessage(BAD);
    }

    @Test
    void testRequireNotNull() {
        assertThatNoException().isThrownBy(() -> Require.requireNotNull(goodStr));
        assertThatNullPointerException()
            .isThrownBy(() -> Require.requireNotNull(nullStr))
            .withMessage("Object is null");
        assertThatNullPointerException()
            .isThrownBy(() -> Require.requireNotNull(nullStr, BAD))
            .withMessage(BAD + " is null");
    }

    @Test
    void testRequireNotEmpty_string() {
        assertThatNoException().isThrownBy(() -> Require.requireNotEmpty(goodStr));
        assertThatNullPointerException()
            .isThrownBy(() -> Require.requireNotEmpty(nullStr))
            .withMessage("String is null");
        assertThatNullPointerException()
            .isThrownBy(() -> Require.requireNotEmpty(nullStr, BAD))
            .withMessage(BAD + " is null");
        assertThatIllegalArgumentException()
            .isThrownBy(() -> Require.requireNotEmpty(emptyStr))
            .withMessage("String is empty");
        assertThatIllegalArgumentException()
            .isThrownBy(() -> Require.requireNotEmpty(emptyStr, BAD))
            .withMessage(BAD + " is empty");
    }

    @Test
    void testRequireNotEmpty_collection() {
        assertThatNoException().isThrownBy(() -> Require.requireNotEmpty(goodList));
        assertThatNullPointerException()
            .isThrownBy(() -> Require.requireNotEmpty(nullList))
            .withMessage("Collection is null");
        assertThatNullPointerException()
            .isThrownBy(() -> Require.requireNotEmpty(nullList, BAD))
            .withMessage(BAD + " is null");
        assertThatIllegalArgumentException()
            .isThrownBy(() -> Require.requireNotEmpty(emptyList))
            .withMessage("Collection is empty");
        assertThatIllegalArgumentException()
            .isThrownBy(() -> Require.requireNotEmpty(emptyList, BAD))
            .withMessage(BAD + " is empty");
    }

    @Test
    void testRequireNotEmpty_iterable() {
        assertThatNoException().isThrownBy(() -> Require.requireNotEmpty(goodIter));
        assertThatNullPointerException()
            .isThrownBy(() -> Require.requireNotEmpty(nullIter))
            .withMessage("Iterable is null");
        assertThatNullPointerException()
            .isThrownBy(() -> Require.requireNotEmpty(nullIter, BAD))
            .withMessage(BAD + " is null");
        assertThatIllegalArgumentException()
            .isThrownBy(() -> Require.requireNotEmpty(emptyIter))
            .withMessage("Iterable is empty");
        assertThatIllegalArgumentException()
            .isThrownBy(() -> Require.requireNotEmpty(emptyIter, BAD))
            .withMessage(BAD + " is empty");
    }

    @Test
    void requireNotEmpty_map() {
        assertThatNoException().isThrownBy(() -> Require.requireNotEmpty(goodMap));
        assertThatNullPointerException().isThrownBy(() -> Require.requireNotEmpty(nullMap)).withMessage("Map is null");
        assertThatNullPointerException()
            .isThrownBy(() -> Require.requireNotEmpty(nullMap, BAD))
            .withMessage(BAD + " is null");
        assertThatIllegalArgumentException()
            .isThrownBy(() -> Require.requireNotEmpty(emptyMap))
            .withMessage("Map is empty");
        assertThatIllegalArgumentException()
            .isThrownBy(() -> Require.requireNotEmpty(emptyMap, BAD))
            .withMessage(BAD + " is empty");
    }

    @Test
    void requireNotEmpty_array() {
        assertThatNoException().isThrownBy(() -> Require.requireNotEmpty(goodArray));
        assertThatNullPointerException()
            .isThrownBy(() -> Require.requireNotEmpty(nullArray))
            .withMessage("Array is null");
        assertThatNullPointerException()
            .isThrownBy(() -> Require.requireNotEmpty(nullArray, BAD))
            .withMessage(BAD + " is null");
        assertThatIllegalArgumentException()
            .isThrownBy(() -> Require.requireNotEmpty(emptyArray))
            .withMessage("Array is empty");
        assertThatIllegalArgumentException()
            .isThrownBy(() -> Require.requireNotEmpty(emptyArray, BAD))
            .withMessage(BAD + " is empty");
    }

    @Test
    void requireNotBlank() {
        assertThatNoException().isThrownBy(() -> Require.requireNotBlank(goodStr));
        assertThatNullPointerException()
            .isThrownBy(() -> Require.requireNotBlank(nullStr))
            .withMessage("String is null");
        assertThatNullPointerException()
            .isThrownBy(() -> Require.requireNotBlank(nullStr, BAD))
            .withMessage(BAD + " is null");
        assertThatIllegalArgumentException()
            .isThrownBy(() -> Require.requireNotBlank(emptyStr))
            .withMessage("String is empty");
        assertThatIllegalArgumentException()
            .isThrownBy(() -> Require.requireNotBlank(emptyStr, BAD))
            .withMessage(BAD + " is empty");
        assertThatIllegalArgumentException()
            .isThrownBy(() -> Require.requireNotBlank(blankStr))
            .withMessage("String is blank");
        assertThatIllegalArgumentException()
            .isThrownBy(() -> Require.requireNotBlank(blankStr, BAD))
            .withMessage(BAD + " is blank");
    }

    @Test
    void testRequireLength_string() {
        assertThatNoException().isThrownBy(() -> Require.requireMinLength(goodStr, goodStr.length()));
        assertThatNullPointerException()
            .isThrownBy(() -> Require.requireMinLength(nullStr, 1))
            .withMessage("String is null");
        assertThatNullPointerException()
            .isThrownBy(() -> Require.requireMinLength(nullStr, 1, BAD))
            .withMessage(BAD + " is null");
        assertThatIllegalArgumentException()
            .isThrownBy(() -> Require.requireMinLength(goodStr, goodStr.length() + 1))
            .withMessage("String length (4) is less than minimum: 5 — contains: good");
        assertThatIllegalArgumentException()
            .isThrownBy(() -> Require.requireMinLength(goodStr, goodStr.length() + 1, BAD))
            .withMessage(BAD + " (4) is less than minimum: 5 — contains: good");
    }

    @Test
    void testRequireLength_array() {
        assertThatNoException().isThrownBy(() -> Require.requireLength(goodArray, goodArray.length, BAD));
        assertThatNullPointerException().isThrownBy(() -> Require.requireLength(nullArray, 1, BAD)).withMessage(BAD + " is null");
        assertThatIllegalArgumentException().isThrownBy(() -> Require.requireLength(goodArray, goodArray.length + 1, BAD)).withMessage(BAD + " (3) is not equal to: 4 — contains: [a, b, c]");
    }

    @Test
    void testRequireMinLength_array() {
        assertThatNoException().isThrownBy(() -> Require.requireMinLength(goodArray, goodArray.length));
        assertThatNullPointerException()
            .isThrownBy(() -> Require.requireMinLength(nullArray, 1))
            .withMessage("Array is null");
        assertThatNullPointerException()
            .isThrownBy(() -> Require.requireMinLength(nullArray, 1, BAD))
            .withMessage(BAD + " is null");
        assertThatIllegalArgumentException()
            .isThrownBy(() -> Require.requireMinLength(goodArray, goodArray.length + 1))
            .withMessage("Array length (3) is less than minimum: 4 — contains: [a, b, c]");
        assertThatIllegalArgumentException()
            .isThrownBy(() -> Require.requireMinLength(goodArray, goodArray.length + 1, BAD))
            .withMessage(BAD + " (3) is less than minimum: 4 — contains: [a, b, c]");
    }

    @Test
    void requireSize_collection() {
        assertThatNoException().isThrownBy(() -> Require.requireMinSize(goodList, goodList.size()));
        assertThatNullPointerException()
            .isThrownBy(() -> Require.requireMinSize(nullList, 1))
            .withMessage("Collection is null");
        assertThatNullPointerException()
            .isThrownBy(() -> Require.requireMinSize(nullList, 1, BAD))
            .withMessage(BAD + " is null");
        assertThatIllegalArgumentException()
            .isThrownBy(() -> Require.requireMinSize(goodList, goodList.size() + 1))
            .withMessage("Collection size (3) is less than minimum: 4 — contains: [a, b, c]");
        assertThatIllegalArgumentException()
            .isThrownBy(() -> Require.requireMinSize(goodList, goodList.size() + 1, BAD))
            .withMessage(BAD + " (3) is less than minimum: 4 — contains: [a, b, c]");
    }

    @Test
    void requireSize_map() {
        assertThatNoException().isThrownBy(() -> Require.requireMinSize(goodMap, goodMap.size()));
        assertThatNullPointerException().isThrownBy(() -> Require.requireMinSize(nullMap, 1)).withMessage("Map is null");
        assertThatNullPointerException()
            .isThrownBy(() -> Require.requireMinSize(nullMap, 1, BAD))
            .withMessage(BAD + " is null");
        assertThatIllegalArgumentException()
            .isThrownBy(() -> Require.requireMinSize(goodMap, goodMap.size() + 1))
            .withMessage("Map size (2) is less than minimum: 3 — contains: {a=1, b=2}");
        assertThatIllegalArgumentException()
            .isThrownBy(() -> Require.requireMinSize(goodMap, goodMap.size() + 1, BAD))
            .withMessage(BAD + " (2) is less than minimum: 3 — contains: {a=1, b=2}");
    }

    @Test
    void requireMin() {
        assertThatNoException().isThrownBy(() -> Require.requireMinValue(1, 0));
        assertThatIllegalArgumentException()
            .isThrownBy(() -> Require.requireMinValue(0, 1))
            .withMessage("Value (0) is less than minimum: 1");
        assertThatIllegalArgumentException()
            .isThrownBy(() -> Require.requireMinValue(0, 1, BAD))
            .withMessage(BAD + " (0) is less than minimum: 1");
    }

    @Test
    void requireMax() {
        assertThatNoException().isThrownBy(() -> Require.requireMaxValue(0, 1));
        assertThatIllegalArgumentException()
            .isThrownBy(() -> Require.requireMaxValue(1, 0))
            .withMessage("Value (1) is greater than maximum: 0");
        assertThatIllegalArgumentException()
            .isThrownBy(() -> Require.requireMaxValue(1, 0, BAD))
            .withMessage(BAD + " (1) is greater than maximum: 0");
    }

    @Test
    void requirePathExists() {
        assertThatNoException().isThrownBy(() -> Require.requirePathExists(goodPathFile));
        assertThatNoException().isThrownBy(() -> Require.requirePathExists(goodPathDir));
        assertThatNoException().isThrownBy(() -> Require.requirePathExists(goodFileDir));
        assertThatNoException().isThrownBy(() -> Require.requirePathExists(goodFileFile));
        assertThatNoException().isThrownBy(() -> Require.requirePathExists(GOOD_FILE_STR));
        assertThatNoException().isThrownBy(() -> Require.requirePathExists(GOOD_DIR_STR));
        assertThatNullPointerException().isThrownBy(() -> Require.requirePathExists(nullFile)).withMessage("Path is null");
        assertThatNullPointerException().isThrownBy(() -> Require.requirePathExists(nullFile, BAD)).withMessage(BAD + " is null");
        assertThatNullPointerException().isThrownBy(() -> Require.requirePathExists(nullPath)).withMessage("Path is null");
        assertThatNullPointerException().isThrownBy(() -> Require.requirePathExists(nullPath, BAD)).withMessage(BAD + " is null");
        assertThatNullPointerException().isThrownBy(() -> Require.requirePathExists(nullStr)).withMessage("Path is null");
        assertThatNullPointerException().isThrownBy(() -> Require.requirePathExists(nullStr, BAD)).withMessage(BAD + " is null");
        assertThatIllegalArgumentException().isThrownBy(() -> Require.requirePathExists(badPathFile)).withMessageContainingAll("Path does not exist:", BAD);
        assertThatIllegalArgumentException().isThrownBy(() -> Require.requirePathExists(badPathFile, BAD)).withMessageContainingAll(BAD + " does not exist:", BAD);
        assertThatIllegalArgumentException().isThrownBy(() -> Require.requirePathExists(badFileFile)).withMessageContainingAll("Path does not exist:", BAD);
        assertThatIllegalArgumentException().isThrownBy(() -> Require.requirePathExists(badFileFile, BAD)).withMessageContainingAll(BAD + " does not exist:", BAD);
        assertThatIllegalArgumentException().isThrownBy(() -> Require.requirePathExists(BAD)).withMessageContainingAll("Path does not exist:", BAD);
        assertThatIllegalArgumentException().isThrownBy(() -> Require.requirePathExists(BAD, BAD)).withMessageContainingAll(BAD + " does not exist:", BAD);
    }

    @Test
    void requirePathNotExists() {
        assertThatNoException().isThrownBy(() -> Require.requirePathNotExists(badPathFile));
        assertThatNoException().isThrownBy(() -> Require.requirePathNotExists(badPathDir));
        assertThatNoException().isThrownBy(() -> Require.requirePathNotExists(badFileDir));
        assertThatNoException().isThrownBy(() -> Require.requirePathNotExists(badFileFile));
        assertThatNoException().isThrownBy(() -> Require.requirePathNotExists(BAD));
        assertThatNullPointerException().isThrownBy(() -> Require.requirePathNotExists(nullFile)).withMessage("Path is null");
        assertThatNullPointerException().isThrownBy(() -> Require.requirePathNotExists(nullFile, BAD)).withMessage(BAD + " is null");
        assertThatNullPointerException().isThrownBy(() -> Require.requirePathNotExists(nullPath)).withMessage("Path is null");
        assertThatNullPointerException().isThrownBy(() -> Require.requirePathNotExists(nullPath, BAD)).withMessage(BAD + " is null");
        assertThatNullPointerException().isThrownBy(() -> Require.requirePathNotExists(nullStr)).withMessage("Path is null");
        assertThatNullPointerException().isThrownBy(() -> Require.requirePathNotExists(nullStr, BAD)).withMessage(BAD + " is null");
        assertThatIllegalArgumentException().isThrownBy(() -> Require.requirePathNotExists(goodPathFile)).withMessageContainingAll("Path exists:", GOOD_FILE_STR);
        assertThatIllegalArgumentException().isThrownBy(() -> Require.requirePathNotExists(goodPathFile, BAD)).withMessageContainingAll(BAD + " exists:", GOOD_FILE_STR);
        assertThatIllegalArgumentException().isThrownBy(() -> Require.requirePathNotExists(goodFileFile)).withMessageContainingAll("Path exists:", GOOD_FILE_STR);
        assertThatIllegalArgumentException().isThrownBy(() -> Require.requirePathNotExists(goodFileFile, BAD)).withMessageContainingAll(BAD + " exists:", GOOD_FILE_STR);
        assertThatIllegalArgumentException().isThrownBy(() -> Require.requirePathNotExists(GOOD_FILE_STR)).withMessageContainingAll("Path exists:", GOOD_FILE_STR);
        assertThatIllegalArgumentException().isThrownBy(() -> Require.requirePathNotExists(GOOD_FILE_STR, BAD)).withMessageContainingAll(BAD + " exists:", GOOD_FILE_STR);
    }

    @Test
    void requireDirectory() {
        assertThatNoException().isThrownBy(() -> Require.requireDirectory(goodPathDir));
        assertThatNoException().isThrownBy(() -> Require.requireDirectory(goodFileDir));
        assertThatNoException().isThrownBy(() -> Require.requireDirectory(GOOD_DIR_STR));
        assertThatNullPointerException().isThrownBy(() -> Require.requireDirectory(nullFile)).withMessage("Directory is null");
        assertThatNullPointerException().isThrownBy(() -> Require.requireDirectory(nullFile, BAD)).withMessage(BAD + " is null");
        assertThatNullPointerException().isThrownBy(() -> Require.requireDirectory(nullPath)).withMessage("Directory is null");
        assertThatNullPointerException().isThrownBy(() -> Require.requireDirectory(nullPath, BAD)).withMessage(BAD + " is null");
        assertThatNullPointerException().isThrownBy(() -> Require.requireDirectory(nullStr)).withMessage("Directory path is null");
        assertThatNullPointerException().isThrownBy(() -> Require.requireDirectory(nullStr, BAD)).withMessage(BAD + " is null");
        assertThatIllegalArgumentException().isThrownBy(() -> Require.requireDirectory(badPathFile)).withMessageContainingAll("Directory does not exist:", BAD);
        assertThatIllegalArgumentException().isThrownBy(() -> Require.requireDirectory(badPathFile, BAD)).withMessageContainingAll(BAD + " does not exist:", BAD);
        assertThatIllegalArgumentException().isThrownBy(() -> Require.requireDirectory(goodPathFile)).withMessageContaining("Directory is not a directory:");
        assertThatIllegalArgumentException().isThrownBy(() -> Require.requireDirectory(goodPathFile, BAD)).withMessageContaining(BAD + " is not a directory:");
        assertThatIllegalArgumentException().isThrownBy(() -> Require.requireDirectory(goodFileFile)).withMessageContaining("Directory is not a directory:");
        assertThatIllegalArgumentException().isThrownBy(() -> Require.requireDirectory(goodFileFile, BAD)).withMessageContaining(BAD + " is not a directory:");
        assertThatIllegalArgumentException().isThrownBy(() -> Require.requireDirectory(BAD)).withMessageContainingAll("Directory does not exist:", BAD);
        assertThatIllegalArgumentException().isThrownBy(() -> Require.requireDirectory(BAD, BAD)).withMessageContainingAll(BAD + " does not exist:", BAD);
    }

    @Test
    void requireRegularFile() {
        assertThatNoException().isThrownBy(() -> Require.requireRegularFile(goodPathFile));
        assertThatNoException().isThrownBy(() -> Require.requireRegularFile(goodFileFile));
        assertThatNoException().isThrownBy(() -> Require.requireRegularFile(GOOD_FILE_STR));
        assertThatNullPointerException().isThrownBy(() -> Require.requireRegularFile(nullFile)).withMessage("File is null");
        assertThatNullPointerException().isThrownBy(() -> Require.requireRegularFile(nullFile, BAD)).withMessage(BAD + " is null");
        assertThatNullPointerException().isThrownBy(() -> Require.requireRegularFile(nullPath)).withMessage("File is null");
        assertThatNullPointerException().isThrownBy(() -> Require.requireRegularFile(nullPath, BAD)).withMessage(BAD + " is null");
        assertThatNullPointerException().isThrownBy(() -> Require.requireRegularFile(nullStr)).withMessage("File path is null");
        assertThatNullPointerException().isThrownBy(() -> Require.requireRegularFile(nullStr, BAD)).withMessage(BAD + " is null");
        assertThatIllegalArgumentException().isThrownBy(() -> Require.requireRegularFile(badPathFile)).withMessageContainingAll("File does not exist:", BAD);
        assertThatIllegalArgumentException().isThrownBy(() -> Require.requireRegularFile(badPathFile, BAD)).withMessageContainingAll(BAD + " does not exist:", BAD);
        assertThatIllegalArgumentException().isThrownBy(() -> Require.requireRegularFile(goodPathDir)).withMessageContaining("File is not a regular file:");
        assertThatIllegalArgumentException().isThrownBy(() -> Require.requireRegularFile(goodPathDir, BAD)).withMessageContaining(BAD + " is not a regular file:");
        assertThatIllegalArgumentException().isThrownBy(() -> Require.requireRegularFile(goodFileDir)).withMessageContaining("File is not a regular file:");
        assertThatIllegalArgumentException().isThrownBy(() -> Require.requireRegularFile(goodFileDir, BAD)).withMessageContaining(BAD + " is not a regular file:");
        assertThatIllegalArgumentException().isThrownBy(() -> Require.requireRegularFile(BAD)).withMessageContainingAll("File does not exist:", BAD);
        assertThatIllegalArgumentException().isThrownBy(() -> Require.requireRegularFile(BAD, BAD)).withMessageContainingAll(BAD + " does not exist:", BAD);
    }

    @Test
    void requireFuture() {
        assertThatNoException().isThrownBy(() -> Require.requireFuture(zdtFuture));
        assertThatNoException().isThrownBy(() -> Require.requireFuture(odtFuture));
        assertThatNoException().isThrownBy(() -> Require.requireFuture(ldtFuture));
        assertThatNoException().isThrownBy(() -> Require.requireFuture(ldFuture));
        assertThatNoException().isThrownBy(() -> Require.requireFuture(ltFuture));
        assertThatNoException().isThrownBy(() -> Require.requireFuture(zdtFuture, BAD));
        assertThatNoException().isThrownBy(() -> Require.requireFuture(odtFuture, BAD));
        assertThatNoException().isThrownBy(() -> Require.requireFuture(ldtFuture, BAD));
        assertThatNoException().isThrownBy(() -> Require.requireFuture(ldFuture, BAD));
        assertThatNoException().isThrownBy(() -> Require.requireFuture(ltFuture, BAD));
        assertThatIllegalArgumentException().isThrownBy(() -> Require.requireFuture(zdtPast)).withMessageContaining("Date Time is not in the future:");
        assertThatIllegalArgumentException().isThrownBy(() -> Require.requireFuture(zdtPast, BAD)).withMessageContaining(BAD + " is not in the future:");
        assertThatIllegalArgumentException().isThrownBy(() -> Require.requireFuture(odtPast)).withMessageContaining("Date Time is not in the future:");
        assertThatIllegalArgumentException().isThrownBy(() -> Require.requireFuture(odtPast, BAD)).withMessageContaining(BAD + " is not in the future:");
        assertThatIllegalArgumentException().isThrownBy(() -> Require.requireFuture(ldtPast)).withMessageContaining("Date Time is not in the future:");
        assertThatIllegalArgumentException().isThrownBy(() -> Require.requireFuture(ldtPast, BAD)).withMessageContaining(BAD + " is not in the future:");
        assertThatIllegalArgumentException().isThrownBy(() -> Require.requireFuture(ldPast)).withMessageContaining("Date is not in the future:");
        assertThatIllegalArgumentException().isThrownBy(() -> Require.requireFuture(ldPast, BAD)).withMessageContaining(BAD + " is not in the future:");
        assertThatIllegalArgumentException().isThrownBy(() -> Require.requireFuture(ltPast)).withMessageContaining("Time is not in the future:");
        assertThatIllegalArgumentException().isThrownBy(() -> Require.requireFuture(ltPast, BAD)).withMessageContaining(BAD + " is not in the future:");
        assertThatNullPointerException().isThrownBy(() -> Require.requireFuture(zdtNull)).withMessage("Date Time is null");
        assertThatNullPointerException().isThrownBy(() -> Require.requireFuture(zdtNull, BAD)).withMessage(BAD + " is null");
        assertThatNullPointerException().isThrownBy(() -> Require.requireFuture(odtNull)).withMessage("Date Time is null");
        assertThatNullPointerException().isThrownBy(() -> Require.requireFuture(odtNull, BAD)).withMessage(BAD + " is null");
        assertThatNullPointerException().isThrownBy(() -> Require.requireFuture(ldtNull)).withMessage("Date Time is null");
        assertThatNullPointerException().isThrownBy(() -> Require.requireFuture(ldtNull, BAD)).withMessage(BAD + " is null");
        assertThatNullPointerException().isThrownBy(() -> Require.requireFuture(ldNull)).withMessage("Date is null");
        assertThatNullPointerException().isThrownBy(() -> Require.requireFuture(ldNull, BAD)).withMessage(BAD + " is null");
        assertThatNullPointerException().isThrownBy(() -> Require.requireFuture(ltNull)).withMessage("Time is null");
        assertThatNullPointerException().isThrownBy(() -> Require.requireFuture(ltNull, BAD)).withMessage(BAD + " is null");
    }

    @Test
    void requirePast() {
        assertThatNoException().isThrownBy(() -> Require.requirePast(zdtPast));
        assertThatNoException().isThrownBy(() -> Require.requirePast(odtPast));
        assertThatNoException().isThrownBy(() -> Require.requirePast(ldtPast));
        assertThatNoException().isThrownBy(() -> Require.requirePast(ldPast));
        assertThatNoException().isThrownBy(() -> Require.requirePast(ltPast));
        assertThatNoException().isThrownBy(() -> Require.requirePast(zdtPast, BAD));
        assertThatNoException().isThrownBy(() -> Require.requirePast(odtPast, BAD));
        assertThatNoException().isThrownBy(() -> Require.requirePast(ldtPast, BAD));
        assertThatNoException().isThrownBy(() -> Require.requirePast(ldPast, BAD));
        assertThatNoException().isThrownBy(() -> Require.requirePast(ltPast, BAD));
        assertThatIllegalArgumentException().isThrownBy(() -> Require.requirePast(zdtFuture)).withMessageContaining("Date Time is not in the past:");
        assertThatIllegalArgumentException().isThrownBy(() -> Require.requirePast(zdtFuture, BAD)).withMessageContaining(BAD + " is not in the past:");
        assertThatIllegalArgumentException().isThrownBy(() -> Require.requirePast(odtFuture)).withMessageContaining("Date Time is not in the past:");
        assertThatIllegalArgumentException().isThrownBy(() -> Require.requirePast(odtFuture, BAD)).withMessageContaining(BAD + " is not in the past:");
        assertThatIllegalArgumentException().isThrownBy(() -> Require.requirePast(ldtFuture)).withMessageContaining("Date Time is not in the past:");
        assertThatIllegalArgumentException().isThrownBy(() -> Require.requirePast(ldtFuture, BAD)).withMessageContaining(BAD + " is not in the past:");
        assertThatIllegalArgumentException().isThrownBy(() -> Require.requirePast(ldFuture)).withMessageContaining("Date is not in the past:");
        assertThatIllegalArgumentException().isThrownBy(() -> Require.requirePast(ldFuture, BAD)).withMessageContaining(BAD + " is not in the past:");
        assertThatIllegalArgumentException().isThrownBy(() -> Require.requirePast(ltFuture)).withMessageContaining("Time is not in the past:");
        assertThatIllegalArgumentException().isThrownBy(() -> Require.requirePast(ltFuture, BAD)).withMessageContaining(BAD + " is not in the past:");
        assertThatNullPointerException().isThrownBy(() -> Require.requirePast(zdtNull)).withMessage("Date Time is null");
        assertThatNullPointerException().isThrownBy(() -> Require.requirePast(odtNull)).withMessage("Date Time is null");
        assertThatNullPointerException().isThrownBy(() -> Require.requirePast(ldtNull)).withMessage("Date Time is null");
        assertThatNullPointerException().isThrownBy(() -> Require.requirePast(ldNull)).withMessage("Date is null");
        assertThatNullPointerException().isThrownBy(() -> Require.requirePast(ltNull)).withMessage("Time is null");
    }

    void betweenThings() {

        // JDK methods that take inclusive, exclusive parameters:
        goodStr.substring(0, 1);
        goodStr.subSequence(0, 1);
        goodList.subList(0, 1);
        Arrays.copyOfRange(goodArray, 0, 1);
        IntStream.range(0, 1);
        new Random().ints(0, 1);
    }
}
