package fr.fbouvet.employee.infrastructure.query.byid;

import org.springframework.data.cassandra.repository.CassandraRepository;

import java.util.UUID;

public interface EmployeeByIdRepository extends CassandraRepository<EmployeeById, UUID> {

}
