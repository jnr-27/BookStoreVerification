package com.book.test.req;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class RequestBase {

    public static Response get(String endPoint,RequestSpecification requestSpecification,int statusCode)
    {

        Response response = RestAssured.given()
                .spec(requestSpecification)
                .when()
                .get(endPoint)
                .then()
                .statusCode(statusCode)
                .extract().response();

        return response;

    }

    public static Response post(String endPoint,RequestSpecification requestSpecification,int statusCode)
    {

        Response response = RestAssured.given()
                .spec(requestSpecification)
                .when()
                .post(endPoint)
                .then()
                .statusCode(statusCode)
                .extract().response();

        return response;

    }
    public static Response put(String endPoint,RequestSpecification requestSpecification,int statusCode)
    {
        Response response = RestAssured.given()
                .spec(requestSpecification)
                .when()
                .put(endPoint)
                .then()
                .statusCode(statusCode)
                .extract().response();

        return response;

    }
    public static Response delete(String endPoint,RequestSpecification requestSpecification,int statusCode)
    {

        Response response = RestAssured.given()
                .spec(requestSpecification)
                .when()
                .delete(endPoint)
                .then()
                .statusCode(statusCode)
                .extract().response();

        return response;

    }
}
