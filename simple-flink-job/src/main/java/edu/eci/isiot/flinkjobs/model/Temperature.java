package edu.eci.isiot.flinkjobs.model;

public class Temperature {
    private String id;
    private int degrees;

    public Temperature(String id, int degrees) {
        this.id = id;
        this.degrees = degrees;
    }

    public Temperature() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getDegrees() {
        return degrees;
    }

    public void setDegrees(int degrees) {
        this.degrees = degrees;
    }


    @Override
    public String toString() {
        return "Temperature{" +
                "id='" + id + '\'' +
                ", degrees=" + degrees +
                '}';
    }
}
