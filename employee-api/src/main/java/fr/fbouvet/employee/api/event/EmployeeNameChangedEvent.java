package fr.fbouvet.employee.api.event;

import lombok.Builder;

import java.util.UUID;

@Builder
public record EmployeeNameChangedEvent(
        UUID id,
        String name
) {
}
