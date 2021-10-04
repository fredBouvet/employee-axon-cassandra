package fr.fbouvet.employee.api.event;

import java.time.LocalDate;
import java.util.UUID;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class EmployeeCreatedEvent {

  UUID id;
  String name;
  String address;
  String email;
  LocalDate birthDate;
}
