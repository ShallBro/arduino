package org.example.arduinoserver.utils;

import lombok.Getter;
import org.example.arduinoserver.model.LogWork;

@Getter
public class LogWorkPumpSingleton {

  private static LogWorkPumpSingleton logWorkPumpSingleton;

  private LogWork logWorkPump;

  private LogWorkPumpSingleton() {
    logWorkPump = new LogWork(TimeStampUtils.getTimestamp());
  }

  public static LogWorkPumpSingleton getInstance() {
    if (logWorkPumpSingleton == null) {
      logWorkPumpSingleton = new LogWorkPumpSingleton();
    }
    return logWorkPumpSingleton;
  }

  public static void setLogWorkPumpGeneratorOnNull() {
    logWorkPumpSingleton = null;
  }
}
