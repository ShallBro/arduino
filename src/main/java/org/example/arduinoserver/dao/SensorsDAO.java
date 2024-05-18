package org.example.arduinoserver.dao;

import java.sql.Timestamp;
import java.util.List;
import org.example.arduinoserver.entity.OperationsEntity;
import org.example.arduinoserver.entity.SensorsEntity;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class SensorsDAO {
  @Autowired
  private SessionFactory sessionFactory;

  private Long sensorsId;

  @Transactional
  public void create(Timestamp timestampStartSensors, Timestamp timestampStopSensors, OperationsEntity operationsEntity, Integer sensors1, Integer sensors2) {
    Session session = sessionFactory.getCurrentSession();
    SensorsEntity sensorsEntity = new SensorsEntity(operationsEntity, timestampStartSensors, sensors1, sensors2, timestampStopSensors);
    session.persist(sensorsEntity);
  }

  public List<SensorsEntity> get(OperationsEntity operationsEntity) {
    Hibernate.initialize(operationsEntity.getSensorsEntityList());
    return operationsEntity.getSensorsEntityList();
  }
}
