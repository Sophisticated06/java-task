package lab_3;

import java.io.BufferedReader;
import java.io.File;
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

    // Главный метод чтения и парсинга файла
    public List<Thermostat> readDevicesFromFile() {
        List<Thermostat> loadedDevices = new ArrayList<>();
        File file = new File(fileName);

        // Проверка на существование файла
        if (!file.exists()) {
            System.out.println("Ошибка: Файл \"" + fileName + "\" не найден на диске!");
            return loadedDevices;
        }
        
        int totalObjectsFound = 0;
        int totalPropertiesRead = 0;
        int totalPropertiesMissing = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader(file, StandardCharsets.UTF_8))){
            String line;
            String currentType = null;
            Map<String, String> currentProperties = new HashMap<>();

            while ((line = reader.readLine()) != null){
                line = line.trim();

                if (line.isEmpty() || line.startsWith("#")){
                    continue;
                }

                // Распознавание заголовков типа [AirConditioner]
                if (line.startsWith("[") && line.endsWith("]")){
                    if (currentType != null) {
                        Thermostat device = createDeviceFromProperties(currentType, currentProperties);
                        if (device != null) {
                            loadedDevices.add(device);
                            totalObjectsFound++;
                        }
                    }

                    currentType = line.substring(1, line.length() - 1).trim();
                    currentProperties.clear(); // Очищаем карту для нового прибора
                    continue;
                }

                // Парсинг параметров вида key = value
                if (line.contains("=")) {
                    String[] parts = line.split("=", 2);
                    String key = parts[0].trim();
                    String value = parts[1].trim();
                    currentProperties.put(key, value);
                    totalPropertiesRead++;
                }
            }

            // Создание самого последнего устройства в файле после окончания цикла
            if (currentType != null) {
                Thermostat device = createDeviceFromProperties(currentType, currentProperties);
                if (device != null) {
                    loadedDevices.add(device);
                    totalObjectsFound++;
                }
            }

            System.out.println("Успешно загружено приборов: " + totalObjectsFound + 
                               ", прочитано свойств: " + totalPropertiesRead + 
                               ", пропущено/ошибок свойств: " + totalPropertiesMissing);

        } catch (IOException e){
            System.out.println("Ошибка при чтении файла: " + e.getMessage());
        }

        return loadedDevices; 

    }

    private Thermostat createDeviceFromProperties(String type, Map<String, String> properties) {
    String location = properties.get("location");
    String unit = properties.get("unit");
    
    // Проверяем наличие обязательных полей
    if (location == null || unit == null) {
        System.out.println("Ошибка: у прибора типа [" + type + "] отсутствуют обязательные поля (location или unit).");
        return null;
    }

    // Внутри createDeviceFromProperties:
    if (location == null || unit == null) {
        System.out.println("Ошибка: у прибора типа [" + type + "] отсутствуют обязательные поля (location или unit).");
        return null;
    }

    double targetTemp = properties.containsKey("targetTemperature") 
            ? Double.parseDouble(properties.get("targetTemperature")) : 20.0;
    double maxTemp = properties.containsKey("maxLimitTemperature") 
            ? Double.parseDouble(properties.get("maxLimitTemperature")) : (targetTemp + 20.0);

    Thermostat device = null;

    switch (type) {
        case "AirConditioner":
            device = new AirConditioner(location, unit, targetTemp, maxTemp);
            break;

        case "Oven":
            device = new Oven(location, unit, targetTemp, maxTemp);
            break;

        case "WirelessRadiatorThermostat":
            device = new WirelessRadiatorThermostat(location, unit, targetTemp, maxTemp);
            break;

        default:
            System.out.println("Предупреждение: неизвестный тип прибора в файле — " + type);
            break;
    }

    return device;
}

}



