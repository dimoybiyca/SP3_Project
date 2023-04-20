package ua.lpnu.ngdeck.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.gson.stream.JsonReader;
import lombok.extern.log4j.Log4j2;
import ua.lpnu.ngdeck.models.Config;
import ua.lpnu.ngdeck.models.Project;

import java.io.*;
import java.util.Arrays;
import java.util.Objects;

@Log4j2
public class ConfigService {

    private static ConfigService configServiceSingleton;

    private Config config;
    private final Gson gson = new GsonBuilder().setPrettyPrinting().serializeNulls().create();
    private final File configFile = new File(System.getProperty("user.home") + "/.ng-deck/config.json");

    private ConfigService() {
        readConfig();
    }

    public static ConfigService getInstance() {
        if(configServiceSingleton == null) {
            configServiceSingleton = new ConfigService();
        }

        return configServiceSingleton;
    }

    public String[] getDirectoriesToWatch(){
        return getConfig().getDirectoriesToWatch();
    }

    public Project getProject(String name){
        return Arrays.stream(getConfig().getProjects())
                .filter(project -> Objects.equals(project.getName(), name))
                .findFirst()
                .orElse(null);
    }

    public Config getConfig() {
        if(config == null) {
            readConfig();
        }

        return config;
    }

    private void createConfig(){
        log.info("Create default config");
        try {
            Writer writer = new FileWriter(configFile.getPath());
            gson.toJson(Config.defaultConfig(), writer);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            log.error("Error creating config {}", e);
            throw new RuntimeException(e);
        }
    }

    private void readConfig(){
        log.info("Read config");

        if(!configFile.exists()) {
            this.createConfig();
        }

        try {
            JsonReader reader = new JsonReader(new FileReader(configFile));
            config = gson.fromJson(reader, Config.class);
        } catch (FileNotFoundException e) {
            log.error("Config doesnt exist", e);
            config = Config.defaultConfig();
            createConfig();
        } catch (JsonSyntaxException e) {
            log.error("Invalid syntax of config file", e);
            config = Config.defaultConfig();
            createConfig();
        }
    }
}
