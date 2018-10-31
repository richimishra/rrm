package com.project.project1.Utils;

import org.springframework.stereotype.Component;

@Component
public class Base62 {
    public static final String ALPHABET = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    public static final int BASE62 = ALPHABET.length();

    public static String toBase62(long value) {
        value = value * 10000000;
        final StringBuilder sb = new StringBuilder(1);
        do {

            sb.insert(0, ALPHABET.charAt((int) (value % BASE62)));
            value /= BASE62;
        } while (value > 0);
        return sb.toString();
    }

    public static long toBase10(String str) {
        return toBase10(new StringBuilder(str).reverse().toString().toCharArray());
    }

    private static long toBase10(char[] chars) {
        long result = 0;
        for (int i = chars.length - 1; i >= 0; i--) {
            result += toBase10(ALPHABET.indexOf(chars[i]), i);
        }
        return result;
    }

    private static long toBase10(long result, int pow) {
        return result * (int) Math.pow(BASE62, pow);
    }

}
