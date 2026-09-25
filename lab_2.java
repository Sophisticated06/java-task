class AirConditioner {
    private String brand;
    private int year;
    private double power;

    public AirConditioner() {
        this.brand = "Неизвестно";
        this.year = 2020;
        this.power = 1.5;
    }

    public AirConditioner(String brand, int year, double power) {
        this.brand = brand;
        this.year = year;
        this.power = power;
    }

    public String getBrand() { return brand; }
    public void setBrand(String brand) { this.brand = brand; }

    public int getYear() { return year; }
    public void setYear(int year) { this.year = year; }

    public double getPower() { return power; }
    public void setPower(double power) { this.power = power; }

    // Метод вывода информации
    public void displayInfo() {
        System.out.println("Марка: " + brand + " | Год: " + year + " | Мощность: " + power + " кВт");
    }

    // Проверка, считается ли кондиционер устаревшим (старше 5 лет)
    public boolean isOld(int currentYear) {
        return (currentYear - this.year) > 5;
    }

    // Расчет энергопотребления за указанное количество часов
    public double calculateEnergy(int hours) {
        return this.power * hours;
    }
}