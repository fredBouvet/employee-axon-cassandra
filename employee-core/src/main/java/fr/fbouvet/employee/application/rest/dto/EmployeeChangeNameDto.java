package fr.fbouvet.employee.application.rest.dto;

import lombok.Builder;

@Builder
public record EmployeeChangeNameDto(String name) {
}
