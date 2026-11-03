package lab_3.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import lab_3.model.AirConditioner;
import lab_3.model.FileDeviceReader;
import lab_3.model.Oven;
import lab_3.model.Thermostat;
import lab_3.model.WirelessRadiatorThermostat;
import lab_3.view.ConsoleView;


public class Main {

    private static String currentFileName = "devices.txt";

    public static void main(String[] args) {
        // Если аргументы переданы через терминал — отрабатываем их и завершаем работу
        if (args.length > 0) {
            handleCommandLineArguments(args);
            return;
        }

        // Если аргументов нет — запускаем полноценное интерактивное меню
        runInteractiveMode();
    }

    private static void handleCommandLineArguments(String[] args) {
        String command = args[0];

        switch (command) {
            case "--help":
            case "-h":
                ConsoleView.printHelp();
                break;

            case "--read":
            case "-r":
                if (args.length > 1) {
                    currentFileName = args[1];
                }
                FileDeviceReader reader = new FileDeviceReader(currentFileName);
                List<Thermostat> devices = reader.readDevicesFromFile();
                ConsoleView.printDevices(devices);
                break;

            case "--cycle":
            case "-c":
                int intervalSeconds = 3;
                
                if (args.length > 1) {
                    try {

                        intervalSeconds = Integer.parseInt(args[1]);
                    } catch (NumberFormatException e) {
                       
                        currentFileName = args[1];
                    }
                }
                
                
                if (args.length > 2) {
                    currentFileName = args[2];
                }

                System.out.println("Запуск циклического опроса с периодом " + intervalSeconds + " сек. (Файл: " + currentFileName + ", Для выхода нажмите Ctrl+C)");
                long intervalMillis = intervalSeconds * 1000L;

                try {
                    while (true) {
                        System.out.println("\n--- Опрос состояния устройств ---");
                        FileDeviceReader cycleReader = new FileDeviceReader(currentFileName);
                        List<Thermostat> currentDevices = cycleReader.readDevicesFromFile();
                        ConsoleView.printDevices(currentDevices);
                        
                        Thread.sleep(intervalMillis);
                    }
                } 
                catch (InterruptedException e) {
                    System.out.println("Циклический опрос был прерван.");
                }
                break;

            case "--csv":
                System.out.println("Режим CSV-логирования находится в разработке...");
                break;

            default:
                System.out.println("Неизвестный ключ: " + command);
                ConsoleView.printHelp();
                break;
        }
    }

    private static void runInteractiveMode() {
        Scanner scanner = new Scanner(System.in);
        List<Thermostat> devices = new ArrayList<>();

        boolean running = true;

        while (running){
            System.out.println("\n|        Управление умными приборами        |");
            System.out.println("1. Добавить Кондиционер");
            System.out.println("2. Добавить Печь");
            System.out.println("3. Добавить Беспроводной термостат батареи");
            System.out.println("4. Показать список всех приборов");
            System.out.println("5. Управлять конкретным прибором");
            System.out.println("6. Загрузить приборы из файла");
            System.out.println("7. Изменить имя файла для чтения (текущий: " + currentFileName + ")");
            System.out.println("0. Выйти из программы");
            System.out.println("Выберите действие: ");

            int choice = readValidInt(scanner, "", 0, 7);

            switch (choice) {
                case 1:
                    ConsoleView.printDevices(devices);
                    break;
                case 2:
                    addAirConditioner(scanner, devices);
                    break;
                case 3:
                    addOven(scanner, devices);
                    break;
                case 4:
                    addWirelessThermostat(scanner, devices);
                    break;
                case 5:
                    manageDevice(scanner, devices);
                    break;
                case 6:
                    loadDevicesFromFileMenu(scanner, devices, currentFileName);
                    break;
                case 7:
                    System.out.print("Введите новое имя файла (например, new_devices.txt): ");
                    String targetName = scanner.nextLine().trim();
                    File oldFile = new File(currentFileName);
                    File newFile = new File(targetName);
                    boolean success = oldFile.renameTo(newFile);

                    if (success) {
                        System.out.println("Успешно! Файл переименован в \"" + targetName + "\".");
                        currentFileName = targetName;
                    } else {
                        System.out.println("Ошибка при переименовании файла.");
                    }
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

    private static void addWirelessThermostat(Scanner scanner, List<Thermostat> devices) {
    System.out.println("\n--- Добавление Термостата батареи ---");
    String location = readValidText(scanner, "Введите комнату (например, Спальня): ");
    String unit = readValidUnit(scanner);

    double targetTemp = readValidDouble(scanner, "Введите целевую температуру (5...28): ", 5.0, 28.0);
    double maxTemp = readValidDouble(scanner, "Введите предельно допустимую температуру (целевая...35): ", targetTemp, 35.0);

    WirelessRadiatorThermostat wrt = new WirelessRadiatorThermostat(location, unit, targetTemp, maxTemp);
    devices.add(wrt); // Upcasting в общий список приборов!
    System.out.println("Термостат батареи успешно добавлен! ID прибора: " + wrt.getId());
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

        System.out.println("\nУправление прибором ID " + selectedDevice.getId() + " (" + selectedDevice.getClass().getSimpleName() + "):");
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
                    int speed = readValidInt(scanner, "Введите скорость вентилятора (1-5): ", 1, 5);
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
        } else if (selectedDevice instanceof WirelessRadiatorThermostat) {
            WirelessRadiatorThermostat wrt = (WirelessRadiatorThermostat) selectedDevice;
            System.out.println("2. Проверить заряд батареи");
            System.out.println("3. Изменить статус датчика окна (Открыто/Закрыто)");
            System.out.println("4. Симулировать разряд батареи");

            int subChoice = readValidInt(scanner, "Выберите действие: ", 1, 4);
            switch (subChoice) {
                case 1:
                    double temp = readValidDouble(scanner, "Введите новую температуру (5...28): ", 5.0, 28.0);
                    wrt.setTemperature(temp);
                    break;
                case 2:
                    wrt.checkBattery();
                    break;
                case 3:
                    System.out.print("Окно открыто? (true / false): ");
                    while (!scanner.hasNextBoolean()) {
                        System.out.println("Ошибка! Введите true или false.");
                        scanner.next();
                    }
                    boolean isOpen = scanner.nextBoolean();
                    scanner.nextLine(); // очистка буфера
                    wrt.setWindowOpen(isOpen);
                    break;
                case 4:
                    int percent = readValidInt(scanner, "Введите процент разряда (1-100): ", 1, 100);
                    wrt.drainBattery(percent);
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

    private static void loadDevicesFromFileMenu(Scanner scanner, List<Thermostat> devices, String fileName) {
    System.out.println("\n      Загрузка приборов из файла: " + fileName + "       ");
    
    
    FileDeviceReader reader = new FileDeviceReader(fileName);


    List<Thermostat> loaded = reader.readDevicesFromFile();

    if (!loaded.isEmpty()) {
        devices.addAll(loaded);
        System.out.println("Успешно добавлено в общую систему устройств: " + loaded.size() + " шт.");
    } else {
        System.out.println("Файл пуст или не содержит корректных данных.");
    }
}
}

    
