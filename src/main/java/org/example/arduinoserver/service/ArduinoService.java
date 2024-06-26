package org.example.arduinoserver.service;

import com.fazecast.jSerialComm.SerialPort;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.example.arduinoserver.arduino.ArduinoReader;
import org.example.arduinoserver.dao.OperationsDAO;
import org.example.arduinoserver.dao.SensorsDAO;
import org.example.arduinoserver.entity.OperationsEntity;
import org.example.arduinoserver.model.Log;
import org.example.arduinoserver.model.Message;
import org.example.arduinoserver.model.Test;
import org.example.arduinoserver.model.User;
import org.example.arduinoserver.utils.TimeStampUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ArduinoService {

  private static String receivedMessage;
  private final SerialPort serialPort;
  private final Integer DATA_RATE = 500000;
  private final Integer TIME_OUT = 10000;


  public ArduinoService() {
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
  @Autowired
  private ArduinoReader arduinoReader;

  @Autowired
  private OperationsDAO operationsDAO;

  @Autowired
  private SensorsDAO sensorsDAO;

  @Autowired
  private PumpService pumpService;

  @Autowired
  private ValveService valveService;

  private OperationsEntity operationsEntity;

  private Message message;

  public static String getReceivedMessage() {
    return receivedMessage;
  }

  public static void setReceivedMessage(String a) {
    receivedMessage = a;
  }

  public void startOperation(User user) {
    operationsEntity = operationsDAO.create(TimeStampUtils.getTimestamp(), user.getUser());
  }

  public List<Log> getLogsOperations() {
    List<Log> logList = new ArrayList<>();
    var operationsEntityList = operationsDAO.get();
    for (OperationsEntity operationsEntityCurrent : operationsEntityList) {
      Log log = new Log();
      List<Map<String, Object>> valveList = new ArrayList<>();
      List<Map<String, Object>> pumpList = new ArrayList<>();
      List<Map<String, Object>> sensors = new ArrayList<>();
      operationsEntityCurrent.getSensorsEntityList().forEach(sensorsEntity -> {
        Map<String, Object> sensorsMap = new HashMap<>();
        sensorsMap.put("log_start", sensorsEntity.getLog_start().toString());
        sensorsMap.put("sensor1", sensorsEntity.getSensors1());
        sensorsMap.put("sensor2", sensorsEntity.getSensors2());
        sensorsMap.put("log_end", sensorsEntity.getLog_end().toString());
        sensors.add(sensorsMap);
      });
      operationsEntityCurrent.getPumpEntityList().forEach(pumpEntity -> {
        Map<String, Object> pumpsMap = new HashMap<>();
        pumpsMap.put("log_work", pumpEntity.getLog_work());
        pumpsMap.put("liters", pumpEntity.getLiters());
        pumpList.add(pumpsMap);
      });
      operationsEntityCurrent.getValveEntityList().forEach(valveEntity -> {
        Map<String, Object> valvesMap = new HashMap<>();
        valvesMap.put("log_work", valveEntity.getLog_work());
        valveList.add(valvesMap);
      });
      Map<String, Object> operation = new HashMap<>();
      operation.put("log_start", operationsEntityCurrent.getLog_start().toString());
      operation.put("log_work", operationsEntityCurrent.getLog_work());
      operation.put("log_end", operationsEntityCurrent.getLog_end().toString());
      log.setSensors(sensors);
      log.setOperation(operation);
      log.setIdOperation(operationsEntityCurrent.getId());
      log.setUser(operationsEntityCurrent.getName());
      log.setPump(pumpList);
      log.setValve(valveList);
      logList.add(log);
    }
    return logList;
  }

  public Message getValueArduino(Test test) {
    Timestamp timestampStartSensors = TimeStampUtils.getTimestamp();
    message = new Message();
//        String resultArduino = getReceivedMessage().replaceAll("\r\n", "");
//        System.out.println(resultArduino);
//        String[] switchArray = resultArduino.split(",");
    Timestamp timestampStopSensors = TimeStampUtils.getTimestamp();
    String[] switchArray = {"2", "1", test.getPump(), test.getValve()};
    message.setSwitch1(Integer.parseInt(switchArray[0]));
    message.setSwitch2(Integer.parseInt(switchArray[1]));
    message.setPump(Integer.parseInt(switchArray[2]));
    message.setValve(Integer.parseInt(switchArray[3]));
    sensorsDAO.create(timestampStartSensors, timestampStopSensors, operationsEntity, message.getSwitch1(), message.getSwitch2());
    pumpService.loggingPump(message.getPump(), operationsEntity);
    valveService.loggingValve(message.getValve(), operationsEntity);

    return message;
  }

  public void stopOperation() {
    operationsDAO.update(TimeStampUtils.getTimestamp());
    if (message.getPump() == 1) {
      pumpService.loggingPump(0, operationsEntity);
    }
    if (message.getValve() == 0) {
      valveService.loggingValve(1, operationsEntity);
    }
  }
}

