package lab_3.model;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.Objects;

public abstract class Thermostat implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;
    private String location;
    private String unit;
    private double currentTemperature;
    protected double targetTemperature;
    protected double maxLimitTemperature;

    public Thermostat(int id, String location, String unit, double targetTemperature, double maxLimitTemperature) {
        this.id = id;
        this.location = Objects.requireNonNullElse(location, "Не указана");
        this.unit = Objects.requireNonNullElse(unit, "°C");

        if (maxLimitTemperature < targetTemperature) {
            this.maxLimitTemperature = targetTemperature + 20.0;
        } else {
            this.maxLimitTemperature = maxLimitTemperature;
        }

        setTemperature(targetTemperature);
    }

    public boolean setTemperature(double temperature) {
        if (!isValidTemperature(temperature)) {
            this.targetTemperature = 20.0;
            this.currentTemperature = 20.0;
            return false;
        }
        this.targetTemperature = temperature;
        this.currentTemperature = temperature;
        return true;
    }

    public void simulateFluctuation() {
        simulateFluctuation(2.0);
    }

    public void simulateFluctuation(double spread) {
        double safeSpread = Math.max(0.0, spread);
        double delta = (Math.random() - 0.5) * safeSpread;
        double updated = this.targetTemperature + delta;
        this.currentTemperature = Math.round(updated * 10.0) / 10.0;
    }

    public void resetSettings() {
        this.targetTemperature = 20.0;
        this.currentTemperature = 20.0;
    }

    protected boolean isValidTemperature(double temp) {
        return temp >= -50.0 && temp <= 300.0;
    }

    public boolean isExceedingLimit() {
        return currentTemperature > maxLimitTemperature;
    }

    public String getDeviceType() {
        return getClass().getSimpleName();
    }

    public int getId() {
        return id;
    }

    public String getLocation() {
        return location;
    }

    public String getUnit() {
        return unit;
    }

    public double getCurrentTemperature() {
        return currentTemperature;
    }

    public double getTargetTemperature() {
        return targetTemperature;
    }

    public double getMaxLimitTemperature() {
        return maxLimitTemperature;
    }

    @Override
    public String toString() {
        DecimalFormat df = new DecimalFormat("#.0");
        StringBuilder sb = new StringBuilder();
        sb.append("[")
          .append(getDeviceType())
          .append(" ID:")
          .append(id)
          .append("] Локация: ")
          .append(location)
          .append(" | Текущая: ")
          .append(df.format(currentTemperature))
          .append(unit)
          .append(" | Целевая: ")
          .append(df.format(targetTemperature))
          .append(unit)
          .append(" | Предел: ")
          .append(df.format(maxLimitTemperature))
          .append(unit);
        return sb.toString();
    }
}