package fr.fbouvet.employee.application.rest.dto;

import lombok.Builder;

import java.time.LocalDate;
import java.util.UUID;


@Builder
public record EmployeeDto(
        UUID id,
        String name,
        String address,
        String email,
        LocalDate birthDate) {
}
