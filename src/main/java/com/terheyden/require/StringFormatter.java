package com.terheyden.require;

/**
 * A highly performant string formatter.
 */
@SuppressWarnings({ "UnclearExpression", "DataFlowIssue" })
public final class StringFormatter {

    private static final char START1 = '{';
    private static final char START2 = '%';
    private static final char END1 = '}';
    private static final char END2 = 's';
    private static final int PLACEHOLDER_LEN = 2;

    private StringFormatter() {
        // Private since this class shouldn't be instantiated.
    }

    public static String format(String str) {
        return str;
    }

    public static String format(String str, Object arg1) {
        if (Check.isBlank(str)) return str;
        FormattedString formatStr = new FormattedString(str);
        return injectPlaceholder(formatStr, arg1).str;
    }

    public static String format(String str, Object arg1, Object arg2) {
        if (Check.isBlank(str)) return str;
        FormattedString formatStr = new FormattedString(str);
        formatStr = injectPlaceholder(formatStr, arg1); // I know, the same reference gets returned.
        formatStr = injectPlaceholder(formatStr, arg2); // Still we can treat it like it's immutable.
        return formatStr.str;
    }

    public static String format(String str, Object arg1, Object arg2, Object arg3) {
        if (Check.isBlank(str)) return str;
        FormattedString formatStr = new FormattedString(str);
        formatStr = injectPlaceholder(formatStr, arg1); // I know, the same reference gets returned.
        formatStr = injectPlaceholder(formatStr, arg2); // Still we can treat it like it's immutable.
        formatStr = injectPlaceholder(formatStr, arg3);
        return formatStr.str;
    }

    public static String format(String str, Object arg1, Object arg2, Object arg3, Object arg4) {
        if (Check.isBlank(str)) return str;
        FormattedString formatStr = new FormattedString(str);
        formatStr = injectPlaceholder(formatStr, arg1); // I know, the same reference gets returned.
        formatStr = injectPlaceholder(formatStr, arg2); // Still we can treat it like it's immutable.
        formatStr = injectPlaceholder(formatStr, arg3);
        formatStr = injectPlaceholder(formatStr, arg4);
        return formatStr.str;
    }

    public static String format(String str, Object arg1, Object arg2, Object arg3, Object arg4, Object arg5) {
        if (Check.isBlank(str)) return str;
        FormattedString formatStr = new FormattedString(str);
        formatStr = injectPlaceholder(formatStr, arg1); // I know, the same reference gets returned.
        formatStr = injectPlaceholder(formatStr, arg2); // Still we can treat it like it's immutable.
        formatStr = injectPlaceholder(formatStr, arg3);
        formatStr = injectPlaceholder(formatStr, arg4);
        formatStr = injectPlaceholder(formatStr, arg5);
        return formatStr.str;
    }

    private static FormattedString injectPlaceholder(FormattedString formatStr, Object arg1) {

        String str = formatStr.str;
        // Since the placeholder is two chars long, we can skip by 2.
        // We'll land on either the first or second char of the placeholder.
        for (int strOff = formatStr.strOffset; strOff < str.length(); strOff += PLACEHOLDER_LEN) {

            int foundIndex = -1;

            if (foundFirstPlaceholder(str, strOff)) {
                foundIndex = strOff;
            } else if (foundSecondPlaceholder(str, strOff)) {
                foundIndex = strOff - 1;
            }

            if (foundIndex >= 0) {
                // Track the str length for later.
                int oldLen = str.length();
                // Found a placeholder, replace it.
                str = str.substring(0, foundIndex) + arg1 + str.substring(foundIndex + PLACEHOLDER_LEN);
                // Move the offset to the end of the replacement text.
                // I thought about supporting nested placeholders, but we can't because we're tracking the placeholder count.
                strOff = foundIndex + PLACEHOLDER_LEN + (str.length() - oldLen);

                formatStr.str = str;
                formatStr.strOffset = strOff;
                return formatStr;
            }
        }

        // Placeholder could not be found.
        return formatStr;
    }

    private static boolean foundFirstPlaceholder(String str, int strOff) {
        return
            str.charAt(strOff) == START1             // found a start char, check if the next char is an end char.
                && strOff + 1 < str.length()         // it's not the end of the string...
                && str.charAt(strOff + 1) == END1    // and we found the end char right after.
            ||
            str.charAt(strOff) == START2             // found a start char, check if the next char is an end char.
                && strOff + 1 < str.length()         // it's not the end of the string...
                && str.charAt(strOff + 1) == END2;   // and we found the end char right after.
    }

    private static boolean foundSecondPlaceholder(String str, int strOff) {
        return
            str.charAt(strOff) == END1                // found an end char, check if the previous char is a start char.
                && strOff > 0                         // it's not the start of the string.
                && str.charAt(strOff - 1) == START1   // and we found the start char right before.
            ||
            str.charAt(strOff) == END2                // found an end char, check if the previous char is a start char.
                && strOff > 0                         // it's not the start of the string.
                && str.charAt(strOff - 1) == START2;  // and we found the start char right before.
    }

    /**
     * Used to track the state as we move through a formatted string and format it.
     * Mutable so we don't waste a lot of objects.
     */
    private static final class FormattedString {
        String str;
        int strOffset;

        FormattedString(String str) {
            this.str = str;
            this.strOffset = 0;
        }
    }
}
