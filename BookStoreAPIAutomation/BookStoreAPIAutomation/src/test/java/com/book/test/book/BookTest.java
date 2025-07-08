package com.book.test.book;

import com.book.report.ReportLog;
import com.book.test.BaseTest;
import com.book.test.contants.EndPoints;
import com.book.test.context.BookDataContext;
import com.book.test.context.UserDataContext;
import com.book.test.model.DetailsResponse;
import com.book.test.model.book.BookNotAvaliableResponse;
import com.book.test.model.book.CreateBookRequest;
import com.book.test.model.book.CreateBookResponse;
import com.book.test.model.book.DeleteBookResponse;
import com.book.test.req.RequestBase;
import com.book.test.req.RequestSpecFactory;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class BookTest extends BaseTest {

    @Test(priority = 4, description = "Create book with valid data", dependsOnGroups = "healthCheck")
    public void testCreateBookWithValidData() {

        CreateBookRequest createBookRequest = new CreateBookRequest("QA Jungle Book", "QA Rudyard Kipling", 1894, "QA Description for Jungle Book");

        ReportLog.getTest().info("Creating book with details " + createBookRequest.toString());

        Response response = RequestBase.post(EndPoints.CREATEBOOK, RequestSpecFactory.getSpecWithRequestBody(createBookRequest, UserDataContext.getAuthToken()), 200);
        System.out.println(response.asString());
        CreateBookResponse createBookResponse = response.as(CreateBookResponse.class);
        BookDataContext.setId(createBookResponse.getId());
        BookDataContext.setName(createBookResponse.getName());
        BookDataContext.setAuthor(createBookResponse.getAuthor());
        BookDataContext.setBookSummary(createBookResponse.getBookSummary());
        BookDataContext.setPublishedYear(createBookResponse.getPublishedYear());


        Assert.assertEquals(createBookRequest.getName(), createBookResponse.getName());
        Assert.assertEquals(createBookRequest.getAuthor(), createBookResponse.getAuthor());
        Assert.assertEquals(createBookRequest.getBookSummary(), createBookResponse.getBookSummary());
        Assert.assertEquals(createBookRequest.getPublishedYear(), createBookResponse.getPublishedYear());

        ReportLog.getTest().info("Book id generated os " + createBookResponse.getId());
        ReportLog.getTest().pass("Book created with details " + createBookResponse.toString());

    }

    @Test(priority = 5, description = "book by ID and verify details", dependsOnMethods = "testCreateBookWithValidData")
    public void testBookByIdAndVerifyDetails() {
        String bookID = String.format(EndPoints.GETBOOK, BookDataContext.getId());
        Response response = RequestBase.get(bookID, RequestSpecFactory.getSpecWithAuth(UserDataContext.getAuthToken()), 200);

        CreateBookResponse createBookResponse = response.as(CreateBookResponse.class);

        ReportLog.getTest().info(String.format("Verifying Created book details ->" +
                        " id:%s, name:%s ,author:%s,year:%s ,bookSummary:%s,  "
                , BookDataContext.getId(), BookDataContext.getName(), BookDataContext.getAuthor(), BookDataContext.getPublishedYear(), BookDataContext.getBookSummary()));


        Assert.assertEquals(BookDataContext.getId(), createBookResponse.getId());
        Assert.assertEquals(BookDataContext.getName(), createBookResponse.getName());
        Assert.assertEquals(BookDataContext.getAuthor(), createBookResponse.getAuthor());
        Assert.assertEquals(BookDataContext.getBookSummary(), createBookResponse.getBookSummary());
        Assert.assertEquals(BookDataContext.getPublishedYear(), createBookResponse.getPublishedYear());
        ReportLog.getTest().info("Verifying Book details for id " + createBookResponse.getId());

        ReportLog.getTest().pass("Book details verified through get request " + createBookResponse.toString());

    }

    @Test(priority = 6, description = "Update book and verify updated details", dependsOnMethods = "testBookByIdAndVerifyDetails")
    public void testUpdateBookWithNewDetails() {
        CreateBookRequest updateBookRequest = new CreateBookRequest(BookDataContext.getName() + " name update", BookDataContext.getAuthor() + " author update", 1894 + 2, BookDataContext.getBookSummary() + " summary update");

        ReportLog.getTest().info("Updating book  details -> name:%s ,author:%s, bookSummary:%s,  " + updateBookRequest.toString());


        String bookID = String.format(EndPoints.UPDATEBOOK, BookDataContext.getId());

        Response response = RequestBase.put(bookID, RequestSpecFactory.getSpecWithRequestBody(updateBookRequest, UserDataContext.getAuthToken()), 200);

        System.out.println(response.asString());
        CreateBookResponse createBookResponse = response.as(CreateBookResponse.class);
        BookDataContext.setId(createBookResponse.getId());
        BookDataContext.setName(createBookResponse.getName());
        BookDataContext.setAuthor(createBookResponse.getAuthor());
        BookDataContext.setBookSummary(createBookResponse.getBookSummary());
        BookDataContext.setPublishedYear(createBookResponse.getPublishedYear());


        Assert.assertEquals(updateBookRequest.getName(), createBookResponse.getName());
        Assert.assertEquals(updateBookRequest.getAuthor(), createBookResponse.getAuthor());
        Assert.assertEquals(updateBookRequest.getBookSummary(), createBookResponse.getBookSummary());
        Assert.assertEquals(updateBookRequest.getPublishedYear(), createBookResponse.getPublishedYear());

        ReportLog.getTest().info("Updated Book for id " + createBookResponse.getId());

        ReportLog.getTest().pass("Updated Book details are " + createBookResponse.toString());

    }

    @Test(priority = 7, description = "Get all books and verify created book exists", dependsOnMethods = "testUpdateBookWithNewDetails")
    public void testGetAllBooksAndVerifyCreatedBook() {

        ReportLog.getTest().info("verifying get Books api  with end point " + EndPoints.GETBOOKS);

        Response response = RequestBase.get(EndPoints.GETBOOKS, RequestSpecFactory.getSpecWithAuth(UserDataContext.getAuthToken()), 200);
        System.out.println(response.asString());

        CreateBookResponse[] createBookResponse = response.as(CreateBookResponse[].class);
        List<CreateBookResponse> createBookResponseList = Arrays.asList(createBookResponse);
        List<CreateBookResponse> requiredBook = createBookResponseList.stream().filter(bookDetails -> bookDetails.getId() == BookDataContext.getId()).collect(Collectors.toList());


        Assert.assertEquals(BookDataContext.getId(), requiredBook.get(0).getId());
        Assert.assertEquals(BookDataContext.getName(), requiredBook.get(0).getName());
        Assert.assertEquals(BookDataContext.getAuthor(), requiredBook.get(0).getAuthor());
        Assert.assertEquals(BookDataContext.getBookSummary(), requiredBook.get(0).getBookSummary());
        Assert.assertEquals(BookDataContext.getPublishedYear(), requiredBook.get(0).getPublishedYear());


        ReportLog.getTest().pass("Get Books call is success and verified book details " + createBookResponse.toString() + " in books list");


    }

    @Test(priority = 8, description = "Delete book by ID", dependsOnMethods = "testGetAllBooksAndVerifyCreatedBook")
    public void testDeleteBookById() {
        String deleteEndPoint = String.format(EndPoints.DELETEBOOK, BookDataContext.getId());
        ReportLog.getTest().info("verifying delete Book api  with end point " + deleteEndPoint);

        Response response = RequestBase.delete(deleteEndPoint, RequestSpecFactory.getSpecWithAuth(UserDataContext.getAuthToken()), 200);
        DeleteBookResponse deleteBookResponse = response.as(DeleteBookResponse.class);
        Assert.assertEquals("Book deleted successfully", deleteBookResponse.getMessage());
        ReportLog.getTest().pass(String.format("Delete Books call is success for Bookid : %s, Book name: %s ", BookDataContext.getId(), BookDataContext.getName()));
    }

    @Test(priority = 9, description = "Create and delete book in same test", dependsOnMethods = "testDeleteBookById")
    public void testCreateAndDeleteBookIndependently() {


        CreateBookRequest createBookRequest = new CreateBookRequest("Test NG Intro", "QA", 2009, "QA Description for Test NG");

        ReportLog.getTest().info("Creating book with details " + createBookRequest.toString());

        Response response = RequestBase.post(EndPoints.CREATEBOOK, RequestSpecFactory.getSpecWithRequestBody(createBookRequest, UserDataContext.getAuthToken()), 200);
        System.out.println(response.asString());
        CreateBookResponse createBookResponse = response.as(CreateBookResponse.class);

        ReportLog.getTest().info("Book id generated os " + createBookResponse.getId());
        ReportLog.getTest().pass("Book created with details " + createBookResponse.toString());

        String deleteEndPoint = String.format(EndPoints.DELETEBOOK, createBookResponse.getId());
        ReportLog.getTest().info("verifying delete Book api  with end point " + deleteEndPoint);

        response = RequestBase.delete(deleteEndPoint, RequestSpecFactory.getSpecWithAuth(UserDataContext.getAuthToken()), 200);
        DeleteBookResponse deleteBookResponse = response.as(DeleteBookResponse.class);
        Assert.assertEquals("Book deleted successfully", deleteBookResponse.getMessage());
        ReportLog.getTest().pass(String.format("Delete Books call is success for Bookid : %s, Book name: %s ", BookDataContext.getId(), BookDataContext.getName()));
    }


    @Test(priority = 10, description = "Verify deleted book returns 404", dependsOnMethods = "testDeleteBookById")
    public void testDeletedBookReturns404() {

        ReportLog.getTest().info(String.format("verifying delete book exists with id: %s ", BookDataContext.getId(), BookDataContext.getName()));
        String bookID = String.format(EndPoints.GETBOOK, BookDataContext.getId());
        Response getResponse = RequestBase.get(bookID, RequestSpecFactory.getSpecWithAuth(UserDataContext.getAuthToken()), 404);
        ReportLog.getTest().info(getResponse.asString());
        BookNotAvaliableResponse bookNotAvaliableResponse = getResponse.as(BookNotAvaliableResponse.class);

        Assert.assertEquals("Book not found", bookNotAvaliableResponse.getDetail());
        ReportLog.getTest().pass(String.format("Delete Books does not exists for Bookid : %s", BookDataContext.getId()));
    }


    @Test(priority = 11, description = "Verify non-existent book returns 404", dependsOnMethods = "testDeleteBookById")
    public void testNonExistentBookReturns404() {
        String bookID = String.format(EndPoints.GETBOOK, 999999999);

        Response response = RequestBase.get(bookID, RequestSpecFactory.getSpecWithAuth(UserDataContext.getAuthToken()), 404);

        System.out.println(response.asString());

        DetailsResponse createBookResponse = response.as(DetailsResponse.class);


        Assert.assertEquals("Book not found", createBookResponse.getDetail());

        ReportLog.getTest().pass("Book details verified through get request " + createBookResponse.toString());

    }


    @Test(priority = 12, description = "verify Update non-existent book returns 404", dependsOnMethods = "testDeleteBookById")
    public void testUpdateNonExistentBookReturns404() {
        CreateBookRequest updateBookRequest = new CreateBookRequest(BookDataContext.getName() + " name update", BookDataContext.getAuthor() + " author update", 1894 + 2, BookDataContext.getBookSummary() + " summary update");

        ReportLog.getTest().info("Updating book which doesnot exist. (deleted) details ->" + updateBookRequest.toString());


        String bookID = String.format(EndPoints.UPDATEBOOK, BookDataContext.getId());

        Response response = RequestBase.put(bookID, RequestSpecFactory.getSpecWithRequestBody(updateBookRequest, UserDataContext.getAuthToken()), 404);

        System.out.println(response.asString());
        DetailsResponse createBookResponse = response.as(DetailsResponse.class);


        Assert.assertEquals("Book not found", createBookResponse.getDetail());

        ReportLog.getTest().pass("Book not found with 404 when not present. server response :" + createBookResponse.getDetail());
    }


}
