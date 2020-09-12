package com.ZomatoAPI.Utilities;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.response.Response;

import java.util.Map;

public class RestAPIUtility {

    public static Response getCall(String endpoint, Map<String, String> headers) {
        return RestAssured.given().headers(headers).log().everything().when().get(endpoint);
    }

    public static Response getCallwithQueryParams(String endpoint, Map<String, String> headers, Map<String, Object> queryParam) {
        return RestAssured.given().headers(headers).log().everything().when().queryParams(queryParam).get(endpoint);
    }
}


