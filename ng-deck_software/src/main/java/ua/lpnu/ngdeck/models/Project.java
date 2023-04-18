package ua.lpnu.ngdeck.models;

import lombok.*;

import java.io.File;

@Builder
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class Project {
    private String name;
    private String path;
    private String runArguments;

    public static Project ofFile(File file) {
        return  Project.builder()
                .name(file.getName())
                .path(file.getPath())
                .runArguments("ng serve")
                .build();
    }

    public String[] getRunArguments() {
        return runArguments.split(" ");
    }
}
