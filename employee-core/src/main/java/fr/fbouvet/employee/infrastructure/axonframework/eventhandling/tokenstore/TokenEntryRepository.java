package fr.fbouvet.employee.infrastructure.axonframework.eventhandling.tokenstore;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.stream.Stream;

public interface TokenEntryRepository extends CassandraRepository<TokenEntry, TokenEntryPK> {

    @Query("select * from token_entry where processorName=:processorName")
    Stream<TokenEntry> findByProcessorName(@Param("processorName") String processorName);
}
