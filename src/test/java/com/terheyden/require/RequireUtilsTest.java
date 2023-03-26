package com.terheyden.require;

import java.time.Duration;

import org.junit.jupiter.api.Test;

import static java.lang.System.out;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNoException;

/**
 * RequireUtilsTest unit tests.
 */
class RequireUtilsTest {

    @Test
    void testDurationToHumanReadableString() {

        Duration maxNanos = Duration.ofNanos(Long.MAX_VALUE);
        out.println("Max duration before toNanos() throws: " + RequireUtils.durationToHumanReadableString(maxNanos));

        // Test that our graceful exception handling is working.
        Duration tooManyNanos = Duration.ofDays(111_111);
        assertThatNoException().isThrownBy(() -> RequireUtils.durationToHumanReadableString(tooManyNanos));

        Duration d1d20h55m44s500ms = Duration
            .ofDays(1)
            .plusHours(20)
            .plusMinutes(55)
            .plusSeconds(44)
            .plusMillis(500);

        Duration d1s500ms = Duration.ofSeconds(1).plusMillis(500);
        assertThat(RequireUtils.durationToHumanReadableString(d1s500ms)).isEqualTo("1s 500ms");

        assertThat(RequireUtils.durationToHumanReadableString(d1d20h55m44s500ms)).isEqualTo("1d 20h 55m 44s 500ms");
        assertThat(RequireUtils.durationToHumanReadableString(d1d20h55m44s500ms, 1)).isEqualTo("1d");
        assertThat(RequireUtils.durationToHumanReadableString(d1d20h55m44s500ms, 2)).isEqualTo("1d 20h");
    }
}
