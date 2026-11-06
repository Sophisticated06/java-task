package lab_3;

import lab_3.controller.MainController;
import lab_3.model.ThermostatManager;
import lab_3.view.ConsoleView;

public class Main {
    public static void main(String[] args) {
        ConsoleView view = new ConsoleView();
        ThermostatManager manager = new ThermostatManager(view);
        MainController controller = new MainController(manager, view);
        
        controller.handle(args);
    }
}
