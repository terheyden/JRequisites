package com.terheyden.require;

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
class RequireTest {

    private static final String ERROR = "error";

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
    private final int[] goodIntArray = new int[] { 1, 2, 3 };
    private final int[] emptyIntArray = new int[0];
    private final int[] nullIntArray = null;

    @Test
    void require() {
        assertThatNoException().isThrownBy(() -> Require.require(true));
        assertThatIllegalArgumentException().isThrownBy(() -> Require.require(false)).withMessage("Condition is false");
        assertThatIllegalArgumentException().isThrownBy(() -> Require.require(false, ERROR)).withMessage(ERROR);
    }

    @Test
    void requireState() {
        assertThatNoException().isThrownBy(() -> Require.requireState(true));
        assertThatIllegalStateException().isThrownBy(() -> Require.requireState(false)).withMessage("Condition is false");
        assertThatIllegalStateException().isThrownBy(() -> Require.requireState(false, ERROR)).withMessage(ERROR);
    }

    @Test
    void requireNotNull() {
        assertThatNoException().isThrownBy(() -> Require.requireNotNull(goodStr));
        assertThatNullPointerException().isThrownBy(() -> Require.requireNotNull(nullStr)).withMessage("Object is null");
        assertThatNullPointerException().isThrownBy(() -> Require.requireNotNull(nullStr, ERROR)).withMessage(ERROR + " is null");
    }

    @Test
    void requireNotEmpty_string() {
        assertThatNoException().isThrownBy(() -> Require.requireNotEmpty(goodStr));
        assertThatNullPointerException().isThrownBy(() -> Require.requireNotEmpty(nullStr)).withMessage("String is null");
        assertThatNullPointerException().isThrownBy(() -> Require.requireNotEmpty(nullStr, ERROR)).withMessage(ERROR + " is null");
        assertThatIllegalArgumentException().isThrownBy(() -> Require.requireNotEmpty(emptyStr)).withMessage("String is empty");
        assertThatIllegalArgumentException().isThrownBy(() -> Require.requireNotEmpty(emptyStr, ERROR)).withMessage(ERROR + " is empty");
    }

    @Test
    void requireNotEmpty_collection() {
        assertThatNoException().isThrownBy(() -> Require.requireNotEmpty(goodList));
        assertThatNullPointerException().isThrownBy(() -> Require.requireNotEmpty(nullList)).withMessage("Collection is null");
        assertThatNullPointerException().isThrownBy(() -> Require.requireNotEmpty(nullList, ERROR)).withMessage(ERROR + " is null");
        assertThatIllegalArgumentException().isThrownBy(() -> Require.requireNotEmpty(emptyList)).withMessage("Collection is empty");
        assertThatIllegalArgumentException().isThrownBy(() -> Require.requireNotEmpty(emptyList, ERROR)).withMessage(ERROR + " is empty");
    }

    @Test
    void requireNotEmpty_map() {
        assertThatNoException().isThrownBy(() -> Require.requireNotEmpty(goodMap));
        assertThatNullPointerException().isThrownBy(() -> Require.requireNotEmpty(nullMap)).withMessage("Map is null");
        assertThatNullPointerException().isThrownBy(() -> Require.requireNotEmpty(nullMap, ERROR)).withMessage(ERROR + " is null");
        assertThatIllegalArgumentException().isThrownBy(() -> Require.requireNotEmpty(emptyMap)).withMessage("Map is empty");
        assertThatIllegalArgumentException().isThrownBy(() -> Require.requireNotEmpty(emptyMap, ERROR)).withMessage(ERROR + " is empty");
    }

    @Test
    void requireNotEmpty_array() {
        assertThatNoException().isThrownBy(() -> Require.requireNotEmpty(goodArray));
        assertThatNullPointerException().isThrownBy(() -> Require.requireNotEmpty(nullArray)).withMessage("Array is null");
        assertThatNullPointerException().isThrownBy(() -> Require.requireNotEmpty(nullArray, ERROR)).withMessage(ERROR + " is null");
        assertThatIllegalArgumentException().isThrownBy(() -> Require.requireNotEmpty(emptyArray)).withMessage("Array is empty");
        assertThatIllegalArgumentException().isThrownBy(() -> Require.requireNotEmpty(emptyArray, ERROR)).withMessage(ERROR + " is empty");
    }

    @Test
    void requireNotBlank() {
        assertThatNoException().isThrownBy(() -> Require.requireNotBlank(goodStr));
        assertThatNullPointerException().isThrownBy(() -> Require.requireNotBlank(nullStr)).withMessage("String is null");
        assertThatNullPointerException().isThrownBy(() -> Require.requireNotBlank(nullStr, ERROR)).withMessage(ERROR + " is null");
        assertThatIllegalArgumentException().isThrownBy(() -> Require.requireNotBlank(emptyStr)).withMessage("String is empty");
        assertThatIllegalArgumentException().isThrownBy(() -> Require.requireNotBlank(emptyStr, ERROR)).withMessage(ERROR + " is empty");
        assertThatIllegalArgumentException().isThrownBy(() -> Require.requireNotBlank(blankStr)).withMessage("String is blank");
        assertThatIllegalArgumentException().isThrownBy(() -> Require.requireNotBlank(blankStr, ERROR)).withMessage(ERROR + " is blank");
    }

    @Test
    void requireLength_string() {
        assertThatNoException().isThrownBy(() -> Require.requireLength(goodStr, goodStr.length()));
        assertThatNullPointerException().isThrownBy(() -> Require.requireLength(nullStr, 1)).withMessage("String is null");
        assertThatNullPointerException().isThrownBy(() -> Require.requireLength(nullStr, 1, ERROR)).withMessage(ERROR + " is null");
        assertThatIllegalArgumentException().isThrownBy(() -> Require.requireLength(goodStr, goodStr.length() + 1)).withMessage("String length (4) is less than minimum: 5 — contents: good");
        assertThatIllegalArgumentException().isThrownBy(() -> Require.requireLength(goodStr, goodStr.length() + 1, ERROR)).withMessage(ERROR + " (4) is less than minimum: 5 — contents: good");
    }

    @Test
    void requireSize_collection() {
        assertThatNoException().isThrownBy(() -> Require.requireSize(goodList, goodList.size()));
        assertThatNullPointerException().isThrownBy(() -> Require.requireSize(nullList, 1)).withMessage("Collection is null");
        assertThatNullPointerException().isThrownBy(() -> Require.requireSize(nullList, 1, ERROR)).withMessage(ERROR + " is null");
        assertThatIllegalArgumentException().isThrownBy(() -> Require.requireSize(goodList, goodList.size() + 1)).withMessage("Collection size (3) is less than minimum: 4 — contents: [a, b, c]");
        assertThatIllegalArgumentException().isThrownBy(() -> Require.requireSize(goodList, goodList.size() + 1, ERROR)).withMessage(ERROR + " (3) is less than minimum: 4 — contents: [a, b, c]");
    }

    @Test
    void requireSize_map() {
        assertThatNoException().isThrownBy(() -> Require.requireSize(goodMap, goodMap.size()));
        assertThatNullPointerException().isThrownBy(() -> Require.requireSize(nullMap, 1)).withMessage("Map is null");
        assertThatNullPointerException().isThrownBy(() -> Require.requireSize(nullMap, 1, ERROR)).withMessage(ERROR + " is null");
        assertThatIllegalArgumentException().isThrownBy(() -> Require.requireSize(goodMap, goodMap.size() + 1)).withMessage("Map size (2) is less than minimum: 3 — contents: {a=1, b=2}");
        assertThatIllegalArgumentException().isThrownBy(() -> Require.requireSize(goodMap, goodMap.size() + 1, ERROR)).withMessage(ERROR + " (2) is less than minimum: 3 — contents: {a=1, b=2}");
    }
}
