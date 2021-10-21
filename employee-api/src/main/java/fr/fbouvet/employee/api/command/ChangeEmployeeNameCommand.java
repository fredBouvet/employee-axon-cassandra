package fr.fbouvet.employee.api.command;

import java.util.UUID;
import lombok.Builder;
import lombok.Value;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Value
@Builder
public class ChangeEmployeeNameCommand {

  @TargetAggregateIdentifier
  UUID id;
  String name;
}
