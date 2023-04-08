package ua.lpnu.ngdeck;

import jssc.*;
import ua.lpnu.ngdeck.data.Codes;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        SerialPort serialPort = new SerialPort("/dev/ttyUSB0");
        try {
            serialPort.openPort();
            serialPort.setParams(9600, 8, 1, SerialPort.PARITY_NONE);//Set params.

            PortReader portReader = new PortReader(serialPort);
            serialPort.addEventListener(portReader, SerialPort.MASK_RXCHAR);
        }
        catch (SerialPortException ex) {
            ex.printStackTrace();
        }
    }
}

class PortReader implements SerialPortEventListener {

    SerialPort serialPort;
    public PortReader(SerialPort serialPort) {
        this.serialPort = serialPort;
    }

    @Override
    public void serialEvent(SerialPortEvent event) {
        System.out.println("started");
        if (event.isRXCHAR() && event.getEventValue() > 0) {
            try {
                String receivedData = serialPort.readString(event.getEventValue());
                System.out.println("Received response: " + receivedData);

                if(receivedData.startsWith("lnch")) {
                    File project = FilesAnalyzer.findProjects()
                            .get(Integer.parseInt(receivedData.substring(4)));
                    ProcessBuilder pb = new ProcessBuilder("ng", "serve");
                    pb.directory(project);
                    pb.start();
                } else if (receivedData.equals("1111")) {
                    Thread.sleep(100);
                    try {
                        serialPort.writeString(String.valueOf(Codes.OK));
                    } catch (SerialPortException e) {
                        e.printStackTrace();
                    }
                } else if (receivedData.equals("9999")) {
                    new DataSenderThread(serialPort, FilesAnalyzer.getData()).start();
                } else {
                    System.out.println("Nothing");
                }
            } catch (SerialPortException | IOException | InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }
}

class DataSenderThread extends Thread {

    SerialPort serialPort;
    List<String> list;

    public DataSenderThread(SerialPort serialPort, List<String> list) {
        this.serialPort = serialPort;
        this.list = list;
    }

    @Override
    public void run() {
        try {
            System.out.println(list);
            serialPort.writeString(list.size() + ",");
            System.out.println("Size: "  + list.size());
            Thread.sleep(100);
            for (String s : list) {
                serialPort.writeString(s + ",");
            }
        } catch (SerialPortException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}