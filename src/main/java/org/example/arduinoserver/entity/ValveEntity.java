package org.example.arduinoserver.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name ="valve")
public class ValveEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "operations_id", referencedColumnName = "id")
  private OperationsEntity operationsEntity;

  private Integer log_work;

  public ValveEntity() {
  }

  public ValveEntity(OperationsEntity operationsEntity, Integer log_work) {
    this.operationsEntity = operationsEntity;
    this.log_work = log_work;
  }
}
