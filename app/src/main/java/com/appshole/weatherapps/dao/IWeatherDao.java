package com.appshole.weatherapps.dao;




import com.appshole.weatherapps.model.Weather;

import org.json.JSONObject;

import java.util.ArrayList;



public interface IWeatherDao {


    ArrayList<Weather> GetActivitiesListFromJSONObject(JSONObject json) throws Exception;
}
