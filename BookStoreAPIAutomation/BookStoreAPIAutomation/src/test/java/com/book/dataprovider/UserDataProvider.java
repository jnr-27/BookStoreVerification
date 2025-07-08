package com.book.dataprovider;

import org.testng.annotations.DataProvider;

public class UserDataProvider {
    @DataProvider(name = "invalidUserData")
    public static Object[][] invalidUserData() {
        return new Object[][]{
                {null, "abcd1234", "Null email"},
                {"", "abcd1234", "Empty email"},
                {"bs1234@example.com", null, "Null password"},
                {"bs1234@example.com", "", "Empty password"}
        };
    }

}
