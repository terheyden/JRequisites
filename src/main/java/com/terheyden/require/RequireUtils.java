package com.terheyden.require;

import javax.annotation.Nullable;
import java.io.File;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.Optional;

/**
 * RequireUtils class.
 */
final class RequireUtils {

    private RequireUtils() {
        // Private since this class shouldn't be instantiated.
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
    static Optional<Path> pathGetOptional(@Nullable String path) {

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

    /**
     * A check just for Iterables to determine length checks.
     * This doesn't account for nulls, so the iterable param must be non-null.
     * @return the iterator used for checking, or {@link Optional#empty()}.
     */
    static <T> Optional<Iterator<T>> hasAtLeastSize(Iterable<T> iterable, int length) {

        if (iterable == null) {
            return Optional.empty();
        }

        Iterator<T> iterator = iterable.iterator();

        while (iterator.hasNext() && length > 0) {
            iterator.next();
            length--;
        }

        // If we ran out of items before hitting the length, then the check fails.
        return length > 0 ? Optional.empty() : Optional.of(iterator);
    }
}
