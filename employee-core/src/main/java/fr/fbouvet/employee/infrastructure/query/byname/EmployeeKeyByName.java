package fr.fbouvet.employee.infrastructure.query.byname;

import static org.springframework.data.cassandra.core.cql.PrimaryKeyType.CLUSTERED;
import static org.springframework.data.cassandra.core.cql.PrimaryKeyType.PARTITIONED;

import java.util.UUID;
import lombok.Builder;
import lombok.Value;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyClass;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;

@PrimaryKeyClass
@Value
@Builder
public class EmployeeKeyByName {

  @PrimaryKeyColumn(type = PARTITIONED)
  String name;

  @PrimaryKeyColumn(type = CLUSTERED)
  UUID id;

}
