package lab_3.model;

import java.text.DecimalFormat;

public abstract class Thermostat {
    private int id;
    private String location;
    private String unit;
    private double currentTemperature;
    protected double targetTemperature;
    protected double maxLimitTemperature;

    public Thermostat(int id, String location, String unit, double targetTemperature, double maxLimitTemperature) {
        this.id = id;
        this.location = location;
        this.unit = unit;
         
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
        double delta = (Math.random() - 0.5) * 2.0;
        this.currentTemperature = Math.round((this.targetTemperature + delta) * 10.0) / 10.0;
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

    // Геттеры
    public int getId() { return id; }
    public String getLocation() { return location; }
    public String getUnit() { return unit; }
    public double getCurrentTemperature() { return currentTemperature; }
    public double getTargetTemperature() { return targetTemperature; }
    public double getMaxLimitTemperature() { return maxLimitTemperature; }

    @Override
    public String toString() {
    DecimalFormat df = new DecimalFormat("#.0");
    StringBuilder sb = new StringBuilder();
    sb.append("[")
      .append(getClass().getSimpleName())
      .append(" ID:")
      .append(id)
      .append("] Локация: ")
      .append(location)
      .append(" | Текущая: ")
      .append(df.format(getCurrentTemperature()))
      .append(unit)
      .append(" | Целевая: ")
      .append(df.format(getTargetTemperature()))
      .append(unit)
      .append(" | Предел: ")
      .append(df.format(getMaxLimitTemperature()))
      .append(unit);
    return sb.toString();
}
}