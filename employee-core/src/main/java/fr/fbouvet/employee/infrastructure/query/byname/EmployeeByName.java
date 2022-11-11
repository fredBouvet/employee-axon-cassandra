package fr.fbouvet.employee.infrastructure.query.byname;

import lombok.Builder;
import lombok.Value;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.time.LocalDate;

@Table("employee_by_name")
@Value
@Builder
public class EmployeeByName {

    @PrimaryKey
    EmployeeKeyByName key;
    String address;
    String email;
    LocalDate birthDate;
}
