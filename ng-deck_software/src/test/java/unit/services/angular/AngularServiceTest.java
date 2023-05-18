package unit.services.angular;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ua.lpnu.ngdeck.services.Angular.AngularService;

public class AngularServiceTest {

    @Test
    public void singletonTest() {
        AngularService expected = AngularService.getInstance();
        AngularService actual = AngularService.getInstance();

        Assertions.assertEquals(expected, actual);
    }
}
