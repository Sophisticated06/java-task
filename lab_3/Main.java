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

    private static void showAllDevices(List<Thermostat> devices) {
        System.out.println("\n--- Список всех зарегистрированных приборов ---");
        if (devices.isEmpty()) {
            System.out.println("Список пуст. Сначала добавьте хотя бы одно устройство");
            return;
        }

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
            System.out.println("Список пуст! Нечем управлять.");
            return;
        }

        System.out.print("Введите ID прибора для управления: ");
        int id = scanner.nextInt();
        scanner.nextLine();

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

        System.out.println("\n--- Управление прибором ID: " + selectedDevice.getId() + " ---");
        System.out.println("1. Изменить целевую температуру");

        if (selectedDevice instanceof AirConditioner) {
            AirConditioner ac = (AirConditioner) selectedDevice;
            System.out.println("2. Переключить Эко-режим");
            System.out.println("3. Изменить скорость вентилятора (1-5)");
            System.out.print("Выберите действие: ");
            int subChoice = scanner.nextInt();
            scanner.nextLine();

            switch (subChoice) {
                case 1:
                    System.out.print("Введите новую температуру: ");
                    double temp = scanner.nextDouble();
                    scanner.nextLine();
                    ac.setTemperature(temp);
                    break;
                case 2:
                    ac.taggleEcoMode();
                    break;
                case 3:
                    System.out.print("Введите скорость (1-5): ");
                    int speed = scanner.nextInt();
                    scanner.nextLine();
                    ac.setFanSpeed(speed);
                    break;
                default:
                    System.out.println("Неверная команда!");
            }
        } else if (selectedDevice instanceof Oven) {
            Oven oven = (Oven) selectedDevice;
            System.out.println("2. Установить таймер (минуты)");
            System.out.println("3. Заблокировать дверцу");
            System.out.println("4. Разблокировать дверцу");
            System.out.print("Выберите действие: ");
            int subChoice = scanner.nextInt();
            scanner.nextLine();

            switch (subChoice) {
                case 1:
                    System.out.print("Введите новую температуру: ");
                    double temp = scanner.nextDouble();
                    scanner.nextLine();
                    oven.setTemperature(temp);
                    break;
                case 2:
                    System.out.print("Введите время в минутах: ");
                    int mins = scanner.nextInt();
                    scanner.nextLine();
                    oven.setTimer(mins);
                    break;
                case 3:
                    oven.lockDoor();
                    break;
                case 4:
                    oven.unlockDoor();
                    break;
                default:
                    System.out.println("Неверная команда!");
            }
        }
    }   
}

    
