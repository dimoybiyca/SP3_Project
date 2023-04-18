package ua.lpnu.ngdeck.services.serial;

public class SerialService {
    private final SerialManager serialManager = new SerialManager();

    public SerialService() {
        serialManager.start();
    }
}
