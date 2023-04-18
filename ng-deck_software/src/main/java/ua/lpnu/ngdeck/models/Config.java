package ua.lpnu.ngdeck.models;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class Config {
    private String[] directoriesToWatch;
    private Project[] projects;

    public static Config defaultConfig(){

        return Config.builder()
                .directoriesToWatch(new String[]{System.getProperty("user.home")+ "/Projects"})
                .projects(new Project[]{
                        Project.builder()
                                .name("")
                                .path("")
                                .runArguments("ng serve")
                                .build()
                })
                .build();
    }
}
