package com.appshole.weatherapps.model;


public class Weather {


    private String city;
    private String todayDate;
    private long tempVal;
    private String weatherTemp;
    private String weatherDescription;
    private String windSpeed;
    private String humidityValue;



    public Weather() {
        this.city = "";
        this.todayDate = "";
        this.tempVal = 0;
        this.weatherTemp = "";
        this.weatherDescription = "";
        this.windSpeed = "";
        this.humidityValue = "";
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getTodayDate() {
        return todayDate;
    }

    public void setTodayDate(String todayDate) {
        this.todayDate = todayDate;
    }

    public long getTempVal() {
        return tempVal;
    }

    public void setTempVal(long tempVal) {
        this.tempVal = tempVal;
    }

    public String getWeatherTemp() {
        return weatherTemp;
    }

    public void setWeatherTemp(String weatherTemp) {
        this.weatherTemp = weatherTemp;
    }

    public String getWeatherDescription() {
        return weatherDescription;
    }

    public void setWeatherDescription(String weatherDescription) {
        this.weatherDescription = weatherDescription;
    }

    public String getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(String windSpeed) {
        this.windSpeed = windSpeed;
    }

    public String getHumidityValue() {
        return humidityValue;
    }

    public void setHumidityValue(String humidityValue) {
        this.humidityValue = humidityValue;
    }
}
