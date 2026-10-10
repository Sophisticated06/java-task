package lab_3;

public class WirelessRadiatorThermostat extends Thermostat {
    private int batteryLevel;      // Уровень заряда батарейки (0 - 100%)
    private int signalStrength; 
    private boolean windowOpen;    // Статус датчика открытого окна

    // Конструктор
    public WirelessRadiatorThermostat(String location, String unit, double targetTemperature, double maxLimitTemperature) {
        super(location, unit, targetTemperature, maxLimitTemperature);
        this.batteryLevel = 100;
        this.signalStrength = -50;
        this.windowOpen = false;
    }

    // Метод: проверка батарейки
    public void checkBattery() {
        System.out.println("Заряд батареи термоголовки (" + getLocation() + "): " + batteryLevel + "%");
        if (batteryLevel < 15) {
            System.out.println("ВНИМАНИЕ: Требуется заменить батарейки в термостате батареи!");
        }
    }

    // Метод: симуляция разряда батарейки
    public void drainBattery(int percent) {
        this.batteryLevel = Math.max(0, this.batteryLevel - percent);
        System.out.println("Батарея разряжена. Текущий заряд: " + this.batteryLevel + "%");
    }

    // Метод: реакция на открытое окно
    public void setWindowOpen(boolean isOpen) {
        this.windowOpen = isOpen;
        if (this.windowOpen) {
            System.out.println("Обнаружено открытое окно в комнате [" + getLocation() + "]! Отопление временно приостановлено.");
        } else {
            System.out.println("Окно закрыто. Термостат батареи возобновляет работу в штатном режиме.");
        }
    }

    public int getBatteryLevel() { return batteryLevel; }
    public int getSignalStrength() { return signalStrength; }
    public boolean isWindowOpen() { return windowOpen; }

    @Override
    protected boolean isValidTemperature(double temp) {
        // Радиатор отопления: минимальная защита от замерзания 5°C, максимальная 28°C
        return temp >= 5.0 && temp <= 28.0;
    }
}