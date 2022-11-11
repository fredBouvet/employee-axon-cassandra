package fr.fbouvet.employee.infrastructure.query.bybirthdate;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.stream.Stream;

public interface EmployeeByBirthDateRepository extends
        CassandraRepository<EmployeeByBirthDate, EmployeeKeyByBirthDate> {

    @Query("select * from employee_by_birth_date where birthDate=:birthDate")
    Stream<EmployeeByBirthDate> findByBirthDate(@Param("birthDate") LocalDate birthDate);
}
