package fr.fbouvet.employee.application.rest.dto;

import lombok.Builder;

import java.time.LocalDate;


@Builder
public record EmployeeCreationDto(
        String name,
        String address,
        String email,
        LocalDate birthDate) {
}
