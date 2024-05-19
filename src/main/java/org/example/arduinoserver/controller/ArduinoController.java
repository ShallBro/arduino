package org.example.arduinoserver.controller;

import java.util.List;
import org.example.arduinoserver.model.Log;
import org.example.arduinoserver.model.Message;
import org.example.arduinoserver.model.Test;
import org.example.arduinoserver.model.User;
import org.example.arduinoserver.service.ArduinoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
public class ArduinoController {

  @Autowired
  private ArduinoService arduinoService;

  @GetMapping("/message")
  public Message getMessage(@RequestBody Test test) {
    return arduinoService.getValueArduino(test);
  }

  @PostMapping("/startOperation")
  public void startOperation(@RequestBody User user) {
    arduinoService.startOperation(user);
  }

  @PostMapping("/endOperation")
  public void endOperation() {
    arduinoService.stopOperation();
  }

  @GetMapping("/getLogs")
  public List<Log> getLogs() {
    return arduinoService.getLogsOperations();
  }
}