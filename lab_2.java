// Класс кондиционера (без слова public, так как файл называется lab_2)
class AirConditioner {
    // Поля (характеристики)
    private String brand;  // Бренд
    private int year;      // Год выпуска
    private double power;  // Мощность в кВт

    // 1. Конструктор по умолчанию
    public AirConditioner() {
        this.brand = "Неизвестно";
        this.year = 2020;
        this.power = 1.5;
    }

    // 2. Конструктор с параметрами
    public AirConditioner(String brand, int year, double power) {
        this.brand = brand;
        this.year = year;
        this.power = power;
    }
}