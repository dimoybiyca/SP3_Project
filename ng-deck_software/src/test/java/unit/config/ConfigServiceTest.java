package unit.config;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import ua.lpnu.ngdeck.config.ConfigService;

public class ConfigServiceTest {

    @Test
    public void singletonTest() {
        ConfigService expected = ConfigService.getInstance();
        ConfigService actual = ConfigService.getInstance();

        Assertions.assertEquals(expected, actual);
    }
}
