package org.example.arduinoserver.dao;

import org.example.arduinoserver.entity.OperationsEntity;
import org.example.arduinoserver.entity.ValveEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class ValveDAO {

  @Autowired
  private SessionFactory sessionFactory;

  @Transactional
  public void create(OperationsEntity operationsEntity, Integer logWork) {
    Session session = sessionFactory.getCurrentSession();
    ValveEntity pumpEntity = new ValveEntity(operationsEntity, logWork);
    session.persist(pumpEntity);
  }
}
