package unit.services.projects;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ua.lpnu.ngdeck.services.projects.FilesMonitor;

public class  FilesMonitorTest {

    FilesMonitor filesMonitor = new FilesMonitor();

    @Test
    public void isActualTest() {
        filesMonitor.isActual();
        Assertions.assertFalse(filesMonitor.isChanged());
    }
}
