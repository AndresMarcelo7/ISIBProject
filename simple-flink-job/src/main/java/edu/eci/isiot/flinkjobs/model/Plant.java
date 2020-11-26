package edu.eci.isiot.flinkjobs.model;

import java.util.HashMap;

public class Plant {
    private int temp, light, humidity, ground, proximity;
    private HashMap<String,Integer> affected;

    public Plant(int temp, int light, int humidity, int ground, int proximity) {
        this.temp = temp;
        this.light = light;
        this.humidity = humidity;
        this.ground = ground;
        this.proximity = proximity;
        affected = new HashMap<>();
    }

    public Plant() {
    }

    public int getTemp() {
        return temp;
    }

    public void setTemp(int temp) {
        this.temp = temp;
    }

    public int getLight() {
        return light;
    }

    public void setLight(int light) {
        this.light = light;
    }

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    public int getGround() {
        return ground;
    }

    public void setGround(int ground) {
        this.ground = ground;
    }

    public int getProximity() {
        return proximity;
    }

    public void setProximity(int proximity) {
        this.proximity = proximity;
    }

    public HashMap<String, Integer> getAffected() {
        return affected;
    }

    public void setAffected(HashMap<String, Integer> affected) {
        this.affected = affected;
    }

    @Override
    public String toString() {
        return "Plant{" +
                "temp=" + temp +
                ", light=" + light +
                ", humidity=" + humidity +
                ", ground=" + ground +
                ", proximity=" + proximity +
                ", affected=" + affected.keySet() +
                '}';
    }
}
