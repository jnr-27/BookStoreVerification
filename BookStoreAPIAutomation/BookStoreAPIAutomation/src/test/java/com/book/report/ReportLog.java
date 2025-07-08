package com.book.report;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ReportLog implements ITestListener {

    private static ExtentReports extent;
    private static ThreadLocal<ExtentTest> test =new ThreadLocal<>();

    public static ExtentTest getTest(){
        return test.get();
    }
    @Override
    public void onStart(ITestContext context) {
        ExtentSparkReporter report=new ExtentSparkReporter("test-output/ExtentReport.html");
        report.config().setReportName("Bookstore API Automation report");
        report.config().setDocumentTitle("Bookstore API Test");

        extent =new ExtentReports();
        extent.attachReporter(report);
        extent.setSystemInfo("Tester", "Nagaraju");

    }

    @Override
    public void onTestStart(ITestResult result) {
        ExtentTest extentTest = extent.createTest(result.getMethod().getDescription());
        test.set(extentTest);
    }

    @Override
    public void onTestFailure(ITestResult result) {
        test.get().fail("Test failed: " + result.getThrowable());
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        test.get().skip("Test skipped: " + result.getThrowable());
    }

    @Override
    public void onFinish(ITestContext context) {
        extent.flush();
    }
}
