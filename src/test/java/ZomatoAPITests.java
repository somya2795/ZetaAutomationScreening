import com.ZomatoAPI.Utilities.BaseClass;
import com.ZomatoAPI.Utilities.Zomato_Common_GetAPIs;
import com.mongodb.util.JSON;
import io.restassured.response.Response;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.lang.reflect.Array;
import java.math.BigInteger;
import java.util.ArrayList;

public class ZomatoAPITests extends BaseClass {

    public static int categoryId;
    public static int cityIdofBangalore;
    public static int establishmentId;
    public static Response response = null;
    public static double lat = 12.9352;
    public static double lon = 77.6245;
    public static ArrayList<Integer> cuisinesInCity = new ArrayList<>();
    public static int res_id;
    public static int countofResUnderACollection ;
    public static int collectionId;
    Zomato_Common_GetAPIs zomatoCommonGetAPIs = new Zomato_Common_GetAPIs();


    @Test
    public void getCategoryIdWithInvalidUserKEy() {
        response = zomatoCommonGetAPIs.getListOfCategories("5466754324");
        Assert.assertEquals(response.statusCode(), 403);

    }
    // validating get CategoryId API and setting category id for Nightlife
    @Test
    public void getCategoryId() {
        response = zomatoCommonGetAPIs.getListOfCategories(user_key);
        Assert.assertEquals(response.statusCode(), 200);
        JSONObject obj = new JSONObject(response.asString());
        JSONArray categories = obj.getJSONArray("categories");
        for (int i = 0; i < categories.length(); i++) {
            String name = categories.getJSONObject(i).getJSONObject("categories").getString("name");
            if (name.equalsIgnoreCase("Nightlife")) {
                System.out.println("entered");
                categoryId = categories.getJSONObject(i).getJSONObject("categories").getInt("id");
                System.out.println("id = " + categoryId);
            } else
                continue;
        }
    }

    //validating get CityId For Delhi and validate against all names in the response
    @Test
    public void getCityId() {
        response = zomatoCommonGetAPIs.getCityId("Delhi");
        Assert.assertEquals(response.statusCode(), 200);
        JSONObject res = new JSONObject(response.asString());
        JSONArray location_suggestions = res.getJSONArray("location_suggestions");
        for (int i = 0; i < location_suggestions.length(); i++) {
            String name = location_suggestions.getJSONObject(i).getString("name");
            Assert.assertTrue(name.contains("Delhi"));
            System.out.println("passed");
        }
    }

    //validating get city id where name = bengaluru
    @Test
    public void getCityIdForBangalore() {
        response = zomatoCommonGetAPIs.getCityId("bengaluru");
        Assert.assertEquals(response.statusCode(), 200);
        JSONObject res = new JSONObject(response.asString());
        JSONArray location_suggestions = res.getJSONArray("location_suggestions");
        cityIdofBangalore = location_suggestions.getJSONObject(0).getInt("id");
        System.out.println(cityIdofBangalore);
        Assert.assertNotNull(cityIdofBangalore);
    }

    // validating get establishments api where city id = 4 and also intialising establishmentId for microbrewery
    @Test
    public void getEstablishmentsForBangalore() {
        response = zomatoCommonGetAPIs.getEstablishmentsForCity(cityIdofBangalore);
        Assert.assertEquals(response.statusCode(), 200);
        JSONObject obj = new JSONObject(response.asString());
        JSONArray establishments = obj.getJSONArray("establishments");
        for (int i = 0; i < establishments.length(); i++) {
            String name = establishments.getJSONObject(i).getJSONObject("establishment").getString("name");
            if (name.equalsIgnoreCase("microbrewery"))
                establishmentId = establishments.getJSONObject(i).getJSONObject("establishment").getInt("id");
            else
                continue;
        }
    }

    // validating search API for all microbrewery in bangalore which has nightlife sorted with rating.
    @Test
    public void getListOfAllMicrobreweryWithNightlifeinBangalore() {
        response = zomatoCommonGetAPIs.searchRestaurants(cityIdofBangalore, "city", establishmentId, categoryId, "rating");
        Assert.assertEquals(response.statusCode(), 200);

    }

    @Test
    public void getListOfRestServingCuisineInACity() {
        response = zomatoCommonGetAPIs.getCuisinesInACity(cityIdofBangalore);
        Assert.assertEquals(response.statusCode(), 200);
        JSONObject obj = new JSONObject(response.asString());
        JSONArray cuisines = obj.getJSONArray("cuisines");
        for (int i = 0; i < cuisines.length(); i++) {
            String cuisineName = cuisines.getJSONObject(i).getJSONObject("cuisine").getString("cuisine_name");
            if (cuisineName.equalsIgnoreCase("Italian") || cuisineName.equalsIgnoreCase("chinese"))
                cuisinesInCity.add(cuisines.getJSONObject(i).getJSONObject("cuisine").getInt("cuisine_id"));
            else
                continue;

        }

    }

    @Test
    public void getListofCafesinBangaloreProvidingChineseAndItalianCuisine() {
        response = zomatoCommonGetAPIs.getEstablishmentsForCity(cityIdofBangalore);
        Assert.assertEquals(response.statusCode(), 200);
        JSONObject obj = new JSONObject(response.asString());
        JSONArray establishments = obj.getJSONArray("establishments");
        for (int i = 0; i < establishments.length(); i++) {
            String name = establishments.getJSONObject(i).getJSONObject("establishment").getString("name");
            if (name.equalsIgnoreCase("cafe"))
                establishmentId = establishments.getJSONObject(i).getJSONObject("establishment").getInt("id");
            else
                continue;
        }

        response = zomatoCommonGetAPIs.searchRestaurants(cityIdofBangalore, "city", cuisinesInCity, establishmentId, "rating");
        Assert.assertEquals(response.statusCode(), 200);

    }

    @Test
    public void getTopRatedRestaurantsNearMe() {
        response = zomatoCommonGetAPIs.getDataDetailBasedOnCoordinates(lat, lon);
        Assert.assertEquals(response.statusCode(), 200);
        double aggregate_rating = 4.5;
        ArrayList<String> nameofRestaurant = new ArrayList<>();
        JSONObject obj = new JSONObject(response.asString());
        JSONArray nearby_restaurants = obj.getJSONArray("nearby_restaurants");
        for (int i = 0; i < nearby_restaurants.length(); i++) {
            double rating = Double.parseDouble(nearby_restaurants.getJSONObject(i).getJSONObject("restaurant").getJSONObject("user_rating").getString("aggregate_rating"));
            if (rating >= aggregate_rating) {
                nameofRestaurant.add(nearby_restaurants.getJSONObject(i).getJSONObject("restaurant").getString("name"));
                res_id = nearby_restaurants.getJSONObject(i).getJSONObject("restaurant").getInt("id");
            }
            else
                continue;
        }
        System.out.println("top rated = "+res_id);


    }


    @Test
    public void getMenuForTopRatedRes()
    {
        response = zomatoCommonGetAPIs.getMenuListForRestaurant(res_id);
        System.out.println(response.asString());
        Assert.assertEquals(response.statusCode(), 200);

    }

    @Test
    public void getResDetailsForTopRatedRes()
    {
        response = zomatoCommonGetAPIs.getRestaurantDetails(res_id);
        Assert.assertEquals(response.statusCode(), 200);

    }

    @Test
    public void getFiveLatestReviewsforARest()
    {
        response = zomatoCommonGetAPIs.getReviewsForARestaurant(res_id,0, 0);
        Assert.assertEquals(response.statusCode(),200);
        JSONObject obj = new JSONObject(response.asString());
        System.out.println(response.asString());
        JSONArray user_reviews = obj.getJSONArray("user_reviews");
        Assert.assertEquals(user_reviews.length(),5);
    }

    @Test
    public void getReviewsBasedOnStartOffsetAndCount()
    {
        response = zomatoCommonGetAPIs.getReviewsForARestaurant(res_id,6, 8);
        Assert.assertEquals(response.statusCode(),200);
        JSONObject obj = new JSONObject(response.asString());
        System.out.println(response.asString());
        int reviews_start=obj.getInt("reviews_start");
        Assert.assertEquals(reviews_start,6);
        int reviews_shown = obj.getInt("reviews_shown");
        Assert.assertEquals(reviews_shown,8);
        JSONArray user_reviews = obj.getJSONArray("user_reviews");
        Assert.assertEquals(user_reviews.length(),8);
    }


    @Test
    public void verifyDailyMenuForInvalidResId()
    {
         res_id = 56553523;
        response = zomatoCommonGetAPIs.getMenuListForRestaurant(res_id);
        Assert.assertEquals(response.statusCode(), 400);
        Assert.assertTrue(response.asString().contains("No Daily Menu Available"));
    }

    @Test
    public void verifyInvalidLatAndLonForGeocode()
    {
        response = zomatoCommonGetAPIs.getDataDetailBasedOnCoordinates(12.442343543678676574635657869857463, 14.45675869768754635236547658);
        Assert.assertEquals(response.statusCode(), 400);
        Assert.assertTrue(response.asString().contains("Invalid coordinates"));
    }


    @Test
    public void verifyGetRestaurantDetailsWithInvalidResId()
    {
        response = zomatoCommonGetAPIs.getRestaurantDetails(res_id);
        Assert.assertEquals(response.statusCode(),400);
    }

    @Test
    public void getCountofRestaurantUnderaCollection()
    {
        String title = "Contactless";
        response = zomatoCommonGetAPIs.getRestaurantCountforCollection(cityIdofBangalore);
        Assert.assertEquals(response.statusCode(),200);
        JSONObject obj=new JSONObject(response.asString());
        JSONArray collections = obj.getJSONArray("collections");
        for(int i=0;i<collections.length();i++)
        {
            if(collections.getJSONObject(i).getJSONObject("collection").getString("title").contains(title)) {
                countofResUnderACollection = collections.getJSONObject(i).getJSONObject("collection").getInt("res_count");
                collectionId = collections.getJSONObject(i).getJSONObject("collection").getInt("collection_id");
            }
            else
                continue;
        }
    }

    @Test
    public void verifyCountofResUnderACollection()
    {
       response = zomatoCommonGetAPIs.searchRestaurants(cityIdofBangalore,"city",collectionId);
       Assert.assertEquals(response.statusCode(),200);
       JSONObject obj=new JSONObject(response.asString());
       int res_found = obj.getInt("results_found");
       Assert.assertEquals(res_found,countofResUnderACollection);
    }









}





