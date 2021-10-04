package fr.fbouvet.employee.infrastructure.query.byid;

import java.util.UUID;
import org.springframework.data.cassandra.repository.CassandraRepository;

public interface EmployeeByIdRepository extends CassandraRepository<EmployeeById, UUID> {

}
