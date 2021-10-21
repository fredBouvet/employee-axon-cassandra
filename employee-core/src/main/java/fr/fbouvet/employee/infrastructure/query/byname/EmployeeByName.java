package fr.fbouvet.employee.infrastructure.query.byname;

import java.time.LocalDate;
import lombok.Builder;
import lombok.Value;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

@Table("employee_by_name")
@Value
@Builder(toBuilder = true)
public class EmployeeByName {

  @PrimaryKey
  EmployeeKeyByName key;
  String address;
  String email;
  LocalDate birthDate;
}
