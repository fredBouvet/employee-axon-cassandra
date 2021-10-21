package fr.fbouvet.employee.api.event;

import java.util.UUID;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class EmployeeNameChangedEvent {

  UUID id;
  String name;
}
