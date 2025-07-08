package com.book.test;

import com.book.report.ReportLog;
import com.book.test.config.Configuration;
import com.book.test.contants.Constants;
import io.restassured.RestAssured;
import org.apache.commons.lang3.StringUtils;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Listeners;

@Listeners(ReportLog.class)
public class BaseTest {


    @BeforeSuite
    public void init() {
        String env = System.getProperty("env", System.getenv("env"));
        if (StringUtils.isBlank(env)) {
            env = Constants.ENVQA;
        }
        String baseUrl = null;
        if (env.equalsIgnoreCase(Constants.ENVQA)) {
            baseUrl = Configuration.getConfiguraions().getConfigValue(Constants.ULR);
        } else if (env.equalsIgnoreCase(Constants.ENVPROD)) {
            baseUrl = Configuration.getConfiguraions().getConfigValue(Constants.PROD_ULR);
        }
        RestAssured.baseURI = baseUrl;

    }

    @AfterSuite
    public void tearDown() {

    }
}
