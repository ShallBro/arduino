package org.example.arduinoserver.utils;

import lombok.Getter;
import org.example.arduinoserver.model.LogWork;

@Getter
public class LogWorkValveSingleton {
  private static LogWorkValveSingleton logWorkValveSingleton;

  private LogWork logWorkValve;

  private LogWorkValveSingleton() {
    logWorkValve = new LogWork(TimeStampUtils.getTimestamp());
  }

  public static LogWorkValveSingleton getInstance() {
    if (logWorkValveSingleton == null) {
      logWorkValveSingleton = new LogWorkValveSingleton();
    }
    return logWorkValveSingleton;
  }

  public static void setLogWorkValveGeneratorOnNull() {
    logWorkValveSingleton = null;
  }
}
