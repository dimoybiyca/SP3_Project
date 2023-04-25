package e2e;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ua.lpnu.ngdeck.config.ConfigService;
import ua.lpnu.ngdeck.models.Config;
import ua.lpnu.ngdeck.models.Project;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class ConfigTest {

    @Test
    public void createConfigTest() {
        File config = new File(System.getProperty("user.home") + "/.ng-deck/config.json");

        if(config.exists()) {
            config.delete();
        }

        Assertions.assertFalse(config.exists());

        ConfigService configService = ConfigService.getInstance();
        Assertions.assertEquals(Config.defaultConfig(), configService.getConfig());
    }

    @Test
    public void getDirectoriesToWatchTest() {
        ConfigService configService = ConfigService.getInstance();

        Assertions.assertArrayEquals(
                new String[]{System.getProperty("user.home") + "/Projects"},
                configService.getDirectoriesToWatch());
    }

    @Test
    public void getConfigTest() {
        ConfigService configService = ConfigService.getInstance();

        Assertions.assertEquals(Config.defaultConfig(), configService.getConfig());
    }

    @Test
    public void invalidJsonTest() throws IOException {
        File config = new File(System.getProperty("user.home") + "/.ng-deck/config.json");

        if(!config.exists()) {
            config.createNewFile();
        }

        FileWriter fileWriter = new FileWriter(config);
        fileWriter.write("Some trash");
        fileWriter.close();

        ConfigService configService = ConfigService.getInstance();

        Assertions.assertEquals(Config.defaultConfig(), configService.getConfig());
    }

    @Test
    public void getProject_NotExist() {
        ConfigService configService = ConfigService.getInstance();

        Assertions.assertNull(configService.getProject("project1"));
    }

    @Test
    public void getProject_Existing() throws IOException {
        ConfigService configService = ConfigService.getInstance();

        File config = new File(System.getProperty("user.home") + "/.ng-deck/config.json");

        if(!config.exists()) {
            config.createNewFile();
        }

        FileWriter fileWriter = new FileWriter(config);
        fileWriter.write("{ \"directoriesToWatch\": [" +
                "\"/home/dimoybiyca/VSCodeWorkspace/Angular\"," +
                "\"/home/dimoybiyca/Projects\"]," +
                "\"projects\": [{\"name\": \"RecipeBook\",\"path\": \"\", \"runArguments\": \"ng serve -o\"}]}"
        );
        fileWriter.close();

        Project expected = Project.builder()
                .name("RecipeBook")
                .path("")
                .runArguments("ng serve -o")
                .build();

        Assertions.assertEquals(expected, configService.getProject("RecipeBook"));
        config.delete();
        configService.getProject("1");
    }
}
