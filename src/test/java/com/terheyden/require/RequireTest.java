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
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.assertj.core.api.Assertions.assertThatIllegalStateException;
import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatNullPointerException;

/**
 * RequireTest unit tests.
 */
@SuppressWarnings("LongLine")
class RequireTest {

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
    void require() {
        assertThatNoException().isThrownBy(() -> Require.require(true));
        assertThatIllegalArgumentException().isThrownBy(() -> Require.require(false)).withMessage("Condition is false");
        assertThatIllegalArgumentException().isThrownBy(() -> Require.require(false, ERROR)).withMessage(ERROR);
    }

    @Test
    void requireState() {
        assertThatNoException().isThrownBy(() -> Require.requireState(true));
        assertThatIllegalStateException()
            .isThrownBy(() -> Require.requireState(false))
            .withMessage("Condition is false");
        assertThatIllegalStateException().isThrownBy(() -> Require.requireState(false, ERROR)).withMessage(ERROR);
    }

    @Test
    void requireNotNull() {
        assertThatNoException().isThrownBy(() -> Require.requireNotNull(goodStr));
        assertThatNullPointerException()
            .isThrownBy(() -> Require.requireNotNull(nullStr))
            .withMessage("Object is null");
        assertThatNullPointerException()
            .isThrownBy(() -> Require.requireNotNull(nullStr, ERROR))
            .withMessage(ERROR + " is null");
    }

    @Test
    void requireNotEmpty_string() {
        assertThatNoException().isThrownBy(() -> Require.requireNotEmpty(goodStr));
        assertThatNullPointerException()
            .isThrownBy(() -> Require.requireNotEmpty(nullStr))
            .withMessage("String is null");
        assertThatNullPointerException()
            .isThrownBy(() -> Require.requireNotEmpty(nullStr, ERROR))
            .withMessage(ERROR + " is null");
        assertThatIllegalArgumentException()
            .isThrownBy(() -> Require.requireNotEmpty(emptyStr))
            .withMessage("String is empty");
        assertThatIllegalArgumentException()
            .isThrownBy(() -> Require.requireNotEmpty(emptyStr, ERROR))
            .withMessage(ERROR + " is empty");
    }

    @Test
    void requireNotEmpty_collection() {
        assertThatNoException().isThrownBy(() -> Require.requireNotEmpty(goodList));
        assertThatNullPointerException()
            .isThrownBy(() -> Require.requireNotEmpty(nullList))
            .withMessage("Collection is null");
        assertThatNullPointerException()
            .isThrownBy(() -> Require.requireNotEmpty(nullList, ERROR))
            .withMessage(ERROR + " is null");
        assertThatIllegalArgumentException()
            .isThrownBy(() -> Require.requireNotEmpty(emptyList))
            .withMessage("Collection is empty");
        assertThatIllegalArgumentException()
            .isThrownBy(() -> Require.requireNotEmpty(emptyList, ERROR))
            .withMessage(ERROR + " is empty");
    }

    @Test
    void requireNotEmpty_map() {
        assertThatNoException().isThrownBy(() -> Require.requireNotEmpty(goodMap));
        assertThatNullPointerException().isThrownBy(() -> Require.requireNotEmpty(nullMap)).withMessage("Map is null");
        assertThatNullPointerException()
            .isThrownBy(() -> Require.requireNotEmpty(nullMap, ERROR))
            .withMessage(ERROR + " is null");
        assertThatIllegalArgumentException()
            .isThrownBy(() -> Require.requireNotEmpty(emptyMap))
            .withMessage("Map is empty");
        assertThatIllegalArgumentException()
            .isThrownBy(() -> Require.requireNotEmpty(emptyMap, ERROR))
            .withMessage(ERROR + " is empty");
    }

    @Test
    void requireNotEmpty_array() {
        assertThatNoException().isThrownBy(() -> Require.requireNotEmpty(goodArray));
        assertThatNullPointerException()
            .isThrownBy(() -> Require.requireNotEmpty(nullArray))
            .withMessage("Array is null");
        assertThatNullPointerException()
            .isThrownBy(() -> Require.requireNotEmpty(nullArray, ERROR))
            .withMessage(ERROR + " is null");
        assertThatIllegalArgumentException()
            .isThrownBy(() -> Require.requireNotEmpty(emptyArray))
            .withMessage("Array is empty");
        assertThatIllegalArgumentException()
            .isThrownBy(() -> Require.requireNotEmpty(emptyArray, ERROR))
            .withMessage(ERROR + " is empty");
    }

    @Test
    void requireNotBlank() {
        assertThatNoException().isThrownBy(() -> Require.requireNotBlank(goodStr));
        assertThatNullPointerException()
            .isThrownBy(() -> Require.requireNotBlank(nullStr))
            .withMessage("String is null");
        assertThatNullPointerException()
            .isThrownBy(() -> Require.requireNotBlank(nullStr, ERROR))
            .withMessage(ERROR + " is null");
        assertThatIllegalArgumentException()
            .isThrownBy(() -> Require.requireNotBlank(emptyStr))
            .withMessage("String is empty");
        assertThatIllegalArgumentException()
            .isThrownBy(() -> Require.requireNotBlank(emptyStr, ERROR))
            .withMessage(ERROR + " is empty");
        assertThatIllegalArgumentException()
            .isThrownBy(() -> Require.requireNotBlank(blankStr))
            .withMessage("String is blank");
        assertThatIllegalArgumentException()
            .isThrownBy(() -> Require.requireNotBlank(blankStr, ERROR))
            .withMessage(ERROR + " is blank");
    }

    @Test
    void requireLength_string() {
        assertThatNoException().isThrownBy(() -> Require.requireLength(goodStr, goodStr.length()));
        assertThatNullPointerException()
            .isThrownBy(() -> Require.requireLength(nullStr, 1))
            .withMessage("String is null");
        assertThatNullPointerException()
            .isThrownBy(() -> Require.requireLength(nullStr, 1, ERROR))
            .withMessage(ERROR + " is null");
        assertThatIllegalArgumentException()
            .isThrownBy(() -> Require.requireLength(goodStr, goodStr.length() + 1))
            .withMessage("String length (4) is less than minimum: 5 — contents: good");
        assertThatIllegalArgumentException()
            .isThrownBy(() -> Require.requireLength(goodStr, goodStr.length() + 1, ERROR))
            .withMessage(ERROR + " (4) is less than minimum: 5 — contents: good");
    }

    @Test
    void requireLength_array() {
        assertThatNoException().isThrownBy(() -> Require.requireLength(goodArray, goodArray.length));
        assertThatNullPointerException()
            .isThrownBy(() -> Require.requireLength(nullArray, 1))
            .withMessage("Array is null");
        assertThatNullPointerException()
            .isThrownBy(() -> Require.requireLength(nullArray, 1, ERROR))
            .withMessage(ERROR + " is null");
        assertThatIllegalArgumentException()
            .isThrownBy(() -> Require.requireLength(goodArray, goodArray.length + 1))
            .withMessage("Array length (3) is less than minimum: 4 — contents: [a, b, c]");
        assertThatIllegalArgumentException()
            .isThrownBy(() -> Require.requireLength(goodArray, goodArray.length + 1, ERROR))
            .withMessage(ERROR + " (3) is less than minimum: 4 — contents: [a, b, c]");
    }

    @Test
    void requireSize_collection() {
        assertThatNoException().isThrownBy(() -> Require.requireSize(goodList, goodList.size()));
        assertThatNullPointerException()
            .isThrownBy(() -> Require.requireSize(nullList, 1))
            .withMessage("Collection is null");
        assertThatNullPointerException()
            .isThrownBy(() -> Require.requireSize(nullList, 1, ERROR))
            .withMessage(ERROR + " is null");
        assertThatIllegalArgumentException()
            .isThrownBy(() -> Require.requireSize(goodList, goodList.size() + 1))
            .withMessage("Collection size (3) is less than minimum: 4 — contents: [a, b, c]");
        assertThatIllegalArgumentException()
            .isThrownBy(() -> Require.requireSize(goodList, goodList.size() + 1, ERROR))
            .withMessage(ERROR + " (3) is less than minimum: 4 — contents: [a, b, c]");
    }

    @Test
    void requireSize_map() {
        assertThatNoException().isThrownBy(() -> Require.requireSize(goodMap, goodMap.size()));
        assertThatNullPointerException().isThrownBy(() -> Require.requireSize(nullMap, 1)).withMessage("Map is null");
        assertThatNullPointerException()
            .isThrownBy(() -> Require.requireSize(nullMap, 1, ERROR))
            .withMessage(ERROR + " is null");
        assertThatIllegalArgumentException()
            .isThrownBy(() -> Require.requireSize(goodMap, goodMap.size() + 1))
            .withMessage("Map size (2) is less than minimum: 3 — contents: {a=1, b=2}");
        assertThatIllegalArgumentException()
            .isThrownBy(() -> Require.requireSize(goodMap, goodMap.size() + 1, ERROR))
            .withMessage(ERROR + " (2) is less than minimum: 3 — contents: {a=1, b=2}");
    }

    @Test
    void requireMin() {
        assertThatNoException().isThrownBy(() -> Require.requireMin(1, 0));
        assertThatIllegalArgumentException()
            .isThrownBy(() -> Require.requireMin(0, 1))
            .withMessage("Value (0) is less than minimum: 1");
        assertThatIllegalArgumentException()
            .isThrownBy(() -> Require.requireMin(0, 1, ERROR))
            .withMessage(ERROR + " (0) is less than minimum: 1");
    }

    @Test
    void requireMax() {
        assertThatNoException().isThrownBy(() -> Require.requireMax(0, 1));
        assertThatIllegalArgumentException()
            .isThrownBy(() -> Require.requireMax(1, 0))
            .withMessage("Value (1) is greater than maximum: 0");
        assertThatIllegalArgumentException()
            .isThrownBy(() -> Require.requireMax(1, 0, ERROR))
            .withMessage(ERROR + " (1) is greater than maximum: 0");
    }

    @Test
    void requireExists() {
        assertThatNoException().isThrownBy(() -> Require.requireExists(goodPathFile));
        assertThatNoException().isThrownBy(() -> Require.requireExists(goodPathDir));
        assertThatNoException().isThrownBy(() -> Require.requireExists(goodFileDir));
        assertThatNoException().isThrownBy(() -> Require.requireExists(goodFileFile));
        assertThatNoException().isThrownBy(() -> Require.requireExists(GOOD_FILE_STR));
        assertThatNoException().isThrownBy(() -> Require.requireExists(GOOD_DIR_STR));
        assertThatNullPointerException().isThrownBy(() -> Require.requireExists(nullFile)).withMessage("Path is null");
        assertThatNullPointerException().isThrownBy(() -> Require.requireExists(nullFile, ERROR)).withMessage(ERROR + " is null");
        assertThatNullPointerException().isThrownBy(() -> Require.requireExists(nullPath)).withMessage("Path is null");
        assertThatNullPointerException().isThrownBy(() -> Require.requireExists(nullPath, ERROR)).withMessage(ERROR + " is null");
        assertThatNullPointerException().isThrownBy(() -> Require.requireExists(nullStr)).withMessage("Path is null");
        assertThatNullPointerException().isThrownBy(() -> Require.requireExists(nullStr, ERROR)).withMessage(ERROR + " is null");
        assertThatIllegalArgumentException().isThrownBy(() -> Require.requireExists(badPathFile)).withMessageContainingAll("Path does not exist:", BAD);
        assertThatIllegalArgumentException().isThrownBy(() -> Require.requireExists(badPathFile, ERROR)).withMessageContainingAll(ERROR + " does not exist:", BAD);
        assertThatIllegalArgumentException().isThrownBy(() -> Require.requireExists(badFileFile)).withMessageContainingAll("Path does not exist:", BAD);
        assertThatIllegalArgumentException().isThrownBy(() -> Require.requireExists(badFileFile, ERROR)).withMessageContainingAll(ERROR + " does not exist:", BAD);
        assertThatIllegalArgumentException().isThrownBy(() -> Require.requireExists(BAD)).withMessageContainingAll("Path does not exist:", BAD);
        assertThatIllegalArgumentException().isThrownBy(() -> Require.requireExists(BAD, ERROR)).withMessageContainingAll(ERROR + " does not exist:", BAD);
    }

    @Test
    void requireNotExists() {
        assertThatNoException().isThrownBy(() -> Require.requireNotExists(badPathFile));
        assertThatNoException().isThrownBy(() -> Require.requireNotExists(badPathDir));
        assertThatNoException().isThrownBy(() -> Require.requireNotExists(badFileDir));
        assertThatNoException().isThrownBy(() -> Require.requireNotExists(badFileFile));
        assertThatNoException().isThrownBy(() -> Require.requireNotExists(BAD));
        assertThatNullPointerException().isThrownBy(() -> Require.requireNotExists(nullFile)).withMessage("Path is null");
        assertThatNullPointerException().isThrownBy(() -> Require.requireNotExists(nullFile, ERROR)).withMessage(ERROR + " is null");
        assertThatNullPointerException().isThrownBy(() -> Require.requireNotExists(nullPath)).withMessage("Path is null");
        assertThatNullPointerException().isThrownBy(() -> Require.requireNotExists(nullPath, ERROR)).withMessage(ERROR + " is null");
        assertThatNullPointerException().isThrownBy(() -> Require.requireNotExists(nullStr)).withMessage("Path is null");
        assertThatNullPointerException().isThrownBy(() -> Require.requireNotExists(nullStr, ERROR)).withMessage(ERROR + " is null");
        assertThatIllegalArgumentException().isThrownBy(() -> Require.requireNotExists(goodPathFile)).withMessageContainingAll("Path exists:", GOOD_FILE_STR);
        assertThatIllegalArgumentException().isThrownBy(() -> Require.requireNotExists(goodPathFile, ERROR)).withMessageContainingAll(ERROR + " exists:", GOOD_FILE_STR);
        assertThatIllegalArgumentException().isThrownBy(() -> Require.requireNotExists(goodFileFile)).withMessageContainingAll("Path exists:", GOOD_FILE_STR);
        assertThatIllegalArgumentException().isThrownBy(() -> Require.requireNotExists(goodFileFile, ERROR)).withMessageContainingAll(ERROR + " exists:", GOOD_FILE_STR);
        assertThatIllegalArgumentException().isThrownBy(() -> Require.requireNotExists(GOOD_FILE_STR)).withMessageContainingAll("Path exists:", GOOD_FILE_STR);
        assertThatIllegalArgumentException().isThrownBy(() -> Require.requireNotExists(GOOD_FILE_STR, ERROR)).withMessageContainingAll(ERROR + " exists:", GOOD_FILE_STR);
    }

    @Test
    void requireDirectory() {
        assertThatNoException().isThrownBy(() -> Require.requireDirectory(goodPathDir));
        assertThatNoException().isThrownBy(() -> Require.requireDirectory(goodFileDir));
        assertThatNoException().isThrownBy(() -> Require.requireDirectory(GOOD_DIR_STR));
        assertThatNullPointerException().isThrownBy(() -> Require.requireDirectory(nullFile)).withMessage("Directory is null");
        assertThatNullPointerException().isThrownBy(() -> Require.requireDirectory(nullFile, ERROR)).withMessage(ERROR + " is null");
        assertThatNullPointerException().isThrownBy(() -> Require.requireDirectory(nullPath)).withMessage("Directory is null");
        assertThatNullPointerException().isThrownBy(() -> Require.requireDirectory(nullPath, ERROR)).withMessage(ERROR + " is null");
        assertThatNullPointerException().isThrownBy(() -> Require.requireDirectory(nullStr)).withMessage("Directory path is null");
        assertThatNullPointerException().isThrownBy(() -> Require.requireDirectory(nullStr, ERROR)).withMessage(ERROR + " is null");
        assertThatIllegalArgumentException().isThrownBy(() -> Require.requireDirectory(badPathFile)).withMessageContainingAll("Directory does not exist:", BAD);
        assertThatIllegalArgumentException().isThrownBy(() -> Require.requireDirectory(badPathFile, ERROR)).withMessageContainingAll(ERROR + " does not exist:", BAD);
        assertThatIllegalArgumentException().isThrownBy(() -> Require.requireDirectory(goodPathFile)).withMessageContaining("Directory is not a directory:");
        assertThatIllegalArgumentException().isThrownBy(() -> Require.requireDirectory(goodPathFile, ERROR)).withMessageContaining(ERROR + " is not a directory:");
        assertThatIllegalArgumentException().isThrownBy(() -> Require.requireDirectory(goodFileFile)).withMessageContaining("Directory is not a directory:");
        assertThatIllegalArgumentException().isThrownBy(() -> Require.requireDirectory(goodFileFile, ERROR)).withMessageContaining(ERROR + " is not a directory:");
        assertThatIllegalArgumentException().isThrownBy(() -> Require.requireDirectory(BAD)).withMessageContainingAll("Directory does not exist:", BAD);
        assertThatIllegalArgumentException().isThrownBy(() -> Require.requireDirectory(BAD, ERROR)).withMessageContainingAll(ERROR + " does not exist:", BAD);
    }

    @Test
    void requireRegularFile() {
        assertThatNoException().isThrownBy(() -> Require.requireRegularFile(goodPathFile));
        assertThatNoException().isThrownBy(() -> Require.requireRegularFile(goodFileFile));
        assertThatNoException().isThrownBy(() -> Require.requireRegularFile(GOOD_FILE_STR));
        assertThatNullPointerException().isThrownBy(() -> Require.requireRegularFile(nullFile)).withMessage("File is null");
        assertThatNullPointerException().isThrownBy(() -> Require.requireRegularFile(nullFile, ERROR)).withMessage(ERROR + " is null");
        assertThatNullPointerException().isThrownBy(() -> Require.requireRegularFile(nullPath)).withMessage("File is null");
        assertThatNullPointerException().isThrownBy(() -> Require.requireRegularFile(nullPath, ERROR)).withMessage(ERROR + " is null");
        assertThatNullPointerException().isThrownBy(() -> Require.requireRegularFile(nullStr)).withMessage("File path is null");
        assertThatNullPointerException().isThrownBy(() -> Require.requireRegularFile(nullStr, ERROR)).withMessage(ERROR + " is null");
        assertThatIllegalArgumentException().isThrownBy(() -> Require.requireRegularFile(badPathFile)).withMessageContainingAll("File does not exist:", BAD);
        assertThatIllegalArgumentException().isThrownBy(() -> Require.requireRegularFile(badPathFile, ERROR)).withMessageContainingAll(ERROR + " does not exist:", BAD);
        assertThatIllegalArgumentException().isThrownBy(() -> Require.requireRegularFile(goodPathDir)).withMessageContaining("File is not a regular file:");
        assertThatIllegalArgumentException().isThrownBy(() -> Require.requireRegularFile(goodPathDir, ERROR)).withMessageContaining(ERROR + " is not a regular file:");
        assertThatIllegalArgumentException().isThrownBy(() -> Require.requireRegularFile(goodFileDir)).withMessageContaining("File is not a regular file:");
        assertThatIllegalArgumentException().isThrownBy(() -> Require.requireRegularFile(goodFileDir, ERROR)).withMessageContaining(ERROR + " is not a regular file:");
        assertThatIllegalArgumentException().isThrownBy(() -> Require.requireRegularFile(BAD)).withMessageContainingAll("File does not exist:", BAD);
        assertThatIllegalArgumentException().isThrownBy(() -> Require.requireRegularFile(BAD, ERROR)).withMessageContainingAll(ERROR + " does not exist:", BAD);
    }

    @Test
    void requireFuture() {
        assertThatNoException().isThrownBy(() -> Require.requireFuture(zdtFuture));
        assertThatNoException().isThrownBy(() -> Require.requireFuture(odtFuture));
        assertThatNoException().isThrownBy(() -> Require.requireFuture(ldtFuture));
        assertThatNoException().isThrownBy(() -> Require.requireFuture(ldFuture));
        assertThatNoException().isThrownBy(() -> Require.requireFuture(ltFuture));
        assertThatNoException().isThrownBy(() -> Require.requireFuture(zdtFuture, ERROR));
        assertThatNoException().isThrownBy(() -> Require.requireFuture(odtFuture, ERROR));
        assertThatNoException().isThrownBy(() -> Require.requireFuture(ldtFuture, ERROR));
        assertThatNoException().isThrownBy(() -> Require.requireFuture(ldFuture, ERROR));
        assertThatNoException().isThrownBy(() -> Require.requireFuture(ltFuture, ERROR));
        assertThatIllegalArgumentException().isThrownBy(() -> Require.requireFuture(zdtPast)).withMessageContaining("Date Time is not in the future:");
        assertThatIllegalArgumentException().isThrownBy(() -> Require.requireFuture(zdtPast, ERROR)).withMessageContaining(ERROR + " is not in the future:");
        assertThatIllegalArgumentException().isThrownBy(() -> Require.requireFuture(odtPast)).withMessageContaining("Date Time is not in the future:");
        assertThatIllegalArgumentException().isThrownBy(() -> Require.requireFuture(odtPast, ERROR)).withMessageContaining(ERROR + " is not in the future:");
        assertThatIllegalArgumentException().isThrownBy(() -> Require.requireFuture(ldtPast)).withMessageContaining("Date Time is not in the future:");
        assertThatIllegalArgumentException().isThrownBy(() -> Require.requireFuture(ldtPast, ERROR)).withMessageContaining(ERROR + " is not in the future:");
        assertThatIllegalArgumentException().isThrownBy(() -> Require.requireFuture(ldPast)).withMessageContaining("Date is not in the future:");
        assertThatIllegalArgumentException().isThrownBy(() -> Require.requireFuture(ldPast, ERROR)).withMessageContaining(ERROR + " is not in the future:");
        assertThatIllegalArgumentException().isThrownBy(() -> Require.requireFuture(ltPast)).withMessageContaining("Time is not in the future:");
        assertThatIllegalArgumentException().isThrownBy(() -> Require.requireFuture(ltPast, ERROR)).withMessageContaining(ERROR + " is not in the future:");
        assertThatNullPointerException().isThrownBy(() -> Require.requireFuture(zdtNull)).withMessage("Date Time is null");
        assertThatNullPointerException().isThrownBy(() -> Require.requireFuture(zdtNull, ERROR)).withMessage(ERROR + " is null");
        assertThatNullPointerException().isThrownBy(() -> Require.requireFuture(odtNull)).withMessage("Date Time is null");
        assertThatNullPointerException().isThrownBy(() -> Require.requireFuture(odtNull, ERROR)).withMessage(ERROR + " is null");
        assertThatNullPointerException().isThrownBy(() -> Require.requireFuture(ldtNull)).withMessage("Date Time is null");
        assertThatNullPointerException().isThrownBy(() -> Require.requireFuture(ldtNull, ERROR)).withMessage(ERROR + " is null");
        assertThatNullPointerException().isThrownBy(() -> Require.requireFuture(ldNull)).withMessage("Date is null");
        assertThatNullPointerException().isThrownBy(() -> Require.requireFuture(ldNull, ERROR)).withMessage(ERROR + " is null");
        assertThatNullPointerException().isThrownBy(() -> Require.requireFuture(ltNull)).withMessage("Time is null");
        assertThatNullPointerException().isThrownBy(() -> Require.requireFuture(ltNull, ERROR)).withMessage(ERROR + " is null");
    }

    @Test
    void requirePast() {
        assertThatNoException().isThrownBy(() -> Require.requirePast(zdtPast));
        assertThatNoException().isThrownBy(() -> Require.requirePast(odtPast));
        assertThatNoException().isThrownBy(() -> Require.requirePast(ldtPast));
        assertThatNoException().isThrownBy(() -> Require.requirePast(ldPast));
        assertThatNoException().isThrownBy(() -> Require.requirePast(ltPast));
        assertThatNoException().isThrownBy(() -> Require.requirePast(zdtPast, ERROR));
        assertThatNoException().isThrownBy(() -> Require.requirePast(odtPast, ERROR));
        assertThatNoException().isThrownBy(() -> Require.requirePast(ldtPast, ERROR));
        assertThatNoException().isThrownBy(() -> Require.requirePast(ldPast, ERROR));
        assertThatNoException().isThrownBy(() -> Require.requirePast(ltPast, ERROR));
        assertThatIllegalArgumentException().isThrownBy(() -> Require.requirePast(zdtFuture)).withMessageContaining("Date Time is not in the past:");
        assertThatIllegalArgumentException().isThrownBy(() -> Require.requirePast(zdtFuture, ERROR)).withMessageContaining(ERROR + " is not in the past:");
        assertThatIllegalArgumentException().isThrownBy(() -> Require.requirePast(odtFuture)).withMessageContaining("Date Time is not in the past:");
        assertThatIllegalArgumentException().isThrownBy(() -> Require.requirePast(odtFuture, ERROR)).withMessageContaining(ERROR + " is not in the past:");
        assertThatIllegalArgumentException().isThrownBy(() -> Require.requirePast(ldtFuture)).withMessageContaining("Date Time is not in the past:");
        assertThatIllegalArgumentException().isThrownBy(() -> Require.requirePast(ldtFuture, ERROR)).withMessageContaining(ERROR + " is not in the past:");
        assertThatIllegalArgumentException().isThrownBy(() -> Require.requirePast(ldFuture)).withMessageContaining("Date is not in the past:");
        assertThatIllegalArgumentException().isThrownBy(() -> Require.requirePast(ldFuture, ERROR)).withMessageContaining(ERROR + " is not in the past:");
        assertThatIllegalArgumentException().isThrownBy(() -> Require.requirePast(ltFuture)).withMessageContaining("Time is not in the past:");
        assertThatIllegalArgumentException().isThrownBy(() -> Require.requirePast(ltFuture, ERROR)).withMessageContaining(ERROR + " is not in the past:");
        assertThatNullPointerException().isThrownBy(() -> Require.requirePast(zdtNull)).withMessage("Date Time is null");
        assertThatNullPointerException().isThrownBy(() -> Require.requirePast(odtNull)).withMessage("Date Time is null");
        assertThatNullPointerException().isThrownBy(() -> Require.requirePast(ldtNull)).withMessage("Date Time is null");
        assertThatNullPointerException().isThrownBy(() -> Require.requirePast(ldNull)).withMessage("Date is null");
        assertThatNullPointerException().isThrownBy(() -> Require.requirePast(ltNull)).withMessage("Time is null");
    }
}
