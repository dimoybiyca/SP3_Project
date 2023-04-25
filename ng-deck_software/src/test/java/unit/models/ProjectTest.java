package unit.models;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ua.lpnu.ngdeck.models.Project;

import java.io.File;

public class ProjectTest {

    @Test
    public void ofFileTest() {
        File file = new File("/usr/home/project1");
        Project actual = Project.ofFile(file);
        Project expected = Project.builder()
                .name("project1")
                .path("/usr/home/project1")
                .runArguments("ng serve")
                .build();
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void getArgumentsTest() {
        Project project = Project.builder()
                .name("project1")
                .path("/usr/home/project1")
                .runArguments("ng serve")
                .build();

        Assertions.assertArrayEquals(new String[]{"ng", "serve"}, project.getRunArguments());
    }
}
