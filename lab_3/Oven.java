package lab_3;

public class Oven extends Thermostat {
    private int timerMinutes;
    private String heatingMode;
    private boolean doorLocked;
    private boolean isPreheated;

    public Oven(String location, String unit, double targetTemperature, double maxLimitTemperature){
        super(location, unit, targetTemperature, maxLimitTemperature);
        this.timerMinutes = 0;
        this.heatingMode = "Стандарт";
        this.doorLocked = false;
        this.isPreheated = false;
    }

    // Метод: Установка таймера
    public void setTimer(int minutes){
        this.timerMinutes = minutes;
        System.err.println("Таймер печи установлен на " + minutes + "минут");
    }

    // Метод: Смена режима нагрева
    public void setHeatingMode(String mode){
        this.heatingMode = mode;
        System.err.println("Режим нагрева изменен на: " + mode);
    }

    // Метод: Блокировка дверцы
    public void lockDoor(){
        this.doorLocked = true;
        System.err.println("Дверца печи заблокирована для безопасности");
    }

    // Метод: Разблокировка дверцы с проверкой на температуру
    public void unlockDoor(){
        if (currentTemperature < 50.0){
            this.doorLocked = false;
            System.err.println("Дверца печи разблокирована");
        }
        else{
            System.err.println("Оибка: Печь слишком горячая! Опасно открывать дверцу");
        }
    }

    public int getTimerMinutes() { return timerMinutes; }
    public String getHeatingMode() { return heatingMode; }
    public boolean isDoorLocked() { return doorLocked; }
    public boolean isPreheated() { return isPreheated; }

}
