import java.util.Random;

public abstract class Thermostat {
    private static int idCounter = 1;
    private final int id;
    private String location;
    private String unit;
    protected double currentTemperature;
    protected double targetTemperature;
    protected double maxLimitTemperature;

    public Thermostat(String location, String unit, double targetTemperature, double maxLimitTemperature){
        this.id = idCounter++;
        this.unit = unit;
        this.targetTemperature = targetTemperature;
        this.maxLimitTemperature = maxLimitTemperature;
        this.currentTemperature = 20.0;
    }

    public void setTemperature(double temperature){
        this.targetTemperature = temperature;
        System.out.println("Целевая температура установлена: " + temperature + " " + unit);
    }

    public boolean isExceedingLimit(){
        return currentTemperature > maxLimitTemperature;
    }

    public void simulateFluctuation(){
        double delta = (new Random().nextDouble() * 4) - 2;
        this.currentTemperature += delta;
        System.out.printf("Температура изменилась. Текущая: %.1f %s%n", currentTemperature, unit);
    }

    public void resetSettings(){
        this.targetTemperature = 20.0;
        System.out.println("Настройки сброшены к базовым (20.0 " + unit + " ).");
    }

    public int getId(){ return id;}
    public String getLocation(){return location;}
    public void setLocation(String location){this.location = location;}
    public double getCurrentTemperature() { return currentTemperature; }
    public double getTargetTemperature() { return targetTemperature; }

    @Override
    public String toString() {
        return String.format("ID: %d | %s | Место: %s | Ед: %s | Текущая: %.1f | Цель: %.1f | Предел: %.1f",
                id, getClass().getSimpleName(), location, unit, currentTemperature, targetTemperature, maxLimitTemperature);
    }
}

