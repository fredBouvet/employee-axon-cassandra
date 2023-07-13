package fr.fbouvet.employee.infrastructure.query;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.cassandra.config.AbstractCassandraConfiguration;

@Configuration
public class CassandraConfig extends AbstractCassandraConfiguration {

    @Value("${spring.data.cassandra.keyspace-name}")
    String keySpaceName;

    @Override
    protected String getKeyspaceName() {
        return keySpaceName;
    }
}
