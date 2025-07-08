package com.book.test.helper;

import com.aventstack.extentreports.ExtentTest;
import com.book.test.model.DetailsResponse;
import io.restassured.response.Response;
import org.testng.Assert;

public class InvalidTokenTestHelper {
    public static void assertForbidden(Response response, String operation, ExtentTest test) {
        DetailsResponse details = response.as(DetailsResponse.class);
        Assert.assertEquals(response.getStatusCode(), 403, "Expected 403 Forbidden");
        Assert.assertEquals(details.getDetail(), "Invalid token or expired token");
        test.info( operation + " failed as expected with invalid token: " + details.getDetail());
    }


}
