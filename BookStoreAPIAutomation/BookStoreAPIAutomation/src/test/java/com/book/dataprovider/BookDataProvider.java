package com.book.dataprovider;

import com.book.test.model.book.CreateBookRequest;
import org.testng.annotations.DataProvider;

public class BookDataProvider {

    @DataProvider(name = "invalidBookData")
    public static Object[][] invalidBookData() {
        return new Object[][] {
                { new CreateBookRequest(null, "Author A", 1990, "Summary A"), "Null name" },
                { new CreateBookRequest("Book B", null, 1990, "Summary B"), "Null author" },
                { new CreateBookRequest("Book C", "Author C", 0, "Summary C"), "Invalid year (0)" },
                { new CreateBookRequest("", "Author D", 2000, "Summary D"), "Empty name" }
        };
    }
}
