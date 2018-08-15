package com.example.abc.weatherapp.Model;

public class Sys {

    private int type, id;
    private double message;
    private String country;
    private double Sunrise, Sunset;

    public Sys(int type, int id, double message, String country, double sunrise, double sunset) {
        this.type = type;
        this.id = id;
        this.message = message;
        this.country = country;
        Sunrise = sunrise;
        Sunset = sunset;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getMessage() {
        return message;
    }

    public void setMessage(double message) {
        this.message = message;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String countryName) {
        this.country = countryName;
    }

    public double getSunrise() {
        return Sunrise;
    }

    public void setSunrise(double sunrise) {
        Sunrise = sunrise;
    }

    public double getSunset() {
        return Sunset;
    }

    public void setSunset(double sunset) {
        Sunset = sunset;
    }
}
