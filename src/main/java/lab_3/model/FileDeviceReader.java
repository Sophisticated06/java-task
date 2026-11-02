package lab_3.model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FileDeviceReader {
    private String fileName; // Имя файла для чтения

    // Конструктор
    public FileDeviceReader(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    // Метод чтения файла с устройствами
    public List<Thermostat> readDevicesFromFile() {
        List<Thermostat> loadedDevices = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName, StandardCharsets.UTF_8))) {
            String line;
            String currentType = null;
            Map<String, String> properties = new HashMap<>();

            while ((line = reader.readLine()) != null) {
                line = line.trim();

                // Пропускаем пустые строки и комментарии
                if (line.isEmpty() || line.startsWith("#")) {
                    continue;
                }

                // Если строка в квадратных скобках — это новый тип устройства
                if (line.startsWith("[") && line.endsWith("]")) {
                    // Если перед этим мы накопили свойства предыдущего прибора — создаем его
                    if (currentType != null) {
                        Thermostat device = createDeviceFromProperties(currentType, properties);
                        if (device != null) {
                            loadedDevices.add(device);
                        }
                    }

                    // Запоминаем новый тип и очищаем свойства для него
                    currentType = line.substring(1, line.length() - 1);
                    properties.clear();
                    continue;
                }

                // Парсим параметры вида "ключ = значение"
                String[] parts = line.split("=", 2);
                if (parts.length == 2) {
                    String key = parts[0].trim();
                    String value = parts[1].trim();
                    properties.put(key, value);
                }
            }

            // Не забываем добавить самый последний прибор в файле после завершения цикла
            if (currentType != null) {
                Thermostat device = createDeviceFromProperties(currentType, properties);
                if (device != null) {
                    loadedDevices.add(device);
                }
            }

        } catch (IOException e) {
            System.err.println("Ошибка чтения файла конфигурации: " + e.getMessage());
        }

        return loadedDevices;
    }

    private double parseDoubleSafe(String rawValue, double defaultValue) {
        if (rawValue == null || rawValue.isEmpty()) {
            return defaultValue;
        }
        try {
            String cleaned = rawValue.replaceAll("[^0-9.,-].*$", "").trim();
            cleaned = cleaned.replace(",", ".");
            return Double.parseDouble(cleaned);
        } catch (NumberFormatException e) {
            System.err.println("[ОШИБКА ПАРСИНГА] Не удалось прочитать число из значения: \"" + rawValue + "\". Используется по умолчанию: " + defaultValue);
            return defaultValue;
        }
    }

    
    private Thermostat createDeviceFromProperties(String type, Map<String, String> properties) {
        try {
            // Проверяем обязательные поля
            if (!properties.containsKey("location") || !properties.containsKey("unit")) {
                System.err.println("[ОШИБКА ДАННЫХ] Пропущены обязательные поля (location или unit) для прибора: " + type);
                return null; // Пропускаем поврежденный прибор, но программа не падает
            }

            String location = properties.get("location");
            String unit = properties.get("unit");

            // Безопасное чтение targetTemperature
            double targetTemp = 20.0;
            if (properties.containsKey("targetTemperature")) {
                targetTemp = parseDoubleSafe(properties.get("targetTemperature"), 20.0);
            }

            // Создаем объект в зависимости от типа прибора
            switch (type) {
                case "AirConditioner": {
                    double maxLimit = targetTemp + 20.0;
                    if (properties.containsKey("maxLimitTemperature")) {
                        maxLimit = parseDoubleSafe(properties.get("maxLimitTemperature"), targetTemp + 20.0);
                    }
                    return new AirConditioner(location, unit, targetTemp, maxLimit);
                }
                    
                case "Oven": {
                    double maxLimit = targetTemp + 200.0;
                    if (properties.containsKey("maxLimitTemperature")) {
                        maxLimit = parseDoubleSafe(properties.get("maxLimitTemperature"), targetTemp + 200.0);
                    }
                    return new Oven(location, unit, targetTemp, maxLimit);
                }
                    
                case "WirelessRadiatorThermostat": {
                    double maxLimit = targetTemp + 20.0;
                    if (properties.containsKey("maxLimitTemperature")) {
                        maxLimit = parseDoubleSafe(properties.get("maxLimitTemperature"), targetTemp + 20.0);
                    }
                    return new WirelessRadiatorThermostat(location, unit, targetTemp, maxLimit);
                }
                    
                default:
                    System.err.println("[ПРЕДУПРЕЖДЕНИЕ] Неизвестный тип прибора в файле: " + type);
                    return null; // Неизвестные приборы просто пропускаются
            }

        } catch (Exception e) {
            System.err.println("[КРИТИЧЕСКАЯ ОШИБКА] Не удалось создать прибор " + type + ": " + e.getMessage());
            return null;
        }
    }
}

