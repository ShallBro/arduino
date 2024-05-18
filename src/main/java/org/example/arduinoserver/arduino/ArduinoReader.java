package org.example.arduinoserver.arduino;

import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortEvent;
import com.fazecast.jSerialComm.SerialPortMessageListener;

import java.io.UnsupportedEncodingException;
import org.example.arduinoserver.service.ArduinoService;

public class ArduinoReader implements SerialPortMessageListener {

    // возвращает разделитель сообщения (в данном случае 0x0a - new line)
    @Override
    public byte[] getMessageDelimiter() {
        return new byte[] {(byte)0x0A};
    }
    // "разделитель означает конец сообщения"
    @Override
    public boolean delimiterIndicatesEndOfMessage() {
        return true;
    }
    // возвращает событие (события?) которые триггерят чтение данных с COM-порта
    @Override
    public int getListeningEvents() {
        return SerialPort.LISTENING_EVENT_DATA_RECEIVED;
    }
    // сам ивент
    @Override
    public void serialEvent(SerialPortEvent serialPortEvent) {
        byte[] delimitedMessage = serialPortEvent.getReceivedData();
        String a = null;
        try {
            a = new String(delimitedMessage, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        if (a.length() == 9) ArduinoService.setReceivedMessage(a);
        else ArduinoService.setReceivedMessage("1,1,1,1\n"); // запасной вариант на случай возникновения ошибок, здесь необходимо указать 2 БЕЗОПАСНЫХ варианта
        System.out.println("Received the following delimited message: " + a);
    }
}
