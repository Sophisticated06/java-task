package lab_3.model;

public class WirelessRadiatorThermostat extends Thermostat {
    public WirelessRadiatorThermostat(int id, String location, double targetTemperature) {
        super(id, location, "°C", targetTemperature, 30.0);
    }
}