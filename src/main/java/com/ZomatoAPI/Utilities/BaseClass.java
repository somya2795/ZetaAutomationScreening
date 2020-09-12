package com.ZomatoAPI.Utilities;

import org.testng.annotations.BeforeSuite;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class BaseClass {

    public static String baseUrl;
    public static String user_key;
    public static Properties prop = null;
    public static Map<String, String> zomatoHeaders = null;

    public BaseClass() {
        try {
            prop = new Properties();
            FileInputStream ip = new FileInputStream(System.getProperty("user.dir") + "/config.properties");
            prop.load(ip);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


        @BeforeSuite()
    public void setup() throws FileNotFoundException {
        baseUrl = prop.getProperty("BASE_URL");
        user_key=prop.getProperty("USER_KEY");
        initialise_Zomato_Headers();
    }

    public void initialise_Zomato_Headers() {
        zomatoHeaders = new HashMap<String, String>();
        zomatoHeaders.put("Accept", "application/json");
        zomatoHeaders.put("user-key", user_key);
        System.out.println(zomatoHeaders);

    }
}
