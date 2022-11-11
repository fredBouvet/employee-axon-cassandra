package fr.fbouvet.employee.infrastructure.axonframework.eventhandling.tokenstore;

import org.springframework.data.cassandra.repository.CassandraRepository;

public interface TokenEntryOwnerRepository extends CassandraRepository<TokenEntryOwner, String> {
}
