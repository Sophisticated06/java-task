package lab_3.view;

import java.util.List;

import lab_3.model.Thermostat;

public class ConsoleView {

    // Вывод справки по аргументам командной строки
    public static void printHelp() {
        System.out.println("--------------------------------------------------");
        System.out.println("       СПРАВКА ПО УПРАВЛЕНИЮ УМНЫМИ ПРИБОРАМИ     ");
        System.out.println("--------------------------------------------------");
        System.out.println("  --help, -h                  Показать эту справку");
        System.out.println("  --read <файл>, -r           Разовый вывод состояния объектов из файла");
        System.out.println("  --cycle <сек>, -c           Цикличный вывод информации (период в секундах)");
        System.out.println("  --csv <сек>                 Логирование параметров в CSV-файл");
        System.out.println("  (без аргументов)            Запуск интерактивного меню");
        System.out.println("--------------------------------------------------");
    }

    // Вывод списка всех устройств
    public static void printDevices(List<Thermostat> devices) {
        if (devices.isEmpty()) {
            System.out.println("Список приборов пуст.");
            return;
        }
        System.out.println("\n--- Список всех зарегистрированных приборов ---");
        for (Thermostat device : devices) {
            System.out.println(device.toString());
        }
    }
}