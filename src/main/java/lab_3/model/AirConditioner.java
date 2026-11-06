package lab_3.model;

public class AirConditioner extends Thermostat {
    public AirConditioner(int id, String location, double targetTemperature) {
        super(id, location, "°C", targetTemperature, 40.0);
    }
}