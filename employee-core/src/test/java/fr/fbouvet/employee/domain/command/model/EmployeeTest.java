package fr.fbouvet.employee.domain.command.model;


import fr.fbouvet.employee.api.command.ChangeEmployeeNameCommand;
import fr.fbouvet.employee.api.command.CreateEmployeeCommand;
import fr.fbouvet.employee.api.event.EmployeeCreatedEvent;
import fr.fbouvet.employee.api.event.EmployeeNameChangedEvent;
import org.axonframework.modelling.command.AggregateNotFoundException;
import org.axonframework.test.aggregate.AggregateTestFixture;
import org.axonframework.test.aggregate.FixtureConfiguration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.UUID;

import static java.util.UUID.randomUUID;
import static org.junit.jupiter.api.Assertions.fail;

class EmployeeTest {

    private FixtureConfiguration<Employee> fixture;

    @BeforeEach
    void setUp() {
        fixture = new AggregateTestFixture<>(Employee.class);
    }

    @Test
    void createEmployeeCommand_withMissingName_shouldThrowException() {

        try {
            CreateEmployeeCommand.builder()
                    .id(randomUUID())
                    .name(null)
                    .address("42 example road")
                    .email("toto@example.com")
                    .birthDate(LocalDate.now().minusYears(21))
                    .build();

            fail("IllegalArgumentException expected");

        } catch (IllegalArgumentException e) {
            // test ok
        }
    }

    @Test
    void createEmployeeCommand_withMissingBirthDate_shouldThrowException() {

        try {
            CreateEmployeeCommand.builder()
                    .id(randomUUID())
                    .name("toto")
                    .address("42 example road")
                    .email("toto@example.com")
                    .birthDate(null)
                    .build();

            fail("IllegalArgumentException expected");

        } catch (IllegalArgumentException e) {
            // test ok
        }
    }

    @Test
    void createEmployeeCommand_withBadEmail_shouldThrowException() {

        try {
            CreateEmployeeCommand.builder()
                    .id(randomUUID())
                    .name("toto")
                    .address("42 example road")
                    .email("Bad email")
                    .birthDate(LocalDate.now().minusYears(21))
                    .build();

            fail("IllegalArgumentException expected");

        } catch (IllegalArgumentException e) {
            // test ok
        }
    }

    @Test
    void createEmployeeCommand_withGoodData_shouldSendEmployeeCreatedEvent() {

        CreateEmployeeCommand command = CreateEmployeeCommand.builder()
                .id(randomUUID())
                .name("toto")
                .address("42 example road")
                .email("toto@example.com")
                .birthDate(LocalDate.now().minusYears(21))
                .build();

        fixture.givenNoPriorActivity()
                .when(command)
                .expectSuccessfulHandlerExecution()
                .expectEvents(
                        EmployeeCreatedEvent.builder()
                                .id(command.id())
                                .name(command.name())
                                .address(command.address())
                                .email(command.email())
                                .birthDate(command.birthDate())
                                .build()
                );
    }

    @Test
    void changeEmployeeNameCommand_withNoEmployee_shouldThrowException() {

        UUID id = randomUUID();
        String name = "toto";

        fixture.givenNoPriorActivity()
                .when(ChangeEmployeeNameCommand.builder()
                        .id(id)
                        .name(name)
                        .build())
                .expectException(AggregateNotFoundException.class);
    }

    @Test
    void changeEmployeeNameCommand_withMissingName_shouldThrowException() {

        UUID id = randomUUID();

        try {
            ChangeEmployeeNameCommand.builder()
                    .id(id)
                    .build();

            fail("IllegalArgumentException expected");

        } catch (IllegalArgumentException e) {
            // test ok
        }
    }

    @Test
    void changeEmployeeNameCommand_withSameName_shouldThrowException() {

        UUID id = randomUUID();
        String name = "toto";

        fixture.given(EmployeeCreatedEvent.builder()
                        .id(id)
                        .name(name)
                        .address("42 example road")
                        .email("toto@example.com")
                        .birthDate(LocalDate.now().minusYears(21))
                        .build())
                .when(
                        ChangeEmployeeNameCommand.builder()
                                .id(id)
                                .name(name)
                                .build()
                )
                .expectException(IllegalArgumentException.class);
    }

    @Test
    void changeEmployeeNameCommand_withOtherName_shouldSendEmployeeNameChangedEvent() {

        UUID id = randomUUID();

        String newName = "bob";
        fixture.given(
                        EmployeeCreatedEvent.builder()
                                .id(id)
                                .name("toto")
                                .address("42 example road")
                                .email("toto@example.com")
                                .birthDate(LocalDate.now().minusYears(21))
                                .build()
                )
                .when(
                        ChangeEmployeeNameCommand.builder()
                                .id(id)
                                .name(newName)
                                .build()
                )
                .expectSuccessfulHandlerExecution()
                .expectEvents(
                        EmployeeNameChangedEvent.builder()
                                .id(id)
                                .name(newName)
                                .build()
                );
    }
}
