package org.example.arduinoserver.utils;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class TimeStampUtils {

  public static Timestamp getTimestamp() {
    return Timestamp.valueOf(LocalDateTime.now(ZoneId.of("Europe/Moscow")));
  }
}
