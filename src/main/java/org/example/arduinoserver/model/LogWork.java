package org.example.arduinoserver.model;

import java.sql.Timestamp;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LogWork {

  private Timestamp timestampStartWork;

  private Timestamp timestampEndWork;

  private Integer liters;

  public LogWork(Timestamp timestampStartWork) {
    this.timestampStartWork = timestampStartWork;
  }
}
