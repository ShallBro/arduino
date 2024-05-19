package org.example.arduinoserver.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Message {
  private Integer switch1;
  private Integer switch2;
  private Integer pump;
  private Integer valve;
}