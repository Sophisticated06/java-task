import java.util.ArrayList;
import java.util.Scanner;

// 1. Класс объекта
class AirConditioner {
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

// 2. Главный класс программы (имя совпадает с именем файла)
public class lab_2 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<AirConditioner> list = new ArrayList<>();

        // Инициализация объектов двумя типами конструкторов
        list.add(new AirConditioner());
        list.add(new AirConditioner("Samsung", 2022, 2.5));
        list.add(new AirConditioner("Daikin", 2026, 3.2));

        boolean exit = false;

        // Цикл меню
        while (!exit) {
            System.out.println("\n===== МЕНЮ =====");
            System.out.println("1. Просмотр списка объектов");
            System.out.println("2. Изменение свойств по номеру");
            System.out.println("3. Добавление объекта в массив");
            System.out.println("4. Удаление объекта из массива");
            System.out.println("5. Вычислить параметр (Самый новый)");
            System.out.println("6. Выход");
            System.out.print("Выберите действие: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Очистка буфера

            switch (choice) {
                case 1:
                    showAll(list);
                    break;
                case 2:
                    editElement(list, scanner);
                    break;
                case 3:
                    addElement(list, scanner);
                    break;
                case 4:
                    removeElement(list, scanner);
                    break;
                case 5:
                    findNewest(list);
                    break;
                case 6:
                    exit = true;
                    System.out.println("Завершение программы.");
                    break;
                default:
                    System.out.println("Неверный пункт!");
            }
        }
    }

    // Просмотр всех элементов
    public static void showAll(ArrayList<AirConditioner> list) {
        if (list.isEmpty()) {
            System.out.println("Массив пуст.");
            return;
        }
        for (int i = 0; i < list.size(); i++) {
            System.out.print("[" + i + "] ");
            list.get(i).displayInfo();
        }
    }

    // Добавление объекта
    public static void addElement(ArrayList<AirConditioner> list, Scanner scanner) {
        System.out.print("Введите бренд: ");
        String brand = scanner.nextLine();
        System.out.print("Введите год выпуска: ");
        int year = scanner.nextInt();
        System.out.print("Введите мощность (кВт): ");
        double power = scanner.nextDouble();

        list.add(new AirConditioner(brand, year, power));
        System.out.println("Объект успешно добавлен!");
    }

    // Редактирование по номеру
    public static void editElement(ArrayList<AirConditioner> list, Scanner scanner) {
        showAll(list);
        System.out.print("Введите индекс элемента: ");
        int index = scanner.nextInt();
        scanner.nextLine();

        if (index >= 0 && index < list.size()) {
            System.out.print("Новый бренд: ");
            list.get(index).setBrand(scanner.nextLine());
            System.out.print("Новый год: ");
            list.get(index).setYear(scanner.nextInt());
            System.out.print("Новая мощность: ");
            list.get(index).setPower(scanner.nextDouble());
            System.out.println("Свойства обновлены!");
        } else {
            System.out.println("Неверный индекс!");
        }
    }

    // Удаление объекта
    public static void removeElement(ArrayList<AirConditioner> list, Scanner scanner) {
        showAll(list);
        System.out.print("Введите индекс для удаления: ");
        int index = scanner.nextInt();

        if (index >= 0 && index < list.size()) {
            list.remove(index);
            System.out.println("Объект удален!");
        } else {
            System.out.println("Неверный индекс!");
        }
    }

    // Нахождение самого нового кондиционера
    public static void findNewest(ArrayList<AirConditioner> list) {
        if (list.isEmpty()) {
            System.out.println("Массив пуст!");
            return;
        }
        AirConditioner newest = list.get(0);
        for (AirConditioner ac : list) {
            if (ac.getYear() > newest.getYear()) {
                newest = ac;
            }
        }
        System.out.println("\nСамый новый кондиционер:");
        newest.displayInfo();
    }
}