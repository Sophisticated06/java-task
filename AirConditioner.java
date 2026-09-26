public class AirConditioner {
    private String brand;
    private int year;
    private double power;

    // Конструктор по умолчанию
    public AirConditioner() {
        this.brand = "Неизвестно";
        this.year = 2020;
        this.power = 1.5;
    }

    // Конструктор с параметрами
    public AirConditioner(String brand, int year, double power) {
        this.brand = brand;
        this.year = year;
        this.power = power;
    }

    // Геттеры и сеттеры
    public String getBrand() { return brand; }
    public void setBrand(String brand) { this.brand = brand; }

    public int getYear() { return year; }
    public void setYear(int year) { this.year = year; }

    public double getPower() { return power; }
    public void setPower(double power) { this.power = power; }

    // Вывод информации
    public void displayInfo() {
        System.out.println("Марка: " + brand + " | Год: " + year + " | Мощность: " + power + " кВт");
    }

    // Метод 1
    public boolean isOld(int currentYear) {
        return (currentYear - this.year) > 5;
    }

    // Метод 2
    public double calculateEnergy(int hours) {
        return this.power * hours;
    }
}