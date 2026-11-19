package lab_3.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

import lab_3.controller.ThermostatManager;
import lab_3.model.Thermostat;
import lab_3.view.ConsoleView;

public class ServerMain {
    // Порт, на котором сервер будет слушать входящие подключения
    private static final int PORT = 5555;
    
    // Ограничение MTU
    private static final int MAX_MTU_BUFFER = 1460;

    public static void main(String[] args) {
        ConsoleView view = new ConsoleView();
        ThermostatManager manager = new ThermostatManager(view);
        String configFile = "testik.txt"; // Файл с исходными параметрами термостатов

        System.out.println("[сервер] Запуск многопоточного сервера термостатов на порту " + PORT + "...");

        // Блок try-with-resources. 
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            while (true) {
                // ACCEPT(): блокирующий вызов.
                Socket clientSocket = serverSocket.accept();
                System.out.println("[сервер] Подключился новый клиент с IP: " + clientSocket.getInetAddress());

                // Многопоточность
                new Thread(new ClientHandler(clientSocket, manager, configFile)).start();
            }
        } catch (IOException e) {
            System.out.println("[сервер] Критическая ошибка работы сервера: " + e.getMessage());
        }
    }


     // Отвечает за изоляцию жизненного цикла работы с конкретным клиентом в отдельном потоке.
    private static class ClientHandler implements Runnable {
        private final Socket socket;
        private final ThermostatManager manager;
        private final String configFile;

        public ClientHandler(Socket socket, ThermostatManager manager, String configFile) {
            this.socket = socket;
            this.manager = manager;
            this.configFile = configFile;
        }

        @Override
        public void run() {
            // Открываем потоки ввода/вывода для работы с сетевым сокетом клиента
            try (
                BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter writer = new PrintWriter(socket.getOutputStream(), true)
            ) {
                String request = reader.readLine();
                System.out.println("[сервер] Получен запрос от " + socket.getInetAddress() + ": " + request);

                if ("GET_DEVICES".equals(request)) {
                    List<Thermostat> devices = manager.readFromFile(configFile, false);
                    
                    for (Thermostat d : devices) {
                        d.simulateFluctuation();
                    }

                    StringBuilder packetBuilder = new StringBuilder();
                    for (Thermostat d : devices) {
                        // Форматируем каждую запись устройства в текстовый протокол
                        String record = String.format("%s;%d;%s;%.1f;%.1f;%.1f\n",
                                d.getClass().getSimpleName(),
                                d.getId(),
                                d.getLocation(),
                                d.getCurrentTemperature(),
                                d.getTargetTemperature(),
                                d.getMaxLimitTemperature());
                        
                        // Проверяем: если текущий пакет превышает условный размер MTU, 
                        // отправляем его частями.
                        if (packetBuilder.length() + record.length() > MAX_MTU_BUFFER) {
                            writer.print(packetBuilder.toString());
                            writer.flush();
                            packetBuilder.setLength(0); // Очищаем буфер под следующий пакет
                        }
                        packetBuilder.append(record);
                    }

                    // Отправляем остаток данных, не вошедших в предыдущий лимит
                    if (packetBuilder.length() > 0) {
                        writer.print(packetBuilder.toString());
                        writer.flush();
                    }
                    
                    System.out.println("[сервер] Пакет данных успешно отправлен клиенту " + socket.getInetAddress());
                } else {
                    writer.println("ERROR: Unknown command");
                }
            } catch (IOException e) {
                System.out.println("[сервер] Ошибка в потоке клиента: " + e.getMessage());
            } finally {
                try {
                    socket.close();
                    System.out.println("[сервер] Соединение с клиентом закрыто.");
                } catch (IOException e) {
                }
            }
        }
    }
}
