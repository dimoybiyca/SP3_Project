package ua.lpnu.ngdeck.services.serial;

import jssc.SerialPort;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;
import ua.lpnu.ngdeck.data.Codes;
import ua.lpnu.ngdeck.data.Commands;
import ua.lpnu.ngdeck.models.Project;
import ua.lpnu.ngdeck.services.Angular.AngularService;
import ua.lpnu.ngdeck.services.projects.ProjectService;
import ua.lpnu.ngdeck.services.projects.ProjectUtils;

import java.util.List;

public class PortReader implements SerialPortEventListener {

    private  SerialPort serialPort;
    private final ProjectService projectService;
    private final AngularService angularService;

    public PortReader(SerialPort serialPort) {
        this.serialPort = serialPort;
        this.projectService = ProjectService.getInstance();
        this.angularService = AngularService.getInstance();
    }

    @Override
    public void serialEvent(SerialPortEvent event) {
        if (event.isRXCHAR() && event.getEventValue() > 0) {
            try {
                String receivedData = serialPort.readString(event.getEventValue());
                System.out.println("Received: " + receivedData);

                if(receivedData.startsWith(Commands.LAUNCH_PROJECT)) {
                   startProject(receivedData);
                }

                switch (receivedData) {
                    case Commands.CHECK_CONNECTION -> sendCode(Codes.OK);
                    case Commands.GET_LIST -> sendList();
                    case Commands.REBOOT_PROJECT -> angularService.reboot();
                    case Commands.STOP_PROJECT -> angularService.stop();
                    case Commands.CHECK_STATE -> checkStatus();
                }
            } catch (SerialPortException | InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void checkStatus() {
        if (projectService.isListChanged()) {
            sendCode(Codes.WAS_CHANGE);
        } else {
            sendCode(Codes.OK);
        }
    }

    private void startProject(String receivedData) {
        String projectName = receivedData.substring(4);
        Project project = projectService.getProjects().stream()
                .filter(p -> p.getName().equals(projectName))
                .findFirst()
                .orElse(null);

        angularService.start(project);
    }

    private void sendList() throws InterruptedException {
        List<Project> projectList = projectService.getProjects();
        this.send(projectList.size() + ",");
        this.send(ProjectUtils.formatToSend(projectList));
        projectService.isActual();
    }

    private void sendCode(int code) {
        this.send(String.valueOf(code));
    }

    private void send(String message) {
        try {
            serialPort.writeString(message);
            System.out.println("Send: " + message);
        } catch (SerialPortException e) {
            throw new RuntimeException(e);
        }
    }
}
