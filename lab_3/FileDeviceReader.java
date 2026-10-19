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
import java.util.Properties;

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

                if (line.startsWith("[") && line.endsWith("]")){
                    if (currentType != null) {
                        Thermostat device = createDeviceFromProperties(currentType, currentProperties, totalPropertiesRead);
                        if (device != null) {
                            loadedDevices.add(device);
                            totalObjectsFound++;
                        }
                    }

                    currentType = line.substring(1, line.length() - 1).trim();
                    currentProperties.clear(); // Очищаем карту для нового прибора
                    continue;
                }
            }
        }
         catch (IOException e){
            System.out.println("Ошибка при чтении файла: " + e.getMessage());
         }
        

    }
}