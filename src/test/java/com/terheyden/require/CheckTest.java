package com.terheyden.require;

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
    void checkNotBlank() {
        assertThat(Check.checkNotBlank(goodStr)).isNotEmpty();
        assertThat(Check.checkNotBlank(nullStr)).isEmpty();
        assertThat(Check.checkNotBlank(emptyStr)).isEmpty();
        assertThat(Check.checkNotBlank(blankStr)).isEmpty();
    }

    @Test
    void checkLength_string() {
        assertThat(Check.checkLength(goodStr, goodStr.length())).isNotEmpty();
        assertThat(Check.checkLength(nullStr, 1)).isEmpty();
        assertThat(Check.checkLength(goodStr, goodStr.length() + 1)).isEmpty();
    }

    @Test
    void checkSize_collection() {
        assertThat(Check.checkSize(goodList, goodList.size())).isNotEmpty();
        assertThat(Check.checkSize(nullList, 1)).isEmpty();
        assertThat(Check.checkSize(goodList, goodList.size() + 1)).isEmpty();
    }

    @Test
    void checkSize_map() {
        assertThat(Check.checkSize(goodMap, goodMap.size())).isNotEmpty();
        assertThat(Check.checkSize(nullMap, 1)).isEmpty();
        assertThat(Check.checkSize(goodMap, goodMap.size() + 1)).isEmpty();
    }
}
