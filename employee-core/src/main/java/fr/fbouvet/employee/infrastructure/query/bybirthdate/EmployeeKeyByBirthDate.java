package fr.fbouvet.employee.infrastructure.query.bybirthdate;

import static org.springframework.data.cassandra.core.cql.PrimaryKeyType.CLUSTERED;
import static org.springframework.data.cassandra.core.cql.PrimaryKeyType.PARTITIONED;

import java.time.LocalDate;
import java.util.UUID;
import lombok.Builder;
import lombok.Value;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyClass;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;

@PrimaryKeyClass
@Value
@Builder
public class EmployeeKeyByBirthDate {

  @PrimaryKeyColumn(type = PARTITIONED)
  LocalDate birthDate;

  @PrimaryKeyColumn(type = CLUSTERED)
  UUID id;
}
