package org.example.arduinoserver.dao;

import java.sql.Timestamp;
import java.time.Duration;
import java.util.List;
import org.example.arduinoserver.entity.OperationsEntity;
import org.example.arduinoserver.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class OperationsDAO {

  @Autowired
  private SessionFactory sessionFactory;

  private Long operationId;

  @Autowired
  private SensorsDAO sensorsDAO;

  @Autowired
  private PumpDAO pumpDAO;

  @Autowired
  private ValveDAO valveDAO;

  @Transactional
  public OperationsEntity create(Timestamp timestampStartOperation, String userName) {
    Session session = sessionFactory.getCurrentSession();
    OperationsEntity operationsEntity = new OperationsEntity(timestampStartOperation, userName);
    session.persist(operationsEntity);
    operationId = operationsEntity.getId();
    return operationsEntity;
  }

  @Transactional
  public List<OperationsEntity> get() {
    Session session = sessionFactory.getCurrentSession();
    List<OperationsEntity> operationsEntityList = session.createQuery("FROM OperationsEntity").getResultList();
    operationsEntityList.forEach(operationsEntity -> {
      operationsEntity.setSensorsEntityList(sensorsDAO.get(operationsEntity));
      operationsEntity.setPumpEntityList(pumpDAO.get(operationsEntity));
      operationsEntity.setValveEntityList(valveDAO.get(operationsEntity));
    });
    return operationsEntityList;
  }

  @Transactional
  public void update(Timestamp timestampStartSensors) {
    Session session = sessionFactory.getCurrentSession();
    OperationsEntity operationsEntity = session.get(OperationsEntity.class, operationId);
    operationsEntity.setLog_end(timestampStartSensors);
    Duration duration = Duration.ofMillis((int)timestampStartSensors.getTime() - (int)operationsEntity.getLog_start().getTime());
    operationsEntity.setLog_work(duration.toSecondsPart());
  }
}
