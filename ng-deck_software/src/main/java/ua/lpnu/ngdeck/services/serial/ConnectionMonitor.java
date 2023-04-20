package ua.lpnu.ngdeck.services.serial;

import jssc.SerialPort;
import jssc.SerialPortException;
import jssc.SerialPortList;
import lombok.extern.log4j.Log4j2;

import java.util.Arrays;
import java.util.List;

@Log4j2
public class ConnectionMonitor extends Thread {

    private String port;
    private SerialPort serialPort;
    private boolean isConnected = false;

    @Override
    public void run() {
        log.info("ConnectionMonitor thread start");
        while (true) {
            try {
                this.checkConnection();

                if(!isConnected) {
                    this.findPort();
                }

                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public SerialPort getSerialPort() {
        log.trace("getSerialPort call");
        try {
            while (!isConnected) {
                Thread.sleep(100);
            }
            return serialPort;
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public String getPortName() {
        return port;
    }

    private void findPort() {
        log.trace("findPort call");
        if(!isConnected) {
            List<String> ports = getPorts();

            for (String port : ports) {
                try {
                    serialPort = new SerialPort(port);
                    serialPort.openPort();
                    if (serialPort.isOpened()) {
                        checkPort(port);
                    }
                    if (this.port != null) {
                        break;
                    }
                } catch (SerialPortException | InterruptedException e) {
                    log.error(e);
                }
            }
        }
    }

    private void checkConnection() {
        log.trace("Check connection call");
        boolean isOpen = false;
        boolean isPortExist = false;

        if(serialPort != null) {
            isOpen = serialPort.isOpened();
        }

        if(port != null) {
            isPortExist = getPorts().contains(this.port);
        }

        this.isConnected = isOpen && isPortExist;
        log.trace("is port active: {}", this.isConnected);
    }

    private void checkPort(String port) throws SerialPortException, InterruptedException {
        log.trace("checkPort call");
        this.initPort();
        Thread.sleep(2500);

        String read = serialPort.readString();
        log.trace("Got '{}' from {}", read, port);
        if(read != null && read.startsWith("1111")) {
            log.trace("port was found");
            this.port = port;
        }
    }

    private void initPort() throws SerialPortException {
        serialPort.setParams(9600, 8, 1, SerialPort.PARITY_NONE);//Set params.
    }

    private List<String> getPorts() {
        return Arrays.stream(SerialPortList.getPortNames())
                .filter(port -> port.startsWith("/dev/ttyUSB"))
                .toList();
    }
}
