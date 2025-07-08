package com.book.test.req;

import com.book.test.config.Configuration;
import com.book.test.contants.Constants;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

public class RequestSpecFactory {



    public static RequestSpecification getSpecWithAuth(String token)
    {
      return new RequestSpecBuilder()
               .setBaseUri((Configuration.getConfiguraions().getConfigValue (Constants.ULR)))
               .setContentType(ContentType.JSON)
               .addHeader("Authorization", "Bearer " + token)
               .addHeader("accept", "application/json")
               .build();

    }
    public static RequestSpecification getSpecWithRequestBody(Object reqObject)
    {
        return new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .setBody(reqObject)
                .build();

    }

    public static RequestSpecification getSpec()
    {
        return new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .build();

    }

    public static RequestSpecification getSpecWithRequestBody(Object reqObject,String token )
    {
        return new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .setBody(reqObject)
                .addHeader("Authorization", "Bearer " + token)
                .addHeader("accept", "application/json")
                .build();

    }
}
