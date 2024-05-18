package org.example.arduinoserver.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.sql.Timestamp;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name ="pump")
@Getter
@Setter
public class PumpEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "operations_id", referencedColumnName = "id")
  private OperationsEntity operationsEntity;

  private Integer log_work;

  private Double liters;

  public PumpEntity(OperationsEntity operationsEntity, Integer log_work, Double liters) {
    this.operationsEntity = operationsEntity;
    this.log_work = log_work;
    this.liters = liters;
  }

  public PumpEntity() {

  }
}
