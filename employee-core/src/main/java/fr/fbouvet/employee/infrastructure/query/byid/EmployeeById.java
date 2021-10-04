package fr.fbouvet.employee.infrastructure.query.byid;

import java.time.LocalDate;
import java.util.UUID;
import lombok.Builder;
import lombok.Value;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

@Table("employee_by_id")
@Value
@Builder
public class EmployeeById {

  @PrimaryKey
  UUID id;
  String name;
  String address;
  String email;
  LocalDate birthDate;
}
