package ua.lpnu.ngdeck.services.serial;

import jssc.SerialPort;
import jssc.SerialPortException;
import lombok.extern.log4j.Log4j2;

import java.util.Objects;

@Log4j2
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
        log.info("SerialManager Thread start");
        while (true) {
            if(!Objects.equals(portName, connectionMonitor.getPortName())) {
                this.portName = connectionMonitor.getPortName();
                serialPort = connectionMonitor.getSerialPort();
                startListening();
            }
        }
    }

    private void startListening() {
        log.info("Start listening on port {}" ,portName);
        try {
            this.portReader = new PortReader(serialPort);
            serialPort.addEventListener(portReader, SerialPort.MASK_RXCHAR);
        } catch (SerialPortException e) {
            log.error("Serial Exception", e);
            throw new RuntimeException(e);
        }
    }
}
