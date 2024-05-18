package org.example.arduinoserver.model;

import java.util.List;
import java.util.Map;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Log {
  private Long idOperation;
  private Map<String, Object> operation;
  private List<Map<String, Object>> sensors;
}
