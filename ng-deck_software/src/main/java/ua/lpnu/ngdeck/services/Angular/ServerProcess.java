package ua.lpnu.ngdeck.services.Angular;

import ua.lpnu.ngdeck.models.Project;

import java.io.File;
import java.io.IOException;


public class ServerProcess extends Thread{
    private final Project project;
    private Process process;

    public ServerProcess(Project project) {
        this.project = project;
    }

    @Override
    public void run() {
        try {
            ProcessBuilder pb = new ProcessBuilder(project.getRunArguments());
            pb.directory(new File(project.getPath()));
            process = pb.start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void stopServer() {
        process.destroy();
    }
}
