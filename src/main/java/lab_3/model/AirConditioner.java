package lab_3.model;

public class AirConditioner extends Thermostat {
    private static final long serialVersionUID = 1L;
    public AirConditioner(int id, String location, double targetTemperature) {
        super(id, location, "°C", targetTemperature, 40.0);
    }
}