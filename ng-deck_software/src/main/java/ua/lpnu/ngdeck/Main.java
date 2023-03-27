package ua.lpnu.ngdeck;

import jssc.*;
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

                if(receivedData.equals("1")) {
                    ProcessBuilder pb = new ProcessBuilder("gnome-terminal");
                    pb.start();
                } else if (receivedData.equals("900")) {
                    new DataSenderThread(serialPort, FilesAnalyzer.getData()).start();
                }
            } catch (SerialPortException | IOException ex) {
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

            for (String s : list) {
                serialPort.writeString(s + ",");
            }
        } catch (SerialPortException e) {
            e.printStackTrace();
        }
    }
}