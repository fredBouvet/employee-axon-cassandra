package fr.fbouvet.employee.api.query.model;

import lombok.Builder;

import java.time.LocalDate;
import java.util.UUID;


@Builder
public record EmployeeView(
        UUID id,
        String name,
        String address,
        String email,
        LocalDate birthDate
) {
}
