package fr.fbouvet.employee.infrastructure.query.bybirthdate;

import lombok.Builder;
import lombok.Value;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

@Table("employee_by_birth_date")
@Value
@Builder
public class EmployeeByBirthDate {

    @PrimaryKey
    EmployeeKeyByBirthDate key;
    String name;
    String address;
    String email;
}
