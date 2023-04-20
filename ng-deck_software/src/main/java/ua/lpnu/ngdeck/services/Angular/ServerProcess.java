package ua.lpnu.ngdeck.services.Angular;

import lombok.extern.log4j.Log4j2;
import ua.lpnu.ngdeck.models.Project;

import java.io.File;
import java.io.IOException;

@Log4j2
public class ServerProcess extends Thread{
    private final Project project;
    private Process process;

    public ServerProcess(Project project) {
        this.project = project;
    }

    @Override
    public void run() {
        try {
            log.info("Angular project {} start, arguments: {}", project.getName(), project.getRunArguments());
            ProcessBuilder pb = new ProcessBuilder(project.getRunArguments());
            pb.directory(new File(project.getPath()));
            process = pb.start();
        } catch (IOException e) {
            log.error(e);
            throw new RuntimeException(e);
        }
    }

    public void stopServer() {
        log.info("Angular project stop");
        process.destroy();
    }
}
