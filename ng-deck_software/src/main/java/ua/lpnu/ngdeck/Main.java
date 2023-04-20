package ua.lpnu.ngdeck;

import ua.lpnu.ngdeck.services.projects.ProjectService;
import ua.lpnu.ngdeck.services.serial.SerialManager;

public class Main {

    public static void main(String[] args) {

        ProjectService projectService = ProjectService.getInstance();

        new SerialManager().start();
    }
}