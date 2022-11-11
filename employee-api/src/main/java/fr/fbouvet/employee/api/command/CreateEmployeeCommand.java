package fr.fbouvet.employee.api.command;

import lombok.Builder;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.time.LocalDate;
import java.util.UUID;


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
