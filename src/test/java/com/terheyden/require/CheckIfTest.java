package com.terheyden.require;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

import static java.util.Collections.emptyList;
import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * CheckIfTest unit tests.
 */
public class CheckIfTest {

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
    private final Map<String, Integer> goodMap = new HashMap<String, Integer>() {{
        put("a", 1);
        put("b", 2);
    }};
    private final Map<String, Integer> emptyMap = new HashMap<>();
    private final Map<String, Integer> nullMap = null;
    private final String[] goodArray = { "a", "b", "c" };
    private final String[] emptyArray = {};
    private final String[] nullArray = null;
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

    @Test
    void testIfNotNull() {
        assertThat(CheckIf.ifNotNull(goodStr)).isNotEmpty().contains(goodStr);
        assertThat(CheckIf.ifNotNull(emptyStr)).isNotEmpty();
        assertThat(CheckIf.ifNotNull(nullStr)).isEmpty();
    }

    @Test
    void testIfNotEmpty_string() {
        assertThat(CheckIf.ifNotEmpty(goodStr)).isNotEmpty().contains(goodStr);
        assertThat(CheckIf.ifNotEmpty(emptyStr)).isEmpty();
        assertThat(CheckIf.ifNotEmpty(nullStr)).isEmpty();
    }

    @Test
    void testIfNotEmpty_collection() {
        assertThat(CheckIf.ifNotEmpty(goodList)).isNotEmpty().contains(goodList);
        assertThat(CheckIf.ifNotEmpty(emptyList)).isEmpty();
        assertThat(CheckIf.ifNotEmpty(nullList)).isEmpty();
    }

    @Test
    void testIfNotEmpty_iterable() {
        assertThat(CheckIf.ifNotEmpty(goodIter)).isNotEmpty().contains(goodIter);
        assertThat(CheckIf.ifNotEmpty(emptyIter)).isEmpty();
        assertThat(CheckIf.ifNotEmpty(nullIter)).isEmpty();
    }

    @Test
    void testIfNotEmpty_map() {
        assertThat(CheckIf.ifNotEmpty(goodMap)).isNotEmpty().contains(goodMap);
        assertThat(CheckIf.ifNotEmpty(emptyMap)).isEmpty();
        assertThat(CheckIf.ifNotEmpty(nullMap)).isEmpty();
    }

    @Test
    void testIfNotEmpty_array() {
        assertThat(CheckIf.ifNotEmpty(goodArray)).isNotEmpty().contains(goodArray);
        assertThat(CheckIf.ifNotEmpty(emptyArray)).isEmpty();
        assertThat(CheckIf.ifNotEmpty(nullArray)).isEmpty();
    }

    @Test
    void testIfNotBlank() {
        assertThat(CheckIf.ifNotBlank(goodStr)).isNotEmpty().contains(goodStr);
        assertThat(CheckIf.ifNotBlank(emptyStr)).isEmpty();
        assertThat(CheckIf.ifNotBlank(nullStr)).isEmpty();
    }

    @Test
    void testIfLength_string() {
        assertThat(CheckIf.ifLength(goodStr, 4)).isNotEmpty().contains(goodStr);
        assertThat(CheckIf.ifLength(goodStr, 5)).isEmpty();
        assertThat(CheckIf.ifLength(emptyStr, 0)).isNotEmpty();
        assertThat(CheckIf.ifLength(nullStr, 0)).isEmpty();
    }

    @Test
    void testIfLength_array() {
        assertThat(CheckIf.ifLength(goodArray, 3)).isNotEmpty().contains(goodArray);
        assertThat(CheckIf.ifLength(goodArray, 4)).isEmpty();
        assertThat(CheckIf.ifLength(emptyArray, 0)).isNotEmpty();
        assertThat(CheckIf.ifLength(nullArray, 0)).isEmpty();
    }

    @Test
    void testIfLengthGreaterThan_string() {
        assertThat(CheckIf.ifLengthGreaterThan(goodStr, 3)).isNotEmpty().contains(goodStr);
        assertThat(CheckIf.ifLengthGreaterThan(goodStr, 4)).isEmpty();
        assertThat(CheckIf.ifLengthGreaterThan(emptyStr, 0)).isEmpty();
        assertThat(CheckIf.ifLengthGreaterThan(nullStr, 0)).isEmpty();
    }

    @Test
    void testIfLengthGreaterThan_array() {
        assertThat(CheckIf.ifLengthGreaterThan(goodArray, 2)).isNotEmpty().contains(goodArray);
        assertThat(CheckIf.ifLengthGreaterThan(goodArray, 3)).isEmpty();
        assertThat(CheckIf.ifLengthGreaterThan(emptyArray, 0)).isEmpty();
        assertThat(CheckIf.ifLengthGreaterThan(nullArray, 0)).isEmpty();
    }

    @Test
    void testIfLengthGreaterOrEqualTo_string() {
        assertThat(CheckIf.ifLengthGreaterOrEqualTo(goodStr, 4)).isNotEmpty().contains(goodStr);
        assertThat(CheckIf.ifLengthGreaterOrEqualTo(goodStr, 5)).isEmpty();
        assertThat(CheckIf.ifLengthGreaterOrEqualTo(emptyStr, 0)).isNotEmpty();
        assertThat(CheckIf.ifLengthGreaterOrEqualTo(nullStr, 0)).isEmpty();
    }

    @Test
    void testIfLengthGreaterOrEqualTo_array() {
        assertThat(CheckIf.ifLengthGreaterOrEqualTo(goodArray, 3)).isNotEmpty().contains(goodArray);
        assertThat(CheckIf.ifLengthGreaterOrEqualTo(goodArray, 4)).isEmpty();
        assertThat(CheckIf.ifLengthGreaterOrEqualTo(emptyArray, 0)).isNotEmpty();
        assertThat(CheckIf.ifLengthGreaterOrEqualTo(nullArray, 0)).isEmpty();
    }

    @Test
    void testIfLengthLessThan_string() {
        assertThat(CheckIf.ifLengthLessThan(goodStr, 5)).isNotEmpty().contains(goodStr);
        assertThat(CheckIf.ifLengthLessThan(goodStr, 4)).isEmpty();
        assertThat(CheckIf.ifLengthLessThan(emptyStr, 1)).isNotEmpty().contains(emptyStr);
        assertThat(CheckIf.ifLengthLessThan(nullStr, 1)).isEmpty();
    }

    @Test
    void testIfLengthLessThan_array() {
        assertThat(CheckIf.ifLengthLessThan(goodArray, 4)).isNotEmpty().contains(goodArray);
        assertThat(CheckIf.ifLengthLessThan(goodArray, 3)).isEmpty();
        assertThat(CheckIf.ifLengthLessThan(emptyArray, 1)).isNotEmpty().contains(emptyArray);
        assertThat(CheckIf.ifLengthLessThan(nullArray, 1)).isEmpty();
    }

    @Test
    void testIfLengthLessOrEqualTo_string() {
        assertThat(CheckIf.ifLengthLessOrEqualTo(goodStr, 4)).isNotEmpty().contains(goodStr);
        assertThat(CheckIf.ifLengthLessOrEqualTo(goodStr, 3)).isEmpty();
        assertThat(CheckIf.ifLengthLessOrEqualTo(emptyStr, 0)).isNotEmpty();
        assertThat(CheckIf.ifLengthLessOrEqualTo(nullStr, 0)).isEmpty();
    }

    @Test
    void testIfLengthLessOrEqualTo_array() {
        assertThat(CheckIf.ifLengthLessOrEqualTo(goodArray, 3)).isNotEmpty().contains(goodArray);
        assertThat(CheckIf.ifLengthLessOrEqualTo(goodArray, 2)).isEmpty();
        assertThat(CheckIf.ifLengthLessOrEqualTo(emptyArray, 0)).isNotEmpty();
        assertThat(CheckIf.ifLengthLessOrEqualTo(nullArray, 0)).isEmpty();
    }

    @Test
    void testIfSize_collection() {
        assertThat(CheckIf.ifSize(goodList, 3)).isNotEmpty().contains(goodList);
        assertThat(CheckIf.ifSize(goodList, 4)).isEmpty();
        assertThat(CheckIf.ifSize(emptyList, 0)).isNotEmpty();
        assertThat(CheckIf.ifSize(nullList, 0)).isEmpty();
    }

    @Test
    void testIfSize_iterable() {
        assertThat(CheckIf.ifSize(goodIter, 3)).isNotEmpty().contains(goodIter);
        assertThat(CheckIf.ifSize(goodIter, 4)).isEmpty();
        assertThat(CheckIf.ifSize(emptyIter, 0)).isNotEmpty();
        assertThat(CheckIf.ifSize(nullIter, 0)).isEmpty();
    }

    @Test
    void testIfSize_map() {
        assertThat(CheckIf.ifSize(goodMap, 2)).isNotEmpty().contains(goodMap);
        assertThat(CheckIf.ifSize(goodMap, 3)).isEmpty();
        assertThat(CheckIf.ifSize(emptyMap, 0)).isNotEmpty().contains(emptyMap);
        assertThat(CheckIf.ifSize(nullMap, 0)).isEmpty();
    }

    @Test
    void testIfSizeGreaterThan_collection() {
        assertThat(CheckIf.ifSizeGreaterThan(goodList, 2)).isNotEmpty().contains(goodList);
        assertThat(CheckIf.ifSizeGreaterThan(goodList, 3)).isEmpty();
        assertThat(CheckIf.ifSizeGreaterThan(emptyList, 0)).isEmpty();
        assertThat(CheckIf.ifSizeGreaterThan(nullList, 0)).isEmpty();
    }

    @Test
    void testIfSizeGreaterThan_iterable() {
        assertThat(CheckIf.ifSizeGreaterThan(goodIter, 2)).isNotEmpty().contains(goodIter);
        assertThat(CheckIf.ifSizeGreaterThan(goodIter, 3)).isEmpty();
        assertThat(CheckIf.ifSizeGreaterThan(emptyIter, 0)).isEmpty();
        assertThat(CheckIf.ifSizeGreaterThan(nullIter, 0)).isEmpty();
    }

    @Test
    void testIfSizeGreaterThan_map() {
        assertThat(CheckIf.ifSizeGreaterThan(goodMap, 1)).isNotEmpty().contains(goodMap);
        assertThat(CheckIf.ifSizeGreaterThan(goodMap, 2)).isEmpty();
        assertThat(CheckIf.ifSizeGreaterThan(emptyMap, 0)).isEmpty();
        assertThat(CheckIf.ifSizeGreaterThan(nullMap, 0)).isEmpty();
    }

    @Test
    void testIfSizeGreaterOrEqualTo_collection() {
        assertThat(CheckIf.ifSizeGreaterOrEqualTo(goodList, 3)).isNotEmpty().contains(goodList);
        assertThat(CheckIf.ifSizeGreaterOrEqualTo(goodList, 4)).isEmpty();
        assertThat(CheckIf.ifSizeGreaterOrEqualTo(emptyList, 0)).isNotEmpty();
        assertThat(CheckIf.ifSizeGreaterOrEqualTo(nullList, 0)).isEmpty();
    }

    @Test
    void testIfSizeGreaterOrEqualTo_iterable() {
        assertThat(CheckIf.ifSizeGreaterOrEqualTo(goodIter, 3)).isNotEmpty().contains(goodIter);
        assertThat(CheckIf.ifSizeGreaterOrEqualTo(goodIter, 4)).isEmpty();
        assertThat(CheckIf.ifSizeGreaterOrEqualTo(emptyIter, 0)).isNotEmpty();
        assertThat(CheckIf.ifSizeGreaterOrEqualTo(nullIter, 0)).isEmpty();
    }

    @Test
    void testIfSizeGreaterOrEqualTo_map() {
        assertThat(CheckIf.ifSizeGreaterOrEqualTo(goodMap, 2)).isNotEmpty().contains(goodMap);
        assertThat(CheckIf.ifSizeGreaterOrEqualTo(goodMap, 3)).isEmpty();
        assertThat(CheckIf.ifSizeGreaterOrEqualTo(emptyMap, 0)).isNotEmpty();
        assertThat(CheckIf.ifSizeGreaterOrEqualTo(nullMap, 0)).isEmpty();
    }

    @Test
    void testIfSizeLessThan_collection() {
        assertThat(CheckIf.ifSizeLessThan(goodList, 4)).isNotEmpty().contains(goodList);
        assertThat(CheckIf.ifSizeLessThan(goodList, 3)).isEmpty();
        assertThat(CheckIf.ifSizeLessThan(emptyList, 1)).isNotEmpty();
        assertThat(CheckIf.ifSizeLessThan(nullList, 1)).isEmpty();
    }

    @Test
    void testIfSizeLessThan_iterable() {
        assertThat(CheckIf.ifSizeLessThan(goodIter, 4)).isNotEmpty().contains(goodIter);
        assertThat(CheckIf.ifSizeLessThan(goodIter, 3)).isEmpty();
        assertThat(CheckIf.ifSizeLessThan(emptyIter, 1)).isNotEmpty();
        assertThat(CheckIf.ifSizeLessThan(nullIter, 1)).isEmpty();
    }

    @Test
    void testIfSizeLessThan_map() {
        assertThat(CheckIf.ifSizeLessThan(goodMap, 3)).isNotEmpty().contains(goodMap);
        assertThat(CheckIf.ifSizeLessThan(goodMap, 2)).isEmpty();
        assertThat(CheckIf.ifSizeLessThan(emptyMap, 1)).isNotEmpty();
        assertThat(CheckIf.ifSizeLessThan(nullMap, 1)).isEmpty();
    }

    @Test
    void testIfSizeLessOrEqualTo_collection() {
        assertThat(CheckIf.ifSizeLessOrEqualTo(goodList, 3)).isNotEmpty().contains(goodList);
        assertThat(CheckIf.ifSizeLessOrEqualTo(goodList, 2)).isEmpty();
        assertThat(CheckIf.ifSizeLessOrEqualTo(emptyList, 0)).isNotEmpty();
        assertThat(CheckIf.ifSizeLessOrEqualTo(nullList, 0)).isEmpty();
    }

    @Test
    void testIfSizeLessOrEqualTo_iterable() {
        assertThat(CheckIf.ifSizeLessOrEqualTo(goodIter, 3)).isNotEmpty().contains(goodIter);
        assertThat(CheckIf.ifSizeLessOrEqualTo(goodIter, 2)).isEmpty();
        assertThat(CheckIf.ifSizeLessOrEqualTo(emptyIter, 0)).isNotEmpty();
        assertThat(CheckIf.ifSizeLessOrEqualTo(nullIter, 0)).isEmpty();
    }

    @Test
    void testIfSizeLessOrEqualTo_map() {
        assertThat(CheckIf.ifSizeLessOrEqualTo(goodMap, 2)).isNotEmpty().contains(goodMap);
        assertThat(CheckIf.ifSizeLessOrEqualTo(goodMap, 1)).isEmpty();
        assertThat(CheckIf.ifSizeLessOrEqualTo(emptyMap, 0)).isNotEmpty();
        assertThat(CheckIf.ifSizeLessOrEqualTo(nullMap, 0)).isEmpty();
    }

    @Test
    void testIfDurationGreaterThan() {
        assertThat(CheckIf.ifDurationGreaterThan(Duration.ofSeconds(1), Duration.ofSeconds(2))).isEmpty();
        assertThat(CheckIf.ifDurationGreaterThan(Duration.ofSeconds(2), Duration.ofSeconds(1))).isNotEmpty();
        assertThat(CheckIf.ifDurationGreaterThan(Duration.ofSeconds(1), Duration.ofSeconds(1))).isEmpty();
    }

    @Test
    void testIfDurationGreaterThanNanos() {
        assertThat(CheckIf.ifDurationGreaterThanNanos(Duration.ofNanos(1), 2)).isEmpty();
        assertThat(CheckIf.ifDurationGreaterThanNanos(Duration.ofNanos(2), 1)).isNotEmpty();
        assertThat(CheckIf.ifDurationGreaterThanNanos(Duration.ofNanos(1), 1)).isEmpty();
    }

    @Test
    void testIfDurationGreaterThanMillis() {
        assertThat(CheckIf.ifDurationGreaterThanMillis(Duration.ofMillis(1), 2)).isEmpty();
        assertThat(CheckIf.ifDurationGreaterThanMillis(Duration.ofMillis(2), 1)).isNotEmpty();
        assertThat(CheckIf.ifDurationGreaterThanMillis(Duration.ofMillis(1), 1)).isEmpty();
    }

    @Test
    void testIfDurationGreaterThanSecs() {
        assertThat(CheckIf.ifDurationGreaterThanSecs(Duration.ofSeconds(1), 2)).isEmpty();
        assertThat(CheckIf.ifDurationGreaterThanSecs(Duration.ofSeconds(2), 1)).isNotEmpty();
        assertThat(CheckIf.ifDurationGreaterThanSecs(Duration.ofSeconds(1), 1)).isEmpty();
    }

    @Test
    void testIfDurationGreaterThanMins() {
        assertThat(CheckIf.ifDurationGreaterThanMins(Duration.ofMinutes(1), 2)).isEmpty();
        assertThat(CheckIf.ifDurationGreaterThanMins(Duration.ofMinutes(2), 1)).isNotEmpty();
        assertThat(CheckIf.ifDurationGreaterThanMins(Duration.ofMinutes(1), 1)).isEmpty();
    }

    @Test
    void testIfDurationGreaterThanHours() {
        assertThat(CheckIf.ifDurationGreaterThanHours(Duration.ofHours(1), 2)).isEmpty();
        assertThat(CheckIf.ifDurationGreaterThanHours(Duration.ofHours(2), 1)).isNotEmpty();
        assertThat(CheckIf.ifDurationGreaterThanHours(Duration.ofHours(1), 1)).isEmpty();
    }

    @Test
    void testIfDurationGreaterThanDays() {
        assertThat(CheckIf.ifDurationGreaterThanDays(Duration.ofDays(1), 2)).isEmpty();
        assertThat(CheckIf.ifDurationGreaterThanDays(Duration.ofDays(2), 1)).isNotEmpty();
        assertThat(CheckIf.ifDurationGreaterThanDays(Duration.ofDays(1), 1)).isEmpty();
    }

    @Test
    void testIfDurationGreaterOrEqualTo() {
        assertThat(CheckIf.ifDurationGreaterOrEqualTo(Duration.ofSeconds(1), Duration.ofSeconds(2))).isEmpty();
        assertThat(CheckIf.ifDurationGreaterOrEqualTo(Duration.ofSeconds(2), Duration.ofSeconds(1))).isNotEmpty();
        assertThat(CheckIf.ifDurationGreaterOrEqualTo(Duration.ofSeconds(1), Duration.ofSeconds(1))).isNotEmpty();
    }

  @Test
    void testIfDurationGreaterOrEqualToNanos() {
        assertThat(CheckIf.ifDurationGreaterOrEqualToNanos(Duration.ofNanos(1), 2)).isEmpty();
        assertThat(CheckIf.ifDurationGreaterOrEqualToNanos(Duration.ofNanos(2), 1)).isNotEmpty();
        assertThat(CheckIf.ifDurationGreaterOrEqualToNanos(Duration.ofNanos(1), 1)).isNotEmpty();
    }

    @Test
    void testIfDurationGreaterOrEqualToMillis() {
        assertThat(CheckIf.ifDurationGreaterOrEqualToMillis(Duration.ofMillis(1), 2)).isEmpty();
        assertThat(CheckIf.ifDurationGreaterOrEqualToMillis(Duration.ofMillis(2), 1)).isNotEmpty();
        assertThat(CheckIf.ifDurationGreaterOrEqualToMillis(Duration.ofMillis(1), 1)).isNotEmpty();
    }

    @Test
    void testIfDurationGreaterOrEqualToSecs() {
        assertThat(CheckIf.ifDurationGreaterOrEqualToSecs(Duration.ofSeconds(1), 2)).isEmpty();
        assertThat(CheckIf.ifDurationGreaterOrEqualToSecs(Duration.ofSeconds(2), 1)).isNotEmpty();
        assertThat(CheckIf.ifDurationGreaterOrEqualToSecs(Duration.ofSeconds(1), 1)).isNotEmpty();
    }

    @Test
    void testIfDurationGreaterOrEqualToMins() {
        assertThat(CheckIf.ifDurationGreaterOrEqualToMins(Duration.ofMinutes(1), 2)).isEmpty();
        assertThat(CheckIf.ifDurationGreaterOrEqualToMins(Duration.ofMinutes(2), 1)).isNotEmpty();
        assertThat(CheckIf.ifDurationGreaterOrEqualToMins(Duration.ofMinutes(1), 1)).isNotEmpty();
    }

    @Test
    void testIfDurationGreaterOrEqualToHours() {
        assertThat(CheckIf.ifDurationGreaterOrEqualToHours(Duration.ofHours(1), 2)).isEmpty();
        assertThat(CheckIf.ifDurationGreaterOrEqualToHours(Duration.ofHours(2), 1)).isNotEmpty();
        assertThat(CheckIf.ifDurationGreaterOrEqualToHours(Duration.ofHours(1), 1)).isNotEmpty();
    }


   @Test
    void testIfDurationGreaterOrEqualToDays() {
        assertThat(CheckIf.ifDurationGreaterOrEqualToDays(Duration.ofDays(1), 2)).isEmpty();
        assertThat(CheckIf.ifDurationGreaterOrEqualToDays(Duration.ofDays(2), 1)).isNotEmpty();
        assertThat(CheckIf.ifDurationGreaterOrEqualToDays(Duration.ofDays(1), 1)).isNotEmpty();
    }

    @Test
    void testIfDurationLessThan() {
        assertThat(CheckIf.ifDurationLessThan(Duration.ofSeconds(1), Duration.ofSeconds(2))).isNotEmpty();
        assertThat(CheckIf.ifDurationLessThan(Duration.ofSeconds(2), Duration.ofSeconds(1))).isEmpty();
        assertThat(CheckIf.ifDurationLessThan(Duration.ofSeconds(1), Duration.ofSeconds(1))).isEmpty();
    }

    @Test
    void testIfDurationLessThanNanos() {
        assertThat(CheckIf.ifDurationLessThanNanos(Duration.ofNanos(1), 2)).isNotEmpty();
        assertThat(CheckIf.ifDurationLessThanNanos(Duration.ofNanos(2), 1)).isEmpty();
        assertThat(CheckIf.ifDurationLessThanNanos(Duration.ofNanos(1), 1)).isEmpty();
    }

    @Test
    void testIfDurationLessThanMillis() {
        assertThat(CheckIf.ifDurationLessThanMillis(Duration.ofMillis(1), 2)).isNotEmpty();
        assertThat(CheckIf.ifDurationLessThanMillis(Duration.ofMillis(2), 1)).isEmpty();
        assertThat(CheckIf.ifDurationLessThanMillis(Duration.ofMillis(1), 1)).isEmpty();
    }

    @Test
    void testIfDurationLessThanSecs() {
        assertThat(CheckIf.ifDurationLessThanSecs(Duration.ofSeconds(1), 2)).isNotEmpty();
        assertThat(CheckIf.ifDurationLessThanSecs(Duration.ofSeconds(2), 1)).isEmpty();
        assertThat(CheckIf.ifDurationLessThanSecs(Duration.ofSeconds(1), 1)).isEmpty();
    }

    @Test
    void testIfDurationLessThanMins() {
        assertThat(CheckIf.ifDurationLessThanMins(Duration.ofMinutes(1), 2)).isNotEmpty();
        assertThat(CheckIf.ifDurationLessThanMins(Duration.ofMinutes(2), 1)).isEmpty();
        assertThat(CheckIf.ifDurationLessThanMins(Duration.ofMinutes(1), 1)).isEmpty();
    }

    @Test
    void testIfDurationLessThanHours() {
        assertThat(CheckIf.ifDurationLessThanHours(Duration.ofHours(1), 2)).isNotEmpty();
        assertThat(CheckIf.ifDurationLessThanHours(Duration.ofHours(2), 1)).isEmpty();
        assertThat(CheckIf.ifDurationLessThanHours(Duration.ofHours(1), 1)).isEmpty();
    }

    @Test
    void testIfDurationLessThanDays() {
        assertThat(CheckIf.ifDurationLessThanDays(Duration.ofDays(1), 2)).isNotEmpty();
        assertThat(CheckIf.ifDurationLessThanDays(Duration.ofDays(2), 1)).isEmpty();
        assertThat(CheckIf.ifDurationLessThanDays(Duration.ofDays(1), 1)).isEmpty();
    }

    @Test
    void testIfDurationLessOrEqualTo() {
        assertThat(CheckIf.ifDurationLessOrEqualTo(Duration.ofSeconds(1), Duration.ofSeconds(2))).isNotEmpty();
        assertThat(CheckIf.ifDurationLessOrEqualTo(Duration.ofSeconds(2), Duration.ofSeconds(1))).isEmpty();
        assertThat(CheckIf.ifDurationLessOrEqualTo(Duration.ofSeconds(1), Duration.ofSeconds(1))).isNotEmpty();
    }

    @Test
    void testIfDurationLessOrEqualToNanos() {
        assertThat(CheckIf.ifDurationLessOrEqualToNanos(Duration.ofNanos(1), 2)).isNotEmpty();
        assertThat(CheckIf.ifDurationLessOrEqualToNanos(Duration.ofNanos(2), 1)).isEmpty();
        assertThat(CheckIf.ifDurationLessOrEqualToNanos(Duration.ofNanos(1), 1)).isNotEmpty();
    }

    @Test
    void testIfDurationLessOrEqualToMillis() {
        assertThat(CheckIf.ifDurationLessOrEqualToMillis(Duration.ofMillis(1), 2)).isNotEmpty();
        assertThat(CheckIf.ifDurationLessOrEqualToMillis(Duration.ofMillis(2), 1)).isEmpty();
        assertThat(CheckIf.ifDurationLessOrEqualToMillis(Duration.ofMillis(1), 1)).isNotEmpty();
    }

    @Test
    void testIfDurationLessOrEqualToSecs() {
        assertThat(CheckIf.ifDurationLessOrEqualToSecs(Duration.ofSeconds(1), 2)).isNotEmpty();
        assertThat(CheckIf.ifDurationLessOrEqualToSecs(Duration.ofSeconds(2), 1)).isEmpty();
        assertThat(CheckIf.ifDurationLessOrEqualToSecs(Duration.ofSeconds(1), 1)).isNotEmpty();
    }

    @Test
    void testIfDurationLessOrEqualToMins() {
        assertThat(CheckIf.ifDurationLessOrEqualToMins(Duration.ofMinutes(1), 2)).isNotEmpty();
        assertThat(CheckIf.ifDurationLessOrEqualToMins(Duration.ofMinutes(2), 1)).isEmpty();
        assertThat(CheckIf.ifDurationLessOrEqualToMins(Duration.ofMinutes(1), 1)).isNotEmpty();
    }

    @Test
    void testIfDurationLessOrEqualToHours() {
        assertThat(CheckIf.ifDurationLessOrEqualToHours(Duration.ofHours(1), 2)).isNotEmpty();
        assertThat(CheckIf.ifDurationLessOrEqualToHours(Duration.ofHours(2), 1)).isEmpty();
        assertThat(CheckIf.ifDurationLessOrEqualToHours(Duration.ofHours(1), 1)).isNotEmpty();
    }

    @Test
    void testIfDurationLessOrEqualToDays() {
        assertThat(CheckIf.ifDurationLessOrEqualToDays(Duration.ofDays(1), 2)).isNotEmpty();
        assertThat(CheckIf.ifDurationLessOrEqualToDays(Duration.ofDays(2), 1)).isEmpty();
        assertThat(CheckIf.ifDurationLessOrEqualToDays(Duration.ofDays(1), 1)).isNotEmpty();
    }

    @Test
    void testIfPathExists_path() {
        assertThat(CheckIf.ifPathExists(goodPathFile)).isNotEmpty();
        assertThat(CheckIf.ifPathExists(goodPathDir)).isNotEmpty();
        assertThat(CheckIf.ifPathExists(badPathDir)).isEmpty();
        assertThat(CheckIf.ifPathExists(nullPath)).isEmpty();
    }

    @Test
    void testIfPathExists_file() {
        assertThat(CheckIf.ifPathExists(goodFileFile)).isNotEmpty();
        assertThat(CheckIf.ifPathExists(goodFileDir)).isNotEmpty();
        assertThat(CheckIf.ifPathExists(badFileFile)).isEmpty();
        assertThat(CheckIf.ifPathExists(nullFile)).isEmpty();
    }

    @Test
    void testIfPathExists_string() {
        assertThat(CheckIf.ifPathExists(GOOD_FILE_STR)).isNotEmpty();
        assertThat(CheckIf.ifPathExists(GOOD_DIR_STR)).isNotEmpty();
        assertThat(CheckIf.ifPathExists(BAD)).isEmpty();
        assertThat(CheckIf.ifPathExists(nullStr)).isEmpty();
    }

    @Test
    void testIfRegularFile_path() {
        assertThat(CheckIf.ifRegularFile(goodPathFile)).isNotEmpty();
        assertThat(CheckIf.ifRegularFile(goodPathDir)).isEmpty();
        assertThat(CheckIf.ifRegularFile(badPathDir)).isEmpty();
        assertThat(CheckIf.ifRegularFile(nullPath)).isEmpty();
    }

    @Test
    void testIfRegularFile_file() {
        assertThat(CheckIf.ifRegularFile(goodFileFile)).isNotEmpty();
        assertThat(CheckIf.ifRegularFile(goodFileDir)).isEmpty();
        assertThat(CheckIf.ifRegularFile(badFileFile)).isEmpty();
        assertThat(CheckIf.ifRegularFile(nullFile)).isEmpty();
    }

    @Test
    void testIfRegularFile_string() {
        assertThat(CheckIf.ifRegularFile(GOOD_FILE_STR)).isNotEmpty();
        assertThat(CheckIf.ifRegularFile(GOOD_DIR_STR)).isEmpty();
        assertThat(CheckIf.ifRegularFile(BAD)).isEmpty();
        assertThat(CheckIf.ifRegularFile(nullStr)).isEmpty();
    }

    @Test
    void testIfDirectory_path() {
        assertThat(CheckIf.ifDirectory(goodPathFile)).isEmpty();
        assertThat(CheckIf.ifDirectory(goodPathDir)).isNotEmpty();
        assertThat(CheckIf.ifDirectory(badPathDir)).isEmpty();
        assertThat(CheckIf.ifDirectory(nullPath)).isEmpty();
    }

    @Test
    void testIfDirectory_file() {
        assertThat(CheckIf.ifDirectory(goodFileFile)).isEmpty();
        assertThat(CheckIf.ifDirectory(goodFileDir)).isNotEmpty();
        assertThat(CheckIf.ifDirectory(badFileFile)).isEmpty();
        assertThat(CheckIf.ifDirectory(nullFile)).isEmpty();
    }

    @Test
    void testIfDirectory_string() {
        assertThat(CheckIf.ifDirectory(GOOD_FILE_STR)).isEmpty();
        assertThat(CheckIf.ifDirectory(GOOD_DIR_STR)).isNotEmpty();
        assertThat(CheckIf.ifDirectory(BAD)).isEmpty();
        assertThat(CheckIf.ifDirectory(nullStr)).isEmpty();
    }
}
