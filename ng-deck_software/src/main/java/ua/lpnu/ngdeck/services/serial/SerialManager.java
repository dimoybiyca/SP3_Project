package ua.lpnu.ngdeck.services.serial;

import jssc.SerialPort;
import jssc.SerialPortException;

import java.util.Objects;

public class SerialManager extends Thread{
    private String portName = "";
    private SerialPort serialPort;
    private PortReader portReader;
    private final ConnectionMonitor connectionMonitor = new ConnectionMonitor();

    public SerialManager() {
        connectionMonitor.start();
        serialPort = connectionMonitor.getSerialPort();
    }

    @Override
    public void run() {
        while (true) {
            if(!Objects.equals(portName, connectionMonitor.getPortName())) {
                this.portName = connectionMonitor.getPortName();
                serialPort = connectionMonitor.getSerialPort();
                startListening();
            }
        }
    }

    public String getPortName() {
        return portName;
    }

    private void startListening() {
        System.out.println("Start listening on port " + portName);
        try {
            this.portReader = new PortReader(serialPort);
            serialPort.addEventListener(portReader, SerialPort.MASK_RXCHAR);
        } catch (SerialPortException e) {
            throw new RuntimeException(e);
        }
    }
}
