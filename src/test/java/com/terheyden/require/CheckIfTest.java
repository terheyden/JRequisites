package com.terheyden.require;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

import static java.util.Collections.emptyList;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * CheckIfTest unit tests.
 */
public class CheckIfTest {

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
    void testIfLength_str() {
        assertThat(CheckIf.ifLength(4, goodStr)).isNotEmpty().contains(goodStr);
        assertThat(CheckIf.ifLength(5, goodStr)).isEmpty();
        assertThat(CheckIf.ifLength(0, emptyStr)).isNotEmpty();
        assertThat(CheckIf.ifLength(0, nullStr)).isEmpty();
    }
}
