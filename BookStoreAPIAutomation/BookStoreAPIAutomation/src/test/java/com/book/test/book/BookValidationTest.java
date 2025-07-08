package com.book.test.book;

import com.book.dataprovider.BookDataProvider;
import com.book.report.ReportLog;
import com.book.test.BaseTest;
import com.book.test.contants.EndPoints;
import com.book.test.context.UserDataContext;
import com.book.test.helper.InvalidTokenTestHelper;
import com.book.test.model.book.CreateBookRequest;
import com.book.test.model.ErrorResponse;
import com.book.test.req.RequestBase;
import com.book.test.req.RequestSpecFactory;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

public class BookValidationTest extends BaseTest {

    @Test(priority = 18,description = "Create book with invalid token should return 403", dependsOnGroups = "healthCheck")
    public void testCreateBookWithInvalidToken() {
        CreateBookRequest book = new CreateBookRequest("Invalid Book", "Author", 2000, "Invalid token test");
        Response response = RequestBase.post(EndPoints.CREATEBOOK, RequestSpecFactory.getSpecWithRequestBody(book, "invalidToken123"), 403);
        InvalidTokenTestHelper.assertForbidden(response, "Create Book", ReportLog.getTest());
    }

    @Test(priority = 19,description = "Get all books with invalid token should return 403", dependsOnGroups = "healthCheck")
    public void testGetBooksWithInvalidToken() {
        Response response = RequestBase.get(EndPoints.GETBOOKS, RequestSpecFactory.getSpecWithAuth("invalidToken123"), 403);
        InvalidTokenTestHelper.assertForbidden(response, "Get All Books", ReportLog.getTest());
    }

    @Test(priority = 20,description = "Update book with invalid token should return 403", dependsOnGroups = "healthCheck")
    public void testUpdateBookWithInvalidToken() {
        CreateBookRequest book = new CreateBookRequest("Updated Book", "Author", 2001, "Update test");
        String bookId = "1";
        Response response = RequestBase.put(String.format(EndPoints.UPDATEBOOK, bookId), RequestSpecFactory.getSpecWithRequestBody(book, "invalidToken123"), 403);
        InvalidTokenTestHelper.assertForbidden(response, "Update Book", ReportLog.getTest());
    }

    @Test(priority = 21,description = "Delete book with invalid token should return 403", dependsOnGroups = "healthCheck")
    public void testDeleteBookWithInvalidToken() {
        String bookId = "1";
        Response response = RequestBase.delete(String.format(EndPoints.DELETEBOOK, bookId), RequestSpecFactory.getSpecWithAuth("invalidToken123"), 403);
        InvalidTokenTestHelper.assertForbidden(response, "Delete Book", ReportLog.getTest());
    }


    @Test(priority = 22,dataProvider = "invalidBookData", dataProviderClass = BookDataProvider.class, description = "Create book with missing or invalid fields should return 422", dependsOnGroups = "userAccount")
    public void testCreateBookWithInvalidData(CreateBookRequest bookRequest, String scenario) {
        ReportLog.getTest().info("Testing scenario: " + scenario);

        Response response = RequestBase.post(
                EndPoints.CREATEBOOK,
                RequestSpecFactory.getSpecWithRequestBody(bookRequest, UserDataContext.getAuthToken()),
                422
        );

        System.out.println(response.asString());

        ErrorResponse errorResponse = response.as(ErrorResponse.class);
        Assert.assertEquals(errorResponse.getDetail().size(), 1);
        Assert.assertEquals(errorResponse.getDetail().get(0).getType(), "missing");
        Assert.assertEquals(errorResponse.getDetail().get(0).getMsg(), "Field required");
    }


}
