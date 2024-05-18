package org.example.arduinoserver.service;

import java.time.Duration;
import org.example.arduinoserver.dao.ValveDAO;
import org.example.arduinoserver.entity.OperationsEntity;
import org.example.arduinoserver.model.LogWork;
import org.example.arduinoserver.utils.LogWorkPumpSingleton;
import org.example.arduinoserver.utils.LogWorkValveSingleton;
import org.example.arduinoserver.utils.TimeStampUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ValveService {
  @Autowired
  private ValveDAO valveDAO;

  private LogWork logWork;

  // Формула для литров 1.2 / logWork
  // Помпа   1,1,1,1,1,1,1,0, 1,0
  // Клапан  0, 1

  public void loggingValve(Integer numberPumpWork, OperationsEntity operationsEntity) {
    if (numberPumpWork == 0) {
      logWork = LogWorkValveSingleton.getInstance().getLogWorkValve();
    }
    else if (numberPumpWork == 1 && logWork != null) {
      logWork.setTimestampEndWork(TimeStampUtils.getTimestamp());
      Duration duration = Duration.ofMillis((int) logWork.getTimestampEndWork().getTime() - (int) logWork.getTimestampStartWork().getTime());
      int logWorkTime = duration.toSecondsPart();
      valveDAO.create(operationsEntity, logWorkTime);
      LogWorkValveSingleton.setLogWorkValveGeneratorOnNull();
      logWork = null;
    }
  }
}
