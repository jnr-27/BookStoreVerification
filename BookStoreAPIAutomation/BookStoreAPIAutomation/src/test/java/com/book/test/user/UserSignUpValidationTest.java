package com.book.test.user;

import com.book.dataprovider.UserDataProvider;
import com.book.report.ReportLog;
import com.book.test.BaseTest;
import com.book.test.config.Configuration;
import com.book.test.contants.Constants;
import com.book.test.contants.EndPoints;
import com.book.test.context.UserDataContext;
import com.book.test.data.EmailGenerator;
import com.book.test.model.DetailsResponse;
import com.book.test.model.ErrorResponse;
import com.book.test.model.user.UserSignUpRequest;
import com.book.test.req.RequestBase;
import com.book.test.req.RequestSpecFactory;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

public class UserSignUpValidationTest extends BaseTest {
    @Test(priority = 13,description = "create user with existing email should return 400", dependsOnGroups = "healthCheck")
    public void testCreateUserWithAlreadyRegisteredEmail() {

        UserDataContext.setEmail(EmailGenerator.generateBsEmail());
        UserDataContext.setPassword(Configuration.getConfiguraions().getConfigValue(Constants.PASSWORD));

        System.out.println(UserDataContext.getEmail());
        System.out.println(UserDataContext.getPassword());

        UserSignUpRequest userSignUp = new UserSignUpRequest();
        userSignUp.setEmail(UserDataContext.getEmail());
        userSignUp.setPassword(UserDataContext.getPassword());

        ReportLog.getTest().info(" Creating new user with details email id " + userSignUp.getEmail());

        Response postResponse = RequestBase.post(EndPoints.SIGNUP, RequestSpecFactory.getSpecWithRequestBody(userSignUp), 200);
        System.out.println(postResponse.asString());

        ReportLog.getTest().info(" Creating already existing user with details email id " + userSignUp.getEmail());

        postResponse = RequestBase.post(EndPoints.SIGNUP, RequestSpecFactory.getSpecWithRequestBody(userSignUp), 400);

        DetailsResponse userSignUpResponse = postResponse.as(DetailsResponse.class);

        Assert.assertEquals("Email already registered", userSignUpResponse.getDetail());
    }

    @Test(priority = 14,description = "Create user with invalid email or password should return 400", dataProvider = "invalidUserData", dataProviderClass = UserDataProvider.class, dependsOnGroups = "healthCheck")
    public void testCreateUserWithInvalidInputs(String email, String password, String scenario) {

        UserSignUpRequest userSignUp = new UserSignUpRequest();
        userSignUp.setEmail(email);
        userSignUp.setPassword(password);

        ReportLog.getTest().info("Testing scenario: " + scenario);

        Response postResponse = RequestBase.post(
                EndPoints.SIGNUP,
                RequestSpecFactory.getSpecWithRequestBody(userSignUp),
                422
        );

        System.out.println(postResponse.asString());

        ErrorResponse errorResponse = postResponse.as(ErrorResponse.class);
        Assert.assertEquals(errorResponse.getDetail().size(), 1);
        Assert.assertEquals(errorResponse.getDetail().get(0).getType(), "missing");
        Assert.assertEquals(errorResponse.getDetail().get(0).getMsg(), "Field required");
        Assert.assertEquals(errorResponse.getDetail().get(0).getLoc().toString(), "[body]");
    }

    @Test(priority = 15,description = "Create user with missing fields or body should return 400", dependsOnGroups = "healthCheck")
    public void testCreateUserWithMissingFields() {

        //currently returning 422 when the full body is missing. should give this even when any field also missing
        //for {}, {"email":"test@gmail.com"} etc. but it is giving 500

        ReportLog.getTest().info("Creating Missing body and fields");

        Response postResponse = RequestBase.post(EndPoints.SIGNUP, RequestSpecFactory.getSpecWithRequestBody(""), 422);

        System.out.println(postResponse.asString());
        ErrorResponse errorResponse = postResponse.as(ErrorResponse.class);
        Assert.assertEquals(1, errorResponse.getDetail().size());
        Assert.assertEquals("missing", errorResponse.getDetail().get(0).getType());
        Assert.assertEquals("Field required", errorResponse.getDetail().get(0).getMsg());
        Assert.assertEquals("[body]", errorResponse.getDetail().get(0).getLoc().toString());
    }


}
