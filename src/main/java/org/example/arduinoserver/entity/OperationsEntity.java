package org.example.arduinoserver.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.sql.Timestamp;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "operations")
@Getter
@Setter
public class OperationsEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private Timestamp log_start;

  private Integer log_work;

  private Timestamp log_end;

  public OperationsEntity(Timestamp logStart) {
    this.log_start = logStart;
  }

  @OneToMany(mappedBy = "operationsEntity")
  private List<SensorsEntity> sensorsEntityList;

  public OperationsEntity() {

  }
}
