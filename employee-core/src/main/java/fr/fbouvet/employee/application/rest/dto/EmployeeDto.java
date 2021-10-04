package fr.fbouvet.employee.application.rest.dto;

import java.time.LocalDate;
import java.util.UUID;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
public class EmployeeDto {

  UUID id;
  String name;
  String address;
  String email;
  LocalDate birthDate;
}
