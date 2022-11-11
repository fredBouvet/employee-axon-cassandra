package fr.fbouvet.employee.api.event;

import lombok.Builder;

import java.time.LocalDate;
import java.util.UUID;


@Builder
public record EmployeeCreatedEvent(
        UUID id,
        String name,
        String address,
        String email,
        LocalDate birthDate) {
}
