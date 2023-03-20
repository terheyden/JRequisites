package com.terheyden.require;

import javax.annotation.Nullable;
import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilderFactory;
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

    /**
     * Used to do XML parsing / validation.
     */
    @Nullable
    private static DocumentBuilderFactory lazyDocumentBuilderFactory;

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

    private static DocumentBuilderFactory getDocumentBuilderFactory() {

            if (lazyDocumentBuilderFactory == null) {
                // It's not super important if there's a thread collision, it'll just get created twice.
                lazyDocumentBuilderFactory = createDocumentBuilderFactory();
            }

            return lazyDocumentBuilderFactory;
    }

    /**
     * Creates a new DocumentBuilderFactory with some security features enabled.
     * Should only be called once when we need to set up our lazy global DocumentBuilderFactory.
     */
    private static DocumentBuilderFactory createDocumentBuilderFactory() {
        try {

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            // Prevent XXE attacks and other XML vulnerabilities:
            // I got these from a SonarQube rule:
            // completely disable DOCTYPE declaration:
            factory.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
            // completely disable external entities declarations:
            factory.setFeature("http://xml.org/sax/features/external-general-entities", false);
            factory.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
            // prohibit the use of all protocols by external entities:
            factory.setAttribute(XMLConstants.ACCESS_EXTERNAL_DTD, "");
            factory.setAttribute(XMLConstants.ACCESS_EXTERNAL_SCHEMA, "");
            // disable entity expansion but keep in mind that this doesn't prevent fetching external entities
            // and this solution is not correct for OpenJDK < 13 due to a bug:
            // https://bugs.openjdk.java.net/browse/JDK-8206132
            factory.setExpandEntityReferences(false);

            return factory;

        } catch (Exception e) {
            return throwUnchecked(e);
        }
    }

    static boolean isXml(String xml) {

        try {

            getDocumentBuilderFactory().newDocumentBuilder().parse(xml);
            return true;

        } catch (Exception ignore) {
            return false;
        }
    }

    static boolean isJson(String json) {

        String cleanJson = json.trim();

        if (cleanJson.startsWith("{")) {
            return cleanJson.endsWith("}");
        } else if (cleanJson.startsWith("[")) {
            return cleanJson.endsWith("]");
        } else {
            return false;
        }

    }
}
