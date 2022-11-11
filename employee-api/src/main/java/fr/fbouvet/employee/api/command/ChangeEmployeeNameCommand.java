package fr.fbouvet.employee.api.command;

import lombok.Builder;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.util.UUID;


@Builder
public record ChangeEmployeeNameCommand(
        @TargetAggregateIdentifier
        UUID id,
        String name
) {
}
