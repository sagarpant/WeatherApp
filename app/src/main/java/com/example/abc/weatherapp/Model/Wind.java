package com.example.abc.weatherapp.Model;

public class Wind {

    private double speed, deg;

    public Wind(double speed, double degree) {
        this.speed = speed;
        this.deg = degree;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public double getDeg() {
        return deg;
    }

    public void setDeg(double degree) {
        this.deg = degree;
    }
}
