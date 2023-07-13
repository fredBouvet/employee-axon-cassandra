package fr.fbouvet.employee.domain.command.model;

import fr.fbouvet.employee.api.command.ChangeEmployeeNameCommand;
import fr.fbouvet.employee.api.command.CreateEmployeeCommand;
import fr.fbouvet.employee.api.event.EmployeeCreatedEvent;
import fr.fbouvet.employee.api.event.EmployeeNameChangedEvent;
import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;

import java.util.UUID;

import static org.axonframework.modelling.command.AggregateLifecycle.apply;


@Aggregate
@NoArgsConstructor
public class Employee {

    @AggregateIdentifier
    private UUID id;
    private String name;

    @CommandHandler
    public Employee(CreateEmployeeCommand command) {

        apply(
                EmployeeCreatedEvent.builder()
                        .id(command.id())
                        .name(command.name())
                        .address(command.address())
                        .email(command.email())
                        .birthDate(command.birthDate())
                        .build()
        );
    }

    @CommandHandler
    public void changeName(ChangeEmployeeNameCommand command) {

        if (command.name().equals(name)) {
            throw new IllegalArgumentException("This name is already set");
        }

        apply(
                EmployeeNameChangedEvent.builder()
                        .id(command.id())
                        .name(command.name())
                        .build()
        );
    }

    @EventSourcingHandler
    public void onEmployeeCreated(EmployeeCreatedEvent event) {
        id = event.id();
        name = event.name();
    }

    @EventSourcingHandler
    public void onNameChanged(EmployeeNameChangedEvent event) {
        name = event.name();
    }
}
