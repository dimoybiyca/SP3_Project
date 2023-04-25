package unit.models;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ua.lpnu.ngdeck.models.Config;
import ua.lpnu.ngdeck.models.Project;

public class ConfigTest {

    @Test
    public void defaultConfigTest() {
        Config expected = Config.builder()
                .directoriesToWatch(new String[]{System.getProperty("user.home") + "/Projects"})
                .projects(new Project[]{
                        Project.builder()
                                .name("")
                                .path("")
                                .runArguments("ng serve")
                                .build()
                })
                .build();
        Config actual = Config.defaultConfig();

        Assertions.assertEquals(expected, actual);
    }
}
