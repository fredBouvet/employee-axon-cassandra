package fr.fbouvet.employee.infrastructure.query.byname;

import java.util.stream.Stream;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.data.repository.query.Param;

public interface EmployeeByNameRepository extends
    CassandraRepository<EmployeeByName, EmployeeKeyByName> {

  @Query("select * from employee_by_name where name=:name")
  Stream<EmployeeByName> findByName(@Param("name") String name);

}
