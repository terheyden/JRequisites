package com.terheyden.require;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

import static java.util.Collections.emptyList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;
import static org.assertj.core.api.Assertions.assertThatNullPointerException;

/**
 * FirstTest unit tests.
 */
class FirstTest {

    private final String goodStr = "good";
    private final String badStr = "bad";
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
    void firstNotNull() {
        assertThat(First.firstNotNull(goodStr, badStr)).isEqualTo(goodStr);
        assertThat(First.firstNotNull(nullStr, goodStr)).isEqualTo(goodStr);
        assertThatNullPointerException().isThrownBy(() -> First.firstNotNull(nullStr, nullStr));
    }

    @Test
    void firstNotEmpty_string() {
        assertThat(First.firstNotEmpty(goodStr, badStr)).isEqualTo(goodStr);
        assertThat(First.firstNotEmpty(emptyStr, goodStr)).isEqualTo(goodStr);
        assertThat(First.firstNotEmpty(blankStr, goodStr)).isEqualTo(blankStr);
        assertThat(First.firstNotEmpty(nullStr, goodStr)).isEqualTo(goodStr);
        assertThatIllegalArgumentException().isThrownBy(() -> First.firstNotEmpty(nullStr, nullStr));
    }

    @Test
    void firstNotEmpty_collection() {
        assertThat(First.firstNotEmpty(goodList, emptyList)).isEqualTo(goodList);
        assertThat(First.firstNotEmpty(emptyList, goodList)).isEqualTo(goodList);
        assertThat(First.firstNotEmpty(nullList, goodList)).isEqualTo(goodList);
        assertThatIllegalArgumentException().isThrownBy(() -> First.firstNotEmpty(nullList, nullList));
    }

    @Test
    void firstNotEmpty_map() {
        assertThat(First.firstNotEmpty(goodMap, emptyMap)).isEqualTo(goodMap);
        assertThat(First.firstNotEmpty(emptyMap, goodMap)).isEqualTo(goodMap);
        assertThat(First.firstNotEmpty(nullMap, goodMap)).isEqualTo(goodMap);
        assertThatIllegalArgumentException().isThrownBy(() -> First.firstNotEmpty(nullMap, nullMap));
    }

    @Test
    void firstNotBlank() {
        assertThat(First.firstNotBlank(goodStr, badStr)).isEqualTo(goodStr);
        assertThat(First.firstNotBlank(goodStr, nullStr)).isEqualTo(goodStr);
        assertThat(First.firstNotBlank(emptyStr, goodStr)).isEqualTo(goodStr);
        assertThat(First.firstNotBlank(blankStr, goodStr)).isEqualTo(goodStr);
        assertThat(First.firstNotBlank(nullStr, goodStr)).isEqualTo(goodStr);
        assertThatIllegalArgumentException().isThrownBy(() -> First.firstNotBlank(nullStr, nullStr));
    }
}
