package lab_3.view;

import java.util.List;
import java.util.Scanner;

import lab_3.model.Thermostat;

public class ConsoleView {

    public void showHelp() {
        System.out.println("----- Справка по использованию термостатов -----");
        System.out.println("  --help, -h                  : Показать эту справку");
        System.out.println("  --read, -r <file>           : Считать корректные приборы из файла");
        System.out.println("  --cycle, -c <sec> <file>    : Циклический опрос приборов");
        System.out.println("  --csv <sec> <in> <out>      : Циклическая запись в CSV");
        System.out.println("  --legacy, -l                : Запустить интерактивное меню");
    }

    public void showError(String message) {
        System.out.println("ПРЕДУПРЕЖДЕНИЕ: " + message);
    }

    public void showMessage(String message) {
        System.out.println(message);
    }

    public void showDevices(List<Thermostat> devices) {
        if (devices == null || devices.isEmpty()) {
            System.out.println("Список устройств пуст.");
            return;
        }
        System.out.println("--- Список устройств ---");
        for (Thermostat device : devices) {
            System.out.println(device);
        }
        System.out.println("------------------------");
    }

    public int readPositiveInt(Scanner scanner, String prompt, int min, int max) {
        while (true) {
            System.out.print(prompt);
            if (scanner.hasNextInt()) {
                int val = scanner.nextInt();
                scanner.nextLine(); // очистка буфера
                if (val >= min && val <= max) {
                    return val;
                }
            } else {
                scanner.nextLine(); // сброс неверного ввода
            }
            System.out.println("Неверный ввод. Пожалуйста, введите число от " + min + " до " + max);
        }
    }

    public double readDouble(Scanner scanner, String prompt, double min, double max) {
        while (true) {
            System.out.print(prompt);
            if (scanner.hasNextDouble()) {
                double val = scanner.nextDouble();
                scanner.nextLine();
                if (val >= min && val <= max) {
                    return val;
                }
            } else {
                scanner.nextLine();
            }
            System.out.println("Введите число в диапазоне от " + min + " до " + max);
        }
    }

    public String readString(Scanner scanner, String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }
}