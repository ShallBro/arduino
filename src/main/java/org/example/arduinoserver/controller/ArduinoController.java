package org.example.arduinoserver.controller;

import java.util.List;
import org.example.arduinoserver.model.Log;
import org.example.arduinoserver.model.Message;
import org.example.arduinoserver.service.ArduinoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;

@RestController
@CrossOrigin(origins = "*")
public class ArduinoController {
    @Autowired
    private ArduinoService arduinoService;

    @GetMapping("/message")
    public Message getMessage() {
        return arduinoService.getValueArduino();
    }

    @PostMapping("/startOperation")
    public void startOperation() {
        arduinoService.startOperation();
    }

    @PostMapping("/endOperation")
    public void endOperation(){
        arduinoService.stopOperation();
    }

    @GetMapping("/getLogs")
    public List<Log> getLogs() {
      return arduinoService.getLogsOperations();
    }
}