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

    public Response searchRestaurants(int entityId, String entityType, int establishment_type, int categoryId, String sortFilter) {
        Map<String, String> headers = new HashMap<>();
        headers.put("Accept", "application/json");
        headers.put("user-key", user_key);
        Map<String, Object> params = new HashMap<>();
        params.put("entity_type", entityType);
        params.put("entity_id", entityId);
        params.put("establishment_type", establishment_type);
        params.put("category", categoryId);
        params.put("sort", sortFilter);
        String url = baseUrl + APIPathConfig.APIPath.Search_for_Restraunts;
        Response response = RestAPIUtility.getCallwithQueryParams(url, headers, params);
        return response;

    }

    public Response searchRestaurants(int entityId, String entityType, ArrayList<Integer> cuisineId, int establishment_type, String sortFilter) {
        Map<String, String> headers = new HashMap<>();
        headers.put("Accept", "application/json");
        headers.put("user-key", user_key);
        Map<String, Object> params = new HashMap<>();
        params.put("entity_type", entityType);
        params.put("entity_id", entityId);
        params.put("cuisines",Integer.toString(cuisineId.get(0))+","+Integer.toString(cuisineId.get(1)));
        params.put("establishment_type", establishment_type);
        params.put("sort", sortFilter);
        String url = baseUrl + APIPathConfig.APIPath.Search_for_Restraunts;
        Response response = RestAPIUtility.getCallwithQueryParams(url, headers, params);
        return response;

    }

    public Response searchRestaurants(int entityId, String entityType,int collection_id)
    {
        Map<String, String> headers = new HashMap<>();
        headers.put("Accept", "application/json");
        headers.put("user-key", user_key);
        Map<String, Object> params = new HashMap<>();
        params.put("entity_type", entityType);
        params.put("entity_id", entityId);
        params.put("collection_id",collection_id);
        String url = baseUrl + APIPathConfig.APIPath.Search_for_Restraunts;
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

    public Response getMenuListForRestaurant(int res_id)
    {
        Map<String, String> headers = new HashMap<>();
        headers.put("Accept", "application/json");
        headers.put("user-key", user_key);
        Map<String, Object> params = new HashMap<>();
        params.put("res_id", res_id);
        String url = baseUrl+APIPathConfig.APIPath.Get_Menu_of_a_Restaraunt;
        Response response = RestAPIUtility.getCallwithQueryParams(url, headers, params);
        return response;
    }

    public Response getRestaurantDetails(int res_id){
        Map<String, String> headers = new HashMap<>();
        headers.put("Accept", "application/json");
        headers.put("user-key", user_key);
        Map<String, Object> params = new HashMap<>();
        params.put("res_id", res_id);
        String url = baseUrl+APIPathConfig.APIPath.Get_Restaraunt;
        Response response = RestAPIUtility.getCallwithQueryParams(url,headers,params);
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

    public Response getReviewsForARestaurant(int resId,int startOffset,int countOfReviews)
    {
        Map<String, String> headers = new HashMap<>();
        headers.put("Accept", "application/json");
        headers.put("user-key", user_key);
        Map<String, Object> params = new HashMap<>();
        params.put("res_id", resId);
        if(startOffset>0)
            params.put("start",String.valueOf(startOffset));
        if(countOfReviews>0)
            params.put("count",String.valueOf(countOfReviews));
        String url = baseUrl+APIPathConfig.APIPath.Get_Reviews_For_Restaurant;
        Response response = RestAPIUtility.getCallwithQueryParams(url,headers,params);
        return response;


    }
}
