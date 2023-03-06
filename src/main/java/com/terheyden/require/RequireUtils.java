package com.terheyden.require;

import javax.annotation.Nullable;
import java.io.File;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

/**
 * RequireUtils class.
 */
final class RequireUtils {

    private RequireUtils() {
        // Private since this class shouldn't be instantiated.
    }

    /**
     * <pre>
     * {@code
     * setupName("The settings", "File") -> "The settings"
     * setupName(null, "File") -> "File"
     * }
     * </pre>
     */
    static String setupName(String name, String defaultTitleName) {
        return name != null ? name : defaultTitleName;
    }

    /**
     * Get the size of a regular file, or the number of files in a directory.
     * @throws IllegalArgumentException If the file is not a regular file or directory
     */
    static long getFileOrDirSize(File path, String name) {

        if (path.isFile()) {
            return path.length();
        }

        if (path.isDirectory()) {
            return path.list().length;
        }

        throw new IllegalArgumentException(name + " is not a file or directory");
    }

    /**
     * For {@link Check}, to verify if a string is a valid path.
     */
    static Optional<Path> safeGetPath(@Nullable String path) {

        if (path == null) {
            return Optional.empty();
        }

        try {

            return Optional.of(Paths.get(path));

        } catch (InvalidPathException ignore) {
            return Optional.empty();
        }
    }

    /**
     * Throw any exception unchecked.
     */
    @SuppressWarnings("unchecked")
    static <E extends Throwable, R> R throwUnchecked(Throwable throwable) throws E {
        throw (E) throwable;
    }
}
