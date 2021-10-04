package fr.fbouvet.employee.application.rest.dto;

import java.time.LocalDate;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
public class EmployeeCreationDto {

  String name;
  String address;
  String email;
  LocalDate birthDate;
}
