package com.terheyden.require;

import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static com.terheyden.require.Require.requireFuture;
import static com.terheyden.require.Require.requireRegularFile;
import static com.terheyden.require.Require.requireState;
import static java.util.Collections.emptyMap;

/**
 * Tutorial.
 */
class Tutorial {

    void tutorial() {

        // Just some variables to use in the tutorial.
        UUID id = null;
        String name = "Cora";
        String nullName = null;
        int age = 29;
        Map<String, String> data = emptyMap();
        List<String> users = Arrays.asList("Mika", "Tashi", "Cora");

        // There are 3 types of checks provided by JRequisites:

        // 1. 'Require' methods will throw an IllegalArgumentException if the check fails.
        //    Just like Objects.requireNonNull(), you can use these checks while assigning values:
        String goodName = Require.requireNotBlank(name);

        // 2. 'Check' methods return a boolean instead of throwing.
        //    Useful for 'if' statements or as stream filters:
        users.stream()
            .filter(Check::notBlank)
            .forEach(System.out::println);

        // 3. 'CheckIf' methods return an Optional, for when you want to provide a default, etc.:
        String greeting = CheckIf.ifNotBlank(name)
            .map(n -> "Hello, " + n)
            .orElse("(no name)");

        // You don't need to specify an error message, just the variable name / object label.
        // No more having to type out: requireNonNull(name, "Name is null");

        // Throws NullPointerException: "Name is null"
        Require.requireNotNull(nullName, "Name");
        // Throws IllegalArgumentException: "Number list has size 2, but required size is: 3 — contains: [one, two]"
        Require.requireSize(users, 3, "Number list");

        // You can perform custom checks:
        Require.requireTrue(age >= 18, "You must be 18 or older to use this app."); // Throws IllegalArgumentException
        requireState(data.containsKey("email"));                        // Throws IllegalStateException

        // Checks build on each other, for example, checkNotBlank() includes checkNotNull() and checkNotEmpty().

        // Plain Java:
        if (users == null || users.isEmpty()) {
            throw new IllegalArgumentException("List of users is null or empty!");
        }

        users.sort(Comparator.naturalOrder());

        // With Java Requirements:
        Require.requireNotEmpty(users, "List of users").sort(Comparator.naturalOrder());

        // There are also checks for files and dirs, and even conversion from String:
        Path settingsFile = requireRegularFile("/some/path/settings.txt");

        // Also requirements and conversions for java.time types.
        LocalDateTime appointment = LocalDateTime.now().plusDays(1);
        requireFuture(appointment);
    }
}
