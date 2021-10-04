package fr.fbouvet.employee.infrastructure.query.byname;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.data.repository.query.Param;

public interface EmployeeByNameRepository extends
    CassandraRepository<EmployeeByName, EmployeeKeyByName> {

  @Query("select * from employee_by_name where name=:name")
  List<EmployeeByName> findByName(@Param("name") String name);

  @Query("select * from employee_by_name where id=:id")
  Optional<EmployeeByName> findById(@Param("id") UUID id);
}
