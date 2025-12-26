package com.xenialsoft.api.common.util;

import com.aventrix.jnanoid.jnanoid.NanoIdUtils;

import lombok.experimental.UtilityClass;

@UtilityClass
public class IdUtils {

    private static final char[] DEFAULT_ALPHABET = "0123456789abcdefghijklmnopqrstuvwxyz".toCharArray();
    public static final int DEFAULT_SIZE = 10;

    public String generate() {
        return generate(DEFAULT_SIZE);
    }

    public String generate(int size) {
        return NanoIdUtils.randomNanoId(NanoIdUtils.DEFAULT_NUMBER_GENERATOR, DEFAULT_ALPHABET, size);
    }

}
