package fr.fbouvet.employee.domain.command.model;

import static java.util.regex.Pattern.compile;
import static org.apache.logging.log4j.util.Strings.isEmpty;
import static org.axonframework.modelling.command.AggregateLifecycle.apply;

import fr.fbouvet.employee.api.command.ChangeEmployeeNameCommand;
import fr.fbouvet.employee.api.command.CreateEmployeeCommand;
import fr.fbouvet.employee.api.event.EmployeeCreatedEvent;
import fr.fbouvet.employee.api.event.EmployeeNameChangedEvent;
import java.util.UUID;
import java.util.regex.Pattern;
import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;


@Aggregate
@NoArgsConstructor
public class Employee {

  private static final Pattern RFC_5322 = compile(
      "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$"
  );

  @AggregateIdentifier
  private UUID id;
  private String name;

  @CommandHandler
  public Employee(CreateEmployeeCommand command) {

    if (isEmpty(command.getName())) {
      throw new IllegalArgumentException("Name must not be empty");
    }

    if (command.getBirthDate() == null) {
      throw new IllegalArgumentException("Birthdate must not be empty");
    }

    if (!RFC_5322.matcher(command.getEmail()).matches()) {
      throw new IllegalArgumentException("Bad email address");
    }

    apply(
        EmployeeCreatedEvent.builder()
            .id(command.getId())
            .name(command.getName())
            .address(command.getAddress())
            .email(command.getEmail())
            .birthDate(command.getBirthDate())
            .build()
    );
  }

  @CommandHandler
  public void changeName(ChangeEmployeeNameCommand command) {

    if (isEmpty(command.getName())) {
      throw new IllegalArgumentException("Name must not be empty");
    }

    if (command.getName().equals(name)) {
      throw new IllegalArgumentException("This name is already set");
    }

    apply(
        EmployeeNameChangedEvent.builder()
            .id(command.getId())
            .name(command.getName())
            .build()
    );
  }

  @EventSourcingHandler
  public void onEmployeeCreated(EmployeeCreatedEvent event) {
    id = event.getId();
    name = event.getName();
  }

  @EventSourcingHandler
  public void onNameChanged(EmployeeNameChangedEvent event) {
    name = event.getName();
  }
}
