package org.example.arduinoserver.arduino;
import java.io.UnsupportedEncodingException;
import com.fazecast.jSerialComm.SerialPort;

public class ArduinoCommunication {
    private SerialPort serialPort;

    private static final int TIME_OUT = 10000;  // Можно настроить тайм-аут, если нужно
    private static final int DATA_RATE = 500000;

    public void initialize() throws UnsupportedEncodingException {
        serialPort = SerialPort.getCommPort("COM7");  // Замените "COM7" на ваш COM-порт
        serialPort.setComPortParameters(DATA_RATE, 8, SerialPort.ONE_STOP_BIT, SerialPort.NO_PARITY);
        serialPort.setComPortTimeouts(SerialPort.TIMEOUT_READ_BLOCKING, TIME_OUT, 0);

        if (serialPort.openPort()) {
            System.out.println("Port is open :)");
        } else {
            System.out.println("Failed to open port :(");
        }

        ArduinoReader arduinoReader = new ArduinoReader();
        serialPort.addDataListener(arduinoReader);

    }

    public synchronized void close() {
        if (serialPort != null) {
            serialPort.closePort();
        }
    }
}
