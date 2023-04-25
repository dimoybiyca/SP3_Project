package unit.services.projects;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ua.lpnu.ngdeck.services.projects.ProjectService;

public class ProjectServiceTest {

    @Test
    public void singletonTest() {
        ProjectService expected = ProjectService.getInstance();
        ProjectService actual = ProjectService.getInstance();

        Assertions.assertEquals(expected, actual);
    }
}
