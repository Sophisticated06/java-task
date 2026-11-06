package lab_3.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import lab_3.model.AirConditioner;
import lab_3.model.Oven;
import lab_3.model.Thermostat;
import lab_3.model.ThermostatManager;
import lab_3.model.WirelessRadiatorThermostat;
import lab_3.view.ConsoleView;

public class MainController {
    private final ThermostatManager manager;
    private final ConsoleView view;
    private final List<Thermostat> devices = new ArrayList<>();
    private final Scanner scanner = new Scanner(System.in);
    private String currentFileName = "testik.txt";

    public MainController(ThermostatManager manager, ConsoleView view) {
        this.manager = manager;
        this.view = view;
    }

    public void handle(String[] args) {
        if (args.length == 0) {
            view.showHelp();
            return;
        }

        try {
            switch (args[0]) {
                case "--help", "-h" -> view.showHelp();
                case "--read", "-r" -> executeRead(args);
                case "--cycle", "-c" -> executeCycle(args);
                case "--csv" -> executeCsv(args);
                case "--legacy", "-l" -> executeLegacyMode();
                default -> {
                    view.showError("Неизвестный параметр: " + args[0]);
                    view.showHelp();
                }
            }
        } catch (Exception e) {
            view.showError(e.getMessage());
        }
    }

    private void executeRead(String[] args) throws IOException {
        String file = args.length > 1 ? args[1] : currentFileName;
        List<Thermostat> list = manager.readFromFile(file);
        view.showDevices(list);
    }

    private void executeCycle(String[] args) throws IOException, InterruptedException {
        int interval = 3;
        String file = currentFileName;

        if (args.length > 1) {
            try {
                interval = Integer.parseInt(args[1]);
            } catch (NumberFormatException e) {
                file = args[1];
            }
        }
        if (args.length > 2) {
            file = args[2];
        }

        view.showMessage("Запуск циклического опроса (Интервал: " + interval + " сек., Файл: " + file + "). Ctrl+C для выхода.");
        while (true) {
            // Передаем false, чтобы ошибки в цикле не выводились
            List<Thermostat> list = manager.readFromFile(file, false);
            for (Thermostat d : list) {
                d.simulateFluctuation();
            }
            view.showDevices(list);
            Thread.sleep(interval * 1000L);
        }
    }

    private void executeCsv(String[] args) throws IOException, InterruptedException {
        int interval = 3;
        String input = currentFileName;
        String output = "devices_log.csv";

        if (args.length > 1) {
            try {
                interval = Integer.parseInt(args[1]);
            } catch (NumberFormatException ignored) {}
        }
        if (args.length > 2) input = args[2];
        if (args.length > 3) output = args[3];

        view.showMessage("Запуск CSV-логирования в " + output + " каждые " + interval + " сек.");
        while (true) {
            // Передаем false, чтобы ошибки при логировании не выводились
            List<Thermostat> list = manager.readFromFile(input, false);
            for (Thermostat d : list) {
                d.simulateFluctuation();
            }
            manager.logToCsv(list, output);
            view.showMessage("[" + java.time.LocalTime.now() + "] Данные записаны в CSV: " + output);
            Thread.sleep(interval * 1000L);
        }
    }

    private void executeLegacyMode() {
        initDefaultDevices();

        while (true) {
            view.showMessage("""
                
                ---------- ИНТЕРАКТИВНОЕ МЕНЮ ТЕРМОСТАТОВ ----------
                1. Показать все устройства
                2. Добавить устройство
                3. Удалить устройство
                4. Изменить целевую температуру
                5. Сбросить настройки устройства
                6. Прочитать устройства из файла
                7. Изменить имя файла для чтения
                0. Выход""");

            int choice = view.readPositiveInt(scanner, "Выберите пункт: ", 0, 7);

            switch (choice) {
                case 1 -> view.showDevices(devices);
                case 2 -> addDeviceMenu();
                case 3 -> removeDeviceMenu();
                case 4 -> editTemperatureMenu();
                case 5 -> resetDeviceMenu();
                case 6 -> loadFromFileMenu();
                case 7 -> changeFileNameMenu();
                case 0 -> {
                    view.showMessage("Выход из интерактивного режима.");
                    return;
                }
            }
        }
    }

    private void initDefaultDevices() {
        if (devices.isEmpty()) {
            devices.add(new AirConditioner(1, "Гостиная", 22.0));
            devices.add(new Oven(2, "Кухня", 180.0));
            devices.add(new WirelessRadiatorThermostat(3, "Спальня", 21.0));
        }
    }

    private Thermostat selectDevice() {
        if (devices.isEmpty()) {
            view.showMessage("Список устройств пуст.");
            return null;
        }
        view.showDevices(devices);
        int id = view.readPositiveInt(scanner, "Введите ID устройства: ", 1, Integer.MAX_VALUE);
        for (Thermostat d : devices) {
            if (d.getId() == id) return d;
        }
        view.showMessage("Устройство с ID " + id + " не найдено.");
        return null;
    }

    private void addDeviceMenu() {
        view.showMessage("""
                --- Выберите тип устройства ---
                1. Кондиционер (AirConditioner)
                2. Духовка (Oven)
                3. Радиатор (WirelessRadiatorThermostat)
                0. Назад""");

        int type = view.readPositiveInt(scanner, "Тип: ", 0, 3);
        if (type == 0) return;

        int id = view.readPositiveInt(scanner, "Введите ID: ", 1, 10000);
        String location = view.readString(scanner, "Введите локацию: ");
        double target = view.readDouble(scanner, "Введите целевую температуру: ", -50.0, 300.0);

        Thermostat device = switch (type) {
            case 1 -> new AirConditioner(id, location, target);
            case 2 -> new Oven(id, location, target);
            case 3 -> new WirelessRadiatorThermostat(id, location, target);
            default -> null;
        };

        if (device != null) {
            devices.add(device);
            view.showMessage("Устройство успешно добавлено!");
        }
    }

    private void removeDeviceMenu() {
        Thermostat d = selectDevice();
        if (d != null) {
            devices.remove(d);
            view.showMessage("Устройство ID " + d.getId() + " удалено.");
        }
    }

    private void editTemperatureMenu() {
        Thermostat d = selectDevice();
        if (d == null) return;

        double newTemp = view.readDouble(scanner, "Введите новую температуру: ", -50.0, 300.0);
        boolean success = d.setTemperature(newTemp);
        if (success) {
            view.showMessage("Температура успешно обновлена до " + newTemp + " " + d.getUnit());
        } else {
            view.showMessage("Недопустимая температура для данного устройства! Установлено значение по умолчанию (20.0).");
        }
    }

    private void resetDeviceMenu() {
        Thermostat d = selectDevice();
        if (d == null) return;
        d.resetSettings();
        view.showMessage("Настройки устройства ID " + d.getId() + " сброшены.");
    }

    private void loadFromFileMenu() {
        try {
            List<Thermostat> loaded = manager.readFromFile(currentFileName);
            devices.clear();
            devices.addAll(loaded);
            view.showMessage("Успешно загружено корректных устройств: " + loaded.size());
        } catch (IOException e) {
            view.showError("Ошибка чтения файла: " + e.getMessage());
        }
    }

    private void changeFileNameMenu() {
        currentFileName = view.readString(scanner, "Введите имя файла для чтения: ");
        view.showMessage("Имя файла изменено на: " + currentFileName);
    }
}