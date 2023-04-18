package ua.lpnu.ngdeck.services.Angular;

import ua.lpnu.ngdeck.models.Project;

public class AngularService {
    private static AngularService angularServiceSingleton;
    private Project activeProject;
    private ServerProcess serverProcess;

    private AngularService() {
    }

    public static AngularService getInstance() {
        if(angularServiceSingleton == null) {
            angularServiceSingleton = new AngularService();
        }

        return angularServiceSingleton;
    }

    public void start(Project project) {
        this.activeProject = project;
        serverProcess = new ServerProcess(activeProject);
        serverProcess.start();
    }

    public void reboot() {
        this.stop();
        this.start(activeProject);
    }

    public void stop() {
        serverProcess.stopServer();
    }
}
