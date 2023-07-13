package fr.fbouvet.employee.api.command;

import lombok.Builder;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.UUID;

import static fr.fbouvet.employee.api.Validator.validate;


public record CreateEmployeeCommand(
        @TargetAggregateIdentifier
        @NotNull
        UUID id,
        @NotEmpty
        String name,
        String address,
        @Email
        String email,
        @NotNull
        LocalDate birthDate
) {

    @Builder
    public CreateEmployeeCommand {
        validate(CreateEmployeeCommand.class, id, name, address, email, birthDate);
    }
}
