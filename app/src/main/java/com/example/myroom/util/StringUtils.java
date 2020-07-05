
package com.example.myroom.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import androidx.annotation.Nullable;

/**
 * Utilities for working with strings like splitting, joining, url-encoding, hex, and digests.
 *
 * @author markus
 */
public class StringUtils {
    private final static char[] HEX_CHARS = {
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'
    };

    /**
     * Splits a String based on a single character, which is usually faster than regex-based String.split().
     * NOTE: split("AA;BB;;", ';') == ["AA", "BB", "", ""], this may be different from String.split()
     *  */
    public static String[] split(String string, char delimiter) {
        int delimeterCount = 0;
        int start = 0;
        int end;
        while ((end = string.indexOf(delimiter, start)) != -1) {
            delimeterCount++;
            start = end + 1;
        }

        String[] result = new String[delimeterCount + 1];
        start = 0;
        for (int i = 0; i < delimeterCount; i++) {
            end = string.indexOf(delimiter, start);
            result[i] = string.substring(start, end);
            start = end + 1;
        }
        result[delimeterCount] = string.substring(start, string.length());
        return result;
    }

    /**
     * URL-Encodes a given string using UTF-8 (some web pages have problems with UTF-8 and umlauts, consider
     * {@link #encodeUrlIso(String)} also). No UnsupportedEncodingException to handle as it is dealt with in this
     * method.
     */
    public static String encodeUrl(String stringToEncode) {
        try {
            return URLEncoder.encode(stringToEncode, "UTF-8");
        } catch (UnsupportedEncodingException e1) {
            throw new RuntimeException(e1);
        }
    }

    /**
     * URL-encodes a given string using ISO-8859-1, which may work better with web pages and umlauts compared to UTF-8.
     * No UnsupportedEncodingException to handle as it is dealt with in this method.
     */
    public static String encodeUrlIso(String stringToEncode) {
        try {
            return URLEncoder.encode(stringToEncode, "ISO-8859-1");
        } catch (UnsupportedEncodingException e1) {
            throw new RuntimeException(e1);
        }
    }

    /**
     * URL-Decodes a given string using UTF-8. No UnsupportedEncodingException to handle as it is dealt with in this
     * method.
     */
    public static String decodeUrl(String stringToDecode) {
        try {
            return URLDecoder.decode(stringToDecode, "UTF-8");
        } catch (UnsupportedEncodingException e1) {
            throw new RuntimeException(e1);
        }
    }

    /**
     * URL-Decodes a given string using ISO-8859-1. No UnsupportedEncodingException to handle as it is dealt with in
     * this method.
     */
    public static String decodeUrlIso(String stringToDecode) {
        try {
            return URLDecoder.decode(stringToDecode, "ISO-8859-1");
        } catch (UnsupportedEncodingException e1) {
            throw new RuntimeException(e1);
        }
    }

    /**
     * Generates the MD5 digest (32 hex characters) for a given String based on UTF-8.
     */
    public static String md5(String stringToEncode) {
        return digest(stringToEncode, "MD5", "UTF-8");
    }

    /**
     * Generates the SHA-1 digest (40 hex characters) for a given String based on UTF-8.
     * The SHA-1 algorithm produces less collisions than MD5.
     *
     * @return SHA-1 digest .
     */
    public static String sha1(String stringToEncode) {
        return digest(stringToEncode, "SHA-1", "UTF-8");
    }

    /**
     * Generates a digest (hex string) for the given string
     */
    public static String digest(String string, String digestAlgo, String encoding) {
        try {
            MessageDigest digester = MessageDigest.getInstance(digestAlgo);
            digester.update(string.getBytes(encoding));
            return hex(digester.digest());
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    public static String hex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for (int i = 0; i < bytes.length; i++) {
            int value = bytes[i] & 0xFF;
            hexChars[i * 2] = HEX_CHARS[value >>> 4];
            hexChars[i * 2 + 1] = HEX_CHARS[value & 0x0F];
        }
        return new String(hexChars);
    }

    /**
     * @throws IllegalArgumentException if the given string is invalid hex
     */
    public static byte[] parseHex(String hex) {
        int length = hex.length();
        if (length % 2 != 0) {
            throw new IllegalArgumentException("Illegal string length: " + length);
        }
        int bytesLength = length / 2;
        byte[] bytes = new byte[bytesLength];
        int idxChar = 0;
        for (int i = 0; i < bytesLength; i++) {
            int value = parseHexDigit(hex.charAt(idxChar++)) << 4;
            value |= parseHexDigit(hex.charAt(idxChar++));
            bytes[i] = (byte) value;
        }
        return bytes;
    }

    /**
     * @throws IllegalArgumentException if the given char is invalid hex
     */
    public static int parseHexDigit(char c) {
        if ('0' <= c && c <= '9') {
            return c - '0';
        } else if ('A' <= c && c <= 'F') {
            return c - 'A' + 10;
        } else if ('a' <= c && c <= 'f') {
            return c - 'a' + 10;
        }
        throw new IllegalArgumentException("Illegal hex digit: " + c);
    }

    /**
     * Simple HTML/XML entity resolving: Only supports unicode enitities and a very limited number text represented
     * entities (apos, quot, gt, lt, and amp). There are many more: http://www.w3.org/TR/REC-html40/sgml/dtd.html
     *
     * @param entity The entity name without & and ; (null throws NPE)
     * @return Resolved entity or the entity itself if it could not be resolved.
     */
    public static String resolveEntity(String entity) {
        if (entity.length() > 1 && entity.charAt(0) == '#') {
            if (entity.charAt(1) == 'x') {
                return String.valueOf((char) Integer.parseInt(entity.substring(2), 16));
            } else {
                return String.valueOf((char) Integer.parseInt(entity.substring(1)));
            }
        } else if (entity.equals("apos")) {
            return "'";
        } else if (entity.equals("quot")) {
            return "\"";
        } else if (entity.equals("gt")) {
            return ">";
        } else if (entity.equals("lt")) {
            return "<";
        } else if (entity.equals("amp")) {
            return "&";
        } else {
            return entity;
        }
    }

    /**
     * Cuts the string at the end if it's longer than maxLength and appends "..." to it. The length of the resulting
     * string including "..." is always less or equal to the given maxLength. It's valid to pass a null text; in this
     * case null is returned.
     */
    public static String ellipsize(String text, int maxLength) {
        return ellipsize(text, maxLength, "...");
    }

    /**
     * Cuts the string at the end if it's longer than maxLength and appends the given end string to it. The length of
     * the resulting string is always less or equal to the given maxLength. It's valid to pass a null text; in this
     * case null is returned.
     */
    public static String ellipsize(String text, int maxLength, String end) {
        if (text != null && text.length() > maxLength) {
            return text.substring(0, maxLength - end.length()) + end;
        }
        return text;
    }

    public static String[] splitLines(String text, boolean skipEmptyLines) {
        if (skipEmptyLines) {
            return text.split("[\n\r]+");
        } else {
            return text.split("\\r?\\n");
        }
    }

    public static List<String> findLinesContaining(String text, String searchText) {
        String[] splitLinesSkipEmpty = splitLines(text, true);
        List<String> matching = new ArrayList<>();
        for (String line : splitLinesSkipEmpty) {
            if (line.contains(searchText)) {
                matching.add(line);
            }
        }
        return matching;
    }

    /**
     * Joins the given iterable objects using the given separator into a single string.
     *
     * @return the joined string or an empty string if iterable is null
     */
    public static String join(Iterable<?> iterable, String separator) {
        if (iterable != null) {
            StringBuilder buf = new StringBuilder();
            Iterator<?> it = iterable.iterator();
            char singleChar = separator.length() == 1 ? separator.charAt(0) : 0;
            while (it.hasNext()) {
                buf.append(it.next());
                if (it.hasNext()) {
                    if (singleChar != 0) {
                        // More efficient
                        buf.append(singleChar);
                    } else {
                        buf.append(separator);
                    }
                }
            }
            return buf.toString();
        } else {
            return "";
        }
    }

    /**
     * Joins the given ints using the given separator into a single string.
     *
     * @return the joined string or an empty string if the int array is null
     */
    public static String join(int[] array, String separator) {
        if (array != null) {
            StringBuilder buf = new StringBuilder(Math.max(16, (separator.length() + 1) * array.length));
            char singleChar = separator.length() == 1 ? separator.charAt(0) : 0;
            for (int i = 0; i < array.length; i++) {
                if (i != 0) {
                    if (singleChar != 0) {
                        // More efficient
                        buf.append(singleChar);
                    } else {
                        buf.append(separator);
                    }
                }
                buf.append(array[i]);
            }
            return buf.toString();
        } else {
            return "";
        }
    }

    /**
     * Joins the given Strings using the given separator into a single string.
     *
     * @return the joined string or an empty string if the String array is null
     */
    public static String join(String[] array, String separator) {
        if (array != null) {
            StringBuilder buf = new StringBuilder(Math.max(16, (separator.length() + 1) * array.length));
            char singleChar = separator.length() == 1 ? separator.charAt(0) : 0;
            for (int i = 0; i < array.length; i++) {
                if (i != 0) {
                    if (singleChar != 0) {
                        // More efficient
                        buf.append(singleChar);
                    } else {
                        buf.append(separator);
                    }
                }
                buf.append(array[i]);
            }
            return buf.toString();
        } else {
            return "";
        }
    }

    /**
     * Returns true if the string is null or 0-length.
     * @param str the string to be examined
     * @return true if str is null or zero length
     */
    public static boolean isEmpty(@Nullable CharSequence str) {
        return str == null || str.length() == 0;
    }

    /**
     * Returns true if a and b are equal, including if they are both null.
     * <p><i>Note: In platform versions 1.1 and earlier, this method only worked well if
     * both the arguments were instances of String.</i></p>
     * @param a first CharSequence to check
     * @param b second CharSequence to check
     * @return true if a and b are equal
     */
    public static boolean equals(CharSequence a, CharSequence b) {
        if (a == b) return true;
        int length;
        if (a != null && b != null && (length = a.length()) == b.length()) {
            if (a instanceof String && b instanceof String) {
                return a.equals(b);
            } else {
                for (int i = 0; i < length; i++) {
                    if (a.charAt(i) != b.charAt(i)) return false;
                }
                return true;
            }
        }
        return false;
    }

    /**
     * 判断字符串是否为null或全为空格
     *
     * @param s 待校验字符串
     * @return {@code true}: null或全空格<br> {@code false}: 不为null且不全空格
     */
    public static boolean isSpace(String s) {
        return (s == null || s.trim().length() == 0);
    }

    /**
     * 字符串转Byte
     * @param str
     * @return
     */
    public static byte[] toByteUtf8(String str){
        try{
            return toByte(str,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return new byte[0];
    }

    /**
     * 字符串转Byte
     * @param str
     * @return
     */
    public static byte[] toByteGbk(String str){
        try{
            return toByte(str,"GBK");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return new byte[0];
    }
    /**
     * 字符串转Byte
     * @param str
     * @param charsetName
     * @return
     * @throws UnsupportedEncodingException
     */
    public static byte[] toByte(String str, String charsetName)throws UnsupportedEncodingException {
        if (StringUtils.isEmpty(str)) return new byte[0];
        return str.getBytes(charsetName);
    }

    /**
     * Byte转字符串
     * @param bytes
     * @return
     */
    public static String fromByteUtf8(byte[] bytes){
        try{
            return fromByte(bytes,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Byte转字符串
     * @param bytes
     * @return
     */
    public static String fromByteGbk(byte[] bytes){
        try{
            return fromByte(bytes,"GBK");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Byte转字符串
     * @param bytes
     * @param charsetName
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String fromByte(byte[] bytes, String charsetName) throws UnsupportedEncodingException {
        return new String(bytes,charsetName);
    }
}
