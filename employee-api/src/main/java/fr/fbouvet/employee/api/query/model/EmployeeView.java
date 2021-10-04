package fr.fbouvet.employee.api.query.model;

import java.time.LocalDate;
import java.util.UUID;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class EmployeeView {

  UUID id;
  String name;
  String address;
  String email;
  LocalDate birthDate;
}
