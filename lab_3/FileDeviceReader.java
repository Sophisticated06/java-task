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

        // Проверяем, существует ли файл
        if (!file.exists()) {
            System.out.println("Ошибка: Файл \"" + fileName + "\" не найден на диске!");
            return loadedDevices;
        }

        

    }
}