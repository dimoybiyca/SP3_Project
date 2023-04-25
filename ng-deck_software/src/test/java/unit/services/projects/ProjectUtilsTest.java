package unit.services.projects;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import ua.lpnu.ngdeck.models.Project;
import ua.lpnu.ngdeck.services.projects.ProjectUtils;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class ProjectUtilsTest {

    @Test
    @DisplayName("Test formatToSend method")
    public void formatToSendTest() {
        Project project1 = Project.builder()
                .name("project1")
                .path("path1")
                .runArguments("arg1")
                .build();

        Project project2 = Project.builder()
                .name("project2")
                .path("path2")
                .runArguments("arg2")
                .build();

        String expected = "project1,project2,";

        Assertions.assertEquals(expected, ProjectUtils.formatToSend(List.of(project1, project2)));
    }

    @Test
    @DisplayName("Test isAngular method with not a directory")
    public void isAngularTest_NotADirectory(@Mock File file) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {

        Mockito.when(file.isDirectory()).thenReturn(false);

        Assertions.assertEquals(false, getIsAngularMethod().invoke(null , file));
    }

    @Test
    @DisplayName("Test isAngular method with valid project")
    public void isAngularTest_ValidProject(@Mock File file) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        File[] list = new File[]{
                new File("projects/angular.json"),
                new File("projects/node_modules")};

        Mockito.when(file.isDirectory()).thenReturn(true);
        Mockito.when(file.listFiles()).thenReturn(list);

        Assertions.assertEquals(true, getIsAngularMethod().invoke(null , file));
    }



    @Test
    @DisplayName("Test isAngular method with invalid project")
    public void isAngularTest_InvalidProject(@Mock File file) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        File[] list = new File[]{new File("projects/angular.json")};

        Mockito.when(file.isDirectory()).thenReturn(true);
        Mockito.when(file.listFiles()).thenReturn(list);

        Assertions.assertEquals(false, getIsAngularMethod().invoke(null , file));
    }

    private Method getIsAngularMethod() throws NoSuchMethodException {
        Method method = ProjectUtils.class.getDeclaredMethod("isAngular", File.class);
        method.setAccessible(true);
        return method;
    }
}
