package ua.lpnu.ngdeck.services.Angular;

import lombok.extern.log4j.Log4j2;
import ua.lpnu.ngdeck.models.Project;

@Log4j2
public class AngularService {
    private static AngularService angularServiceSingleton;
    private Project activeProject;
    private ServerProcess serverProcess;

    private AngularService() {}

    public static AngularService getInstance() {
        if(angularServiceSingleton == null) {
            angularServiceSingleton = new AngularService();
        }

        return angularServiceSingleton;
    }

    public void start(Project project) {
        log.info("start call");
        this.activeProject = project;
        serverProcess = new ServerProcess(activeProject);
        serverProcess.start();
    }

    public void reboot() {
        log.info("reboot call");
        this.stop();
        this.start(activeProject);
    }

    public void stop() {
        log.info("stop call");
        serverProcess.stopServer();
    }
}
