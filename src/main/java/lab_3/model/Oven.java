package lab_3.model;

public class Oven extends Thermostat {
    public Oven(int id, String location, double targetTemperature) {
        super(id, location, "°C", targetTemperature, 300.0);
    }
}