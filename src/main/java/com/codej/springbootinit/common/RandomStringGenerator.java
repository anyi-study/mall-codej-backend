package com.codej.springbootinit.common;

import java.util.Random;

public class RandomStringGenerator {

    public static void main(String[] args) {
        String randomString = generateRandomString(7);
        System.out.println("Generated string: " + randomString);
    }

    public static String generateRandomString(int length) {
        if (length <= 0) {
            throw new IllegalArgumentException("Length must be positive.");
        }

        // 定义字符集
        char[] digits = {'1', '2', '3', '4', '5', '6', '7', '8', '9'};
        char[] letters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();

        // 合并字符集
        char[] allChars = new char[digits.length + letters.length];
        System.arraycopy(digits, 0, allChars, 0, digits.length);
        System.arraycopy(letters, 0, allChars, digits.length, letters.length);

        // 生成字符串
        StringBuilder sb = new StringBuilder(length);

        // 生成首位字符
        Random random = new Random();
        char firstChar = allChars[random.nextInt(allChars.length)];
        sb.append(firstChar);

        // 生成剩余字符
        for (int i = 1; i < length; i++) {
            char nextChar = allChars[random.nextInt(allChars.length)];
            sb.append(nextChar);
        }

        return sb.toString();
    }
}
