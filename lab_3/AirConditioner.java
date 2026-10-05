package lab_3;
public class AirConditioner extends Thermostat {
    private int fanSpeed;
    private boolean ecoMode;
    private String filterStatus;
    private double powerConsumption;


    public AirConditioner(String location, String unit, double targetTemperature, double maxLimitTemperature){
        super(location, unit, targetTemperature, maxLimitTemperature);
        this.fanSpeed = 1;
        this.ecoMode = false;
        this.filterStatus = "Чистый";
        this.powerConsumption = 1.5;
    }
    
    // Метод: Переключение эко-режима
    public void taggleEcoMode(){
        this.ecoMode = !this.ecoMode;
        System.out.println("Эко-режим кондиционера: " + (ecoMode ? "Включен" : "Выключен"));
    }
    
    // Метод: Установка скорости вентилятора
    public void setFanSpeed(int speed){
        if (speed >= 1 && speed <= 5){
            this.fanSpeed = speed;
            System.out.println("Скорость вентилятора установлена на: " + speed);
        }
        else{
            System.out.println("Ошибка: скорость должна быть от 1 до 5");
        }
    }

    // Метод: Очистка фильта
    public void cleanFilter(){
        this.filterStatus = "Чистый";
        System.err.println("Воздушный фильтр успешно очищен");
    }

    // Метод: Режим турбо-охлаждения
    public void coolFast(){
        this.targetTemperature -= 3.0;
        this.fanSpeed = 5;
        System.err.println("Запущен режим быстрый Turbo: температура снижена, вентилятор на максимуме");
    }

    public int getFanSpeed() { return fanSpeed; }
    public boolean isEcoMode() { return ecoMode; }
    public String getFilterStatus() { return filterStatus; }
    public double getPowerConsumption() { return powerConsumption; }
}
