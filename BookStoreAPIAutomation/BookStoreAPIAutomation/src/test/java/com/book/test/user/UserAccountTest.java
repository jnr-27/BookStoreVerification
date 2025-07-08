package com.book.test.user;

import com.book.report.ReportLog;
import com.book.test.BaseTest;
import com.book.test.config.Configuration;
import com.book.test.contants.Constants;
import com.book.test.contants.EndPoints;
import com.book.test.context.UserDataContext;
import com.book.test.data.EmailGenerator;
import com.book.test.model.user.UserAuthResponse;
import com.book.test.model.user.UserSignUpRequest;
import com.book.test.model.user.UserSignUpResponse;
import com.book.test.req.RequestBase;
import com.book.test.req.RequestSpecFactory;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;


public class UserAccountTest extends BaseTest {

    @Test(priority = 2, description = "Create user with valid data", dependsOnGroups = "healthCheck", groups = "userAccount")
    public void testCreateUserWithValidCredentials() {
        UserDataContext.setEmail(EmailGenerator.generateBsEmail());
        UserDataContext.setPassword(Configuration.getConfiguraions().getConfigValue(Constants.PASSWORD));

        System.out.println(UserDataContext.getEmail());

        UserSignUpRequest userSignUp = new UserSignUpRequest();
        userSignUp.setEmail(UserDataContext.getEmail());
        userSignUp.setPassword(UserDataContext.getPassword());

        ReportLog.getTest().info("Creating new user with details email id " + userSignUp.getEmail());

        Response postResponse = RequestBase.post(EndPoints.SIGNUP, RequestSpecFactory.getSpecWithRequestBody(userSignUp), 200);
        System.out.println(postResponse.asString());

        UserSignUpResponse userSignUpResponse = postResponse.as(UserSignUpResponse.class);
        Assert.assertEquals("User created successfully", userSignUpResponse.getMessage());
        ReportLog.getTest().pass("User created with emailid " + userSignUp.getEmail());
    }

    @Test(priority = 3, description = "Login user and get token", dependsOnMethods = "testCreateUserWithValidCredentials", groups = "userAccount")
    public void testLoginUserWithValidCredentials() {

        UserSignUpRequest userSignUp = new UserSignUpRequest();
        userSignUp.setEmail(UserDataContext.getEmail());
        userSignUp.setPassword(UserDataContext.getPassword());

        ReportLog.getTest().info("User is logging in with email id " + userSignUp.getEmail());

        Response postResponse = RequestBase.post(EndPoints.LOGIN, RequestSpecFactory.getSpecWithRequestBody(userSignUp), 200);


        UserAuthResponse userAuthResponse = postResponse.as(UserAuthResponse.class);
        Assert.assertNotNull(userAuthResponse.getAccessToken());
        Assert.assertEquals("bearer", userAuthResponse.getTokenType());
        UserDataContext.setAuthToken(userAuthResponse.getAccessToken());

        ReportLog.getTest().pass("User is logged in and token is generated successfully" + userSignUp.getEmail());
    }

}
