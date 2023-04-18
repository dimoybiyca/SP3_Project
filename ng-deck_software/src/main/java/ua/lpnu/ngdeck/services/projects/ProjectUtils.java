package ua.lpnu.ngdeck.services.projects;

import ua.lpnu.ngdeck.models.Project;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ProjectUtils {

    public static String formatToSend(List<Project> projects) {

        List<String> projectList =  projects.stream()
                .map(Project::getName)
                .map(str -> str.length() > 16 ? str.substring(0, 16): str)
                .toList();

        return projectList.toString()
                .substring(1)
                .replaceAll(", ", ",")
                .replaceAll("]", ",");
    }

    public static ArrayList<File> getProjects(String[] directories) {
        ArrayList<File> projects = new ArrayList<>();

        for(String dir : directories) {
            projects.addAll(findProjects(dir));
        }

        return projects;
    }

    private static ArrayList<File> findProjects(String path) {
        File file = new File(path);
        ArrayList<File> result = new ArrayList<>();

        File[] subFiles = file.listFiles();

        if(subFiles == null) {
            return new ArrayList<>();
        }

        for (File subFile : subFiles) {
            if (subFile.isDirectory()) {
                if (isAngular(subFile)) {
                    result.add(subFile);
                } else {
                    result.addAll(findProjects(subFile.getPath()));
                }
            }
        }

        return result;
    }

    private static boolean isAngular(File file) {
        if(file.isDirectory()) {
            File[] subFiles = file.listFiles();

            if (subFiles != null) {
                return Arrays.stream(subFiles)
                        .map(File::getName)
                        .filter(name -> name.equals("angular.json") || name.equals("node_modules"))
                        .toList().size() == 2;
            }
        }

        return false;
    }
}
