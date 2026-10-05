
import java.util.ArrayList;
import java.util.Scanner;

import lab_3.AirConditioner;

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

            // Безопасный ввод пункта меню
            if (scanner.hasNextInt()) {
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
            } else {
                System.out.println("Ошибка! Введите целое число из меню.");
                scanner.nextLine(); // Очистка некорректного ввода
            }
        }
    }

    private static int readPositiveInt(Scanner scanner, String prompt) {
        int value;
        while (true) {
            System.out.print(prompt);
            if (scanner.hasNextInt()) {
                value = scanner.nextInt();
                if (value >= 0) {
                    break; // 
                } else {
                    System.out.println("Ошибка! Число не может быть отрицательным.");
                }
            } else {
                System.out.println("Ошибка! Введены буквы или некорректный символ. Введите число.");
                scanner.next(); 
            }
        }
        return value;
    }

    
    private static double readPositiveDouble(Scanner scanner, String prompt) {
        double value;
        while (true) {
            System.out.print(prompt);
            if (scanner.hasNextDouble()) {
                value = scanner.nextDouble();
                if (value >= 0) {
                    break;
                } else {
                    System.out.println("Ошибка! Число не может быть отрицательным.");
                }
            } else {
                System.out.println("Ошибка! Введены буквы или некорректный символ.");
                scanner.next();
            }
        }
        return value;
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

        int year = readPositiveInt(scanner, "Введите год выпуска: ");
        double power = readPositiveDouble(scanner, "Введите мощность (кВт): ");

        list.add(new AirConditioner(brand, year, power));
        System.out.println("Объект успешно добавлен!");
    }

    // Редактирование по номеру
    public static void editElement(ArrayList<AirConditioner> list, Scanner scanner) {
        showAll(list);
        if (list.isEmpty()) return;

        int index = readPositiveInt(scanner, "Введите индекс элемента: ");
        scanner.nextLine(); // Очистка буфера после ввода числа

        if (index >= 0 && index < list.size()) {
            System.out.print("Новый бренд: ");
            list.get(index).setBrand(scanner.nextLine());

            int year = readPositiveInt(scanner, "Новый год: ");
            list.get(index).setYear(year);

            double power = readPositiveDouble(scanner, "Новая мощность: ");
            list.get(index).setPower(power);

            System.out.println("Свойства обновлены!");
        } else {
            System.out.println("Элемента с таким индексом не существует!");
        }
    }

    // Удаление объекта
    public static void removeElement(ArrayList<AirConditioner> list, Scanner scanner) {
        showAll(list);
        if (list.isEmpty()) return;

        int index = readPositiveInt(scanner, "Введите индекс для удаления: ");

        if (index >= 0 && index < list.size()) {
            list.remove(index);
            System.out.println("Объект удален!");
        } else {
            System.out.println("Элемента с таким индексом не существует!");
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