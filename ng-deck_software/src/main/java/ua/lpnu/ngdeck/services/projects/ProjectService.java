package ua.lpnu.ngdeck.services.projects;


import java.io.File;
import java.util.List;
import lombok.extern.log4j.Log4j2;

import ua.lpnu.ngdeck.config.ConfigService;
import ua.lpnu.ngdeck.models.Project;

@Log4j2
public class ProjectService {
    private static ProjectService projectServiceSingleton;

    private final FilesMonitor projectsMonitor = new FilesMonitor();
    private final ConfigService configService = ConfigService.getInstance();

    private List<File> projects;
    private boolean listChanged = false;

    private ProjectService() {
        projectsMonitor.start();
        projects = ProjectUtils.getProjects(configService.getDirectoriesToWatch());
    }

    public static ProjectService getInstance() {
        if(projectServiceSingleton == null) {
            projectServiceSingleton = new ProjectService();
        }

        return projectServiceSingleton;
    }

    public boolean isListChanged() {
        this.checkList();
        return listChanged;
    }

    public void isActual() {
        listChanged = false;
    }

    public void checkList() {
        log.trace("checkList call");
        if(projectsMonitor.isChanged()) {
            List<File> newProjects = ProjectUtils.getProjects(configService.getDirectoriesToWatch());
            listChanged = !newProjects.equals(projects);
            log.trace("is list of projects changed: {}", listChanged);

            if(listChanged) {
                projects = newProjects;
            }

            projectsMonitor.isActual();
        }
    }

    public List<Project> getProjects() {
        this.checkList();
        return projects.stream()
                .map(Project::ofFile)
                .map(project -> {
                    Project configProject = configService.getProject(project.getName());

                    if(configProject != null) {
                        if(configProject.getPath() == null || configProject.getPath().isBlank()) {
                            configProject.setPath(project.getPath());
                        }
                        return configProject;
                    }
                    return project;
                })
                .toList();
    }
}
