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
@Table(name = "sensors")
@Getter
@Setter
public class SensorsEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "operations_id", referencedColumnName = "id")
  private OperationsEntity operationsEntity;

  private Timestamp log_start;

  private Integer sensors1;

  private Integer sensors2;

  private Timestamp log_end;

  public SensorsEntity(OperationsEntity operationsEntity, Timestamp logStart, Integer sensorOne, Integer sensorTwo, Timestamp logEnd) {
    this.operationsEntity = operationsEntity;
    this.log_start = logStart;
    this.sensors1 = sensorOne;
    this.sensors2 = sensorTwo;
    this.log_end = logEnd;
  }

  public SensorsEntity() {

  }
}
