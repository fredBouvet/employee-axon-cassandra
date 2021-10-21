package fr.fbouvet.employee.api.query;

import java.time.LocalDate;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class FindEmployeeByBirthDateQuery {

  LocalDate birthDate;
}
