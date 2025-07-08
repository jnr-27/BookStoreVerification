package com.book.test.data;

import java.util.Random;

public class EmailGenerator {

    private static final String CHARACTERS = "abcdefghijklmnopqrstuvwxyz0123456789";
    private static final String DOMAIN = "@bsstore.com";
    private static final int RANDOM_LENGTH = 6;  // 8 total - 2 fixed = 6

    public static String generateBsEmail() {
        StringBuilder sb = new StringBuilder("bs");  // fixed prefix
        Random random = new Random();

        for (int i = 0; i < RANDOM_LENGTH; i++) {
            sb.append(CHARACTERS.charAt(random.nextInt(CHARACTERS.length())));
        }

        return sb.toString() + DOMAIN;
    }
}
