package com.book.test.context;

public class UserDataContext {

    private static String email;
    private static String password;
    private static String authToken;

    public static void setEmail(String email) {
        UserDataContext.email = email;
    }

    public static void setPassword(String password) {
        UserDataContext.password = password;
    }

    public static void setAuthToken(String authToken) {
        UserDataContext.authToken = authToken;
    }

    public static String getEmail() {
        return email;
    }

    public static String getPassword() {
        return password;
    }

    public static String getAuthToken() {
        return authToken;
    }




}
