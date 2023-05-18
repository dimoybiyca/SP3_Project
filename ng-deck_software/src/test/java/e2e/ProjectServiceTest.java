package e2e;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ua.lpnu.ngdeck.config.ConfigService;
import ua.lpnu.ngdeck.models.Project;
import ua.lpnu.ngdeck.services.projects.ProjectService;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class ProjectServiceTest {
    ProjectService projectService = ProjectService.getInstance();

    @BeforeAll
    public static void setDefaultConfig() {
        File config = new File(System.getProperty("user.home") + "/.ng-deck/config.json");

        if(config.exists()) {
            config.delete();
        }
    }

    @Test
    public void checkListTest() {
        List<Project> expected = List.of(
                Project.builder().name("task-book").path("/home/dimoybiyca/Projects/task-book").runArguments("ng serve").build(),
                Project.builder().name("svitKeramikuClient").path("/home/dimoybiyca/Projects/SvitKeramiku/svitKeramikuClient").runArguments("ng serve").build()
        );

        Assertions.assertEquals(expected, projectService.getProjects());
    }

    @Test
    public void changeFilesTest() {
        File file = new File(System.getProperty("user.home") + "/Projects/testFile1");

        if(file.exists()) {
            file.delete();
        } else {
            file.mkdir();
        }

        Assertions.assertFalse(projectService.isListChanged());
    }

    @Test
    public void checkListTest_WithConfig() throws IOException {
        ConfigService configService = ConfigService.getInstance();
        File config = new File(System.getProperty("user.home") + "/.ng-deck/config.json");

        if(!config.exists()) {
            config.createNewFile();
        }

        FileWriter fileWriter = new FileWriter(config);
        fileWriter.write("{\n" +
                "  \"directoriesToWatch\": [\n" +
                "    \"/home/dimoybiyca/Projects\"\n" +
                "  ],\n" +
                "  \"projects\": [\n" +
                "    {\n" +
                "      \"name\": \"task-book\",\n" +
                "      \"path\": \"\",\n" +
                "      \"runArguments\": \"ng serve -o\"\n" +
                "    }\n" +
                "  ]\n" +
                "}"
        );
        fileWriter.close();

        List<Project> expected = List.of(
                Project.builder().name("task-book").path("/home/dimoybiyca/Projects/task-book").runArguments("ng serve -o").build(),
                Project.builder().name("svitKeramikuClient").path("/home/dimoybiyca/Projects/SvitKeramiku/svitKeramikuClient").runArguments("ng serve").build()
        );

        Assertions.assertEquals(expected, projectService.getProjects());
        config.delete();
        configService.getProject("1");
    }

    @Test
    public void changeFilesTest_CreateNewProject() throws IOException, InterruptedException {
        File file = new File(System.getProperty("user.home") + "/Projects/testFile1");
        File file1 = new File(System.getProperty("user.home") + "/Projects/testFile1/angular.json");
        File file2 = new File(System.getProperty("user.home") + "/Projects/testFile1/node_modules");
        File file3 = new File(System.getProperty("user.home") + "/Projects/testFile2");

        if(!file.exists()) {
            file.mkdir();
        }

        file1.createNewFile();
        file2.mkdir();
        file3.createNewFile();

        Thread.sleep(100);
        boolean actual =  projectService.isListChanged();

        file1.delete();
        file2.delete();
        file.delete();
        file3.delete();

        Assertions.assertTrue(actual);

        Assertions.assertTrue(projectService.isListChanged());
        projectService.isActual();
        Assertions.assertFalse(projectService.isListChanged());
    }
}
