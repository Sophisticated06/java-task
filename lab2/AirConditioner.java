public class AirConditioner {
    private String brand;
    private int year;
    private double power;
    private int b;
    // Конструктор по умолчанию
    public AirConditioner() {
        this.brand = "Неизвестно";
        this.year = 2020;
        this.power = 1.5;
    }

    // Конструктор с параметрами (используем сеттеры с проверкой)
    public AirConditioner(String brand, int year, double power) {
        this.brand = brand;
        setYear(year);
        setPower(power);
    }

    public AirConditioner(String brand, int year, double power,int b) {
        this(brand, year, power);
    }
    // Геттеры и сеттеры
    public String getBrand() { return brand; }
    public void setBrand(String brand) { this.brand = brand; }

    public int getYear() { return year; }
    public void setYear(int year) { 
        if (year < 0) {
            System.out.println("Ошибка: год выпуска не может быть отрицательным!");
        } else {
            this.year = year; 
        }
    }

    public double getPower() { return power; }
    public void setPower(double power) { 
        if (power < 0) {
            System.out.println("Ошибка: мощность не может быть отрицательной!");
        } else {
            this.power = power; 
        }
    }

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