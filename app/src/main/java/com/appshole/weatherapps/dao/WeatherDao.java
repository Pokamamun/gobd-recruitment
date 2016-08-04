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
    public ArrayList<Weather> GetActivitiesListFromJSONObject(JSONObject jsonOBject) throws Exception {


        ArrayList<Weather> clientInfoList = new ArrayList<Weather>();
        JSONObject json;
        Weather clientInfo = null;
        try {
            JSONArray jArray = jsonOBject.getJSONArray("weather");

            for (int i = 0; i < jArray.length(); i++) {
                clientInfo = new Weather();

                json = jArray.getJSONObject(i);

                try {
                    clientInfo.setWeatherDescription(json.getString("description"));
                } catch (Exception e) {

                }


                try {
                    clientInfo.setCity(jsonOBject.getString("name"));
                } catch (Exception e) {

                }
                JSONObject jsonObjectMain = null;
                try {
                     jsonObjectMain = jsonOBject.getJSONObject("main");
                } catch (Exception e) {

                }


                try {
                    clientInfo.setHumidityValue(jsonObjectMain.getString("humidity"));
                } catch (Exception e) {

                }

                try {
                    clientInfo.setTempVal(jsonObjectMain.getLong("temp"));
                } catch (Exception e) {

                }

                try {
                    clientInfo.setTodayDate(getTodayDateInStringFormat());
                } catch (Exception e) {

                }


                try {
                    clientInfo.setWeatherTemp(jsonObjectMain.getString("temp"));
                } catch (Exception e) {

                }

                JSONObject jsonObjectWind = null;
                try {
                    jsonObjectWind = jsonOBject.getJSONObject("wind");
                } catch (Exception e) {

                }


                try {
                    clientInfo.setWindSpeed(jsonObjectWind.getString("speed"));
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
