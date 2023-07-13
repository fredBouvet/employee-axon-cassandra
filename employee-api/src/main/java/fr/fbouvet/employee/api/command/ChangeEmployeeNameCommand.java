package fr.fbouvet.employee.api.command;

import lombok.Builder;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;

import static fr.fbouvet.employee.api.Validator.validate;


public record ChangeEmployeeNameCommand(
        @TargetAggregateIdentifier
        @NotNull
        UUID id,
        @NotEmpty
        String name
) {
    @Builder
    public ChangeEmployeeNameCommand {
        validate(ChangeEmployeeNameCommand.class, id, name);
    }
}
