package lab_3.client;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Properties;

import lab_3.view.ConsoleView;

public class ClientMain {

    public static void main(String[] args) {
        ConsoleView view = new ConsoleView();
        Properties props = new Properties();

        // Загрузка сетевых настроек из файла Application.properties
        try (FileInputStream fis = new FileInputStream("Application.properties")) {
            props.load(fis);
        } catch (IOException e) {
            view.showError("Не удалось загрузить Application.properties: " + e.getMessage());
            return;
        }

        // Извлекаем хост и порт с заданием значений по умолчанию
        String host = props.getProperty("server.host", "127.0.0.1");
        int port = Integer.parseInt(props.getProperty("server.port", "5555"));

        view.showMessage("[клиент] Чтение конфигурации: Сервер -> " + host + ":" + port);
        view.showMessage("[клиент] Установка соединения по TCP с сервером...");

        // Устанавка сетевого соединения и открытие потоков обмена данными
        try (Socket socket = new Socket(host, port);
             PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            writer.println("GET_DEVICES");
            view.showMessage("[клиент] Запрос GET_DEVICES успешно отправлен.");

            // Читаем ответ от сервера построчно
            String line;
            view.showMessage("\n--- ОТВЕТ СЕРВЕРА (Состояние устройств) ---");
            while ((line = reader.readLine()) != null) {
        
                String[] parts = line.split(";");
                if (parts.length >= 6) {
                    System.out.printf("Тип: %-25s | ID: %-2s | Локация: %-10s | Тек: %5s°C | Цель: %5s°C | Предел: %5s°C%n",
                            parts[0], parts[1], parts[2], parts[3], parts[4], parts[5]);
                } else {
                    System.out.println(line);
                }
            }
            view.showMessage("-------------------------------------------");

        } catch (IOException e) {
            view.showError("Сетевая ошибка клиента: " + e.getMessage());
        }
    }
}