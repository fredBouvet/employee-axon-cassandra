package fr.fbouvet.employee.infrastructure.axonframework.eventhandling.tokenstore;

import lombok.Builder;
import lombok.Value;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyClass;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;

import static org.springframework.data.cassandra.core.cql.PrimaryKeyType.CLUSTERED;
import static org.springframework.data.cassandra.core.cql.PrimaryKeyType.PARTITIONED;

@PrimaryKeyClass
@Value
@Builder
public class TokenEntryPK {

    @PrimaryKeyColumn(type = PARTITIONED)
    String processorName;

    @PrimaryKeyColumn(type = CLUSTERED)
    int segment;
}
