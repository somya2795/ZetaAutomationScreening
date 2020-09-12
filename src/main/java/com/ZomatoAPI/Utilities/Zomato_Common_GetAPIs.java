package com.ZomatoAPI.Utilities;

import io.restassured.RestAssured;
import io.restassured.response.Response;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Zomato_Common_GetAPIs extends BaseClass {

    public Response getListOfCategories(String userkey) {
        Map<String, String> headers = new HashMap<>();
        headers.put("Accept", "application/json");
        headers.put("user-key", userkey);
        System.out.println("get calls = " + baseUrl);
        String url = baseUrl + APIPathConfig.APIPath.Get_List_of_Categories;
        Response response = RestAPIUtility.getCall(url, headers);
        return response;
    }

    public Response getCityId(String cityName) {
        Map<String, String> headers = new HashMap<>();
        headers.put("Accept", "application/json");
        headers.put("user-key", user_key);
        Map<String, Object> params = new HashMap<>();
        params.put("q", cityName);
        String url = baseUrl + APIPathConfig.APIPath.Get_City_Id;
        Response response = RestAPIUtility.getCallwithQueryParams(url, headers, params);
        return response;

    }

    public Response getEstablishmentsForCity(int cityId) {
        Map<String, String> headers = new HashMap<>();
        headers.put("Accept", "application/json");
        headers.put("user-key", user_key);
        Map<String, Object> params = new HashMap<>();
        params.put("city_id", cityId);
        String url = baseUrl + APIPathConfig.APIPath.Get_Resturant_Type_In_City;
        System.out.println(url);
        Response response = RestAPIUtility.getCallwithQueryParams(url, headers, params);
        return response;
    }

    public Response getCuisinesInACity(int cityId)
    {
        Map<String, String> headers = new HashMap<>();
        headers.put("Accept", "application/json");
        headers.put("user-key", user_key);
        Map<String, Object> params = new HashMap<>();
        params.put("city_id", cityId);
        String url = baseUrl+APIPathConfig.APIPath.Get_List_of_all_Cuisines_in_a_City;
        Response response = RestAPIUtility.getCallwithQueryParams(url, headers, params);
        return response;
    }

    public Response getDataDetailBasedOnCoordinates(Double latitude,Double lonngitude)
    {
        Map<String, String> headers = new HashMap<>();
        headers.put("Accept", "application/json");
        headers.put("user-key", user_key);
        Map<String, Object> params = new HashMap<>();
        params.put("lat", latitude);
        params.put("lon",lonngitude);
        String url = baseUrl+APIPathConfig.APIPath.Get_Data_Based_On_Geocode;
        Response response = RestAPIUtility.getCallwithQueryParams(url, headers, params);
        return response;
    }



    public Response getRestaurantCountforCollection(int city_id)
    {
        Map<String, String> headers = new HashMap<>();
        headers.put("Accept", "application/json");
        headers.put("user-key", user_key);
        Map<String, Object> params = new HashMap<>();
        params.put("city_id", city_id);
        String url = baseUrl+APIPathConfig.APIPath.Get_Zomato_Collections_In_A_City;
        Response response = RestAPIUtility.getCallwithQueryParams(url,headers,params);
        return response;
    }


}
