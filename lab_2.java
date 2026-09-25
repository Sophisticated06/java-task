import java.util.ArrayList;
import java.util.Scanner;

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
public class lab_2 {
    public static void main(String[] args) {
        ArrayList<AirConditioner> list = new ArrayList<>();

        
        AirConditioner ac1 = new AirConditioner(); // По умолчанию
        AirConditioner ac2 = new AirConditioner("Samsung", 2023, 2.5); // С параметрами
        AirConditioner ac3 = new AirConditioner("Daikin", 2026, 3.0); // С параметрами

        list.add(ac1);
        list.add(ac2);
        list.add(ac3);

        System.out.println("Программа запущена, массив объектов создан.");
    }

    
    public static void findNewest(ArrayList<AirConditioner> list) {
        if (list.isEmpty()) {
            System.out.println("Список пуст!");
            return;
        }

        AirConditioner newest = list.get(0);
        for (AirConditioner ac : list) {
            if (ac.getYear() > newest.getYear()) {
                newest = ac;
            }
        }

        System.out.println("\n=== Самый новый кондиционер ===");
        newest.displayInfo();
    }
}