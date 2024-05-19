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
import org.example.arduinoserver.model.User;

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

  private String name;

  public OperationsEntity(Timestamp logStart, String name) {
    this.log_start = logStart;
    this.name = name;
  }

  @OneToMany(mappedBy = "operationsEntity")
  private List<SensorsEntity> sensorsEntityList;

  @OneToMany(mappedBy = "operationsEntity")
  private List<PumpEntity> pumpEntityList;

  @OneToMany(mappedBy = "operationsEntity")
  private List<ValveEntity> valveEntityList;

  public OperationsEntity() {

  }
}
