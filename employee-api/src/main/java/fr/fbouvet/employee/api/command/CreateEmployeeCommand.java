package fr.fbouvet.employee.api.command;

import java.time.LocalDate;
import java.util.UUID;
import lombok.Builder;
import lombok.Value;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Value
@Builder
public class CreateEmployeeCommand {

  @TargetAggregateIdentifier
  UUID id;
  String name;
  String address;
  String email;
  LocalDate birthDate;
}
