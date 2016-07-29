package com.appshole.weatherapps.dao;





import com.appshole.weatherapps.model.Weather;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;


public class WeatherDao implements IWeatherDao {


    @Override
    public ArrayList<Weather> GetActivitiesListFromJSONObject(JSONObject json1) throws Exception {


        ArrayList<Weather> clientInfoList = new ArrayList<Weather>();
        JSONObject json;
        Weather clientInfo = null;
        try {
            JSONArray jArray = json1.getJSONArray("weather");

            for (int i = 0; i < jArray.length(); i++) {
                clientInfo = new Weather();

                json = jArray.getJSONObject(i);

                try {
                    clientInfo.setWeatherDescription(json.getString("description"));
                } catch (Exception e) {

                }


                try {
                    clientInfo.setCity(json1.getString("name"));
                } catch (Exception e) {

                }
                JSONObject jsonObject = null;
                try {
                     jsonObject = json1.getJSONObject("main");
                } catch (Exception e) {

                }


                try {
                    clientInfo.setHumidityValue(jsonObject.getString("humidity"));
                } catch (Exception e) {

                }

                try {
                    clientInfo.setTempVal(jsonObject.getLong("temp"));
                } catch (Exception e) {

                }

                try {
                    clientInfo.setTodayDate(getTodayDateInStringFormat());
                } catch (Exception e) {

                }


                try {
                    clientInfo.setWeatherTemp(jsonObject.getString("temp"));
                } catch (Exception e) {

                }

                JSONObject jsonObject1 = null;
                try {
                     jsonObject1 = json1.getJSONObject("wind");
                } catch (Exception e) {

                }


                try {
                    clientInfo.setWindSpeed(jsonObject1.getString("speed"));
                } catch (Exception e) {

                }

                try {

                } catch (Exception e) {

                }


                clientInfoList.add(clientInfo);
            }
        } catch (JSONException ex) {
            throw new Exception(ex.getMessage());
        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        }

        return clientInfoList;

    }

    private String getTodayDateInStringFormat() {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("E, d MMMM", Locale.getDefault());
        return df.format(c.getTime());
    }

}
