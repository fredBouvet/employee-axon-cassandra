package fr.fbouvet.employee.domain.command.model;

import static org.apache.logging.log4j.util.Strings.isEmpty;
import static org.axonframework.modelling.command.AggregateLifecycle.apply;

import fr.fbouvet.employee.api.command.CreateEmployeeCommand;
import fr.fbouvet.employee.api.event.EmployeeCreatedEvent;
import java.time.LocalDate;
import java.util.UUID;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;

@Aggregate
public class Employee {

  @AggregateIdentifier
  private UUID id;
  private String name;
  private String address;
  private String email;
  private LocalDate birthDate;

  @CommandHandler
  public Employee(CreateEmployeeCommand createEmployeeCommand) {

    if (isEmpty(createEmployeeCommand.getName())) {
      throw new IllegalArgumentException("name must not be empty");
    }

    if (createEmployeeCommand.getBirthDate() == null) {
      throw new IllegalArgumentException("birthdate not be empty");
    }

    apply(
        EmployeeCreatedEvent.builder()
            .id(createEmployeeCommand.getId())
            .name(createEmployeeCommand.getName())
            .address(createEmployeeCommand.getAddress())
            .email(createEmployeeCommand.getEmail())
            .birthDate(createEmployeeCommand.getBirthDate())
            .build()
    );
  }

  @EventSourcingHandler
  public void onEmployeeCreated(EmployeeCreatedEvent employeeCreatedEvent) {
    id = employeeCreatedEvent.getId();
    name = employeeCreatedEvent.getName();
    address = employeeCreatedEvent.getAddress();
    email = employeeCreatedEvent.getEmail();
    birthDate = employeeCreatedEvent.getBirthDate();
  }
}
