package lab_3;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


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
        System.out.println("\n    Добавление Кондиционера     ");
        String location = readValidText(scanner, "Введите комнатное расположение (например, Гостиная): ");
        String unit = readValidUnit(scanner);

        double targetTemp = readValidDouble(scanner, "Введите целевую температуру (-20...30): ", -20.0, 30.0);
        double maxTemp = readValidDouble(scanner, "Введите предельно допустимую температуру (целевая...45): ", targetTemp, 45.0);

        AirConditioner ac = new AirConditioner(location, unit, targetTemp, maxTemp);
        devices.add(ac);
        System.out.println("Кондиционер успешно добавлен! ID прибора: " + ac.getId());
    }

    private static void addOven(Scanner scanner, List<Thermostat> devices) {
        System.out.println("\n    Добавление Печи     ");
        String location = readValidText(scanner, "Введите расположение печи (например, Кухня): ");
        String unit = readValidUnit(scanner);

        double targetTemp = readValidDouble(scanner, "Введите целевую температуру нагрева (30...300): ", 30.0, 300.0);
        double maxTemp = readValidDouble(scanner, "Введите предельно допустимую температуру (целевая...350): ", targetTemp, 350.0);

        Oven oven = new Oven(location, unit, targetTemp, maxTemp);
        devices.add(oven);
        System.out.println("Печь успешно добавлена! ID прибора: " + oven.getId());
    }

    private static void showAllDevices(List<Thermostat> devices) {
        if (devices.isEmpty()) {
            System.out.println("Список приборов пуст.");
            return;
        }
        System.out.println("\n--- Список всех зарегистрированных приборов ---");
        for (Thermostat device : devices) {
            String typeName = (device instanceof AirConditioner) ? "Кондиционер" : "Печь";
            System.out.println("ID: " + device.getId() +
                    " | Тип: " + typeName +
                    " | Локация: " + device.getLocation() +
                    " | Целевая темп.: " + device.getTargetTemperature() + device.getUnit() +
                    " | Текущая темп.: " + device.getCurrentTemperature() + device.getUnit());
        }
    }

    private static void manageDevice(Scanner scanner, List<Thermostat> devices) {
        if (devices.isEmpty()) {
            System.out.println("Список приборов пуст. Нечем управлять.");
            return;
        }

        int id = readValidInt(scanner, "Введите ID прибора для управления: ", 1, Integer.MAX_VALUE);
        Thermostat selectedDevice = null;

        for (Thermostat d : devices) {
            if (d.getId() == id) {
                selectedDevice = d;
                break;
            }
        }

        if (selectedDevice == null) {
            System.out.println("Прибор с таким ID не найден!");
            return;
        }

        System.out.println("\nУправление прибором ID " + selectedDevice.getId() + ":");
        System.out.println("1. Изменить целевую температуру");

        if (selectedDevice instanceof AirConditioner) {
            AirConditioner ac = (AirConditioner) selectedDevice;
            System.out.println("2. Переключить Эко-режим");
            System.out.println("3. Изменить скорость вентилятора (1-5)");

            int subChoice = readValidInt(scanner, "Выберите действие: ", 1, 3);
            switch (subChoice) {
                case 1:
                    double temp = readValidDouble(scanner, "Введите новую температуру (-20...30): ", -20.0, 30.0);
                    ac.setTemperature(temp);
                    break;
                case 2:
                    ac.taggleEcoMode();
                    break;
                case 3:
                    int speed = readValidInt(scanner, "Введите скорость (1-5): ", 1, 5);
                    ac.setFanSpeed(speed);
                    break;
            }
        } else if (selectedDevice instanceof Oven) {
            Oven oven = (Oven) selectedDevice;
            System.out.println("2. Установить таймер (минуты)");

            int subChoice = readValidInt(scanner, "Выберите действие: ", 1, 2);
            switch (subChoice) {
                case 1:
                    double temp = readValidDouble(scanner, "Введите новую температуру (30...300): ", 30.0, 300.0);
                    oven.setTemperature(temp);
                    break;
                case 2:
                    int mins = readValidInt(scanner, "Введите время таймера в минутах (1-1440): ", 1, 1440);
                    oven.setTimer(mins);
                    break;
            }
        }
    }

    private static int readValidInt(Scanner scanner, String prompt, int min, int max) {
        while (true) {
            System.out.print(prompt);
            if (scanner.hasNextInt()) {
                int value = scanner.nextInt();
                scanner.nextLine();
                if (value >= min && value <= max) {
                    return value;
                }
                System.out.println("Ошибка! Введите число от " + min + " до " + max + ".");
            } else {
                System.out.println("Ошибка! Ввод должен содержать только цифры (буквы недопустимы).");
                scanner.nextLine();
            }
        }
    }

    private static double readValidDouble(Scanner scanner, String prompt, double min, double max) {
        while (true) {
            System.out.print(prompt);
            if (scanner.hasNextDouble()) {
                double value = scanner.nextDouble();
                scanner.nextLine();
                if (value >= min && value <= max) {
                    return value;
                }
                System.out.println("Ошибка! Введите число от " + min + " до " + max + ".");
            } else {
                System.out.println("Ошибка! Ввод должен содержать только цифры (буквы недопустимы).");
                scanner.nextLine();
            }
        }
    }

    private static String readValidText(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            if (!input.isEmpty() && input.matches("[a-zA-Zа-яА-ЯёЁ\\s]+")) {
                return input;
            }
            System.out.println("Ошибка! Ввод должен содержать только буквы (без цифр и спецсимволов).");
        }
    }

    private static String readValidUnit(Scanner scanner) {
        while (true) {
            System.out.print("Введите единицы измерения (C или F): ");
            String input = scanner.nextLine().trim().toUpperCase();
            if (input.equals("C") || input.equals("F")) {
                return input;
            }
            System.out.println("Ошибка! Разрешено вводить только 'C' или 'F'.");
        }
    }
}

    
