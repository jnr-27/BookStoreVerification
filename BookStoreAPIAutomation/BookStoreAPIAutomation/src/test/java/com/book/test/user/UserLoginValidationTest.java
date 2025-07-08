package com.book.test.user;

import com.book.test.BaseTest;
import com.book.test.config.Configuration;
import com.book.test.contants.Constants;
import com.book.test.contants.EndPoints;
import com.book.test.data.EmailGenerator;
import com.book.dataprovider.UserDataProvider;
import com.book.test.model.DetailsResponse;
import com.book.test.model.ErrorResponse;
import com.book.test.model.user.UserSignUpRequest;
import com.book.report.ReportLog;
import com.book.test.req.RequestBase;
import com.book.test.req.RequestSpecFactory;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;


public class UserLoginValidationTest extends BaseTest {


    @Test(priority = 16, description = "Login with wrong password or invalid email should return 400", dependsOnGroups = "healthCheck")
    public void testLoginWithWrongPassword() {
        UserSignUpRequest userSignUpforNegativeScenarios = new UserSignUpRequest();
        userSignUpforNegativeScenarios.setEmail(EmailGenerator.generateBsEmail());
        userSignUpforNegativeScenarios.setPassword(Configuration.getConfiguraions().getConfigValue(Constants.PASSWORD));

        ReportLog.getTest().info(" Creating new user with details email id " + userSignUpforNegativeScenarios.getEmail());

        Response postResponse = RequestBase.post(EndPoints.SIGNUP, RequestSpecFactory.getSpecWithRequestBody(userSignUpforNegativeScenarios), 200);
        UserSignUpRequest userSignUp = new UserSignUpRequest();
        userSignUp.setEmail(userSignUpforNegativeScenarios.getEmail());
        userSignUp.setPassword(userSignUpforNegativeScenarios.getPassword() + "p");

        ReportLog.getTest().info(" User is logging in with email id " + userSignUp.getEmail() + " with wrong password");

        Response loginResponse = RequestBase.post(EndPoints.LOGIN, RequestSpecFactory.getSpecWithRequestBody(userSignUp), 400);
        DetailsResponse detailsResponse = loginResponse.as(DetailsResponse.class);

        Assert.assertEquals("Incorrect email or password", detailsResponse.getDetail());

        userSignUp = new UserSignUpRequest();
        userSignUp.setEmail(userSignUpforNegativeScenarios.getEmail() + "e");
        userSignUp.setPassword(userSignUpforNegativeScenarios.getPassword());

        ReportLog.getTest().info(" User is logging in with invalid email id " + userSignUp.getEmail());

        loginResponse = RequestBase.post(EndPoints.LOGIN, RequestSpecFactory.getSpecWithRequestBody(userSignUp), 400);
        detailsResponse = loginResponse.as(DetailsResponse.class);

        Assert.assertEquals("Incorrect email or password", detailsResponse.getDetail());


    }

    @Test(priority = 17,description = "Login with missing email or password should return 422", dataProvider = "invalidUserData", dataProviderClass = UserDataProvider.class, dependsOnGroups = "healthCheck")
    public void testLoginWithInvalidInputs(String email, String password, String scenario) {

        UserSignUpRequest userSignUp = new UserSignUpRequest();
        userSignUp.setEmail(email);
        userSignUp.setPassword(password);

        ReportLog.getTest().info("Testing scenario: " + scenario);

        Response postResponse = RequestBase.post(
                EndPoints.LOGIN,
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


}
