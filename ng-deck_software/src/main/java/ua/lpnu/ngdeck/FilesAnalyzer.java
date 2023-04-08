package ua.lpnu.ngdeck;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FilesAnalyzer {

    public static List<String> find() {
        return FilesAnalyzer.findProjects(System.getProperty("user.home")+ "/VSCodeWorkspace").stream()
                .map(File::getName)
                .collect(Collectors.toList());
    }

    public static List<String> getData() {
        List<String> data = FilesAnalyzer.find();

        data = data.stream()
                .map(str -> str.length() > 16 ? str.substring(0, 16): str)
                .toList();

        return data;
    }

    public static ArrayList<File> findProjects() {
        return findProjects(System.getProperty("user.home")+ "/VSCodeWorkspace");
    }
    public static ArrayList<File> findProjects(String path) {
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

            if(subFiles == null) {
                return false;
            }

            for (File subFile : subFiles) {
                if (subFile.getName().equals("angular.json")) {
                    return true;
                }
            }
        }

        return false;
    }
}
