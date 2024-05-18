package org.example.arduinoserver.service;

import java.time.Duration;
import org.example.arduinoserver.dao.PumpDAO;
import org.example.arduinoserver.entity.OperationsEntity;
import org.example.arduinoserver.model.LogWork;
import org.example.arduinoserver.utils.LogWorkPumpSingleton;
import org.example.arduinoserver.utils.TimeStampUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PumpService {

  @Autowired
  private PumpDAO pumpDAO;

  private LogWork logWork;

  // Формула для литров 1.2 / logWork
  // Помпа   1,1,1,1,1,1,1,0, 1,0
  // Клапан  0, 1

  public void loggingPump(Integer numberPumpWork, OperationsEntity operationsEntity) {
    if (numberPumpWork == 1) {
      logWork = LogWorkPumpSingleton.getInstance().getLogWorkPump();
    }
    else if (numberPumpWork == 0 && logWork != null) {
      logWork.setTimestampEndWork(TimeStampUtils.getTimestamp());
      Duration duration = Duration.ofMillis((int) logWork.getTimestampEndWork().getTime() - (int) logWork.getTimestampStartWork().getTime());
      int logWorkTime = duration.toSecondsPart();
      double liters = 1.2 / logWorkTime;
      pumpDAO.create(operationsEntity, logWorkTime, liters);
      LogWorkPumpSingleton.setLogWorkPumpGeneratorOnNull();
      logWork = null;
    }
  }
}
