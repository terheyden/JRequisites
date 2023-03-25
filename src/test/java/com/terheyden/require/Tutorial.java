package com.terheyden.require;

import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static com.terheyden.require.Check.checkNotBlank;
import static com.terheyden.require.Check.checkNotNull;
import static com.terheyden.require.Require.requireFuture;
import static com.terheyden.require.Require.requireNotBlank;
import static com.terheyden.require.Require.requireNotEmpty;
import static com.terheyden.require.Require.requireNotNull;
import static com.terheyden.require.Require.requireRegularFile;
import static com.terheyden.require.Require.requireState;
import static java.util.Collections.emptyList;
import static java.util.Collections.emptyMap;

/**
 * Tutorial.
 */
class Tutorial {

    void tutorial() {

        UUID id = null;
        String name = "Cora";
        int age = 29;
        Map<String, String> data = emptyMap();
        List<String> logMessages = emptyList();

        // 'Require' checks will throw an IllegalArgumentException if the check fails.
        // You can perform a check and assign the value to a variable in one line.
        UUID verifiedId = requireNotNull(id, "ID");
        String goodName = requireNotBlank(name, "Name");

        // Empty checks work on collections, maps, and strings.
        requireNotEmpty(data, "Data");

        // You can perform custom checks:
        Require.requireTrue(age >= 18, "You must be 18 or older to use this app."); // Throws IllegalArgumentException
        requireState(data.containsKey("email"));                        // Throws IllegalStateException

        // For providing simple default values, use the 'First' class:
        UUID goodId = checkNotNull(id).orElseGet(UUID::randomUUID);
        String userName = checkNotBlank(name).orElse("(no name)");

        // Finally, use the 'Check' class to use an Optional instead of throwing an exception.
        String greeting = checkNotBlank(name)
            .map(nam -> "Hi there, " + nam + "!")
            .orElse("I'm all alone...");

        // Checks build on each other, for example, checkNotBlank() includes checkNotNull() and checkNotEmpty().

        // Plain Java:
        if (logMessages == null || logMessages.isEmpty()) {
            throw new IllegalArgumentException("You have no log messages!");
        }

        logMessages.sort(Comparator.naturalOrder());

        // With Java Requirements:
        Require.requireSizeGreaterThan(3, logMessages).sort(Comparator.naturalOrder());

        // There are also checks for files and dirs, and even conversion from String:
        Path settingsFile = requireRegularFile("/some/path/settings.txt");

        // Also requirements and conversions for java.time types.
        LocalDateTime appointment = LocalDateTime.now().plusDays(1);
        requireFuture(appointment);
    }
}
