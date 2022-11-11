package fr.fbouvet.employee.api.command;

import java.time.LocalDate;
import java.util.UUID;

import lombok.Builder;
import org.axonframework.modelling.command.TargetAggregateIdentifier;


@Builder
public record CreateEmployeeCommand(
        @TargetAggregateIdentifier
        UUID id,
        String name,
        String address,
        String email,
        LocalDate birthDate
) {
}
