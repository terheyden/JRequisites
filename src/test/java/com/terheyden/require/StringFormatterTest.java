package com.terheyden.require;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * StringFormatterTest unit tests.
 */
class StringFormatterTest {

    @Test
    void lonePlaceholder_goodValue_goodResult() {
        testBothFormats1("{}", "hello", "hello");
    }

    @Test
    void oneMiddlePlaceholder_goodValue_goodResult() {
        testBothFormats1("hello {} world", "there", "hello there world");
    }

    @Test
    void oneEndPlaceholder_goodValue_goodResult() {
        testBothFormats1("hello {}", "world", "hello world");
    }

    @Test
    void oneStartPlaceholder_goodValue_goodResult() {
        testBothFormats1("{} world", "hello", "hello world");
    }

    @Test
    void oneLonePlaceholder_shortValue_goodResult() {
        testBothFormats1("{}", "x", "x");
    }

    @Test
    void oneLonePlaceholder_nestedValue_goodResult() {
        testBothFormats1("{}", "{}", "{}");
    }

    @Test
    void twoLonePlaceholders_goodValues_goodResult() {
        testBothFormats2("{}{}", "hello", "world", "helloworld");
    }

    @Test
    void twoLonePlaceholders_shortValues_goodResult() {
        testBothFormats2("{}{}", "x", "y", "xy");
    }

    @Test
    void twoLonePlaceholders_nestedValues_goodResult() {
        testBothFormats2("{}{}", "{}", "{}", "{}{}");
    }

    @Test
    void twoLonePlaceholders_nestedValues2_goodResult() {
        testBothFormats2("{}{}", "{}", "x", "{}x");
    }

    @Test
    void twoEmbedded_goodVals_goodResult() {
        testBothFormats2("a {} c {} e", "b", "d", "a b c d e");
    }

    private static void testBothFormats1(String logFormat, Object arg1, String expected) {
        // Test the log placeholder version:
        assertThat(StringFormatter.format(logFormat, arg1)).isEqualTo(expected);
        // Test the String.format() placeholder version:
        String stringFormat = logFormat.replace("{}", "%s");
        assertThat(StringFormatter.format(stringFormat, arg1)).isEqualTo(expected);
    }

    private static void testBothFormats2(String logFormat, Object arg1, Object arg2, String expected) {
        // Test the log placeholder version:
        assertThat(StringFormatter.format(logFormat, arg1, arg2)).isEqualTo(expected);
        // Test the String.format() placeholder version:
        String stringFormat = logFormat.replace("{}", "%s");
        assertThat(StringFormatter.format(stringFormat, arg1, arg2)).isEqualTo(expected);
    }
}
