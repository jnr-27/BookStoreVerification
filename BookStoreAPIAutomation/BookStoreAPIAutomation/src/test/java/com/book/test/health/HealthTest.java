package com.book.test.health;

import com.book.report.ReportLog;
import com.book.test.BaseTest;
import com.book.test.contants.EndPoints;
import com.book.test.model.HealthResponse;
import com.book.test.req.RequestBase;
import com.book.test.req.RequestSpecFactory;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;


public class HealthTest extends BaseTest {

    @Test(priority = 1, description = "Verify service health endpoint returns status 'up'", groups = "healthCheck")
    public void verifyServiceHealthStatus() {

        ReportLog.getTest().info("Checking Service health UP or DOWN");

        Response postResponse = RequestBase.get(EndPoints.HEALTH, RequestSpecFactory.getSpec(), 200);
        System.out.println(postResponse.asString());

        HealthResponse healthResponse = postResponse.as(HealthResponse.class);
        Assert.assertEquals("up", healthResponse.getStatus());
        ReportLog.getTest().pass("Service health is UP");
    }

}
