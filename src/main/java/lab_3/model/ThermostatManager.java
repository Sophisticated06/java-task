package lab_3.model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import lab_3.view.ConsoleView;

public class ThermostatManager {
    private final ConsoleView view;

    public ThermostatManager(ConsoleView view) {
        this.view = view;
    }

    // Метод с выводом ошибок (для --read)
    public List<Thermostat> readFromFile(String filename) throws IOException {
        return readFromFile(filename, true);
    }

    // Универсальный метод с возможностью отключить вывод ошибок (для --cycle и --csv)
    public List<Thermostat> readFromFile(String filename, boolean verbose) throws IOException {
        List<Thermostat> devices = new ArrayList<>();
        
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            int lineNumber = 0;
            
            String currentType = null;
            Map<String, String> currentProps = new HashMap<>();
            int blockStartLine = 0;

            while ((line = br.readLine()) != null) {
                lineNumber++;
                String trimmed = line.trim();

                if (trimmed.isEmpty() || trimmed.startsWith("#")) {
                    continue;
                }

                if (trimmed.startsWith("[") && trimmed.endsWith("]")) {
                    if (currentType != null) {
                        processDeviceBlock(currentType, currentProps, blockStartLine, devices, verbose);
                        currentProps.clear();
                    }
                    
                    currentType = trimmed.substring(1, trimmed.length() - 1).trim();
                    blockStartLine = lineNumber;
                    continue;
                }

                if (currentType == null) {
                    if (verbose) {
                        view.showError("Строка " + lineNumber + ": неожиданный текст вне блоков устройств («" + trimmed + "»)");
                    }
                    continue;
                }

                if (trimmed.contains("=")) {
                    int eqIndex = trimmed.indexOf('=');
                    String key = trimmed.substring(0, eqIndex).trim();
                    String value = trimmed.substring(eqIndex + 1).trim();
                    
                    if (value.contains("#")) {
                        if (verbose) {
                            view.showError("Строка " + lineNumber + ": некорректное значение с комментариями/мусором («" + value + "»)");
                        }
                        value = value.substring(0, value.indexOf('#')).trim();
                    }
                    
                    currentProps.put(key, value);
                } else {
                    if (verbose) {
                        view.showError("Строка " + lineNumber + ": неожиданный текст в блоке («" + trimmed + "»)");
                    }
                }
            }

            if (currentType != null) {
                processDeviceBlock(currentType, currentProps, blockStartLine, devices, verbose);
            }
        }
        return devices;
    }

    private void processDeviceBlock(String type, Map<String, String> props, int lineNum, List<Thermostat> devices, boolean verbose) {
        if (!type.equals("AirConditioner") && !type.equals("Oven") && !type.equals("WirelessRadiatorThermostat")) {
            if (verbose) {
                view.showError("Строка " + lineNum + ": неизвестный тип прибора («" + type + "»)");
            }
            return;
        }

        if (!props.containsKey("location")) {
            if (verbose) {
                view.showError("Строка " + lineNum + ": обязательное поле location НАМЕРЕННО УДАЛЕНО для типа " + type);
            }
            return;
        }

        if (!props.containsKey("unit")) {
            if (verbose) {
                view.showError("Строка " + lineNum + ": обязательное поле unit НАМЕРЕННО УДАЛЕНО для типа " + type);
            }
            return;
        }

        try {
            int id = devices.size() + 1;
            String location = props.get("location");
            
            double targetTemp = 20.0;
            if (props.containsKey("targetTemperature")) {
                String tempStr = props.get("targetTemperature").replace(',', '.');
                targetTemp = Double.parseDouble(tempStr);
            }

            Thermostat device = switch (type) {
                case "AirConditioner" -> new AirConditioner(id, location, targetTemp);
                case "Oven" -> new Oven(id, location, targetTemp);
                case "WirelessRadiatorThermostat" -> new WirelessRadiatorThermostat(id, location, targetTemp);
                default -> null;
            };

            if (device != null) {
                if (props.containsKey("maxLimitTemperature")) {
                    try {
                        String limitStr = props.get("maxLimitTemperature").replace(',', '.');
                        Double.parseDouble(limitStr);
                    } catch (NumberFormatException e) {
                        if (verbose) {
                            view.showError("Строка " + lineNum + ": некорректное значение maxLimitTemperature («" + props.get("maxLimitTemperature") + "»)");
                        }
                    }
                }
                devices.add(device);
            }
        } catch (NumberFormatException e) {
            if (verbose) {
                view.showError("Строка " + lineNum + ": ошибка формата числа в блоке устройства " + type);
            }
        }
    }

    public void logToCsv(List<Thermostat> devices, String outputFile) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(outputFile, true))) {
            java.io.File file = new java.io.File(outputFile);
            if (file.length() == 0) {
                writer.println("Timestamp,ID,Type,Location,TargetTemperature,MaxLimitTemperature");
            }
            String timestamp = LocalDateTime.now().toString();
            for (Thermostat d : devices) {
                writer.printf(Locale.US, "%s,%d,%s,%s,%.1f,%.1f%n",
                        timestamp,
                        d.getId(),
                        d.getClass().getSimpleName(),
                        d.getLocation(),
                        d.getTargetTemperature(),
                        d.getMaxLimitTemperature()
                );
            }
        }
    }
}