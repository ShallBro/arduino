package org.example.arduinoserver.dao;

import java.sql.Timestamp;
import java.util.List;
import org.example.arduinoserver.entity.OperationsEntity;
import org.example.arduinoserver.entity.PumpEntity;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class PumpDAO {

  @Autowired
  private SessionFactory sessionFactory;

  @Transactional
  public void create(OperationsEntity operationsEntity, Integer logWork, Double liters) {
    Session session = sessionFactory.getCurrentSession();
    PumpEntity pumpEntity = new PumpEntity(operationsEntity, logWork, liters);
    session.persist(pumpEntity);
  }

  public List<PumpEntity> get(OperationsEntity operationsEntity) {
    Hibernate.initialize(operationsEntity.getPumpEntityList());
    return operationsEntity.getPumpEntityList();
  }

}
