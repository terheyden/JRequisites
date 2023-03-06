package com.terheyden.require;

import java.util.Collection;
import java.util.Map;

/**
 * First class.
 */
public final class First {

    private static final String ALL_PARAMETERS_ARE_NULL = "All parameters are null";
    private static final String ALL_STRINGS_ARE_EMPTY = "All strings are empty";
    private static final String ALL_COLLECTIONS_ARE_EMPTY = "All collections are empty";
    private static final String ALL_MAPS_ARE_EMPTY = "All maps are empty";
    private static final String ALL_STRINGS_ARE_BLANK = "All strings are blank";

    private First() {
        // Private since this class shouldn't be instantiated.
    }

    public static <T> T firstNotNull(T first, T second) {
        if (first != null) return first;
        if (second != null) return second;
        throw new NullPointerException(ALL_PARAMETERS_ARE_NULL);
    }

    public static <T> T firstNotNull(T first, T second, T third) {
        if (first != null) return first;
        if (second != null) return second;
        if (third != null) return third;
        throw new NullPointerException(ALL_PARAMETERS_ARE_NULL);
    }

    public static <T extends CharSequence> T firstNotEmpty(T firstStr, T secondStr) {
        if (firstStr != null && firstStr.length() > 0) return firstStr;
        if (secondStr != null && secondStr.length() > 0) return secondStr;
        throw new IllegalArgumentException(ALL_STRINGS_ARE_EMPTY);
    }

    public static <T extends CharSequence> T firstNotEmpty(T firstStr, T secondStr, T thirdStr) {
        if (firstStr != null && firstStr.length() > 0) return firstStr;
        if (secondStr != null && secondStr.length() > 0) return secondStr;
        if (thirdStr != null && thirdStr.length() > 0) return thirdStr;
        throw new IllegalArgumentException(ALL_STRINGS_ARE_EMPTY);
    }

    public static <T extends Collection<?>> T firstNotEmpty(T firstColl, T secondColl) {
        if (firstColl != null && !firstColl.isEmpty()) return firstColl;
        if (secondColl != null && !secondColl.isEmpty()) return secondColl;
        throw new IllegalArgumentException(ALL_COLLECTIONS_ARE_EMPTY);
    }

    public static <T extends Collection<?>> T firstNotEmpty(T firstColl, T secondColl, T thirdColl) {
        if (firstColl != null && !firstColl.isEmpty()) return firstColl;
        if (secondColl != null && !secondColl.isEmpty()) return secondColl;
        if (thirdColl != null && !thirdColl.isEmpty()) return thirdColl;
        throw new IllegalArgumentException(ALL_COLLECTIONS_ARE_EMPTY);
    }

    public static <T extends Map<?, ?>> T firstNotEmpty(T firstMap, T secondMap) {
        if (firstMap != null && !firstMap.isEmpty()) return firstMap;
        if (secondMap != null && !secondMap.isEmpty()) return secondMap;
        throw new IllegalArgumentException(ALL_MAPS_ARE_EMPTY);
    }

    public static <T extends Map<?, ?>> T firstNotEmpty(T firstMap, T secondMap, T thirdMap) {
        if (firstMap != null && !firstMap.isEmpty()) return firstMap;
        if (secondMap != null && !secondMap.isEmpty()) return secondMap;
        if (thirdMap != null && !thirdMap.isEmpty()) return thirdMap;
        throw new IllegalArgumentException(ALL_MAPS_ARE_EMPTY);
    }

    public static <T extends CharSequence> T firstNotBlank(T firstStr, T secondStr) {
        if (firstStr != null && firstStr.length() > 0 && !firstStr.toString().trim().isEmpty()) return firstStr;
        if (secondStr != null && secondStr.length() > 0 && !secondStr.toString().trim().isEmpty()) return secondStr;
        throw new IllegalArgumentException(ALL_STRINGS_ARE_BLANK);
    }

    public static <T extends CharSequence> T firstNotBlank(T firstStr, T secondStr, T thirdStr) {
        if (firstStr != null && firstStr.length() > 0 && !firstStr.toString().trim().isEmpty()) return firstStr;
        if (secondStr != null && secondStr.length() > 0 && !secondStr.toString().trim().isEmpty()) return secondStr;
        if (thirdStr != null && thirdStr.length() > 0 && !thirdStr.toString().trim().isEmpty()) return thirdStr;
        throw new IllegalArgumentException(ALL_STRINGS_ARE_BLANK);
    }
}
