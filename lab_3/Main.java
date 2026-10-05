package lab_3;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.List;


public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<Thermostat> devices = new ArrayList<>();

        boolean running = true;

        while (running){
            System.out.println("\n|        Управление умными приборами        |");
            System.out.println("1. Добавить Кондиционер");
            System.out.println("2. Добавить Печь");
            System.out.println("3. Показать список всех приборов");
            System.out.println("4. Управлять конкретным прибором");
            System.out.println("0. Выйти из программы");
            System.out.println("Выберите действие: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    addAirConditioner(scanner, devices);
                    break;
                case 2:
                    addOven(scanner, devices);
                    break;
                case 3:
                    showAllDevices(devices);
                    break;
                case 4:
                    manageDevice(scanner, devices);
                    break;
                case 0:
                    running = false;
                    System.out.println("Завершение работы программы.");
                    break;
                default:
                    System.out.println("Неверный пункт меню! Попробуйте снова.");
            }

        }
    }

    private static void addAirConditioner(Scanner scanner, List<Thermostat> devices) {
        System.out.println("\n--- Добавление Кондиционера ---");
        System.out.print("Введите комнатное расположение (например, Гостиная): ");
        String location = scanner.nextLine();

        System.out.print("Введите единицы измерения (C или F): ");
        String unit = scanner.nextLine();

        System.out.print("Введите целевую температуру: ");
        double targetTemp = scanner.nextDouble();

        System.out.print("Введите предельно допустимую температуру: ");
        double maxTemp = scanner.nextDouble();
        scanner.nextLine();

        AirConditioner ac = new AirConditioner(location, unit, targetTemp, maxTemp);
        devices.add(ac);
        System.out.println("Кондиционер успешно добавлен! ID прибора: " + ac.getId());
    }

    private static void addOven(Scanner scanner, List<Thermostat> devices) {
        System.out.println("\n--- Добавление Печи ---");
        System.out.print("Введите расположение печи (например, Кухня): ");
        String location = scanner.nextLine();

        System.out.print("Введите единицы измерения (C или F): ");
        String unit = scanner.nextLine();

        System.out.print("Введите целевую температуру нагрева: ");
        double targetTemp = scanner.nextDouble();

        System.out.print("Введите предельно допустимую температуру: ");
        double maxTemp = scanner.nextDouble();
        scanner.nextLine();

        Oven oven = new Oven(location, unit, targetTemp, maxTemp);
        devices.add(oven);
        System.out.println("Печь успешно добавлена! ID прибора: " + oven.getId());
    }
}
